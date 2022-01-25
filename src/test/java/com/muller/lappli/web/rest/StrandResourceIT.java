package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.Strand;
import com.muller.lappli.domain.Study;
import com.muller.lappli.domain.enumeration.AssemblyMean;
import com.muller.lappli.repository.StrandRepository;
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
 * Integration tests for the {@link StrandResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StrandResourceIT {

    private static final Double DEFAULT_DIAMETER_ASSEMBLY_STEP = 1D;
    private static final Double UPDATED_DIAMETER_ASSEMBLY_STEP = 2D;

    private static final AssemblyMean DEFAULT_ASSEMBLY_MEAN = AssemblyMean.RIGHT;
    private static final AssemblyMean UPDATED_ASSEMBLY_MEAN = AssemblyMean.LEFT;

    private static final String ENTITY_API_URL = "/api/strands";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StrandRepository strandRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStrandMockMvc;

    private Strand strand;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Strand createEntity(EntityManager em) {
        Strand strand = new Strand().diameterAssemblyStep(DEFAULT_DIAMETER_ASSEMBLY_STEP).assemblyMean(DEFAULT_ASSEMBLY_MEAN);
        // Add required entity
        Study study;
        if (TestUtil.findAll(em, Study.class).isEmpty()) {
            study = StudyResourceIT.createEntity(em);
            em.persist(study);
            em.flush();
        } else {
            study = TestUtil.findAll(em, Study.class).get(0);
        }
        strand.setFutureStudy(study);
        return strand;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Strand createUpdatedEntity(EntityManager em) {
        Strand strand = new Strand().diameterAssemblyStep(UPDATED_DIAMETER_ASSEMBLY_STEP).assemblyMean(UPDATED_ASSEMBLY_MEAN);
        // Add required entity
        Study study;
        if (TestUtil.findAll(em, Study.class).isEmpty()) {
            study = StudyResourceIT.createUpdatedEntity(em);
            em.persist(study);
            em.flush();
        } else {
            study = TestUtil.findAll(em, Study.class).get(0);
        }
        strand.setFutureStudy(study);
        return strand;
    }

    @BeforeEach
    public void initTest() {
        strand = createEntity(em);
    }

    @Test
    @Transactional
    void createStrand() throws Exception {
        int databaseSizeBeforeCreate = strandRepository.findAll().size();
        // Create the Strand
        restStrandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strand)))
            .andExpect(status().isCreated());

        // Validate the Strand in the database
        List<Strand> strandList = strandRepository.findAll();
        assertThat(strandList).hasSize(databaseSizeBeforeCreate + 1);
        Strand testStrand = strandList.get(strandList.size() - 1);
        assertThat(testStrand.getDiameterAssemblyStep()).isEqualTo(DEFAULT_DIAMETER_ASSEMBLY_STEP);
        assertThat(testStrand.getAssemblyMean()).isEqualTo(DEFAULT_ASSEMBLY_MEAN);
    }

    @Test
    @Transactional
    void createStrandWithExistingId() throws Exception {
        // Create the Strand with an existing ID
        strand.setId(1L);

        int databaseSizeBeforeCreate = strandRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStrandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strand)))
            .andExpect(status().isBadRequest());

        // Validate the Strand in the database
        List<Strand> strandList = strandRepository.findAll();
        assertThat(strandList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDiameterAssemblyStepIsRequired() throws Exception {
        int databaseSizeBeforeTest = strandRepository.findAll().size();
        // set the field null
        strand.setDiameterAssemblyStep(null);

        // Create the Strand, which fails.

        restStrandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strand)))
            .andExpect(status().isBadRequest());

        List<Strand> strandList = strandRepository.findAll();
        assertThat(strandList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAssemblyMeanIsRequired() throws Exception {
        int databaseSizeBeforeTest = strandRepository.findAll().size();
        // set the field null
        strand.setAssemblyMean(null);

        // Create the Strand, which fails.

        restStrandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strand)))
            .andExpect(status().isBadRequest());

        List<Strand> strandList = strandRepository.findAll();
        assertThat(strandList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStrands() throws Exception {
        // Initialize the database
        strandRepository.saveAndFlush(strand);

        // Get all the strandList
        restStrandMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(strand.getId().intValue())))
            .andExpect(jsonPath("$.[*].diameterAssemblyStep").value(hasItem(DEFAULT_DIAMETER_ASSEMBLY_STEP.doubleValue())))
            .andExpect(jsonPath("$.[*].assemblyMean").value(hasItem(DEFAULT_ASSEMBLY_MEAN.toString())));
    }

    @Test
    @Transactional
    void getStrand() throws Exception {
        // Initialize the database
        strandRepository.saveAndFlush(strand);

        // Get the strand
        restStrandMockMvc
            .perform(get(ENTITY_API_URL_ID, strand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(strand.getId().intValue()))
            .andExpect(jsonPath("$.diameterAssemblyStep").value(DEFAULT_DIAMETER_ASSEMBLY_STEP.doubleValue()))
            .andExpect(jsonPath("$.assemblyMean").value(DEFAULT_ASSEMBLY_MEAN.toString()));
    }

    @Test
    @Transactional
    void getNonExistingStrand() throws Exception {
        // Get the strand
        restStrandMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStrand() throws Exception {
        // Initialize the database
        strandRepository.saveAndFlush(strand);

        int databaseSizeBeforeUpdate = strandRepository.findAll().size();

        // Update the strand
        Strand updatedStrand = strandRepository.findById(strand.getId()).get();
        // Disconnect from session so that the updates on updatedStrand are not directly saved in db
        em.detach(updatedStrand);
        updatedStrand.diameterAssemblyStep(UPDATED_DIAMETER_ASSEMBLY_STEP).assemblyMean(UPDATED_ASSEMBLY_MEAN);

        restStrandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStrand.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedStrand))
            )
            .andExpect(status().isOk());

        // Validate the Strand in the database
        List<Strand> strandList = strandRepository.findAll();
        assertThat(strandList).hasSize(databaseSizeBeforeUpdate);
        Strand testStrand = strandList.get(strandList.size() - 1);
        assertThat(testStrand.getDiameterAssemblyStep()).isEqualTo(UPDATED_DIAMETER_ASSEMBLY_STEP);
        assertThat(testStrand.getAssemblyMean()).isEqualTo(UPDATED_ASSEMBLY_MEAN);
    }

    @Test
    @Transactional
    void putNonExistingStrand() throws Exception {
        int databaseSizeBeforeUpdate = strandRepository.findAll().size();
        strand.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStrandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, strand.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(strand))
            )
            .andExpect(status().isBadRequest());

        // Validate the Strand in the database
        List<Strand> strandList = strandRepository.findAll();
        assertThat(strandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStrand() throws Exception {
        int databaseSizeBeforeUpdate = strandRepository.findAll().size();
        strand.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(strand))
            )
            .andExpect(status().isBadRequest());

        // Validate the Strand in the database
        List<Strand> strandList = strandRepository.findAll();
        assertThat(strandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStrand() throws Exception {
        int databaseSizeBeforeUpdate = strandRepository.findAll().size();
        strand.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrandMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strand)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Strand in the database
        List<Strand> strandList = strandRepository.findAll();
        assertThat(strandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStrandWithPatch() throws Exception {
        // Initialize the database
        strandRepository.saveAndFlush(strand);

        int databaseSizeBeforeUpdate = strandRepository.findAll().size();

        // Update the strand using partial update
        Strand partialUpdatedStrand = new Strand();
        partialUpdatedStrand.setId(strand.getId());

        partialUpdatedStrand.diameterAssemblyStep(UPDATED_DIAMETER_ASSEMBLY_STEP);

        restStrandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStrand.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStrand))
            )
            .andExpect(status().isOk());

        // Validate the Strand in the database
        List<Strand> strandList = strandRepository.findAll();
        assertThat(strandList).hasSize(databaseSizeBeforeUpdate);
        Strand testStrand = strandList.get(strandList.size() - 1);
        assertThat(testStrand.getDiameterAssemblyStep()).isEqualTo(UPDATED_DIAMETER_ASSEMBLY_STEP);
        assertThat(testStrand.getAssemblyMean()).isEqualTo(DEFAULT_ASSEMBLY_MEAN);
    }

    @Test
    @Transactional
    void fullUpdateStrandWithPatch() throws Exception {
        // Initialize the database
        strandRepository.saveAndFlush(strand);

        int databaseSizeBeforeUpdate = strandRepository.findAll().size();

        // Update the strand using partial update
        Strand partialUpdatedStrand = new Strand();
        partialUpdatedStrand.setId(strand.getId());

        partialUpdatedStrand.diameterAssemblyStep(UPDATED_DIAMETER_ASSEMBLY_STEP).assemblyMean(UPDATED_ASSEMBLY_MEAN);

        restStrandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStrand.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStrand))
            )
            .andExpect(status().isOk());

        // Validate the Strand in the database
        List<Strand> strandList = strandRepository.findAll();
        assertThat(strandList).hasSize(databaseSizeBeforeUpdate);
        Strand testStrand = strandList.get(strandList.size() - 1);
        assertThat(testStrand.getDiameterAssemblyStep()).isEqualTo(UPDATED_DIAMETER_ASSEMBLY_STEP);
        assertThat(testStrand.getAssemblyMean()).isEqualTo(UPDATED_ASSEMBLY_MEAN);
    }

    @Test
    @Transactional
    void patchNonExistingStrand() throws Exception {
        int databaseSizeBeforeUpdate = strandRepository.findAll().size();
        strand.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStrandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, strand.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(strand))
            )
            .andExpect(status().isBadRequest());

        // Validate the Strand in the database
        List<Strand> strandList = strandRepository.findAll();
        assertThat(strandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStrand() throws Exception {
        int databaseSizeBeforeUpdate = strandRepository.findAll().size();
        strand.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(strand))
            )
            .andExpect(status().isBadRequest());

        // Validate the Strand in the database
        List<Strand> strandList = strandRepository.findAll();
        assertThat(strandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStrand() throws Exception {
        int databaseSizeBeforeUpdate = strandRepository.findAll().size();
        strand.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrandMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(strand)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Strand in the database
        List<Strand> strandList = strandRepository.findAll();
        assertThat(strandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStrand() throws Exception {
        // Initialize the database
        strandRepository.saveAndFlush(strand);

        int databaseSizeBeforeDelete = strandRepository.findAll().size();

        // Delete the strand
        restStrandMockMvc
            .perform(delete(ENTITY_API_URL_ID, strand.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Strand> strandList = strandRepository.findAll();
        assertThat(strandList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
