package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.Lifter;
import com.muller.lappli.domain.LifterRunMeasure;
import com.muller.lappli.domain.enumeration.MarkingTechnique;
import com.muller.lappli.domain.enumeration.MarkingType;
import com.muller.lappli.repository.LifterRunMeasureRepository;
import com.muller.lappli.service.criteria.LifterRunMeasureCriteria;
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
 * Integration tests for the {@link LifterRunMeasureResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LifterRunMeasureResourceIT {

    private static final Double DEFAULT_MILIMETER_DIAMETER = 1D;
    private static final Double UPDATED_MILIMETER_DIAMETER = 2D;
    private static final Double SMALLER_MILIMETER_DIAMETER = 1D - 1D;

    private static final Double DEFAULT_METER_PER_SECOND_SPEED = 1D;
    private static final Double UPDATED_METER_PER_SECOND_SPEED = 2D;
    private static final Double SMALLER_METER_PER_SECOND_SPEED = 1D - 1D;

    private static final MarkingType DEFAULT_MARKING_TYPE = MarkingType.LIFTING;
    private static final MarkingType UPDATED_MARKING_TYPE = MarkingType.SPIRALLY_COLORED;

    private static final MarkingTechnique DEFAULT_MARKING_TECHNIQUE = MarkingTechnique.NONE;
    private static final MarkingTechnique UPDATED_MARKING_TECHNIQUE = MarkingTechnique.NONE_SUITABLE;

    private static final Double DEFAULT_FORMATED_HOUR_PREPARATION_TIME = 1D;
    private static final Double UPDATED_FORMATED_HOUR_PREPARATION_TIME = 2D;
    private static final Double SMALLER_FORMATED_HOUR_PREPARATION_TIME = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/lifter-run-measures";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LifterRunMeasureRepository lifterRunMeasureRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLifterRunMeasureMockMvc;

    private LifterRunMeasure lifterRunMeasure;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LifterRunMeasure createEntity(EntityManager em) {
        LifterRunMeasure lifterRunMeasure = new LifterRunMeasure()
            .milimeterDiameter(DEFAULT_MILIMETER_DIAMETER)
            .meterPerSecondSpeed(DEFAULT_METER_PER_SECOND_SPEED)
            .markingType(DEFAULT_MARKING_TYPE)
            .markingTechnique(DEFAULT_MARKING_TECHNIQUE)
            .formatedHourPreparationTime(DEFAULT_FORMATED_HOUR_PREPARATION_TIME);
        // Add required entity
        Lifter lifter;
        if (TestUtil.findAll(em, Lifter.class).isEmpty()) {
            lifter = LifterResourceIT.createEntity(em);
            em.persist(lifter);
            em.flush();
        } else {
            lifter = TestUtil.findAll(em, Lifter.class).get(0);
        }
        lifterRunMeasure.setLifter(lifter);
        return lifterRunMeasure;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LifterRunMeasure createUpdatedEntity(EntityManager em) {
        LifterRunMeasure lifterRunMeasure = new LifterRunMeasure()
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER)
            .meterPerSecondSpeed(UPDATED_METER_PER_SECOND_SPEED)
            .markingType(UPDATED_MARKING_TYPE)
            .markingTechnique(UPDATED_MARKING_TECHNIQUE)
            .formatedHourPreparationTime(UPDATED_FORMATED_HOUR_PREPARATION_TIME);
        // Add required entity
        Lifter lifter;
        if (TestUtil.findAll(em, Lifter.class).isEmpty()) {
            lifter = LifterResourceIT.createUpdatedEntity(em);
            em.persist(lifter);
            em.flush();
        } else {
            lifter = TestUtil.findAll(em, Lifter.class).get(0);
        }
        lifterRunMeasure.setLifter(lifter);
        return lifterRunMeasure;
    }

    @BeforeEach
    public void initTest() {
        lifterRunMeasure = createEntity(em);
    }

    @Test
    @Transactional
    void createLifterRunMeasure() throws Exception {
        int databaseSizeBeforeCreate = lifterRunMeasureRepository.findAll().size();
        // Create the LifterRunMeasure
        restLifterRunMeasureMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lifterRunMeasure))
            )
            .andExpect(status().isCreated());

        // Validate the LifterRunMeasure in the database
        List<LifterRunMeasure> lifterRunMeasureList = lifterRunMeasureRepository.findAll();
        assertThat(lifterRunMeasureList).hasSize(databaseSizeBeforeCreate + 1);
        LifterRunMeasure testLifterRunMeasure = lifterRunMeasureList.get(lifterRunMeasureList.size() - 1);
        assertThat(testLifterRunMeasure.getMilimeterDiameter()).isEqualTo(DEFAULT_MILIMETER_DIAMETER);
        assertThat(testLifterRunMeasure.getMeterPerSecondSpeed()).isEqualTo(DEFAULT_METER_PER_SECOND_SPEED);
        assertThat(testLifterRunMeasure.getMarkingType()).isEqualTo(DEFAULT_MARKING_TYPE);
        assertThat(testLifterRunMeasure.getMarkingTechnique()).isEqualTo(DEFAULT_MARKING_TECHNIQUE);
        assertThat(testLifterRunMeasure.getFormatedHourPreparationTime()).isEqualTo(DEFAULT_FORMATED_HOUR_PREPARATION_TIME);
    }

    @Test
    @Transactional
    void createLifterRunMeasureWithExistingId() throws Exception {
        // Create the LifterRunMeasure with an existing ID
        lifterRunMeasure.setId(1L);

        int databaseSizeBeforeCreate = lifterRunMeasureRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLifterRunMeasureMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lifterRunMeasure))
            )
            .andExpect(status().isBadRequest());

        // Validate the LifterRunMeasure in the database
        List<LifterRunMeasure> lifterRunMeasureList = lifterRunMeasureRepository.findAll();
        assertThat(lifterRunMeasureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMarkingTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = lifterRunMeasureRepository.findAll().size();
        // set the field null
        lifterRunMeasure.setMarkingType(null);

        // Create the LifterRunMeasure, which fails.

        restLifterRunMeasureMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lifterRunMeasure))
            )
            .andExpect(status().isBadRequest());

        List<LifterRunMeasure> lifterRunMeasureList = lifterRunMeasureRepository.findAll();
        assertThat(lifterRunMeasureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMarkingTechniqueIsRequired() throws Exception {
        int databaseSizeBeforeTest = lifterRunMeasureRepository.findAll().size();
        // set the field null
        lifterRunMeasure.setMarkingTechnique(null);

        // Create the LifterRunMeasure, which fails.

        restLifterRunMeasureMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lifterRunMeasure))
            )
            .andExpect(status().isBadRequest());

        List<LifterRunMeasure> lifterRunMeasureList = lifterRunMeasureRepository.findAll();
        assertThat(lifterRunMeasureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLifterRunMeasures() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        // Get all the lifterRunMeasureList
        restLifterRunMeasureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lifterRunMeasure.getId().intValue())))
            .andExpect(jsonPath("$.[*].milimeterDiameter").value(hasItem(DEFAULT_MILIMETER_DIAMETER.doubleValue())))
            .andExpect(jsonPath("$.[*].meterPerSecondSpeed").value(hasItem(DEFAULT_METER_PER_SECOND_SPEED.doubleValue())))
            .andExpect(jsonPath("$.[*].markingType").value(hasItem(DEFAULT_MARKING_TYPE.toString())))
            .andExpect(jsonPath("$.[*].markingTechnique").value(hasItem(DEFAULT_MARKING_TECHNIQUE.toString())))
            .andExpect(jsonPath("$.[*].formatedHourPreparationTime").value(hasItem(DEFAULT_FORMATED_HOUR_PREPARATION_TIME.doubleValue())));
    }

    @Test
    @Transactional
    void getLifterRunMeasure() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        // Get the lifterRunMeasure
        restLifterRunMeasureMockMvc
            .perform(get(ENTITY_API_URL_ID, lifterRunMeasure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lifterRunMeasure.getId().intValue()))
            .andExpect(jsonPath("$.milimeterDiameter").value(DEFAULT_MILIMETER_DIAMETER.doubleValue()))
            .andExpect(jsonPath("$.meterPerSecondSpeed").value(DEFAULT_METER_PER_SECOND_SPEED.doubleValue()))
            .andExpect(jsonPath("$.markingType").value(DEFAULT_MARKING_TYPE.toString()))
            .andExpect(jsonPath("$.markingTechnique").value(DEFAULT_MARKING_TECHNIQUE.toString()))
            .andExpect(jsonPath("$.formatedHourPreparationTime").value(DEFAULT_FORMATED_HOUR_PREPARATION_TIME.doubleValue()));
    }

    @Test
    @Transactional
    void getLifterRunMeasuresByIdFiltering() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        Long id = lifterRunMeasure.getId();

        defaultLifterRunMeasureShouldBeFound("id.equals=" + id);
        defaultLifterRunMeasureShouldNotBeFound("id.notEquals=" + id);

        defaultLifterRunMeasureShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLifterRunMeasureShouldNotBeFound("id.greaterThan=" + id);

        defaultLifterRunMeasureShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLifterRunMeasureShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLifterRunMeasuresByMilimeterDiameterIsEqualToSomething() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        // Get all the lifterRunMeasureList where milimeterDiameter equals to DEFAULT_MILIMETER_DIAMETER
        defaultLifterRunMeasureShouldBeFound("milimeterDiameter.equals=" + DEFAULT_MILIMETER_DIAMETER);

        // Get all the lifterRunMeasureList where milimeterDiameter equals to UPDATED_MILIMETER_DIAMETER
        defaultLifterRunMeasureShouldNotBeFound("milimeterDiameter.equals=" + UPDATED_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllLifterRunMeasuresByMilimeterDiameterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        // Get all the lifterRunMeasureList where milimeterDiameter not equals to DEFAULT_MILIMETER_DIAMETER
        defaultLifterRunMeasureShouldNotBeFound("milimeterDiameter.notEquals=" + DEFAULT_MILIMETER_DIAMETER);

        // Get all the lifterRunMeasureList where milimeterDiameter not equals to UPDATED_MILIMETER_DIAMETER
        defaultLifterRunMeasureShouldBeFound("milimeterDiameter.notEquals=" + UPDATED_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllLifterRunMeasuresByMilimeterDiameterIsInShouldWork() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        // Get all the lifterRunMeasureList where milimeterDiameter in DEFAULT_MILIMETER_DIAMETER or UPDATED_MILIMETER_DIAMETER
        defaultLifterRunMeasureShouldBeFound("milimeterDiameter.in=" + DEFAULT_MILIMETER_DIAMETER + "," + UPDATED_MILIMETER_DIAMETER);

        // Get all the lifterRunMeasureList where milimeterDiameter equals to UPDATED_MILIMETER_DIAMETER
        defaultLifterRunMeasureShouldNotBeFound("milimeterDiameter.in=" + UPDATED_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllLifterRunMeasuresByMilimeterDiameterIsNullOrNotNull() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        // Get all the lifterRunMeasureList where milimeterDiameter is not null
        defaultLifterRunMeasureShouldBeFound("milimeterDiameter.specified=true");

        // Get all the lifterRunMeasureList where milimeterDiameter is null
        defaultLifterRunMeasureShouldNotBeFound("milimeterDiameter.specified=false");
    }

    @Test
    @Transactional
    void getAllLifterRunMeasuresByMilimeterDiameterIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        // Get all the lifterRunMeasureList where milimeterDiameter is greater than or equal to DEFAULT_MILIMETER_DIAMETER
        defaultLifterRunMeasureShouldBeFound("milimeterDiameter.greaterThanOrEqual=" + DEFAULT_MILIMETER_DIAMETER);

        // Get all the lifterRunMeasureList where milimeterDiameter is greater than or equal to UPDATED_MILIMETER_DIAMETER
        defaultLifterRunMeasureShouldNotBeFound("milimeterDiameter.greaterThanOrEqual=" + UPDATED_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllLifterRunMeasuresByMilimeterDiameterIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        // Get all the lifterRunMeasureList where milimeterDiameter is less than or equal to DEFAULT_MILIMETER_DIAMETER
        defaultLifterRunMeasureShouldBeFound("milimeterDiameter.lessThanOrEqual=" + DEFAULT_MILIMETER_DIAMETER);

        // Get all the lifterRunMeasureList where milimeterDiameter is less than or equal to SMALLER_MILIMETER_DIAMETER
        defaultLifterRunMeasureShouldNotBeFound("milimeterDiameter.lessThanOrEqual=" + SMALLER_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllLifterRunMeasuresByMilimeterDiameterIsLessThanSomething() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        // Get all the lifterRunMeasureList where milimeterDiameter is less than DEFAULT_MILIMETER_DIAMETER
        defaultLifterRunMeasureShouldNotBeFound("milimeterDiameter.lessThan=" + DEFAULT_MILIMETER_DIAMETER);

        // Get all the lifterRunMeasureList where milimeterDiameter is less than UPDATED_MILIMETER_DIAMETER
        defaultLifterRunMeasureShouldBeFound("milimeterDiameter.lessThan=" + UPDATED_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllLifterRunMeasuresByMilimeterDiameterIsGreaterThanSomething() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        // Get all the lifterRunMeasureList where milimeterDiameter is greater than DEFAULT_MILIMETER_DIAMETER
        defaultLifterRunMeasureShouldNotBeFound("milimeterDiameter.greaterThan=" + DEFAULT_MILIMETER_DIAMETER);

        // Get all the lifterRunMeasureList where milimeterDiameter is greater than SMALLER_MILIMETER_DIAMETER
        defaultLifterRunMeasureShouldBeFound("milimeterDiameter.greaterThan=" + SMALLER_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllLifterRunMeasuresByMeterPerSecondSpeedIsEqualToSomething() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        // Get all the lifterRunMeasureList where meterPerSecondSpeed equals to DEFAULT_METER_PER_SECOND_SPEED
        defaultLifterRunMeasureShouldBeFound("meterPerSecondSpeed.equals=" + DEFAULT_METER_PER_SECOND_SPEED);

        // Get all the lifterRunMeasureList where meterPerSecondSpeed equals to UPDATED_METER_PER_SECOND_SPEED
        defaultLifterRunMeasureShouldNotBeFound("meterPerSecondSpeed.equals=" + UPDATED_METER_PER_SECOND_SPEED);
    }

    @Test
    @Transactional
    void getAllLifterRunMeasuresByMeterPerSecondSpeedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        // Get all the lifterRunMeasureList where meterPerSecondSpeed not equals to DEFAULT_METER_PER_SECOND_SPEED
        defaultLifterRunMeasureShouldNotBeFound("meterPerSecondSpeed.notEquals=" + DEFAULT_METER_PER_SECOND_SPEED);

        // Get all the lifterRunMeasureList where meterPerSecondSpeed not equals to UPDATED_METER_PER_SECOND_SPEED
        defaultLifterRunMeasureShouldBeFound("meterPerSecondSpeed.notEquals=" + UPDATED_METER_PER_SECOND_SPEED);
    }

    @Test
    @Transactional
    void getAllLifterRunMeasuresByMeterPerSecondSpeedIsInShouldWork() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        // Get all the lifterRunMeasureList where meterPerSecondSpeed in DEFAULT_METER_PER_SECOND_SPEED or UPDATED_METER_PER_SECOND_SPEED
        defaultLifterRunMeasureShouldBeFound(
            "meterPerSecondSpeed.in=" + DEFAULT_METER_PER_SECOND_SPEED + "," + UPDATED_METER_PER_SECOND_SPEED
        );

        // Get all the lifterRunMeasureList where meterPerSecondSpeed equals to UPDATED_METER_PER_SECOND_SPEED
        defaultLifterRunMeasureShouldNotBeFound("meterPerSecondSpeed.in=" + UPDATED_METER_PER_SECOND_SPEED);
    }

    @Test
    @Transactional
    void getAllLifterRunMeasuresByMeterPerSecondSpeedIsNullOrNotNull() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        // Get all the lifterRunMeasureList where meterPerSecondSpeed is not null
        defaultLifterRunMeasureShouldBeFound("meterPerSecondSpeed.specified=true");

        // Get all the lifterRunMeasureList where meterPerSecondSpeed is null
        defaultLifterRunMeasureShouldNotBeFound("meterPerSecondSpeed.specified=false");
    }

    @Test
    @Transactional
    void getAllLifterRunMeasuresByMeterPerSecondSpeedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        // Get all the lifterRunMeasureList where meterPerSecondSpeed is greater than or equal to DEFAULT_METER_PER_SECOND_SPEED
        defaultLifterRunMeasureShouldBeFound("meterPerSecondSpeed.greaterThanOrEqual=" + DEFAULT_METER_PER_SECOND_SPEED);

        // Get all the lifterRunMeasureList where meterPerSecondSpeed is greater than or equal to UPDATED_METER_PER_SECOND_SPEED
        defaultLifterRunMeasureShouldNotBeFound("meterPerSecondSpeed.greaterThanOrEqual=" + UPDATED_METER_PER_SECOND_SPEED);
    }

    @Test
    @Transactional
    void getAllLifterRunMeasuresByMeterPerSecondSpeedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        // Get all the lifterRunMeasureList where meterPerSecondSpeed is less than or equal to DEFAULT_METER_PER_SECOND_SPEED
        defaultLifterRunMeasureShouldBeFound("meterPerSecondSpeed.lessThanOrEqual=" + DEFAULT_METER_PER_SECOND_SPEED);

        // Get all the lifterRunMeasureList where meterPerSecondSpeed is less than or equal to SMALLER_METER_PER_SECOND_SPEED
        defaultLifterRunMeasureShouldNotBeFound("meterPerSecondSpeed.lessThanOrEqual=" + SMALLER_METER_PER_SECOND_SPEED);
    }

    @Test
    @Transactional
    void getAllLifterRunMeasuresByMeterPerSecondSpeedIsLessThanSomething() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        // Get all the lifterRunMeasureList where meterPerSecondSpeed is less than DEFAULT_METER_PER_SECOND_SPEED
        defaultLifterRunMeasureShouldNotBeFound("meterPerSecondSpeed.lessThan=" + DEFAULT_METER_PER_SECOND_SPEED);

        // Get all the lifterRunMeasureList where meterPerSecondSpeed is less than UPDATED_METER_PER_SECOND_SPEED
        defaultLifterRunMeasureShouldBeFound("meterPerSecondSpeed.lessThan=" + UPDATED_METER_PER_SECOND_SPEED);
    }

    @Test
    @Transactional
    void getAllLifterRunMeasuresByMeterPerSecondSpeedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        // Get all the lifterRunMeasureList where meterPerSecondSpeed is greater than DEFAULT_METER_PER_SECOND_SPEED
        defaultLifterRunMeasureShouldNotBeFound("meterPerSecondSpeed.greaterThan=" + DEFAULT_METER_PER_SECOND_SPEED);

        // Get all the lifterRunMeasureList where meterPerSecondSpeed is greater than SMALLER_METER_PER_SECOND_SPEED
        defaultLifterRunMeasureShouldBeFound("meterPerSecondSpeed.greaterThan=" + SMALLER_METER_PER_SECOND_SPEED);
    }

    @Test
    @Transactional
    void getAllLifterRunMeasuresByMarkingTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        // Get all the lifterRunMeasureList where markingType equals to DEFAULT_MARKING_TYPE
        defaultLifterRunMeasureShouldBeFound("markingType.equals=" + DEFAULT_MARKING_TYPE);

        // Get all the lifterRunMeasureList where markingType equals to UPDATED_MARKING_TYPE
        defaultLifterRunMeasureShouldNotBeFound("markingType.equals=" + UPDATED_MARKING_TYPE);
    }

    @Test
    @Transactional
    void getAllLifterRunMeasuresByMarkingTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        // Get all the lifterRunMeasureList where markingType not equals to DEFAULT_MARKING_TYPE
        defaultLifterRunMeasureShouldNotBeFound("markingType.notEquals=" + DEFAULT_MARKING_TYPE);

        // Get all the lifterRunMeasureList where markingType not equals to UPDATED_MARKING_TYPE
        defaultLifterRunMeasureShouldBeFound("markingType.notEquals=" + UPDATED_MARKING_TYPE);
    }

    @Test
    @Transactional
    void getAllLifterRunMeasuresByMarkingTypeIsInShouldWork() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        // Get all the lifterRunMeasureList where markingType in DEFAULT_MARKING_TYPE or UPDATED_MARKING_TYPE
        defaultLifterRunMeasureShouldBeFound("markingType.in=" + DEFAULT_MARKING_TYPE + "," + UPDATED_MARKING_TYPE);

        // Get all the lifterRunMeasureList where markingType equals to UPDATED_MARKING_TYPE
        defaultLifterRunMeasureShouldNotBeFound("markingType.in=" + UPDATED_MARKING_TYPE);
    }

    @Test
    @Transactional
    void getAllLifterRunMeasuresByMarkingTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        // Get all the lifterRunMeasureList where markingType is not null
        defaultLifterRunMeasureShouldBeFound("markingType.specified=true");

        // Get all the lifterRunMeasureList where markingType is null
        defaultLifterRunMeasureShouldNotBeFound("markingType.specified=false");
    }

    @Test
    @Transactional
    void getAllLifterRunMeasuresByMarkingTechniqueIsEqualToSomething() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        // Get all the lifterRunMeasureList where markingTechnique equals to DEFAULT_MARKING_TECHNIQUE
        defaultLifterRunMeasureShouldBeFound("markingTechnique.equals=" + DEFAULT_MARKING_TECHNIQUE);

        // Get all the lifterRunMeasureList where markingTechnique equals to UPDATED_MARKING_TECHNIQUE
        defaultLifterRunMeasureShouldNotBeFound("markingTechnique.equals=" + UPDATED_MARKING_TECHNIQUE);
    }

    @Test
    @Transactional
    void getAllLifterRunMeasuresByMarkingTechniqueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        // Get all the lifterRunMeasureList where markingTechnique not equals to DEFAULT_MARKING_TECHNIQUE
        defaultLifterRunMeasureShouldNotBeFound("markingTechnique.notEquals=" + DEFAULT_MARKING_TECHNIQUE);

        // Get all the lifterRunMeasureList where markingTechnique not equals to UPDATED_MARKING_TECHNIQUE
        defaultLifterRunMeasureShouldBeFound("markingTechnique.notEquals=" + UPDATED_MARKING_TECHNIQUE);
    }

    @Test
    @Transactional
    void getAllLifterRunMeasuresByMarkingTechniqueIsInShouldWork() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        // Get all the lifterRunMeasureList where markingTechnique in DEFAULT_MARKING_TECHNIQUE or UPDATED_MARKING_TECHNIQUE
        defaultLifterRunMeasureShouldBeFound("markingTechnique.in=" + DEFAULT_MARKING_TECHNIQUE + "," + UPDATED_MARKING_TECHNIQUE);

        // Get all the lifterRunMeasureList where markingTechnique equals to UPDATED_MARKING_TECHNIQUE
        defaultLifterRunMeasureShouldNotBeFound("markingTechnique.in=" + UPDATED_MARKING_TECHNIQUE);
    }

    @Test
    @Transactional
    void getAllLifterRunMeasuresByMarkingTechniqueIsNullOrNotNull() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        // Get all the lifterRunMeasureList where markingTechnique is not null
        defaultLifterRunMeasureShouldBeFound("markingTechnique.specified=true");

        // Get all the lifterRunMeasureList where markingTechnique is null
        defaultLifterRunMeasureShouldNotBeFound("markingTechnique.specified=false");
    }

    @Test
    @Transactional
    void getAllLifterRunMeasuresByFormatedHourPreparationTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        // Get all the lifterRunMeasureList where formatedHourPreparationTime equals to DEFAULT_FORMATED_HOUR_PREPARATION_TIME
        defaultLifterRunMeasureShouldBeFound("formatedHourPreparationTime.equals=" + DEFAULT_FORMATED_HOUR_PREPARATION_TIME);

        // Get all the lifterRunMeasureList where formatedHourPreparationTime equals to UPDATED_FORMATED_HOUR_PREPARATION_TIME
        defaultLifterRunMeasureShouldNotBeFound("formatedHourPreparationTime.equals=" + UPDATED_FORMATED_HOUR_PREPARATION_TIME);
    }

    @Test
    @Transactional
    void getAllLifterRunMeasuresByFormatedHourPreparationTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        // Get all the lifterRunMeasureList where formatedHourPreparationTime not equals to DEFAULT_FORMATED_HOUR_PREPARATION_TIME
        defaultLifterRunMeasureShouldNotBeFound("formatedHourPreparationTime.notEquals=" + DEFAULT_FORMATED_HOUR_PREPARATION_TIME);

        // Get all the lifterRunMeasureList where formatedHourPreparationTime not equals to UPDATED_FORMATED_HOUR_PREPARATION_TIME
        defaultLifterRunMeasureShouldBeFound("formatedHourPreparationTime.notEquals=" + UPDATED_FORMATED_HOUR_PREPARATION_TIME);
    }

    @Test
    @Transactional
    void getAllLifterRunMeasuresByFormatedHourPreparationTimeIsInShouldWork() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        // Get all the lifterRunMeasureList where formatedHourPreparationTime in DEFAULT_FORMATED_HOUR_PREPARATION_TIME or UPDATED_FORMATED_HOUR_PREPARATION_TIME
        defaultLifterRunMeasureShouldBeFound(
            "formatedHourPreparationTime.in=" + DEFAULT_FORMATED_HOUR_PREPARATION_TIME + "," + UPDATED_FORMATED_HOUR_PREPARATION_TIME
        );

        // Get all the lifterRunMeasureList where formatedHourPreparationTime equals to UPDATED_FORMATED_HOUR_PREPARATION_TIME
        defaultLifterRunMeasureShouldNotBeFound("formatedHourPreparationTime.in=" + UPDATED_FORMATED_HOUR_PREPARATION_TIME);
    }

    @Test
    @Transactional
    void getAllLifterRunMeasuresByFormatedHourPreparationTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        // Get all the lifterRunMeasureList where formatedHourPreparationTime is not null
        defaultLifterRunMeasureShouldBeFound("formatedHourPreparationTime.specified=true");

        // Get all the lifterRunMeasureList where formatedHourPreparationTime is null
        defaultLifterRunMeasureShouldNotBeFound("formatedHourPreparationTime.specified=false");
    }

    @Test
    @Transactional
    void getAllLifterRunMeasuresByFormatedHourPreparationTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        // Get all the lifterRunMeasureList where formatedHourPreparationTime is greater than or equal to DEFAULT_FORMATED_HOUR_PREPARATION_TIME
        defaultLifterRunMeasureShouldBeFound("formatedHourPreparationTime.greaterThanOrEqual=" + DEFAULT_FORMATED_HOUR_PREPARATION_TIME);

        // Get all the lifterRunMeasureList where formatedHourPreparationTime is greater than or equal to UPDATED_FORMATED_HOUR_PREPARATION_TIME
        defaultLifterRunMeasureShouldNotBeFound("formatedHourPreparationTime.greaterThanOrEqual=" + UPDATED_FORMATED_HOUR_PREPARATION_TIME);
    }

    @Test
    @Transactional
    void getAllLifterRunMeasuresByFormatedHourPreparationTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        // Get all the lifterRunMeasureList where formatedHourPreparationTime is less than or equal to DEFAULT_FORMATED_HOUR_PREPARATION_TIME
        defaultLifterRunMeasureShouldBeFound("formatedHourPreparationTime.lessThanOrEqual=" + DEFAULT_FORMATED_HOUR_PREPARATION_TIME);

        // Get all the lifterRunMeasureList where formatedHourPreparationTime is less than or equal to SMALLER_FORMATED_HOUR_PREPARATION_TIME
        defaultLifterRunMeasureShouldNotBeFound("formatedHourPreparationTime.lessThanOrEqual=" + SMALLER_FORMATED_HOUR_PREPARATION_TIME);
    }

    @Test
    @Transactional
    void getAllLifterRunMeasuresByFormatedHourPreparationTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        // Get all the lifterRunMeasureList where formatedHourPreparationTime is less than DEFAULT_FORMATED_HOUR_PREPARATION_TIME
        defaultLifterRunMeasureShouldNotBeFound("formatedHourPreparationTime.lessThan=" + DEFAULT_FORMATED_HOUR_PREPARATION_TIME);

        // Get all the lifterRunMeasureList where formatedHourPreparationTime is less than UPDATED_FORMATED_HOUR_PREPARATION_TIME
        defaultLifterRunMeasureShouldBeFound("formatedHourPreparationTime.lessThan=" + UPDATED_FORMATED_HOUR_PREPARATION_TIME);
    }

    @Test
    @Transactional
    void getAllLifterRunMeasuresByFormatedHourPreparationTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        // Get all the lifterRunMeasureList where formatedHourPreparationTime is greater than DEFAULT_FORMATED_HOUR_PREPARATION_TIME
        defaultLifterRunMeasureShouldNotBeFound("formatedHourPreparationTime.greaterThan=" + DEFAULT_FORMATED_HOUR_PREPARATION_TIME);

        // Get all the lifterRunMeasureList where formatedHourPreparationTime is greater than SMALLER_FORMATED_HOUR_PREPARATION_TIME
        defaultLifterRunMeasureShouldBeFound("formatedHourPreparationTime.greaterThan=" + SMALLER_FORMATED_HOUR_PREPARATION_TIME);
    }

    @Test
    @Transactional
    void getAllLifterRunMeasuresByLifterIsEqualToSomething() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);
        Lifter lifter;
        if (TestUtil.findAll(em, Lifter.class).isEmpty()) {
            lifter = LifterResourceIT.createEntity(em);
            em.persist(lifter);
            em.flush();
        } else {
            lifter = TestUtil.findAll(em, Lifter.class).get(0);
        }
        em.persist(lifter);
        em.flush();
        lifterRunMeasure.setLifter(lifter);
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);
        Long lifterId = lifter.getId();

        // Get all the lifterRunMeasureList where lifter equals to lifterId
        defaultLifterRunMeasureShouldBeFound("lifterId.equals=" + lifterId);

        // Get all the lifterRunMeasureList where lifter equals to (lifterId + 1)
        defaultLifterRunMeasureShouldNotBeFound("lifterId.equals=" + (lifterId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLifterRunMeasureShouldBeFound(String filter) throws Exception {
        restLifterRunMeasureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lifterRunMeasure.getId().intValue())))
            .andExpect(jsonPath("$.[*].milimeterDiameter").value(hasItem(DEFAULT_MILIMETER_DIAMETER.doubleValue())))
            .andExpect(jsonPath("$.[*].meterPerSecondSpeed").value(hasItem(DEFAULT_METER_PER_SECOND_SPEED.doubleValue())))
            .andExpect(jsonPath("$.[*].markingType").value(hasItem(DEFAULT_MARKING_TYPE.toString())))
            .andExpect(jsonPath("$.[*].markingTechnique").value(hasItem(DEFAULT_MARKING_TECHNIQUE.toString())))
            .andExpect(jsonPath("$.[*].formatedHourPreparationTime").value(hasItem(DEFAULT_FORMATED_HOUR_PREPARATION_TIME.doubleValue())));

        // Check, that the count call also returns 1
        restLifterRunMeasureMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLifterRunMeasureShouldNotBeFound(String filter) throws Exception {
        restLifterRunMeasureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLifterRunMeasureMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLifterRunMeasure() throws Exception {
        // Get the lifterRunMeasure
        restLifterRunMeasureMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLifterRunMeasure() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        int databaseSizeBeforeUpdate = lifterRunMeasureRepository.findAll().size();

        // Update the lifterRunMeasure
        LifterRunMeasure updatedLifterRunMeasure = lifterRunMeasureRepository.findById(lifterRunMeasure.getId()).get();
        // Disconnect from session so that the updates on updatedLifterRunMeasure are not directly saved in db
        em.detach(updatedLifterRunMeasure);
        updatedLifterRunMeasure
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER)
            .meterPerSecondSpeed(UPDATED_METER_PER_SECOND_SPEED)
            .markingType(UPDATED_MARKING_TYPE)
            .markingTechnique(UPDATED_MARKING_TECHNIQUE)
            .formatedHourPreparationTime(UPDATED_FORMATED_HOUR_PREPARATION_TIME);

        restLifterRunMeasureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLifterRunMeasure.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLifterRunMeasure))
            )
            .andExpect(status().isOk());

        // Validate the LifterRunMeasure in the database
        List<LifterRunMeasure> lifterRunMeasureList = lifterRunMeasureRepository.findAll();
        assertThat(lifterRunMeasureList).hasSize(databaseSizeBeforeUpdate);
        LifterRunMeasure testLifterRunMeasure = lifterRunMeasureList.get(lifterRunMeasureList.size() - 1);
        assertThat(testLifterRunMeasure.getMilimeterDiameter()).isEqualTo(UPDATED_MILIMETER_DIAMETER);
        assertThat(testLifterRunMeasure.getMeterPerSecondSpeed()).isEqualTo(UPDATED_METER_PER_SECOND_SPEED);
        assertThat(testLifterRunMeasure.getMarkingType()).isEqualTo(UPDATED_MARKING_TYPE);
        assertThat(testLifterRunMeasure.getMarkingTechnique()).isEqualTo(UPDATED_MARKING_TECHNIQUE);
        assertThat(testLifterRunMeasure.getFormatedHourPreparationTime()).isEqualTo(UPDATED_FORMATED_HOUR_PREPARATION_TIME);
    }

    @Test
    @Transactional
    void putNonExistingLifterRunMeasure() throws Exception {
        int databaseSizeBeforeUpdate = lifterRunMeasureRepository.findAll().size();
        lifterRunMeasure.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLifterRunMeasureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, lifterRunMeasure.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lifterRunMeasure))
            )
            .andExpect(status().isBadRequest());

        // Validate the LifterRunMeasure in the database
        List<LifterRunMeasure> lifterRunMeasureList = lifterRunMeasureRepository.findAll();
        assertThat(lifterRunMeasureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLifterRunMeasure() throws Exception {
        int databaseSizeBeforeUpdate = lifterRunMeasureRepository.findAll().size();
        lifterRunMeasure.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLifterRunMeasureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lifterRunMeasure))
            )
            .andExpect(status().isBadRequest());

        // Validate the LifterRunMeasure in the database
        List<LifterRunMeasure> lifterRunMeasureList = lifterRunMeasureRepository.findAll();
        assertThat(lifterRunMeasureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLifterRunMeasure() throws Exception {
        int databaseSizeBeforeUpdate = lifterRunMeasureRepository.findAll().size();
        lifterRunMeasure.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLifterRunMeasureMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lifterRunMeasure))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LifterRunMeasure in the database
        List<LifterRunMeasure> lifterRunMeasureList = lifterRunMeasureRepository.findAll();
        assertThat(lifterRunMeasureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLifterRunMeasureWithPatch() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        int databaseSizeBeforeUpdate = lifterRunMeasureRepository.findAll().size();

        // Update the lifterRunMeasure using partial update
        LifterRunMeasure partialUpdatedLifterRunMeasure = new LifterRunMeasure();
        partialUpdatedLifterRunMeasure.setId(lifterRunMeasure.getId());

        partialUpdatedLifterRunMeasure
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER)
            .meterPerSecondSpeed(UPDATED_METER_PER_SECOND_SPEED)
            .markingType(UPDATED_MARKING_TYPE)
            .formatedHourPreparationTime(UPDATED_FORMATED_HOUR_PREPARATION_TIME);

        restLifterRunMeasureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLifterRunMeasure.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLifterRunMeasure))
            )
            .andExpect(status().isOk());

        // Validate the LifterRunMeasure in the database
        List<LifterRunMeasure> lifterRunMeasureList = lifterRunMeasureRepository.findAll();
        assertThat(lifterRunMeasureList).hasSize(databaseSizeBeforeUpdate);
        LifterRunMeasure testLifterRunMeasure = lifterRunMeasureList.get(lifterRunMeasureList.size() - 1);
        assertThat(testLifterRunMeasure.getMilimeterDiameter()).isEqualTo(UPDATED_MILIMETER_DIAMETER);
        assertThat(testLifterRunMeasure.getMeterPerSecondSpeed()).isEqualTo(UPDATED_METER_PER_SECOND_SPEED);
        assertThat(testLifterRunMeasure.getMarkingType()).isEqualTo(UPDATED_MARKING_TYPE);
        assertThat(testLifterRunMeasure.getMarkingTechnique()).isEqualTo(DEFAULT_MARKING_TECHNIQUE);
        assertThat(testLifterRunMeasure.getFormatedHourPreparationTime()).isEqualTo(UPDATED_FORMATED_HOUR_PREPARATION_TIME);
    }

    @Test
    @Transactional
    void fullUpdateLifterRunMeasureWithPatch() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        int databaseSizeBeforeUpdate = lifterRunMeasureRepository.findAll().size();

        // Update the lifterRunMeasure using partial update
        LifterRunMeasure partialUpdatedLifterRunMeasure = new LifterRunMeasure();
        partialUpdatedLifterRunMeasure.setId(lifterRunMeasure.getId());

        partialUpdatedLifterRunMeasure
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER)
            .meterPerSecondSpeed(UPDATED_METER_PER_SECOND_SPEED)
            .markingType(UPDATED_MARKING_TYPE)
            .markingTechnique(UPDATED_MARKING_TECHNIQUE)
            .formatedHourPreparationTime(UPDATED_FORMATED_HOUR_PREPARATION_TIME);

        restLifterRunMeasureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLifterRunMeasure.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLifterRunMeasure))
            )
            .andExpect(status().isOk());

        // Validate the LifterRunMeasure in the database
        List<LifterRunMeasure> lifterRunMeasureList = lifterRunMeasureRepository.findAll();
        assertThat(lifterRunMeasureList).hasSize(databaseSizeBeforeUpdate);
        LifterRunMeasure testLifterRunMeasure = lifterRunMeasureList.get(lifterRunMeasureList.size() - 1);
        assertThat(testLifterRunMeasure.getMilimeterDiameter()).isEqualTo(UPDATED_MILIMETER_DIAMETER);
        assertThat(testLifterRunMeasure.getMeterPerSecondSpeed()).isEqualTo(UPDATED_METER_PER_SECOND_SPEED);
        assertThat(testLifterRunMeasure.getMarkingType()).isEqualTo(UPDATED_MARKING_TYPE);
        assertThat(testLifterRunMeasure.getMarkingTechnique()).isEqualTo(UPDATED_MARKING_TECHNIQUE);
        assertThat(testLifterRunMeasure.getFormatedHourPreparationTime()).isEqualTo(UPDATED_FORMATED_HOUR_PREPARATION_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingLifterRunMeasure() throws Exception {
        int databaseSizeBeforeUpdate = lifterRunMeasureRepository.findAll().size();
        lifterRunMeasure.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLifterRunMeasureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, lifterRunMeasure.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lifterRunMeasure))
            )
            .andExpect(status().isBadRequest());

        // Validate the LifterRunMeasure in the database
        List<LifterRunMeasure> lifterRunMeasureList = lifterRunMeasureRepository.findAll();
        assertThat(lifterRunMeasureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLifterRunMeasure() throws Exception {
        int databaseSizeBeforeUpdate = lifterRunMeasureRepository.findAll().size();
        lifterRunMeasure.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLifterRunMeasureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lifterRunMeasure))
            )
            .andExpect(status().isBadRequest());

        // Validate the LifterRunMeasure in the database
        List<LifterRunMeasure> lifterRunMeasureList = lifterRunMeasureRepository.findAll();
        assertThat(lifterRunMeasureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLifterRunMeasure() throws Exception {
        int databaseSizeBeforeUpdate = lifterRunMeasureRepository.findAll().size();
        lifterRunMeasure.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLifterRunMeasureMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lifterRunMeasure))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LifterRunMeasure in the database
        List<LifterRunMeasure> lifterRunMeasureList = lifterRunMeasureRepository.findAll();
        assertThat(lifterRunMeasureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLifterRunMeasure() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        int databaseSizeBeforeDelete = lifterRunMeasureRepository.findAll().size();

        // Delete the lifterRunMeasure
        restLifterRunMeasureMockMvc
            .perform(delete(ENTITY_API_URL_ID, lifterRunMeasure.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LifterRunMeasure> lifterRunMeasureList = lifterRunMeasureRepository.findAll();
        assertThat(lifterRunMeasureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
