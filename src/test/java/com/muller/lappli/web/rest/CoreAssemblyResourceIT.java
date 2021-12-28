package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.CoreAssembly;
import com.muller.lappli.domain.Position;
import com.muller.lappli.domain.Strand;
import com.muller.lappli.domain.enumeration.AssemblyMean;
import com.muller.lappli.repository.CoreAssemblyRepository;
import com.muller.lappli.service.criteria.CoreAssemblyCriteria;
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

    private static final Long DEFAULT_OPERATION_LAYER = 1L;
    private static final Long UPDATED_OPERATION_LAYER = 2L;
    private static final Long SMALLER_OPERATION_LAYER = 1L - 1L;

    private static final Long DEFAULT_PRODUCTION_STEP = 1L;
    private static final Long UPDATED_PRODUCTION_STEP = 2L;
    private static final Long SMALLER_PRODUCTION_STEP = 1L - 1L;

    private static final Double DEFAULT_ASSEMBLY_STEP = 1D;
    private static final Double UPDATED_ASSEMBLY_STEP = 2D;
    private static final Double SMALLER_ASSEMBLY_STEP = 1D - 1D;

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
            .operationLayer(DEFAULT_OPERATION_LAYER)
            .productionStep(DEFAULT_PRODUCTION_STEP)
            .assemblyStep(DEFAULT_ASSEMBLY_STEP)
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
        coreAssembly.setStrand(strand);
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
            .productionStep(UPDATED_PRODUCTION_STEP)
            .assemblyStep(UPDATED_ASSEMBLY_STEP)
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
        coreAssembly.setStrand(strand);
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
        assertThat(testCoreAssembly.getProductionStep()).isEqualTo(DEFAULT_PRODUCTION_STEP);
        assertThat(testCoreAssembly.getAssemblyStep()).isEqualTo(DEFAULT_ASSEMBLY_STEP);
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
    void checkProductionStepIsRequired() throws Exception {
        int databaseSizeBeforeTest = coreAssemblyRepository.findAll().size();
        // set the field null
        coreAssembly.setProductionStep(null);

        // Create the CoreAssembly, which fails.

        restCoreAssemblyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(coreAssembly)))
            .andExpect(status().isBadRequest());

        List<CoreAssembly> coreAssemblyList = coreAssemblyRepository.findAll();
        assertThat(coreAssemblyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAssemblyStepIsRequired() throws Exception {
        int databaseSizeBeforeTest = coreAssemblyRepository.findAll().size();
        // set the field null
        coreAssembly.setAssemblyStep(null);

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
            .andExpect(jsonPath("$.[*].operationLayer").value(hasItem(DEFAULT_OPERATION_LAYER.intValue())))
            .andExpect(jsonPath("$.[*].productionStep").value(hasItem(DEFAULT_PRODUCTION_STEP.intValue())))
            .andExpect(jsonPath("$.[*].assemblyStep").value(hasItem(DEFAULT_ASSEMBLY_STEP.doubleValue())))
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
            .andExpect(jsonPath("$.operationLayer").value(DEFAULT_OPERATION_LAYER.intValue()))
            .andExpect(jsonPath("$.productionStep").value(DEFAULT_PRODUCTION_STEP.intValue()))
            .andExpect(jsonPath("$.assemblyStep").value(DEFAULT_ASSEMBLY_STEP.doubleValue()))
            .andExpect(jsonPath("$.assemblyMean").value(DEFAULT_ASSEMBLY_MEAN.toString()));
    }

    @Test
    @Transactional
    void getCoreAssembliesByIdFiltering() throws Exception {
        // Initialize the database
        coreAssemblyRepository.saveAndFlush(coreAssembly);

        Long id = coreAssembly.getId();

        defaultCoreAssemblyShouldBeFound("id.equals=" + id);
        defaultCoreAssemblyShouldNotBeFound("id.notEquals=" + id);

        defaultCoreAssemblyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCoreAssemblyShouldNotBeFound("id.greaterThan=" + id);

        defaultCoreAssemblyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCoreAssemblyShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCoreAssembliesByOperationLayerIsEqualToSomething() throws Exception {
        // Initialize the database
        coreAssemblyRepository.saveAndFlush(coreAssembly);

        // Get all the coreAssemblyList where operationLayer equals to DEFAULT_OPERATION_LAYER
        defaultCoreAssemblyShouldBeFound("operationLayer.equals=" + DEFAULT_OPERATION_LAYER);

        // Get all the coreAssemblyList where operationLayer equals to UPDATED_OPERATION_LAYER
        defaultCoreAssemblyShouldNotBeFound("operationLayer.equals=" + UPDATED_OPERATION_LAYER);
    }

    @Test
    @Transactional
    void getAllCoreAssembliesByOperationLayerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coreAssemblyRepository.saveAndFlush(coreAssembly);

        // Get all the coreAssemblyList where operationLayer not equals to DEFAULT_OPERATION_LAYER
        defaultCoreAssemblyShouldNotBeFound("operationLayer.notEquals=" + DEFAULT_OPERATION_LAYER);

        // Get all the coreAssemblyList where operationLayer not equals to UPDATED_OPERATION_LAYER
        defaultCoreAssemblyShouldBeFound("operationLayer.notEquals=" + UPDATED_OPERATION_LAYER);
    }

    @Test
    @Transactional
    void getAllCoreAssembliesByOperationLayerIsInShouldWork() throws Exception {
        // Initialize the database
        coreAssemblyRepository.saveAndFlush(coreAssembly);

        // Get all the coreAssemblyList where operationLayer in DEFAULT_OPERATION_LAYER or UPDATED_OPERATION_LAYER
        defaultCoreAssemblyShouldBeFound("operationLayer.in=" + DEFAULT_OPERATION_LAYER + "," + UPDATED_OPERATION_LAYER);

        // Get all the coreAssemblyList where operationLayer equals to UPDATED_OPERATION_LAYER
        defaultCoreAssemblyShouldNotBeFound("operationLayer.in=" + UPDATED_OPERATION_LAYER);
    }

    @Test
    @Transactional
    void getAllCoreAssembliesByOperationLayerIsNullOrNotNull() throws Exception {
        // Initialize the database
        coreAssemblyRepository.saveAndFlush(coreAssembly);

        // Get all the coreAssemblyList where operationLayer is not null
        defaultCoreAssemblyShouldBeFound("operationLayer.specified=true");

        // Get all the coreAssemblyList where operationLayer is null
        defaultCoreAssemblyShouldNotBeFound("operationLayer.specified=false");
    }

    @Test
    @Transactional
    void getAllCoreAssembliesByOperationLayerIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        coreAssemblyRepository.saveAndFlush(coreAssembly);

        // Get all the coreAssemblyList where operationLayer is greater than or equal to DEFAULT_OPERATION_LAYER
        defaultCoreAssemblyShouldBeFound("operationLayer.greaterThanOrEqual=" + DEFAULT_OPERATION_LAYER);

        // Get all the coreAssemblyList where operationLayer is greater than or equal to UPDATED_OPERATION_LAYER
        defaultCoreAssemblyShouldNotBeFound("operationLayer.greaterThanOrEqual=" + UPDATED_OPERATION_LAYER);
    }

    @Test
    @Transactional
    void getAllCoreAssembliesByOperationLayerIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        coreAssemblyRepository.saveAndFlush(coreAssembly);

        // Get all the coreAssemblyList where operationLayer is less than or equal to DEFAULT_OPERATION_LAYER
        defaultCoreAssemblyShouldBeFound("operationLayer.lessThanOrEqual=" + DEFAULT_OPERATION_LAYER);

        // Get all the coreAssemblyList where operationLayer is less than or equal to SMALLER_OPERATION_LAYER
        defaultCoreAssemblyShouldNotBeFound("operationLayer.lessThanOrEqual=" + SMALLER_OPERATION_LAYER);
    }

    @Test
    @Transactional
    void getAllCoreAssembliesByOperationLayerIsLessThanSomething() throws Exception {
        // Initialize the database
        coreAssemblyRepository.saveAndFlush(coreAssembly);

        // Get all the coreAssemblyList where operationLayer is less than DEFAULT_OPERATION_LAYER
        defaultCoreAssemblyShouldNotBeFound("operationLayer.lessThan=" + DEFAULT_OPERATION_LAYER);

        // Get all the coreAssemblyList where operationLayer is less than UPDATED_OPERATION_LAYER
        defaultCoreAssemblyShouldBeFound("operationLayer.lessThan=" + UPDATED_OPERATION_LAYER);
    }

    @Test
    @Transactional
    void getAllCoreAssembliesByOperationLayerIsGreaterThanSomething() throws Exception {
        // Initialize the database
        coreAssemblyRepository.saveAndFlush(coreAssembly);

        // Get all the coreAssemblyList where operationLayer is greater than DEFAULT_OPERATION_LAYER
        defaultCoreAssemblyShouldNotBeFound("operationLayer.greaterThan=" + DEFAULT_OPERATION_LAYER);

        // Get all the coreAssemblyList where operationLayer is greater than SMALLER_OPERATION_LAYER
        defaultCoreAssemblyShouldBeFound("operationLayer.greaterThan=" + SMALLER_OPERATION_LAYER);
    }

    @Test
    @Transactional
    void getAllCoreAssembliesByProductionStepIsEqualToSomething() throws Exception {
        // Initialize the database
        coreAssemblyRepository.saveAndFlush(coreAssembly);

        // Get all the coreAssemblyList where productionStep equals to DEFAULT_PRODUCTION_STEP
        defaultCoreAssemblyShouldBeFound("productionStep.equals=" + DEFAULT_PRODUCTION_STEP);

        // Get all the coreAssemblyList where productionStep equals to UPDATED_PRODUCTION_STEP
        defaultCoreAssemblyShouldNotBeFound("productionStep.equals=" + UPDATED_PRODUCTION_STEP);
    }

    @Test
    @Transactional
    void getAllCoreAssembliesByProductionStepIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coreAssemblyRepository.saveAndFlush(coreAssembly);

        // Get all the coreAssemblyList where productionStep not equals to DEFAULT_PRODUCTION_STEP
        defaultCoreAssemblyShouldNotBeFound("productionStep.notEquals=" + DEFAULT_PRODUCTION_STEP);

        // Get all the coreAssemblyList where productionStep not equals to UPDATED_PRODUCTION_STEP
        defaultCoreAssemblyShouldBeFound("productionStep.notEquals=" + UPDATED_PRODUCTION_STEP);
    }

    @Test
    @Transactional
    void getAllCoreAssembliesByProductionStepIsInShouldWork() throws Exception {
        // Initialize the database
        coreAssemblyRepository.saveAndFlush(coreAssembly);

        // Get all the coreAssemblyList where productionStep in DEFAULT_PRODUCTION_STEP or UPDATED_PRODUCTION_STEP
        defaultCoreAssemblyShouldBeFound("productionStep.in=" + DEFAULT_PRODUCTION_STEP + "," + UPDATED_PRODUCTION_STEP);

        // Get all the coreAssemblyList where productionStep equals to UPDATED_PRODUCTION_STEP
        defaultCoreAssemblyShouldNotBeFound("productionStep.in=" + UPDATED_PRODUCTION_STEP);
    }

    @Test
    @Transactional
    void getAllCoreAssembliesByProductionStepIsNullOrNotNull() throws Exception {
        // Initialize the database
        coreAssemblyRepository.saveAndFlush(coreAssembly);

        // Get all the coreAssemblyList where productionStep is not null
        defaultCoreAssemblyShouldBeFound("productionStep.specified=true");

        // Get all the coreAssemblyList where productionStep is null
        defaultCoreAssemblyShouldNotBeFound("productionStep.specified=false");
    }

    @Test
    @Transactional
    void getAllCoreAssembliesByProductionStepIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        coreAssemblyRepository.saveAndFlush(coreAssembly);

        // Get all the coreAssemblyList where productionStep is greater than or equal to DEFAULT_PRODUCTION_STEP
        defaultCoreAssemblyShouldBeFound("productionStep.greaterThanOrEqual=" + DEFAULT_PRODUCTION_STEP);

        // Get all the coreAssemblyList where productionStep is greater than or equal to UPDATED_PRODUCTION_STEP
        defaultCoreAssemblyShouldNotBeFound("productionStep.greaterThanOrEqual=" + UPDATED_PRODUCTION_STEP);
    }

    @Test
    @Transactional
    void getAllCoreAssembliesByProductionStepIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        coreAssemblyRepository.saveAndFlush(coreAssembly);

        // Get all the coreAssemblyList where productionStep is less than or equal to DEFAULT_PRODUCTION_STEP
        defaultCoreAssemblyShouldBeFound("productionStep.lessThanOrEqual=" + DEFAULT_PRODUCTION_STEP);

        // Get all the coreAssemblyList where productionStep is less than or equal to SMALLER_PRODUCTION_STEP
        defaultCoreAssemblyShouldNotBeFound("productionStep.lessThanOrEqual=" + SMALLER_PRODUCTION_STEP);
    }

    @Test
    @Transactional
    void getAllCoreAssembliesByProductionStepIsLessThanSomething() throws Exception {
        // Initialize the database
        coreAssemblyRepository.saveAndFlush(coreAssembly);

        // Get all the coreAssemblyList where productionStep is less than DEFAULT_PRODUCTION_STEP
        defaultCoreAssemblyShouldNotBeFound("productionStep.lessThan=" + DEFAULT_PRODUCTION_STEP);

        // Get all the coreAssemblyList where productionStep is less than UPDATED_PRODUCTION_STEP
        defaultCoreAssemblyShouldBeFound("productionStep.lessThan=" + UPDATED_PRODUCTION_STEP);
    }

    @Test
    @Transactional
    void getAllCoreAssembliesByProductionStepIsGreaterThanSomething() throws Exception {
        // Initialize the database
        coreAssemblyRepository.saveAndFlush(coreAssembly);

        // Get all the coreAssemblyList where productionStep is greater than DEFAULT_PRODUCTION_STEP
        defaultCoreAssemblyShouldNotBeFound("productionStep.greaterThan=" + DEFAULT_PRODUCTION_STEP);

        // Get all the coreAssemblyList where productionStep is greater than SMALLER_PRODUCTION_STEP
        defaultCoreAssemblyShouldBeFound("productionStep.greaterThan=" + SMALLER_PRODUCTION_STEP);
    }

    @Test
    @Transactional
    void getAllCoreAssembliesByAssemblyStepIsEqualToSomething() throws Exception {
        // Initialize the database
        coreAssemblyRepository.saveAndFlush(coreAssembly);

        // Get all the coreAssemblyList where assemblyStep equals to DEFAULT_ASSEMBLY_STEP
        defaultCoreAssemblyShouldBeFound("assemblyStep.equals=" + DEFAULT_ASSEMBLY_STEP);

        // Get all the coreAssemblyList where assemblyStep equals to UPDATED_ASSEMBLY_STEP
        defaultCoreAssemblyShouldNotBeFound("assemblyStep.equals=" + UPDATED_ASSEMBLY_STEP);
    }

    @Test
    @Transactional
    void getAllCoreAssembliesByAssemblyStepIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coreAssemblyRepository.saveAndFlush(coreAssembly);

        // Get all the coreAssemblyList where assemblyStep not equals to DEFAULT_ASSEMBLY_STEP
        defaultCoreAssemblyShouldNotBeFound("assemblyStep.notEquals=" + DEFAULT_ASSEMBLY_STEP);

        // Get all the coreAssemblyList where assemblyStep not equals to UPDATED_ASSEMBLY_STEP
        defaultCoreAssemblyShouldBeFound("assemblyStep.notEquals=" + UPDATED_ASSEMBLY_STEP);
    }

    @Test
    @Transactional
    void getAllCoreAssembliesByAssemblyStepIsInShouldWork() throws Exception {
        // Initialize the database
        coreAssemblyRepository.saveAndFlush(coreAssembly);

        // Get all the coreAssemblyList where assemblyStep in DEFAULT_ASSEMBLY_STEP or UPDATED_ASSEMBLY_STEP
        defaultCoreAssemblyShouldBeFound("assemblyStep.in=" + DEFAULT_ASSEMBLY_STEP + "," + UPDATED_ASSEMBLY_STEP);

        // Get all the coreAssemblyList where assemblyStep equals to UPDATED_ASSEMBLY_STEP
        defaultCoreAssemblyShouldNotBeFound("assemblyStep.in=" + UPDATED_ASSEMBLY_STEP);
    }

    @Test
    @Transactional
    void getAllCoreAssembliesByAssemblyStepIsNullOrNotNull() throws Exception {
        // Initialize the database
        coreAssemblyRepository.saveAndFlush(coreAssembly);

        // Get all the coreAssemblyList where assemblyStep is not null
        defaultCoreAssemblyShouldBeFound("assemblyStep.specified=true");

        // Get all the coreAssemblyList where assemblyStep is null
        defaultCoreAssemblyShouldNotBeFound("assemblyStep.specified=false");
    }

    @Test
    @Transactional
    void getAllCoreAssembliesByAssemblyStepIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        coreAssemblyRepository.saveAndFlush(coreAssembly);

        // Get all the coreAssemblyList where assemblyStep is greater than or equal to DEFAULT_ASSEMBLY_STEP
        defaultCoreAssemblyShouldBeFound("assemblyStep.greaterThanOrEqual=" + DEFAULT_ASSEMBLY_STEP);

        // Get all the coreAssemblyList where assemblyStep is greater than or equal to UPDATED_ASSEMBLY_STEP
        defaultCoreAssemblyShouldNotBeFound("assemblyStep.greaterThanOrEqual=" + UPDATED_ASSEMBLY_STEP);
    }

    @Test
    @Transactional
    void getAllCoreAssembliesByAssemblyStepIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        coreAssemblyRepository.saveAndFlush(coreAssembly);

        // Get all the coreAssemblyList where assemblyStep is less than or equal to DEFAULT_ASSEMBLY_STEP
        defaultCoreAssemblyShouldBeFound("assemblyStep.lessThanOrEqual=" + DEFAULT_ASSEMBLY_STEP);

        // Get all the coreAssemblyList where assemblyStep is less than or equal to SMALLER_ASSEMBLY_STEP
        defaultCoreAssemblyShouldNotBeFound("assemblyStep.lessThanOrEqual=" + SMALLER_ASSEMBLY_STEP);
    }

    @Test
    @Transactional
    void getAllCoreAssembliesByAssemblyStepIsLessThanSomething() throws Exception {
        // Initialize the database
        coreAssemblyRepository.saveAndFlush(coreAssembly);

        // Get all the coreAssemblyList where assemblyStep is less than DEFAULT_ASSEMBLY_STEP
        defaultCoreAssemblyShouldNotBeFound("assemblyStep.lessThan=" + DEFAULT_ASSEMBLY_STEP);

        // Get all the coreAssemblyList where assemblyStep is less than UPDATED_ASSEMBLY_STEP
        defaultCoreAssemblyShouldBeFound("assemblyStep.lessThan=" + UPDATED_ASSEMBLY_STEP);
    }

    @Test
    @Transactional
    void getAllCoreAssembliesByAssemblyStepIsGreaterThanSomething() throws Exception {
        // Initialize the database
        coreAssemblyRepository.saveAndFlush(coreAssembly);

        // Get all the coreAssemblyList where assemblyStep is greater than DEFAULT_ASSEMBLY_STEP
        defaultCoreAssemblyShouldNotBeFound("assemblyStep.greaterThan=" + DEFAULT_ASSEMBLY_STEP);

        // Get all the coreAssemblyList where assemblyStep is greater than SMALLER_ASSEMBLY_STEP
        defaultCoreAssemblyShouldBeFound("assemblyStep.greaterThan=" + SMALLER_ASSEMBLY_STEP);
    }

    @Test
    @Transactional
    void getAllCoreAssembliesByAssemblyMeanIsEqualToSomething() throws Exception {
        // Initialize the database
        coreAssemblyRepository.saveAndFlush(coreAssembly);

        // Get all the coreAssemblyList where assemblyMean equals to DEFAULT_ASSEMBLY_MEAN
        defaultCoreAssemblyShouldBeFound("assemblyMean.equals=" + DEFAULT_ASSEMBLY_MEAN);

        // Get all the coreAssemblyList where assemblyMean equals to UPDATED_ASSEMBLY_MEAN
        defaultCoreAssemblyShouldNotBeFound("assemblyMean.equals=" + UPDATED_ASSEMBLY_MEAN);
    }

    @Test
    @Transactional
    void getAllCoreAssembliesByAssemblyMeanIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coreAssemblyRepository.saveAndFlush(coreAssembly);

        // Get all the coreAssemblyList where assemblyMean not equals to DEFAULT_ASSEMBLY_MEAN
        defaultCoreAssemblyShouldNotBeFound("assemblyMean.notEquals=" + DEFAULT_ASSEMBLY_MEAN);

        // Get all the coreAssemblyList where assemblyMean not equals to UPDATED_ASSEMBLY_MEAN
        defaultCoreAssemblyShouldBeFound("assemblyMean.notEquals=" + UPDATED_ASSEMBLY_MEAN);
    }

    @Test
    @Transactional
    void getAllCoreAssembliesByAssemblyMeanIsInShouldWork() throws Exception {
        // Initialize the database
        coreAssemblyRepository.saveAndFlush(coreAssembly);

        // Get all the coreAssemblyList where assemblyMean in DEFAULT_ASSEMBLY_MEAN or UPDATED_ASSEMBLY_MEAN
        defaultCoreAssemblyShouldBeFound("assemblyMean.in=" + DEFAULT_ASSEMBLY_MEAN + "," + UPDATED_ASSEMBLY_MEAN);

        // Get all the coreAssemblyList where assemblyMean equals to UPDATED_ASSEMBLY_MEAN
        defaultCoreAssemblyShouldNotBeFound("assemblyMean.in=" + UPDATED_ASSEMBLY_MEAN);
    }

    @Test
    @Transactional
    void getAllCoreAssembliesByAssemblyMeanIsNullOrNotNull() throws Exception {
        // Initialize the database
        coreAssemblyRepository.saveAndFlush(coreAssembly);

        // Get all the coreAssemblyList where assemblyMean is not null
        defaultCoreAssemblyShouldBeFound("assemblyMean.specified=true");

        // Get all the coreAssemblyList where assemblyMean is null
        defaultCoreAssemblyShouldNotBeFound("assemblyMean.specified=false");
    }

    @Test
    @Transactional
    void getAllCoreAssembliesByPositionsIsEqualToSomething() throws Exception {
        // Initialize the database
        coreAssemblyRepository.saveAndFlush(coreAssembly);
        Position positions;
        if (TestUtil.findAll(em, Position.class).isEmpty()) {
            positions = PositionResourceIT.createEntity(em);
            em.persist(positions);
            em.flush();
        } else {
            positions = TestUtil.findAll(em, Position.class).get(0);
        }
        em.persist(positions);
        em.flush();
        coreAssembly.addPositions(positions);
        coreAssemblyRepository.saveAndFlush(coreAssembly);
        Long positionsId = positions.getId();

        // Get all the coreAssemblyList where positions equals to positionsId
        defaultCoreAssemblyShouldBeFound("positionsId.equals=" + positionsId);

        // Get all the coreAssemblyList where positions equals to (positionsId + 1)
        defaultCoreAssemblyShouldNotBeFound("positionsId.equals=" + (positionsId + 1));
    }

    @Test
    @Transactional
    void getAllCoreAssembliesByStrandIsEqualToSomething() throws Exception {
        // Initialize the database
        coreAssemblyRepository.saveAndFlush(coreAssembly);
        Strand strand;
        if (TestUtil.findAll(em, Strand.class).isEmpty()) {
            strand = StrandResourceIT.createEntity(em);
            em.persist(strand);
            em.flush();
        } else {
            strand = TestUtil.findAll(em, Strand.class).get(0);
        }
        em.persist(strand);
        em.flush();
        coreAssembly.setStrand(strand);
        coreAssemblyRepository.saveAndFlush(coreAssembly);
        Long strandId = strand.getId();

        // Get all the coreAssemblyList where strand equals to strandId
        defaultCoreAssemblyShouldBeFound("strandId.equals=" + strandId);

        // Get all the coreAssemblyList where strand equals to (strandId + 1)
        defaultCoreAssemblyShouldNotBeFound("strandId.equals=" + (strandId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCoreAssemblyShouldBeFound(String filter) throws Exception {
        restCoreAssemblyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coreAssembly.getId().intValue())))
            .andExpect(jsonPath("$.[*].operationLayer").value(hasItem(DEFAULT_OPERATION_LAYER.intValue())))
            .andExpect(jsonPath("$.[*].productionStep").value(hasItem(DEFAULT_PRODUCTION_STEP.intValue())))
            .andExpect(jsonPath("$.[*].assemblyStep").value(hasItem(DEFAULT_ASSEMBLY_STEP.doubleValue())))
            .andExpect(jsonPath("$.[*].assemblyMean").value(hasItem(DEFAULT_ASSEMBLY_MEAN.toString())));

        // Check, that the count call also returns 1
        restCoreAssemblyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCoreAssemblyShouldNotBeFound(String filter) throws Exception {
        restCoreAssemblyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCoreAssemblyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
        updatedCoreAssembly.productionStep(UPDATED_PRODUCTION_STEP).assemblyStep(UPDATED_ASSEMBLY_STEP).assemblyMean(UPDATED_ASSEMBLY_MEAN);

        ResultMatcher expectedResult = updatedCoreAssembly.positionsAreRight() ? status().isOk() : status().isBadRequest();

        restCoreAssemblyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCoreAssembly.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCoreAssembly))
            )
            .andExpect(expectedResult);

        // Validate the CoreAssembly in the database
        List<CoreAssembly> coreAssemblyList = coreAssemblyRepository.findAll();
        assertThat(coreAssemblyList).hasSize(databaseSizeBeforeUpdate);
        CoreAssembly testCoreAssembly = coreAssemblyList.get(coreAssemblyList.size() - 1);
        assertThat(testCoreAssembly.getOperationLayer()).isEqualTo(UPDATED_OPERATION_LAYER);
        assertThat(testCoreAssembly.getProductionStep()).isEqualTo(UPDATED_PRODUCTION_STEP);
        assertThat(testCoreAssembly.getAssemblyStep()).isEqualTo(UPDATED_ASSEMBLY_STEP);
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

        partialUpdatedCoreAssembly.assemblyMean(UPDATED_ASSEMBLY_MEAN);

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
        assertThat(testCoreAssembly.getProductionStep()).isEqualTo(DEFAULT_PRODUCTION_STEP);
        assertThat(testCoreAssembly.getAssemblyStep()).isEqualTo(DEFAULT_ASSEMBLY_STEP);
        assertThat(testCoreAssembly.getAssemblyMean()).isEqualTo(UPDATED_ASSEMBLY_MEAN);
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
            .productionStep(UPDATED_PRODUCTION_STEP)
            .assemblyStep(UPDATED_ASSEMBLY_STEP)
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
        assertThat(testCoreAssembly.getOperationLayer()).isEqualTo(UPDATED_OPERATION_LAYER);
        assertThat(testCoreAssembly.getProductionStep()).isEqualTo(UPDATED_PRODUCTION_STEP);
        assertThat(testCoreAssembly.getAssemblyStep()).isEqualTo(UPDATED_ASSEMBLY_STEP);
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
