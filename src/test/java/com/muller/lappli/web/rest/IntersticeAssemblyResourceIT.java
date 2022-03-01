package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.IntersticeAssembly;
import com.muller.lappli.domain.StrandSupply;
import com.muller.lappli.repository.IntersticeAssemblyRepository;
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
 * Integration tests for the {@link IntersticeAssemblyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IntersticeAssemblyResourceIT {

    private static final Long DEFAULT_ASSEMBLY_LAYER = 1L;
    private static final Long UPDATED_ASSEMBLY_LAYER = 2L;

    private static final Long DEFAULT_INTERSTICE_LAYER = 1L;
    private static final Long UPDATED_INTERSTICE_LAYER = 2L;

    private static final Double DEFAULT_FORCED_MEAN_MILIMETER_COMPONENT_DIAMETER = 1D;
    private static final Double UPDATED_FORCED_MEAN_MILIMETER_COMPONENT_DIAMETER = 2D;

    private static final String ENTITY_API_URL = "/api/interstice-assemblies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IntersticeAssemblyRepository intersticeAssemblyRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIntersticeAssemblyMockMvc;

    private IntersticeAssembly intersticeAssembly;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IntersticeAssembly createEntity(EntityManager em) {
        IntersticeAssembly intersticeAssembly = new IntersticeAssembly()
            .assemblyLayer(DEFAULT_ASSEMBLY_LAYER)
            .intersticeLayer(DEFAULT_INTERSTICE_LAYER)
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
        intersticeAssembly.setOwnerStrandSupply(strandSupply);
        return intersticeAssembly;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IntersticeAssembly createUpdatedEntity(EntityManager em) {
        IntersticeAssembly intersticeAssembly = new IntersticeAssembly()
            .assemblyLayer(UPDATED_ASSEMBLY_LAYER)
            .intersticeLayer(UPDATED_INTERSTICE_LAYER)
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
        intersticeAssembly.setOwnerStrandSupply(strandSupply);
        return intersticeAssembly;
    }

    @BeforeEach
    public void initTest() {
        intersticeAssembly = createEntity(em);
    }

    @Test
    @Transactional
    void createIntersticeAssembly() throws Exception {
        int databaseSizeBeforeCreate = intersticeAssemblyRepository.findAll().size();
        // Create the IntersticeAssembly
        restIntersticeAssemblyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(intersticeAssembly))
            )
            .andExpect(status().isCreated());

        // Validate the IntersticeAssembly in the database
        List<IntersticeAssembly> intersticeAssemblyList = intersticeAssemblyRepository.findAll();
        assertThat(intersticeAssemblyList).hasSize(databaseSizeBeforeCreate + 1);
        IntersticeAssembly testIntersticeAssembly = intersticeAssemblyList.get(intersticeAssemblyList.size() - 1);
        assertThat(testIntersticeAssembly.getAssemblyLayer()).isEqualTo(DEFAULT_ASSEMBLY_LAYER);
        assertThat(testIntersticeAssembly.getIntersticeLayer()).isEqualTo(DEFAULT_INTERSTICE_LAYER);
        assertThat(testIntersticeAssembly.getForcedMeanMilimeterComponentDiameter())
            .isEqualTo(DEFAULT_FORCED_MEAN_MILIMETER_COMPONENT_DIAMETER);
    }

    @Test
    @Transactional
    void createIntersticeAssemblyWithExistingId() throws Exception {
        // Create the IntersticeAssembly with an existing ID
        intersticeAssembly.setId(1L);

        int databaseSizeBeforeCreate = intersticeAssemblyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIntersticeAssemblyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(intersticeAssembly))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntersticeAssembly in the database
        List<IntersticeAssembly> intersticeAssemblyList = intersticeAssemblyRepository.findAll();
        assertThat(intersticeAssemblyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAssemblyLayerIsRequired() throws Exception {
        int databaseSizeBeforeTest = intersticeAssemblyRepository.findAll().size();
        // set the field null
        intersticeAssembly.setAssemblyLayer(null);

        // Create the IntersticeAssembly, which fails.

        restIntersticeAssemblyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(intersticeAssembly))
            )
            .andExpect(status().isBadRequest());

        List<IntersticeAssembly> intersticeAssemblyList = intersticeAssemblyRepository.findAll();
        assertThat(intersticeAssemblyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIntersticeLayerIsRequired() throws Exception {
        int databaseSizeBeforeTest = intersticeAssemblyRepository.findAll().size();
        // set the field null
        intersticeAssembly.setIntersticeLayer(null);

        // Create the IntersticeAssembly, which fails.

        restIntersticeAssemblyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(intersticeAssembly))
            )
            .andExpect(status().isBadRequest());

        List<IntersticeAssembly> intersticeAssemblyList = intersticeAssemblyRepository.findAll();
        assertThat(intersticeAssemblyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllIntersticeAssemblies() throws Exception {
        // Initialize the database
        intersticeAssemblyRepository.saveAndFlush(intersticeAssembly);

        // Get all the intersticeAssemblyList
        restIntersticeAssemblyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(intersticeAssembly.getId().intValue())))
            .andExpect(jsonPath("$.[*].assemblyLayer").value(hasItem(DEFAULT_ASSEMBLY_LAYER.intValue())))
            .andExpect(jsonPath("$.[*].intersticeLayer").value(hasItem(DEFAULT_INTERSTICE_LAYER.intValue())))
            .andExpect(
                jsonPath("$.[*].forcedMeanMilimeterComponentDiameter")
                    .value(hasItem(DEFAULT_FORCED_MEAN_MILIMETER_COMPONENT_DIAMETER.doubleValue()))
            );
    }

    @Test
    @Transactional
    void getIntersticeAssembly() throws Exception {
        // Initialize the database
        intersticeAssemblyRepository.saveAndFlush(intersticeAssembly);

        // Get the intersticeAssembly
        restIntersticeAssemblyMockMvc
            .perform(get(ENTITY_API_URL_ID, intersticeAssembly.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(intersticeAssembly.getId().intValue()))
            .andExpect(jsonPath("$.assemblyLayer").value(DEFAULT_ASSEMBLY_LAYER.intValue()))
            .andExpect(jsonPath("$.intersticeLayer").value(DEFAULT_INTERSTICE_LAYER.intValue()))
            .andExpect(
                jsonPath("$.forcedMeanMilimeterComponentDiameter").value(DEFAULT_FORCED_MEAN_MILIMETER_COMPONENT_DIAMETER.doubleValue())
            );
    }

    @Test
    @Transactional
    void getNonExistingIntersticeAssembly() throws Exception {
        // Get the intersticeAssembly
        restIntersticeAssemblyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewIntersticeAssembly() throws Exception {
        // Initialize the database
        intersticeAssemblyRepository.saveAndFlush(intersticeAssembly);

        int databaseSizeBeforeUpdate = intersticeAssemblyRepository.findAll().size();

        // Update the intersticeAssembly
        IntersticeAssembly updatedIntersticeAssembly = intersticeAssemblyRepository.findById(intersticeAssembly.getId()).get();
        // Disconnect from session so that the updates on updatedIntersticeAssembly are not directly saved in db
        em.detach(updatedIntersticeAssembly);
        updatedIntersticeAssembly
            .assemblyLayer(UPDATED_ASSEMBLY_LAYER)
            .intersticeLayer(UPDATED_INTERSTICE_LAYER)
            .forcedMeanMilimeterComponentDiameter(UPDATED_FORCED_MEAN_MILIMETER_COMPONENT_DIAMETER);

        restIntersticeAssemblyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIntersticeAssembly.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedIntersticeAssembly))
            )
            .andExpect(status().isOk());

        // Validate the IntersticeAssembly in the database
        List<IntersticeAssembly> intersticeAssemblyList = intersticeAssemblyRepository.findAll();
        assertThat(intersticeAssemblyList).hasSize(databaseSizeBeforeUpdate);
        IntersticeAssembly testIntersticeAssembly = intersticeAssemblyList.get(intersticeAssemblyList.size() - 1);
        assertThat(testIntersticeAssembly.getAssemblyLayer()).isEqualTo(UPDATED_ASSEMBLY_LAYER);
        assertThat(testIntersticeAssembly.getIntersticeLayer()).isEqualTo(UPDATED_INTERSTICE_LAYER);
        assertThat(testIntersticeAssembly.getForcedMeanMilimeterComponentDiameter())
            .isEqualTo(UPDATED_FORCED_MEAN_MILIMETER_COMPONENT_DIAMETER);
    }

    @Test
    @Transactional
    void putNonExistingIntersticeAssembly() throws Exception {
        int databaseSizeBeforeUpdate = intersticeAssemblyRepository.findAll().size();
        intersticeAssembly.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntersticeAssemblyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intersticeAssembly.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(intersticeAssembly))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntersticeAssembly in the database
        List<IntersticeAssembly> intersticeAssemblyList = intersticeAssemblyRepository.findAll();
        assertThat(intersticeAssemblyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIntersticeAssembly() throws Exception {
        int databaseSizeBeforeUpdate = intersticeAssemblyRepository.findAll().size();
        intersticeAssembly.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntersticeAssemblyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(intersticeAssembly))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntersticeAssembly in the database
        List<IntersticeAssembly> intersticeAssemblyList = intersticeAssemblyRepository.findAll();
        assertThat(intersticeAssemblyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIntersticeAssembly() throws Exception {
        int databaseSizeBeforeUpdate = intersticeAssemblyRepository.findAll().size();
        intersticeAssembly.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntersticeAssemblyMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(intersticeAssembly))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IntersticeAssembly in the database
        List<IntersticeAssembly> intersticeAssemblyList = intersticeAssemblyRepository.findAll();
        assertThat(intersticeAssemblyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIntersticeAssemblyWithPatch() throws Exception {
        // Initialize the database
        intersticeAssemblyRepository.saveAndFlush(intersticeAssembly);

        int databaseSizeBeforeUpdate = intersticeAssemblyRepository.findAll().size();

        // Update the intersticeAssembly using partial update
        IntersticeAssembly partialUpdatedIntersticeAssembly = new IntersticeAssembly();
        partialUpdatedIntersticeAssembly.setId(intersticeAssembly.getId());

        restIntersticeAssemblyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIntersticeAssembly.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIntersticeAssembly))
            )
            .andExpect(status().isOk());

        // Validate the IntersticeAssembly in the database
        List<IntersticeAssembly> intersticeAssemblyList = intersticeAssemblyRepository.findAll();
        assertThat(intersticeAssemblyList).hasSize(databaseSizeBeforeUpdate);
        IntersticeAssembly testIntersticeAssembly = intersticeAssemblyList.get(intersticeAssemblyList.size() - 1);
        assertThat(testIntersticeAssembly.getAssemblyLayer()).isEqualTo(DEFAULT_ASSEMBLY_LAYER);
        assertThat(testIntersticeAssembly.getIntersticeLayer()).isEqualTo(DEFAULT_INTERSTICE_LAYER);
        assertThat(testIntersticeAssembly.getForcedMeanMilimeterComponentDiameter())
            .isEqualTo(DEFAULT_FORCED_MEAN_MILIMETER_COMPONENT_DIAMETER);
    }

    @Test
    @Transactional
    void fullUpdateIntersticeAssemblyWithPatch() throws Exception {
        // Initialize the database
        intersticeAssemblyRepository.saveAndFlush(intersticeAssembly);

        int databaseSizeBeforeUpdate = intersticeAssemblyRepository.findAll().size();

        // Update the intersticeAssembly using partial update
        IntersticeAssembly partialUpdatedIntersticeAssembly = new IntersticeAssembly();
        partialUpdatedIntersticeAssembly.setId(intersticeAssembly.getId());

        partialUpdatedIntersticeAssembly
            .assemblyLayer(UPDATED_ASSEMBLY_LAYER)
            .intersticeLayer(UPDATED_INTERSTICE_LAYER)
            .forcedMeanMilimeterComponentDiameter(UPDATED_FORCED_MEAN_MILIMETER_COMPONENT_DIAMETER);

        restIntersticeAssemblyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIntersticeAssembly.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIntersticeAssembly))
            )
            .andExpect(status().isOk());

        // Validate the IntersticeAssembly in the database
        List<IntersticeAssembly> intersticeAssemblyList = intersticeAssemblyRepository.findAll();
        assertThat(intersticeAssemblyList).hasSize(databaseSizeBeforeUpdate);
        IntersticeAssembly testIntersticeAssembly = intersticeAssemblyList.get(intersticeAssemblyList.size() - 1);
        assertThat(testIntersticeAssembly.getAssemblyLayer()).isEqualTo(UPDATED_ASSEMBLY_LAYER);
        assertThat(testIntersticeAssembly.getIntersticeLayer()).isEqualTo(UPDATED_INTERSTICE_LAYER);
        assertThat(testIntersticeAssembly.getForcedMeanMilimeterComponentDiameter())
            .isEqualTo(UPDATED_FORCED_MEAN_MILIMETER_COMPONENT_DIAMETER);
    }

    @Test
    @Transactional
    void patchNonExistingIntersticeAssembly() throws Exception {
        int databaseSizeBeforeUpdate = intersticeAssemblyRepository.findAll().size();
        intersticeAssembly.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntersticeAssemblyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intersticeAssembly.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(intersticeAssembly))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntersticeAssembly in the database
        List<IntersticeAssembly> intersticeAssemblyList = intersticeAssemblyRepository.findAll();
        assertThat(intersticeAssemblyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIntersticeAssembly() throws Exception {
        int databaseSizeBeforeUpdate = intersticeAssemblyRepository.findAll().size();
        intersticeAssembly.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntersticeAssemblyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(intersticeAssembly))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntersticeAssembly in the database
        List<IntersticeAssembly> intersticeAssemblyList = intersticeAssemblyRepository.findAll();
        assertThat(intersticeAssemblyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIntersticeAssembly() throws Exception {
        int databaseSizeBeforeUpdate = intersticeAssemblyRepository.findAll().size();
        intersticeAssembly.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntersticeAssemblyMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(intersticeAssembly))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IntersticeAssembly in the database
        List<IntersticeAssembly> intersticeAssemblyList = intersticeAssemblyRepository.findAll();
        assertThat(intersticeAssemblyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIntersticeAssembly() throws Exception {
        // Initialize the database
        intersticeAssemblyRepository.saveAndFlush(intersticeAssembly);

        int databaseSizeBeforeDelete = intersticeAssemblyRepository.findAll().size();

        // Delete the intersticeAssembly
        restIntersticeAssemblyMockMvc
            .perform(delete(ENTITY_API_URL_ID, intersticeAssembly.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IntersticeAssembly> intersticeAssemblyList = intersticeAssemblyRepository.findAll();
        assertThat(intersticeAssemblyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
