package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.CopperFiber;
import com.muller.lappli.repository.CopperFiberRepository;
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
 * Integration tests for the {@link CopperFiberResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CopperFiberResourceIT {

    private static final Long DEFAULT_NUMBER = 1L;
    private static final Long UPDATED_NUMBER = 2L;

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_COPPER_IS_RED_NOT_TINNED = false;
    private static final Boolean UPDATED_COPPER_IS_RED_NOT_TINNED = true;

    private static final Double DEFAULT_MILIMETER_DIAMETER = 1D;
    private static final Double UPDATED_MILIMETER_DIAMETER = 2D;

    private static final String ENTITY_API_URL = "/api/copper-fibers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CopperFiberRepository copperFiberRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCopperFiberMockMvc;

    private CopperFiber copperFiber;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CopperFiber createEntity(EntityManager em) {
        CopperFiber copperFiber = new CopperFiber()
            .number(DEFAULT_NUMBER)
            .designation(DEFAULT_DESIGNATION)
            .copperIsRedNotTinned(DEFAULT_COPPER_IS_RED_NOT_TINNED)
            .milimeterDiameter(DEFAULT_MILIMETER_DIAMETER);
        return copperFiber;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CopperFiber createUpdatedEntity(EntityManager em) {
        CopperFiber copperFiber = new CopperFiber()
            .number(UPDATED_NUMBER)
            .designation(UPDATED_DESIGNATION)
            .copperIsRedNotTinned(UPDATED_COPPER_IS_RED_NOT_TINNED)
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER);
        return copperFiber;
    }

    @BeforeEach
    public void initTest() {
        copperFiber = createEntity(em);
    }

    @Test
    @Transactional
    void createCopperFiber() throws Exception {
        int databaseSizeBeforeCreate = copperFiberRepository.findAll().size();
        // Create the CopperFiber
        restCopperFiberMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(copperFiber)))
            .andExpect(status().isCreated());

        // Validate the CopperFiber in the database
        List<CopperFiber> copperFiberList = copperFiberRepository.findAll();
        assertThat(copperFiberList).hasSize(databaseSizeBeforeCreate + 1);
        CopperFiber testCopperFiber = copperFiberList.get(copperFiberList.size() - 1);
        assertThat(testCopperFiber.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testCopperFiber.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testCopperFiber.getCopperIsRedNotTinned()).isEqualTo(DEFAULT_COPPER_IS_RED_NOT_TINNED);
        assertThat(testCopperFiber.getMilimeterDiameter()).isEqualTo(DEFAULT_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void createCopperFiberWithExistingId() throws Exception {
        // Create the CopperFiber with an existing ID
        copperFiber.setId(1L);

        int databaseSizeBeforeCreate = copperFiberRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCopperFiberMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(copperFiber)))
            .andExpect(status().isBadRequest());

        // Validate the CopperFiber in the database
        List<CopperFiber> copperFiberList = copperFiberRepository.findAll();
        assertThat(copperFiberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCopperIsRedNotTinnedIsRequired() throws Exception {
        int databaseSizeBeforeTest = copperFiberRepository.findAll().size();
        // set the field null
        copperFiber.setCopperIsRedNotTinned(null);

        // Create the CopperFiber, which fails.

        restCopperFiberMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(copperFiber)))
            .andExpect(status().isBadRequest());

        List<CopperFiber> copperFiberList = copperFiberRepository.findAll();
        assertThat(copperFiberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMilimeterDiameterIsRequired() throws Exception {
        int databaseSizeBeforeTest = copperFiberRepository.findAll().size();
        // set the field null
        copperFiber.setMilimeterDiameter(null);

        // Create the CopperFiber, which fails.

        restCopperFiberMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(copperFiber)))
            .andExpect(status().isBadRequest());

        List<CopperFiber> copperFiberList = copperFiberRepository.findAll();
        assertThat(copperFiberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCopperFibers() throws Exception {
        // Initialize the database
        copperFiberRepository.saveAndFlush(copperFiber);

        // Get all the copperFiberList
        restCopperFiberMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(copperFiber.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].copperIsRedNotTinned").value(hasItem(DEFAULT_COPPER_IS_RED_NOT_TINNED.booleanValue())))
            .andExpect(jsonPath("$.[*].milimeterDiameter").value(hasItem(DEFAULT_MILIMETER_DIAMETER.doubleValue())));
    }

    @Test
    @Transactional
    void getCopperFiber() throws Exception {
        // Initialize the database
        copperFiberRepository.saveAndFlush(copperFiber);

        // Get the copperFiber
        restCopperFiberMockMvc
            .perform(get(ENTITY_API_URL_ID, copperFiber.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(copperFiber.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.intValue()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.copperIsRedNotTinned").value(DEFAULT_COPPER_IS_RED_NOT_TINNED.booleanValue()))
            .andExpect(jsonPath("$.milimeterDiameter").value(DEFAULT_MILIMETER_DIAMETER.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingCopperFiber() throws Exception {
        // Get the copperFiber
        restCopperFiberMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCopperFiber() throws Exception {
        // Initialize the database
        copperFiberRepository.saveAndFlush(copperFiber);

        int databaseSizeBeforeUpdate = copperFiberRepository.findAll().size();

        // Update the copperFiber
        CopperFiber updatedCopperFiber = copperFiberRepository.findById(copperFiber.getId()).get();
        // Disconnect from session so that the updates on updatedCopperFiber are not directly saved in db
        em.detach(updatedCopperFiber);
        updatedCopperFiber
            .number(UPDATED_NUMBER)
            .designation(UPDATED_DESIGNATION)
            .copperIsRedNotTinned(UPDATED_COPPER_IS_RED_NOT_TINNED)
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER);

        restCopperFiberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCopperFiber.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCopperFiber))
            )
            .andExpect(status().isOk());

        // Validate the CopperFiber in the database
        List<CopperFiber> copperFiberList = copperFiberRepository.findAll();
        assertThat(copperFiberList).hasSize(databaseSizeBeforeUpdate);
        CopperFiber testCopperFiber = copperFiberList.get(copperFiberList.size() - 1);
        assertThat(testCopperFiber.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testCopperFiber.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testCopperFiber.getCopperIsRedNotTinned()).isEqualTo(UPDATED_COPPER_IS_RED_NOT_TINNED);
        assertThat(testCopperFiber.getMilimeterDiameter()).isEqualTo(UPDATED_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void putNonExistingCopperFiber() throws Exception {
        int databaseSizeBeforeUpdate = copperFiberRepository.findAll().size();
        copperFiber.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCopperFiberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, copperFiber.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(copperFiber))
            )
            .andExpect(status().isBadRequest());

        // Validate the CopperFiber in the database
        List<CopperFiber> copperFiberList = copperFiberRepository.findAll();
        assertThat(copperFiberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCopperFiber() throws Exception {
        int databaseSizeBeforeUpdate = copperFiberRepository.findAll().size();
        copperFiber.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCopperFiberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(copperFiber))
            )
            .andExpect(status().isBadRequest());

        // Validate the CopperFiber in the database
        List<CopperFiber> copperFiberList = copperFiberRepository.findAll();
        assertThat(copperFiberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCopperFiber() throws Exception {
        int databaseSizeBeforeUpdate = copperFiberRepository.findAll().size();
        copperFiber.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCopperFiberMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(copperFiber)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CopperFiber in the database
        List<CopperFiber> copperFiberList = copperFiberRepository.findAll();
        assertThat(copperFiberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCopperFiberWithPatch() throws Exception {
        // Initialize the database
        copperFiberRepository.saveAndFlush(copperFiber);

        int databaseSizeBeforeUpdate = copperFiberRepository.findAll().size();

        // Update the copperFiber using partial update
        CopperFiber partialUpdatedCopperFiber = new CopperFiber();
        partialUpdatedCopperFiber.setId(copperFiber.getId());

        partialUpdatedCopperFiber.number(UPDATED_NUMBER);

        restCopperFiberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCopperFiber.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCopperFiber))
            )
            .andExpect(status().isOk());

        // Validate the CopperFiber in the database
        List<CopperFiber> copperFiberList = copperFiberRepository.findAll();
        assertThat(copperFiberList).hasSize(databaseSizeBeforeUpdate);
        CopperFiber testCopperFiber = copperFiberList.get(copperFiberList.size() - 1);
        assertThat(testCopperFiber.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testCopperFiber.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testCopperFiber.getCopperIsRedNotTinned()).isEqualTo(DEFAULT_COPPER_IS_RED_NOT_TINNED);
        assertThat(testCopperFiber.getMilimeterDiameter()).isEqualTo(DEFAULT_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void fullUpdateCopperFiberWithPatch() throws Exception {
        // Initialize the database
        copperFiberRepository.saveAndFlush(copperFiber);

        int databaseSizeBeforeUpdate = copperFiberRepository.findAll().size();

        // Update the copperFiber using partial update
        CopperFiber partialUpdatedCopperFiber = new CopperFiber();
        partialUpdatedCopperFiber.setId(copperFiber.getId());

        partialUpdatedCopperFiber
            .number(UPDATED_NUMBER)
            .designation(UPDATED_DESIGNATION)
            .copperIsRedNotTinned(UPDATED_COPPER_IS_RED_NOT_TINNED)
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER);

        restCopperFiberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCopperFiber.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCopperFiber))
            )
            .andExpect(status().isOk());

        // Validate the CopperFiber in the database
        List<CopperFiber> copperFiberList = copperFiberRepository.findAll();
        assertThat(copperFiberList).hasSize(databaseSizeBeforeUpdate);
        CopperFiber testCopperFiber = copperFiberList.get(copperFiberList.size() - 1);
        assertThat(testCopperFiber.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testCopperFiber.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testCopperFiber.getCopperIsRedNotTinned()).isEqualTo(UPDATED_COPPER_IS_RED_NOT_TINNED);
        assertThat(testCopperFiber.getMilimeterDiameter()).isEqualTo(UPDATED_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void patchNonExistingCopperFiber() throws Exception {
        int databaseSizeBeforeUpdate = copperFiberRepository.findAll().size();
        copperFiber.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCopperFiberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, copperFiber.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(copperFiber))
            )
            .andExpect(status().isBadRequest());

        // Validate the CopperFiber in the database
        List<CopperFiber> copperFiberList = copperFiberRepository.findAll();
        assertThat(copperFiberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCopperFiber() throws Exception {
        int databaseSizeBeforeUpdate = copperFiberRepository.findAll().size();
        copperFiber.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCopperFiberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(copperFiber))
            )
            .andExpect(status().isBadRequest());

        // Validate the CopperFiber in the database
        List<CopperFiber> copperFiberList = copperFiberRepository.findAll();
        assertThat(copperFiberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCopperFiber() throws Exception {
        int databaseSizeBeforeUpdate = copperFiberRepository.findAll().size();
        copperFiber.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCopperFiberMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(copperFiber))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CopperFiber in the database
        List<CopperFiber> copperFiberList = copperFiberRepository.findAll();
        assertThat(copperFiberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCopperFiber() throws Exception {
        // Initialize the database
        copperFiberRepository.saveAndFlush(copperFiber);

        int databaseSizeBeforeDelete = copperFiberRepository.findAll().size();

        // Delete the copperFiber
        restCopperFiberMockMvc
            .perform(delete(ENTITY_API_URL_ID, copperFiber.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CopperFiber> copperFiberList = copperFiberRepository.findAll();
        assertThat(copperFiberList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
