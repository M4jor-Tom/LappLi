package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.Bangle;
import com.muller.lappli.repository.BangleRepository;
import com.muller.lappli.service.criteria.BangleCriteria;
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
 * Integration tests for the {@link BangleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BangleResourceIT {

    private static final Long DEFAULT_NUMBER = 1L;
    private static final Long UPDATED_NUMBER = 2L;
    private static final Long SMALLER_NUMBER = 1L - 1L;

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final Double DEFAULT_GRAM_PER_METER_LINEAR_MASS = 1D;
    private static final Double UPDATED_GRAM_PER_METER_LINEAR_MASS = 2D;
    private static final Double SMALLER_GRAM_PER_METER_LINEAR_MASS = 1D - 1D;

    private static final Double DEFAULT_MILIMETER_DIAMETER = 1D;
    private static final Double UPDATED_MILIMETER_DIAMETER = 2D;
    private static final Double SMALLER_MILIMETER_DIAMETER = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/bangles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BangleRepository bangleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBangleMockMvc;

    private Bangle bangle;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bangle createEntity(EntityManager em) {
        Bangle bangle = new Bangle()
            .number(DEFAULT_NUMBER)
            .designation(DEFAULT_DESIGNATION)
            .gramPerMeterLinearMass(DEFAULT_GRAM_PER_METER_LINEAR_MASS)
            .milimeterDiameter(DEFAULT_MILIMETER_DIAMETER);
        return bangle;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bangle createUpdatedEntity(EntityManager em) {
        Bangle bangle = new Bangle()
            .number(UPDATED_NUMBER)
            .designation(UPDATED_DESIGNATION)
            .gramPerMeterLinearMass(UPDATED_GRAM_PER_METER_LINEAR_MASS)
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER);
        return bangle;
    }

    @BeforeEach
    public void initTest() {
        bangle = createEntity(em);
    }

    @Test
    @Transactional
    void createBangle() throws Exception {
        int databaseSizeBeforeCreate = bangleRepository.findAll().size();
        // Create the Bangle
        restBangleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bangle)))
            .andExpect(status().isCreated());

        // Validate the Bangle in the database
        List<Bangle> bangleList = bangleRepository.findAll();
        assertThat(bangleList).hasSize(databaseSizeBeforeCreate + 1);
        Bangle testBangle = bangleList.get(bangleList.size() - 1);
        assertThat(testBangle.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testBangle.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testBangle.getGramPerMeterLinearMass()).isEqualTo(DEFAULT_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testBangle.getMilimeterDiameter()).isEqualTo(DEFAULT_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void createBangleWithExistingId() throws Exception {
        // Create the Bangle with an existing ID
        bangle.setId(1L);

        int databaseSizeBeforeCreate = bangleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBangleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bangle)))
            .andExpect(status().isBadRequest());

        // Validate the Bangle in the database
        List<Bangle> bangleList = bangleRepository.findAll();
        assertThat(bangleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = bangleRepository.findAll().size();
        // set the field null
        bangle.setNumber(null);

        // Create the Bangle, which fails.

        restBangleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bangle)))
            .andExpect(status().isBadRequest());

        List<Bangle> bangleList = bangleRepository.findAll();
        assertThat(bangleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = bangleRepository.findAll().size();
        // set the field null
        bangle.setDesignation(null);

        // Create the Bangle, which fails.

        restBangleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bangle)))
            .andExpect(status().isBadRequest());

        List<Bangle> bangleList = bangleRepository.findAll();
        assertThat(bangleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGramPerMeterLinearMassIsRequired() throws Exception {
        int databaseSizeBeforeTest = bangleRepository.findAll().size();
        // set the field null
        bangle.setGramPerMeterLinearMass(null);

        // Create the Bangle, which fails.

        restBangleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bangle)))
            .andExpect(status().isBadRequest());

        List<Bangle> bangleList = bangleRepository.findAll();
        assertThat(bangleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMilimeterDiameterIsRequired() throws Exception {
        int databaseSizeBeforeTest = bangleRepository.findAll().size();
        // set the field null
        bangle.setMilimeterDiameter(null);

        // Create the Bangle, which fails.

        restBangleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bangle)))
            .andExpect(status().isBadRequest());

        List<Bangle> bangleList = bangleRepository.findAll();
        assertThat(bangleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBangles() throws Exception {
        // Initialize the database
        bangleRepository.saveAndFlush(bangle);

        // Get all the bangleList
        restBangleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bangle.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].gramPerMeterLinearMass").value(hasItem(DEFAULT_GRAM_PER_METER_LINEAR_MASS.doubleValue())))
            .andExpect(jsonPath("$.[*].milimeterDiameter").value(hasItem(DEFAULT_MILIMETER_DIAMETER.doubleValue())));
    }

    @Test
    @Transactional
    void getBangle() throws Exception {
        // Initialize the database
        bangleRepository.saveAndFlush(bangle);

        // Get the bangle
        restBangleMockMvc
            .perform(get(ENTITY_API_URL_ID, bangle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bangle.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.intValue()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.gramPerMeterLinearMass").value(DEFAULT_GRAM_PER_METER_LINEAR_MASS.doubleValue()))
            .andExpect(jsonPath("$.milimeterDiameter").value(DEFAULT_MILIMETER_DIAMETER.doubleValue()));
    }

    @Test
    @Transactional
    void getBanglesByIdFiltering() throws Exception {
        // Initialize the database
        bangleRepository.saveAndFlush(bangle);

        Long id = bangle.getId();

        defaultBangleShouldBeFound("id.equals=" + id);
        defaultBangleShouldNotBeFound("id.notEquals=" + id);

        defaultBangleShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBangleShouldNotBeFound("id.greaterThan=" + id);

        defaultBangleShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBangleShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBanglesByNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        bangleRepository.saveAndFlush(bangle);

        // Get all the bangleList where number equals to DEFAULT_NUMBER
        defaultBangleShouldBeFound("number.equals=" + DEFAULT_NUMBER);

        // Get all the bangleList where number equals to UPDATED_NUMBER
        defaultBangleShouldNotBeFound("number.equals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllBanglesByNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bangleRepository.saveAndFlush(bangle);

        // Get all the bangleList where number not equals to DEFAULT_NUMBER
        defaultBangleShouldNotBeFound("number.notEquals=" + DEFAULT_NUMBER);

        // Get all the bangleList where number not equals to UPDATED_NUMBER
        defaultBangleShouldBeFound("number.notEquals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllBanglesByNumberIsInShouldWork() throws Exception {
        // Initialize the database
        bangleRepository.saveAndFlush(bangle);

        // Get all the bangleList where number in DEFAULT_NUMBER or UPDATED_NUMBER
        defaultBangleShouldBeFound("number.in=" + DEFAULT_NUMBER + "," + UPDATED_NUMBER);

        // Get all the bangleList where number equals to UPDATED_NUMBER
        defaultBangleShouldNotBeFound("number.in=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllBanglesByNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        bangleRepository.saveAndFlush(bangle);

        // Get all the bangleList where number is not null
        defaultBangleShouldBeFound("number.specified=true");

        // Get all the bangleList where number is null
        defaultBangleShouldNotBeFound("number.specified=false");
    }

    @Test
    @Transactional
    void getAllBanglesByNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bangleRepository.saveAndFlush(bangle);

        // Get all the bangleList where number is greater than or equal to DEFAULT_NUMBER
        defaultBangleShouldBeFound("number.greaterThanOrEqual=" + DEFAULT_NUMBER);

        // Get all the bangleList where number is greater than or equal to UPDATED_NUMBER
        defaultBangleShouldNotBeFound("number.greaterThanOrEqual=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllBanglesByNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bangleRepository.saveAndFlush(bangle);

        // Get all the bangleList where number is less than or equal to DEFAULT_NUMBER
        defaultBangleShouldBeFound("number.lessThanOrEqual=" + DEFAULT_NUMBER);

        // Get all the bangleList where number is less than or equal to SMALLER_NUMBER
        defaultBangleShouldNotBeFound("number.lessThanOrEqual=" + SMALLER_NUMBER);
    }

    @Test
    @Transactional
    void getAllBanglesByNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        bangleRepository.saveAndFlush(bangle);

        // Get all the bangleList where number is less than DEFAULT_NUMBER
        defaultBangleShouldNotBeFound("number.lessThan=" + DEFAULT_NUMBER);

        // Get all the bangleList where number is less than UPDATED_NUMBER
        defaultBangleShouldBeFound("number.lessThan=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllBanglesByNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bangleRepository.saveAndFlush(bangle);

        // Get all the bangleList where number is greater than DEFAULT_NUMBER
        defaultBangleShouldNotBeFound("number.greaterThan=" + DEFAULT_NUMBER);

        // Get all the bangleList where number is greater than SMALLER_NUMBER
        defaultBangleShouldBeFound("number.greaterThan=" + SMALLER_NUMBER);
    }

    @Test
    @Transactional
    void getAllBanglesByDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        bangleRepository.saveAndFlush(bangle);

        // Get all the bangleList where designation equals to DEFAULT_DESIGNATION
        defaultBangleShouldBeFound("designation.equals=" + DEFAULT_DESIGNATION);

        // Get all the bangleList where designation equals to UPDATED_DESIGNATION
        defaultBangleShouldNotBeFound("designation.equals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllBanglesByDesignationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bangleRepository.saveAndFlush(bangle);

        // Get all the bangleList where designation not equals to DEFAULT_DESIGNATION
        defaultBangleShouldNotBeFound("designation.notEquals=" + DEFAULT_DESIGNATION);

        // Get all the bangleList where designation not equals to UPDATED_DESIGNATION
        defaultBangleShouldBeFound("designation.notEquals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllBanglesByDesignationIsInShouldWork() throws Exception {
        // Initialize the database
        bangleRepository.saveAndFlush(bangle);

        // Get all the bangleList where designation in DEFAULT_DESIGNATION or UPDATED_DESIGNATION
        defaultBangleShouldBeFound("designation.in=" + DEFAULT_DESIGNATION + "," + UPDATED_DESIGNATION);

        // Get all the bangleList where designation equals to UPDATED_DESIGNATION
        defaultBangleShouldNotBeFound("designation.in=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllBanglesByDesignationIsNullOrNotNull() throws Exception {
        // Initialize the database
        bangleRepository.saveAndFlush(bangle);

        // Get all the bangleList where designation is not null
        defaultBangleShouldBeFound("designation.specified=true");

        // Get all the bangleList where designation is null
        defaultBangleShouldNotBeFound("designation.specified=false");
    }

    @Test
    @Transactional
    void getAllBanglesByDesignationContainsSomething() throws Exception {
        // Initialize the database
        bangleRepository.saveAndFlush(bangle);

        // Get all the bangleList where designation contains DEFAULT_DESIGNATION
        defaultBangleShouldBeFound("designation.contains=" + DEFAULT_DESIGNATION);

        // Get all the bangleList where designation contains UPDATED_DESIGNATION
        defaultBangleShouldNotBeFound("designation.contains=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllBanglesByDesignationNotContainsSomething() throws Exception {
        // Initialize the database
        bangleRepository.saveAndFlush(bangle);

        // Get all the bangleList where designation does not contain DEFAULT_DESIGNATION
        defaultBangleShouldNotBeFound("designation.doesNotContain=" + DEFAULT_DESIGNATION);

        // Get all the bangleList where designation does not contain UPDATED_DESIGNATION
        defaultBangleShouldBeFound("designation.doesNotContain=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllBanglesByGramPerMeterLinearMassIsEqualToSomething() throws Exception {
        // Initialize the database
        bangleRepository.saveAndFlush(bangle);

        // Get all the bangleList where gramPerMeterLinearMass equals to DEFAULT_GRAM_PER_METER_LINEAR_MASS
        defaultBangleShouldBeFound("gramPerMeterLinearMass.equals=" + DEFAULT_GRAM_PER_METER_LINEAR_MASS);

        // Get all the bangleList where gramPerMeterLinearMass equals to UPDATED_GRAM_PER_METER_LINEAR_MASS
        defaultBangleShouldNotBeFound("gramPerMeterLinearMass.equals=" + UPDATED_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllBanglesByGramPerMeterLinearMassIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bangleRepository.saveAndFlush(bangle);

        // Get all the bangleList where gramPerMeterLinearMass not equals to DEFAULT_GRAM_PER_METER_LINEAR_MASS
        defaultBangleShouldNotBeFound("gramPerMeterLinearMass.notEquals=" + DEFAULT_GRAM_PER_METER_LINEAR_MASS);

        // Get all the bangleList where gramPerMeterLinearMass not equals to UPDATED_GRAM_PER_METER_LINEAR_MASS
        defaultBangleShouldBeFound("gramPerMeterLinearMass.notEquals=" + UPDATED_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllBanglesByGramPerMeterLinearMassIsInShouldWork() throws Exception {
        // Initialize the database
        bangleRepository.saveAndFlush(bangle);

        // Get all the bangleList where gramPerMeterLinearMass in DEFAULT_GRAM_PER_METER_LINEAR_MASS or UPDATED_GRAM_PER_METER_LINEAR_MASS
        defaultBangleShouldBeFound(
            "gramPerMeterLinearMass.in=" + DEFAULT_GRAM_PER_METER_LINEAR_MASS + "," + UPDATED_GRAM_PER_METER_LINEAR_MASS
        );

        // Get all the bangleList where gramPerMeterLinearMass equals to UPDATED_GRAM_PER_METER_LINEAR_MASS
        defaultBangleShouldNotBeFound("gramPerMeterLinearMass.in=" + UPDATED_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllBanglesByGramPerMeterLinearMassIsNullOrNotNull() throws Exception {
        // Initialize the database
        bangleRepository.saveAndFlush(bangle);

        // Get all the bangleList where gramPerMeterLinearMass is not null
        defaultBangleShouldBeFound("gramPerMeterLinearMass.specified=true");

        // Get all the bangleList where gramPerMeterLinearMass is null
        defaultBangleShouldNotBeFound("gramPerMeterLinearMass.specified=false");
    }

    @Test
    @Transactional
    void getAllBanglesByGramPerMeterLinearMassIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bangleRepository.saveAndFlush(bangle);

        // Get all the bangleList where gramPerMeterLinearMass is greater than or equal to DEFAULT_GRAM_PER_METER_LINEAR_MASS
        defaultBangleShouldBeFound("gramPerMeterLinearMass.greaterThanOrEqual=" + DEFAULT_GRAM_PER_METER_LINEAR_MASS);

        // Get all the bangleList where gramPerMeterLinearMass is greater than or equal to UPDATED_GRAM_PER_METER_LINEAR_MASS
        defaultBangleShouldNotBeFound("gramPerMeterLinearMass.greaterThanOrEqual=" + UPDATED_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllBanglesByGramPerMeterLinearMassIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bangleRepository.saveAndFlush(bangle);

        // Get all the bangleList where gramPerMeterLinearMass is less than or equal to DEFAULT_GRAM_PER_METER_LINEAR_MASS
        defaultBangleShouldBeFound("gramPerMeterLinearMass.lessThanOrEqual=" + DEFAULT_GRAM_PER_METER_LINEAR_MASS);

        // Get all the bangleList where gramPerMeterLinearMass is less than or equal to SMALLER_GRAM_PER_METER_LINEAR_MASS
        defaultBangleShouldNotBeFound("gramPerMeterLinearMass.lessThanOrEqual=" + SMALLER_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllBanglesByGramPerMeterLinearMassIsLessThanSomething() throws Exception {
        // Initialize the database
        bangleRepository.saveAndFlush(bangle);

        // Get all the bangleList where gramPerMeterLinearMass is less than DEFAULT_GRAM_PER_METER_LINEAR_MASS
        defaultBangleShouldNotBeFound("gramPerMeterLinearMass.lessThan=" + DEFAULT_GRAM_PER_METER_LINEAR_MASS);

        // Get all the bangleList where gramPerMeterLinearMass is less than UPDATED_GRAM_PER_METER_LINEAR_MASS
        defaultBangleShouldBeFound("gramPerMeterLinearMass.lessThan=" + UPDATED_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllBanglesByGramPerMeterLinearMassIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bangleRepository.saveAndFlush(bangle);

        // Get all the bangleList where gramPerMeterLinearMass is greater than DEFAULT_GRAM_PER_METER_LINEAR_MASS
        defaultBangleShouldNotBeFound("gramPerMeterLinearMass.greaterThan=" + DEFAULT_GRAM_PER_METER_LINEAR_MASS);

        // Get all the bangleList where gramPerMeterLinearMass is greater than SMALLER_GRAM_PER_METER_LINEAR_MASS
        defaultBangleShouldBeFound("gramPerMeterLinearMass.greaterThan=" + SMALLER_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllBanglesByMilimeterDiameterIsEqualToSomething() throws Exception {
        // Initialize the database
        bangleRepository.saveAndFlush(bangle);

        // Get all the bangleList where milimeterDiameter equals to DEFAULT_MILIMETER_DIAMETER
        defaultBangleShouldBeFound("milimeterDiameter.equals=" + DEFAULT_MILIMETER_DIAMETER);

        // Get all the bangleList where milimeterDiameter equals to UPDATED_MILIMETER_DIAMETER
        defaultBangleShouldNotBeFound("milimeterDiameter.equals=" + UPDATED_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllBanglesByMilimeterDiameterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bangleRepository.saveAndFlush(bangle);

        // Get all the bangleList where milimeterDiameter not equals to DEFAULT_MILIMETER_DIAMETER
        defaultBangleShouldNotBeFound("milimeterDiameter.notEquals=" + DEFAULT_MILIMETER_DIAMETER);

        // Get all the bangleList where milimeterDiameter not equals to UPDATED_MILIMETER_DIAMETER
        defaultBangleShouldBeFound("milimeterDiameter.notEquals=" + UPDATED_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllBanglesByMilimeterDiameterIsInShouldWork() throws Exception {
        // Initialize the database
        bangleRepository.saveAndFlush(bangle);

        // Get all the bangleList where milimeterDiameter in DEFAULT_MILIMETER_DIAMETER or UPDATED_MILIMETER_DIAMETER
        defaultBangleShouldBeFound("milimeterDiameter.in=" + DEFAULT_MILIMETER_DIAMETER + "," + UPDATED_MILIMETER_DIAMETER);

        // Get all the bangleList where milimeterDiameter equals to UPDATED_MILIMETER_DIAMETER
        defaultBangleShouldNotBeFound("milimeterDiameter.in=" + UPDATED_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllBanglesByMilimeterDiameterIsNullOrNotNull() throws Exception {
        // Initialize the database
        bangleRepository.saveAndFlush(bangle);

        // Get all the bangleList where milimeterDiameter is not null
        defaultBangleShouldBeFound("milimeterDiameter.specified=true");

        // Get all the bangleList where milimeterDiameter is null
        defaultBangleShouldNotBeFound("milimeterDiameter.specified=false");
    }

    @Test
    @Transactional
    void getAllBanglesByMilimeterDiameterIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bangleRepository.saveAndFlush(bangle);

        // Get all the bangleList where milimeterDiameter is greater than or equal to DEFAULT_MILIMETER_DIAMETER
        defaultBangleShouldBeFound("milimeterDiameter.greaterThanOrEqual=" + DEFAULT_MILIMETER_DIAMETER);

        // Get all the bangleList where milimeterDiameter is greater than or equal to UPDATED_MILIMETER_DIAMETER
        defaultBangleShouldNotBeFound("milimeterDiameter.greaterThanOrEqual=" + UPDATED_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllBanglesByMilimeterDiameterIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bangleRepository.saveAndFlush(bangle);

        // Get all the bangleList where milimeterDiameter is less than or equal to DEFAULT_MILIMETER_DIAMETER
        defaultBangleShouldBeFound("milimeterDiameter.lessThanOrEqual=" + DEFAULT_MILIMETER_DIAMETER);

        // Get all the bangleList where milimeterDiameter is less than or equal to SMALLER_MILIMETER_DIAMETER
        defaultBangleShouldNotBeFound("milimeterDiameter.lessThanOrEqual=" + SMALLER_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllBanglesByMilimeterDiameterIsLessThanSomething() throws Exception {
        // Initialize the database
        bangleRepository.saveAndFlush(bangle);

        // Get all the bangleList where milimeterDiameter is less than DEFAULT_MILIMETER_DIAMETER
        defaultBangleShouldNotBeFound("milimeterDiameter.lessThan=" + DEFAULT_MILIMETER_DIAMETER);

        // Get all the bangleList where milimeterDiameter is less than UPDATED_MILIMETER_DIAMETER
        defaultBangleShouldBeFound("milimeterDiameter.lessThan=" + UPDATED_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllBanglesByMilimeterDiameterIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bangleRepository.saveAndFlush(bangle);

        // Get all the bangleList where milimeterDiameter is greater than DEFAULT_MILIMETER_DIAMETER
        defaultBangleShouldNotBeFound("milimeterDiameter.greaterThan=" + DEFAULT_MILIMETER_DIAMETER);

        // Get all the bangleList where milimeterDiameter is greater than SMALLER_MILIMETER_DIAMETER
        defaultBangleShouldBeFound("milimeterDiameter.greaterThan=" + SMALLER_MILIMETER_DIAMETER);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBangleShouldBeFound(String filter) throws Exception {
        restBangleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bangle.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].gramPerMeterLinearMass").value(hasItem(DEFAULT_GRAM_PER_METER_LINEAR_MASS.doubleValue())))
            .andExpect(jsonPath("$.[*].milimeterDiameter").value(hasItem(DEFAULT_MILIMETER_DIAMETER.doubleValue())));

        // Check, that the count call also returns 1
        restBangleMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBangleShouldNotBeFound(String filter) throws Exception {
        restBangleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBangleMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBangle() throws Exception {
        // Get the bangle
        restBangleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBangle() throws Exception {
        // Initialize the database
        bangleRepository.saveAndFlush(bangle);

        int databaseSizeBeforeUpdate = bangleRepository.findAll().size();

        // Update the bangle
        Bangle updatedBangle = bangleRepository.findById(bangle.getId()).get();
        // Disconnect from session so that the updates on updatedBangle are not directly saved in db
        em.detach(updatedBangle);
        updatedBangle
            .number(UPDATED_NUMBER)
            .designation(UPDATED_DESIGNATION)
            .gramPerMeterLinearMass(UPDATED_GRAM_PER_METER_LINEAR_MASS)
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER);

        restBangleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBangle.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBangle))
            )
            .andExpect(status().isOk());

        // Validate the Bangle in the database
        List<Bangle> bangleList = bangleRepository.findAll();
        assertThat(bangleList).hasSize(databaseSizeBeforeUpdate);
        Bangle testBangle = bangleList.get(bangleList.size() - 1);
        assertThat(testBangle.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testBangle.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testBangle.getGramPerMeterLinearMass()).isEqualTo(UPDATED_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testBangle.getMilimeterDiameter()).isEqualTo(UPDATED_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void putNonExistingBangle() throws Exception {
        int databaseSizeBeforeUpdate = bangleRepository.findAll().size();
        bangle.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBangleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bangle.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bangle))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bangle in the database
        List<Bangle> bangleList = bangleRepository.findAll();
        assertThat(bangleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBangle() throws Exception {
        int databaseSizeBeforeUpdate = bangleRepository.findAll().size();
        bangle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBangleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bangle))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bangle in the database
        List<Bangle> bangleList = bangleRepository.findAll();
        assertThat(bangleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBangle() throws Exception {
        int databaseSizeBeforeUpdate = bangleRepository.findAll().size();
        bangle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBangleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bangle)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bangle in the database
        List<Bangle> bangleList = bangleRepository.findAll();
        assertThat(bangleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBangleWithPatch() throws Exception {
        // Initialize the database
        bangleRepository.saveAndFlush(bangle);

        int databaseSizeBeforeUpdate = bangleRepository.findAll().size();

        // Update the bangle using partial update
        Bangle partialUpdatedBangle = new Bangle();
        partialUpdatedBangle.setId(bangle.getId());

        partialUpdatedBangle.number(UPDATED_NUMBER).designation(UPDATED_DESIGNATION).milimeterDiameter(UPDATED_MILIMETER_DIAMETER);

        restBangleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBangle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBangle))
            )
            .andExpect(status().isOk());

        // Validate the Bangle in the database
        List<Bangle> bangleList = bangleRepository.findAll();
        assertThat(bangleList).hasSize(databaseSizeBeforeUpdate);
        Bangle testBangle = bangleList.get(bangleList.size() - 1);
        assertThat(testBangle.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testBangle.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testBangle.getGramPerMeterLinearMass()).isEqualTo(DEFAULT_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testBangle.getMilimeterDiameter()).isEqualTo(UPDATED_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void fullUpdateBangleWithPatch() throws Exception {
        // Initialize the database
        bangleRepository.saveAndFlush(bangle);

        int databaseSizeBeforeUpdate = bangleRepository.findAll().size();

        // Update the bangle using partial update
        Bangle partialUpdatedBangle = new Bangle();
        partialUpdatedBangle.setId(bangle.getId());

        partialUpdatedBangle
            .number(UPDATED_NUMBER)
            .designation(UPDATED_DESIGNATION)
            .gramPerMeterLinearMass(UPDATED_GRAM_PER_METER_LINEAR_MASS)
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER);

        restBangleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBangle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBangle))
            )
            .andExpect(status().isOk());

        // Validate the Bangle in the database
        List<Bangle> bangleList = bangleRepository.findAll();
        assertThat(bangleList).hasSize(databaseSizeBeforeUpdate);
        Bangle testBangle = bangleList.get(bangleList.size() - 1);
        assertThat(testBangle.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testBangle.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testBangle.getGramPerMeterLinearMass()).isEqualTo(UPDATED_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testBangle.getMilimeterDiameter()).isEqualTo(UPDATED_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void patchNonExistingBangle() throws Exception {
        int databaseSizeBeforeUpdate = bangleRepository.findAll().size();
        bangle.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBangleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bangle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bangle))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bangle in the database
        List<Bangle> bangleList = bangleRepository.findAll();
        assertThat(bangleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBangle() throws Exception {
        int databaseSizeBeforeUpdate = bangleRepository.findAll().size();
        bangle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBangleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bangle))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bangle in the database
        List<Bangle> bangleList = bangleRepository.findAll();
        assertThat(bangleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBangle() throws Exception {
        int databaseSizeBeforeUpdate = bangleRepository.findAll().size();
        bangle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBangleMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bangle)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bangle in the database
        List<Bangle> bangleList = bangleRepository.findAll();
        assertThat(bangleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBangle() throws Exception {
        // Initialize the database
        bangleRepository.saveAndFlush(bangle);

        int databaseSizeBeforeDelete = bangleRepository.findAll().size();

        // Delete the bangle
        restBangleMockMvc
            .perform(delete(ENTITY_API_URL_ID, bangle.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bangle> bangleList = bangleRepository.findAll();
        assertThat(bangleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
