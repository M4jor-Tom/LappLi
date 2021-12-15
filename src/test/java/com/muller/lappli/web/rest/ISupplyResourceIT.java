package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.ElementSupply;
import com.muller.lappli.domain.ISupply;
import com.muller.lappli.domain.Strand;
import com.muller.lappli.repository.ISupplyRepository;
import com.muller.lappli.service.criteria.ISupplyCriteria;
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
 * Integration tests for the {@link ISupplyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ISupplyResourceIT {

    private static final Long DEFAULT_APPARITIONS = 1L;
    private static final Long UPDATED_APPARITIONS = 2L;
    private static final Long SMALLER_APPARITIONS = 1L - 1L;

    private static final Double DEFAULT_MILIMETER_DIAMETER = 1D;
    //private static final Double UPDATED_MILIMETER_DIAMETER = 2D;
    //private static final Double SMALLER_MILIMETER_DIAMETER = 1D - 1D;

    private static final Double DEFAULT_GRAM_PER_METER_LINEAR_MASS = 1D;
    private static final Double UPDATED_GRAM_PER_METER_LINEAR_MASS = 2D;
    private static final Double SMALLER_GRAM_PER_METER_LINEAR_MASS = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/i-supplies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ISupplyRepository iSupplyRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restISupplyMockMvc;

    private ISupply iSupply;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ISupply createEntity(EntityManager em) {
        ISupply iSupply = new ElementSupply().apparitions(DEFAULT_APPARITIONS);
        //.milimeterDiameter(DEFAULT_MILIMETER_DIAMETER)
        //.gramPerMeterLinearMass(DEFAULT_GRAM_PER_METER_LINEAR_MASS);
        // Add required entity
        Strand strand;
        if (TestUtil.findAll(em, Strand.class).isEmpty()) {
            strand = StrandResourceIT.createEntity(em);
            em.persist(strand);
            em.flush();
        } else {
            strand = TestUtil.findAll(em, Strand.class).get(0);
        }
        iSupply.setStrand(strand);
        return iSupply;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ISupply createUpdatedEntity(EntityManager em) {
        ISupply iSupply = new ElementSupply().apparitions(UPDATED_APPARITIONS);
        //.milimeterDiameter(UPDATED_MILIMETER_DIAMETER)
        //.gramPerMeterLinearMass(UPDATED_GRAM_PER_METER_LINEAR_MASS);
        // Add required entity
        Strand strand;
        if (TestUtil.findAll(em, Strand.class).isEmpty()) {
            strand = StrandResourceIT.createUpdatedEntity(em);
            em.persist(strand);
            em.flush();
        } else {
            strand = TestUtil.findAll(em, Strand.class).get(0);
        }
        iSupply.setStrand(strand);
        return iSupply;
    }

    @BeforeEach
    public void initTest() {
        iSupply = createEntity(em);
    }

    @Test
    @Transactional
    void createISupply() throws Exception {
        int databaseSizeBeforeCreate = iSupplyRepository.findAll().size();
        // Create the ISupply
        restISupplyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(iSupply)))
            .andExpect(status().isCreated());

        // Validate the ISupply in the database
        List<ISupply> iSupplyList = iSupplyRepository.findAll();
        assertThat(iSupplyList).hasSize(databaseSizeBeforeCreate + 1);
        ISupply testISupply = iSupplyList.get(iSupplyList.size() - 1);
        assertThat(testISupply.getApparitions()).isEqualTo(DEFAULT_APPARITIONS);
        assertThat(testISupply.getMilimeterDiameter()).isEqualTo(DEFAULT_MILIMETER_DIAMETER);
        assertThat(testISupply.getGramPerMeterLinearMass()).isEqualTo(DEFAULT_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void createISupplyWithExistingId() throws Exception {
        // Create the ISupply with an existing ID
        iSupply.setId(1L);

        int databaseSizeBeforeCreate = iSupplyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restISupplyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(iSupply)))
            .andExpect(status().isBadRequest());

        // Validate the ISupply in the database
        List<ISupply> iSupplyList = iSupplyRepository.findAll();
        assertThat(iSupplyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkApparitionsIsRequired() throws Exception {
        int databaseSizeBeforeTest = iSupplyRepository.findAll().size();
        // set the field null
        iSupply.setApparitions(null);

        // Create the ISupply, which fails.

        restISupplyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(iSupply)))
            .andExpect(status().isBadRequest());

        List<ISupply> iSupplyList = iSupplyRepository.findAll();
        assertThat(iSupplyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllISupplies() throws Exception {
        // Initialize the database
        iSupplyRepository.saveAndFlush(iSupply);

        // Get all the iSupplyList
        restISupplyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(iSupply.getId().intValue())))
            .andExpect(jsonPath("$.[*].apparitions").value(hasItem(DEFAULT_APPARITIONS.intValue())))
            .andExpect(jsonPath("$.[*].milimeterDiameter").value(hasItem(DEFAULT_MILIMETER_DIAMETER.doubleValue())))
            .andExpect(jsonPath("$.[*].gramPerMeterLinearMass").value(hasItem(DEFAULT_GRAM_PER_METER_LINEAR_MASS.doubleValue())));
    }

    @Test
    @Transactional
    void getISupply() throws Exception {
        // Initialize the database
        iSupplyRepository.saveAndFlush(iSupply);

        // Get the iSupply
        restISupplyMockMvc
            .perform(get(ENTITY_API_URL_ID, iSupply.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(iSupply.getId().intValue()))
            .andExpect(jsonPath("$.apparitions").value(DEFAULT_APPARITIONS.intValue()))
            .andExpect(jsonPath("$.milimeterDiameter").value(DEFAULT_MILIMETER_DIAMETER.doubleValue()))
            .andExpect(jsonPath("$.gramPerMeterLinearMass").value(DEFAULT_GRAM_PER_METER_LINEAR_MASS.doubleValue()));
    }

    @Test
    @Transactional
    void getISuppliesByIdFiltering() throws Exception {
        // Initialize the database
        iSupplyRepository.saveAndFlush(iSupply);

        Long id = iSupply.getId();

        defaultISupplyShouldBeFound("id.equals=" + id);
        defaultISupplyShouldNotBeFound("id.notEquals=" + id);

        defaultISupplyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultISupplyShouldNotBeFound("id.greaterThan=" + id);

        defaultISupplyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultISupplyShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllISuppliesByApparitionsIsEqualToSomething() throws Exception {
        // Initialize the database
        iSupplyRepository.saveAndFlush(iSupply);

        // Get all the iSupplyList where apparitions equals to DEFAULT_APPARITIONS
        defaultISupplyShouldBeFound("apparitions.equals=" + DEFAULT_APPARITIONS);

        // Get all the iSupplyList where apparitions equals to UPDATED_APPARITIONS
        defaultISupplyShouldNotBeFound("apparitions.equals=" + UPDATED_APPARITIONS);
    }

    @Test
    @Transactional
    void getAllISuppliesByApparitionsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        iSupplyRepository.saveAndFlush(iSupply);

        // Get all the iSupplyList where apparitions not equals to DEFAULT_APPARITIONS
        defaultISupplyShouldNotBeFound("apparitions.notEquals=" + DEFAULT_APPARITIONS);

        // Get all the iSupplyList where apparitions not equals to UPDATED_APPARITIONS
        defaultISupplyShouldBeFound("apparitions.notEquals=" + UPDATED_APPARITIONS);
    }

    @Test
    @Transactional
    void getAllISuppliesByApparitionsIsInShouldWork() throws Exception {
        // Initialize the database
        iSupplyRepository.saveAndFlush(iSupply);

        // Get all the iSupplyList where apparitions in DEFAULT_APPARITIONS or UPDATED_APPARITIONS
        defaultISupplyShouldBeFound("apparitions.in=" + DEFAULT_APPARITIONS + "," + UPDATED_APPARITIONS);

        // Get all the iSupplyList where apparitions equals to UPDATED_APPARITIONS
        defaultISupplyShouldNotBeFound("apparitions.in=" + UPDATED_APPARITIONS);
    }

    @Test
    @Transactional
    void getAllISuppliesByApparitionsIsNullOrNotNull() throws Exception {
        // Initialize the database
        iSupplyRepository.saveAndFlush(iSupply);

        // Get all the iSupplyList where apparitions is not null
        defaultISupplyShouldBeFound("apparitions.specified=true");

        // Get all the iSupplyList where apparitions is null
        defaultISupplyShouldNotBeFound("apparitions.specified=false");
    }

    @Test
    @Transactional
    void getAllISuppliesByApparitionsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        iSupplyRepository.saveAndFlush(iSupply);

        // Get all the iSupplyList where apparitions is greater than or equal to DEFAULT_APPARITIONS
        defaultISupplyShouldBeFound("apparitions.greaterThanOrEqual=" + DEFAULT_APPARITIONS);

        // Get all the iSupplyList where apparitions is greater than or equal to UPDATED_APPARITIONS
        defaultISupplyShouldNotBeFound("apparitions.greaterThanOrEqual=" + UPDATED_APPARITIONS);
    }

    @Test
    @Transactional
    void getAllISuppliesByApparitionsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        iSupplyRepository.saveAndFlush(iSupply);

        // Get all the iSupplyList where apparitions is less than or equal to DEFAULT_APPARITIONS
        defaultISupplyShouldBeFound("apparitions.lessThanOrEqual=" + DEFAULT_APPARITIONS);

        // Get all the iSupplyList where apparitions is less than or equal to SMALLER_APPARITIONS
        defaultISupplyShouldNotBeFound("apparitions.lessThanOrEqual=" + SMALLER_APPARITIONS);
    }

    @Test
    @Transactional
    void getAllISuppliesByApparitionsIsLessThanSomething() throws Exception {
        // Initialize the database
        iSupplyRepository.saveAndFlush(iSupply);

        // Get all the iSupplyList where apparitions is less than DEFAULT_APPARITIONS
        defaultISupplyShouldNotBeFound("apparitions.lessThan=" + DEFAULT_APPARITIONS);

        // Get all the iSupplyList where apparitions is less than UPDATED_APPARITIONS
        defaultISupplyShouldBeFound("apparitions.lessThan=" + UPDATED_APPARITIONS);
    }

    @Test
    @Transactional
    void getAllISuppliesByApparitionsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        iSupplyRepository.saveAndFlush(iSupply);

        // Get all the iSupplyList where apparitions is greater than DEFAULT_APPARITIONS
        defaultISupplyShouldNotBeFound("apparitions.greaterThan=" + DEFAULT_APPARITIONS);

        // Get all the iSupplyList where apparitions is greater than SMALLER_APPARITIONS
        defaultISupplyShouldBeFound("apparitions.greaterThan=" + SMALLER_APPARITIONS);
    }

    @Test
    @Transactional
    void getAllISuppliesByGramPerMeterLinearMassIsEqualToSomething() throws Exception {
        // Initialize the database
        iSupplyRepository.saveAndFlush(iSupply);

        // Get all the iSupplyList where gramPerMeterLinearMass equals to DEFAULT_GRAM_PER_METER_LINEAR_MASS
        defaultISupplyShouldBeFound("gramPerMeterLinearMass.equals=" + DEFAULT_GRAM_PER_METER_LINEAR_MASS);

        // Get all the iSupplyList where gramPerMeterLinearMass equals to UPDATED_GRAM_PER_METER_LINEAR_MASS
        defaultISupplyShouldNotBeFound("gramPerMeterLinearMass.equals=" + UPDATED_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllISuppliesByGramPerMeterLinearMassIsNotEqualToSomething() throws Exception {
        // Initialize the database
        iSupplyRepository.saveAndFlush(iSupply);

        // Get all the iSupplyList where gramPerMeterLinearMass not equals to DEFAULT_GRAM_PER_METER_LINEAR_MASS
        defaultISupplyShouldNotBeFound("gramPerMeterLinearMass.notEquals=" + DEFAULT_GRAM_PER_METER_LINEAR_MASS);

        // Get all the iSupplyList where gramPerMeterLinearMass not equals to UPDATED_GRAM_PER_METER_LINEAR_MASS
        defaultISupplyShouldBeFound("gramPerMeterLinearMass.notEquals=" + UPDATED_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllISuppliesByGramPerMeterLinearMassIsInShouldWork() throws Exception {
        // Initialize the database
        iSupplyRepository.saveAndFlush(iSupply);

        // Get all the iSupplyList where gramPerMeterLinearMass in DEFAULT_GRAM_PER_METER_LINEAR_MASS or UPDATED_GRAM_PER_METER_LINEAR_MASS
        defaultISupplyShouldBeFound(
            "gramPerMeterLinearMass.in=" + DEFAULT_GRAM_PER_METER_LINEAR_MASS + "," + UPDATED_GRAM_PER_METER_LINEAR_MASS
        );

        // Get all the iSupplyList where gramPerMeterLinearMass equals to UPDATED_GRAM_PER_METER_LINEAR_MASS
        defaultISupplyShouldNotBeFound("gramPerMeterLinearMass.in=" + UPDATED_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllISuppliesByGramPerMeterLinearMassIsNullOrNotNull() throws Exception {
        // Initialize the database
        iSupplyRepository.saveAndFlush(iSupply);

        // Get all the iSupplyList where gramPerMeterLinearMass is not null
        defaultISupplyShouldBeFound("gramPerMeterLinearMass.specified=true");

        // Get all the iSupplyList where gramPerMeterLinearMass is null
        defaultISupplyShouldNotBeFound("gramPerMeterLinearMass.specified=false");
    }

    @Test
    @Transactional
    void getAllISuppliesByGramPerMeterLinearMassIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        iSupplyRepository.saveAndFlush(iSupply);

        // Get all the iSupplyList where gramPerMeterLinearMass is greater than or equal to DEFAULT_GRAM_PER_METER_LINEAR_MASS
        defaultISupplyShouldBeFound("gramPerMeterLinearMass.greaterThanOrEqual=" + DEFAULT_GRAM_PER_METER_LINEAR_MASS);

        // Get all the iSupplyList where gramPerMeterLinearMass is greater than or equal to UPDATED_GRAM_PER_METER_LINEAR_MASS
        defaultISupplyShouldNotBeFound("gramPerMeterLinearMass.greaterThanOrEqual=" + UPDATED_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllISuppliesByGramPerMeterLinearMassIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        iSupplyRepository.saveAndFlush(iSupply);

        // Get all the iSupplyList where gramPerMeterLinearMass is less than or equal to DEFAULT_GRAM_PER_METER_LINEAR_MASS
        defaultISupplyShouldBeFound("gramPerMeterLinearMass.lessThanOrEqual=" + DEFAULT_GRAM_PER_METER_LINEAR_MASS);

        // Get all the iSupplyList where gramPerMeterLinearMass is less than or equal to SMALLER_GRAM_PER_METER_LINEAR_MASS
        defaultISupplyShouldNotBeFound("gramPerMeterLinearMass.lessThanOrEqual=" + SMALLER_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllISuppliesByGramPerMeterLinearMassIsLessThanSomething() throws Exception {
        // Initialize the database
        iSupplyRepository.saveAndFlush(iSupply);

        // Get all the iSupplyList where gramPerMeterLinearMass is less than DEFAULT_GRAM_PER_METER_LINEAR_MASS
        defaultISupplyShouldNotBeFound("gramPerMeterLinearMass.lessThan=" + DEFAULT_GRAM_PER_METER_LINEAR_MASS);

        // Get all the iSupplyList where gramPerMeterLinearMass is less than UPDATED_GRAM_PER_METER_LINEAR_MASS
        defaultISupplyShouldBeFound("gramPerMeterLinearMass.lessThan=" + UPDATED_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllISuppliesByGramPerMeterLinearMassIsGreaterThanSomething() throws Exception {
        // Initialize the database
        iSupplyRepository.saveAndFlush(iSupply);

        // Get all the iSupplyList where gramPerMeterLinearMass is greater than DEFAULT_GRAM_PER_METER_LINEAR_MASS
        defaultISupplyShouldNotBeFound("gramPerMeterLinearMass.greaterThan=" + DEFAULT_GRAM_PER_METER_LINEAR_MASS);

        // Get all the iSupplyList where gramPerMeterLinearMass is greater than SMALLER_GRAM_PER_METER_LINEAR_MASS
        defaultISupplyShouldBeFound("gramPerMeterLinearMass.greaterThan=" + SMALLER_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void getAllISuppliesByStrandIsEqualToSomething() throws Exception {
        // Initialize the database
        iSupplyRepository.saveAndFlush(iSupply);
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
        iSupply.setStrand(strand);
        iSupplyRepository.saveAndFlush(iSupply);
        Long strandId = strand.getId();

        // Get all the iSupplyList where strand equals to strandId
        defaultISupplyShouldBeFound("strandId.equals=" + strandId);

        // Get all the iSupplyList where strand equals to (strandId + 1)
        defaultISupplyShouldNotBeFound("strandId.equals=" + (strandId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultISupplyShouldBeFound(String filter) throws Exception {
        restISupplyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(iSupply.getId().intValue())))
            .andExpect(jsonPath("$.[*].apparitions").value(hasItem(DEFAULT_APPARITIONS.intValue())))
            .andExpect(jsonPath("$.[*].milimeterDiameter").value(hasItem(DEFAULT_MILIMETER_DIAMETER.doubleValue())))
            .andExpect(jsonPath("$.[*].gramPerMeterLinearMass").value(hasItem(DEFAULT_GRAM_PER_METER_LINEAR_MASS.doubleValue())));

        // Check, that the count call also returns 1
        restISupplyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultISupplyShouldNotBeFound(String filter) throws Exception {
        restISupplyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restISupplyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingISupply() throws Exception {
        // Get the iSupply
        restISupplyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNonExistingISupply() throws Exception {
        int databaseSizeBeforeUpdate = iSupplyRepository.findAll().size();
        iSupply.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restISupplyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, iSupply.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(iSupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the ISupply in the database
        List<ISupply> iSupplyList = iSupplyRepository.findAll();
        assertThat(iSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchISupply() throws Exception {
        int databaseSizeBeforeUpdate = iSupplyRepository.findAll().size();
        iSupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restISupplyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(iSupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the ISupply in the database
        List<ISupply> iSupplyList = iSupplyRepository.findAll();
        assertThat(iSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamISupply() throws Exception {
        int databaseSizeBeforeUpdate = iSupplyRepository.findAll().size();
        iSupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restISupplyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(iSupply)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ISupply in the database
        List<ISupply> iSupplyList = iSupplyRepository.findAll();
        assertThat(iSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateISupplyWithPatch() throws Exception {
        // Initialize the database
        iSupplyRepository.saveAndFlush(iSupply);

        int databaseSizeBeforeUpdate = iSupplyRepository.findAll().size();

        // Update the iSupply using partial update
        ISupply partialUpdatedISupply = new ElementSupply();
        partialUpdatedISupply.setId(iSupply.getId());

        restISupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedISupply.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedISupply))
            )
            .andExpect(status().isOk());

        // Validate the ISupply in the database
        List<ISupply> iSupplyList = iSupplyRepository.findAll();
        assertThat(iSupplyList).hasSize(databaseSizeBeforeUpdate);
        ISupply testISupply = iSupplyList.get(iSupplyList.size() - 1);
        assertThat(testISupply.getApparitions()).isEqualTo(DEFAULT_APPARITIONS);
        assertThat(testISupply.getMilimeterDiameter()).isEqualTo(DEFAULT_MILIMETER_DIAMETER);
        assertThat(testISupply.getGramPerMeterLinearMass()).isEqualTo(DEFAULT_GRAM_PER_METER_LINEAR_MASS);
    }

    @Test
    @Transactional
    void patchNonExistingISupply() throws Exception {
        int databaseSizeBeforeUpdate = iSupplyRepository.findAll().size();
        iSupply.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restISupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, iSupply.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(iSupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the ISupply in the database
        List<ISupply> iSupplyList = iSupplyRepository.findAll();
        assertThat(iSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchISupply() throws Exception {
        int databaseSizeBeforeUpdate = iSupplyRepository.findAll().size();
        iSupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restISupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(iSupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the ISupply in the database
        List<ISupply> iSupplyList = iSupplyRepository.findAll();
        assertThat(iSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamISupply() throws Exception {
        int databaseSizeBeforeUpdate = iSupplyRepository.findAll().size();
        iSupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restISupplyMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(iSupply)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ISupply in the database
        List<ISupply> iSupplyList = iSupplyRepository.findAll();
        assertThat(iSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteISupply() throws Exception {
        // Initialize the database
        iSupplyRepository.saveAndFlush(iSupply);

        int databaseSizeBeforeDelete = iSupplyRepository.findAll().size();

        // Delete the iSupply
        restISupplyMockMvc
            .perform(delete(ENTITY_API_URL_ID, iSupply.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ISupply> iSupplyList = iSupplyRepository.findAll();
        assertThat(iSupplyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
