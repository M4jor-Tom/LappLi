package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.Material;
import com.muller.lappli.domain.OneStudySupply;
import com.muller.lappli.domain.Strand;
import com.muller.lappli.domain.enumeration.Color;
import com.muller.lappli.domain.enumeration.MarkingType;
import com.muller.lappli.repository.OneStudySupplyRepository;
import com.muller.lappli.service.criteria.OneStudySupplyCriteria;
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
 * Integration tests for the {@link OneStudySupplyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OneStudySupplyResourceIT {

    private static final Long DEFAULT_APPARITIONS = 1L;
    private static final Long UPDATED_APPARITIONS = 2L;
    private static final Long SMALLER_APPARITIONS = 1L - 1L;

    private static final Long DEFAULT_NUMBER = 1L;
    private static final Long UPDATED_NUMBER = 2L;
    private static final Long SMALLER_NUMBER = 1L - 1L;

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final MarkingType DEFAULT_MARKING_TYPE = MarkingType.LIFTING;
    private static final MarkingType UPDATED_MARKING_TYPE = MarkingType.SPIRALLY_COLORED;

    private static final Double DEFAULT_GRAM_PER_METER_LINEAR_MASS = 1D;
    private static final Double UPDATED_GRAM_PER_METER_LINEAR_MASS = 2D;
    private static final Double SMALLER_GRAM_PER_METER_LINEAR_MASS = 1D - 1D;

    private static final Double DEFAULT_MILIMETER_DIAMETER = 1D;
    private static final Double UPDATED_MILIMETER_DIAMETER = 2D;
    private static final Double SMALLER_MILIMETER_DIAMETER = 1D - 1D;

    private static final Color DEFAULT_SURFACE_COLOR = Color.NATURAL;
    private static final Color UPDATED_SURFACE_COLOR = Color.WHITE;

    private static final String ENTITY_API_URL = "/api/one-study-supplies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OneStudySupplyRepository oneStudySupplyRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOneStudySupplyMockMvc;

    private OneStudySupply oneStudySupply;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OneStudySupply createEntity(EntityManager em) {
        OneStudySupply oneStudySupply = new OneStudySupply()
            .apparitions(DEFAULT_APPARITIONS)
            .number(DEFAULT_NUMBER)
            .designation(DEFAULT_DESIGNATION)
            .description(DEFAULT_DESCRIPTION)
            .markingType(DEFAULT_MARKING_TYPE)
            .gramPerMeterLinearMass(DEFAULT_GRAM_PER_METER_LINEAR_MASS)
            .milimeterDiameter(DEFAULT_MILIMETER_DIAMETER)
            .surfaceColor(DEFAULT_SURFACE_COLOR);
        // Add required entity
        Material material;
        if (TestUtil.findAll(em, Material.class).isEmpty()) {
            material = MaterialResourceIT.createEntity(em);
            em.persist(material);
            em.flush();
        } else {
            material = TestUtil.findAll(em, Material.class).get(0);
        }
        oneStudySupply.setSurfaceMaterial(material);
        // Add required entity
        Strand strand;
        if (TestUtil.findAll(em, Strand.class).isEmpty()) {
            strand = StrandResourceIT.createEntity(em);
            em.persist(strand);
            em.flush();
        } else {
            strand = TestUtil.findAll(em, Strand.class).get(0);
        }
        oneStudySupply.setStrand(strand);
        return oneStudySupply;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OneStudySupply createUpdatedEntity(EntityManager em) {
        OneStudySupply oneStudySupply = new OneStudySupply()
            .apparitions(UPDATED_APPARITIONS)
            .number(UPDATED_NUMBER)
            .designation(UPDATED_DESIGNATION)
            .description(UPDATED_DESCRIPTION)
            .markingType(UPDATED_MARKING_TYPE)
            .gramPerMeterLinearMass(UPDATED_GRAM_PER_METER_LINEAR_MASS)
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER)
            .surfaceColor(UPDATED_SURFACE_COLOR);
        // Add required entity
        Material material;
        if (TestUtil.findAll(em, Material.class).isEmpty()) {
            material = MaterialResourceIT.createUpdatedEntity(em);
            em.persist(material);
            em.flush();
        } else {
            material = TestUtil.findAll(em, Material.class).get(0);
        }
        oneStudySupply.setSurfaceMaterial(material);
        // Add required entity
        Strand strand;
        if (TestUtil.findAll(em, Strand.class).isEmpty()) {
            strand = StrandResourceIT.createUpdatedEntity(em);
            em.persist(strand);
            em.flush();
        } else {
            strand = TestUtil.findAll(em, Strand.class).get(0);
        }
        oneStudySupply.setStrand(strand);
        return oneStudySupply;
    }

    @BeforeEach
    public void initTest() {
        oneStudySupply = createEntity(em);
    }

    @Test
    @Transactional
    void createOneStudySupply() throws Exception {
        int databaseSizeBeforeCreate = oneStudySupplyRepository.findAll().size();
        // Create the OneStudySupply
        restOneStudySupplyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oneStudySupply))
            )
            .andExpect(status().isCreated());

        // Validate the OneStudySupply in the database
        List<OneStudySupply> oneStudySupplyList = oneStudySupplyRepository.findAll();
        assertThat(oneStudySupplyList).hasSize(databaseSizeBeforeCreate + 1);
        OneStudySupply testOneStudySupply = oneStudySupplyList.get(oneStudySupplyList.size() - 1);
        assertThat(testOneStudySupply.getApparitions()).isEqualTo(DEFAULT_APPARITIONS);
        assertThat(testOneStudySupply.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testOneStudySupply.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testOneStudySupply.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOneStudySupply.getMarkingType()).isEqualTo(DEFAULT_MARKING_TYPE);
        assertThat(testOneStudySupply.getGramPerMeterLinearMass()).isEqualTo(DEFAULT_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testOneStudySupply.getMilimeterDiameter()).isEqualTo(DEFAULT_MILIMETER_DIAMETER);
        assertThat(testOneStudySupply.getSurfaceColor()).isEqualTo(DEFAULT_SURFACE_COLOR);
    }

    @Test
    @Transactional
    void createOneStudySupplyWithExistingId() throws Exception {
        // Create the OneStudySupply with an existing ID
        oneStudySupply.setId(1L);

        int databaseSizeBeforeCreate = oneStudySupplyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOneStudySupplyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oneStudySupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the OneStudySupply in the database
        List<OneStudySupply> oneStudySupplyList = oneStudySupplyRepository.findAll();
        assertThat(oneStudySupplyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMarkingTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = oneStudySupplyRepository.findAll().size();
        // set the field null
        oneStudySupply.setMarkingType(null);

        // Create the OneStudySupply, which fails.

        restOneStudySupplyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oneStudySupply))
            )
            .andExpect(status().isBadRequest());

        List<OneStudySupply> oneStudySupplyList = oneStudySupplyRepository.findAll();
        assertThat(oneStudySupplyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGramPerMeterLinearMassIsRequired() throws Exception {
        int databaseSizeBeforeTest = oneStudySupplyRepository.findAll().size();
        // set the field null
        oneStudySupply.setGramPerMeterLinearMass(null);

        // Create the OneStudySupply, which fails.

        restOneStudySupplyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oneStudySupply))
            )
            .andExpect(status().isBadRequest());

        List<OneStudySupply> oneStudySupplyList = oneStudySupplyRepository.findAll();
        assertThat(oneStudySupplyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMilimeterDiameterIsRequired() throws Exception {
        int databaseSizeBeforeTest = oneStudySupplyRepository.findAll().size();
        // set the field null
        oneStudySupply.setMilimeterDiameter(null);

        // Create the OneStudySupply, which fails.

        restOneStudySupplyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oneStudySupply))
            )
            .andExpect(status().isBadRequest());

        List<OneStudySupply> oneStudySupplyList = oneStudySupplyRepository.findAll();
        assertThat(oneStudySupplyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSurfaceColorIsRequired() throws Exception {
        int databaseSizeBeforeTest = oneStudySupplyRepository.findAll().size();
        // set the field null
        oneStudySupply.setSurfaceColor(null);

        // Create the OneStudySupply, which fails.

        restOneStudySupplyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oneStudySupply))
            )
            .andExpect(status().isBadRequest());

        List<OneStudySupply> oneStudySupplyList = oneStudySupplyRepository.findAll();
        assertThat(oneStudySupplyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOneStudySupplies() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList
        restOneStudySupplyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(oneStudySupply.getId().intValue())))
            .andExpect(jsonPath("$.[*].apparitions").value(hasItem(DEFAULT_APPARITIONS.intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].markingType").value(hasItem(DEFAULT_MARKING_TYPE.toString())))
            .andExpect(jsonPath("$.[*].gramPerMeterLinearMass").value(hasItem(DEFAULT_GRAM_PER_METER_LINEAR_MASS.doubleValue())))
            .andExpect(jsonPath("$.[*].milimeterDiameter").value(hasItem(DEFAULT_MILIMETER_DIAMETER.doubleValue())))
            .andExpect(jsonPath("$.[*].surfaceColor").value(hasItem(DEFAULT_SURFACE_COLOR.toString())));
    }

    @Test
    @Transactional
    void getOneStudySupply() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get the oneStudySupply
        restOneStudySupplyMockMvc
            .perform(get(ENTITY_API_URL_ID, oneStudySupply.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(oneStudySupply.getId().intValue()))
            .andExpect(jsonPath("$.apparitions").value(DEFAULT_APPARITIONS.intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.intValue()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.markingType").value(DEFAULT_MARKING_TYPE.toString()))
            .andExpect(jsonPath("$.gramPerMeterLinearMass").value(DEFAULT_GRAM_PER_METER_LINEAR_MASS.doubleValue()))
            .andExpect(jsonPath("$.milimeterDiameter").value(DEFAULT_MILIMETER_DIAMETER.doubleValue()))
            .andExpect(jsonPath("$.surfaceColor").value(DEFAULT_SURFACE_COLOR.toString()));
    }

    @Test
    @Transactional
    void getOneStudySuppliesByIdFiltering() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        Long id = oneStudySupply.getId();

        defaultOneStudySupplyShouldBeFound("id.equals=" + id);
        defaultOneStudySupplyShouldNotBeFound("id.notEquals=" + id);

        defaultOneStudySupplyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOneStudySupplyShouldNotBeFound("id.greaterThan=" + id);

        defaultOneStudySupplyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOneStudySupplyShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByApparitionsIsEqualToSomething() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where apparitions equals to DEFAULT_APPARITIONS
        defaultOneStudySupplyShouldBeFound("apparitions.equals=" + DEFAULT_APPARITIONS);

        // Get all the oneStudySupplyList where apparitions equals to UPDATED_APPARITIONS
        defaultOneStudySupplyShouldNotBeFound("apparitions.equals=" + UPDATED_APPARITIONS);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByApparitionsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where apparitions not equals to DEFAULT_APPARITIONS
        defaultOneStudySupplyShouldNotBeFound("apparitions.notEquals=" + DEFAULT_APPARITIONS);

        // Get all the oneStudySupplyList where apparitions not equals to UPDATED_APPARITIONS
        defaultOneStudySupplyShouldBeFound("apparitions.notEquals=" + UPDATED_APPARITIONS);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByApparitionsIsInShouldWork() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where apparitions in DEFAULT_APPARITIONS or UPDATED_APPARITIONS
        defaultOneStudySupplyShouldBeFound("apparitions.in=" + DEFAULT_APPARITIONS + "," + UPDATED_APPARITIONS);

        // Get all the oneStudySupplyList where apparitions equals to UPDATED_APPARITIONS
        defaultOneStudySupplyShouldNotBeFound("apparitions.in=" + UPDATED_APPARITIONS);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByApparitionsIsNullOrNotNull() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where apparitions is not null
        defaultOneStudySupplyShouldBeFound("apparitions.specified=true");

        // Get all the oneStudySupplyList where apparitions is null
        defaultOneStudySupplyShouldNotBeFound("apparitions.specified=false");
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByApparitionsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where apparitions is greater than or equal to DEFAULT_APPARITIONS
        defaultOneStudySupplyShouldBeFound("apparitions.greaterThanOrEqual=" + DEFAULT_APPARITIONS);

        // Get all the oneStudySupplyList where apparitions is greater than or equal to UPDATED_APPARITIONS
        defaultOneStudySupplyShouldNotBeFound("apparitions.greaterThanOrEqual=" + UPDATED_APPARITIONS);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByApparitionsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where apparitions is less than or equal to DEFAULT_APPARITIONS
        defaultOneStudySupplyShouldBeFound("apparitions.lessThanOrEqual=" + DEFAULT_APPARITIONS);

        // Get all the oneStudySupplyList where apparitions is less than or equal to SMALLER_APPARITIONS
        defaultOneStudySupplyShouldNotBeFound("apparitions.lessThanOrEqual=" + SMALLER_APPARITIONS);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByApparitionsIsLessThanSomething() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where apparitions is less than DEFAULT_APPARITIONS
        defaultOneStudySupplyShouldNotBeFound("apparitions.lessThan=" + DEFAULT_APPARITIONS);

        // Get all the oneStudySupplyList where apparitions is less than UPDATED_APPARITIONS
        defaultOneStudySupplyShouldBeFound("apparitions.lessThan=" + UPDATED_APPARITIONS);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByApparitionsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where apparitions is greater than DEFAULT_APPARITIONS
        defaultOneStudySupplyShouldNotBeFound("apparitions.greaterThan=" + DEFAULT_APPARITIONS);

        // Get all the oneStudySupplyList where apparitions is greater than SMALLER_APPARITIONS
        defaultOneStudySupplyShouldBeFound("apparitions.greaterThan=" + SMALLER_APPARITIONS);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where number equals to DEFAULT_NUMBER
        defaultOneStudySupplyShouldBeFound("number.equals=" + DEFAULT_NUMBER);

        // Get all the oneStudySupplyList where number equals to UPDATED_NUMBER
        defaultOneStudySupplyShouldNotBeFound("number.equals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where number not equals to DEFAULT_NUMBER
        defaultOneStudySupplyShouldNotBeFound("number.notEquals=" + DEFAULT_NUMBER);

        // Get all the oneStudySupplyList where number not equals to UPDATED_NUMBER
        defaultOneStudySupplyShouldBeFound("number.notEquals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByNumberIsInShouldWork() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where number in DEFAULT_NUMBER or UPDATED_NUMBER
        defaultOneStudySupplyShouldBeFound("number.in=" + DEFAULT_NUMBER + "," + UPDATED_NUMBER);

        // Get all the oneStudySupplyList where number equals to UPDATED_NUMBER
        defaultOneStudySupplyShouldNotBeFound("number.in=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where number is not null
        defaultOneStudySupplyShouldBeFound("number.specified=true");

        // Get all the oneStudySupplyList where number is null
        defaultOneStudySupplyShouldNotBeFound("number.specified=false");
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where number is greater than or equal to DEFAULT_NUMBER
        defaultOneStudySupplyShouldBeFound("number.greaterThanOrEqual=" + DEFAULT_NUMBER);

        // Get all the oneStudySupplyList where number is greater than or equal to UPDATED_NUMBER
        defaultOneStudySupplyShouldNotBeFound("number.greaterThanOrEqual=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where number is less than or equal to DEFAULT_NUMBER
        defaultOneStudySupplyShouldBeFound("number.lessThanOrEqual=" + DEFAULT_NUMBER);

        // Get all the oneStudySupplyList where number is less than or equal to SMALLER_NUMBER
        defaultOneStudySupplyShouldNotBeFound("number.lessThanOrEqual=" + SMALLER_NUMBER);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where number is less than DEFAULT_NUMBER
        defaultOneStudySupplyShouldNotBeFound("number.lessThan=" + DEFAULT_NUMBER);

        // Get all the oneStudySupplyList where number is less than UPDATED_NUMBER
        defaultOneStudySupplyShouldBeFound("number.lessThan=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where number is greater than DEFAULT_NUMBER
        defaultOneStudySupplyShouldNotBeFound("number.greaterThan=" + DEFAULT_NUMBER);

        // Get all the oneStudySupplyList where number is greater than SMALLER_NUMBER
        defaultOneStudySupplyShouldBeFound("number.greaterThan=" + SMALLER_NUMBER);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where designation equals to DEFAULT_DESIGNATION
        defaultOneStudySupplyShouldBeFound("designation.equals=" + DEFAULT_DESIGNATION);

        // Get all the oneStudySupplyList where designation equals to UPDATED_DESIGNATION
        defaultOneStudySupplyShouldNotBeFound("designation.equals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByDesignationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where designation not equals to DEFAULT_DESIGNATION
        defaultOneStudySupplyShouldNotBeFound("designation.notEquals=" + DEFAULT_DESIGNATION);

        // Get all the oneStudySupplyList where designation not equals to UPDATED_DESIGNATION
        defaultOneStudySupplyShouldBeFound("designation.notEquals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByDesignationIsInShouldWork() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where designation in DEFAULT_DESIGNATION or UPDATED_DESIGNATION
        defaultOneStudySupplyShouldBeFound("designation.in=" + DEFAULT_DESIGNATION + "," + UPDATED_DESIGNATION);

        // Get all the oneStudySupplyList where designation equals to UPDATED_DESIGNATION
        defaultOneStudySupplyShouldNotBeFound("designation.in=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByDesignationIsNullOrNotNull() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where designation is not null
        defaultOneStudySupplyShouldBeFound("designation.specified=true");

        // Get all the oneStudySupplyList where designation is null
        defaultOneStudySupplyShouldNotBeFound("designation.specified=false");
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByDesignationContainsSomething() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where designation contains DEFAULT_DESIGNATION
        defaultOneStudySupplyShouldBeFound("designation.contains=" + DEFAULT_DESIGNATION);

        // Get all the oneStudySupplyList where designation contains UPDATED_DESIGNATION
        defaultOneStudySupplyShouldNotBeFound("designation.contains=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByDesignationNotContainsSomething() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where designation does not contain DEFAULT_DESIGNATION
        defaultOneStudySupplyShouldNotBeFound("designation.doesNotContain=" + DEFAULT_DESIGNATION);

        // Get all the oneStudySupplyList where designation does not contain UPDATED_DESIGNATION
        defaultOneStudySupplyShouldBeFound("designation.doesNotContain=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where description equals to DEFAULT_DESCRIPTION
        defaultOneStudySupplyShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the oneStudySupplyList where description equals to UPDATED_DESCRIPTION
        defaultOneStudySupplyShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where description not equals to DEFAULT_DESCRIPTION
        defaultOneStudySupplyShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the oneStudySupplyList where description not equals to UPDATED_DESCRIPTION
        defaultOneStudySupplyShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultOneStudySupplyShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the oneStudySupplyList where description equals to UPDATED_DESCRIPTION
        defaultOneStudySupplyShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where description is not null
        defaultOneStudySupplyShouldBeFound("description.specified=true");

        // Get all the oneStudySupplyList where description is null
        defaultOneStudySupplyShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where description contains DEFAULT_DESCRIPTION
        defaultOneStudySupplyShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the oneStudySupplyList where description contains UPDATED_DESCRIPTION
        defaultOneStudySupplyShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where description does not contain DEFAULT_DESCRIPTION
        defaultOneStudySupplyShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the oneStudySupplyList where description does not contain UPDATED_DESCRIPTION
        defaultOneStudySupplyShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByMarkingTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where markingType equals to DEFAULT_MARKING_TYPE
        defaultOneStudySupplyShouldBeFound("markingType.equals=" + DEFAULT_MARKING_TYPE);

        // Get all the oneStudySupplyList where markingType equals to UPDATED_MARKING_TYPE
        defaultOneStudySupplyShouldNotBeFound("markingType.equals=" + UPDATED_MARKING_TYPE);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByMarkingTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where markingType not equals to DEFAULT_MARKING_TYPE
        defaultOneStudySupplyShouldNotBeFound("markingType.notEquals=" + DEFAULT_MARKING_TYPE);

        // Get all the oneStudySupplyList where markingType not equals to UPDATED_MARKING_TYPE
        defaultOneStudySupplyShouldBeFound("markingType.notEquals=" + UPDATED_MARKING_TYPE);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByMarkingTypeIsInShouldWork() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where markingType in DEFAULT_MARKING_TYPE or UPDATED_MARKING_TYPE
        defaultOneStudySupplyShouldBeFound("markingType.in=" + DEFAULT_MARKING_TYPE + "," + UPDATED_MARKING_TYPE);

        // Get all the oneStudySupplyList where markingType equals to UPDATED_MARKING_TYPE
        defaultOneStudySupplyShouldNotBeFound("markingType.in=" + UPDATED_MARKING_TYPE);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByMarkingTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where markingType is not null
        defaultOneStudySupplyShouldBeFound("markingType.specified=true");

        // Get all the oneStudySupplyList where markingType is null
        defaultOneStudySupplyShouldNotBeFound("markingType.specified=false");
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByGramPerMeterLinearMassIsEqualToSomething() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where gramPerMeterLinearMass equals to DEFAULT_GRAM_PER_METER_LINEAR_MASS
        defaultOneStudySupplyShouldBeFound("gramPerMeterLinearMass.equals=" + DEFAULT_GRAM_PER_METER_LINEAR_MASS);

        // Get all the oneStudySupplyList where gramPerMeterLinearMass equals to UPDATED_GRAM_PER_METER_LINEAR_MASS
        defaultOneStudySupplyShouldNotBeFound("gramPerMeterLinearMass.equals=" + UPDATED_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByGramPerMeterLinearMassIsNotEqualToSomething() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where gramPerMeterLinearMass not equals to DEFAULT_GRAM_PER_METER_LINEAR_MASS
        defaultOneStudySupplyShouldNotBeFound("gramPerMeterLinearMass.notEquals=" + DEFAULT_GRAM_PER_METER_LINEAR_MASS);

        // Get all the oneStudySupplyList where gramPerMeterLinearMass not equals to UPDATED_GRAM_PER_METER_LINEAR_MASS
        defaultOneStudySupplyShouldBeFound("gramPerMeterLinearMass.notEquals=" + UPDATED_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByGramPerMeterLinearMassIsInShouldWork() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where gramPerMeterLinearMass in DEFAULT_GRAM_PER_METER_LINEAR_MASS or UPDATED_GRAM_PER_METER_LINEAR_MASS
        defaultOneStudySupplyShouldBeFound(
            "gramPerMeterLinearMass.in=" + DEFAULT_GRAM_PER_METER_LINEAR_MASS + "," + UPDATED_GRAM_PER_METER_LINEAR_MASS
        );

        // Get all the oneStudySupplyList where gramPerMeterLinearMass equals to UPDATED_GRAM_PER_METER_LINEAR_MASS
        defaultOneStudySupplyShouldNotBeFound("gramPerMeterLinearMass.in=" + UPDATED_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByGramPerMeterLinearMassIsNullOrNotNull() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where gramPerMeterLinearMass is not null
        defaultOneStudySupplyShouldBeFound("gramPerMeterLinearMass.specified=true");

        // Get all the oneStudySupplyList where gramPerMeterLinearMass is null
        defaultOneStudySupplyShouldNotBeFound("gramPerMeterLinearMass.specified=false");
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByGramPerMeterLinearMassIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where gramPerMeterLinearMass is greater than or equal to DEFAULT_GRAM_PER_METER_LINEAR_MASS
        defaultOneStudySupplyShouldBeFound("gramPerMeterLinearMass.greaterThanOrEqual=" + DEFAULT_GRAM_PER_METER_LINEAR_MASS);

        // Get all the oneStudySupplyList where gramPerMeterLinearMass is greater than or equal to UPDATED_GRAM_PER_METER_LINEAR_MASS
        defaultOneStudySupplyShouldNotBeFound("gramPerMeterLinearMass.greaterThanOrEqual=" + UPDATED_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByGramPerMeterLinearMassIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where gramPerMeterLinearMass is less than or equal to DEFAULT_GRAM_PER_METER_LINEAR_MASS
        defaultOneStudySupplyShouldBeFound("gramPerMeterLinearMass.lessThanOrEqual=" + DEFAULT_GRAM_PER_METER_LINEAR_MASS);

        // Get all the oneStudySupplyList where gramPerMeterLinearMass is less than or equal to SMALLER_GRAM_PER_METER_LINEAR_MASS
        defaultOneStudySupplyShouldNotBeFound("gramPerMeterLinearMass.lessThanOrEqual=" + SMALLER_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByGramPerMeterLinearMassIsLessThanSomething() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where gramPerMeterLinearMass is less than DEFAULT_GRAM_PER_METER_LINEAR_MASS
        defaultOneStudySupplyShouldNotBeFound("gramPerMeterLinearMass.lessThan=" + DEFAULT_GRAM_PER_METER_LINEAR_MASS);

        // Get all the oneStudySupplyList where gramPerMeterLinearMass is less than UPDATED_GRAM_PER_METER_LINEAR_MASS
        defaultOneStudySupplyShouldBeFound("gramPerMeterLinearMass.lessThan=" + UPDATED_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByGramPerMeterLinearMassIsGreaterThanSomething() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where gramPerMeterLinearMass is greater than DEFAULT_GRAM_PER_METER_LINEAR_MASS
        defaultOneStudySupplyShouldNotBeFound("gramPerMeterLinearMass.greaterThan=" + DEFAULT_GRAM_PER_METER_LINEAR_MASS);

        // Get all the oneStudySupplyList where gramPerMeterLinearMass is greater than SMALLER_GRAM_PER_METER_LINEAR_MASS
        defaultOneStudySupplyShouldBeFound("gramPerMeterLinearMass.greaterThan=" + SMALLER_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByMilimeterDiameterIsEqualToSomething() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where milimeterDiameter equals to DEFAULT_MILIMETER_DIAMETER
        defaultOneStudySupplyShouldBeFound("milimeterDiameter.equals=" + DEFAULT_MILIMETER_DIAMETER);

        // Get all the oneStudySupplyList where milimeterDiameter equals to UPDATED_MILIMETER_DIAMETER
        defaultOneStudySupplyShouldNotBeFound("milimeterDiameter.equals=" + UPDATED_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByMilimeterDiameterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where milimeterDiameter not equals to DEFAULT_MILIMETER_DIAMETER
        defaultOneStudySupplyShouldNotBeFound("milimeterDiameter.notEquals=" + DEFAULT_MILIMETER_DIAMETER);

        // Get all the oneStudySupplyList where milimeterDiameter not equals to UPDATED_MILIMETER_DIAMETER
        defaultOneStudySupplyShouldBeFound("milimeterDiameter.notEquals=" + UPDATED_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByMilimeterDiameterIsInShouldWork() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where milimeterDiameter in DEFAULT_MILIMETER_DIAMETER or UPDATED_MILIMETER_DIAMETER
        defaultOneStudySupplyShouldBeFound("milimeterDiameter.in=" + DEFAULT_MILIMETER_DIAMETER + "," + UPDATED_MILIMETER_DIAMETER);

        // Get all the oneStudySupplyList where milimeterDiameter equals to UPDATED_MILIMETER_DIAMETER
        defaultOneStudySupplyShouldNotBeFound("milimeterDiameter.in=" + UPDATED_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByMilimeterDiameterIsNullOrNotNull() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where milimeterDiameter is not null
        defaultOneStudySupplyShouldBeFound("milimeterDiameter.specified=true");

        // Get all the oneStudySupplyList where milimeterDiameter is null
        defaultOneStudySupplyShouldNotBeFound("milimeterDiameter.specified=false");
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByMilimeterDiameterIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where milimeterDiameter is greater than or equal to DEFAULT_MILIMETER_DIAMETER
        defaultOneStudySupplyShouldBeFound("milimeterDiameter.greaterThanOrEqual=" + DEFAULT_MILIMETER_DIAMETER);

        // Get all the oneStudySupplyList where milimeterDiameter is greater than or equal to UPDATED_MILIMETER_DIAMETER
        defaultOneStudySupplyShouldNotBeFound("milimeterDiameter.greaterThanOrEqual=" + UPDATED_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByMilimeterDiameterIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where milimeterDiameter is less than or equal to DEFAULT_MILIMETER_DIAMETER
        defaultOneStudySupplyShouldBeFound("milimeterDiameter.lessThanOrEqual=" + DEFAULT_MILIMETER_DIAMETER);

        // Get all the oneStudySupplyList where milimeterDiameter is less than or equal to SMALLER_MILIMETER_DIAMETER
        defaultOneStudySupplyShouldNotBeFound("milimeterDiameter.lessThanOrEqual=" + SMALLER_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByMilimeterDiameterIsLessThanSomething() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where milimeterDiameter is less than DEFAULT_MILIMETER_DIAMETER
        defaultOneStudySupplyShouldNotBeFound("milimeterDiameter.lessThan=" + DEFAULT_MILIMETER_DIAMETER);

        // Get all the oneStudySupplyList where milimeterDiameter is less than UPDATED_MILIMETER_DIAMETER
        defaultOneStudySupplyShouldBeFound("milimeterDiameter.lessThan=" + UPDATED_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByMilimeterDiameterIsGreaterThanSomething() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where milimeterDiameter is greater than DEFAULT_MILIMETER_DIAMETER
        defaultOneStudySupplyShouldNotBeFound("milimeterDiameter.greaterThan=" + DEFAULT_MILIMETER_DIAMETER);

        // Get all the oneStudySupplyList where milimeterDiameter is greater than SMALLER_MILIMETER_DIAMETER
        defaultOneStudySupplyShouldBeFound("milimeterDiameter.greaterThan=" + SMALLER_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesBySurfaceColorIsEqualToSomething() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where surfaceColor equals to DEFAULT_SURFACE_COLOR
        defaultOneStudySupplyShouldBeFound("surfaceColor.equals=" + DEFAULT_SURFACE_COLOR);

        // Get all the oneStudySupplyList where surfaceColor equals to UPDATED_SURFACE_COLOR
        defaultOneStudySupplyShouldNotBeFound("surfaceColor.equals=" + UPDATED_SURFACE_COLOR);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesBySurfaceColorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where surfaceColor not equals to DEFAULT_SURFACE_COLOR
        defaultOneStudySupplyShouldNotBeFound("surfaceColor.notEquals=" + DEFAULT_SURFACE_COLOR);

        // Get all the oneStudySupplyList where surfaceColor not equals to UPDATED_SURFACE_COLOR
        defaultOneStudySupplyShouldBeFound("surfaceColor.notEquals=" + UPDATED_SURFACE_COLOR);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesBySurfaceColorIsInShouldWork() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where surfaceColor in DEFAULT_SURFACE_COLOR or UPDATED_SURFACE_COLOR
        defaultOneStudySupplyShouldBeFound("surfaceColor.in=" + DEFAULT_SURFACE_COLOR + "," + UPDATED_SURFACE_COLOR);

        // Get all the oneStudySupplyList where surfaceColor equals to UPDATED_SURFACE_COLOR
        defaultOneStudySupplyShouldNotBeFound("surfaceColor.in=" + UPDATED_SURFACE_COLOR);
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesBySurfaceColorIsNullOrNotNull() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList where surfaceColor is not null
        defaultOneStudySupplyShouldBeFound("surfaceColor.specified=true");

        // Get all the oneStudySupplyList where surfaceColor is null
        defaultOneStudySupplyShouldNotBeFound("surfaceColor.specified=false");
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesBySurfaceMaterialIsEqualToSomething() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);
        Material surfaceMaterial;
        if (TestUtil.findAll(em, Material.class).isEmpty()) {
            surfaceMaterial = MaterialResourceIT.createEntity(em);
            em.persist(surfaceMaterial);
            em.flush();
        } else {
            surfaceMaterial = TestUtil.findAll(em, Material.class).get(0);
        }
        em.persist(surfaceMaterial);
        em.flush();
        oneStudySupply.setSurfaceMaterial(surfaceMaterial);
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);
        Long surfaceMaterialId = surfaceMaterial.getId();

        // Get all the oneStudySupplyList where surfaceMaterial equals to surfaceMaterialId
        defaultOneStudySupplyShouldBeFound("surfaceMaterialId.equals=" + surfaceMaterialId);

        // Get all the oneStudySupplyList where surfaceMaterial equals to (surfaceMaterialId + 1)
        defaultOneStudySupplyShouldNotBeFound("surfaceMaterialId.equals=" + (surfaceMaterialId + 1));
    }

    @Test
    @Transactional
    void getAllOneStudySuppliesByStrandIsEqualToSomething() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);
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
        oneStudySupply.setStrand(strand);
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);
        Long strandId = strand.getId();

        // Get all the oneStudySupplyList where strand equals to strandId
        defaultOneStudySupplyShouldBeFound("strandId.equals=" + strandId);

        // Get all the oneStudySupplyList where strand equals to (strandId + 1)
        defaultOneStudySupplyShouldNotBeFound("strandId.equals=" + (strandId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOneStudySupplyShouldBeFound(String filter) throws Exception {
        restOneStudySupplyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(oneStudySupply.getId().intValue())))
            .andExpect(jsonPath("$.[*].apparitions").value(hasItem(DEFAULT_APPARITIONS.intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].markingType").value(hasItem(DEFAULT_MARKING_TYPE.toString())))
            .andExpect(jsonPath("$.[*].gramPerMeterLinearMass").value(hasItem(DEFAULT_GRAM_PER_METER_LINEAR_MASS.doubleValue())))
            .andExpect(jsonPath("$.[*].milimeterDiameter").value(hasItem(DEFAULT_MILIMETER_DIAMETER.doubleValue())))
            .andExpect(jsonPath("$.[*].surfaceColor").value(hasItem(DEFAULT_SURFACE_COLOR.toString())));

        // Check, that the count call also returns 1
        restOneStudySupplyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOneStudySupplyShouldNotBeFound(String filter) throws Exception {
        restOneStudySupplyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOneStudySupplyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOneStudySupply() throws Exception {
        // Get the oneStudySupply
        restOneStudySupplyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOneStudySupply() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        int databaseSizeBeforeUpdate = oneStudySupplyRepository.findAll().size();

        // Update the oneStudySupply
        OneStudySupply updatedOneStudySupply = oneStudySupplyRepository.findById(oneStudySupply.getId()).get();
        // Disconnect from session so that the updates on updatedOneStudySupply are not directly saved in db
        em.detach(updatedOneStudySupply);
        updatedOneStudySupply
            .apparitions(UPDATED_APPARITIONS)
            .number(UPDATED_NUMBER)
            .designation(UPDATED_DESIGNATION)
            .description(UPDATED_DESCRIPTION)
            .markingType(UPDATED_MARKING_TYPE)
            .gramPerMeterLinearMass(UPDATED_GRAM_PER_METER_LINEAR_MASS)
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER)
            .surfaceColor(UPDATED_SURFACE_COLOR);

        restOneStudySupplyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOneStudySupply.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOneStudySupply))
            )
            .andExpect(status().isOk());

        // Validate the OneStudySupply in the database
        List<OneStudySupply> oneStudySupplyList = oneStudySupplyRepository.findAll();
        assertThat(oneStudySupplyList).hasSize(databaseSizeBeforeUpdate);
        OneStudySupply testOneStudySupply = oneStudySupplyList.get(oneStudySupplyList.size() - 1);
        assertThat(testOneStudySupply.getApparitions()).isEqualTo(UPDATED_APPARITIONS);
        assertThat(testOneStudySupply.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testOneStudySupply.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testOneStudySupply.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOneStudySupply.getMarkingType()).isEqualTo(UPDATED_MARKING_TYPE);
        assertThat(testOneStudySupply.getGramPerMeterLinearMass()).isEqualTo(UPDATED_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testOneStudySupply.getMilimeterDiameter()).isEqualTo(UPDATED_MILIMETER_DIAMETER);
        assertThat(testOneStudySupply.getSurfaceColor()).isEqualTo(UPDATED_SURFACE_COLOR);
    }

    @Test
    @Transactional
    void putNonExistingOneStudySupply() throws Exception {
        int databaseSizeBeforeUpdate = oneStudySupplyRepository.findAll().size();
        oneStudySupply.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOneStudySupplyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, oneStudySupply.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(oneStudySupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the OneStudySupply in the database
        List<OneStudySupply> oneStudySupplyList = oneStudySupplyRepository.findAll();
        assertThat(oneStudySupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOneStudySupply() throws Exception {
        int databaseSizeBeforeUpdate = oneStudySupplyRepository.findAll().size();
        oneStudySupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOneStudySupplyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(oneStudySupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the OneStudySupply in the database
        List<OneStudySupply> oneStudySupplyList = oneStudySupplyRepository.findAll();
        assertThat(oneStudySupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOneStudySupply() throws Exception {
        int databaseSizeBeforeUpdate = oneStudySupplyRepository.findAll().size();
        oneStudySupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOneStudySupplyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oneStudySupply)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OneStudySupply in the database
        List<OneStudySupply> oneStudySupplyList = oneStudySupplyRepository.findAll();
        assertThat(oneStudySupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOneStudySupplyWithPatch() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        int databaseSizeBeforeUpdate = oneStudySupplyRepository.findAll().size();

        // Update the oneStudySupply using partial update
        OneStudySupply partialUpdatedOneStudySupply = new OneStudySupply();
        partialUpdatedOneStudySupply.setId(oneStudySupply.getId());

        partialUpdatedOneStudySupply.surfaceColor(UPDATED_SURFACE_COLOR);

        restOneStudySupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOneStudySupply.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOneStudySupply))
            )
            .andExpect(status().isOk());

        // Validate the OneStudySupply in the database
        List<OneStudySupply> oneStudySupplyList = oneStudySupplyRepository.findAll();
        assertThat(oneStudySupplyList).hasSize(databaseSizeBeforeUpdate);
        OneStudySupply testOneStudySupply = oneStudySupplyList.get(oneStudySupplyList.size() - 1);
        assertThat(testOneStudySupply.getApparitions()).isEqualTo(DEFAULT_APPARITIONS);
        assertThat(testOneStudySupply.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testOneStudySupply.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testOneStudySupply.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOneStudySupply.getMarkingType()).isEqualTo(DEFAULT_MARKING_TYPE);
        assertThat(testOneStudySupply.getGramPerMeterLinearMass()).isEqualTo(DEFAULT_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testOneStudySupply.getMilimeterDiameter()).isEqualTo(DEFAULT_MILIMETER_DIAMETER);
        assertThat(testOneStudySupply.getSurfaceColor()).isEqualTo(UPDATED_SURFACE_COLOR);
    }

    @Test
    @Transactional
    void fullUpdateOneStudySupplyWithPatch() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        int databaseSizeBeforeUpdate = oneStudySupplyRepository.findAll().size();

        // Update the oneStudySupply using partial update
        OneStudySupply partialUpdatedOneStudySupply = new OneStudySupply();
        partialUpdatedOneStudySupply.setId(oneStudySupply.getId());

        partialUpdatedOneStudySupply
            .apparitions(UPDATED_APPARITIONS)
            .number(UPDATED_NUMBER)
            .designation(UPDATED_DESIGNATION)
            .description(UPDATED_DESCRIPTION)
            .markingType(UPDATED_MARKING_TYPE)
            .gramPerMeterLinearMass(UPDATED_GRAM_PER_METER_LINEAR_MASS)
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER)
            .surfaceColor(UPDATED_SURFACE_COLOR);

        restOneStudySupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOneStudySupply.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOneStudySupply))
            )
            .andExpect(status().isOk());

        // Validate the OneStudySupply in the database
        List<OneStudySupply> oneStudySupplyList = oneStudySupplyRepository.findAll();
        assertThat(oneStudySupplyList).hasSize(databaseSizeBeforeUpdate);
        OneStudySupply testOneStudySupply = oneStudySupplyList.get(oneStudySupplyList.size() - 1);
        assertThat(testOneStudySupply.getApparitions()).isEqualTo(UPDATED_APPARITIONS);
        assertThat(testOneStudySupply.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testOneStudySupply.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testOneStudySupply.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOneStudySupply.getMarkingType()).isEqualTo(UPDATED_MARKING_TYPE);
        assertThat(testOneStudySupply.getGramPerMeterLinearMass()).isEqualTo(UPDATED_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testOneStudySupply.getMilimeterDiameter()).isEqualTo(UPDATED_MILIMETER_DIAMETER);
        assertThat(testOneStudySupply.getSurfaceColor()).isEqualTo(UPDATED_SURFACE_COLOR);
    }

    @Test
    @Transactional
    void patchNonExistingOneStudySupply() throws Exception {
        int databaseSizeBeforeUpdate = oneStudySupplyRepository.findAll().size();
        oneStudySupply.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOneStudySupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, oneStudySupply.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(oneStudySupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the OneStudySupply in the database
        List<OneStudySupply> oneStudySupplyList = oneStudySupplyRepository.findAll();
        assertThat(oneStudySupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOneStudySupply() throws Exception {
        int databaseSizeBeforeUpdate = oneStudySupplyRepository.findAll().size();
        oneStudySupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOneStudySupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(oneStudySupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the OneStudySupply in the database
        List<OneStudySupply> oneStudySupplyList = oneStudySupplyRepository.findAll();
        assertThat(oneStudySupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOneStudySupply() throws Exception {
        int databaseSizeBeforeUpdate = oneStudySupplyRepository.findAll().size();
        oneStudySupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOneStudySupplyMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(oneStudySupply))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OneStudySupply in the database
        List<OneStudySupply> oneStudySupplyList = oneStudySupplyRepository.findAll();
        assertThat(oneStudySupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOneStudySupply() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        int databaseSizeBeforeDelete = oneStudySupplyRepository.findAll().size();

        // Delete the oneStudySupply
        restOneStudySupplyMockMvc
            .perform(delete(ENTITY_API_URL_ID, oneStudySupply.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OneStudySupply> oneStudySupplyList = oneStudySupplyRepository.findAll();
        assertThat(oneStudySupplyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
