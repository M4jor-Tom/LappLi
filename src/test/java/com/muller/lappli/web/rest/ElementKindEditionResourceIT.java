package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.ElementKind;
import com.muller.lappli.domain.ElementKindEdition;
import com.muller.lappli.repository.ElementKindEditionRepository;
//import com.muller.lappli.service.criteria.ElementKindEditionCriteria;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
//import java.util.Random;
//import java.util.concurrent.atomic.AtomicLong;
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
 * Integration tests for the {@link ElementKindEditionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ElementKindEditionResourceIT {

    //private static final Instant DEFAULT_EDITION_DATE_TIME = Instant.ofEpochMilli(0L);
    //private static final Instant UPDATED_EDITION_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_NEW_GRAM_PER_METER_LINEAR_MASS = 1D;
    private static final Double UPDATED_NEW_GRAM_PER_METER_LINEAR_MASS = 2D;
    private static final Double SMALLER_NEW_GRAM_PER_METER_LINEAR_MASS = 1D - 1D;

    private static final Double DEFAULT_NEW_MILIMETER_DIAMETER = 1D;
    private static final Double UPDATED_NEW_MILIMETER_DIAMETER = 2D;
    private static final Double SMALLER_NEW_MILIMETER_DIAMETER = 1D - 1D;

    private static final Double DEFAULT_NEW_INSULATION_THICKNESS = 1D;
    private static final Double UPDATED_NEW_INSULATION_THICKNESS = 2D;
    private static final Double SMALLER_NEW_INSULATION_THICKNESS = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/element-kind-editions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    //private static Random random = new Random();
    //private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ElementKindEditionRepository elementKindEditionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restElementKindEditionMockMvc;

    private ElementKindEdition elementKindEdition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ElementKindEdition createEntity(EntityManager em) {
        ElementKindEdition elementKindEdition = new ElementKindEdition()
            .newGramPerMeterLinearMass(DEFAULT_NEW_GRAM_PER_METER_LINEAR_MASS)
            .newMilimeterDiameter(DEFAULT_NEW_MILIMETER_DIAMETER)
            .newInsulationThickness(DEFAULT_NEW_INSULATION_THICKNESS);
        // Add required entity
        ElementKind elementKind;
        if (TestUtil.findAll(em, ElementKind.class).isEmpty()) {
            elementKind = ElementKindResourceIT.createEntity(em);
            em.persist(elementKind);
            em.flush();
        } else {
            elementKind = TestUtil.findAll(em, ElementKind.class).get(0);
        }
        elementKindEdition.setEditedElementKind(elementKind);
        return elementKindEdition;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ElementKindEdition createUpdatedEntity(EntityManager em) {
        ElementKindEdition elementKindEdition = new ElementKindEdition()
            .newGramPerMeterLinearMass(UPDATED_NEW_GRAM_PER_METER_LINEAR_MASS)
            .newMilimeterDiameter(UPDATED_NEW_MILIMETER_DIAMETER)
            .newInsulationThickness(UPDATED_NEW_INSULATION_THICKNESS);
        // Add required entity
        ElementKind elementKind;
        if (TestUtil.findAll(em, ElementKind.class).isEmpty()) {
            elementKind = ElementKindResourceIT.createUpdatedEntity(em);
            em.persist(elementKind);
            em.flush();
        } else {
            elementKind = TestUtil.findAll(em, ElementKind.class).get(0);
        }
        elementKindEdition.setEditedElementKind(elementKind);
        return elementKindEdition;
    }

    @BeforeEach
    public void initTest() {
        elementKindEdition = createEntity(em);
    }

    @Test
    @Transactional
    void createElementKindEdition() throws Exception {
        int databaseSizeBeforeCreate = elementKindEditionRepository.findAll().size();
        // Create the ElementKindEdition
        restElementKindEditionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(elementKindEdition))
            )
            .andExpect(status().isCreated());

        // Validate the ElementKindEdition in the database
        List<ElementKindEdition> elementKindEditionList = elementKindEditionRepository.findAll();
        assertThat(elementKindEditionList).hasSize(databaseSizeBeforeCreate + 1);
        ElementKindEdition testElementKindEdition = elementKindEditionList.get(elementKindEditionList.size() - 1);
        //assertThat(testElementKindEdition.getEditionDateTime()).isEqualTo(DEFAULT_EDITION_DATE_TIME);
        assertThat(testElementKindEdition.getNewGramPerMeterLinearMass()).isEqualTo(DEFAULT_NEW_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testElementKindEdition.getNewMilimeterDiameter()).isEqualTo(DEFAULT_NEW_MILIMETER_DIAMETER);
        assertThat(testElementKindEdition.getNewInsulationThickness()).isEqualTo(DEFAULT_NEW_INSULATION_THICKNESS);
    }

    @Test
    @Transactional
    void createElementKindEditionWithExistingId() throws Exception {
        // Create the ElementKindEdition with an existing ID
        elementKindEdition.setId(1L);

        int databaseSizeBeforeCreate = elementKindEditionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restElementKindEditionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(elementKindEdition))
            )
            .andExpect(status().isBadRequest());

        // Validate the ElementKindEdition in the database
        List<ElementKindEdition> elementKindEditionList = elementKindEditionRepository.findAll();
        assertThat(elementKindEditionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllElementKindEditions() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        // Get all the elementKindEditionList
        restElementKindEditionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(elementKindEdition.getId().intValue())))
            //.andExpect(jsonPath("$.[*].editionDateTime").value(hasItem(DEFAULT_EDITION_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].newGramPerMeterLinearMass").value(hasItem(DEFAULT_NEW_GRAM_PER_METER_LINEAR_MASS.doubleValue())))
            .andExpect(jsonPath("$.[*].newMilimeterDiameter").value(hasItem(DEFAULT_NEW_MILIMETER_DIAMETER.doubleValue())))
            .andExpect(jsonPath("$.[*].newInsulationThickness").value(hasItem(DEFAULT_NEW_INSULATION_THICKNESS.doubleValue())));
    }

    @Test
    @Transactional
    void getElementKindEdition() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        // Get the elementKindEdition
        restElementKindEditionMockMvc
            .perform(get(ENTITY_API_URL_ID, elementKindEdition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(elementKindEdition.getId().intValue()))
            //.andExpect(jsonPath("$.editionDateTime").value(DEFAULT_EDITION_DATE_TIME.toString()))
            .andExpect(jsonPath("$.newGramPerMeterLinearMass").value(DEFAULT_NEW_GRAM_PER_METER_LINEAR_MASS.doubleValue()))
            .andExpect(jsonPath("$.newMilimeterDiameter").value(DEFAULT_NEW_MILIMETER_DIAMETER.doubleValue()))
            .andExpect(jsonPath("$.newInsulationThickness").value(DEFAULT_NEW_INSULATION_THICKNESS.doubleValue()));
    }

    @Test
    @Transactional
    void getElementKindEditionsByIdFiltering() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        Long id = elementKindEdition.getId();

        defaultElementKindEditionShouldBeFound("id.equals=" + id);
        defaultElementKindEditionShouldNotBeFound("id.notEquals=" + id);

        defaultElementKindEditionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultElementKindEditionShouldNotBeFound("id.greaterThan=" + id);

        defaultElementKindEditionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultElementKindEditionShouldNotBeFound("id.lessThan=" + id);
    }

    /*
    @Test
    @Transactional
    void getAllElementKindEditionsByEditionDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        // Get all the elementKindEditionList where editionDateTime equals to DEFAULT_EDITION_DATE_TIME
        defaultElementKindEditionShouldBeFound("editionDateTime.equals=" + DEFAULT_EDITION_DATE_TIME);

        // Get all the elementKindEditionList where editionDateTime equals to UPDATED_EDITION_DATE_TIME
        defaultElementKindEditionShouldNotBeFound("editionDateTime.equals=" + UPDATED_EDITION_DATE_TIME);
    }*/
    /*
    @Test
    @Transactional
    void getAllElementKindEditionsByEditionDateTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        // Get all the elementKindEditionList where editionDateTime not equals to DEFAULT_EDITION_DATE_TIME
        defaultElementKindEditionShouldNotBeFound("editionDateTime.notEquals=" + DEFAULT_EDITION_DATE_TIME);

        // Get all the elementKindEditionList where editionDateTime not equals to UPDATED_EDITION_DATE_TIME
        defaultElementKindEditionShouldBeFound("editionDateTime.notEquals=" + UPDATED_EDITION_DATE_TIME);
    }*/
    /*
    @Test
    @Transactional
    void getAllElementKindEditionsByEditionDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        // Get all the elementKindEditionList where editionDateTime in DEFAULT_EDITION_DATE_TIME or UPDATED_EDITION_DATE_TIME
        defaultElementKindEditionShouldBeFound("editionDateTime.in=" + DEFAULT_EDITION_DATE_TIME + "," + UPDATED_EDITION_DATE_TIME);

        // Get all the elementKindEditionList where editionDateTime equals to UPDATED_EDITION_DATE_TIME
        defaultElementKindEditionShouldNotBeFound("editionDateTime.in=" + UPDATED_EDITION_DATE_TIME);
    }*/

    @Test
    @Transactional
    void getAllElementKindEditionsByEditionDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        // Get all the elementKindEditionList where editionDateTime is not null
        defaultElementKindEditionShouldBeFound("editionDateTime.specified=true");

        // Get all the elementKindEditionList where editionDateTime is null
        defaultElementKindEditionShouldNotBeFound("editionDateTime.specified=false");
    }

    @Test
    @Transactional
    void getAllElementKindEditionsByNewGramPerMeterLinearMassIsEqualToSomething() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        // Get all the elementKindEditionList where newGramPerMeterLinearMass equals to DEFAULT_NEW_GRAM_PER_METER_LINEAR_MASS
        defaultElementKindEditionShouldBeFound("newGramPerMeterLinearMass.equals=" + DEFAULT_NEW_GRAM_PER_METER_LINEAR_MASS);

        // Get all the elementKindEditionList where newGramPerMeterLinearMass equals to UPDATED_NEW_GRAM_PER_METER_LINEAR_MASS
        defaultElementKindEditionShouldNotBeFound("newGramPerMeterLinearMass.equals=" + UPDATED_NEW_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllElementKindEditionsByNewGramPerMeterLinearMassIsNotEqualToSomething() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        // Get all the elementKindEditionList where newGramPerMeterLinearMass not equals to DEFAULT_NEW_GRAM_PER_METER_LINEAR_MASS
        defaultElementKindEditionShouldNotBeFound("newGramPerMeterLinearMass.notEquals=" + DEFAULT_NEW_GRAM_PER_METER_LINEAR_MASS);

        // Get all the elementKindEditionList where newGramPerMeterLinearMass not equals to UPDATED_NEW_GRAM_PER_METER_LINEAR_MASS
        defaultElementKindEditionShouldBeFound("newGramPerMeterLinearMass.notEquals=" + UPDATED_NEW_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllElementKindEditionsByNewGramPerMeterLinearMassIsInShouldWork() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        // Get all the elementKindEditionList where newGramPerMeterLinearMass in DEFAULT_NEW_GRAM_PER_METER_LINEAR_MASS or UPDATED_NEW_GRAM_PER_METER_LINEAR_MASS
        defaultElementKindEditionShouldBeFound(
            "newGramPerMeterLinearMass.in=" + DEFAULT_NEW_GRAM_PER_METER_LINEAR_MASS + "," + UPDATED_NEW_GRAM_PER_METER_LINEAR_MASS
        );

        // Get all the elementKindEditionList where newGramPerMeterLinearMass equals to UPDATED_NEW_GRAM_PER_METER_LINEAR_MASS
        defaultElementKindEditionShouldNotBeFound("newGramPerMeterLinearMass.in=" + UPDATED_NEW_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllElementKindEditionsByNewGramPerMeterLinearMassIsNullOrNotNull() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        // Get all the elementKindEditionList where newGramPerMeterLinearMass is not null
        defaultElementKindEditionShouldBeFound("newGramPerMeterLinearMass.specified=true");

        // Get all the elementKindEditionList where newGramPerMeterLinearMass is null
        defaultElementKindEditionShouldNotBeFound("newGramPerMeterLinearMass.specified=false");
    }

    @Test
    @Transactional
    void getAllElementKindEditionsByNewGramPerMeterLinearMassIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        // Get all the elementKindEditionList where newGramPerMeterLinearMass is greater than or equal to DEFAULT_NEW_GRAM_PER_METER_LINEAR_MASS
        defaultElementKindEditionShouldBeFound("newGramPerMeterLinearMass.greaterThanOrEqual=" + DEFAULT_NEW_GRAM_PER_METER_LINEAR_MASS);

        // Get all the elementKindEditionList where newGramPerMeterLinearMass is greater than or equal to UPDATED_NEW_GRAM_PER_METER_LINEAR_MASS
        defaultElementKindEditionShouldNotBeFound("newGramPerMeterLinearMass.greaterThanOrEqual=" + UPDATED_NEW_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllElementKindEditionsByNewGramPerMeterLinearMassIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        // Get all the elementKindEditionList where newGramPerMeterLinearMass is less than or equal to DEFAULT_NEW_GRAM_PER_METER_LINEAR_MASS
        defaultElementKindEditionShouldBeFound("newGramPerMeterLinearMass.lessThanOrEqual=" + DEFAULT_NEW_GRAM_PER_METER_LINEAR_MASS);

        // Get all the elementKindEditionList where newGramPerMeterLinearMass is less than or equal to SMALLER_NEW_GRAM_PER_METER_LINEAR_MASS
        defaultElementKindEditionShouldNotBeFound("newGramPerMeterLinearMass.lessThanOrEqual=" + SMALLER_NEW_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllElementKindEditionsByNewGramPerMeterLinearMassIsLessThanSomething() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        // Get all the elementKindEditionList where newGramPerMeterLinearMass is less than DEFAULT_NEW_GRAM_PER_METER_LINEAR_MASS
        defaultElementKindEditionShouldNotBeFound("newGramPerMeterLinearMass.lessThan=" + DEFAULT_NEW_GRAM_PER_METER_LINEAR_MASS);

        // Get all the elementKindEditionList where newGramPerMeterLinearMass is less than UPDATED_NEW_GRAM_PER_METER_LINEAR_MASS
        defaultElementKindEditionShouldBeFound("newGramPerMeterLinearMass.lessThan=" + UPDATED_NEW_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllElementKindEditionsByNewGramPerMeterLinearMassIsGreaterThanSomething() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        // Get all the elementKindEditionList where newGramPerMeterLinearMass is greater than DEFAULT_NEW_GRAM_PER_METER_LINEAR_MASS
        defaultElementKindEditionShouldNotBeFound("newGramPerMeterLinearMass.greaterThan=" + DEFAULT_NEW_GRAM_PER_METER_LINEAR_MASS);

        // Get all the elementKindEditionList where newGramPerMeterLinearMass is greater than SMALLER_NEW_GRAM_PER_METER_LINEAR_MASS
        defaultElementKindEditionShouldBeFound("newGramPerMeterLinearMass.greaterThan=" + SMALLER_NEW_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllElementKindEditionsByNewMilimeterDiameterIsEqualToSomething() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        // Get all the elementKindEditionList where newMilimeterDiameter equals to DEFAULT_NEW_MILIMETER_DIAMETER
        defaultElementKindEditionShouldBeFound("newMilimeterDiameter.equals=" + DEFAULT_NEW_MILIMETER_DIAMETER);

        // Get all the elementKindEditionList where newMilimeterDiameter equals to UPDATED_NEW_MILIMETER_DIAMETER
        defaultElementKindEditionShouldNotBeFound("newMilimeterDiameter.equals=" + UPDATED_NEW_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllElementKindEditionsByNewMilimeterDiameterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        // Get all the elementKindEditionList where newMilimeterDiameter not equals to DEFAULT_NEW_MILIMETER_DIAMETER
        defaultElementKindEditionShouldNotBeFound("newMilimeterDiameter.notEquals=" + DEFAULT_NEW_MILIMETER_DIAMETER);

        // Get all the elementKindEditionList where newMilimeterDiameter not equals to UPDATED_NEW_MILIMETER_DIAMETER
        defaultElementKindEditionShouldBeFound("newMilimeterDiameter.notEquals=" + UPDATED_NEW_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllElementKindEditionsByNewMilimeterDiameterIsInShouldWork() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        // Get all the elementKindEditionList where newMilimeterDiameter in DEFAULT_NEW_MILIMETER_DIAMETER or UPDATED_NEW_MILIMETER_DIAMETER
        defaultElementKindEditionShouldBeFound(
            "newMilimeterDiameter.in=" + DEFAULT_NEW_MILIMETER_DIAMETER + "," + UPDATED_NEW_MILIMETER_DIAMETER
        );

        // Get all the elementKindEditionList where newMilimeterDiameter equals to UPDATED_NEW_MILIMETER_DIAMETER
        defaultElementKindEditionShouldNotBeFound("newMilimeterDiameter.in=" + UPDATED_NEW_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllElementKindEditionsByNewMilimeterDiameterIsNullOrNotNull() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        // Get all the elementKindEditionList where newMilimeterDiameter is not null
        defaultElementKindEditionShouldBeFound("newMilimeterDiameter.specified=true");

        // Get all the elementKindEditionList where newMilimeterDiameter is null
        defaultElementKindEditionShouldNotBeFound("newMilimeterDiameter.specified=false");
    }

    @Test
    @Transactional
    void getAllElementKindEditionsByNewMilimeterDiameterIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        // Get all the elementKindEditionList where newMilimeterDiameter is greater than or equal to DEFAULT_NEW_MILIMETER_DIAMETER
        defaultElementKindEditionShouldBeFound("newMilimeterDiameter.greaterThanOrEqual=" + DEFAULT_NEW_MILIMETER_DIAMETER);

        // Get all the elementKindEditionList where newMilimeterDiameter is greater than or equal to UPDATED_NEW_MILIMETER_DIAMETER
        defaultElementKindEditionShouldNotBeFound("newMilimeterDiameter.greaterThanOrEqual=" + UPDATED_NEW_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllElementKindEditionsByNewMilimeterDiameterIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        // Get all the elementKindEditionList where newMilimeterDiameter is less than or equal to DEFAULT_NEW_MILIMETER_DIAMETER
        defaultElementKindEditionShouldBeFound("newMilimeterDiameter.lessThanOrEqual=" + DEFAULT_NEW_MILIMETER_DIAMETER);

        // Get all the elementKindEditionList where newMilimeterDiameter is less than or equal to SMALLER_NEW_MILIMETER_DIAMETER
        defaultElementKindEditionShouldNotBeFound("newMilimeterDiameter.lessThanOrEqual=" + SMALLER_NEW_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllElementKindEditionsByNewMilimeterDiameterIsLessThanSomething() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        // Get all the elementKindEditionList where newMilimeterDiameter is less than DEFAULT_NEW_MILIMETER_DIAMETER
        defaultElementKindEditionShouldNotBeFound("newMilimeterDiameter.lessThan=" + DEFAULT_NEW_MILIMETER_DIAMETER);

        // Get all the elementKindEditionList where newMilimeterDiameter is less than UPDATED_NEW_MILIMETER_DIAMETER
        defaultElementKindEditionShouldBeFound("newMilimeterDiameter.lessThan=" + UPDATED_NEW_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllElementKindEditionsByNewMilimeterDiameterIsGreaterThanSomething() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        // Get all the elementKindEditionList where newMilimeterDiameter is greater than DEFAULT_NEW_MILIMETER_DIAMETER
        defaultElementKindEditionShouldNotBeFound("newMilimeterDiameter.greaterThan=" + DEFAULT_NEW_MILIMETER_DIAMETER);

        // Get all the elementKindEditionList where newMilimeterDiameter is greater than SMALLER_NEW_MILIMETER_DIAMETER
        defaultElementKindEditionShouldBeFound("newMilimeterDiameter.greaterThan=" + SMALLER_NEW_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllElementKindEditionsByNewInsulationThicknessIsEqualToSomething() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        // Get all the elementKindEditionList where newInsulationThickness equals to DEFAULT_NEW_INSULATION_THICKNESS
        defaultElementKindEditionShouldBeFound("newInsulationThickness.equals=" + DEFAULT_NEW_INSULATION_THICKNESS);

        // Get all the elementKindEditionList where newInsulationThickness equals to UPDATED_NEW_INSULATION_THICKNESS
        defaultElementKindEditionShouldNotBeFound("newInsulationThickness.equals=" + UPDATED_NEW_INSULATION_THICKNESS);
    }

    @Test
    @Transactional
    void getAllElementKindEditionsByNewInsulationThicknessIsNotEqualToSomething() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        // Get all the elementKindEditionList where newInsulationThickness not equals to DEFAULT_NEW_INSULATION_THICKNESS
        defaultElementKindEditionShouldNotBeFound("newInsulationThickness.notEquals=" + DEFAULT_NEW_INSULATION_THICKNESS);

        // Get all the elementKindEditionList where newInsulationThickness not equals to UPDATED_NEW_INSULATION_THICKNESS
        defaultElementKindEditionShouldBeFound("newInsulationThickness.notEquals=" + UPDATED_NEW_INSULATION_THICKNESS);
    }

    @Test
    @Transactional
    void getAllElementKindEditionsByNewInsulationThicknessIsInShouldWork() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        // Get all the elementKindEditionList where newInsulationThickness in DEFAULT_NEW_INSULATION_THICKNESS or UPDATED_NEW_INSULATION_THICKNESS
        defaultElementKindEditionShouldBeFound(
            "newInsulationThickness.in=" + DEFAULT_NEW_INSULATION_THICKNESS + "," + UPDATED_NEW_INSULATION_THICKNESS
        );

        // Get all the elementKindEditionList where newInsulationThickness equals to UPDATED_NEW_INSULATION_THICKNESS
        defaultElementKindEditionShouldNotBeFound("newInsulationThickness.in=" + UPDATED_NEW_INSULATION_THICKNESS);
    }

    @Test
    @Transactional
    void getAllElementKindEditionsByNewInsulationThicknessIsNullOrNotNull() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        // Get all the elementKindEditionList where newInsulationThickness is not null
        defaultElementKindEditionShouldBeFound("newInsulationThickness.specified=true");

        // Get all the elementKindEditionList where newInsulationThickness is null
        defaultElementKindEditionShouldNotBeFound("newInsulationThickness.specified=false");
    }

    @Test
    @Transactional
    void getAllElementKindEditionsByNewInsulationThicknessIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        // Get all the elementKindEditionList where newInsulationThickness is greater than or equal to DEFAULT_NEW_INSULATION_THICKNESS
        defaultElementKindEditionShouldBeFound("newInsulationThickness.greaterThanOrEqual=" + DEFAULT_NEW_INSULATION_THICKNESS);

        // Get all the elementKindEditionList where newInsulationThickness is greater than or equal to UPDATED_NEW_INSULATION_THICKNESS
        defaultElementKindEditionShouldNotBeFound("newInsulationThickness.greaterThanOrEqual=" + UPDATED_NEW_INSULATION_THICKNESS);
    }

    @Test
    @Transactional
    void getAllElementKindEditionsByNewInsulationThicknessIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        // Get all the elementKindEditionList where newInsulationThickness is less than or equal to DEFAULT_NEW_INSULATION_THICKNESS
        defaultElementKindEditionShouldBeFound("newInsulationThickness.lessThanOrEqual=" + DEFAULT_NEW_INSULATION_THICKNESS);

        // Get all the elementKindEditionList where newInsulationThickness is less than or equal to SMALLER_NEW_INSULATION_THICKNESS
        defaultElementKindEditionShouldNotBeFound("newInsulationThickness.lessThanOrEqual=" + SMALLER_NEW_INSULATION_THICKNESS);
    }

    @Test
    @Transactional
    void getAllElementKindEditionsByNewInsulationThicknessIsLessThanSomething() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        // Get all the elementKindEditionList where newInsulationThickness is less than DEFAULT_NEW_INSULATION_THICKNESS
        defaultElementKindEditionShouldNotBeFound("newInsulationThickness.lessThan=" + DEFAULT_NEW_INSULATION_THICKNESS);

        // Get all the elementKindEditionList where newInsulationThickness is less than UPDATED_NEW_INSULATION_THICKNESS
        defaultElementKindEditionShouldBeFound("newInsulationThickness.lessThan=" + UPDATED_NEW_INSULATION_THICKNESS);
    }

    @Test
    @Transactional
    void getAllElementKindEditionsByNewInsulationThicknessIsGreaterThanSomething() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        // Get all the elementKindEditionList where newInsulationThickness is greater than DEFAULT_NEW_INSULATION_THICKNESS
        defaultElementKindEditionShouldNotBeFound("newInsulationThickness.greaterThan=" + DEFAULT_NEW_INSULATION_THICKNESS);

        // Get all the elementKindEditionList where newInsulationThickness is greater than SMALLER_NEW_INSULATION_THICKNESS
        defaultElementKindEditionShouldBeFound("newInsulationThickness.greaterThan=" + SMALLER_NEW_INSULATION_THICKNESS);
    }

    @Test
    @Transactional
    void getAllElementKindEditionsByEditedElementKindIsEqualToSomething() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);
        ElementKind editedElementKind;
        if (TestUtil.findAll(em, ElementKind.class).isEmpty()) {
            editedElementKind = ElementKindResourceIT.createEntity(em);
            em.persist(editedElementKind);
            em.flush();
        } else {
            editedElementKind = TestUtil.findAll(em, ElementKind.class).get(0);
        }
        em.persist(editedElementKind);
        em.flush();
        elementKindEdition.setEditedElementKind(editedElementKind);
        elementKindEditionRepository.saveAndFlush(elementKindEdition);
        Long editedElementKindId = editedElementKind.getId();

        // Get all the elementKindEditionList where editedElementKind equals to editedElementKindId
        defaultElementKindEditionShouldBeFound("editedElementKindId.equals=" + editedElementKindId);

        // Get all the elementKindEditionList where editedElementKind equals to (editedElementKindId + 1)
        defaultElementKindEditionShouldNotBeFound("editedElementKindId.equals=" + (editedElementKindId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultElementKindEditionShouldBeFound(String filter) throws Exception {
        restElementKindEditionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(elementKindEdition.getId().intValue())))
            //.andExpect(jsonPath("$.[*].editionDateTime").value(hasItem(DEFAULT_EDITION_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].newGramPerMeterLinearMass").value(hasItem(DEFAULT_NEW_GRAM_PER_METER_LINEAR_MASS.doubleValue())))
            .andExpect(jsonPath("$.[*].newMilimeterDiameter").value(hasItem(DEFAULT_NEW_MILIMETER_DIAMETER.doubleValue())))
            .andExpect(jsonPath("$.[*].newInsulationThickness").value(hasItem(DEFAULT_NEW_INSULATION_THICKNESS.doubleValue())));

        // Check, that the count call also returns 1
        restElementKindEditionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultElementKindEditionShouldNotBeFound(String filter) throws Exception {
        restElementKindEditionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restElementKindEditionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingElementKindEdition() throws Exception {
        // Get the elementKindEdition
        restElementKindEditionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewElementKindEdition() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        int databaseSizeBeforeUpdate = elementKindEditionRepository.findAll().size();

        // Update the elementKindEdition
        ElementKindEdition updatedElementKindEdition = elementKindEditionRepository.findById(elementKindEdition.getId()).get();
        // Disconnect from session so that the updates on updatedElementKindEdition are not directly saved in db
        em.detach(updatedElementKindEdition);
        updatedElementKindEdition
            .newGramPerMeterLinearMass(UPDATED_NEW_GRAM_PER_METER_LINEAR_MASS)
            .newMilimeterDiameter(UPDATED_NEW_MILIMETER_DIAMETER)
            .newInsulationThickness(UPDATED_NEW_INSULATION_THICKNESS);

        restElementKindEditionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedElementKindEdition.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedElementKindEdition))
            )
            .andExpect(status().isOk());

        // Validate the ElementKindEdition in the database
        List<ElementKindEdition> elementKindEditionList = elementKindEditionRepository.findAll();
        assertThat(elementKindEditionList).hasSize(databaseSizeBeforeUpdate);
        ElementKindEdition testElementKindEdition = elementKindEditionList.get(elementKindEditionList.size() - 1);
        assertThat(testElementKindEdition.getNewGramPerMeterLinearMass()).isEqualTo(UPDATED_NEW_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testElementKindEdition.getNewMilimeterDiameter()).isEqualTo(UPDATED_NEW_MILIMETER_DIAMETER);
        assertThat(testElementKindEdition.getNewInsulationThickness()).isEqualTo(UPDATED_NEW_INSULATION_THICKNESS);
    }
    /*
    @Test
    @Transactional
    void putNonExistingElementKindEdition() throws Exception {
        int databaseSizeBeforeUpdate = elementKindEditionRepository.findAll().size();
        elementKindEdition.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restElementKindEditionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, elementKindEdition.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(elementKindEdition))
            )
            .andExpect(status().isBadRequest());

        // Validate the ElementKindEdition in the database
        List<ElementKindEdition> elementKindEditionList = elementKindEditionRepository.findAll();
        assertThat(elementKindEditionList).hasSize(databaseSizeBeforeUpdate);
    }*/
    /*
    @Test
    @Transactional
    void putWithIdMismatchElementKindEdition() throws Exception {
        int databaseSizeBeforeUpdate = elementKindEditionRepository.findAll().size();
        elementKindEdition.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restElementKindEditionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(elementKindEdition))
            )
            .andExpect(status().isBadRequest());

        // Validate the ElementKindEdition in the database
        List<ElementKindEdition> elementKindEditionList = elementKindEditionRepository.findAll();
        assertThat(elementKindEditionList).hasSize(databaseSizeBeforeUpdate);
    }*/
    /*
    @Test
    @Transactional
    void putWithMissingIdPathParamElementKindEdition() throws Exception {
        int databaseSizeBeforeUpdate = elementKindEditionRepository.findAll().size();
        elementKindEdition.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restElementKindEditionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(elementKindEdition))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ElementKindEdition in the database
        List<ElementKindEdition> elementKindEditionList = elementKindEditionRepository.findAll();
        assertThat(elementKindEditionList).hasSize(databaseSizeBeforeUpdate);
    }*/
    /*
    @Test
    @Transactional
    void partialUpdateElementKindEditionWithPatch() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        int databaseSizeBeforeUpdate = elementKindEditionRepository.findAll().size();

        // Update the elementKindEdition using partial update
        ElementKindEdition partialUpdatedElementKindEdition = new ElementKindEdition();
        partialUpdatedElementKindEdition.setId(elementKindEdition.getId());

        partialUpdatedElementKindEdition.newMilimeterDiameter(UPDATED_NEW_MILIMETER_DIAMETER);

        restElementKindEditionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedElementKindEdition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedElementKindEdition))
            )
            .andExpect(status().isOk());

        // Validate the ElementKindEdition in the database
        List<ElementKindEdition> elementKindEditionList = elementKindEditionRepository.findAll();
        assertThat(elementKindEditionList).hasSize(databaseSizeBeforeUpdate);
        ElementKindEdition testElementKindEdition = elementKindEditionList.get(elementKindEditionList.size() - 1);
        assertThat(testElementKindEdition.getNewGramPerMeterLinearMass()).isEqualTo(DEFAULT_NEW_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testElementKindEdition.getNewMilimeterDiameter()).isEqualTo(UPDATED_NEW_MILIMETER_DIAMETER);
        assertThat(testElementKindEdition.getNewInsulationThickness()).isEqualTo(DEFAULT_NEW_INSULATION_THICKNESS);
    }*/
    /*
    @Test
    @Transactional
    void fullUpdateElementKindEditionWithPatch() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        int databaseSizeBeforeUpdate = elementKindEditionRepository.findAll().size();

        // Update the elementKindEdition using partial update
        ElementKindEdition partialUpdatedElementKindEdition = new ElementKindEdition();
        partialUpdatedElementKindEdition.setId(elementKindEdition.getId());

        partialUpdatedElementKindEdition
            .newGramPerMeterLinearMass(UPDATED_NEW_GRAM_PER_METER_LINEAR_MASS)
            .newMilimeterDiameter(UPDATED_NEW_MILIMETER_DIAMETER)
            .newInsulationThickness(UPDATED_NEW_INSULATION_THICKNESS);

        restElementKindEditionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedElementKindEdition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedElementKindEdition))
            )
            .andExpect(status().isOk());

        // Validate the ElementKindEdition in the database
        List<ElementKindEdition> elementKindEditionList = elementKindEditionRepository.findAll();
        assertThat(elementKindEditionList).hasSize(databaseSizeBeforeUpdate);
        ElementKindEdition testElementKindEdition = elementKindEditionList.get(elementKindEditionList.size() - 1);
        assertThat(testElementKindEdition.getNewGramPerMeterLinearMass()).isEqualTo(UPDATED_NEW_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testElementKindEdition.getNewMilimeterDiameter()).isEqualTo(UPDATED_NEW_MILIMETER_DIAMETER);
        assertThat(testElementKindEdition.getNewInsulationThickness()).isEqualTo(UPDATED_NEW_INSULATION_THICKNESS);
    }*/
    /*
    @Test
    @Transactional
    void patchNonExistingElementKindEdition() throws Exception {
        int databaseSizeBeforeUpdate = elementKindEditionRepository.findAll().size();
        elementKindEdition.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restElementKindEditionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, elementKindEdition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(elementKindEdition))
            )
            .andExpect(status().isBadRequest());

        // Validate the ElementKindEdition in the database
        List<ElementKindEdition> elementKindEditionList = elementKindEditionRepository.findAll();
        assertThat(elementKindEditionList).hasSize(databaseSizeBeforeUpdate);
    }*/
    /*
    @Test
    @Transactional
    void patchWithIdMismatchElementKindEdition() throws Exception {
        int databaseSizeBeforeUpdate = elementKindEditionRepository.findAll().size();
        elementKindEdition.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restElementKindEditionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(elementKindEdition))
            )
            .andExpect(status().isBadRequest());

        // Validate the ElementKindEdition in the database
        List<ElementKindEdition> elementKindEditionList = elementKindEditionRepository.findAll();
        assertThat(elementKindEditionList).hasSize(databaseSizeBeforeUpdate);
    }*/
    /*
    @Test
    @Transactional
    void patchWithMissingIdPathParamElementKindEdition() throws Exception {
        int databaseSizeBeforeUpdate = elementKindEditionRepository.findAll().size();
        elementKindEdition.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restElementKindEditionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(elementKindEdition))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ElementKindEdition in the database
        List<ElementKindEdition> elementKindEditionList = elementKindEditionRepository.findAll();
        assertThat(elementKindEditionList).hasSize(databaseSizeBeforeUpdate);
    }*/
    /*
    @Test
    @Transactional
    void deleteElementKindEdition() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        int databaseSizeBeforeDelete = elementKindEditionRepository.findAll().size();

        // Delete the elementKindEdition
        restElementKindEditionMockMvc
            .perform(delete(ENTITY_API_URL_ID, elementKindEdition.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ElementKindEdition> elementKindEditionList = elementKindEditionRepository.findAll();
        assertThat(elementKindEditionList).hasSize(databaseSizeBeforeDelete - 1);
    }*/
}
