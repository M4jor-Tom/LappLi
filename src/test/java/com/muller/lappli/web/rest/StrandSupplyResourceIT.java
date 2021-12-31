package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.Strand;
import com.muller.lappli.domain.StrandSupply;
import com.muller.lappli.domain.Study;
import com.muller.lappli.domain.enumeration.MarkingType;
import com.muller.lappli.domain.enumeration.SupplyState;
import com.muller.lappli.repository.StrandSupplyRepository;
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
 * Integration tests for the {@link StrandSupplyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StrandSupplyResourceIT {

    private static final SupplyState DEFAULT_SUPPLY_STATE = SupplyState.UNDIVIDED;
    private static final SupplyState UPDATED_SUPPLY_STATE = SupplyState.DIVIDED_UNPLACED;

    private static final Long DEFAULT_APPARITIONS = 1L;
    private static final Long UPDATED_APPARITIONS = 2L;

    private static final MarkingType DEFAULT_MARKING_TYPE = MarkingType.LIFTING;
    private static final MarkingType UPDATED_MARKING_TYPE = MarkingType.SPIRALLY_COLORED;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/strand-supplies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StrandSupplyRepository strandSupplyRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStrandSupplyMockMvc;

    private StrandSupply strandSupply;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StrandSupply createEntity(EntityManager em) {
        StrandSupply strandSupply = new StrandSupply()
            .supplyState(DEFAULT_SUPPLY_STATE)
            .apparitions(DEFAULT_APPARITIONS)
            .markingType(DEFAULT_MARKING_TYPE)
            .description(DEFAULT_DESCRIPTION);
        // Add required entity
        Strand strand;
        if (TestUtil.findAll(em, Strand.class).isEmpty()) {
            strand = StrandResourceIT.createEntity(em);
            em.persist(strand);
            em.flush();
        } else {
            strand = TestUtil.findAll(em, Strand.class).get(0);
        }
        strandSupply.setStrand(strand);
        // Add required entity
        Study study;
        if (TestUtil.findAll(em, Study.class).isEmpty()) {
            study = StudyResourceIT.createEntity(em);
            em.persist(study);
            em.flush();
        } else {
            study = TestUtil.findAll(em, Study.class).get(0);
        }
        strandSupply.setStudy(study);
        return strandSupply;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StrandSupply createUpdatedEntity(EntityManager em) {
        StrandSupply strandSupply = new StrandSupply()
            .supplyState(UPDATED_SUPPLY_STATE)
            .apparitions(UPDATED_APPARITIONS)
            .markingType(UPDATED_MARKING_TYPE)
            .description(UPDATED_DESCRIPTION);
        // Add required entity
        Strand strand;
        if (TestUtil.findAll(em, Strand.class).isEmpty()) {
            strand = StrandResourceIT.createUpdatedEntity(em);
            em.persist(strand);
            em.flush();
        } else {
            strand = TestUtil.findAll(em, Strand.class).get(0);
        }
        strandSupply.setStrand(strand);
        // Add required entity
        Study study;
        if (TestUtil.findAll(em, Study.class).isEmpty()) {
            study = StudyResourceIT.createUpdatedEntity(em);
            em.persist(study);
            em.flush();
        } else {
            study = TestUtil.findAll(em, Study.class).get(0);
        }
        strandSupply.setStudy(study);
        return strandSupply;
    }

    @BeforeEach
    public void initTest() {
        strandSupply = createEntity(em);
    }

    @Test
    @Transactional
    void createStrandSupply() throws Exception {
        int databaseSizeBeforeCreate = strandSupplyRepository.findAll().size();
        // Create the StrandSupply
        restStrandSupplyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strandSupply)))
            .andExpect(status().isCreated());

        // Validate the StrandSupply in the database
        List<StrandSupply> strandSupplyList = strandSupplyRepository.findAll();
        assertThat(strandSupplyList).hasSize(databaseSizeBeforeCreate + 1);
        StrandSupply testStrandSupply = strandSupplyList.get(strandSupplyList.size() - 1);
        assertThat(testStrandSupply.getSupplyState()).isEqualTo(DEFAULT_SUPPLY_STATE);
        assertThat(testStrandSupply.getApparitions()).isEqualTo(DEFAULT_APPARITIONS);
        assertThat(testStrandSupply.getMarkingType()).isEqualTo(DEFAULT_MARKING_TYPE);
        assertThat(testStrandSupply.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createStrandSupplyWithExistingId() throws Exception {
        // Create the StrandSupply with an existing ID
        strandSupply.setId(1L);

        int databaseSizeBeforeCreate = strandSupplyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStrandSupplyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strandSupply)))
            .andExpect(status().isBadRequest());

        // Validate the StrandSupply in the database
        List<StrandSupply> strandSupplyList = strandSupplyRepository.findAll();
        assertThat(strandSupplyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSupplyStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = strandSupplyRepository.findAll().size();
        // set the field null
        strandSupply.setSupplyState(null);

        // Create the StrandSupply, which fails.

        restStrandSupplyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strandSupply)))
            .andExpect(status().isBadRequest());

        List<StrandSupply> strandSupplyList = strandSupplyRepository.findAll();
        assertThat(strandSupplyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkApparitionsIsRequired() throws Exception {
        int databaseSizeBeforeTest = strandSupplyRepository.findAll().size();
        // set the field null
        strandSupply.setApparitions(null);

        // Create the StrandSupply, which fails.

        restStrandSupplyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strandSupply)))
            .andExpect(status().isBadRequest());

        List<StrandSupply> strandSupplyList = strandSupplyRepository.findAll();
        assertThat(strandSupplyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMarkingTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = strandSupplyRepository.findAll().size();
        // set the field null
        strandSupply.setMarkingType(null);

        // Create the StrandSupply, which fails.

        restStrandSupplyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strandSupply)))
            .andExpect(status().isBadRequest());

        List<StrandSupply> strandSupplyList = strandSupplyRepository.findAll();
        assertThat(strandSupplyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStrandSupplies() throws Exception {
        // Initialize the database
        strandSupplyRepository.saveAndFlush(strandSupply);

        // Get all the strandSupplyList
        restStrandSupplyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(strandSupply.getId().intValue())))
            .andExpect(jsonPath("$.[*].supplyState").value(hasItem(DEFAULT_SUPPLY_STATE.toString())))
            .andExpect(jsonPath("$.[*].apparitions").value(hasItem(DEFAULT_APPARITIONS.intValue())))
            .andExpect(jsonPath("$.[*].markingType").value(hasItem(DEFAULT_MARKING_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getStrandSupply() throws Exception {
        // Initialize the database
        strandSupplyRepository.saveAndFlush(strandSupply);

        // Get the strandSupply
        restStrandSupplyMockMvc
            .perform(get(ENTITY_API_URL_ID, strandSupply.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(strandSupply.getId().intValue()))
            .andExpect(jsonPath("$.supplyState").value(DEFAULT_SUPPLY_STATE.toString()))
            .andExpect(jsonPath("$.apparitions").value(DEFAULT_APPARITIONS.intValue()))
            .andExpect(jsonPath("$.markingType").value(DEFAULT_MARKING_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingStrandSupply() throws Exception {
        // Get the strandSupply
        restStrandSupplyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStrandSupply() throws Exception {
        // Initialize the database
        strandSupplyRepository.saveAndFlush(strandSupply);

        int databaseSizeBeforeUpdate = strandSupplyRepository.findAll().size();

        // Update the strandSupply
        StrandSupply updatedStrandSupply = strandSupplyRepository.findById(strandSupply.getId()).get();
        // Disconnect from session so that the updates on updatedStrandSupply are not directly saved in db
        em.detach(updatedStrandSupply);
        updatedStrandSupply
            .supplyState(UPDATED_SUPPLY_STATE)
            .apparitions(UPDATED_APPARITIONS)
            .markingType(UPDATED_MARKING_TYPE)
            .description(UPDATED_DESCRIPTION);

        restStrandSupplyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStrandSupply.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedStrandSupply))
            )
            .andExpect(status().isOk());

        // Validate the StrandSupply in the database
        List<StrandSupply> strandSupplyList = strandSupplyRepository.findAll();
        assertThat(strandSupplyList).hasSize(databaseSizeBeforeUpdate);
        StrandSupply testStrandSupply = strandSupplyList.get(strandSupplyList.size() - 1);
        assertThat(testStrandSupply.getSupplyState()).isEqualTo(UPDATED_SUPPLY_STATE);
        assertThat(testStrandSupply.getApparitions()).isEqualTo(UPDATED_APPARITIONS);
        assertThat(testStrandSupply.getMarkingType()).isEqualTo(UPDATED_MARKING_TYPE);
        assertThat(testStrandSupply.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingStrandSupply() throws Exception {
        int databaseSizeBeforeUpdate = strandSupplyRepository.findAll().size();
        strandSupply.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStrandSupplyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, strandSupply.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(strandSupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the StrandSupply in the database
        List<StrandSupply> strandSupplyList = strandSupplyRepository.findAll();
        assertThat(strandSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStrandSupply() throws Exception {
        int databaseSizeBeforeUpdate = strandSupplyRepository.findAll().size();
        strandSupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrandSupplyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(strandSupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the StrandSupply in the database
        List<StrandSupply> strandSupplyList = strandSupplyRepository.findAll();
        assertThat(strandSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStrandSupply() throws Exception {
        int databaseSizeBeforeUpdate = strandSupplyRepository.findAll().size();
        strandSupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrandSupplyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strandSupply)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the StrandSupply in the database
        List<StrandSupply> strandSupplyList = strandSupplyRepository.findAll();
        assertThat(strandSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStrandSupplyWithPatch() throws Exception {
        // Initialize the database
        strandSupplyRepository.saveAndFlush(strandSupply);

        int databaseSizeBeforeUpdate = strandSupplyRepository.findAll().size();

        // Update the strandSupply using partial update
        StrandSupply partialUpdatedStrandSupply = new StrandSupply();
        partialUpdatedStrandSupply.setId(strandSupply.getId());

        partialUpdatedStrandSupply.markingType(UPDATED_MARKING_TYPE).description(UPDATED_DESCRIPTION);

        restStrandSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStrandSupply.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStrandSupply))
            )
            .andExpect(status().isOk());

        // Validate the StrandSupply in the database
        List<StrandSupply> strandSupplyList = strandSupplyRepository.findAll();
        assertThat(strandSupplyList).hasSize(databaseSizeBeforeUpdate);
        StrandSupply testStrandSupply = strandSupplyList.get(strandSupplyList.size() - 1);
        assertThat(testStrandSupply.getSupplyState()).isEqualTo(DEFAULT_SUPPLY_STATE);
        assertThat(testStrandSupply.getApparitions()).isEqualTo(DEFAULT_APPARITIONS);
        assertThat(testStrandSupply.getMarkingType()).isEqualTo(UPDATED_MARKING_TYPE);
        assertThat(testStrandSupply.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateStrandSupplyWithPatch() throws Exception {
        // Initialize the database
        strandSupplyRepository.saveAndFlush(strandSupply);

        int databaseSizeBeforeUpdate = strandSupplyRepository.findAll().size();

        // Update the strandSupply using partial update
        StrandSupply partialUpdatedStrandSupply = new StrandSupply();
        partialUpdatedStrandSupply.setId(strandSupply.getId());

        partialUpdatedStrandSupply
            .supplyState(UPDATED_SUPPLY_STATE)
            .apparitions(UPDATED_APPARITIONS)
            .markingType(UPDATED_MARKING_TYPE)
            .description(UPDATED_DESCRIPTION);

        restStrandSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStrandSupply.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStrandSupply))
            )
            .andExpect(status().isOk());

        // Validate the StrandSupply in the database
        List<StrandSupply> strandSupplyList = strandSupplyRepository.findAll();
        assertThat(strandSupplyList).hasSize(databaseSizeBeforeUpdate);
        StrandSupply testStrandSupply = strandSupplyList.get(strandSupplyList.size() - 1);
        assertThat(testStrandSupply.getSupplyState()).isEqualTo(UPDATED_SUPPLY_STATE);
        assertThat(testStrandSupply.getApparitions()).isEqualTo(UPDATED_APPARITIONS);
        assertThat(testStrandSupply.getMarkingType()).isEqualTo(UPDATED_MARKING_TYPE);
        assertThat(testStrandSupply.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingStrandSupply() throws Exception {
        int databaseSizeBeforeUpdate = strandSupplyRepository.findAll().size();
        strandSupply.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStrandSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, strandSupply.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(strandSupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the StrandSupply in the database
        List<StrandSupply> strandSupplyList = strandSupplyRepository.findAll();
        assertThat(strandSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStrandSupply() throws Exception {
        int databaseSizeBeforeUpdate = strandSupplyRepository.findAll().size();
        strandSupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrandSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(strandSupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the StrandSupply in the database
        List<StrandSupply> strandSupplyList = strandSupplyRepository.findAll();
        assertThat(strandSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStrandSupply() throws Exception {
        int databaseSizeBeforeUpdate = strandSupplyRepository.findAll().size();
        strandSupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrandSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(strandSupply))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StrandSupply in the database
        List<StrandSupply> strandSupplyList = strandSupplyRepository.findAll();
        assertThat(strandSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStrandSupply() throws Exception {
        // Initialize the database
        strandSupplyRepository.saveAndFlush(strandSupply);

        int databaseSizeBeforeDelete = strandSupplyRepository.findAll().size();

        // Delete the strandSupply
        restStrandSupplyMockMvc
            .perform(delete(ENTITY_API_URL_ID, strandSupply.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StrandSupply> strandSupplyList = strandSupplyRepository.findAll();
        assertThat(strandSupplyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
