package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.CoreAssembly;
import com.muller.lappli.domain.StrandSupply;
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
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CoreAssemblyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CoreAssemblyResourceIT {

    private static final Long DEFAULT_OPERATION_LAYER = 1L;
    private static final Long UPDATED_OPERATION_LAYER = 2L;

    private static final Double DEFAULT_FORCED_MEAN_MILIMETER_COMPONENT_DIAMETER = 1D;
    private static final Double UPDATED_FORCED_MEAN_MILIMETER_COMPONENT_DIAMETER = 2D;

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
            .operationLayer(DEFAULT_OPERATION_LAYER)
            .forcedMeanMilimeterComponentDiameter(DEFAULT_FORCED_MEAN_MILIMETER_COMPONENT_DIAMETER);
        // Add required entity
        StrandSupply strandSupply;
        if (TestUtil.findAll(em, StrandSupply.class).isEmpty()) {
            strandSupply = StrandSupplyResourceIT.createEntity(em);
            em.persist(strandSupply);
            em.flush();
        } else {
            strandSupply = TestUtil.findAll(em, StrandSupply.class).get(0);
        }
        coreAssembly.setOwnerStrandSupply(strandSupply);
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
            .operationLayer(UPDATED_OPERATION_LAYER)
            .forcedMeanMilimeterComponentDiameter(UPDATED_FORCED_MEAN_MILIMETER_COMPONENT_DIAMETER);
        // Add required entity
        StrandSupply strandSupply;
        if (TestUtil.findAll(em, StrandSupply.class).isEmpty()) {
            strandSupply = StrandSupplyResourceIT.createUpdatedEntity(em);
            em.persist(strandSupply);
            em.flush();
        } else {
            strandSupply = TestUtil.findAll(em, StrandSupply.class).get(0);
        }
        coreAssembly.setOwnerStrandSupply(strandSupply);
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
        assertThat(testCoreAssembly.getOperationLayer()).isEqualTo(DEFAULT_OPERATION_LAYER);
        assertThat(testCoreAssembly.getForcedMeanMilimeterComponentDiameter()).isEqualTo(DEFAULT_FORCED_MEAN_MILIMETER_COMPONENT_DIAMETER);
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
    void checkOperationLayerIsRequired() throws Exception {
        int databaseSizeBeforeTest = coreAssemblyRepository.findAll().size();
        // set the field null
        coreAssembly.setOperationLayer(null);

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
            .andExpect(jsonPath("$.[*].operationLayer").value(hasItem(DEFAULT_OPERATION_LAYER.intValue())))
            .andExpect(
                jsonPath("$.[*].forcedMeanMilimeterComponentDiameter")
                    .value(hasItem(DEFAULT_FORCED_MEAN_MILIMETER_COMPONENT_DIAMETER.doubleValue()))
            );
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
            .andExpect(jsonPath("$.operationLayer").value(DEFAULT_OPERATION_LAYER.intValue()))
            .andExpect(
                jsonPath("$.forcedMeanMilimeterComponentDiameter").value(DEFAULT_FORCED_MEAN_MILIMETER_COMPONENT_DIAMETER.doubleValue())
            );
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
            .operationLayer(UPDATED_OPERATION_LAYER)
            .forcedMeanMilimeterComponentDiameter(UPDATED_FORCED_MEAN_MILIMETER_COMPONENT_DIAMETER);

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
        assertThat(testCoreAssembly.getOperationLayer()).isEqualTo(UPDATED_OPERATION_LAYER);
        assertThat(testCoreAssembly.getForcedMeanMilimeterComponentDiameter()).isEqualTo(UPDATED_FORCED_MEAN_MILIMETER_COMPONENT_DIAMETER);
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
        assertThat(testCoreAssembly.getOperationLayer()).isEqualTo(DEFAULT_OPERATION_LAYER);
        assertThat(testCoreAssembly.getForcedMeanMilimeterComponentDiameter()).isEqualTo(DEFAULT_FORCED_MEAN_MILIMETER_COMPONENT_DIAMETER);
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
            .operationLayer(UPDATED_OPERATION_LAYER)
            .forcedMeanMilimeterComponentDiameter(UPDATED_FORCED_MEAN_MILIMETER_COMPONENT_DIAMETER);

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
        assertThat(testCoreAssembly.getOperationLayer()).isEqualTo(UPDATED_OPERATION_LAYER);
        assertThat(testCoreAssembly.getForcedMeanMilimeterComponentDiameter()).isEqualTo(UPDATED_FORCED_MEAN_MILIMETER_COMPONENT_DIAMETER);
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
