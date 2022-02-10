package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.SupplyPosition;
import com.muller.lappli.repository.SupplyPositionRepository;
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
 * Integration tests for the {@link SupplyPositionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SupplyPositionResourceIT {

    private static final Long DEFAULT_SUPPLY_APPARITIONS_USAGE = 1L;
    private static final Long UPDATED_SUPPLY_APPARITIONS_USAGE = 2L;

    private static final String ENTITY_API_URL = "/api/supply-positions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SupplyPositionRepository supplyPositionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSupplyPositionMockMvc;

    private SupplyPosition supplyPosition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SupplyPosition createEntity(EntityManager em) {
        SupplyPosition supplyPosition = new SupplyPosition().supplyApparitionsUsage(DEFAULT_SUPPLY_APPARITIONS_USAGE);
        return supplyPosition;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SupplyPosition createUpdatedEntity(EntityManager em) {
        SupplyPosition supplyPosition = new SupplyPosition().supplyApparitionsUsage(UPDATED_SUPPLY_APPARITIONS_USAGE);
        return supplyPosition;
    }

    @BeforeEach
    public void initTest() {
        supplyPosition = createEntity(em);
    }

    @Test
    @Transactional
    void createSupplyPosition() throws Exception {
        int databaseSizeBeforeCreate = supplyPositionRepository.findAll().size();
        // Create the SupplyPosition
        restSupplyPositionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(supplyPosition))
            )
            .andExpect(status().isCreated());

        // Validate the SupplyPosition in the database
        List<SupplyPosition> supplyPositionList = supplyPositionRepository.findAll();
        assertThat(supplyPositionList).hasSize(databaseSizeBeforeCreate + 1);
        SupplyPosition testSupplyPosition = supplyPositionList.get(supplyPositionList.size() - 1);
        assertThat(testSupplyPosition.getSupplyApparitionsUsage()).isEqualTo(DEFAULT_SUPPLY_APPARITIONS_USAGE);
    }

    @Test
    @Transactional
    void createSupplyPositionWithExistingId() throws Exception {
        // Create the SupplyPosition with an existing ID
        supplyPosition.setId(1L);

        int databaseSizeBeforeCreate = supplyPositionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupplyPositionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(supplyPosition))
            )
            .andExpect(status().isBadRequest());

        // Validate the SupplyPosition in the database
        List<SupplyPosition> supplyPositionList = supplyPositionRepository.findAll();
        assertThat(supplyPositionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSupplyApparitionsUsageIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplyPositionRepository.findAll().size();
        // set the field null
        supplyPosition.setSupplyApparitionsUsage(null);

        // Create the SupplyPosition, which fails.

        restSupplyPositionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(supplyPosition))
            )
            .andExpect(status().isBadRequest());

        List<SupplyPosition> supplyPositionList = supplyPositionRepository.findAll();
        assertThat(supplyPositionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSupplyPositions() throws Exception {
        // Initialize the database
        supplyPositionRepository.saveAndFlush(supplyPosition);

        // Get all the supplyPositionList
        restSupplyPositionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplyPosition.getId().intValue())))
            .andExpect(jsonPath("$.[*].supplyApparitionsUsage").value(hasItem(DEFAULT_SUPPLY_APPARITIONS_USAGE.intValue())));
    }

    @Test
    @Transactional
    void getSupplyPosition() throws Exception {
        // Initialize the database
        supplyPositionRepository.saveAndFlush(supplyPosition);

        // Get the supplyPosition
        restSupplyPositionMockMvc
            .perform(get(ENTITY_API_URL_ID, supplyPosition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(supplyPosition.getId().intValue()))
            .andExpect(jsonPath("$.supplyApparitionsUsage").value(DEFAULT_SUPPLY_APPARITIONS_USAGE.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingSupplyPosition() throws Exception {
        // Get the supplyPosition
        restSupplyPositionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSupplyPosition() throws Exception {
        // Initialize the database
        supplyPositionRepository.saveAndFlush(supplyPosition);

        int databaseSizeBeforeUpdate = supplyPositionRepository.findAll().size();

        // Update the supplyPosition
        SupplyPosition updatedSupplyPosition = supplyPositionRepository.findById(supplyPosition.getId()).get();
        // Disconnect from session so that the updates on updatedSupplyPosition are not directly saved in db
        em.detach(updatedSupplyPosition);
        updatedSupplyPosition.supplyApparitionsUsage(UPDATED_SUPPLY_APPARITIONS_USAGE);

        restSupplyPositionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSupplyPosition.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSupplyPosition))
            )
            .andExpect(status().isOk());

        // Validate the SupplyPosition in the database
        List<SupplyPosition> supplyPositionList = supplyPositionRepository.findAll();
        assertThat(supplyPositionList).hasSize(databaseSizeBeforeUpdate);
        SupplyPosition testSupplyPosition = supplyPositionList.get(supplyPositionList.size() - 1);
        assertThat(testSupplyPosition.getSupplyApparitionsUsage()).isEqualTo(UPDATED_SUPPLY_APPARITIONS_USAGE);
    }

    @Test
    @Transactional
    void putNonExistingSupplyPosition() throws Exception {
        int databaseSizeBeforeUpdate = supplyPositionRepository.findAll().size();
        supplyPosition.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplyPositionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, supplyPosition.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supplyPosition))
            )
            .andExpect(status().isBadRequest());

        // Validate the SupplyPosition in the database
        List<SupplyPosition> supplyPositionList = supplyPositionRepository.findAll();
        assertThat(supplyPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSupplyPosition() throws Exception {
        int databaseSizeBeforeUpdate = supplyPositionRepository.findAll().size();
        supplyPosition.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplyPositionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supplyPosition))
            )
            .andExpect(status().isBadRequest());

        // Validate the SupplyPosition in the database
        List<SupplyPosition> supplyPositionList = supplyPositionRepository.findAll();
        assertThat(supplyPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSupplyPosition() throws Exception {
        int databaseSizeBeforeUpdate = supplyPositionRepository.findAll().size();
        supplyPosition.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplyPositionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(supplyPosition)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SupplyPosition in the database
        List<SupplyPosition> supplyPositionList = supplyPositionRepository.findAll();
        assertThat(supplyPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSupplyPositionWithPatch() throws Exception {
        // Initialize the database
        supplyPositionRepository.saveAndFlush(supplyPosition);

        int databaseSizeBeforeUpdate = supplyPositionRepository.findAll().size();

        // Update the supplyPosition using partial update
        SupplyPosition partialUpdatedSupplyPosition = new SupplyPosition();
        partialUpdatedSupplyPosition.setId(supplyPosition.getId());

        partialUpdatedSupplyPosition.supplyApparitionsUsage(UPDATED_SUPPLY_APPARITIONS_USAGE);

        restSupplyPositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSupplyPosition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSupplyPosition))
            )
            .andExpect(status().isOk());

        // Validate the SupplyPosition in the database
        List<SupplyPosition> supplyPositionList = supplyPositionRepository.findAll();
        assertThat(supplyPositionList).hasSize(databaseSizeBeforeUpdate);
        SupplyPosition testSupplyPosition = supplyPositionList.get(supplyPositionList.size() - 1);
        assertThat(testSupplyPosition.getSupplyApparitionsUsage()).isEqualTo(UPDATED_SUPPLY_APPARITIONS_USAGE);
    }

    @Test
    @Transactional
    void fullUpdateSupplyPositionWithPatch() throws Exception {
        // Initialize the database
        supplyPositionRepository.saveAndFlush(supplyPosition);

        int databaseSizeBeforeUpdate = supplyPositionRepository.findAll().size();

        // Update the supplyPosition using partial update
        SupplyPosition partialUpdatedSupplyPosition = new SupplyPosition();
        partialUpdatedSupplyPosition.setId(supplyPosition.getId());

        partialUpdatedSupplyPosition.supplyApparitionsUsage(UPDATED_SUPPLY_APPARITIONS_USAGE);

        restSupplyPositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSupplyPosition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSupplyPosition))
            )
            .andExpect(status().isOk());

        // Validate the SupplyPosition in the database
        List<SupplyPosition> supplyPositionList = supplyPositionRepository.findAll();
        assertThat(supplyPositionList).hasSize(databaseSizeBeforeUpdate);
        SupplyPosition testSupplyPosition = supplyPositionList.get(supplyPositionList.size() - 1);
        assertThat(testSupplyPosition.getSupplyApparitionsUsage()).isEqualTo(UPDATED_SUPPLY_APPARITIONS_USAGE);
    }

    @Test
    @Transactional
    void patchNonExistingSupplyPosition() throws Exception {
        int databaseSizeBeforeUpdate = supplyPositionRepository.findAll().size();
        supplyPosition.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplyPositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, supplyPosition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(supplyPosition))
            )
            .andExpect(status().isBadRequest());

        // Validate the SupplyPosition in the database
        List<SupplyPosition> supplyPositionList = supplyPositionRepository.findAll();
        assertThat(supplyPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSupplyPosition() throws Exception {
        int databaseSizeBeforeUpdate = supplyPositionRepository.findAll().size();
        supplyPosition.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplyPositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(supplyPosition))
            )
            .andExpect(status().isBadRequest());

        // Validate the SupplyPosition in the database
        List<SupplyPosition> supplyPositionList = supplyPositionRepository.findAll();
        assertThat(supplyPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSupplyPosition() throws Exception {
        int databaseSizeBeforeUpdate = supplyPositionRepository.findAll().size();
        supplyPosition.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplyPositionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(supplyPosition))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SupplyPosition in the database
        List<SupplyPosition> supplyPositionList = supplyPositionRepository.findAll();
        assertThat(supplyPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSupplyPosition() throws Exception {
        // Initialize the database
        supplyPositionRepository.saveAndFlush(supplyPosition);

        int databaseSizeBeforeDelete = supplyPositionRepository.findAll().size();

        // Delete the supplyPosition
        restSupplyPositionMockMvc
            .perform(delete(ENTITY_API_URL_ID, supplyPosition.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SupplyPosition> supplyPositionList = supplyPositionRepository.findAll();
        assertThat(supplyPositionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
