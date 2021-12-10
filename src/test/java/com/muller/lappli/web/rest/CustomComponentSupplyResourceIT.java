package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.CustomComponent;
import com.muller.lappli.domain.CustomComponentSupply;
import com.muller.lappli.domain.enumeration.MarkingType;
import com.muller.lappli.repository.CustomComponentSupplyRepository;
import com.muller.lappli.service.criteria.CustomComponentSupplyCriteria;
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
 * Integration tests for the {@link CustomComponentSupplyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustomComponentSupplyResourceIT {

    private static final Long DEFAULT_APPARITIONS = 1L;
    private static final Long UPDATED_APPARITIONS = 2L;
    private static final Long SMALLER_APPARITIONS = 1L - 1L;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final MarkingType DEFAULT_MARKING_TYPE = MarkingType.LIFTING;
    private static final MarkingType UPDATED_MARKING_TYPE = MarkingType.SPIRALLY_COLORED;

    private static final String ENTITY_API_URL = "/api/custom-component-supplies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustomComponentSupplyRepository customComponentSupplyRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomComponentSupplyMockMvc;

    private CustomComponentSupply customComponentSupply;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomComponentSupply createEntity(EntityManager em) {
        CustomComponentSupply customComponentSupply = new CustomComponentSupply()
            .apparitions(DEFAULT_APPARITIONS)
            .description(DEFAULT_DESCRIPTION)
            .markingType(DEFAULT_MARKING_TYPE);
        // Add required entity
        CustomComponent customComponent;
        if (TestUtil.findAll(em, CustomComponent.class).isEmpty()) {
            customComponent = CustomComponentResourceIT.createEntity(em);
            em.persist(customComponent);
            em.flush();
        } else {
            customComponent = TestUtil.findAll(em, CustomComponent.class).get(0);
        }
        customComponentSupply.setCustomComponent(customComponent);
        return customComponentSupply;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomComponentSupply createUpdatedEntity(EntityManager em) {
        CustomComponentSupply customComponentSupply = new CustomComponentSupply()
            .apparitions(UPDATED_APPARITIONS)
            .description(UPDATED_DESCRIPTION)
            .markingType(UPDATED_MARKING_TYPE);
        // Add required entity
        CustomComponent customComponent;
        if (TestUtil.findAll(em, CustomComponent.class).isEmpty()) {
            customComponent = CustomComponentResourceIT.createUpdatedEntity(em);
            em.persist(customComponent);
            em.flush();
        } else {
            customComponent = TestUtil.findAll(em, CustomComponent.class).get(0);
        }
        customComponentSupply.setCustomComponent(customComponent);
        return customComponentSupply;
    }

    @BeforeEach
    public void initTest() {
        customComponentSupply = createEntity(em);
    }

    @Test
    @Transactional
    void createCustomComponentSupply() throws Exception {
        int databaseSizeBeforeCreate = customComponentSupplyRepository.findAll().size();
        // Create the CustomComponentSupply
        restCustomComponentSupplyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customComponentSupply))
            )
            .andExpect(status().isCreated());

        // Validate the CustomComponentSupply in the database
        List<CustomComponentSupply> customComponentSupplyList = customComponentSupplyRepository.findAll();
        assertThat(customComponentSupplyList).hasSize(databaseSizeBeforeCreate + 1);
        CustomComponentSupply testCustomComponentSupply = customComponentSupplyList.get(customComponentSupplyList.size() - 1);
        assertThat(testCustomComponentSupply.getApparitions()).isEqualTo(DEFAULT_APPARITIONS);
        assertThat(testCustomComponentSupply.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCustomComponentSupply.getMarkingType()).isEqualTo(DEFAULT_MARKING_TYPE);
    }

    @Test
    @Transactional
    void createCustomComponentSupplyWithExistingId() throws Exception {
        // Create the CustomComponentSupply with an existing ID
        customComponentSupply.setId(1L);

        int databaseSizeBeforeCreate = customComponentSupplyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomComponentSupplyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customComponentSupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomComponentSupply in the database
        List<CustomComponentSupply> customComponentSupplyList = customComponentSupplyRepository.findAll();
        assertThat(customComponentSupplyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkApparitionsIsRequired() throws Exception {
        int databaseSizeBeforeTest = customComponentSupplyRepository.findAll().size();
        // set the field null
        customComponentSupply.setApparitions(null);

        // Create the CustomComponentSupply, which fails.

        restCustomComponentSupplyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customComponentSupply))
            )
            .andExpect(status().isBadRequest());

        List<CustomComponentSupply> customComponentSupplyList = customComponentSupplyRepository.findAll();
        assertThat(customComponentSupplyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMarkingTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = customComponentSupplyRepository.findAll().size();
        // set the field null
        customComponentSupply.setMarkingType(null);

        // Create the CustomComponentSupply, which fails.

        restCustomComponentSupplyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customComponentSupply))
            )
            .andExpect(status().isBadRequest());

        List<CustomComponentSupply> customComponentSupplyList = customComponentSupplyRepository.findAll();
        assertThat(customComponentSupplyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustomComponentSupplies() throws Exception {
        // Initialize the database
        customComponentSupplyRepository.saveAndFlush(customComponentSupply);

        // Get all the customComponentSupplyList
        restCustomComponentSupplyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customComponentSupply.getId().intValue())))
            .andExpect(jsonPath("$.[*].apparitions").value(hasItem(DEFAULT_APPARITIONS.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].markingType").value(hasItem(DEFAULT_MARKING_TYPE.toString())));
    }

    @Test
    @Transactional
    void getCustomComponentSupply() throws Exception {
        // Initialize the database
        customComponentSupplyRepository.saveAndFlush(customComponentSupply);

        // Get the customComponentSupply
        restCustomComponentSupplyMockMvc
            .perform(get(ENTITY_API_URL_ID, customComponentSupply.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customComponentSupply.getId().intValue()))
            .andExpect(jsonPath("$.apparitions").value(DEFAULT_APPARITIONS.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.markingType").value(DEFAULT_MARKING_TYPE.toString()));
    }

    @Test
    @Transactional
    void getCustomComponentSuppliesByIdFiltering() throws Exception {
        // Initialize the database
        customComponentSupplyRepository.saveAndFlush(customComponentSupply);

        Long id = customComponentSupply.getId();

        defaultCustomComponentSupplyShouldBeFound("id.equals=" + id);
        defaultCustomComponentSupplyShouldNotBeFound("id.notEquals=" + id);

        defaultCustomComponentSupplyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustomComponentSupplyShouldNotBeFound("id.greaterThan=" + id);

        defaultCustomComponentSupplyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustomComponentSupplyShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCustomComponentSuppliesByApparitionsIsEqualToSomething() throws Exception {
        // Initialize the database
        customComponentSupplyRepository.saveAndFlush(customComponentSupply);

        // Get all the customComponentSupplyList where apparitions equals to DEFAULT_APPARITIONS
        defaultCustomComponentSupplyShouldBeFound("apparitions.equals=" + DEFAULT_APPARITIONS);

        // Get all the customComponentSupplyList where apparitions equals to UPDATED_APPARITIONS
        defaultCustomComponentSupplyShouldNotBeFound("apparitions.equals=" + UPDATED_APPARITIONS);
    }

    @Test
    @Transactional
    void getAllCustomComponentSuppliesByApparitionsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customComponentSupplyRepository.saveAndFlush(customComponentSupply);

        // Get all the customComponentSupplyList where apparitions not equals to DEFAULT_APPARITIONS
        defaultCustomComponentSupplyShouldNotBeFound("apparitions.notEquals=" + DEFAULT_APPARITIONS);

        // Get all the customComponentSupplyList where apparitions not equals to UPDATED_APPARITIONS
        defaultCustomComponentSupplyShouldBeFound("apparitions.notEquals=" + UPDATED_APPARITIONS);
    }

    @Test
    @Transactional
    void getAllCustomComponentSuppliesByApparitionsIsInShouldWork() throws Exception {
        // Initialize the database
        customComponentSupplyRepository.saveAndFlush(customComponentSupply);

        // Get all the customComponentSupplyList where apparitions in DEFAULT_APPARITIONS or UPDATED_APPARITIONS
        defaultCustomComponentSupplyShouldBeFound("apparitions.in=" + DEFAULT_APPARITIONS + "," + UPDATED_APPARITIONS);

        // Get all the customComponentSupplyList where apparitions equals to UPDATED_APPARITIONS
        defaultCustomComponentSupplyShouldNotBeFound("apparitions.in=" + UPDATED_APPARITIONS);
    }

    @Test
    @Transactional
    void getAllCustomComponentSuppliesByApparitionsIsNullOrNotNull() throws Exception {
        // Initialize the database
        customComponentSupplyRepository.saveAndFlush(customComponentSupply);

        // Get all the customComponentSupplyList where apparitions is not null
        defaultCustomComponentSupplyShouldBeFound("apparitions.specified=true");

        // Get all the customComponentSupplyList where apparitions is null
        defaultCustomComponentSupplyShouldNotBeFound("apparitions.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomComponentSuppliesByApparitionsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customComponentSupplyRepository.saveAndFlush(customComponentSupply);

        // Get all the customComponentSupplyList where apparitions is greater than or equal to DEFAULT_APPARITIONS
        defaultCustomComponentSupplyShouldBeFound("apparitions.greaterThanOrEqual=" + DEFAULT_APPARITIONS);

        // Get all the customComponentSupplyList where apparitions is greater than or equal to UPDATED_APPARITIONS
        defaultCustomComponentSupplyShouldNotBeFound("apparitions.greaterThanOrEqual=" + UPDATED_APPARITIONS);
    }

    @Test
    @Transactional
    void getAllCustomComponentSuppliesByApparitionsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customComponentSupplyRepository.saveAndFlush(customComponentSupply);

        // Get all the customComponentSupplyList where apparitions is less than or equal to DEFAULT_APPARITIONS
        defaultCustomComponentSupplyShouldBeFound("apparitions.lessThanOrEqual=" + DEFAULT_APPARITIONS);

        // Get all the customComponentSupplyList where apparitions is less than or equal to SMALLER_APPARITIONS
        defaultCustomComponentSupplyShouldNotBeFound("apparitions.lessThanOrEqual=" + SMALLER_APPARITIONS);
    }

    @Test
    @Transactional
    void getAllCustomComponentSuppliesByApparitionsIsLessThanSomething() throws Exception {
        // Initialize the database
        customComponentSupplyRepository.saveAndFlush(customComponentSupply);

        // Get all the customComponentSupplyList where apparitions is less than DEFAULT_APPARITIONS
        defaultCustomComponentSupplyShouldNotBeFound("apparitions.lessThan=" + DEFAULT_APPARITIONS);

        // Get all the customComponentSupplyList where apparitions is less than UPDATED_APPARITIONS
        defaultCustomComponentSupplyShouldBeFound("apparitions.lessThan=" + UPDATED_APPARITIONS);
    }

    @Test
    @Transactional
    void getAllCustomComponentSuppliesByApparitionsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customComponentSupplyRepository.saveAndFlush(customComponentSupply);

        // Get all the customComponentSupplyList where apparitions is greater than DEFAULT_APPARITIONS
        defaultCustomComponentSupplyShouldNotBeFound("apparitions.greaterThan=" + DEFAULT_APPARITIONS);

        // Get all the customComponentSupplyList where apparitions is greater than SMALLER_APPARITIONS
        defaultCustomComponentSupplyShouldBeFound("apparitions.greaterThan=" + SMALLER_APPARITIONS);
    }

    @Test
    @Transactional
    void getAllCustomComponentSuppliesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        customComponentSupplyRepository.saveAndFlush(customComponentSupply);

        // Get all the customComponentSupplyList where description equals to DEFAULT_DESCRIPTION
        defaultCustomComponentSupplyShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the customComponentSupplyList where description equals to UPDATED_DESCRIPTION
        defaultCustomComponentSupplyShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCustomComponentSuppliesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customComponentSupplyRepository.saveAndFlush(customComponentSupply);

        // Get all the customComponentSupplyList where description not equals to DEFAULT_DESCRIPTION
        defaultCustomComponentSupplyShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the customComponentSupplyList where description not equals to UPDATED_DESCRIPTION
        defaultCustomComponentSupplyShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCustomComponentSuppliesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        customComponentSupplyRepository.saveAndFlush(customComponentSupply);

        // Get all the customComponentSupplyList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCustomComponentSupplyShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the customComponentSupplyList where description equals to UPDATED_DESCRIPTION
        defaultCustomComponentSupplyShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCustomComponentSuppliesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        customComponentSupplyRepository.saveAndFlush(customComponentSupply);

        // Get all the customComponentSupplyList where description is not null
        defaultCustomComponentSupplyShouldBeFound("description.specified=true");

        // Get all the customComponentSupplyList where description is null
        defaultCustomComponentSupplyShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomComponentSuppliesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        customComponentSupplyRepository.saveAndFlush(customComponentSupply);

        // Get all the customComponentSupplyList where description contains DEFAULT_DESCRIPTION
        defaultCustomComponentSupplyShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the customComponentSupplyList where description contains UPDATED_DESCRIPTION
        defaultCustomComponentSupplyShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCustomComponentSuppliesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        customComponentSupplyRepository.saveAndFlush(customComponentSupply);

        // Get all the customComponentSupplyList where description does not contain DEFAULT_DESCRIPTION
        defaultCustomComponentSupplyShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the customComponentSupplyList where description does not contain UPDATED_DESCRIPTION
        defaultCustomComponentSupplyShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCustomComponentSuppliesByMarkingTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        customComponentSupplyRepository.saveAndFlush(customComponentSupply);

        // Get all the customComponentSupplyList where markingType equals to DEFAULT_MARKING_TYPE
        defaultCustomComponentSupplyShouldBeFound("markingType.equals=" + DEFAULT_MARKING_TYPE);

        // Get all the customComponentSupplyList where markingType equals to UPDATED_MARKING_TYPE
        defaultCustomComponentSupplyShouldNotBeFound("markingType.equals=" + UPDATED_MARKING_TYPE);
    }

    @Test
    @Transactional
    void getAllCustomComponentSuppliesByMarkingTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customComponentSupplyRepository.saveAndFlush(customComponentSupply);

        // Get all the customComponentSupplyList where markingType not equals to DEFAULT_MARKING_TYPE
        defaultCustomComponentSupplyShouldNotBeFound("markingType.notEquals=" + DEFAULT_MARKING_TYPE);

        // Get all the customComponentSupplyList where markingType not equals to UPDATED_MARKING_TYPE
        defaultCustomComponentSupplyShouldBeFound("markingType.notEquals=" + UPDATED_MARKING_TYPE);
    }

    @Test
    @Transactional
    void getAllCustomComponentSuppliesByMarkingTypeIsInShouldWork() throws Exception {
        // Initialize the database
        customComponentSupplyRepository.saveAndFlush(customComponentSupply);

        // Get all the customComponentSupplyList where markingType in DEFAULT_MARKING_TYPE or UPDATED_MARKING_TYPE
        defaultCustomComponentSupplyShouldBeFound("markingType.in=" + DEFAULT_MARKING_TYPE + "," + UPDATED_MARKING_TYPE);

        // Get all the customComponentSupplyList where markingType equals to UPDATED_MARKING_TYPE
        defaultCustomComponentSupplyShouldNotBeFound("markingType.in=" + UPDATED_MARKING_TYPE);
    }

    @Test
    @Transactional
    void getAllCustomComponentSuppliesByMarkingTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        customComponentSupplyRepository.saveAndFlush(customComponentSupply);

        // Get all the customComponentSupplyList where markingType is not null
        defaultCustomComponentSupplyShouldBeFound("markingType.specified=true");

        // Get all the customComponentSupplyList where markingType is null
        defaultCustomComponentSupplyShouldNotBeFound("markingType.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomComponentSuppliesByCustomComponentIsEqualToSomething() throws Exception {
        // Initialize the database
        customComponentSupplyRepository.saveAndFlush(customComponentSupply);
        CustomComponent customComponent;
        if (TestUtil.findAll(em, CustomComponent.class).isEmpty()) {
            customComponent = CustomComponentResourceIT.createEntity(em);
            em.persist(customComponent);
            em.flush();
        } else {
            customComponent = TestUtil.findAll(em, CustomComponent.class).get(0);
        }
        em.persist(customComponent);
        em.flush();
        customComponentSupply.setCustomComponent(customComponent);
        customComponentSupplyRepository.saveAndFlush(customComponentSupply);
        Long customComponentId = customComponent.getId();

        // Get all the customComponentSupplyList where customComponent equals to customComponentId
        defaultCustomComponentSupplyShouldBeFound("customComponentId.equals=" + customComponentId);

        // Get all the customComponentSupplyList where customComponent equals to (customComponentId + 1)
        defaultCustomComponentSupplyShouldNotBeFound("customComponentId.equals=" + (customComponentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustomComponentSupplyShouldBeFound(String filter) throws Exception {
        restCustomComponentSupplyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customComponentSupply.getId().intValue())))
            .andExpect(jsonPath("$.[*].apparitions").value(hasItem(DEFAULT_APPARITIONS.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].markingType").value(hasItem(DEFAULT_MARKING_TYPE.toString())));

        // Check, that the count call also returns 1
        restCustomComponentSupplyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustomComponentSupplyShouldNotBeFound(String filter) throws Exception {
        restCustomComponentSupplyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustomComponentSupplyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCustomComponentSupply() throws Exception {
        // Get the customComponentSupply
        restCustomComponentSupplyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustomComponentSupply() throws Exception {
        // Initialize the database
        customComponentSupplyRepository.saveAndFlush(customComponentSupply);

        int databaseSizeBeforeUpdate = customComponentSupplyRepository.findAll().size();

        // Update the customComponentSupply
        CustomComponentSupply updatedCustomComponentSupply = customComponentSupplyRepository.findById(customComponentSupply.getId()).get();
        // Disconnect from session so that the updates on updatedCustomComponentSupply are not directly saved in db
        em.detach(updatedCustomComponentSupply);
        updatedCustomComponentSupply.apparitions(UPDATED_APPARITIONS).description(UPDATED_DESCRIPTION).markingType(UPDATED_MARKING_TYPE);

        restCustomComponentSupplyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCustomComponentSupply.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCustomComponentSupply))
            )
            .andExpect(status().isOk());

        // Validate the CustomComponentSupply in the database
        List<CustomComponentSupply> customComponentSupplyList = customComponentSupplyRepository.findAll();
        assertThat(customComponentSupplyList).hasSize(databaseSizeBeforeUpdate);
        CustomComponentSupply testCustomComponentSupply = customComponentSupplyList.get(customComponentSupplyList.size() - 1);
        assertThat(testCustomComponentSupply.getApparitions()).isEqualTo(UPDATED_APPARITIONS);
        assertThat(testCustomComponentSupply.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCustomComponentSupply.getMarkingType()).isEqualTo(UPDATED_MARKING_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingCustomComponentSupply() throws Exception {
        int databaseSizeBeforeUpdate = customComponentSupplyRepository.findAll().size();
        customComponentSupply.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomComponentSupplyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, customComponentSupply.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customComponentSupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomComponentSupply in the database
        List<CustomComponentSupply> customComponentSupplyList = customComponentSupplyRepository.findAll();
        assertThat(customComponentSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustomComponentSupply() throws Exception {
        int databaseSizeBeforeUpdate = customComponentSupplyRepository.findAll().size();
        customComponentSupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomComponentSupplyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customComponentSupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomComponentSupply in the database
        List<CustomComponentSupply> customComponentSupplyList = customComponentSupplyRepository.findAll();
        assertThat(customComponentSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustomComponentSupply() throws Exception {
        int databaseSizeBeforeUpdate = customComponentSupplyRepository.findAll().size();
        customComponentSupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomComponentSupplyMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customComponentSupply))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomComponentSupply in the database
        List<CustomComponentSupply> customComponentSupplyList = customComponentSupplyRepository.findAll();
        assertThat(customComponentSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustomComponentSupplyWithPatch() throws Exception {
        // Initialize the database
        customComponentSupplyRepository.saveAndFlush(customComponentSupply);

        int databaseSizeBeforeUpdate = customComponentSupplyRepository.findAll().size();

        // Update the customComponentSupply using partial update
        CustomComponentSupply partialUpdatedCustomComponentSupply = new CustomComponentSupply();
        partialUpdatedCustomComponentSupply.setId(customComponentSupply.getId());

        partialUpdatedCustomComponentSupply.apparitions(UPDATED_APPARITIONS);

        restCustomComponentSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomComponentSupply.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomComponentSupply))
            )
            .andExpect(status().isOk());

        // Validate the CustomComponentSupply in the database
        List<CustomComponentSupply> customComponentSupplyList = customComponentSupplyRepository.findAll();
        assertThat(customComponentSupplyList).hasSize(databaseSizeBeforeUpdate);
        CustomComponentSupply testCustomComponentSupply = customComponentSupplyList.get(customComponentSupplyList.size() - 1);
        assertThat(testCustomComponentSupply.getApparitions()).isEqualTo(UPDATED_APPARITIONS);
        assertThat(testCustomComponentSupply.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCustomComponentSupply.getMarkingType()).isEqualTo(DEFAULT_MARKING_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateCustomComponentSupplyWithPatch() throws Exception {
        // Initialize the database
        customComponentSupplyRepository.saveAndFlush(customComponentSupply);

        int databaseSizeBeforeUpdate = customComponentSupplyRepository.findAll().size();

        // Update the customComponentSupply using partial update
        CustomComponentSupply partialUpdatedCustomComponentSupply = new CustomComponentSupply();
        partialUpdatedCustomComponentSupply.setId(customComponentSupply.getId());

        partialUpdatedCustomComponentSupply
            .apparitions(UPDATED_APPARITIONS)
            .description(UPDATED_DESCRIPTION)
            .markingType(UPDATED_MARKING_TYPE);

        restCustomComponentSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomComponentSupply.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomComponentSupply))
            )
            .andExpect(status().isOk());

        // Validate the CustomComponentSupply in the database
        List<CustomComponentSupply> customComponentSupplyList = customComponentSupplyRepository.findAll();
        assertThat(customComponentSupplyList).hasSize(databaseSizeBeforeUpdate);
        CustomComponentSupply testCustomComponentSupply = customComponentSupplyList.get(customComponentSupplyList.size() - 1);
        assertThat(testCustomComponentSupply.getApparitions()).isEqualTo(UPDATED_APPARITIONS);
        assertThat(testCustomComponentSupply.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCustomComponentSupply.getMarkingType()).isEqualTo(UPDATED_MARKING_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingCustomComponentSupply() throws Exception {
        int databaseSizeBeforeUpdate = customComponentSupplyRepository.findAll().size();
        customComponentSupply.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomComponentSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, customComponentSupply.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customComponentSupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomComponentSupply in the database
        List<CustomComponentSupply> customComponentSupplyList = customComponentSupplyRepository.findAll();
        assertThat(customComponentSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustomComponentSupply() throws Exception {
        int databaseSizeBeforeUpdate = customComponentSupplyRepository.findAll().size();
        customComponentSupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomComponentSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customComponentSupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomComponentSupply in the database
        List<CustomComponentSupply> customComponentSupplyList = customComponentSupplyRepository.findAll();
        assertThat(customComponentSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustomComponentSupply() throws Exception {
        int databaseSizeBeforeUpdate = customComponentSupplyRepository.findAll().size();
        customComponentSupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomComponentSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customComponentSupply))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomComponentSupply in the database
        List<CustomComponentSupply> customComponentSupplyList = customComponentSupplyRepository.findAll();
        assertThat(customComponentSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustomComponentSupply() throws Exception {
        // Initialize the database
        customComponentSupplyRepository.saveAndFlush(customComponentSupply);

        int databaseSizeBeforeDelete = customComponentSupplyRepository.findAll().size();

        // Delete the customComponentSupply
        restCustomComponentSupplyMockMvc
            .perform(delete(ENTITY_API_URL_ID, customComponentSupply.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomComponentSupply> customComponentSupplyList = customComponentSupplyRepository.findAll();
        assertThat(customComponentSupplyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
