package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.Strip;
import com.muller.lappli.repository.StripRepository;
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
 * Integration tests for the {@link StripResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StripResourceIT {

    private static final Long DEFAULT_NUMBER = 1L;
    private static final Long UPDATED_NUMBER = 2L;

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final Double DEFAULT_MILIMETER_THICKNESS = 1D;
    private static final Double UPDATED_MILIMETER_THICKNESS = 2D;

    private static final String ENTITY_API_URL = "/api/strips";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StripRepository stripRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStripMockMvc;

    private Strip strip;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Strip createEntity(EntityManager em) {
        Strip strip = new Strip().number(DEFAULT_NUMBER).designation(DEFAULT_DESIGNATION).milimeterThickness(DEFAULT_MILIMETER_THICKNESS);
        return strip;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Strip createUpdatedEntity(EntityManager em) {
        Strip strip = new Strip().number(UPDATED_NUMBER).designation(UPDATED_DESIGNATION).milimeterThickness(UPDATED_MILIMETER_THICKNESS);
        return strip;
    }

    @BeforeEach
    public void initTest() {
        strip = createEntity(em);
    }

    @Test
    @Transactional
    void createStrip() throws Exception {
        int databaseSizeBeforeCreate = stripRepository.findAll().size();
        // Create the Strip
        restStripMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strip)))
            .andExpect(status().isCreated());

        // Validate the Strip in the database
        List<Strip> stripList = stripRepository.findAll();
        assertThat(stripList).hasSize(databaseSizeBeforeCreate + 1);
        Strip testStrip = stripList.get(stripList.size() - 1);
        assertThat(testStrip.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testStrip.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testStrip.getMilimeterThickness()).isEqualTo(DEFAULT_MILIMETER_THICKNESS);
    }

    @Test
    @Transactional
    void createStripWithExistingId() throws Exception {
        // Create the Strip with an existing ID
        strip.setId(1L);

        int databaseSizeBeforeCreate = stripRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStripMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strip)))
            .andExpect(status().isBadRequest());

        // Validate the Strip in the database
        List<Strip> stripList = stripRepository.findAll();
        assertThat(stripList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = stripRepository.findAll().size();
        // set the field null
        strip.setNumber(null);

        // Create the Strip, which fails.

        restStripMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strip)))
            .andExpect(status().isBadRequest());

        List<Strip> stripList = stripRepository.findAll();
        assertThat(stripList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = stripRepository.findAll().size();
        // set the field null
        strip.setDesignation(null);

        // Create the Strip, which fails.

        restStripMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strip)))
            .andExpect(status().isBadRequest());

        List<Strip> stripList = stripRepository.findAll();
        assertThat(stripList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMilimeterThicknessIsRequired() throws Exception {
        int databaseSizeBeforeTest = stripRepository.findAll().size();
        // set the field null
        strip.setMilimeterThickness(null);

        // Create the Strip, which fails.

        restStripMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strip)))
            .andExpect(status().isBadRequest());

        List<Strip> stripList = stripRepository.findAll();
        assertThat(stripList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStrips() throws Exception {
        // Initialize the database
        stripRepository.saveAndFlush(strip);

        // Get all the stripList
        restStripMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(strip.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].milimeterThickness").value(hasItem(DEFAULT_MILIMETER_THICKNESS.doubleValue())));
    }

    @Test
    @Transactional
    void getStrip() throws Exception {
        // Initialize the database
        stripRepository.saveAndFlush(strip);

        // Get the strip
        restStripMockMvc
            .perform(get(ENTITY_API_URL_ID, strip.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(strip.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.intValue()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.milimeterThickness").value(DEFAULT_MILIMETER_THICKNESS.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingStrip() throws Exception {
        // Get the strip
        restStripMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStrip() throws Exception {
        // Initialize the database
        stripRepository.saveAndFlush(strip);

        int databaseSizeBeforeUpdate = stripRepository.findAll().size();

        // Update the strip
        Strip updatedStrip = stripRepository.findById(strip.getId()).get();
        // Disconnect from session so that the updates on updatedStrip are not directly saved in db
        em.detach(updatedStrip);
        updatedStrip.number(UPDATED_NUMBER).designation(UPDATED_DESIGNATION).milimeterThickness(UPDATED_MILIMETER_THICKNESS);

        restStripMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStrip.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedStrip))
            )
            .andExpect(status().isOk());

        // Validate the Strip in the database
        List<Strip> stripList = stripRepository.findAll();
        assertThat(stripList).hasSize(databaseSizeBeforeUpdate);
        Strip testStrip = stripList.get(stripList.size() - 1);
        assertThat(testStrip.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testStrip.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testStrip.getMilimeterThickness()).isEqualTo(UPDATED_MILIMETER_THICKNESS);
    }

    @Test
    @Transactional
    void putNonExistingStrip() throws Exception {
        int databaseSizeBeforeUpdate = stripRepository.findAll().size();
        strip.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStripMockMvc
            .perform(
                put(ENTITY_API_URL_ID, strip.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(strip))
            )
            .andExpect(status().isBadRequest());

        // Validate the Strip in the database
        List<Strip> stripList = stripRepository.findAll();
        assertThat(stripList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStrip() throws Exception {
        int databaseSizeBeforeUpdate = stripRepository.findAll().size();
        strip.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStripMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(strip))
            )
            .andExpect(status().isBadRequest());

        // Validate the Strip in the database
        List<Strip> stripList = stripRepository.findAll();
        assertThat(stripList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStrip() throws Exception {
        int databaseSizeBeforeUpdate = stripRepository.findAll().size();
        strip.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStripMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strip)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Strip in the database
        List<Strip> stripList = stripRepository.findAll();
        assertThat(stripList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStripWithPatch() throws Exception {
        // Initialize the database
        stripRepository.saveAndFlush(strip);

        int databaseSizeBeforeUpdate = stripRepository.findAll().size();

        // Update the strip using partial update
        Strip partialUpdatedStrip = new Strip();
        partialUpdatedStrip.setId(strip.getId());

        partialUpdatedStrip.designation(UPDATED_DESIGNATION);

        restStripMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStrip.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStrip))
            )
            .andExpect(status().isOk());

        // Validate the Strip in the database
        List<Strip> stripList = stripRepository.findAll();
        assertThat(stripList).hasSize(databaseSizeBeforeUpdate);
        Strip testStrip = stripList.get(stripList.size() - 1);
        assertThat(testStrip.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testStrip.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testStrip.getMilimeterThickness()).isEqualTo(DEFAULT_MILIMETER_THICKNESS);
    }

    @Test
    @Transactional
    void fullUpdateStripWithPatch() throws Exception {
        // Initialize the database
        stripRepository.saveAndFlush(strip);

        int databaseSizeBeforeUpdate = stripRepository.findAll().size();

        // Update the strip using partial update
        Strip partialUpdatedStrip = new Strip();
        partialUpdatedStrip.setId(strip.getId());

        partialUpdatedStrip.number(UPDATED_NUMBER).designation(UPDATED_DESIGNATION).milimeterThickness(UPDATED_MILIMETER_THICKNESS);

        restStripMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStrip.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStrip))
            )
            .andExpect(status().isOk());

        // Validate the Strip in the database
        List<Strip> stripList = stripRepository.findAll();
        assertThat(stripList).hasSize(databaseSizeBeforeUpdate);
        Strip testStrip = stripList.get(stripList.size() - 1);
        assertThat(testStrip.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testStrip.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testStrip.getMilimeterThickness()).isEqualTo(UPDATED_MILIMETER_THICKNESS);
    }

    @Test
    @Transactional
    void patchNonExistingStrip() throws Exception {
        int databaseSizeBeforeUpdate = stripRepository.findAll().size();
        strip.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStripMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, strip.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(strip))
            )
            .andExpect(status().isBadRequest());

        // Validate the Strip in the database
        List<Strip> stripList = stripRepository.findAll();
        assertThat(stripList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStrip() throws Exception {
        int databaseSizeBeforeUpdate = stripRepository.findAll().size();
        strip.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStripMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(strip))
            )
            .andExpect(status().isBadRequest());

        // Validate the Strip in the database
        List<Strip> stripList = stripRepository.findAll();
        assertThat(stripList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStrip() throws Exception {
        int databaseSizeBeforeUpdate = stripRepository.findAll().size();
        strip.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStripMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(strip)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Strip in the database
        List<Strip> stripList = stripRepository.findAll();
        assertThat(stripList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStrip() throws Exception {
        // Initialize the database
        stripRepository.saveAndFlush(strip);

        int databaseSizeBeforeDelete = stripRepository.findAll().size();

        // Delete the strip
        restStripMockMvc
            .perform(delete(ENTITY_API_URL_ID, strip.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Strip> stripList = stripRepository.findAll();
        assertThat(stripList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
