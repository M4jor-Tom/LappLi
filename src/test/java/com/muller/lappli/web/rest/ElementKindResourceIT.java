package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.Copper;
import com.muller.lappli.domain.ElementKind;
import com.muller.lappli.domain.Material;
import com.muller.lappli.repository.ElementKindRepository;
//import com.muller.lappli.service.criteria.ElementKindCriteria;
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
 * Integration tests for the {@link ElementKindResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ElementKindResourceIT {

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final Double DEFAULT_GRAM_PER_METER_LINEAR_MASS = 1D;
    private static final Double UPDATED_GRAM_PER_METER_LINEAR_MASS = 2D;
    private static final Double SMALLER_GRAM_PER_METER_LINEAR_MASS = 1D - 1D;

    private static final Double DEFAULT_MILIMETER_DIAMETER = 1D;
    private static final Double UPDATED_MILIMETER_DIAMETER = 2D;
    private static final Double SMALLER_MILIMETER_DIAMETER = 1D - 1D;

    private static final Double DEFAULT_INSULATION_THICKNESS = 1D;
    private static final Double UPDATED_INSULATION_THICKNESS = 2D;
    private static final Double SMALLER_INSULATION_THICKNESS = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/element-kinds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    //private static Random random = new Random();
    //private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ElementKindRepository elementKindRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restElementKindMockMvc;

    private ElementKind elementKind;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ElementKind createEntity(EntityManager em) {
        ElementKind elementKind = new ElementKind()
            .designation(DEFAULT_DESIGNATION)
            .gramPerMeterLinearMass(DEFAULT_GRAM_PER_METER_LINEAR_MASS)
            .milimeterDiameter(DEFAULT_MILIMETER_DIAMETER)
            .insulationThickness(DEFAULT_INSULATION_THICKNESS);
        // Add required entity
        Copper copper;
        if (TestUtil.findAll(em, Copper.class).isEmpty()) {
            copper = CopperResourceIT.createEntity(em);
            em.persist(copper);
            em.flush();
        } else {
            copper = TestUtil.findAll(em, Copper.class).get(0);
        }
        elementKind.setCopper(copper);
        // Add required entity
        Material material;
        if (TestUtil.findAll(em, Material.class).isEmpty()) {
            material = MaterialResourceIT.createEntity(em);
            em.persist(material);
            em.flush();
        } else {
            material = TestUtil.findAll(em, Material.class).get(0);
        }
        elementKind.setInsulationMaterial(material);
        return elementKind;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ElementKind createUpdatedEntity(EntityManager em) {
        ElementKind elementKind = new ElementKind()
            .designation(UPDATED_DESIGNATION)
            .gramPerMeterLinearMass(UPDATED_GRAM_PER_METER_LINEAR_MASS)
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER)
            .insulationThickness(UPDATED_INSULATION_THICKNESS);
        // Add required entity
        Copper copper;
        if (TestUtil.findAll(em, Copper.class).isEmpty()) {
            copper = CopperResourceIT.createUpdatedEntity(em);
            em.persist(copper);
            em.flush();
        } else {
            copper = TestUtil.findAll(em, Copper.class).get(0);
        }
        elementKind.setCopper(copper);
        // Add required entity
        Material material;
        if (TestUtil.findAll(em, Material.class).isEmpty()) {
            material = MaterialResourceIT.createUpdatedEntity(em);
            em.persist(material);
            em.flush();
        } else {
            material = TestUtil.findAll(em, Material.class).get(0);
        }
        elementKind.setInsulationMaterial(material);
        return elementKind;
    }

    @BeforeEach
    public void initTest() {
        elementKind = createEntity(em);
    }

    @Test
    @Transactional
    void createElementKind() throws Exception {
        int databaseSizeBeforeCreate = elementKindRepository.findAll().size();
        // Create the ElementKind
        restElementKindMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(elementKind)))
            .andExpect(status().isCreated());

        // Validate the ElementKind in the database
        List<ElementKind> elementKindList = elementKindRepository.findAll();
        assertThat(elementKindList).hasSize(databaseSizeBeforeCreate + 1);
        ElementKind testElementKind = elementKindList.get(elementKindList.size() - 1);
        assertThat(testElementKind.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testElementKind.getGramPerMeterLinearMass()).isEqualTo(DEFAULT_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testElementKind.getMilimeterDiameter()).isEqualTo(DEFAULT_MILIMETER_DIAMETER);
        assertThat(testElementKind.getInsulationThickness()).isEqualTo(DEFAULT_INSULATION_THICKNESS);
    }

    @Test
    @Transactional
    void createElementKindWithExistingId() throws Exception {
        // Create the ElementKind with an existing ID
        elementKind.setId(1L);

        int databaseSizeBeforeCreate = elementKindRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restElementKindMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(elementKind)))
            .andExpect(status().isBadRequest());

        // Validate the ElementKind in the database
        List<ElementKind> elementKindList = elementKindRepository.findAll();
        assertThat(elementKindList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = elementKindRepository.findAll().size();
        // set the field null
        elementKind.setDesignation(null);

        // Create the ElementKind, which fails.

        restElementKindMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(elementKind)))
            .andExpect(status().isBadRequest());

        List<ElementKind> elementKindList = elementKindRepository.findAll();
        assertThat(elementKindList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGramPerMeterLinearMassIsRequired() throws Exception {
        int databaseSizeBeforeTest = elementKindRepository.findAll().size();
        // set the field null
        elementKind.setGramPerMeterLinearMass(null);

        // Create the ElementKind, which fails.

        restElementKindMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(elementKind)))
            .andExpect(status().isBadRequest());

        List<ElementKind> elementKindList = elementKindRepository.findAll();
        assertThat(elementKindList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMilimeterDiameterIsRequired() throws Exception {
        int databaseSizeBeforeTest = elementKindRepository.findAll().size();
        // set the field null
        elementKind.setMilimeterDiameter(null);

        // Create the ElementKind, which fails.

        restElementKindMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(elementKind)))
            .andExpect(status().isBadRequest());

        List<ElementKind> elementKindList = elementKindRepository.findAll();
        assertThat(elementKindList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkInsulationThicknessIsRequired() throws Exception {
        int databaseSizeBeforeTest = elementKindRepository.findAll().size();
        // set the field null
        elementKind.setInsulationThickness(null);

        // Create the ElementKind, which fails.

        restElementKindMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(elementKind)))
            .andExpect(status().isBadRequest());

        List<ElementKind> elementKindList = elementKindRepository.findAll();
        assertThat(elementKindList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllElementKinds() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        // Get all the elementKindList
        restElementKindMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(elementKind.getId().intValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].gramPerMeterLinearMass").value(hasItem(DEFAULT_GRAM_PER_METER_LINEAR_MASS.doubleValue())))
            .andExpect(jsonPath("$.[*].milimeterDiameter").value(hasItem(DEFAULT_MILIMETER_DIAMETER.doubleValue())))
            .andExpect(jsonPath("$.[*].insulationThickness").value(hasItem(DEFAULT_INSULATION_THICKNESS.doubleValue())));
    }

    @Test
    @Transactional
    void getElementKind() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        // Get the elementKind
        restElementKindMockMvc
            .perform(get(ENTITY_API_URL_ID, elementKind.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(elementKind.getId().intValue()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.gramPerMeterLinearMass").value(DEFAULT_GRAM_PER_METER_LINEAR_MASS.doubleValue()))
            .andExpect(jsonPath("$.milimeterDiameter").value(DEFAULT_MILIMETER_DIAMETER.doubleValue()))
            .andExpect(jsonPath("$.insulationThickness").value(DEFAULT_INSULATION_THICKNESS.doubleValue()));
    }

    @Test
    @Transactional
    void getElementKindsByIdFiltering() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        Long id = elementKind.getId();

        defaultElementKindShouldBeFound("id.equals=" + id);
        defaultElementKindShouldNotBeFound("id.notEquals=" + id);

        defaultElementKindShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultElementKindShouldNotBeFound("id.greaterThan=" + id);

        defaultElementKindShouldBeFound("id.lessThanOrEqual=" + id);
        defaultElementKindShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllElementKindsByDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        // Get all the elementKindList where designation equals to DEFAULT_DESIGNATION
        defaultElementKindShouldBeFound("designation.equals=" + DEFAULT_DESIGNATION);

        // Get all the elementKindList where designation equals to UPDATED_DESIGNATION
        defaultElementKindShouldNotBeFound("designation.equals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllElementKindsByDesignationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        // Get all the elementKindList where designation not equals to DEFAULT_DESIGNATION
        defaultElementKindShouldNotBeFound("designation.notEquals=" + DEFAULT_DESIGNATION);

        // Get all the elementKindList where designation not equals to UPDATED_DESIGNATION
        defaultElementKindShouldBeFound("designation.notEquals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllElementKindsByDesignationIsInShouldWork() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        // Get all the elementKindList where designation in DEFAULT_DESIGNATION or UPDATED_DESIGNATION
        defaultElementKindShouldBeFound("designation.in=" + DEFAULT_DESIGNATION + "," + UPDATED_DESIGNATION);

        // Get all the elementKindList where designation equals to UPDATED_DESIGNATION
        defaultElementKindShouldNotBeFound("designation.in=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllElementKindsByDesignationIsNullOrNotNull() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        // Get all the elementKindList where designation is not null
        defaultElementKindShouldBeFound("designation.specified=true");

        // Get all the elementKindList where designation is null
        defaultElementKindShouldNotBeFound("designation.specified=false");
    }

    @Test
    @Transactional
    void getAllElementKindsByDesignationContainsSomething() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        // Get all the elementKindList where designation contains DEFAULT_DESIGNATION
        defaultElementKindShouldBeFound("designation.contains=" + DEFAULT_DESIGNATION);

        // Get all the elementKindList where designation contains UPDATED_DESIGNATION
        defaultElementKindShouldNotBeFound("designation.contains=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllElementKindsByDesignationNotContainsSomething() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        // Get all the elementKindList where designation does not contain DEFAULT_DESIGNATION
        defaultElementKindShouldNotBeFound("designation.doesNotContain=" + DEFAULT_DESIGNATION);

        // Get all the elementKindList where designation does not contain UPDATED_DESIGNATION
        defaultElementKindShouldBeFound("designation.doesNotContain=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllElementKindsByGramPerMeterLinearMassIsEqualToSomething() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        // Get all the elementKindList where gramPerMeterLinearMass equals to DEFAULT_GRAM_PER_METER_LINEAR_MASS
        defaultElementKindShouldBeFound("gramPerMeterLinearMass.equals=" + DEFAULT_GRAM_PER_METER_LINEAR_MASS);

        // Get all the elementKindList where gramPerMeterLinearMass equals to UPDATED_GRAM_PER_METER_LINEAR_MASS
        defaultElementKindShouldNotBeFound("gramPerMeterLinearMass.equals=" + UPDATED_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllElementKindsByGramPerMeterLinearMassIsNotEqualToSomething() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        // Get all the elementKindList where gramPerMeterLinearMass not equals to DEFAULT_GRAM_PER_METER_LINEAR_MASS
        defaultElementKindShouldNotBeFound("gramPerMeterLinearMass.notEquals=" + DEFAULT_GRAM_PER_METER_LINEAR_MASS);

        // Get all the elementKindList where gramPerMeterLinearMass not equals to UPDATED_GRAM_PER_METER_LINEAR_MASS
        defaultElementKindShouldBeFound("gramPerMeterLinearMass.notEquals=" + UPDATED_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllElementKindsByGramPerMeterLinearMassIsInShouldWork() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        // Get all the elementKindList where gramPerMeterLinearMass in DEFAULT_GRAM_PER_METER_LINEAR_MASS or UPDATED_GRAM_PER_METER_LINEAR_MASS
        defaultElementKindShouldBeFound(
            "gramPerMeterLinearMass.in=" + DEFAULT_GRAM_PER_METER_LINEAR_MASS + "," + UPDATED_GRAM_PER_METER_LINEAR_MASS
        );

        // Get all the elementKindList where gramPerMeterLinearMass equals to UPDATED_GRAM_PER_METER_LINEAR_MASS
        defaultElementKindShouldNotBeFound("gramPerMeterLinearMass.in=" + UPDATED_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllElementKindsByGramPerMeterLinearMassIsNullOrNotNull() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        // Get all the elementKindList where gramPerMeterLinearMass is not null
        defaultElementKindShouldBeFound("gramPerMeterLinearMass.specified=true");

        // Get all the elementKindList where gramPerMeterLinearMass is null
        defaultElementKindShouldNotBeFound("gramPerMeterLinearMass.specified=false");
    }

    @Test
    @Transactional
    void getAllElementKindsByGramPerMeterLinearMassIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        // Get all the elementKindList where gramPerMeterLinearMass is greater than or equal to DEFAULT_GRAM_PER_METER_LINEAR_MASS
        defaultElementKindShouldBeFound("gramPerMeterLinearMass.greaterThanOrEqual=" + DEFAULT_GRAM_PER_METER_LINEAR_MASS);

        // Get all the elementKindList where gramPerMeterLinearMass is greater than or equal to UPDATED_GRAM_PER_METER_LINEAR_MASS
        defaultElementKindShouldNotBeFound("gramPerMeterLinearMass.greaterThanOrEqual=" + UPDATED_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllElementKindsByGramPerMeterLinearMassIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        // Get all the elementKindList where gramPerMeterLinearMass is less than or equal to DEFAULT_GRAM_PER_METER_LINEAR_MASS
        defaultElementKindShouldBeFound("gramPerMeterLinearMass.lessThanOrEqual=" + DEFAULT_GRAM_PER_METER_LINEAR_MASS);

        // Get all the elementKindList where gramPerMeterLinearMass is less than or equal to SMALLER_GRAM_PER_METER_LINEAR_MASS
        defaultElementKindShouldNotBeFound("gramPerMeterLinearMass.lessThanOrEqual=" + SMALLER_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllElementKindsByGramPerMeterLinearMassIsLessThanSomething() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        // Get all the elementKindList where gramPerMeterLinearMass is less than DEFAULT_GRAM_PER_METER_LINEAR_MASS
        defaultElementKindShouldNotBeFound("gramPerMeterLinearMass.lessThan=" + DEFAULT_GRAM_PER_METER_LINEAR_MASS);

        // Get all the elementKindList where gramPerMeterLinearMass is less than UPDATED_GRAM_PER_METER_LINEAR_MASS
        defaultElementKindShouldBeFound("gramPerMeterLinearMass.lessThan=" + UPDATED_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllElementKindsByGramPerMeterLinearMassIsGreaterThanSomething() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        // Get all the elementKindList where gramPerMeterLinearMass is greater than DEFAULT_GRAM_PER_METER_LINEAR_MASS
        defaultElementKindShouldNotBeFound("gramPerMeterLinearMass.greaterThan=" + DEFAULT_GRAM_PER_METER_LINEAR_MASS);

        // Get all the elementKindList where gramPerMeterLinearMass is greater than SMALLER_GRAM_PER_METER_LINEAR_MASS
        defaultElementKindShouldBeFound("gramPerMeterLinearMass.greaterThan=" + SMALLER_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllElementKindsByMilimeterDiameterIsEqualToSomething() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        // Get all the elementKindList where milimeterDiameter equals to DEFAULT_MILIMETER_DIAMETER
        defaultElementKindShouldBeFound("milimeterDiameter.equals=" + DEFAULT_MILIMETER_DIAMETER);

        // Get all the elementKindList where milimeterDiameter equals to UPDATED_MILIMETER_DIAMETER
        defaultElementKindShouldNotBeFound("milimeterDiameter.equals=" + UPDATED_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllElementKindsByMilimeterDiameterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        // Get all the elementKindList where milimeterDiameter not equals to DEFAULT_MILIMETER_DIAMETER
        defaultElementKindShouldNotBeFound("milimeterDiameter.notEquals=" + DEFAULT_MILIMETER_DIAMETER);

        // Get all the elementKindList where milimeterDiameter not equals to UPDATED_MILIMETER_DIAMETER
        defaultElementKindShouldBeFound("milimeterDiameter.notEquals=" + UPDATED_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllElementKindsByMilimeterDiameterIsInShouldWork() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        // Get all the elementKindList where milimeterDiameter in DEFAULT_MILIMETER_DIAMETER or UPDATED_MILIMETER_DIAMETER
        defaultElementKindShouldBeFound("milimeterDiameter.in=" + DEFAULT_MILIMETER_DIAMETER + "," + UPDATED_MILIMETER_DIAMETER);

        // Get all the elementKindList where milimeterDiameter equals to UPDATED_MILIMETER_DIAMETER
        defaultElementKindShouldNotBeFound("milimeterDiameter.in=" + UPDATED_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllElementKindsByMilimeterDiameterIsNullOrNotNull() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        // Get all the elementKindList where milimeterDiameter is not null
        defaultElementKindShouldBeFound("milimeterDiameter.specified=true");

        // Get all the elementKindList where milimeterDiameter is null
        defaultElementKindShouldNotBeFound("milimeterDiameter.specified=false");
    }

    @Test
    @Transactional
    void getAllElementKindsByMilimeterDiameterIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        // Get all the elementKindList where milimeterDiameter is greater than or equal to DEFAULT_MILIMETER_DIAMETER
        defaultElementKindShouldBeFound("milimeterDiameter.greaterThanOrEqual=" + DEFAULT_MILIMETER_DIAMETER);

        // Get all the elementKindList where milimeterDiameter is greater than or equal to UPDATED_MILIMETER_DIAMETER
        defaultElementKindShouldNotBeFound("milimeterDiameter.greaterThanOrEqual=" + UPDATED_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllElementKindsByMilimeterDiameterIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        // Get all the elementKindList where milimeterDiameter is less than or equal to DEFAULT_MILIMETER_DIAMETER
        defaultElementKindShouldBeFound("milimeterDiameter.lessThanOrEqual=" + DEFAULT_MILIMETER_DIAMETER);

        // Get all the elementKindList where milimeterDiameter is less than or equal to SMALLER_MILIMETER_DIAMETER
        defaultElementKindShouldNotBeFound("milimeterDiameter.lessThanOrEqual=" + SMALLER_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllElementKindsByMilimeterDiameterIsLessThanSomething() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        // Get all the elementKindList where milimeterDiameter is less than DEFAULT_MILIMETER_DIAMETER
        defaultElementKindShouldNotBeFound("milimeterDiameter.lessThan=" + DEFAULT_MILIMETER_DIAMETER);

        // Get all the elementKindList where milimeterDiameter is less than UPDATED_MILIMETER_DIAMETER
        defaultElementKindShouldBeFound("milimeterDiameter.lessThan=" + UPDATED_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllElementKindsByMilimeterDiameterIsGreaterThanSomething() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        // Get all the elementKindList where milimeterDiameter is greater than DEFAULT_MILIMETER_DIAMETER
        defaultElementKindShouldNotBeFound("milimeterDiameter.greaterThan=" + DEFAULT_MILIMETER_DIAMETER);

        // Get all the elementKindList where milimeterDiameter is greater than SMALLER_MILIMETER_DIAMETER
        defaultElementKindShouldBeFound("milimeterDiameter.greaterThan=" + SMALLER_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllElementKindsByInsulationThicknessIsEqualToSomething() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        // Get all the elementKindList where insulationThickness equals to DEFAULT_INSULATION_THICKNESS
        defaultElementKindShouldBeFound("insulationThickness.equals=" + DEFAULT_INSULATION_THICKNESS);

        // Get all the elementKindList where insulationThickness equals to UPDATED_INSULATION_THICKNESS
        defaultElementKindShouldNotBeFound("insulationThickness.equals=" + UPDATED_INSULATION_THICKNESS);
    }

    @Test
    @Transactional
    void getAllElementKindsByInsulationThicknessIsNotEqualToSomething() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        // Get all the elementKindList where insulationThickness not equals to DEFAULT_INSULATION_THICKNESS
        defaultElementKindShouldNotBeFound("insulationThickness.notEquals=" + DEFAULT_INSULATION_THICKNESS);

        // Get all the elementKindList where insulationThickness not equals to UPDATED_INSULATION_THICKNESS
        defaultElementKindShouldBeFound("insulationThickness.notEquals=" + UPDATED_INSULATION_THICKNESS);
    }

    @Test
    @Transactional
    void getAllElementKindsByInsulationThicknessIsInShouldWork() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        // Get all the elementKindList where insulationThickness in DEFAULT_INSULATION_THICKNESS or UPDATED_INSULATION_THICKNESS
        defaultElementKindShouldBeFound("insulationThickness.in=" + DEFAULT_INSULATION_THICKNESS + "," + UPDATED_INSULATION_THICKNESS);

        // Get all the elementKindList where insulationThickness equals to UPDATED_INSULATION_THICKNESS
        defaultElementKindShouldNotBeFound("insulationThickness.in=" + UPDATED_INSULATION_THICKNESS);
    }

    @Test
    @Transactional
    void getAllElementKindsByInsulationThicknessIsNullOrNotNull() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        // Get all the elementKindList where insulationThickness is not null
        defaultElementKindShouldBeFound("insulationThickness.specified=true");

        // Get all the elementKindList where insulationThickness is null
        defaultElementKindShouldNotBeFound("insulationThickness.specified=false");
    }

    @Test
    @Transactional
    void getAllElementKindsByInsulationThicknessIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        // Get all the elementKindList where insulationThickness is greater than or equal to DEFAULT_INSULATION_THICKNESS
        defaultElementKindShouldBeFound("insulationThickness.greaterThanOrEqual=" + DEFAULT_INSULATION_THICKNESS);

        // Get all the elementKindList where insulationThickness is greater than or equal to UPDATED_INSULATION_THICKNESS
        defaultElementKindShouldNotBeFound("insulationThickness.greaterThanOrEqual=" + UPDATED_INSULATION_THICKNESS);
    }

    @Test
    @Transactional
    void getAllElementKindsByInsulationThicknessIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        // Get all the elementKindList where insulationThickness is less than or equal to DEFAULT_INSULATION_THICKNESS
        defaultElementKindShouldBeFound("insulationThickness.lessThanOrEqual=" + DEFAULT_INSULATION_THICKNESS);

        // Get all the elementKindList where insulationThickness is less than or equal to SMALLER_INSULATION_THICKNESS
        defaultElementKindShouldNotBeFound("insulationThickness.lessThanOrEqual=" + SMALLER_INSULATION_THICKNESS);
    }

    @Test
    @Transactional
    void getAllElementKindsByInsulationThicknessIsLessThanSomething() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        // Get all the elementKindList where insulationThickness is less than DEFAULT_INSULATION_THICKNESS
        defaultElementKindShouldNotBeFound("insulationThickness.lessThan=" + DEFAULT_INSULATION_THICKNESS);

        // Get all the elementKindList where insulationThickness is less than UPDATED_INSULATION_THICKNESS
        defaultElementKindShouldBeFound("insulationThickness.lessThan=" + UPDATED_INSULATION_THICKNESS);
    }

    @Test
    @Transactional
    void getAllElementKindsByInsulationThicknessIsGreaterThanSomething() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        // Get all the elementKindList where insulationThickness is greater than DEFAULT_INSULATION_THICKNESS
        defaultElementKindShouldNotBeFound("insulationThickness.greaterThan=" + DEFAULT_INSULATION_THICKNESS);

        // Get all the elementKindList where insulationThickness is greater than SMALLER_INSULATION_THICKNESS
        defaultElementKindShouldBeFound("insulationThickness.greaterThan=" + SMALLER_INSULATION_THICKNESS);
    }

    @Test
    @Transactional
    void getAllElementKindsByCopperIsEqualToSomething() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);
        Copper copper;
        if (TestUtil.findAll(em, Copper.class).isEmpty()) {
            copper = CopperResourceIT.createEntity(em);
            em.persist(copper);
            em.flush();
        } else {
            copper = TestUtil.findAll(em, Copper.class).get(0);
        }
        em.persist(copper);
        em.flush();
        elementKind.setCopper(copper);
        elementKindRepository.saveAndFlush(elementKind);
        Long copperId = copper.getId();

        // Get all the elementKindList where copper equals to copperId
        defaultElementKindShouldBeFound("copperId.equals=" + copperId);

        // Get all the elementKindList where copper equals to (copperId + 1)
        defaultElementKindShouldNotBeFound("copperId.equals=" + (copperId + 1));
    }

    @Test
    @Transactional
    void getAllElementKindsByInsulationMaterialIsEqualToSomething() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);
        Material insulationMaterial;
        if (TestUtil.findAll(em, Material.class).isEmpty()) {
            insulationMaterial = MaterialResourceIT.createEntity(em);
            em.persist(insulationMaterial);
            em.flush();
        } else {
            insulationMaterial = TestUtil.findAll(em, Material.class).get(0);
        }
        em.persist(insulationMaterial);
        em.flush();
        elementKind.setInsulationMaterial(insulationMaterial);
        elementKindRepository.saveAndFlush(elementKind);
        Long insulationMaterialId = insulationMaterial.getId();

        // Get all the elementKindList where insulationMaterial equals to insulationMaterialId
        defaultElementKindShouldBeFound("insulationMaterialId.equals=" + insulationMaterialId);

        // Get all the elementKindList where insulationMaterial equals to (insulationMaterialId + 1)
        defaultElementKindShouldNotBeFound("insulationMaterialId.equals=" + (insulationMaterialId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultElementKindShouldBeFound(String filter) throws Exception {
        restElementKindMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(elementKind.getId().intValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].gramPerMeterLinearMass").value(hasItem(DEFAULT_GRAM_PER_METER_LINEAR_MASS.doubleValue())))
            .andExpect(jsonPath("$.[*].milimeterDiameter").value(hasItem(DEFAULT_MILIMETER_DIAMETER.doubleValue())))
            .andExpect(jsonPath("$.[*].insulationThickness").value(hasItem(DEFAULT_INSULATION_THICKNESS.doubleValue())));

        // Check, that the count call also returns 1
        restElementKindMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultElementKindShouldNotBeFound(String filter) throws Exception {
        restElementKindMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restElementKindMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingElementKind() throws Exception {
        // Get the elementKind
        restElementKindMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewElementKind() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        int databaseSizeBeforeUpdate = elementKindRepository.findAll().size();

        // Update the elementKind
        ElementKind updatedElementKind = elementKindRepository.findById(elementKind.getId()).get();
        // Disconnect from session so that the updates on updatedElementKind are not directly saved in db
        em.detach(updatedElementKind);
        updatedElementKind
            .designation(UPDATED_DESIGNATION)
            .gramPerMeterLinearMass(UPDATED_GRAM_PER_METER_LINEAR_MASS)
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER)
            .insulationThickness(UPDATED_INSULATION_THICKNESS);

        restElementKindMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedElementKind.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedElementKind))
            )
            //.andExpect(status().isOk());
            .andExpect(status().isBadRequest());

        // Validate the ElementKind in the database
        List<ElementKind> elementKindList = elementKindRepository.findAll();
        assertThat(elementKindList).hasSize(databaseSizeBeforeUpdate);
        ElementKind testElementKind = elementKindList.get(elementKindList.size() - 1);
        assertThat(testElementKind.getDesignation()).isEqualTo(DEFAULT_DESIGNATION); //UPDATED_DESIGNATION);
        assertThat(testElementKind.getGramPerMeterLinearMass()).isEqualTo(DEFAULT_GRAM_PER_METER_LINEAR_MASS); //UPDATED_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testElementKind.getMilimeterDiameter()).isEqualTo(DEFAULT_MILIMETER_DIAMETER); //UPDATED_MILIMETER_DIAMETER);
        assertThat(testElementKind.getInsulationThickness()).isEqualTo(DEFAULT_INSULATION_THICKNESS); //UPDATED_INSULATION_THICKNESS);
    }

    /*
    @Test
    @Transactional
    void putNonExistingElementKind() throws Exception {
        int databaseSizeBeforeUpdate = elementKindRepository.findAll().size();
        elementKind.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restElementKindMockMvc
            .perform(
                put(ENTITY_API_URL_ID, elementKind.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(elementKind))
            )
            .andExpect(status().isBadRequest());

        // Validate the ElementKind in the database
        List<ElementKind> elementKindList = elementKindRepository.findAll();
        assertThat(elementKindList).hasSize(databaseSizeBeforeUpdate);
    }*/
    /*
    @Test
    @Transactional
    void putWithIdMismatchElementKind() throws Exception {
        int databaseSizeBeforeUpdate = elementKindRepository.findAll().size();
        elementKind.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restElementKindMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(elementKind))
            )
            .andExpect(status().isBadRequest());

        // Validate the ElementKind in the database
        List<ElementKind> elementKindList = elementKindRepository.findAll();
        assertThat(elementKindList).hasSize(databaseSizeBeforeUpdate);
    }*/
    /*
    @Test
    @Transactional
    void putWithMissingIdPathParamElementKind() throws Exception {
        int databaseSizeBeforeUpdate = elementKindRepository.findAll().size();
        elementKind.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restElementKindMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(elementKind)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ElementKind in the database
        List<ElementKind> elementKindList = elementKindRepository.findAll();
        assertThat(elementKindList).hasSize(databaseSizeBeforeUpdate);
    }*/
    /*
    @Test
    @Transactional
    void partialUpdateElementKindWithPatch() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        int databaseSizeBeforeUpdate = elementKindRepository.findAll().size();

        // Update the elementKind using partial update
        ElementKind partialUpdatedElementKind = new ElementKind();
        partialUpdatedElementKind.setId(elementKind.getId());

        partialUpdatedElementKind
            .designation(UPDATED_DESIGNATION)
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER)
            .insulationThickness(UPDATED_INSULATION_THICKNESS);

        restElementKindMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedElementKind.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedElementKind))
            )
            .andExpect(status().isOk());

        // Validate the ElementKind in the database
        List<ElementKind> elementKindList = elementKindRepository.findAll();
        assertThat(elementKindList).hasSize(databaseSizeBeforeUpdate);
        ElementKind testElementKind = elementKindList.get(elementKindList.size() - 1);
        assertThat(testElementKind.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testElementKind.getGramPerMeterLinearMass()).isEqualTo(DEFAULT_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testElementKind.getMilimeterDiameter()).isEqualTo(UPDATED_MILIMETER_DIAMETER);
        assertThat(testElementKind.getInsulationThickness()).isEqualTo(UPDATED_INSULATION_THICKNESS);
    }*/
    /*
    @Test
    @Transactional
    void fullUpdateElementKindWithPatch() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        int databaseSizeBeforeUpdate = elementKindRepository.findAll().size();

        // Update the elementKind using partial update
        ElementKind partialUpdatedElementKind = new ElementKind();
        partialUpdatedElementKind.setId(elementKind.getId());

        partialUpdatedElementKind
            .designation(UPDATED_DESIGNATION)
            .gramPerMeterLinearMass(UPDATED_GRAM_PER_METER_LINEAR_MASS)
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER)
            .insulationThickness(UPDATED_INSULATION_THICKNESS);

        restElementKindMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedElementKind.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedElementKind))
            )
            .andExpect(status().isOk());

        // Validate the ElementKind in the database
        List<ElementKind> elementKindList = elementKindRepository.findAll();
        assertThat(elementKindList).hasSize(databaseSizeBeforeUpdate);
        ElementKind testElementKind = elementKindList.get(elementKindList.size() - 1);
        assertThat(testElementKind.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testElementKind.getGramPerMeterLinearMass()).isEqualTo(UPDATED_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testElementKind.getMilimeterDiameter()).isEqualTo(UPDATED_MILIMETER_DIAMETER);
        assertThat(testElementKind.getInsulationThickness()).isEqualTo(UPDATED_INSULATION_THICKNESS);
    }*/
    /*
    @Test
    @Transactional
    void patchNonExistingElementKind() throws Exception {
        int databaseSizeBeforeUpdate = elementKindRepository.findAll().size();
        elementKind.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restElementKindMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, elementKind.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(elementKind))
            )
            .andExpect(status().isBadRequest());

        // Validate the ElementKind in the database
        List<ElementKind> elementKindList = elementKindRepository.findAll();
        assertThat(elementKindList).hasSize(databaseSizeBeforeUpdate);
    }*/
    /*
    @Test
    @Transactional
    void patchWithIdMismatchElementKind() throws Exception {
        int databaseSizeBeforeUpdate = elementKindRepository.findAll().size();
        elementKind.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restElementKindMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(elementKind))
            )
            .andExpect(status().isBadRequest());

        // Validate the ElementKind in the database
        List<ElementKind> elementKindList = elementKindRepository.findAll();
        assertThat(elementKindList).hasSize(databaseSizeBeforeUpdate);
    }*/
    /*
    @Test
    @Transactional
    void patchWithMissingIdPathParamElementKind() throws Exception {
        int databaseSizeBeforeUpdate = elementKindRepository.findAll().size();
        elementKind.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restElementKindMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(elementKind))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ElementKind in the database
        List<ElementKind> elementKindList = elementKindRepository.findAll();
        assertThat(elementKindList).hasSize(databaseSizeBeforeUpdate);
    }*/

    @Test
    @Transactional
    void deleteElementKind() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        int databaseSizeBeforeDelete = elementKindRepository.findAll().size();

        // Delete the elementKind
        restElementKindMockMvc
            .perform(delete(ENTITY_API_URL_ID, elementKind.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ElementKind> elementKindList = elementKindRepository.findAll();
        assertThat(elementKindList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
