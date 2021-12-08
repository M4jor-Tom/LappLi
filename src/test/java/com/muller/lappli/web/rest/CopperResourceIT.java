package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.Copper;
import com.muller.lappli.repository.CopperRepository;
import com.muller.lappli.service.criteria.CopperCriteria;
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
 * Integration tests for the {@link CopperResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CopperResourceIT {

    private static final Long DEFAULT_NUMBER = 1L;
    private static final Long UPDATED_NUMBER = 2L;
    private static final Long SMALLER_NUMBER = 1L - 1L;

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/coppers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CopperRepository copperRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCopperMockMvc;

    private Copper copper;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Copper createEntity(EntityManager em) {
        Copper copper = new Copper().number(DEFAULT_NUMBER).designation(DEFAULT_DESIGNATION);
        return copper;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Copper createUpdatedEntity(EntityManager em) {
        Copper copper = new Copper().number(UPDATED_NUMBER).designation(UPDATED_DESIGNATION);
        return copper;
    }

    @BeforeEach
    public void initTest() {
        copper = createEntity(em);
    }

    @Test
    @Transactional
    void createCopper() throws Exception {
        int databaseSizeBeforeCreate = copperRepository.findAll().size();
        // Create the Copper
        restCopperMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(copper)))
            .andExpect(status().isCreated());

        // Validate the Copper in the database
        List<Copper> copperList = copperRepository.findAll();
        assertThat(copperList).hasSize(databaseSizeBeforeCreate + 1);
        Copper testCopper = copperList.get(copperList.size() - 1);
        assertThat(testCopper.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testCopper.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
    }

    @Test
    @Transactional
    void createCopperWithExistingId() throws Exception {
        // Create the Copper with an existing ID
        copper.setId(1L);

        int databaseSizeBeforeCreate = copperRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCopperMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(copper)))
            .andExpect(status().isBadRequest());

        // Validate the Copper in the database
        List<Copper> copperList = copperRepository.findAll();
        assertThat(copperList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = copperRepository.findAll().size();
        // set the field null
        copper.setNumber(null);

        // Create the Copper, which fails.

        restCopperMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(copper)))
            .andExpect(status().isBadRequest());

        List<Copper> copperList = copperRepository.findAll();
        assertThat(copperList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = copperRepository.findAll().size();
        // set the field null
        copper.setDesignation(null);

        // Create the Copper, which fails.

        restCopperMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(copper)))
            .andExpect(status().isBadRequest());

        List<Copper> copperList = copperRepository.findAll();
        assertThat(copperList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCoppers() throws Exception {
        // Initialize the database
        copperRepository.saveAndFlush(copper);

        // Get all the copperList
        restCopperMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(copper.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)));
    }

    @Test
    @Transactional
    void getCopper() throws Exception {
        // Initialize the database
        copperRepository.saveAndFlush(copper);

        // Get the copper
        restCopperMockMvc
            .perform(get(ENTITY_API_URL_ID, copper.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(copper.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.intValue()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION));
    }

    @Test
    @Transactional
    void getCoppersByIdFiltering() throws Exception {
        // Initialize the database
        copperRepository.saveAndFlush(copper);

        Long id = copper.getId();

        defaultCopperShouldBeFound("id.equals=" + id);
        defaultCopperShouldNotBeFound("id.notEquals=" + id);

        defaultCopperShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCopperShouldNotBeFound("id.greaterThan=" + id);

        defaultCopperShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCopperShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCoppersByNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        copperRepository.saveAndFlush(copper);

        // Get all the copperList where number equals to DEFAULT_NUMBER
        defaultCopperShouldBeFound("number.equals=" + DEFAULT_NUMBER);

        // Get all the copperList where number equals to UPDATED_NUMBER
        defaultCopperShouldNotBeFound("number.equals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllCoppersByNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        copperRepository.saveAndFlush(copper);

        // Get all the copperList where number not equals to DEFAULT_NUMBER
        defaultCopperShouldNotBeFound("number.notEquals=" + DEFAULT_NUMBER);

        // Get all the copperList where number not equals to UPDATED_NUMBER
        defaultCopperShouldBeFound("number.notEquals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllCoppersByNumberIsInShouldWork() throws Exception {
        // Initialize the database
        copperRepository.saveAndFlush(copper);

        // Get all the copperList where number in DEFAULT_NUMBER or UPDATED_NUMBER
        defaultCopperShouldBeFound("number.in=" + DEFAULT_NUMBER + "," + UPDATED_NUMBER);

        // Get all the copperList where number equals to UPDATED_NUMBER
        defaultCopperShouldNotBeFound("number.in=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllCoppersByNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        copperRepository.saveAndFlush(copper);

        // Get all the copperList where number is not null
        defaultCopperShouldBeFound("number.specified=true");

        // Get all the copperList where number is null
        defaultCopperShouldNotBeFound("number.specified=false");
    }

    @Test
    @Transactional
    void getAllCoppersByNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        copperRepository.saveAndFlush(copper);

        // Get all the copperList where number is greater than or equal to DEFAULT_NUMBER
        defaultCopperShouldBeFound("number.greaterThanOrEqual=" + DEFAULT_NUMBER);

        // Get all the copperList where number is greater than or equal to UPDATED_NUMBER
        defaultCopperShouldNotBeFound("number.greaterThanOrEqual=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllCoppersByNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        copperRepository.saveAndFlush(copper);

        // Get all the copperList where number is less than or equal to DEFAULT_NUMBER
        defaultCopperShouldBeFound("number.lessThanOrEqual=" + DEFAULT_NUMBER);

        // Get all the copperList where number is less than or equal to SMALLER_NUMBER
        defaultCopperShouldNotBeFound("number.lessThanOrEqual=" + SMALLER_NUMBER);
    }

    @Test
    @Transactional
    void getAllCoppersByNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        copperRepository.saveAndFlush(copper);

        // Get all the copperList where number is less than DEFAULT_NUMBER
        defaultCopperShouldNotBeFound("number.lessThan=" + DEFAULT_NUMBER);

        // Get all the copperList where number is less than UPDATED_NUMBER
        defaultCopperShouldBeFound("number.lessThan=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllCoppersByNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        copperRepository.saveAndFlush(copper);

        // Get all the copperList where number is greater than DEFAULT_NUMBER
        defaultCopperShouldNotBeFound("number.greaterThan=" + DEFAULT_NUMBER);

        // Get all the copperList where number is greater than SMALLER_NUMBER
        defaultCopperShouldBeFound("number.greaterThan=" + SMALLER_NUMBER);
    }

    @Test
    @Transactional
    void getAllCoppersByDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        copperRepository.saveAndFlush(copper);

        // Get all the copperList where designation equals to DEFAULT_DESIGNATION
        defaultCopperShouldBeFound("designation.equals=" + DEFAULT_DESIGNATION);

        // Get all the copperList where designation equals to UPDATED_DESIGNATION
        defaultCopperShouldNotBeFound("designation.equals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllCoppersByDesignationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        copperRepository.saveAndFlush(copper);

        // Get all the copperList where designation not equals to DEFAULT_DESIGNATION
        defaultCopperShouldNotBeFound("designation.notEquals=" + DEFAULT_DESIGNATION);

        // Get all the copperList where designation not equals to UPDATED_DESIGNATION
        defaultCopperShouldBeFound("designation.notEquals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllCoppersByDesignationIsInShouldWork() throws Exception {
        // Initialize the database
        copperRepository.saveAndFlush(copper);

        // Get all the copperList where designation in DEFAULT_DESIGNATION or UPDATED_DESIGNATION
        defaultCopperShouldBeFound("designation.in=" + DEFAULT_DESIGNATION + "," + UPDATED_DESIGNATION);

        // Get all the copperList where designation equals to UPDATED_DESIGNATION
        defaultCopperShouldNotBeFound("designation.in=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllCoppersByDesignationIsNullOrNotNull() throws Exception {
        // Initialize the database
        copperRepository.saveAndFlush(copper);

        // Get all the copperList where designation is not null
        defaultCopperShouldBeFound("designation.specified=true");

        // Get all the copperList where designation is null
        defaultCopperShouldNotBeFound("designation.specified=false");
    }

    @Test
    @Transactional
    void getAllCoppersByDesignationContainsSomething() throws Exception {
        // Initialize the database
        copperRepository.saveAndFlush(copper);

        // Get all the copperList where designation contains DEFAULT_DESIGNATION
        defaultCopperShouldBeFound("designation.contains=" + DEFAULT_DESIGNATION);

        // Get all the copperList where designation contains UPDATED_DESIGNATION
        defaultCopperShouldNotBeFound("designation.contains=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllCoppersByDesignationNotContainsSomething() throws Exception {
        // Initialize the database
        copperRepository.saveAndFlush(copper);

        // Get all the copperList where designation does not contain DEFAULT_DESIGNATION
        defaultCopperShouldNotBeFound("designation.doesNotContain=" + DEFAULT_DESIGNATION);

        // Get all the copperList where designation does not contain UPDATED_DESIGNATION
        defaultCopperShouldBeFound("designation.doesNotContain=" + UPDATED_DESIGNATION);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCopperShouldBeFound(String filter) throws Exception {
        restCopperMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(copper.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)));

        // Check, that the count call also returns 1
        restCopperMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCopperShouldNotBeFound(String filter) throws Exception {
        restCopperMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCopperMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCopper() throws Exception {
        // Get the copper
        restCopperMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCopper() throws Exception {
        // Initialize the database
        copperRepository.saveAndFlush(copper);

        int databaseSizeBeforeUpdate = copperRepository.findAll().size();

        // Update the copper
        Copper updatedCopper = copperRepository.findById(copper.getId()).get();
        // Disconnect from session so that the updates on updatedCopper are not directly saved in db
        em.detach(updatedCopper);
        updatedCopper.number(UPDATED_NUMBER).designation(UPDATED_DESIGNATION);

        restCopperMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCopper.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCopper))
            )
            .andExpect(status().isOk());

        // Validate the Copper in the database
        List<Copper> copperList = copperRepository.findAll();
        assertThat(copperList).hasSize(databaseSizeBeforeUpdate);
        Copper testCopper = copperList.get(copperList.size() - 1);
        assertThat(testCopper.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testCopper.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void putNonExistingCopper() throws Exception {
        int databaseSizeBeforeUpdate = copperRepository.findAll().size();
        copper.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCopperMockMvc
            .perform(
                put(ENTITY_API_URL_ID, copper.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(copper))
            )
            .andExpect(status().isBadRequest());

        // Validate the Copper in the database
        List<Copper> copperList = copperRepository.findAll();
        assertThat(copperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCopper() throws Exception {
        int databaseSizeBeforeUpdate = copperRepository.findAll().size();
        copper.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCopperMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(copper))
            )
            .andExpect(status().isBadRequest());

        // Validate the Copper in the database
        List<Copper> copperList = copperRepository.findAll();
        assertThat(copperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCopper() throws Exception {
        int databaseSizeBeforeUpdate = copperRepository.findAll().size();
        copper.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCopperMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(copper)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Copper in the database
        List<Copper> copperList = copperRepository.findAll();
        assertThat(copperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCopperWithPatch() throws Exception {
        // Initialize the database
        copperRepository.saveAndFlush(copper);

        int databaseSizeBeforeUpdate = copperRepository.findAll().size();

        // Update the copper using partial update
        Copper partialUpdatedCopper = new Copper();
        partialUpdatedCopper.setId(copper.getId());

        restCopperMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCopper.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCopper))
            )
            .andExpect(status().isOk());

        // Validate the Copper in the database
        List<Copper> copperList = copperRepository.findAll();
        assertThat(copperList).hasSize(databaseSizeBeforeUpdate);
        Copper testCopper = copperList.get(copperList.size() - 1);
        assertThat(testCopper.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testCopper.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
    }

    @Test
    @Transactional
    void fullUpdateCopperWithPatch() throws Exception {
        // Initialize the database
        copperRepository.saveAndFlush(copper);

        int databaseSizeBeforeUpdate = copperRepository.findAll().size();

        // Update the copper using partial update
        Copper partialUpdatedCopper = new Copper();
        partialUpdatedCopper.setId(copper.getId());

        partialUpdatedCopper.number(UPDATED_NUMBER).designation(UPDATED_DESIGNATION);

        restCopperMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCopper.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCopper))
            )
            .andExpect(status().isOk());

        // Validate the Copper in the database
        List<Copper> copperList = copperRepository.findAll();
        assertThat(copperList).hasSize(databaseSizeBeforeUpdate);
        Copper testCopper = copperList.get(copperList.size() - 1);
        assertThat(testCopper.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testCopper.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void patchNonExistingCopper() throws Exception {
        int databaseSizeBeforeUpdate = copperRepository.findAll().size();
        copper.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCopperMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, copper.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(copper))
            )
            .andExpect(status().isBadRequest());

        // Validate the Copper in the database
        List<Copper> copperList = copperRepository.findAll();
        assertThat(copperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCopper() throws Exception {
        int databaseSizeBeforeUpdate = copperRepository.findAll().size();
        copper.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCopperMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(copper))
            )
            .andExpect(status().isBadRequest());

        // Validate the Copper in the database
        List<Copper> copperList = copperRepository.findAll();
        assertThat(copperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCopper() throws Exception {
        int databaseSizeBeforeUpdate = copperRepository.findAll().size();
        copper.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCopperMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(copper)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Copper in the database
        List<Copper> copperList = copperRepository.findAll();
        assertThat(copperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCopper() throws Exception {
        // Initialize the database
        copperRepository.saveAndFlush(copper);

        int databaseSizeBeforeDelete = copperRepository.findAll().size();

        // Delete the copper
        restCopperMockMvc
            .perform(delete(ENTITY_API_URL_ID, copper.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Copper> copperList = copperRepository.findAll();
        assertThat(copperList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
