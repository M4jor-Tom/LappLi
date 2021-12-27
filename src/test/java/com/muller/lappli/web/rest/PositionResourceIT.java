package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.BangleSupply;
import com.muller.lappli.domain.CentralAssembly;
import com.muller.lappli.domain.CoreAssembly;
import com.muller.lappli.domain.CustomComponentSupply;
import com.muller.lappli.domain.ElementSupply;
import com.muller.lappli.domain.IntersticeAssembly;
import com.muller.lappli.domain.OneStudySupply;
import com.muller.lappli.domain.Position;
import com.muller.lappli.repository.PositionRepository;
import com.muller.lappli.service.criteria.PositionCriteria;
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
 * Integration tests for the {@link PositionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PositionResourceIT {

    private static final Long DEFAULT_VALUE = 1L;
    private static final Long UPDATED_VALUE = 2L;
    private static final Long SMALLER_VALUE = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/positions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPositionMockMvc;

    private Position position;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Position createEntity(EntityManager em) {
        Position position = new Position().value(DEFAULT_VALUE);
        return position;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Position createUpdatedEntity(EntityManager em) {
        Position position = new Position().value(UPDATED_VALUE);
        return position;
    }

    @BeforeEach
    public void initTest() {
        position = createEntity(em);
    }

    @Test
    @Transactional
    void createPosition() throws Exception {
        int databaseSizeBeforeCreate = positionRepository.findAll().size();
        // Create the Position
        restPositionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(position)))
            .andExpect(status().isCreated());

        // Validate the Position in the database
        List<Position> positionList = positionRepository.findAll();
        assertThat(positionList).hasSize(databaseSizeBeforeCreate + 1);
        Position testPosition = positionList.get(positionList.size() - 1);
        assertThat(testPosition.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void createPositionWithExistingId() throws Exception {
        // Create the Position with an existing ID
        position.setId(1L);

        int databaseSizeBeforeCreate = positionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPositionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(position)))
            .andExpect(status().isBadRequest());

        // Validate the Position in the database
        List<Position> positionList = positionRepository.findAll();
        assertThat(positionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = positionRepository.findAll().size();
        // set the field null
        position.setValue(null);

        // Create the Position, which fails.

        restPositionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(position)))
            .andExpect(status().isBadRequest());

        List<Position> positionList = positionRepository.findAll();
        assertThat(positionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPositions() throws Exception {
        // Initialize the database
        positionRepository.saveAndFlush(position);

        // Get all the positionList
        restPositionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(position.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.intValue())));
    }

    @Test
    @Transactional
    void getPosition() throws Exception {
        // Initialize the database
        positionRepository.saveAndFlush(position);

        // Get the position
        restPositionMockMvc
            .perform(get(ENTITY_API_URL_ID, position.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(position.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.intValue()));
    }

    @Test
    @Transactional
    void getPositionsByIdFiltering() throws Exception {
        // Initialize the database
        positionRepository.saveAndFlush(position);

        Long id = position.getId();

        defaultPositionShouldBeFound("id.equals=" + id);
        defaultPositionShouldNotBeFound("id.notEquals=" + id);

        defaultPositionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPositionShouldNotBeFound("id.greaterThan=" + id);

        defaultPositionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPositionShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPositionsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        positionRepository.saveAndFlush(position);

        // Get all the positionList where value equals to DEFAULT_VALUE
        defaultPositionShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the positionList where value equals to UPDATED_VALUE
        defaultPositionShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllPositionsByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        positionRepository.saveAndFlush(position);

        // Get all the positionList where value not equals to DEFAULT_VALUE
        defaultPositionShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the positionList where value not equals to UPDATED_VALUE
        defaultPositionShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllPositionsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        positionRepository.saveAndFlush(position);

        // Get all the positionList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultPositionShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the positionList where value equals to UPDATED_VALUE
        defaultPositionShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllPositionsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        positionRepository.saveAndFlush(position);

        // Get all the positionList where value is not null
        defaultPositionShouldBeFound("value.specified=true");

        // Get all the positionList where value is null
        defaultPositionShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    void getAllPositionsByValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        positionRepository.saveAndFlush(position);

        // Get all the positionList where value is greater than or equal to DEFAULT_VALUE
        defaultPositionShouldBeFound("value.greaterThanOrEqual=" + DEFAULT_VALUE);

        // Get all the positionList where value is greater than or equal to UPDATED_VALUE
        defaultPositionShouldNotBeFound("value.greaterThanOrEqual=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllPositionsByValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        positionRepository.saveAndFlush(position);

        // Get all the positionList where value is less than or equal to DEFAULT_VALUE
        defaultPositionShouldBeFound("value.lessThanOrEqual=" + DEFAULT_VALUE);

        // Get all the positionList where value is less than or equal to SMALLER_VALUE
        defaultPositionShouldNotBeFound("value.lessThanOrEqual=" + SMALLER_VALUE);
    }

    @Test
    @Transactional
    void getAllPositionsByValueIsLessThanSomething() throws Exception {
        // Initialize the database
        positionRepository.saveAndFlush(position);

        // Get all the positionList where value is less than DEFAULT_VALUE
        defaultPositionShouldNotBeFound("value.lessThan=" + DEFAULT_VALUE);

        // Get all the positionList where value is less than UPDATED_VALUE
        defaultPositionShouldBeFound("value.lessThan=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllPositionsByValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        positionRepository.saveAndFlush(position);

        // Get all the positionList where value is greater than DEFAULT_VALUE
        defaultPositionShouldNotBeFound("value.greaterThan=" + DEFAULT_VALUE);

        // Get all the positionList where value is greater than SMALLER_VALUE
        defaultPositionShouldBeFound("value.greaterThan=" + SMALLER_VALUE);
    }

    @Test
    @Transactional
    void getAllPositionsByElementSupplyIsEqualToSomething() throws Exception {
        // Initialize the database
        positionRepository.saveAndFlush(position);
        ElementSupply elementSupply;
        if (TestUtil.findAll(em, ElementSupply.class).isEmpty()) {
            elementSupply = ElementSupplyResourceIT.createEntity(em);
            em.persist(elementSupply);
            em.flush();
        } else {
            elementSupply = TestUtil.findAll(em, ElementSupply.class).get(0);
        }
        em.persist(elementSupply);
        em.flush();
        position.setElementSupply(elementSupply);
        positionRepository.saveAndFlush(position);
        Long elementSupplyId = elementSupply.getId();

        // Get all the positionList where elementSupply equals to elementSupplyId
        defaultPositionShouldBeFound("elementSupplyId.equals=" + elementSupplyId);

        // Get all the positionList where elementSupply equals to (elementSupplyId + 1)
        defaultPositionShouldNotBeFound("elementSupplyId.equals=" + (elementSupplyId + 1));
    }

    @Test
    @Transactional
    void getAllPositionsByBangleSupplyIsEqualToSomething() throws Exception {
        // Initialize the database
        positionRepository.saveAndFlush(position);
        BangleSupply bangleSupply;
        if (TestUtil.findAll(em, BangleSupply.class).isEmpty()) {
            bangleSupply = BangleSupplyResourceIT.createEntity(em);
            em.persist(bangleSupply);
            em.flush();
        } else {
            bangleSupply = TestUtil.findAll(em, BangleSupply.class).get(0);
        }
        em.persist(bangleSupply);
        em.flush();
        position.setBangleSupply(bangleSupply);
        positionRepository.saveAndFlush(position);
        Long bangleSupplyId = bangleSupply.getId();

        // Get all the positionList where bangleSupply equals to bangleSupplyId
        defaultPositionShouldBeFound("bangleSupplyId.equals=" + bangleSupplyId);

        // Get all the positionList where bangleSupply equals to (bangleSupplyId + 1)
        defaultPositionShouldNotBeFound("bangleSupplyId.equals=" + (bangleSupplyId + 1));
    }

    @Test
    @Transactional
    void getAllPositionsByCustomComponentSupplyIsEqualToSomething() throws Exception {
        // Initialize the database
        positionRepository.saveAndFlush(position);
        CustomComponentSupply customComponentSupply;
        if (TestUtil.findAll(em, CustomComponentSupply.class).isEmpty()) {
            customComponentSupply = CustomComponentSupplyResourceIT.createEntity(em);
            em.persist(customComponentSupply);
            em.flush();
        } else {
            customComponentSupply = TestUtil.findAll(em, CustomComponentSupply.class).get(0);
        }
        em.persist(customComponentSupply);
        em.flush();
        position.setCustomComponentSupply(customComponentSupply);
        positionRepository.saveAndFlush(position);
        Long customComponentSupplyId = customComponentSupply.getId();

        // Get all the positionList where customComponentSupply equals to customComponentSupplyId
        defaultPositionShouldBeFound("customComponentSupplyId.equals=" + customComponentSupplyId);

        // Get all the positionList where customComponentSupply equals to (customComponentSupplyId + 1)
        defaultPositionShouldNotBeFound("customComponentSupplyId.equals=" + (customComponentSupplyId + 1));
    }

    @Test
    @Transactional
    void getAllPositionsByOneStudySupplyIsEqualToSomething() throws Exception {
        // Initialize the database
        positionRepository.saveAndFlush(position);
        OneStudySupply oneStudySupply;
        if (TestUtil.findAll(em, OneStudySupply.class).isEmpty()) {
            oneStudySupply = OneStudySupplyResourceIT.createEntity(em);
            em.persist(oneStudySupply);
            em.flush();
        } else {
            oneStudySupply = TestUtil.findAll(em, OneStudySupply.class).get(0);
        }
        em.persist(oneStudySupply);
        em.flush();
        position.setOneStudySupply(oneStudySupply);
        positionRepository.saveAndFlush(position);
        Long oneStudySupplyId = oneStudySupply.getId();

        // Get all the positionList where oneStudySupply equals to oneStudySupplyId
        defaultPositionShouldBeFound("oneStudySupplyId.equals=" + oneStudySupplyId);

        // Get all the positionList where oneStudySupply equals to (oneStudySupplyId + 1)
        defaultPositionShouldNotBeFound("oneStudySupplyId.equals=" + (oneStudySupplyId + 1));
    }

    @Test
    @Transactional
    void getAllPositionsByOwnerCentralAssemblyIsEqualToSomething() throws Exception {
        // Initialize the database
        positionRepository.saveAndFlush(position);
        CentralAssembly ownerCentralAssembly;
        if (TestUtil.findAll(em, CentralAssembly.class).isEmpty()) {
            ownerCentralAssembly = CentralAssemblyResourceIT.createEntity(em);
            em.persist(ownerCentralAssembly);
            em.flush();
        } else {
            ownerCentralAssembly = TestUtil.findAll(em, CentralAssembly.class).get(0);
        }
        em.persist(ownerCentralAssembly);
        em.flush();
        position.setOwnerCentralAssembly(ownerCentralAssembly);
        ownerCentralAssembly.setPosition(position);
        positionRepository.saveAndFlush(position);
        Long ownerCentralAssemblyId = ownerCentralAssembly.getId();

        // Get all the positionList where ownerCentralAssembly equals to ownerCentralAssemblyId
        defaultPositionShouldBeFound("ownerCentralAssemblyId.equals=" + ownerCentralAssemblyId);

        // Get all the positionList where ownerCentralAssembly equals to (ownerCentralAssemblyId + 1)
        defaultPositionShouldNotBeFound("ownerCentralAssemblyId.equals=" + (ownerCentralAssemblyId + 1));
    }

    @Test
    @Transactional
    void getAllPositionsByOwnerCoreAssemblyIsEqualToSomething() throws Exception {
        // Initialize the database
        positionRepository.saveAndFlush(position);
        CoreAssembly ownerCoreAssembly;
        if (TestUtil.findAll(em, CoreAssembly.class).isEmpty()) {
            ownerCoreAssembly = CoreAssemblyResourceIT.createEntity(em);
            em.persist(ownerCoreAssembly);
            em.flush();
        } else {
            ownerCoreAssembly = TestUtil.findAll(em, CoreAssembly.class).get(0);
        }
        em.persist(ownerCoreAssembly);
        em.flush();
        position.setOwnerCoreAssembly(ownerCoreAssembly);
        positionRepository.saveAndFlush(position);
        Long ownerCoreAssemblyId = ownerCoreAssembly.getId();

        // Get all the positionList where ownerCoreAssembly equals to ownerCoreAssemblyId
        defaultPositionShouldBeFound("ownerCoreAssemblyId.equals=" + ownerCoreAssemblyId);

        // Get all the positionList where ownerCoreAssembly equals to (ownerCoreAssemblyId + 1)
        defaultPositionShouldNotBeFound("ownerCoreAssemblyId.equals=" + (ownerCoreAssemblyId + 1));
    }

    @Test
    @Transactional
    void getAllPositionsByOwnerIntersticeAssemblyIsEqualToSomething() throws Exception {
        // Initialize the database
        positionRepository.saveAndFlush(position);
        IntersticeAssembly ownerIntersticeAssembly;
        if (TestUtil.findAll(em, IntersticeAssembly.class).isEmpty()) {
            ownerIntersticeAssembly = IntersticeAssemblyResourceIT.createEntity(em);
            em.persist(ownerIntersticeAssembly);
            em.flush();
        } else {
            ownerIntersticeAssembly = TestUtil.findAll(em, IntersticeAssembly.class).get(0);
        }
        em.persist(ownerIntersticeAssembly);
        em.flush();
        position.setOwnerIntersticeAssembly(ownerIntersticeAssembly);
        positionRepository.saveAndFlush(position);
        Long ownerIntersticeAssemblyId = ownerIntersticeAssembly.getId();

        // Get all the positionList where ownerIntersticeAssembly equals to ownerIntersticeAssemblyId
        defaultPositionShouldBeFound("ownerIntersticeAssemblyId.equals=" + ownerIntersticeAssemblyId);

        // Get all the positionList where ownerIntersticeAssembly equals to (ownerIntersticeAssemblyId + 1)
        defaultPositionShouldNotBeFound("ownerIntersticeAssemblyId.equals=" + (ownerIntersticeAssemblyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPositionShouldBeFound(String filter) throws Exception {
        restPositionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(position.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.intValue())));

        // Check, that the count call also returns 1
        restPositionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPositionShouldNotBeFound(String filter) throws Exception {
        restPositionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPositionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPosition() throws Exception {
        // Get the position
        restPositionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPosition() throws Exception {
        // Initialize the database
        positionRepository.saveAndFlush(position);

        int databaseSizeBeforeUpdate = positionRepository.findAll().size();

        // Update the position
        Position updatedPosition = positionRepository.findById(position.getId()).get();
        // Disconnect from session so that the updates on updatedPosition are not directly saved in db
        em.detach(updatedPosition);
        updatedPosition.value(UPDATED_VALUE);

        ResultMatcher expectuedResult = updatedPosition.isRight() ? status().isOk() : status().isBadRequest();

        restPositionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPosition.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPosition))
            )
            .andExpect(expectuedResult);

        // Validate the Position in the database
        List<Position> positionList = positionRepository.findAll();
        assertThat(positionList).hasSize(databaseSizeBeforeUpdate);
        Position testPosition = positionList.get(positionList.size() - 1);
        assertThat(testPosition.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingPosition() throws Exception {
        int databaseSizeBeforeUpdate = positionRepository.findAll().size();
        position.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPositionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, position.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(position))
            )
            .andExpect(status().isBadRequest());

        // Validate the Position in the database
        List<Position> positionList = positionRepository.findAll();
        assertThat(positionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPosition() throws Exception {
        int databaseSizeBeforeUpdate = positionRepository.findAll().size();
        position.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPositionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(position))
            )
            .andExpect(status().isBadRequest());

        // Validate the Position in the database
        List<Position> positionList = positionRepository.findAll();
        assertThat(positionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPosition() throws Exception {
        int databaseSizeBeforeUpdate = positionRepository.findAll().size();
        position.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPositionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(position)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Position in the database
        List<Position> positionList = positionRepository.findAll();
        assertThat(positionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePositionWithPatch() throws Exception {
        // Initialize the database
        positionRepository.saveAndFlush(position);

        int databaseSizeBeforeUpdate = positionRepository.findAll().size();

        // Update the position using partial update
        Position partialUpdatedPosition = new Position();
        partialUpdatedPosition.setId(position.getId());

        restPositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPosition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPosition))
            )
            .andExpect(status().isOk());

        // Validate the Position in the database
        List<Position> positionList = positionRepository.findAll();
        assertThat(positionList).hasSize(databaseSizeBeforeUpdate);
        Position testPosition = positionList.get(positionList.size() - 1);
        assertThat(testPosition.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void fullUpdatePositionWithPatch() throws Exception {
        // Initialize the database
        positionRepository.saveAndFlush(position);

        int databaseSizeBeforeUpdate = positionRepository.findAll().size();

        // Update the position using partial update
        Position partialUpdatedPosition = new Position();
        partialUpdatedPosition.setId(position.getId());

        partialUpdatedPosition.value(UPDATED_VALUE);

        restPositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPosition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPosition))
            )
            .andExpect(status().isOk());

        // Validate the Position in the database
        List<Position> positionList = positionRepository.findAll();
        assertThat(positionList).hasSize(databaseSizeBeforeUpdate);
        Position testPosition = positionList.get(positionList.size() - 1);
        assertThat(testPosition.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingPosition() throws Exception {
        int databaseSizeBeforeUpdate = positionRepository.findAll().size();
        position.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, position.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(position))
            )
            .andExpect(status().isBadRequest());

        // Validate the Position in the database
        List<Position> positionList = positionRepository.findAll();
        assertThat(positionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPosition() throws Exception {
        int databaseSizeBeforeUpdate = positionRepository.findAll().size();
        position.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(position))
            )
            .andExpect(status().isBadRequest());

        // Validate the Position in the database
        List<Position> positionList = positionRepository.findAll();
        assertThat(positionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPosition() throws Exception {
        int databaseSizeBeforeUpdate = positionRepository.findAll().size();
        position.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPositionMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(position)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Position in the database
        List<Position> positionList = positionRepository.findAll();
        assertThat(positionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePosition() throws Exception {
        // Initialize the database
        positionRepository.saveAndFlush(position);

        int databaseSizeBeforeDelete = positionRepository.findAll().size();

        // Delete the position
        restPositionMockMvc
            .perform(delete(ENTITY_API_URL_ID, position.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Position> positionList = positionRepository.findAll();
        assertThat(positionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
