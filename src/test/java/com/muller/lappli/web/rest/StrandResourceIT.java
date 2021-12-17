package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.BangleSupply;
import com.muller.lappli.domain.CustomComponentSupply;
import com.muller.lappli.domain.ElementSupply;
import com.muller.lappli.domain.Strand;
import com.muller.lappli.repository.StrandRepository;
import com.muller.lappli.service.criteria.StrandCriteria;
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
 * Integration tests for the {@link StrandResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StrandResourceIT {

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/strands";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StrandRepository strandRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStrandMockMvc;

    private Strand strand;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Strand createEntity(EntityManager em) {
        Strand strand = new Strand().designation(DEFAULT_DESIGNATION);
        return strand;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Strand createUpdatedEntity(EntityManager em) {
        Strand strand = new Strand().designation(UPDATED_DESIGNATION);
        return strand;
    }

    @BeforeEach
    public void initTest() {
        strand = createEntity(em);
    }

    @Test
    @Transactional
    void createStrand() throws Exception {
        int databaseSizeBeforeCreate = strandRepository.findAll().size();
        // Create the Strand
        restStrandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strand)))
            .andExpect(status().isCreated());

        // Validate the Strand in the database
        List<Strand> strandList = strandRepository.findAll();
        assertThat(strandList).hasSize(databaseSizeBeforeCreate + 1);
        Strand testStrand = strandList.get(strandList.size() - 1);
        assertThat(testStrand.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
    }

    @Test
    @Transactional
    void createStrandWithExistingId() throws Exception {
        // Create the Strand with an existing ID
        strand.setId(1L);

        int databaseSizeBeforeCreate = strandRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStrandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strand)))
            .andExpect(status().isBadRequest());

        // Validate the Strand in the database
        List<Strand> strandList = strandRepository.findAll();
        assertThat(strandList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = strandRepository.findAll().size();
        // set the field null
        strand.setDesignation(null);

        // Create the Strand, which fails.

        restStrandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strand)))
            .andExpect(status().isBadRequest());

        List<Strand> strandList = strandRepository.findAll();
        assertThat(strandList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStrands() throws Exception {
        // Initialize the database
        strandRepository.saveAndFlush(strand);

        // Get all the strandList
        restStrandMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(strand.getId().intValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)));
    }

    @Test
    @Transactional
    void getStrand() throws Exception {
        // Initialize the database
        strandRepository.saveAndFlush(strand);

        // Get the strand
        restStrandMockMvc
            .perform(get(ENTITY_API_URL_ID, strand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(strand.getId().intValue()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION));
    }

    @Test
    @Transactional
    void getStrandsByIdFiltering() throws Exception {
        // Initialize the database
        strandRepository.saveAndFlush(strand);

        Long id = strand.getId();

        defaultStrandShouldBeFound("id.equals=" + id);
        defaultStrandShouldNotBeFound("id.notEquals=" + id);

        defaultStrandShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStrandShouldNotBeFound("id.greaterThan=" + id);

        defaultStrandShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStrandShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllStrandsByDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        strandRepository.saveAndFlush(strand);

        // Get all the strandList where designation equals to DEFAULT_DESIGNATION
        defaultStrandShouldBeFound("designation.equals=" + DEFAULT_DESIGNATION);

        // Get all the strandList where designation equals to UPDATED_DESIGNATION
        defaultStrandShouldNotBeFound("designation.equals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllStrandsByDesignationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        strandRepository.saveAndFlush(strand);

        // Get all the strandList where designation not equals to DEFAULT_DESIGNATION
        defaultStrandShouldNotBeFound("designation.notEquals=" + DEFAULT_DESIGNATION);

        // Get all the strandList where designation not equals to UPDATED_DESIGNATION
        defaultStrandShouldBeFound("designation.notEquals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllStrandsByDesignationIsInShouldWork() throws Exception {
        // Initialize the database
        strandRepository.saveAndFlush(strand);

        // Get all the strandList where designation in DEFAULT_DESIGNATION or UPDATED_DESIGNATION
        defaultStrandShouldBeFound("designation.in=" + DEFAULT_DESIGNATION + "," + UPDATED_DESIGNATION);

        // Get all the strandList where designation equals to UPDATED_DESIGNATION
        defaultStrandShouldNotBeFound("designation.in=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllStrandsByDesignationIsNullOrNotNull() throws Exception {
        // Initialize the database
        strandRepository.saveAndFlush(strand);

        // Get all the strandList where designation is not null
        defaultStrandShouldBeFound("designation.specified=true");

        // Get all the strandList where designation is null
        defaultStrandShouldNotBeFound("designation.specified=false");
    }

    @Test
    @Transactional
    void getAllStrandsByDesignationContainsSomething() throws Exception {
        // Initialize the database
        strandRepository.saveAndFlush(strand);

        // Get all the strandList where designation contains DEFAULT_DESIGNATION
        defaultStrandShouldBeFound("designation.contains=" + DEFAULT_DESIGNATION);

        // Get all the strandList where designation contains UPDATED_DESIGNATION
        defaultStrandShouldNotBeFound("designation.contains=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllStrandsByDesignationNotContainsSomething() throws Exception {
        // Initialize the database
        strandRepository.saveAndFlush(strand);

        // Get all the strandList where designation does not contain DEFAULT_DESIGNATION
        defaultStrandShouldNotBeFound("designation.doesNotContain=" + DEFAULT_DESIGNATION);

        // Get all the strandList where designation does not contain UPDATED_DESIGNATION
        defaultStrandShouldBeFound("designation.doesNotContain=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllStrandsByElementSuppliesIsEqualToSomething() throws Exception {
        // Initialize the database
        strandRepository.saveAndFlush(strand);
        ElementSupply elementSupplies;
        if (TestUtil.findAll(em, ElementSupply.class).isEmpty()) {
            elementSupplies = ElementSupplyResourceIT.createEntity(em);
            em.persist(elementSupplies);
            em.flush();
        } else {
            elementSupplies = TestUtil.findAll(em, ElementSupply.class).get(0);
        }
        em.persist(elementSupplies);
        em.flush();
        strand.addElementSupplies(elementSupplies);
        strandRepository.saveAndFlush(strand);
        Long elementSuppliesId = elementSupplies.getId();

        // Get all the strandList where elementSupplies equals to elementSuppliesId
        defaultStrandShouldBeFound("elementSuppliesId.equals=" + elementSuppliesId);

        // Get all the strandList where elementSupplies equals to (elementSuppliesId + 1)
        defaultStrandShouldNotBeFound("elementSuppliesId.equals=" + (elementSuppliesId + 1));
    }

    @Test
    @Transactional
    void getAllStrandsByBangleSuppliesIsEqualToSomething() throws Exception {
        // Initialize the database
        strandRepository.saveAndFlush(strand);
        BangleSupply bangleSupplies;
        if (TestUtil.findAll(em, BangleSupply.class).isEmpty()) {
            bangleSupplies = BangleSupplyResourceIT.createEntity(em);
            em.persist(bangleSupplies);
            em.flush();
        } else {
            bangleSupplies = TestUtil.findAll(em, BangleSupply.class).get(0);
        }
        em.persist(bangleSupplies);
        em.flush();
        strand.addBangleSupplies(bangleSupplies);
        strandRepository.saveAndFlush(strand);
        Long bangleSuppliesId = bangleSupplies.getId();

        // Get all the strandList where bangleSupplies equals to bangleSuppliesId
        defaultStrandShouldBeFound("bangleSuppliesId.equals=" + bangleSuppliesId);

        // Get all the strandList where bangleSupplies equals to (bangleSuppliesId + 1)
        defaultStrandShouldNotBeFound("bangleSuppliesId.equals=" + (bangleSuppliesId + 1));
    }

    @Test
    @Transactional
    void getAllStrandsByCustomComponentSuppliesIsEqualToSomething() throws Exception {
        // Initialize the database
        strandRepository.saveAndFlush(strand);
        CustomComponentSupply customComponentSupplies;
        if (TestUtil.findAll(em, CustomComponentSupply.class).isEmpty()) {
            customComponentSupplies = CustomComponentSupplyResourceIT.createEntity(em);
            em.persist(customComponentSupplies);
            em.flush();
        } else {
            customComponentSupplies = TestUtil.findAll(em, CustomComponentSupply.class).get(0);
        }
        em.persist(customComponentSupplies);
        em.flush();
        strand.addCustomComponentSupplies(customComponentSupplies);
        strandRepository.saveAndFlush(strand);
        Long customComponentSuppliesId = customComponentSupplies.getId();

        // Get all the strandList where customComponentSupplies equals to customComponentSuppliesId
        defaultStrandShouldBeFound("customComponentSuppliesId.equals=" + customComponentSuppliesId);

        // Get all the strandList where customComponentSupplies equals to (customComponentSuppliesId + 1)
        defaultStrandShouldNotBeFound("customComponentSuppliesId.equals=" + (customComponentSuppliesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStrandShouldBeFound(String filter) throws Exception {
        restStrandMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(strand.getId().intValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)));

        // Check, that the count call also returns 1
        restStrandMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStrandShouldNotBeFound(String filter) throws Exception {
        restStrandMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStrandMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingStrand() throws Exception {
        // Get the strand
        restStrandMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStrand() throws Exception {
        // Initialize the database
        strandRepository.saveAndFlush(strand);

        int databaseSizeBeforeUpdate = strandRepository.findAll().size();

        // Update the strand
        Strand updatedStrand = strandRepository.findById(strand.getId()).get();
        // Disconnect from session so that the updates on updatedStrand are not directly saved in db
        em.detach(updatedStrand);
        updatedStrand.designation(UPDATED_DESIGNATION);

        restStrandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStrand.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedStrand))
            )
            .andExpect(status().isOk());

        // Validate the Strand in the database
        List<Strand> strandList = strandRepository.findAll();
        assertThat(strandList).hasSize(databaseSizeBeforeUpdate);
        Strand testStrand = strandList.get(strandList.size() - 1);
        assertThat(testStrand.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void putNonExistingStrand() throws Exception {
        int databaseSizeBeforeUpdate = strandRepository.findAll().size();
        strand.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStrandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, strand.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(strand))
            )
            .andExpect(status().isBadRequest());

        // Validate the Strand in the database
        List<Strand> strandList = strandRepository.findAll();
        assertThat(strandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStrand() throws Exception {
        int databaseSizeBeforeUpdate = strandRepository.findAll().size();
        strand.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(strand))
            )
            .andExpect(status().isBadRequest());

        // Validate the Strand in the database
        List<Strand> strandList = strandRepository.findAll();
        assertThat(strandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStrand() throws Exception {
        int databaseSizeBeforeUpdate = strandRepository.findAll().size();
        strand.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrandMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strand)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Strand in the database
        List<Strand> strandList = strandRepository.findAll();
        assertThat(strandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStrandWithPatch() throws Exception {
        // Initialize the database
        strandRepository.saveAndFlush(strand);

        int databaseSizeBeforeUpdate = strandRepository.findAll().size();

        // Update the strand using partial update
        Strand partialUpdatedStrand = new Strand();
        partialUpdatedStrand.setId(strand.getId());

        partialUpdatedStrand.designation(UPDATED_DESIGNATION);

        restStrandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStrand.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStrand))
            )
            .andExpect(status().isOk());

        // Validate the Strand in the database
        List<Strand> strandList = strandRepository.findAll();
        assertThat(strandList).hasSize(databaseSizeBeforeUpdate);
        Strand testStrand = strandList.get(strandList.size() - 1);
        assertThat(testStrand.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void fullUpdateStrandWithPatch() throws Exception {
        // Initialize the database
        strandRepository.saveAndFlush(strand);

        int databaseSizeBeforeUpdate = strandRepository.findAll().size();

        // Update the strand using partial update
        Strand partialUpdatedStrand = new Strand();
        partialUpdatedStrand.setId(strand.getId());

        partialUpdatedStrand.designation(UPDATED_DESIGNATION);

        restStrandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStrand.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStrand))
            )
            .andExpect(status().isOk());

        // Validate the Strand in the database
        List<Strand> strandList = strandRepository.findAll();
        assertThat(strandList).hasSize(databaseSizeBeforeUpdate);
        Strand testStrand = strandList.get(strandList.size() - 1);
        assertThat(testStrand.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void patchNonExistingStrand() throws Exception {
        int databaseSizeBeforeUpdate = strandRepository.findAll().size();
        strand.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStrandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, strand.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(strand))
            )
            .andExpect(status().isBadRequest());

        // Validate the Strand in the database
        List<Strand> strandList = strandRepository.findAll();
        assertThat(strandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStrand() throws Exception {
        int databaseSizeBeforeUpdate = strandRepository.findAll().size();
        strand.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(strand))
            )
            .andExpect(status().isBadRequest());

        // Validate the Strand in the database
        List<Strand> strandList = strandRepository.findAll();
        assertThat(strandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStrand() throws Exception {
        int databaseSizeBeforeUpdate = strandRepository.findAll().size();
        strand.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrandMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(strand)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Strand in the database
        List<Strand> strandList = strandRepository.findAll();
        assertThat(strandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStrand() throws Exception {
        // Initialize the database
        strandRepository.saveAndFlush(strand);

        int databaseSizeBeforeDelete = strandRepository.findAll().size();

        // Delete the strand
        restStrandMockMvc
            .perform(delete(ENTITY_API_URL_ID, strand.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Strand> strandList = strandRepository.findAll();
        assertThat(strandList).hasSize(databaseSizeBeforeDelete - 1);
    }
}