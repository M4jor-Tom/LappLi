package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.StrandSupply;
import com.muller.lappli.domain.Strip;
import com.muller.lappli.domain.StripLaying;
import com.muller.lappli.repository.StripLayingRepository;
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
 * Integration tests for the {@link StripLayingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StripLayingResourceIT {

    private static final Long DEFAULT_OPERATION_LAYER = 1L;
    private static final Long UPDATED_OPERATION_LAYER = 2L;

    private static final String ENTITY_API_URL = "/api/strip-layings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StripLayingRepository stripLayingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStripLayingMockMvc;

    private StripLaying stripLaying;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StripLaying createEntity(EntityManager em) {
        StripLaying stripLaying = new StripLaying().operationLayer(DEFAULT_OPERATION_LAYER);
        // Add required entity
        Strip strip;
        if (TestUtil.findAll(em, Strip.class).isEmpty()) {
            strip = StripResourceIT.createEntity(em);
            em.persist(strip);
            em.flush();
        } else {
            strip = TestUtil.findAll(em, Strip.class).get(0);
        }
        stripLaying.setStrip(strip);
        // Add required entity
        StrandSupply strandSupply;
        if (TestUtil.findAll(em, StrandSupply.class).isEmpty()) {
            strandSupply = StrandSupplyResourceIT.createEntity(em);
            em.persist(strandSupply);
            em.flush();
        } else {
            strandSupply = TestUtil.findAll(em, StrandSupply.class).get(0);
        }
        stripLaying.setOwnerStrandSupply(strandSupply);
        return stripLaying;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StripLaying createUpdatedEntity(EntityManager em) {
        StripLaying stripLaying = new StripLaying().operationLayer(UPDATED_OPERATION_LAYER);
        // Add required entity
        Strip strip;
        if (TestUtil.findAll(em, Strip.class).isEmpty()) {
            strip = StripResourceIT.createUpdatedEntity(em);
            em.persist(strip);
            em.flush();
        } else {
            strip = TestUtil.findAll(em, Strip.class).get(0);
        }
        stripLaying.setStrip(strip);
        // Add required entity
        StrandSupply strandSupply;
        if (TestUtil.findAll(em, StrandSupply.class).isEmpty()) {
            strandSupply = StrandSupplyResourceIT.createUpdatedEntity(em);
            em.persist(strandSupply);
            em.flush();
        } else {
            strandSupply = TestUtil.findAll(em, StrandSupply.class).get(0);
        }
        stripLaying.setOwnerStrandSupply(strandSupply);
        return stripLaying;
    }

    @BeforeEach
    public void initTest() {
        stripLaying = createEntity(em);
    }

    @Test
    @Transactional
    void createStripLaying() throws Exception {
        int databaseSizeBeforeCreate = stripLayingRepository.findAll().size();
        // Create the StripLaying
        restStripLayingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stripLaying)))
            .andExpect(status().isCreated());

        // Validate the StripLaying in the database
        List<StripLaying> stripLayingList = stripLayingRepository.findAll();
        assertThat(stripLayingList).hasSize(databaseSizeBeforeCreate + 1);
        StripLaying testStripLaying = stripLayingList.get(stripLayingList.size() - 1);
        assertThat(testStripLaying.getOperationLayer()).isEqualTo(DEFAULT_OPERATION_LAYER);
    }

    @Test
    @Transactional
    void createStripLayingWithExistingId() throws Exception {
        // Create the StripLaying with an existing ID
        stripLaying.setId(1L);

        int databaseSizeBeforeCreate = stripLayingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStripLayingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stripLaying)))
            .andExpect(status().isBadRequest());

        // Validate the StripLaying in the database
        List<StripLaying> stripLayingList = stripLayingRepository.findAll();
        assertThat(stripLayingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkOperationLayerIsRequired() throws Exception {
        int databaseSizeBeforeTest = stripLayingRepository.findAll().size();
        // set the field null
        stripLaying.setOperationLayer(null);

        // Create the StripLaying, which fails.

        restStripLayingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stripLaying)))
            .andExpect(status().isBadRequest());

        List<StripLaying> stripLayingList = stripLayingRepository.findAll();
        assertThat(stripLayingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStripLayings() throws Exception {
        // Initialize the database
        stripLayingRepository.saveAndFlush(stripLaying);

        // Get all the stripLayingList
        restStripLayingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stripLaying.getId().intValue())))
            .andExpect(jsonPath("$.[*].operationLayer").value(hasItem(DEFAULT_OPERATION_LAYER.intValue())));
    }

    @Test
    @Transactional
    void getStripLaying() throws Exception {
        // Initialize the database
        stripLayingRepository.saveAndFlush(stripLaying);

        // Get the stripLaying
        restStripLayingMockMvc
            .perform(get(ENTITY_API_URL_ID, stripLaying.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stripLaying.getId().intValue()))
            .andExpect(jsonPath("$.operationLayer").value(DEFAULT_OPERATION_LAYER.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingStripLaying() throws Exception {
        // Get the stripLaying
        restStripLayingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStripLaying() throws Exception {
        // Initialize the database
        stripLayingRepository.saveAndFlush(stripLaying);

        int databaseSizeBeforeUpdate = stripLayingRepository.findAll().size();

        // Update the stripLaying
        StripLaying updatedStripLaying = stripLayingRepository.findById(stripLaying.getId()).get();
        // Disconnect from session so that the updates on updatedStripLaying are not directly saved in db
        em.detach(updatedStripLaying);
        updatedStripLaying.operationLayer(UPDATED_OPERATION_LAYER);

        restStripLayingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStripLaying.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedStripLaying))
            )
            .andExpect(status().isOk());

        // Validate the StripLaying in the database
        List<StripLaying> stripLayingList = stripLayingRepository.findAll();
        assertThat(stripLayingList).hasSize(databaseSizeBeforeUpdate);
        StripLaying testStripLaying = stripLayingList.get(stripLayingList.size() - 1);
        assertThat(testStripLaying.getOperationLayer()).isEqualTo(DEFAULT_OPERATION_LAYER);
    }

    @Test
    @Transactional
    void putNonExistingStripLaying() throws Exception {
        int databaseSizeBeforeUpdate = stripLayingRepository.findAll().size();
        stripLaying.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStripLayingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stripLaying.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stripLaying))
            )
            .andExpect(status().isBadRequest());

        // Validate the StripLaying in the database
        List<StripLaying> stripLayingList = stripLayingRepository.findAll();
        assertThat(stripLayingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStripLaying() throws Exception {
        int databaseSizeBeforeUpdate = stripLayingRepository.findAll().size();
        stripLaying.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStripLayingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stripLaying))
            )
            .andExpect(status().isBadRequest());

        // Validate the StripLaying in the database
        List<StripLaying> stripLayingList = stripLayingRepository.findAll();
        assertThat(stripLayingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStripLaying() throws Exception {
        int databaseSizeBeforeUpdate = stripLayingRepository.findAll().size();
        stripLaying.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStripLayingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stripLaying)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the StripLaying in the database
        List<StripLaying> stripLayingList = stripLayingRepository.findAll();
        assertThat(stripLayingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStripLayingWithPatch() throws Exception {
        // Initialize the database
        stripLayingRepository.saveAndFlush(stripLaying);

        int databaseSizeBeforeUpdate = stripLayingRepository.findAll().size();

        // Update the stripLaying using partial update
        StripLaying partialUpdatedStripLaying = new StripLaying();
        partialUpdatedStripLaying.setId(stripLaying.getId());

        restStripLayingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStripLaying.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStripLaying))
            )
            .andExpect(status().isOk());

        // Validate the StripLaying in the database
        List<StripLaying> stripLayingList = stripLayingRepository.findAll();
        assertThat(stripLayingList).hasSize(databaseSizeBeforeUpdate);
        StripLaying testStripLaying = stripLayingList.get(stripLayingList.size() - 1);
        assertThat(testStripLaying.getOperationLayer()).isEqualTo(DEFAULT_OPERATION_LAYER);
    }

    @Test
    @Transactional
    void fullUpdateStripLayingWithPatch() throws Exception {
        // Initialize the database
        stripLayingRepository.saveAndFlush(stripLaying);

        int databaseSizeBeforeUpdate = stripLayingRepository.findAll().size();

        // Update the stripLaying using partial update
        StripLaying partialUpdatedStripLaying = new StripLaying();
        partialUpdatedStripLaying.setId(stripLaying.getId());

        partialUpdatedStripLaying.operationLayer(UPDATED_OPERATION_LAYER);

        restStripLayingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStripLaying.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStripLaying))
            )
            .andExpect(status().isOk());

        // Validate the StripLaying in the database
        List<StripLaying> stripLayingList = stripLayingRepository.findAll();
        assertThat(stripLayingList).hasSize(databaseSizeBeforeUpdate);
        StripLaying testStripLaying = stripLayingList.get(stripLayingList.size() - 1);
        assertThat(testStripLaying.getOperationLayer()).isEqualTo(UPDATED_OPERATION_LAYER);
    }

    @Test
    @Transactional
    void patchNonExistingStripLaying() throws Exception {
        int databaseSizeBeforeUpdate = stripLayingRepository.findAll().size();
        stripLaying.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStripLayingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, stripLaying.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stripLaying))
            )
            .andExpect(status().isBadRequest());

        // Validate the StripLaying in the database
        List<StripLaying> stripLayingList = stripLayingRepository.findAll();
        assertThat(stripLayingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStripLaying() throws Exception {
        int databaseSizeBeforeUpdate = stripLayingRepository.findAll().size();
        stripLaying.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStripLayingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stripLaying))
            )
            .andExpect(status().isBadRequest());

        // Validate the StripLaying in the database
        List<StripLaying> stripLayingList = stripLayingRepository.findAll();
        assertThat(stripLayingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStripLaying() throws Exception {
        int databaseSizeBeforeUpdate = stripLayingRepository.findAll().size();
        stripLaying.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStripLayingMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(stripLaying))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StripLaying in the database
        List<StripLaying> stripLayingList = stripLayingRepository.findAll();
        assertThat(stripLayingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStripLaying() throws Exception {
        // Initialize the database
        stripLayingRepository.saveAndFlush(stripLaying);

        int databaseSizeBeforeDelete = stripLayingRepository.findAll().size();

        // Delete the stripLaying
        restStripLayingMockMvc
            .perform(delete(ENTITY_API_URL_ID, stripLaying.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StripLaying> stripLayingList = stripLayingRepository.findAll();
        assertThat(stripLayingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
