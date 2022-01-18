package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.CoreAssembly;
import com.muller.lappli.domain.Strand;
import com.muller.lappli.domain.enumeration.AssemblyMean;
import com.muller.lappli.repository.CoreAssemblyRepository;
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
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CoreAssemblyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CoreAssemblyResourceIT {

    private static final Long DEFAULT_ASSEMBLY_LAYER = 1L;
    private static final Long UPDATED_ASSEMBLY_LAYER = 2L;

    private static final Double DEFAULT_DIAMETER_ASSEMBLY_STEP = 1D;
    private static final Double UPDATED_DIAMETER_ASSEMBLY_STEP = 2D;

    private static final AssemblyMean DEFAULT_ASSEMBLY_MEAN = AssemblyMean.RIGHT;
    private static final AssemblyMean UPDATED_ASSEMBLY_MEAN = AssemblyMean.LEFT;

    private static final String ENTITY_API_URL = "/api/core-assemblies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CoreAssemblyRepository coreAssemblyRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCoreAssemblyMockMvc;

    private CoreAssembly coreAssembly;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CoreAssembly createEntity(EntityManager em) {
        CoreAssembly coreAssembly = new CoreAssembly()
            .assemblyLayer(DEFAULT_ASSEMBLY_LAYER)
            .diameterAssemblyStep(DEFAULT_DIAMETER_ASSEMBLY_STEP)
            .assemblyMean(DEFAULT_ASSEMBLY_MEAN);
        // Add required entity
        Strand strand;
        if (TestUtil.findAll(em, Strand.class).isEmpty()) {
            strand = StrandResourceIT.createEntity(em);
            em.persist(strand);
            em.flush();
        } else {
            strand = TestUtil.findAll(em, Strand.class).get(0);
        }
        coreAssembly.setOwnerStrand(strand);
        return coreAssembly;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CoreAssembly createUpdatedEntity(EntityManager em) {
        CoreAssembly coreAssembly = new CoreAssembly()
            .assemblyLayer(UPDATED_ASSEMBLY_LAYER)
            .diameterAssemblyStep(UPDATED_DIAMETER_ASSEMBLY_STEP)
            .assemblyMean(UPDATED_ASSEMBLY_MEAN);
        // Add required entity
        Strand strand;
        if (TestUtil.findAll(em, Strand.class).isEmpty()) {
            strand = StrandResourceIT.createUpdatedEntity(em);
            em.persist(strand);
            em.flush();
        } else {
            strand = TestUtil.findAll(em, Strand.class).get(0);
        }
        coreAssembly.setOwnerStrand(strand);
        return coreAssembly;
    }

    @BeforeEach
    public void initTest() {
        coreAssembly = createEntity(em);
    }

    @Test
    @Transactional
    void createCoreAssembly() throws Exception {
        int databaseSizeBeforeCreate = coreAssemblyRepository.findAll().size();
        // Create the CoreAssembly
        restCoreAssemblyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(coreAssembly)))
            .andExpect(status().isCreated());

        // Validate the CoreAssembly in the database
        List<CoreAssembly> coreAssemblyList = coreAssemblyRepository.findAll();
        assertThat(coreAssemblyList).hasSize(databaseSizeBeforeCreate + 1);
        CoreAssembly testCoreAssembly = coreAssemblyList.get(coreAssemblyList.size() - 1);
        assertThat(testCoreAssembly.getAssemblyLayer()).isEqualTo(DEFAULT_ASSEMBLY_LAYER);
        assertThat(testCoreAssembly.getDiameterAssemblyStep()).isEqualTo(DEFAULT_DIAMETER_ASSEMBLY_STEP);
        assertThat(testCoreAssembly.getAssemblyMean()).isEqualTo(DEFAULT_ASSEMBLY_MEAN);
    }

    @Test
    @Transactional
    void createCoreAssemblyWithExistingId() throws Exception {
        // Create the CoreAssembly with an existing ID
        coreAssembly.setId(1L);

        int databaseSizeBeforeCreate = coreAssemblyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoreAssemblyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(coreAssembly)))
            .andExpect(status().isBadRequest());

        // Validate the CoreAssembly in the database
        List<CoreAssembly> coreAssemblyList = coreAssemblyRepository.findAll();
        assertThat(coreAssemblyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAssemblyLayerIsRequired() throws Exception {
        int databaseSizeBeforeTest = coreAssemblyRepository.findAll().size();
        // set the field null
        coreAssembly.setAssemblyLayer(null);

        // Create the CoreAssembly, which fails.

        restCoreAssemblyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(coreAssembly)))
            .andExpect(status().isBadRequest());

        List<CoreAssembly> coreAssemblyList = coreAssemblyRepository.findAll();
        assertThat(coreAssemblyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDiameterAssemblyStepIsRequired() throws Exception {
        int databaseSizeBeforeTest = coreAssemblyRepository.findAll().size();
        // set the field null
        coreAssembly.setDiameterAssemblyStep(null);

        // Create the CoreAssembly, which fails.

        restCoreAssemblyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(coreAssembly)))
            .andExpect(status().isBadRequest());

        List<CoreAssembly> coreAssemblyList = coreAssemblyRepository.findAll();
        assertThat(coreAssemblyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAssemblyMeanIsRequired() throws Exception {
        int databaseSizeBeforeTest = coreAssemblyRepository.findAll().size();
        // set the field null
        coreAssembly.setAssemblyMean(null);

        // Create the CoreAssembly, which fails.

        restCoreAssemblyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(coreAssembly)))
            .andExpect(status().isBadRequest());

        List<CoreAssembly> coreAssemblyList = coreAssemblyRepository.findAll();
        assertThat(coreAssemblyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCoreAssemblies() throws Exception {
        // Initialize the database
        coreAssemblyRepository.saveAndFlush(coreAssembly);

        // Get all the coreAssemblyList
        restCoreAssemblyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coreAssembly.getId().intValue())))
            .andExpect(jsonPath("$.[*].assemblyLayer").value(hasItem(DEFAULT_ASSEMBLY_LAYER.intValue())))
            .andExpect(jsonPath("$.[*].diameterAssemblyStep").value(hasItem(DEFAULT_DIAMETER_ASSEMBLY_STEP.doubleValue())))
            .andExpect(jsonPath("$.[*].assemblyMean").value(hasItem(DEFAULT_ASSEMBLY_MEAN.toString())));
    }

    @Test
    @Transactional
    void getCoreAssembly() throws Exception {
        // Initialize the database
        coreAssemblyRepository.saveAndFlush(coreAssembly);

        // Get the coreAssembly
        restCoreAssemblyMockMvc
            .perform(get(ENTITY_API_URL_ID, coreAssembly.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(coreAssembly.getId().intValue()))
            .andExpect(jsonPath("$.assemblyLayer").value(DEFAULT_ASSEMBLY_LAYER.intValue()))
            .andExpect(jsonPath("$.diameterAssemblyStep").value(DEFAULT_DIAMETER_ASSEMBLY_STEP.doubleValue()))
            .andExpect(jsonPath("$.assemblyMean").value(DEFAULT_ASSEMBLY_MEAN.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCoreAssembly() throws Exception {
        // Get the coreAssembly
        restCoreAssemblyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCoreAssembly() throws Exception {
        // Initialize the database
        coreAssemblyRepository.saveAndFlush(coreAssembly);

        int databaseSizeBeforeUpdate = coreAssemblyRepository.findAll().size();

        // Update the coreAssembly
        CoreAssembly updatedCoreAssembly = coreAssemblyRepository.findById(coreAssembly.getId()).get();
        // Disconnect from session so that the updates on updatedCoreAssembly are not directly saved in db
        em.detach(updatedCoreAssembly);
        updatedCoreAssembly
            .assemblyLayer(UPDATED_ASSEMBLY_LAYER)
            .diameterAssemblyStep(UPDATED_DIAMETER_ASSEMBLY_STEP)
            .assemblyMean(UPDATED_ASSEMBLY_MEAN);

        restCoreAssemblyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCoreAssembly.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCoreAssembly))
            )
            .andExpect(status().isOk());

        // Validate the CoreAssembly in the database
        List<CoreAssembly> coreAssemblyList = coreAssemblyRepository.findAll();
        assertThat(coreAssemblyList).hasSize(databaseSizeBeforeUpdate);
        CoreAssembly testCoreAssembly = coreAssemblyList.get(coreAssemblyList.size() - 1);
        assertThat(testCoreAssembly.getAssemblyLayer()).isEqualTo(UPDATED_ASSEMBLY_LAYER);
        assertThat(testCoreAssembly.getDiameterAssemblyStep()).isEqualTo(UPDATED_DIAMETER_ASSEMBLY_STEP);
        assertThat(testCoreAssembly.getAssemblyMean()).isEqualTo(UPDATED_ASSEMBLY_MEAN);
    }

    @Test
    @Transactional
    void putNonExistingCoreAssembly() throws Exception {
        int databaseSizeBeforeUpdate = coreAssemblyRepository.findAll().size();
        coreAssembly.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCoreAssemblyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, coreAssembly.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(coreAssembly))
            )
            .andExpect(status().isBadRequest());

        // Validate the CoreAssembly in the database
        List<CoreAssembly> coreAssemblyList = coreAssemblyRepository.findAll();
        assertThat(coreAssemblyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCoreAssembly() throws Exception {
        int databaseSizeBeforeUpdate = coreAssemblyRepository.findAll().size();
        coreAssembly.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCoreAssemblyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(coreAssembly))
            )
            .andExpect(status().isBadRequest());

        // Validate the CoreAssembly in the database
        List<CoreAssembly> coreAssemblyList = coreAssemblyRepository.findAll();
        assertThat(coreAssemblyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCoreAssembly() throws Exception {
        int databaseSizeBeforeUpdate = coreAssemblyRepository.findAll().size();
        coreAssembly.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCoreAssemblyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(coreAssembly)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CoreAssembly in the database
        List<CoreAssembly> coreAssemblyList = coreAssemblyRepository.findAll();
        assertThat(coreAssemblyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCoreAssemblyWithPatch() throws Exception {
        // Initialize the database
        coreAssemblyRepository.saveAndFlush(coreAssembly);

        int databaseSizeBeforeUpdate = coreAssemblyRepository.findAll().size();

        // Update the coreAssembly using partial update
        CoreAssembly partialUpdatedCoreAssembly = new CoreAssembly();
        partialUpdatedCoreAssembly.setId(coreAssembly.getId());

        restCoreAssemblyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCoreAssembly.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCoreAssembly))
            )
            .andExpect(status().isOk());

        // Validate the CoreAssembly in the database
        List<CoreAssembly> coreAssemblyList = coreAssemblyRepository.findAll();
        assertThat(coreAssemblyList).hasSize(databaseSizeBeforeUpdate);
        CoreAssembly testCoreAssembly = coreAssemblyList.get(coreAssemblyList.size() - 1);
        assertThat(testCoreAssembly.getAssemblyLayer()).isEqualTo(DEFAULT_ASSEMBLY_LAYER);
        assertThat(testCoreAssembly.getDiameterAssemblyStep()).isEqualTo(DEFAULT_DIAMETER_ASSEMBLY_STEP);
        assertThat(testCoreAssembly.getAssemblyMean()).isEqualTo(DEFAULT_ASSEMBLY_MEAN);
    }

    @Test
    @Transactional
    void fullUpdateCoreAssemblyWithPatch() throws Exception {
        // Initialize the database
        coreAssemblyRepository.saveAndFlush(coreAssembly);

        int databaseSizeBeforeUpdate = coreAssemblyRepository.findAll().size();

        // Update the coreAssembly using partial update
        CoreAssembly partialUpdatedCoreAssembly = new CoreAssembly();
        partialUpdatedCoreAssembly.setId(coreAssembly.getId());

        partialUpdatedCoreAssembly
            .assemblyLayer(UPDATED_ASSEMBLY_LAYER)
            .diameterAssemblyStep(UPDATED_DIAMETER_ASSEMBLY_STEP)
            .assemblyMean(UPDATED_ASSEMBLY_MEAN);

        restCoreAssemblyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCoreAssembly.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCoreAssembly))
            )
            .andExpect(status().isOk());

        // Validate the CoreAssembly in the database
        List<CoreAssembly> coreAssemblyList = coreAssemblyRepository.findAll();
        assertThat(coreAssemblyList).hasSize(databaseSizeBeforeUpdate);
        CoreAssembly testCoreAssembly = coreAssemblyList.get(coreAssemblyList.size() - 1);
        assertThat(testCoreAssembly.getAssemblyLayer()).isEqualTo(UPDATED_ASSEMBLY_LAYER);
        assertThat(testCoreAssembly.getDiameterAssemblyStep()).isEqualTo(UPDATED_DIAMETER_ASSEMBLY_STEP);
        assertThat(testCoreAssembly.getAssemblyMean()).isEqualTo(UPDATED_ASSEMBLY_MEAN);
    }

    @Test
    @Transactional
    void patchNonExistingCoreAssembly() throws Exception {
        int databaseSizeBeforeUpdate = coreAssemblyRepository.findAll().size();
        coreAssembly.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCoreAssemblyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, coreAssembly.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(coreAssembly))
            )
            .andExpect(status().isBadRequest());

        // Validate the CoreAssembly in the database
        List<CoreAssembly> coreAssemblyList = coreAssemblyRepository.findAll();
        assertThat(coreAssemblyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCoreAssembly() throws Exception {
        int databaseSizeBeforeUpdate = coreAssemblyRepository.findAll().size();
        coreAssembly.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCoreAssemblyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(coreAssembly))
            )
            .andExpect(status().isBadRequest());

        // Validate the CoreAssembly in the database
        List<CoreAssembly> coreAssemblyList = coreAssemblyRepository.findAll();
        assertThat(coreAssemblyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCoreAssembly() throws Exception {
        int databaseSizeBeforeUpdate = coreAssemblyRepository.findAll().size();
        coreAssembly.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCoreAssemblyMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(coreAssembly))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CoreAssembly in the database
        List<CoreAssembly> coreAssemblyList = coreAssemblyRepository.findAll();
        assertThat(coreAssemblyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCoreAssembly() throws Exception {
        // Initialize the database
        coreAssemblyRepository.saveAndFlush(coreAssembly);

        int databaseSizeBeforeDelete = coreAssemblyRepository.findAll().size();

        // Delete the coreAssembly
        restCoreAssemblyMockMvc
            .perform(delete(ENTITY_API_URL_ID, coreAssembly.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CoreAssembly> coreAssemblyList = coreAssemblyRepository.findAll();
        assertThat(coreAssemblyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
