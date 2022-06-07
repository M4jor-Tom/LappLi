package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.Plaiter;
import com.muller.lappli.repository.PlaiterRepository;
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
 * Integration tests for the {@link PlaiterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlaiterResourceIT {

    private static final Long DEFAULT_INDEX = 0L;
    private static final Long UPDATED_INDEX = 1L;

    private static final Long DEFAULT_TOTAL_BOBINS_COUNT = 0L;
    private static final Long UPDATED_TOTAL_BOBINS_COUNT = 1L;

    private static final String ENTITY_API_URL = "/api/plaiters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlaiterRepository plaiterRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlaiterMockMvc;

    private Plaiter plaiter;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plaiter createEntity(EntityManager em) {
        Plaiter plaiter = new Plaiter().index(DEFAULT_INDEX).totalBobinsCount(DEFAULT_TOTAL_BOBINS_COUNT);
        return plaiter;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plaiter createUpdatedEntity(EntityManager em) {
        Plaiter plaiter = new Plaiter().index(UPDATED_INDEX).totalBobinsCount(UPDATED_TOTAL_BOBINS_COUNT);
        return plaiter;
    }

    @BeforeEach
    public void initTest() {
        plaiter = createEntity(em);
    }

    @Test
    @Transactional
    void createPlaiter() throws Exception {
        int databaseSizeBeforeCreate = plaiterRepository.findAll().size();
        // Create the Plaiter
        restPlaiterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plaiter)))
            .andExpect(status().isCreated());

        // Validate the Plaiter in the database
        List<Plaiter> plaiterList = plaiterRepository.findAll();
        assertThat(plaiterList).hasSize(databaseSizeBeforeCreate + 1);
        Plaiter testPlaiter = plaiterList.get(plaiterList.size() - 1);
        assertThat(testPlaiter.getIndex()).isEqualTo(DEFAULT_INDEX);
        assertThat(testPlaiter.getTotalBobinsCount()).isEqualTo(DEFAULT_TOTAL_BOBINS_COUNT);
    }

    @Test
    @Transactional
    void createPlaiterWithExistingId() throws Exception {
        // Create the Plaiter with an existing ID
        plaiter.setId(1L);

        int databaseSizeBeforeCreate = plaiterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlaiterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plaiter)))
            .andExpect(status().isBadRequest());

        // Validate the Plaiter in the database
        List<Plaiter> plaiterList = plaiterRepository.findAll();
        assertThat(plaiterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIndexIsRequired() throws Exception {
        int databaseSizeBeforeTest = plaiterRepository.findAll().size();
        // set the field null
        plaiter.setIndex(null);

        // Create the Plaiter, which fails.

        restPlaiterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plaiter)))
            .andExpect(status().isBadRequest());

        List<Plaiter> plaiterList = plaiterRepository.findAll();
        assertThat(plaiterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalBobinsCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = plaiterRepository.findAll().size();
        // set the field null
        plaiter.setTotalBobinsCount(null);

        // Create the Plaiter, which fails.

        restPlaiterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plaiter)))
            .andExpect(status().isBadRequest());

        List<Plaiter> plaiterList = plaiterRepository.findAll();
        assertThat(plaiterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPlaiters() throws Exception {
        // Initialize the database
        plaiterRepository.saveAndFlush(plaiter);

        // Get all the plaiterList
        restPlaiterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plaiter.getId().intValue())))
            .andExpect(jsonPath("$.[*].index").value(hasItem(DEFAULT_INDEX.intValue())))
            .andExpect(jsonPath("$.[*].totalBobinsCount").value(hasItem(DEFAULT_TOTAL_BOBINS_COUNT.intValue())));
    }

    @Test
    @Transactional
    void getPlaiter() throws Exception {
        // Initialize the database
        plaiterRepository.saveAndFlush(plaiter);

        // Get the plaiter
        restPlaiterMockMvc
            .perform(get(ENTITY_API_URL_ID, plaiter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plaiter.getId().intValue()))
            .andExpect(jsonPath("$.index").value(DEFAULT_INDEX.intValue()))
            .andExpect(jsonPath("$.totalBobinsCount").value(DEFAULT_TOTAL_BOBINS_COUNT.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingPlaiter() throws Exception {
        // Get the plaiter
        restPlaiterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPlaiter() throws Exception {
        // Initialize the database
        plaiterRepository.saveAndFlush(plaiter);

        int databaseSizeBeforeUpdate = plaiterRepository.findAll().size();

        // Update the plaiter
        Plaiter updatedPlaiter = plaiterRepository.findById(plaiter.getId()).get();
        // Disconnect from session so that the updates on updatedPlaiter are not directly saved in db
        em.detach(updatedPlaiter);
        updatedPlaiter.index(UPDATED_INDEX).totalBobinsCount(UPDATED_TOTAL_BOBINS_COUNT);

        restPlaiterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPlaiter.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPlaiter))
            )
            .andExpect(status().isOk());

        // Validate the Plaiter in the database
        List<Plaiter> plaiterList = plaiterRepository.findAll();
        assertThat(plaiterList).hasSize(databaseSizeBeforeUpdate);
        Plaiter testPlaiter = plaiterList.get(plaiterList.size() - 1);
        assertThat(testPlaiter.getIndex()).isEqualTo(UPDATED_INDEX);
        assertThat(testPlaiter.getTotalBobinsCount()).isEqualTo(UPDATED_TOTAL_BOBINS_COUNT);
    }

    @Test
    @Transactional
    void putNonExistingPlaiter() throws Exception {
        int databaseSizeBeforeUpdate = plaiterRepository.findAll().size();
        plaiter.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlaiterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, plaiter.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plaiter))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plaiter in the database
        List<Plaiter> plaiterList = plaiterRepository.findAll();
        assertThat(plaiterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlaiter() throws Exception {
        int databaseSizeBeforeUpdate = plaiterRepository.findAll().size();
        plaiter.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaiterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plaiter))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plaiter in the database
        List<Plaiter> plaiterList = plaiterRepository.findAll();
        assertThat(plaiterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlaiter() throws Exception {
        int databaseSizeBeforeUpdate = plaiterRepository.findAll().size();
        plaiter.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaiterMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plaiter)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Plaiter in the database
        List<Plaiter> plaiterList = plaiterRepository.findAll();
        assertThat(plaiterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlaiterWithPatch() throws Exception {
        // Initialize the database
        plaiterRepository.saveAndFlush(plaiter);

        int databaseSizeBeforeUpdate = plaiterRepository.findAll().size();

        // Update the plaiter using partial update
        Plaiter partialUpdatedPlaiter = new Plaiter();
        partialUpdatedPlaiter.setId(plaiter.getId());

        partialUpdatedPlaiter.index(UPDATED_INDEX);

        restPlaiterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlaiter.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlaiter))
            )
            .andExpect(status().isOk());

        // Validate the Plaiter in the database
        List<Plaiter> plaiterList = plaiterRepository.findAll();
        assertThat(plaiterList).hasSize(databaseSizeBeforeUpdate);
        Plaiter testPlaiter = plaiterList.get(plaiterList.size() - 1);
        assertThat(testPlaiter.getIndex()).isEqualTo(UPDATED_INDEX);
        assertThat(testPlaiter.getTotalBobinsCount()).isEqualTo(DEFAULT_TOTAL_BOBINS_COUNT);
    }

    @Test
    @Transactional
    void fullUpdatePlaiterWithPatch() throws Exception {
        // Initialize the database
        plaiterRepository.saveAndFlush(plaiter);

        int databaseSizeBeforeUpdate = plaiterRepository.findAll().size();

        // Update the plaiter using partial update
        Plaiter partialUpdatedPlaiter = new Plaiter();
        partialUpdatedPlaiter.setId(plaiter.getId());

        partialUpdatedPlaiter.index(UPDATED_INDEX).totalBobinsCount(UPDATED_TOTAL_BOBINS_COUNT);

        restPlaiterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlaiter.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlaiter))
            )
            .andExpect(status().isOk());

        // Validate the Plaiter in the database
        List<Plaiter> plaiterList = plaiterRepository.findAll();
        assertThat(plaiterList).hasSize(databaseSizeBeforeUpdate);
        Plaiter testPlaiter = plaiterList.get(plaiterList.size() - 1);
        assertThat(testPlaiter.getIndex()).isEqualTo(UPDATED_INDEX);
        assertThat(testPlaiter.getTotalBobinsCount()).isEqualTo(UPDATED_TOTAL_BOBINS_COUNT);
    }

    @Test
    @Transactional
    void patchNonExistingPlaiter() throws Exception {
        int databaseSizeBeforeUpdate = plaiterRepository.findAll().size();
        plaiter.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlaiterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, plaiter.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plaiter))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plaiter in the database
        List<Plaiter> plaiterList = plaiterRepository.findAll();
        assertThat(plaiterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlaiter() throws Exception {
        int databaseSizeBeforeUpdate = plaiterRepository.findAll().size();
        plaiter.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaiterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plaiter))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plaiter in the database
        List<Plaiter> plaiterList = plaiterRepository.findAll();
        assertThat(plaiterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlaiter() throws Exception {
        int databaseSizeBeforeUpdate = plaiterRepository.findAll().size();
        plaiter.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaiterMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(plaiter)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Plaiter in the database
        List<Plaiter> plaiterList = plaiterRepository.findAll();
        assertThat(plaiterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlaiter() throws Exception {
        // Initialize the database
        plaiterRepository.saveAndFlush(plaiter);

        int databaseSizeBeforeDelete = plaiterRepository.findAll().size();

        // Delete the plaiter
        restPlaiterMockMvc
            .perform(delete(ENTITY_API_URL_ID, plaiter.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Plaiter> plaiterList = plaiterRepository.findAll();
        assertThat(plaiterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
