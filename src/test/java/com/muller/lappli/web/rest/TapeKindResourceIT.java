package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.TapeKind;
import com.muller.lappli.repository.TapeKindRepository;
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
 * Integration tests for the {@link TapeKindResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TapeKindResourceIT {

    private static final Double DEFAULT_TARGET_COVERING_RATE = 1D;
    private static final Double UPDATED_TARGET_COVERING_RATE = 2D;

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tape-kinds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TapeKindRepository tapeKindRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTapeKindMockMvc;

    private TapeKind tapeKind;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TapeKind createEntity(EntityManager em) {
        TapeKind tapeKind = new TapeKind().targetCoveringRate(DEFAULT_TARGET_COVERING_RATE).designation(DEFAULT_DESIGNATION);
        return tapeKind;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TapeKind createUpdatedEntity(EntityManager em) {
        TapeKind tapeKind = new TapeKind().targetCoveringRate(UPDATED_TARGET_COVERING_RATE).designation(UPDATED_DESIGNATION);
        return tapeKind;
    }

    @BeforeEach
    public void initTest() {
        tapeKind = createEntity(em);
    }

    @Test
    @Transactional
    void createTapeKind() throws Exception {
        int databaseSizeBeforeCreate = tapeKindRepository.findAll().size();
        // Create the TapeKind
        restTapeKindMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tapeKind)))
            .andExpect(status().isCreated());

        // Validate the TapeKind in the database
        List<TapeKind> tapeKindList = tapeKindRepository.findAll();
        assertThat(tapeKindList).hasSize(databaseSizeBeforeCreate + 1);
        TapeKind testTapeKind = tapeKindList.get(tapeKindList.size() - 1);
        assertThat(testTapeKind.getTargetCoveringRate()).isEqualTo(DEFAULT_TARGET_COVERING_RATE);
        assertThat(testTapeKind.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
    }

    @Test
    @Transactional
    void createTapeKindWithExistingId() throws Exception {
        // Create the TapeKind with an existing ID
        tapeKind.setId(1L);

        int databaseSizeBeforeCreate = tapeKindRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTapeKindMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tapeKind)))
            .andExpect(status().isBadRequest());

        // Validate the TapeKind in the database
        List<TapeKind> tapeKindList = tapeKindRepository.findAll();
        assertThat(tapeKindList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTargetCoveringRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = tapeKindRepository.findAll().size();
        // set the field null
        tapeKind.setTargetCoveringRate(null);

        // Create the TapeKind, which fails.

        restTapeKindMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tapeKind)))
            .andExpect(status().isBadRequest());

        List<TapeKind> tapeKindList = tapeKindRepository.findAll();
        assertThat(tapeKindList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = tapeKindRepository.findAll().size();
        // set the field null
        tapeKind.setDesignation(null);

        // Create the TapeKind, which fails.

        restTapeKindMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tapeKind)))
            .andExpect(status().isBadRequest());

        List<TapeKind> tapeKindList = tapeKindRepository.findAll();
        assertThat(tapeKindList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTapeKinds() throws Exception {
        // Initialize the database
        tapeKindRepository.saveAndFlush(tapeKind);

        // Get all the tapeKindList
        restTapeKindMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tapeKind.getId().intValue())))
            .andExpect(jsonPath("$.[*].targetCoveringRate").value(hasItem(DEFAULT_TARGET_COVERING_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)));
    }

    @Test
    @Transactional
    void getTapeKind() throws Exception {
        // Initialize the database
        tapeKindRepository.saveAndFlush(tapeKind);

        // Get the tapeKind
        restTapeKindMockMvc
            .perform(get(ENTITY_API_URL_ID, tapeKind.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tapeKind.getId().intValue()))
            .andExpect(jsonPath("$.targetCoveringRate").value(DEFAULT_TARGET_COVERING_RATE.doubleValue()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION));
    }

    @Test
    @Transactional
    void getNonExistingTapeKind() throws Exception {
        // Get the tapeKind
        restTapeKindMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTapeKind() throws Exception {
        // Initialize the database
        tapeKindRepository.saveAndFlush(tapeKind);

        int databaseSizeBeforeUpdate = tapeKindRepository.findAll().size();

        // Update the tapeKind
        TapeKind updatedTapeKind = tapeKindRepository.findById(tapeKind.getId()).get();
        // Disconnect from session so that the updates on updatedTapeKind are not directly saved in db
        em.detach(updatedTapeKind);
        updatedTapeKind.targetCoveringRate(UPDATED_TARGET_COVERING_RATE).designation(UPDATED_DESIGNATION);

        restTapeKindMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTapeKind.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTapeKind))
            )
            .andExpect(status().isOk());

        // Validate the TapeKind in the database
        List<TapeKind> tapeKindList = tapeKindRepository.findAll();
        assertThat(tapeKindList).hasSize(databaseSizeBeforeUpdate);
        TapeKind testTapeKind = tapeKindList.get(tapeKindList.size() - 1);
        assertThat(testTapeKind.getTargetCoveringRate()).isEqualTo(UPDATED_TARGET_COVERING_RATE);
        assertThat(testTapeKind.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void putNonExistingTapeKind() throws Exception {
        int databaseSizeBeforeUpdate = tapeKindRepository.findAll().size();
        tapeKind.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTapeKindMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tapeKind.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tapeKind))
            )
            .andExpect(status().isBadRequest());

        // Validate the TapeKind in the database
        List<TapeKind> tapeKindList = tapeKindRepository.findAll();
        assertThat(tapeKindList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTapeKind() throws Exception {
        int databaseSizeBeforeUpdate = tapeKindRepository.findAll().size();
        tapeKind.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTapeKindMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tapeKind))
            )
            .andExpect(status().isBadRequest());

        // Validate the TapeKind in the database
        List<TapeKind> tapeKindList = tapeKindRepository.findAll();
        assertThat(tapeKindList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTapeKind() throws Exception {
        int databaseSizeBeforeUpdate = tapeKindRepository.findAll().size();
        tapeKind.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTapeKindMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tapeKind)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TapeKind in the database
        List<TapeKind> tapeKindList = tapeKindRepository.findAll();
        assertThat(tapeKindList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTapeKindWithPatch() throws Exception {
        // Initialize the database
        tapeKindRepository.saveAndFlush(tapeKind);

        int databaseSizeBeforeUpdate = tapeKindRepository.findAll().size();

        // Update the tapeKind using partial update
        TapeKind partialUpdatedTapeKind = new TapeKind();
        partialUpdatedTapeKind.setId(tapeKind.getId());

        partialUpdatedTapeKind.targetCoveringRate(UPDATED_TARGET_COVERING_RATE).designation(UPDATED_DESIGNATION);

        restTapeKindMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTapeKind.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTapeKind))
            )
            .andExpect(status().isOk());

        // Validate the TapeKind in the database
        List<TapeKind> tapeKindList = tapeKindRepository.findAll();
        assertThat(tapeKindList).hasSize(databaseSizeBeforeUpdate);
        TapeKind testTapeKind = tapeKindList.get(tapeKindList.size() - 1);
        assertThat(testTapeKind.getTargetCoveringRate()).isEqualTo(UPDATED_TARGET_COVERING_RATE);
        assertThat(testTapeKind.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void fullUpdateTapeKindWithPatch() throws Exception {
        // Initialize the database
        tapeKindRepository.saveAndFlush(tapeKind);

        int databaseSizeBeforeUpdate = tapeKindRepository.findAll().size();

        // Update the tapeKind using partial update
        TapeKind partialUpdatedTapeKind = new TapeKind();
        partialUpdatedTapeKind.setId(tapeKind.getId());

        partialUpdatedTapeKind.targetCoveringRate(UPDATED_TARGET_COVERING_RATE).designation(UPDATED_DESIGNATION);

        restTapeKindMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTapeKind.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTapeKind))
            )
            .andExpect(status().isOk());

        // Validate the TapeKind in the database
        List<TapeKind> tapeKindList = tapeKindRepository.findAll();
        assertThat(tapeKindList).hasSize(databaseSizeBeforeUpdate);
        TapeKind testTapeKind = tapeKindList.get(tapeKindList.size() - 1);
        assertThat(testTapeKind.getTargetCoveringRate()).isEqualTo(UPDATED_TARGET_COVERING_RATE);
        assertThat(testTapeKind.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void patchNonExistingTapeKind() throws Exception {
        int databaseSizeBeforeUpdate = tapeKindRepository.findAll().size();
        tapeKind.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTapeKindMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tapeKind.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tapeKind))
            )
            .andExpect(status().isBadRequest());

        // Validate the TapeKind in the database
        List<TapeKind> tapeKindList = tapeKindRepository.findAll();
        assertThat(tapeKindList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTapeKind() throws Exception {
        int databaseSizeBeforeUpdate = tapeKindRepository.findAll().size();
        tapeKind.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTapeKindMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tapeKind))
            )
            .andExpect(status().isBadRequest());

        // Validate the TapeKind in the database
        List<TapeKind> tapeKindList = tapeKindRepository.findAll();
        assertThat(tapeKindList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTapeKind() throws Exception {
        int databaseSizeBeforeUpdate = tapeKindRepository.findAll().size();
        tapeKind.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTapeKindMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tapeKind)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TapeKind in the database
        List<TapeKind> tapeKindList = tapeKindRepository.findAll();
        assertThat(tapeKindList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTapeKind() throws Exception {
        // Initialize the database
        tapeKindRepository.saveAndFlush(tapeKind);

        int databaseSizeBeforeDelete = tapeKindRepository.findAll().size();

        // Delete the tapeKind
        restTapeKindMockMvc
            .perform(delete(ENTITY_API_URL_ID, tapeKind.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TapeKind> tapeKindList = tapeKindRepository.findAll();
        assertThat(tapeKindList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
