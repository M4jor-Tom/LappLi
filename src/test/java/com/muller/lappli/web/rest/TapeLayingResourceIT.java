package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.StrandSupply;
import com.muller.lappli.domain.Tape;
import com.muller.lappli.domain.TapeLaying;
import com.muller.lappli.domain.enumeration.AssemblyMean;
import com.muller.lappli.repository.TapeLayingRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TapeLayingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TapeLayingResourceIT {

    private static final Long DEFAULT_OPERATION_LAYER = 1L;
    private static final Long UPDATED_OPERATION_LAYER = 2L;

    private static final AssemblyMean DEFAULT_ASSEMBLY_MEAN = AssemblyMean.RIGHT;
    private static final AssemblyMean UPDATED_ASSEMBLY_MEAN = AssemblyMean.LEFT;

    private static final String ENTITY_API_URL = "/api/tape-layings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TapeLayingRepository tapeLayingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTapeLayingMockMvc;

    private TapeLaying tapeLaying;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TapeLaying createEntity(EntityManager em) {
        TapeLaying tapeLaying = new TapeLaying().operationLayer(DEFAULT_OPERATION_LAYER).assemblyMean(DEFAULT_ASSEMBLY_MEAN);
        // Add required entity
        Tape tape;
        if (TestUtil.findAll(em, Tape.class).isEmpty()) {
            tape = TapeResourceIT.createEntity(em);
            em.persist(tape);
            em.flush();
        } else {
            tape = TestUtil.findAll(em, Tape.class).get(0);
        }
        tapeLaying.setTape(tape);
        // Add required entity
        StrandSupply strandSupply;
        if (TestUtil.findAll(em, StrandSupply.class).isEmpty()) {
            strandSupply = StrandSupplyResourceIT.createEntity(em);
            em.persist(strandSupply);
            em.flush();
        } else {
            strandSupply = TestUtil.findAll(em, StrandSupply.class).get(0);
        }
        tapeLaying.setOwnerStrandSupply(strandSupply);
        return tapeLaying;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TapeLaying createUpdatedEntity(EntityManager em) {
        TapeLaying tapeLaying = new TapeLaying().operationLayer(UPDATED_OPERATION_LAYER).assemblyMean(UPDATED_ASSEMBLY_MEAN);
        // Add required entity
        Tape tape;
        if (TestUtil.findAll(em, Tape.class).isEmpty()) {
            tape = TapeResourceIT.createUpdatedEntity(em);
            em.persist(tape);
            em.flush();
        } else {
            tape = TestUtil.findAll(em, Tape.class).get(0);
        }
        tapeLaying.setTape(tape);
        // Add required entity
        StrandSupply strandSupply;
        if (TestUtil.findAll(em, StrandSupply.class).isEmpty()) {
            strandSupply = StrandSupplyResourceIT.createUpdatedEntity(em);
            em.persist(strandSupply);
            em.flush();
        } else {
            strandSupply = TestUtil.findAll(em, StrandSupply.class).get(0);
        }
        tapeLaying.setOwnerStrandSupply(strandSupply);
        return tapeLaying;
    }

    @BeforeEach
    public void initTest() {
        tapeLaying = createEntity(em);
    }

    @Test
    @Transactional
    void createTapeLaying() throws Exception {
        int databaseSizeBeforeCreate = tapeLayingRepository.findAll().size();
        // Create the TapeLaying
        restTapeLayingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tapeLaying)))
            .andExpect(status().isCreated());

        // Validate the TapeLaying in the database
        List<TapeLaying> tapeLayingList = tapeLayingRepository.findAll();
        assertThat(tapeLayingList).hasSize(databaseSizeBeforeCreate + 1);
        TapeLaying testTapeLaying = tapeLayingList.get(tapeLayingList.size() - 1);
        assertThat(testTapeLaying.getOperationLayer()).isEqualTo(DEFAULT_OPERATION_LAYER);
        assertThat(testTapeLaying.getAssemblyMean()).isEqualTo(DEFAULT_ASSEMBLY_MEAN);
    }

    @Test
    @Transactional
    void createTapeLayingWithExistingId() throws Exception {
        // Create the TapeLaying with an existing ID
        tapeLaying.setId(1L);

        int databaseSizeBeforeCreate = tapeLayingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTapeLayingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tapeLaying)))
            .andExpect(status().isBadRequest());

        // Validate the TapeLaying in the database
        List<TapeLaying> tapeLayingList = tapeLayingRepository.findAll();
        assertThat(tapeLayingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkOperationLayerIsRequired() throws Exception {
        int databaseSizeBeforeTest = tapeLayingRepository.findAll().size();
        // set the field null
        tapeLaying.setOperationLayer(null);

        // Create the TapeLaying, which fails.

        restTapeLayingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tapeLaying)))
            .andExpect(status().isBadRequest());

        List<TapeLaying> tapeLayingList = tapeLayingRepository.findAll();
        assertThat(tapeLayingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAssemblyMeanIsRequired() throws Exception {
        int databaseSizeBeforeTest = tapeLayingRepository.findAll().size();
        // set the field null
        tapeLaying.setAssemblyMean(null);

        // Create the TapeLaying, which fails.

        restTapeLayingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tapeLaying)))
            .andExpect(status().isBadRequest());

        List<TapeLaying> tapeLayingList = tapeLayingRepository.findAll();
        assertThat(tapeLayingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTapeLayings() throws Exception {
        // Initialize the database
        tapeLayingRepository.saveAndFlush(tapeLaying);

        // Get all the tapeLayingList
        restTapeLayingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tapeLaying.getId().intValue())))
            .andExpect(jsonPath("$.[*].operationLayer").value(hasItem(DEFAULT_OPERATION_LAYER.intValue())))
            .andExpect(jsonPath("$.[*].assemblyMean").value(hasItem(DEFAULT_ASSEMBLY_MEAN.toString())));
    }

    @Test
    @Transactional
    void getTapeLaying() throws Exception {
        // Initialize the database
        tapeLayingRepository.saveAndFlush(tapeLaying);

        // Get the tapeLaying
        restTapeLayingMockMvc
            .perform(get(ENTITY_API_URL_ID, tapeLaying.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tapeLaying.getId().intValue()))
            .andExpect(jsonPath("$.operationLayer").value(DEFAULT_OPERATION_LAYER.intValue()))
            .andExpect(jsonPath("$.assemblyMean").value(DEFAULT_ASSEMBLY_MEAN.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTapeLaying() throws Exception {
        // Get the tapeLaying
        restTapeLayingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTapeLaying() throws Exception {
        // Initialize the database
        tapeLayingRepository.saveAndFlush(tapeLaying);

        int databaseSizeBeforeUpdate = tapeLayingRepository.findAll().size();

        // Update the tapeLaying
        TapeLaying updatedTapeLaying = tapeLayingRepository.findById(tapeLaying.getId()).get();
        // Disconnect from session so that the updates on updatedTapeLaying are not directly saved in db
        em.detach(updatedTapeLaying);
        updatedTapeLaying.operationLayer(UPDATED_OPERATION_LAYER).assemblyMean(UPDATED_ASSEMBLY_MEAN);

        restTapeLayingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTapeLaying.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTapeLaying))
            )
            .andExpect(status().isOk());

        // Validate the TapeLaying in the database
        List<TapeLaying> tapeLayingList = tapeLayingRepository.findAll();
        assertThat(tapeLayingList).hasSize(databaseSizeBeforeUpdate);
        TapeLaying testTapeLaying = tapeLayingList.get(tapeLayingList.size() - 1);
        assertThat(testTapeLaying.getOperationLayer()).isEqualTo(DEFAULT_OPERATION_LAYER);
        assertThat(testTapeLaying.getAssemblyMean()).isEqualTo(UPDATED_ASSEMBLY_MEAN);
    }

    @Test
    @Transactional
    void putNonExistingTapeLaying() throws Exception {
        int databaseSizeBeforeUpdate = tapeLayingRepository.findAll().size();
        tapeLaying.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTapeLayingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tapeLaying.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tapeLaying))
            )
            .andExpect(status().isBadRequest());

        // Validate the TapeLaying in the database
        List<TapeLaying> tapeLayingList = tapeLayingRepository.findAll();
        assertThat(tapeLayingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTapeLaying() throws Exception {
        int databaseSizeBeforeUpdate = tapeLayingRepository.findAll().size();
        tapeLaying.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTapeLayingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tapeLaying))
            )
            .andExpect(status().isBadRequest());

        // Validate the TapeLaying in the database
        List<TapeLaying> tapeLayingList = tapeLayingRepository.findAll();
        assertThat(tapeLayingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTapeLaying() throws Exception {
        int databaseSizeBeforeUpdate = tapeLayingRepository.findAll().size();
        tapeLaying.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTapeLayingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tapeLaying)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TapeLaying in the database
        List<TapeLaying> tapeLayingList = tapeLayingRepository.findAll();
        assertThat(tapeLayingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTapeLayingWithPatch() throws Exception {
        // Initialize the database
        tapeLayingRepository.saveAndFlush(tapeLaying);

        int databaseSizeBeforeUpdate = tapeLayingRepository.findAll().size();

        // Update the tapeLaying using partial update
        TapeLaying partialUpdatedTapeLaying = new TapeLaying();
        partialUpdatedTapeLaying.setId(tapeLaying.getId());

        partialUpdatedTapeLaying.assemblyMean(UPDATED_ASSEMBLY_MEAN);

        restTapeLayingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTapeLaying.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTapeLaying))
            )
            .andExpect(status().isOk());

        // Validate the TapeLaying in the database
        List<TapeLaying> tapeLayingList = tapeLayingRepository.findAll();
        assertThat(tapeLayingList).hasSize(databaseSizeBeforeUpdate);
        TapeLaying testTapeLaying = tapeLayingList.get(tapeLayingList.size() - 1);
        assertThat(testTapeLaying.getOperationLayer()).isEqualTo(DEFAULT_OPERATION_LAYER);
        assertThat(testTapeLaying.getAssemblyMean()).isEqualTo(UPDATED_ASSEMBLY_MEAN);
    }

    @Test
    @Transactional
    void fullUpdateTapeLayingWithPatch() throws Exception {
        // Initialize the database
        tapeLayingRepository.saveAndFlush(tapeLaying);

        int databaseSizeBeforeUpdate = tapeLayingRepository.findAll().size();

        // Update the tapeLaying using partial update
        TapeLaying partialUpdatedTapeLaying = new TapeLaying();
        partialUpdatedTapeLaying.setId(tapeLaying.getId());

        partialUpdatedTapeLaying.operationLayer(UPDATED_OPERATION_LAYER).assemblyMean(UPDATED_ASSEMBLY_MEAN);

        restTapeLayingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTapeLaying.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTapeLaying))
            )
            .andExpect(status().isOk());

        // Validate the TapeLaying in the database
        List<TapeLaying> tapeLayingList = tapeLayingRepository.findAll();
        assertThat(tapeLayingList).hasSize(databaseSizeBeforeUpdate);
        TapeLaying testTapeLaying = tapeLayingList.get(tapeLayingList.size() - 1);
        assertThat(testTapeLaying.getOperationLayer()).isEqualTo(DEFAULT_OPERATION_LAYER);
        assertThat(testTapeLaying.getAssemblyMean()).isEqualTo(UPDATED_ASSEMBLY_MEAN);
    }

    @Test
    @Transactional
    void patchNonExistingTapeLaying() throws Exception {
        int databaseSizeBeforeUpdate = tapeLayingRepository.findAll().size();
        tapeLaying.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTapeLayingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tapeLaying.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tapeLaying))
            )
            .andExpect(status().isBadRequest());

        // Validate the TapeLaying in the database
        List<TapeLaying> tapeLayingList = tapeLayingRepository.findAll();
        assertThat(tapeLayingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTapeLaying() throws Exception {
        int databaseSizeBeforeUpdate = tapeLayingRepository.findAll().size();
        tapeLaying.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTapeLayingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tapeLaying))
            )
            .andExpect(status().isBadRequest());

        // Validate the TapeLaying in the database
        List<TapeLaying> tapeLayingList = tapeLayingRepository.findAll();
        assertThat(tapeLayingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTapeLaying() throws Exception {
        int databaseSizeBeforeUpdate = tapeLayingRepository.findAll().size();
        tapeLaying.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTapeLayingMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tapeLaying))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TapeLaying in the database
        List<TapeLaying> tapeLayingList = tapeLayingRepository.findAll();
        assertThat(tapeLayingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTapeLaying() throws Exception {
        // Initialize the database
        tapeLayingRepository.saveAndFlush(tapeLaying);

        int databaseSizeBeforeDelete = tapeLayingRepository.findAll().size();

        // Delete the tapeLaying
        restTapeLayingMockMvc
            .perform(delete(ENTITY_API_URL_ID, tapeLaying.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TapeLaying> tapeLayingList = tapeLayingRepository.findAll();
        assertThat(tapeLayingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
