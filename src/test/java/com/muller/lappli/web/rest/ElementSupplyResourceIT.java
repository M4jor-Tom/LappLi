package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.Element;
import com.muller.lappli.domain.ElementSupply;
import com.muller.lappli.domain.enumeration.MarkingType;
import com.muller.lappli.repository.ElementSupplyRepository;
import com.muller.lappli.service.criteria.ElementSupplyCriteria;
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
 * Integration tests for the {@link ElementSupplyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ElementSupplyResourceIT {

    private static final Long DEFAULT_APPARITIONS = 1L;
    private static final Long UPDATED_APPARITIONS = 2L;
    private static final Long SMALLER_APPARITIONS = 1L - 1L;

    private static final String DEFAULT_FORCED_MARKING = "AAAAAAAAAA";
    private static final String UPDATED_FORCED_MARKING = "BBBBBBBBBB";

    private static final MarkingType DEFAULT_MARKING_TYPE = MarkingType.LIFTING;
    private static final MarkingType UPDATED_MARKING_TYPE = MarkingType.SPIRALLY_COLORED;

    private static final String ENTITY_API_URL = "/api/element-supplies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ElementSupplyRepository elementSupplyRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restElementSupplyMockMvc;

    private ElementSupply elementSupply;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ElementSupply createEntity(EntityManager em) {
        ElementSupply elementSupply = new ElementSupply()
            .apparitions(DEFAULT_APPARITIONS)
            .forcedMarking(DEFAULT_FORCED_MARKING)
            .markingType(DEFAULT_MARKING_TYPE);
        return elementSupply;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ElementSupply createUpdatedEntity(EntityManager em) {
        ElementSupply elementSupply = new ElementSupply()
            .apparitions(UPDATED_APPARITIONS)
            .forcedMarking(UPDATED_FORCED_MARKING)
            .markingType(UPDATED_MARKING_TYPE);
        return elementSupply;
    }

    @BeforeEach
    public void initTest() {
        elementSupply = createEntity(em);
    }

    @Test
    @Transactional
    void createElementSupply() throws Exception {
        int databaseSizeBeforeCreate = elementSupplyRepository.findAll().size();
        // Create the ElementSupply
        restElementSupplyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(elementSupply)))
            .andExpect(status().isCreated());

        // Validate the ElementSupply in the database
        List<ElementSupply> elementSupplyList = elementSupplyRepository.findAll();
        assertThat(elementSupplyList).hasSize(databaseSizeBeforeCreate + 1);
        ElementSupply testElementSupply = elementSupplyList.get(elementSupplyList.size() - 1);
        assertThat(testElementSupply.getApparitions()).isEqualTo(DEFAULT_APPARITIONS);
        assertThat(testElementSupply.getForcedMarking()).isEqualTo(DEFAULT_FORCED_MARKING);
        assertThat(testElementSupply.getMarkingType()).isEqualTo(DEFAULT_MARKING_TYPE);
    }

    @Test
    @Transactional
    void createElementSupplyWithExistingId() throws Exception {
        // Create the ElementSupply with an existing ID
        elementSupply.setId(1L);

        int databaseSizeBeforeCreate = elementSupplyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restElementSupplyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(elementSupply)))
            .andExpect(status().isBadRequest());

        // Validate the ElementSupply in the database
        List<ElementSupply> elementSupplyList = elementSupplyRepository.findAll();
        assertThat(elementSupplyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkApparitionsIsRequired() throws Exception {
        int databaseSizeBeforeTest = elementSupplyRepository.findAll().size();
        // set the field null
        elementSupply.setApparitions(null);

        // Create the ElementSupply, which fails.

        restElementSupplyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(elementSupply)))
            .andExpect(status().isBadRequest());

        List<ElementSupply> elementSupplyList = elementSupplyRepository.findAll();
        assertThat(elementSupplyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllElementSupplies() throws Exception {
        // Initialize the database
        elementSupplyRepository.saveAndFlush(elementSupply);

        // Get all the elementSupplyList
        restElementSupplyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(elementSupply.getId().intValue())))
            .andExpect(jsonPath("$.[*].apparitions").value(hasItem(DEFAULT_APPARITIONS.intValue())))
            .andExpect(jsonPath("$.[*].forcedMarking").value(hasItem(DEFAULT_FORCED_MARKING)))
            .andExpect(jsonPath("$.[*].markingType").value(hasItem(DEFAULT_MARKING_TYPE.toString())));
    }

    @Test
    @Transactional
    void getElementSupply() throws Exception {
        // Initialize the database
        elementSupplyRepository.saveAndFlush(elementSupply);

        // Get the elementSupply
        restElementSupplyMockMvc
            .perform(get(ENTITY_API_URL_ID, elementSupply.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(elementSupply.getId().intValue()))
            .andExpect(jsonPath("$.apparitions").value(DEFAULT_APPARITIONS.intValue()))
            .andExpect(jsonPath("$.forcedMarking").value(DEFAULT_FORCED_MARKING))
            .andExpect(jsonPath("$.markingType").value(DEFAULT_MARKING_TYPE.toString()));
    }

    @Test
    @Transactional
    void getElementSuppliesByIdFiltering() throws Exception {
        // Initialize the database
        elementSupplyRepository.saveAndFlush(elementSupply);

        Long id = elementSupply.getId();

        defaultElementSupplyShouldBeFound("id.equals=" + id);
        defaultElementSupplyShouldNotBeFound("id.notEquals=" + id);

        defaultElementSupplyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultElementSupplyShouldNotBeFound("id.greaterThan=" + id);

        defaultElementSupplyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultElementSupplyShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllElementSuppliesByApparitionsIsEqualToSomething() throws Exception {
        // Initialize the database
        elementSupplyRepository.saveAndFlush(elementSupply);

        // Get all the elementSupplyList where apparitions equals to DEFAULT_APPARITIONS
        defaultElementSupplyShouldBeFound("apparitions.equals=" + DEFAULT_APPARITIONS);

        // Get all the elementSupplyList where apparitions equals to UPDATED_APPARITIONS
        defaultElementSupplyShouldNotBeFound("apparitions.equals=" + UPDATED_APPARITIONS);
    }

    @Test
    @Transactional
    void getAllElementSuppliesByApparitionsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        elementSupplyRepository.saveAndFlush(elementSupply);

        // Get all the elementSupplyList where apparitions not equals to DEFAULT_APPARITIONS
        defaultElementSupplyShouldNotBeFound("apparitions.notEquals=" + DEFAULT_APPARITIONS);

        // Get all the elementSupplyList where apparitions not equals to UPDATED_APPARITIONS
        defaultElementSupplyShouldBeFound("apparitions.notEquals=" + UPDATED_APPARITIONS);
    }

    @Test
    @Transactional
    void getAllElementSuppliesByApparitionsIsInShouldWork() throws Exception {
        // Initialize the database
        elementSupplyRepository.saveAndFlush(elementSupply);

        // Get all the elementSupplyList where apparitions in DEFAULT_APPARITIONS or UPDATED_APPARITIONS
        defaultElementSupplyShouldBeFound("apparitions.in=" + DEFAULT_APPARITIONS + "," + UPDATED_APPARITIONS);

        // Get all the elementSupplyList where apparitions equals to UPDATED_APPARITIONS
        defaultElementSupplyShouldNotBeFound("apparitions.in=" + UPDATED_APPARITIONS);
    }

    @Test
    @Transactional
    void getAllElementSuppliesByApparitionsIsNullOrNotNull() throws Exception {
        // Initialize the database
        elementSupplyRepository.saveAndFlush(elementSupply);

        // Get all the elementSupplyList where apparitions is not null
        defaultElementSupplyShouldBeFound("apparitions.specified=true");

        // Get all the elementSupplyList where apparitions is null
        defaultElementSupplyShouldNotBeFound("apparitions.specified=false");
    }

    @Test
    @Transactional
    void getAllElementSuppliesByApparitionsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        elementSupplyRepository.saveAndFlush(elementSupply);

        // Get all the elementSupplyList where apparitions is greater than or equal to DEFAULT_APPARITIONS
        defaultElementSupplyShouldBeFound("apparitions.greaterThanOrEqual=" + DEFAULT_APPARITIONS);

        // Get all the elementSupplyList where apparitions is greater than or equal to UPDATED_APPARITIONS
        defaultElementSupplyShouldNotBeFound("apparitions.greaterThanOrEqual=" + UPDATED_APPARITIONS);
    }

    @Test
    @Transactional
    void getAllElementSuppliesByApparitionsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        elementSupplyRepository.saveAndFlush(elementSupply);

        // Get all the elementSupplyList where apparitions is less than or equal to DEFAULT_APPARITIONS
        defaultElementSupplyShouldBeFound("apparitions.lessThanOrEqual=" + DEFAULT_APPARITIONS);

        // Get all the elementSupplyList where apparitions is less than or equal to SMALLER_APPARITIONS
        defaultElementSupplyShouldNotBeFound("apparitions.lessThanOrEqual=" + SMALLER_APPARITIONS);
    }

    @Test
    @Transactional
    void getAllElementSuppliesByApparitionsIsLessThanSomething() throws Exception {
        // Initialize the database
        elementSupplyRepository.saveAndFlush(elementSupply);

        // Get all the elementSupplyList where apparitions is less than DEFAULT_APPARITIONS
        defaultElementSupplyShouldNotBeFound("apparitions.lessThan=" + DEFAULT_APPARITIONS);

        // Get all the elementSupplyList where apparitions is less than UPDATED_APPARITIONS
        defaultElementSupplyShouldBeFound("apparitions.lessThan=" + UPDATED_APPARITIONS);
    }

    @Test
    @Transactional
    void getAllElementSuppliesByApparitionsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        elementSupplyRepository.saveAndFlush(elementSupply);

        // Get all the elementSupplyList where apparitions is greater than DEFAULT_APPARITIONS
        defaultElementSupplyShouldNotBeFound("apparitions.greaterThan=" + DEFAULT_APPARITIONS);

        // Get all the elementSupplyList where apparitions is greater than SMALLER_APPARITIONS
        defaultElementSupplyShouldBeFound("apparitions.greaterThan=" + SMALLER_APPARITIONS);
    }

    @Test
    @Transactional
    void getAllElementSuppliesByForcedMarkingIsEqualToSomething() throws Exception {
        // Initialize the database
        elementSupplyRepository.saveAndFlush(elementSupply);

        // Get all the elementSupplyList where forcedMarking equals to DEFAULT_FORCED_MARKING
        defaultElementSupplyShouldBeFound("forcedMarking.equals=" + DEFAULT_FORCED_MARKING);

        // Get all the elementSupplyList where forcedMarking equals to UPDATED_FORCED_MARKING
        defaultElementSupplyShouldNotBeFound("forcedMarking.equals=" + UPDATED_FORCED_MARKING);
    }

    @Test
    @Transactional
    void getAllElementSuppliesByForcedMarkingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        elementSupplyRepository.saveAndFlush(elementSupply);

        // Get all the elementSupplyList where forcedMarking not equals to DEFAULT_FORCED_MARKING
        defaultElementSupplyShouldNotBeFound("forcedMarking.notEquals=" + DEFAULT_FORCED_MARKING);

        // Get all the elementSupplyList where forcedMarking not equals to UPDATED_FORCED_MARKING
        defaultElementSupplyShouldBeFound("forcedMarking.notEquals=" + UPDATED_FORCED_MARKING);
    }

    @Test
    @Transactional
    void getAllElementSuppliesByForcedMarkingIsInShouldWork() throws Exception {
        // Initialize the database
        elementSupplyRepository.saveAndFlush(elementSupply);

        // Get all the elementSupplyList where forcedMarking in DEFAULT_FORCED_MARKING or UPDATED_FORCED_MARKING
        defaultElementSupplyShouldBeFound("forcedMarking.in=" + DEFAULT_FORCED_MARKING + "," + UPDATED_FORCED_MARKING);

        // Get all the elementSupplyList where forcedMarking equals to UPDATED_FORCED_MARKING
        defaultElementSupplyShouldNotBeFound("forcedMarking.in=" + UPDATED_FORCED_MARKING);
    }

    @Test
    @Transactional
    void getAllElementSuppliesByForcedMarkingIsNullOrNotNull() throws Exception {
        // Initialize the database
        elementSupplyRepository.saveAndFlush(elementSupply);

        // Get all the elementSupplyList where forcedMarking is not null
        defaultElementSupplyShouldBeFound("forcedMarking.specified=true");

        // Get all the elementSupplyList where forcedMarking is null
        defaultElementSupplyShouldNotBeFound("forcedMarking.specified=false");
    }

    @Test
    @Transactional
    void getAllElementSuppliesByForcedMarkingContainsSomething() throws Exception {
        // Initialize the database
        elementSupplyRepository.saveAndFlush(elementSupply);

        // Get all the elementSupplyList where forcedMarking contains DEFAULT_FORCED_MARKING
        defaultElementSupplyShouldBeFound("forcedMarking.contains=" + DEFAULT_FORCED_MARKING);

        // Get all the elementSupplyList where forcedMarking contains UPDATED_FORCED_MARKING
        defaultElementSupplyShouldNotBeFound("forcedMarking.contains=" + UPDATED_FORCED_MARKING);
    }

    @Test
    @Transactional
    void getAllElementSuppliesByForcedMarkingNotContainsSomething() throws Exception {
        // Initialize the database
        elementSupplyRepository.saveAndFlush(elementSupply);

        // Get all the elementSupplyList where forcedMarking does not contain DEFAULT_FORCED_MARKING
        defaultElementSupplyShouldNotBeFound("forcedMarking.doesNotContain=" + DEFAULT_FORCED_MARKING);

        // Get all the elementSupplyList where forcedMarking does not contain UPDATED_FORCED_MARKING
        defaultElementSupplyShouldBeFound("forcedMarking.doesNotContain=" + UPDATED_FORCED_MARKING);
    }

    @Test
    @Transactional
    void getAllElementSuppliesByMarkingTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        elementSupplyRepository.saveAndFlush(elementSupply);

        // Get all the elementSupplyList where markingType equals to DEFAULT_MARKING_TYPE
        defaultElementSupplyShouldBeFound("markingType.equals=" + DEFAULT_MARKING_TYPE);

        // Get all the elementSupplyList where markingType equals to UPDATED_MARKING_TYPE
        defaultElementSupplyShouldNotBeFound("markingType.equals=" + UPDATED_MARKING_TYPE);
    }

    @Test
    @Transactional
    void getAllElementSuppliesByMarkingTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        elementSupplyRepository.saveAndFlush(elementSupply);

        // Get all the elementSupplyList where markingType not equals to DEFAULT_MARKING_TYPE
        defaultElementSupplyShouldNotBeFound("markingType.notEquals=" + DEFAULT_MARKING_TYPE);

        // Get all the elementSupplyList where markingType not equals to UPDATED_MARKING_TYPE
        defaultElementSupplyShouldBeFound("markingType.notEquals=" + UPDATED_MARKING_TYPE);
    }

    @Test
    @Transactional
    void getAllElementSuppliesByMarkingTypeIsInShouldWork() throws Exception {
        // Initialize the database
        elementSupplyRepository.saveAndFlush(elementSupply);

        // Get all the elementSupplyList where markingType in DEFAULT_MARKING_TYPE or UPDATED_MARKING_TYPE
        defaultElementSupplyShouldBeFound("markingType.in=" + DEFAULT_MARKING_TYPE + "," + UPDATED_MARKING_TYPE);

        // Get all the elementSupplyList where markingType equals to UPDATED_MARKING_TYPE
        defaultElementSupplyShouldNotBeFound("markingType.in=" + UPDATED_MARKING_TYPE);
    }

    @Test
    @Transactional
    void getAllElementSuppliesByMarkingTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        elementSupplyRepository.saveAndFlush(elementSupply);

        // Get all the elementSupplyList where markingType is not null
        defaultElementSupplyShouldBeFound("markingType.specified=true");

        // Get all the elementSupplyList where markingType is null
        defaultElementSupplyShouldNotBeFound("markingType.specified=false");
    }

    @Test
    @Transactional
    void getAllElementSuppliesByElementIsEqualToSomething() throws Exception {
        // Initialize the database
        elementSupplyRepository.saveAndFlush(elementSupply);
        Element element;
        if (TestUtil.findAll(em, Element.class).isEmpty()) {
            element = ElementResourceIT.createEntity(em);
            em.persist(element);
            em.flush();
        } else {
            element = TestUtil.findAll(em, Element.class).get(0);
        }
        em.persist(element);
        em.flush();
        elementSupply.setElement(element);
        elementSupplyRepository.saveAndFlush(elementSupply);
        Long elementId = element.getId();

        // Get all the elementSupplyList where element equals to elementId
        defaultElementSupplyShouldBeFound("elementId.equals=" + elementId);

        // Get all the elementSupplyList where element equals to (elementId + 1)
        defaultElementSupplyShouldNotBeFound("elementId.equals=" + (elementId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultElementSupplyShouldBeFound(String filter) throws Exception {
        restElementSupplyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(elementSupply.getId().intValue())))
            .andExpect(jsonPath("$.[*].apparitions").value(hasItem(DEFAULT_APPARITIONS.intValue())))
            .andExpect(jsonPath("$.[*].forcedMarking").value(hasItem(DEFAULT_FORCED_MARKING)))
            .andExpect(jsonPath("$.[*].markingType").value(hasItem(DEFAULT_MARKING_TYPE.toString())));

        // Check, that the count call also returns 1
        restElementSupplyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultElementSupplyShouldNotBeFound(String filter) throws Exception {
        restElementSupplyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restElementSupplyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingElementSupply() throws Exception {
        // Get the elementSupply
        restElementSupplyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewElementSupply() throws Exception {
        // Initialize the database
        elementSupplyRepository.saveAndFlush(elementSupply);

        int databaseSizeBeforeUpdate = elementSupplyRepository.findAll().size();

        // Update the elementSupply
        ElementSupply updatedElementSupply = elementSupplyRepository.findById(elementSupply.getId()).get();
        // Disconnect from session so that the updates on updatedElementSupply are not directly saved in db
        em.detach(updatedElementSupply);
        updatedElementSupply.apparitions(UPDATED_APPARITIONS).forcedMarking(UPDATED_FORCED_MARKING).markingType(UPDATED_MARKING_TYPE);

        restElementSupplyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedElementSupply.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedElementSupply))
            )
            .andExpect(status().isOk());

        // Validate the ElementSupply in the database
        List<ElementSupply> elementSupplyList = elementSupplyRepository.findAll();
        assertThat(elementSupplyList).hasSize(databaseSizeBeforeUpdate);
        ElementSupply testElementSupply = elementSupplyList.get(elementSupplyList.size() - 1);
        assertThat(testElementSupply.getApparitions()).isEqualTo(UPDATED_APPARITIONS);
        assertThat(testElementSupply.getForcedMarking()).isEqualTo(UPDATED_FORCED_MARKING);
        assertThat(testElementSupply.getMarkingType()).isEqualTo(UPDATED_MARKING_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingElementSupply() throws Exception {
        int databaseSizeBeforeUpdate = elementSupplyRepository.findAll().size();
        elementSupply.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restElementSupplyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, elementSupply.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(elementSupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the ElementSupply in the database
        List<ElementSupply> elementSupplyList = elementSupplyRepository.findAll();
        assertThat(elementSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchElementSupply() throws Exception {
        int databaseSizeBeforeUpdate = elementSupplyRepository.findAll().size();
        elementSupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restElementSupplyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(elementSupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the ElementSupply in the database
        List<ElementSupply> elementSupplyList = elementSupplyRepository.findAll();
        assertThat(elementSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamElementSupply() throws Exception {
        int databaseSizeBeforeUpdate = elementSupplyRepository.findAll().size();
        elementSupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restElementSupplyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(elementSupply)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ElementSupply in the database
        List<ElementSupply> elementSupplyList = elementSupplyRepository.findAll();
        assertThat(elementSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateElementSupplyWithPatch() throws Exception {
        // Initialize the database
        elementSupplyRepository.saveAndFlush(elementSupply);

        int databaseSizeBeforeUpdate = elementSupplyRepository.findAll().size();

        // Update the elementSupply using partial update
        ElementSupply partialUpdatedElementSupply = new ElementSupply();
        partialUpdatedElementSupply.setId(elementSupply.getId());

        partialUpdatedElementSupply.apparitions(UPDATED_APPARITIONS).markingType(UPDATED_MARKING_TYPE);

        restElementSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedElementSupply.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedElementSupply))
            )
            .andExpect(status().isOk());

        // Validate the ElementSupply in the database
        List<ElementSupply> elementSupplyList = elementSupplyRepository.findAll();
        assertThat(elementSupplyList).hasSize(databaseSizeBeforeUpdate);
        ElementSupply testElementSupply = elementSupplyList.get(elementSupplyList.size() - 1);
        assertThat(testElementSupply.getApparitions()).isEqualTo(UPDATED_APPARITIONS);
        assertThat(testElementSupply.getForcedMarking()).isEqualTo(DEFAULT_FORCED_MARKING);
        assertThat(testElementSupply.getMarkingType()).isEqualTo(UPDATED_MARKING_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateElementSupplyWithPatch() throws Exception {
        // Initialize the database
        elementSupplyRepository.saveAndFlush(elementSupply);

        int databaseSizeBeforeUpdate = elementSupplyRepository.findAll().size();

        // Update the elementSupply using partial update
        ElementSupply partialUpdatedElementSupply = new ElementSupply();
        partialUpdatedElementSupply.setId(elementSupply.getId());

        partialUpdatedElementSupply
            .apparitions(UPDATED_APPARITIONS)
            .forcedMarking(UPDATED_FORCED_MARKING)
            .markingType(UPDATED_MARKING_TYPE);

        restElementSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedElementSupply.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedElementSupply))
            )
            .andExpect(status().isOk());

        // Validate the ElementSupply in the database
        List<ElementSupply> elementSupplyList = elementSupplyRepository.findAll();
        assertThat(elementSupplyList).hasSize(databaseSizeBeforeUpdate);
        ElementSupply testElementSupply = elementSupplyList.get(elementSupplyList.size() - 1);
        assertThat(testElementSupply.getApparitions()).isEqualTo(UPDATED_APPARITIONS);
        assertThat(testElementSupply.getForcedMarking()).isEqualTo(UPDATED_FORCED_MARKING);
        assertThat(testElementSupply.getMarkingType()).isEqualTo(UPDATED_MARKING_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingElementSupply() throws Exception {
        int databaseSizeBeforeUpdate = elementSupplyRepository.findAll().size();
        elementSupply.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restElementSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, elementSupply.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(elementSupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the ElementSupply in the database
        List<ElementSupply> elementSupplyList = elementSupplyRepository.findAll();
        assertThat(elementSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchElementSupply() throws Exception {
        int databaseSizeBeforeUpdate = elementSupplyRepository.findAll().size();
        elementSupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restElementSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(elementSupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the ElementSupply in the database
        List<ElementSupply> elementSupplyList = elementSupplyRepository.findAll();
        assertThat(elementSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamElementSupply() throws Exception {
        int databaseSizeBeforeUpdate = elementSupplyRepository.findAll().size();
        elementSupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restElementSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(elementSupply))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ElementSupply in the database
        List<ElementSupply> elementSupplyList = elementSupplyRepository.findAll();
        assertThat(elementSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteElementSupply() throws Exception {
        // Initialize the database
        elementSupplyRepository.saveAndFlush(elementSupply);

        int databaseSizeBeforeDelete = elementSupplyRepository.findAll().size();

        // Delete the elementSupply
        restElementSupplyMockMvc
            .perform(delete(ENTITY_API_URL_ID, elementSupply.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ElementSupply> elementSupplyList = elementSupplyRepository.findAll();
        assertThat(elementSupplyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
