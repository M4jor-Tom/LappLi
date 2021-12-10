package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.CustomComponent;
import com.muller.lappli.domain.Material;
import com.muller.lappli.repository.CustomComponentRepository;
import com.muller.lappli.service.criteria.CustomComponentCriteria;
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
 * Integration tests for the {@link CustomComponentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustomComponentResourceIT {

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

    private static final String ENTITY_API_URL = "/api/custom-components";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustomComponentRepository customComponentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomComponentMockMvc;

    private CustomComponent customComponent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomComponent createEntity(EntityManager em) {
        CustomComponent customComponent = new CustomComponent()
            .number(DEFAULT_NUMBER)
            .designation(DEFAULT_DESIGNATION)
            .gramPerMeterLinearMass(DEFAULT_GRAM_PER_METER_LINEAR_MASS)
            .milimeterDiameter(DEFAULT_MILIMETER_DIAMETER);
        // Add required entity
        Material material;
        if (TestUtil.findAll(em, Material.class).isEmpty()) {
            material = MaterialResourceIT.createEntity(em);
            em.persist(material);
            em.flush();
        } else {
            material = TestUtil.findAll(em, Material.class).get(0);
        }
        customComponent.setSurfaceMaterial(material);
        return customComponent;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomComponent createUpdatedEntity(EntityManager em) {
        CustomComponent customComponent = new CustomComponent()
            .number(UPDATED_NUMBER)
            .designation(UPDATED_DESIGNATION)
            .gramPerMeterLinearMass(UPDATED_GRAM_PER_METER_LINEAR_MASS)
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER);
        // Add required entity
        Material material;
        if (TestUtil.findAll(em, Material.class).isEmpty()) {
            material = MaterialResourceIT.createUpdatedEntity(em);
            em.persist(material);
            em.flush();
        } else {
            material = TestUtil.findAll(em, Material.class).get(0);
        }
        customComponent.setSurfaceMaterial(material);
        return customComponent;
    }

    @BeforeEach
    public void initTest() {
        customComponent = createEntity(em);
    }

    @Test
    @Transactional
    void createCustomComponent() throws Exception {
        int databaseSizeBeforeCreate = customComponentRepository.findAll().size();
        // Create the CustomComponent
        restCustomComponentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customComponent))
            )
            .andExpect(status().isCreated());

        // Validate the CustomComponent in the database
        List<CustomComponent> customComponentList = customComponentRepository.findAll();
        assertThat(customComponentList).hasSize(databaseSizeBeforeCreate + 1);
        CustomComponent testCustomComponent = customComponentList.get(customComponentList.size() - 1);
        assertThat(testCustomComponent.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testCustomComponent.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testCustomComponent.getGramPerMeterLinearMass()).isEqualTo(DEFAULT_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testCustomComponent.getMilimeterDiameter()).isEqualTo(DEFAULT_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void createCustomComponentWithExistingId() throws Exception {
        // Create the CustomComponent with an existing ID
        customComponent.setId(1L);

        int databaseSizeBeforeCreate = customComponentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomComponentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customComponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomComponent in the database
        List<CustomComponent> customComponentList = customComponentRepository.findAll();
        assertThat(customComponentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkGramPerMeterLinearMassIsRequired() throws Exception {
        int databaseSizeBeforeTest = customComponentRepository.findAll().size();
        // set the field null
        customComponent.setGramPerMeterLinearMass(null);

        // Create the CustomComponent, which fails.

        restCustomComponentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customComponent))
            )
            .andExpect(status().isBadRequest());

        List<CustomComponent> customComponentList = customComponentRepository.findAll();
        assertThat(customComponentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMilimeterDiameterIsRequired() throws Exception {
        int databaseSizeBeforeTest = customComponentRepository.findAll().size();
        // set the field null
        customComponent.setMilimeterDiameter(null);

        // Create the CustomComponent, which fails.

        restCustomComponentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customComponent))
            )
            .andExpect(status().isBadRequest());

        List<CustomComponent> customComponentList = customComponentRepository.findAll();
        assertThat(customComponentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustomComponents() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        // Get all the customComponentList
        restCustomComponentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customComponent.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].gramPerMeterLinearMass").value(hasItem(DEFAULT_GRAM_PER_METER_LINEAR_MASS.doubleValue())))
            .andExpect(jsonPath("$.[*].milimeterDiameter").value(hasItem(DEFAULT_MILIMETER_DIAMETER.doubleValue())));
    }

    @Test
    @Transactional
    void getCustomComponent() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        // Get the customComponent
        restCustomComponentMockMvc
            .perform(get(ENTITY_API_URL_ID, customComponent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customComponent.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.intValue()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.gramPerMeterLinearMass").value(DEFAULT_GRAM_PER_METER_LINEAR_MASS.doubleValue()))
            .andExpect(jsonPath("$.milimeterDiameter").value(DEFAULT_MILIMETER_DIAMETER.doubleValue()));
    }

    @Test
    @Transactional
    void getCustomComponentsByIdFiltering() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        Long id = customComponent.getId();

        defaultCustomComponentShouldBeFound("id.equals=" + id);
        defaultCustomComponentShouldNotBeFound("id.notEquals=" + id);

        defaultCustomComponentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustomComponentShouldNotBeFound("id.greaterThan=" + id);

        defaultCustomComponentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustomComponentShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCustomComponentsByNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        // Get all the customComponentList where number equals to DEFAULT_NUMBER
        defaultCustomComponentShouldBeFound("number.equals=" + DEFAULT_NUMBER);

        // Get all the customComponentList where number equals to UPDATED_NUMBER
        defaultCustomComponentShouldNotBeFound("number.equals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustomComponentsByNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        // Get all the customComponentList where number not equals to DEFAULT_NUMBER
        defaultCustomComponentShouldNotBeFound("number.notEquals=" + DEFAULT_NUMBER);

        // Get all the customComponentList where number not equals to UPDATED_NUMBER
        defaultCustomComponentShouldBeFound("number.notEquals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustomComponentsByNumberIsInShouldWork() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        // Get all the customComponentList where number in DEFAULT_NUMBER or UPDATED_NUMBER
        defaultCustomComponentShouldBeFound("number.in=" + DEFAULT_NUMBER + "," + UPDATED_NUMBER);

        // Get all the customComponentList where number equals to UPDATED_NUMBER
        defaultCustomComponentShouldNotBeFound("number.in=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustomComponentsByNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        // Get all the customComponentList where number is not null
        defaultCustomComponentShouldBeFound("number.specified=true");

        // Get all the customComponentList where number is null
        defaultCustomComponentShouldNotBeFound("number.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomComponentsByNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        // Get all the customComponentList where number is greater than or equal to DEFAULT_NUMBER
        defaultCustomComponentShouldBeFound("number.greaterThanOrEqual=" + DEFAULT_NUMBER);

        // Get all the customComponentList where number is greater than or equal to UPDATED_NUMBER
        defaultCustomComponentShouldNotBeFound("number.greaterThanOrEqual=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustomComponentsByNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        // Get all the customComponentList where number is less than or equal to DEFAULT_NUMBER
        defaultCustomComponentShouldBeFound("number.lessThanOrEqual=" + DEFAULT_NUMBER);

        // Get all the customComponentList where number is less than or equal to SMALLER_NUMBER
        defaultCustomComponentShouldNotBeFound("number.lessThanOrEqual=" + SMALLER_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustomComponentsByNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        // Get all the customComponentList where number is less than DEFAULT_NUMBER
        defaultCustomComponentShouldNotBeFound("number.lessThan=" + DEFAULT_NUMBER);

        // Get all the customComponentList where number is less than UPDATED_NUMBER
        defaultCustomComponentShouldBeFound("number.lessThan=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustomComponentsByNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        // Get all the customComponentList where number is greater than DEFAULT_NUMBER
        defaultCustomComponentShouldNotBeFound("number.greaterThan=" + DEFAULT_NUMBER);

        // Get all the customComponentList where number is greater than SMALLER_NUMBER
        defaultCustomComponentShouldBeFound("number.greaterThan=" + SMALLER_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustomComponentsByDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        // Get all the customComponentList where designation equals to DEFAULT_DESIGNATION
        defaultCustomComponentShouldBeFound("designation.equals=" + DEFAULT_DESIGNATION);

        // Get all the customComponentList where designation equals to UPDATED_DESIGNATION
        defaultCustomComponentShouldNotBeFound("designation.equals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllCustomComponentsByDesignationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        // Get all the customComponentList where designation not equals to DEFAULT_DESIGNATION
        defaultCustomComponentShouldNotBeFound("designation.notEquals=" + DEFAULT_DESIGNATION);

        // Get all the customComponentList where designation not equals to UPDATED_DESIGNATION
        defaultCustomComponentShouldBeFound("designation.notEquals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllCustomComponentsByDesignationIsInShouldWork() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        // Get all the customComponentList where designation in DEFAULT_DESIGNATION or UPDATED_DESIGNATION
        defaultCustomComponentShouldBeFound("designation.in=" + DEFAULT_DESIGNATION + "," + UPDATED_DESIGNATION);

        // Get all the customComponentList where designation equals to UPDATED_DESIGNATION
        defaultCustomComponentShouldNotBeFound("designation.in=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllCustomComponentsByDesignationIsNullOrNotNull() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        // Get all the customComponentList where designation is not null
        defaultCustomComponentShouldBeFound("designation.specified=true");

        // Get all the customComponentList where designation is null
        defaultCustomComponentShouldNotBeFound("designation.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomComponentsByDesignationContainsSomething() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        // Get all the customComponentList where designation contains DEFAULT_DESIGNATION
        defaultCustomComponentShouldBeFound("designation.contains=" + DEFAULT_DESIGNATION);

        // Get all the customComponentList where designation contains UPDATED_DESIGNATION
        defaultCustomComponentShouldNotBeFound("designation.contains=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllCustomComponentsByDesignationNotContainsSomething() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        // Get all the customComponentList where designation does not contain DEFAULT_DESIGNATION
        defaultCustomComponentShouldNotBeFound("designation.doesNotContain=" + DEFAULT_DESIGNATION);

        // Get all the customComponentList where designation does not contain UPDATED_DESIGNATION
        defaultCustomComponentShouldBeFound("designation.doesNotContain=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllCustomComponentsByGramPerMeterLinearMassIsEqualToSomething() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        // Get all the customComponentList where gramPerMeterLinearMass equals to DEFAULT_GRAM_PER_METER_LINEAR_MASS
        defaultCustomComponentShouldBeFound("gramPerMeterLinearMass.equals=" + DEFAULT_GRAM_PER_METER_LINEAR_MASS);

        // Get all the customComponentList where gramPerMeterLinearMass equals to UPDATED_GRAM_PER_METER_LINEAR_MASS
        defaultCustomComponentShouldNotBeFound("gramPerMeterLinearMass.equals=" + UPDATED_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllCustomComponentsByGramPerMeterLinearMassIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        // Get all the customComponentList where gramPerMeterLinearMass not equals to DEFAULT_GRAM_PER_METER_LINEAR_MASS
        defaultCustomComponentShouldNotBeFound("gramPerMeterLinearMass.notEquals=" + DEFAULT_GRAM_PER_METER_LINEAR_MASS);

        // Get all the customComponentList where gramPerMeterLinearMass not equals to UPDATED_GRAM_PER_METER_LINEAR_MASS
        defaultCustomComponentShouldBeFound("gramPerMeterLinearMass.notEquals=" + UPDATED_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllCustomComponentsByGramPerMeterLinearMassIsInShouldWork() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        // Get all the customComponentList where gramPerMeterLinearMass in DEFAULT_GRAM_PER_METER_LINEAR_MASS or UPDATED_GRAM_PER_METER_LINEAR_MASS
        defaultCustomComponentShouldBeFound(
            "gramPerMeterLinearMass.in=" + DEFAULT_GRAM_PER_METER_LINEAR_MASS + "," + UPDATED_GRAM_PER_METER_LINEAR_MASS
        );

        // Get all the customComponentList where gramPerMeterLinearMass equals to UPDATED_GRAM_PER_METER_LINEAR_MASS
        defaultCustomComponentShouldNotBeFound("gramPerMeterLinearMass.in=" + UPDATED_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllCustomComponentsByGramPerMeterLinearMassIsNullOrNotNull() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        // Get all the customComponentList where gramPerMeterLinearMass is not null
        defaultCustomComponentShouldBeFound("gramPerMeterLinearMass.specified=true");

        // Get all the customComponentList where gramPerMeterLinearMass is null
        defaultCustomComponentShouldNotBeFound("gramPerMeterLinearMass.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomComponentsByGramPerMeterLinearMassIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        // Get all the customComponentList where gramPerMeterLinearMass is greater than or equal to DEFAULT_GRAM_PER_METER_LINEAR_MASS
        defaultCustomComponentShouldBeFound("gramPerMeterLinearMass.greaterThanOrEqual=" + DEFAULT_GRAM_PER_METER_LINEAR_MASS);

        // Get all the customComponentList where gramPerMeterLinearMass is greater than or equal to UPDATED_GRAM_PER_METER_LINEAR_MASS
        defaultCustomComponentShouldNotBeFound("gramPerMeterLinearMass.greaterThanOrEqual=" + UPDATED_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllCustomComponentsByGramPerMeterLinearMassIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        // Get all the customComponentList where gramPerMeterLinearMass is less than or equal to DEFAULT_GRAM_PER_METER_LINEAR_MASS
        defaultCustomComponentShouldBeFound("gramPerMeterLinearMass.lessThanOrEqual=" + DEFAULT_GRAM_PER_METER_LINEAR_MASS);

        // Get all the customComponentList where gramPerMeterLinearMass is less than or equal to SMALLER_GRAM_PER_METER_LINEAR_MASS
        defaultCustomComponentShouldNotBeFound("gramPerMeterLinearMass.lessThanOrEqual=" + SMALLER_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllCustomComponentsByGramPerMeterLinearMassIsLessThanSomething() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        // Get all the customComponentList where gramPerMeterLinearMass is less than DEFAULT_GRAM_PER_METER_LINEAR_MASS
        defaultCustomComponentShouldNotBeFound("gramPerMeterLinearMass.lessThan=" + DEFAULT_GRAM_PER_METER_LINEAR_MASS);

        // Get all the customComponentList where gramPerMeterLinearMass is less than UPDATED_GRAM_PER_METER_LINEAR_MASS
        defaultCustomComponentShouldBeFound("gramPerMeterLinearMass.lessThan=" + UPDATED_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllCustomComponentsByGramPerMeterLinearMassIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        // Get all the customComponentList where gramPerMeterLinearMass is greater than DEFAULT_GRAM_PER_METER_LINEAR_MASS
        defaultCustomComponentShouldNotBeFound("gramPerMeterLinearMass.greaterThan=" + DEFAULT_GRAM_PER_METER_LINEAR_MASS);

        // Get all the customComponentList where gramPerMeterLinearMass is greater than SMALLER_GRAM_PER_METER_LINEAR_MASS
        defaultCustomComponentShouldBeFound("gramPerMeterLinearMass.greaterThan=" + SMALLER_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllCustomComponentsByMilimeterDiameterIsEqualToSomething() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        // Get all the customComponentList where milimeterDiameter equals to DEFAULT_MILIMETER_DIAMETER
        defaultCustomComponentShouldBeFound("milimeterDiameter.equals=" + DEFAULT_MILIMETER_DIAMETER);

        // Get all the customComponentList where milimeterDiameter equals to UPDATED_MILIMETER_DIAMETER
        defaultCustomComponentShouldNotBeFound("milimeterDiameter.equals=" + UPDATED_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllCustomComponentsByMilimeterDiameterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        // Get all the customComponentList where milimeterDiameter not equals to DEFAULT_MILIMETER_DIAMETER
        defaultCustomComponentShouldNotBeFound("milimeterDiameter.notEquals=" + DEFAULT_MILIMETER_DIAMETER);

        // Get all the customComponentList where milimeterDiameter not equals to UPDATED_MILIMETER_DIAMETER
        defaultCustomComponentShouldBeFound("milimeterDiameter.notEquals=" + UPDATED_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllCustomComponentsByMilimeterDiameterIsInShouldWork() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        // Get all the customComponentList where milimeterDiameter in DEFAULT_MILIMETER_DIAMETER or UPDATED_MILIMETER_DIAMETER
        defaultCustomComponentShouldBeFound("milimeterDiameter.in=" + DEFAULT_MILIMETER_DIAMETER + "," + UPDATED_MILIMETER_DIAMETER);

        // Get all the customComponentList where milimeterDiameter equals to UPDATED_MILIMETER_DIAMETER
        defaultCustomComponentShouldNotBeFound("milimeterDiameter.in=" + UPDATED_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllCustomComponentsByMilimeterDiameterIsNullOrNotNull() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        // Get all the customComponentList where milimeterDiameter is not null
        defaultCustomComponentShouldBeFound("milimeterDiameter.specified=true");

        // Get all the customComponentList where milimeterDiameter is null
        defaultCustomComponentShouldNotBeFound("milimeterDiameter.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomComponentsByMilimeterDiameterIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        // Get all the customComponentList where milimeterDiameter is greater than or equal to DEFAULT_MILIMETER_DIAMETER
        defaultCustomComponentShouldBeFound("milimeterDiameter.greaterThanOrEqual=" + DEFAULT_MILIMETER_DIAMETER);

        // Get all the customComponentList where milimeterDiameter is greater than or equal to UPDATED_MILIMETER_DIAMETER
        defaultCustomComponentShouldNotBeFound("milimeterDiameter.greaterThanOrEqual=" + UPDATED_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllCustomComponentsByMilimeterDiameterIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        // Get all the customComponentList where milimeterDiameter is less than or equal to DEFAULT_MILIMETER_DIAMETER
        defaultCustomComponentShouldBeFound("milimeterDiameter.lessThanOrEqual=" + DEFAULT_MILIMETER_DIAMETER);

        // Get all the customComponentList where milimeterDiameter is less than or equal to SMALLER_MILIMETER_DIAMETER
        defaultCustomComponentShouldNotBeFound("milimeterDiameter.lessThanOrEqual=" + SMALLER_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllCustomComponentsByMilimeterDiameterIsLessThanSomething() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        // Get all the customComponentList where milimeterDiameter is less than DEFAULT_MILIMETER_DIAMETER
        defaultCustomComponentShouldNotBeFound("milimeterDiameter.lessThan=" + DEFAULT_MILIMETER_DIAMETER);

        // Get all the customComponentList where milimeterDiameter is less than UPDATED_MILIMETER_DIAMETER
        defaultCustomComponentShouldBeFound("milimeterDiameter.lessThan=" + UPDATED_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllCustomComponentsByMilimeterDiameterIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        // Get all the customComponentList where milimeterDiameter is greater than DEFAULT_MILIMETER_DIAMETER
        defaultCustomComponentShouldNotBeFound("milimeterDiameter.greaterThan=" + DEFAULT_MILIMETER_DIAMETER);

        // Get all the customComponentList where milimeterDiameter is greater than SMALLER_MILIMETER_DIAMETER
        defaultCustomComponentShouldBeFound("milimeterDiameter.greaterThan=" + SMALLER_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void getAllCustomComponentsBySurfaceMaterialIsEqualToSomething() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);
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
        customComponent.setSurfaceMaterial(surfaceMaterial);
        customComponentRepository.saveAndFlush(customComponent);
        Long surfaceMaterialId = surfaceMaterial.getId();

        // Get all the customComponentList where surfaceMaterial equals to surfaceMaterialId
        defaultCustomComponentShouldBeFound("surfaceMaterialId.equals=" + surfaceMaterialId);

        // Get all the customComponentList where surfaceMaterial equals to (surfaceMaterialId + 1)
        defaultCustomComponentShouldNotBeFound("surfaceMaterialId.equals=" + (surfaceMaterialId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustomComponentShouldBeFound(String filter) throws Exception {
        restCustomComponentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customComponent.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].gramPerMeterLinearMass").value(hasItem(DEFAULT_GRAM_PER_METER_LINEAR_MASS.doubleValue())))
            .andExpect(jsonPath("$.[*].milimeterDiameter").value(hasItem(DEFAULT_MILIMETER_DIAMETER.doubleValue())));

        // Check, that the count call also returns 1
        restCustomComponentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustomComponentShouldNotBeFound(String filter) throws Exception {
        restCustomComponentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustomComponentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCustomComponent() throws Exception {
        // Get the customComponent
        restCustomComponentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustomComponent() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        int databaseSizeBeforeUpdate = customComponentRepository.findAll().size();

        // Update the customComponent
        CustomComponent updatedCustomComponent = customComponentRepository.findById(customComponent.getId()).get();
        // Disconnect from session so that the updates on updatedCustomComponent are not directly saved in db
        em.detach(updatedCustomComponent);
        updatedCustomComponent
            .number(UPDATED_NUMBER)
            .designation(UPDATED_DESIGNATION)
            .gramPerMeterLinearMass(UPDATED_GRAM_PER_METER_LINEAR_MASS)
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER);

        restCustomComponentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCustomComponent.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCustomComponent))
            )
            .andExpect(status().isOk());

        // Validate the CustomComponent in the database
        List<CustomComponent> customComponentList = customComponentRepository.findAll();
        assertThat(customComponentList).hasSize(databaseSizeBeforeUpdate);
        CustomComponent testCustomComponent = customComponentList.get(customComponentList.size() - 1);
        assertThat(testCustomComponent.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testCustomComponent.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testCustomComponent.getGramPerMeterLinearMass()).isEqualTo(UPDATED_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testCustomComponent.getMilimeterDiameter()).isEqualTo(UPDATED_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void putNonExistingCustomComponent() throws Exception {
        int databaseSizeBeforeUpdate = customComponentRepository.findAll().size();
        customComponent.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomComponentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, customComponent.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customComponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomComponent in the database
        List<CustomComponent> customComponentList = customComponentRepository.findAll();
        assertThat(customComponentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustomComponent() throws Exception {
        int databaseSizeBeforeUpdate = customComponentRepository.findAll().size();
        customComponent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomComponentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customComponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomComponent in the database
        List<CustomComponent> customComponentList = customComponentRepository.findAll();
        assertThat(customComponentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustomComponent() throws Exception {
        int databaseSizeBeforeUpdate = customComponentRepository.findAll().size();
        customComponent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomComponentMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customComponent))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomComponent in the database
        List<CustomComponent> customComponentList = customComponentRepository.findAll();
        assertThat(customComponentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustomComponentWithPatch() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        int databaseSizeBeforeUpdate = customComponentRepository.findAll().size();

        // Update the customComponent using partial update
        CustomComponent partialUpdatedCustomComponent = new CustomComponent();
        partialUpdatedCustomComponent.setId(customComponent.getId());

        partialUpdatedCustomComponent.designation(UPDATED_DESIGNATION);

        restCustomComponentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomComponent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomComponent))
            )
            .andExpect(status().isOk());

        // Validate the CustomComponent in the database
        List<CustomComponent> customComponentList = customComponentRepository.findAll();
        assertThat(customComponentList).hasSize(databaseSizeBeforeUpdate);
        CustomComponent testCustomComponent = customComponentList.get(customComponentList.size() - 1);
        assertThat(testCustomComponent.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testCustomComponent.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testCustomComponent.getGramPerMeterLinearMass()).isEqualTo(DEFAULT_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testCustomComponent.getMilimeterDiameter()).isEqualTo(DEFAULT_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void fullUpdateCustomComponentWithPatch() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        int databaseSizeBeforeUpdate = customComponentRepository.findAll().size();

        // Update the customComponent using partial update
        CustomComponent partialUpdatedCustomComponent = new CustomComponent();
        partialUpdatedCustomComponent.setId(customComponent.getId());

        partialUpdatedCustomComponent
            .number(UPDATED_NUMBER)
            .designation(UPDATED_DESIGNATION)
            .gramPerMeterLinearMass(UPDATED_GRAM_PER_METER_LINEAR_MASS)
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER);

        restCustomComponentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomComponent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomComponent))
            )
            .andExpect(status().isOk());

        // Validate the CustomComponent in the database
        List<CustomComponent> customComponentList = customComponentRepository.findAll();
        assertThat(customComponentList).hasSize(databaseSizeBeforeUpdate);
        CustomComponent testCustomComponent = customComponentList.get(customComponentList.size() - 1);
        assertThat(testCustomComponent.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testCustomComponent.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testCustomComponent.getGramPerMeterLinearMass()).isEqualTo(UPDATED_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testCustomComponent.getMilimeterDiameter()).isEqualTo(UPDATED_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void patchNonExistingCustomComponent() throws Exception {
        int databaseSizeBeforeUpdate = customComponentRepository.findAll().size();
        customComponent.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomComponentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, customComponent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customComponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomComponent in the database
        List<CustomComponent> customComponentList = customComponentRepository.findAll();
        assertThat(customComponentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustomComponent() throws Exception {
        int databaseSizeBeforeUpdate = customComponentRepository.findAll().size();
        customComponent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomComponentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customComponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomComponent in the database
        List<CustomComponent> customComponentList = customComponentRepository.findAll();
        assertThat(customComponentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustomComponent() throws Exception {
        int databaseSizeBeforeUpdate = customComponentRepository.findAll().size();
        customComponent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomComponentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customComponent))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomComponent in the database
        List<CustomComponent> customComponentList = customComponentRepository.findAll();
        assertThat(customComponentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustomComponent() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        int databaseSizeBeforeDelete = customComponentRepository.findAll().size();

        // Delete the customComponent
        restCustomComponentMockMvc
            .perform(delete(ENTITY_API_URL_ID, customComponent.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomComponent> customComponentList = customComponentRepository.findAll();
        assertThat(customComponentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
