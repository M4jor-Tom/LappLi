package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.FlatSheathing;
import com.muller.lappli.domain.FlatSheathingSupplyPosition;
import com.muller.lappli.domain.SupplyPosition;
import com.muller.lappli.repository.FlatSheathingSupplyPositionRepository;
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
 * Integration tests for the {@link FlatSheathingSupplyPositionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FlatSheathingSupplyPositionResourceIT {

    private static final Long DEFAULT_LOCATION_IN_OWNER_FLAT_SHEATHING = 0L;
    private static final Long UPDATED_LOCATION_IN_OWNER_FLAT_SHEATHING = 1L;

    private static final String ENTITY_API_URL = "/api/flat-sheathing-supply-positions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FlatSheathingSupplyPositionRepository flatSheathingSupplyPositionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFlatSheathingSupplyPositionMockMvc;

    private FlatSheathingSupplyPosition flatSheathingSupplyPosition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FlatSheathingSupplyPosition createEntity(EntityManager em) {
        FlatSheathingSupplyPosition flatSheathingSupplyPosition = new FlatSheathingSupplyPosition()
            .locationInOwnerFlatSheathing(DEFAULT_LOCATION_IN_OWNER_FLAT_SHEATHING);
        // Add required entity
        SupplyPosition supplyPosition;
        if (TestUtil.findAll(em, SupplyPosition.class).isEmpty()) {
            supplyPosition = SupplyPositionResourceIT.createEntity(em);
            em.persist(supplyPosition);
            em.flush();
        } else {
            supplyPosition = TestUtil.findAll(em, SupplyPosition.class).get(0);
        }
        flatSheathingSupplyPosition.setSupplyPosition(supplyPosition);
        // Add required entity
        FlatSheathing flatSheathing;
        if (TestUtil.findAll(em, FlatSheathing.class).isEmpty()) {
            flatSheathing = FlatSheathingResourceIT.createEntity(em);
            em.persist(flatSheathing);
            em.flush();
        } else {
            flatSheathing = TestUtil.findAll(em, FlatSheathing.class).get(0);
        }
        flatSheathingSupplyPosition.setOwnerFlatSheathing(flatSheathing);
        return flatSheathingSupplyPosition;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FlatSheathingSupplyPosition createUpdatedEntity(EntityManager em) {
        FlatSheathingSupplyPosition flatSheathingSupplyPosition = new FlatSheathingSupplyPosition()
            .locationInOwnerFlatSheathing(UPDATED_LOCATION_IN_OWNER_FLAT_SHEATHING);
        // Add required entity
        SupplyPosition supplyPosition;
        if (TestUtil.findAll(em, SupplyPosition.class).isEmpty()) {
            supplyPosition = SupplyPositionResourceIT.createUpdatedEntity(em);
            em.persist(supplyPosition);
            em.flush();
        } else {
            supplyPosition = TestUtil.findAll(em, SupplyPosition.class).get(0);
        }
        flatSheathingSupplyPosition.setSupplyPosition(supplyPosition);
        // Add required entity
        FlatSheathing flatSheathing;
        if (TestUtil.findAll(em, FlatSheathing.class).isEmpty()) {
            flatSheathing = FlatSheathingResourceIT.createUpdatedEntity(em);
            em.persist(flatSheathing);
            em.flush();
        } else {
            flatSheathing = TestUtil.findAll(em, FlatSheathing.class).get(0);
        }
        flatSheathingSupplyPosition.setOwnerFlatSheathing(flatSheathing);
        return flatSheathingSupplyPosition;
    }

    @BeforeEach
    public void initTest() {
        flatSheathingSupplyPosition = createEntity(em);
    }

    @Test
    @Transactional
    void createFlatSheathingSupplyPosition() throws Exception {
        int databaseSizeBeforeCreate = flatSheathingSupplyPositionRepository.findAll().size();
        // Create the FlatSheathingSupplyPosition
        restFlatSheathingSupplyPositionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(flatSheathingSupplyPosition))
            )
            .andExpect(status().isCreated());

        // Validate the FlatSheathingSupplyPosition in the database
        List<FlatSheathingSupplyPosition> flatSheathingSupplyPositionList = flatSheathingSupplyPositionRepository.findAll();
        assertThat(flatSheathingSupplyPositionList).hasSize(databaseSizeBeforeCreate + 1);
        FlatSheathingSupplyPosition testFlatSheathingSupplyPosition = flatSheathingSupplyPositionList.get(
            flatSheathingSupplyPositionList.size() - 1
        );
        assertThat(testFlatSheathingSupplyPosition.getLocationInOwnerFlatSheathing()).isEqualTo(DEFAULT_LOCATION_IN_OWNER_FLAT_SHEATHING);

        // Validate the id for MapsId, the ids must be same
        assertThat(testFlatSheathingSupplyPosition.getId()).isEqualTo(testFlatSheathingSupplyPosition.getSupplyPosition().getId());
    }

    @Test
    @Transactional
    void createFlatSheathingSupplyPositionWithExistingId() throws Exception {
        // Create the FlatSheathingSupplyPosition with an existing ID
        flatSheathingSupplyPosition.setId(1L);

        int databaseSizeBeforeCreate = flatSheathingSupplyPositionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFlatSheathingSupplyPositionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(flatSheathingSupplyPosition))
            )
            .andExpect(status().isBadRequest());

        // Validate the FlatSheathingSupplyPosition in the database
        List<FlatSheathingSupplyPosition> flatSheathingSupplyPositionList = flatSheathingSupplyPositionRepository.findAll();
        assertThat(flatSheathingSupplyPositionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void updateFlatSheathingSupplyPositionMapsIdAssociationWithNewId() throws Exception {
        // Initialize the database
        flatSheathingSupplyPositionRepository.saveAndFlush(flatSheathingSupplyPosition);
        int databaseSizeBeforeCreate = flatSheathingSupplyPositionRepository.findAll().size();

        // Add a new parent entity
        SupplyPosition supplyPosition = SupplyPositionResourceIT.createUpdatedEntity(em);
        em.persist(supplyPosition);
        em.flush();

        // Load the flatSheathingSupplyPosition
        FlatSheathingSupplyPosition updatedFlatSheathingSupplyPosition = flatSheathingSupplyPositionRepository
            .findById(flatSheathingSupplyPosition.getId())
            .get();
        assertThat(updatedFlatSheathingSupplyPosition).isNotNull();
        // Disconnect from session so that the updates on updatedFlatSheathingSupplyPosition are not directly saved in db
        em.detach(updatedFlatSheathingSupplyPosition);

        // Update the SupplyPosition with new association value
        updatedFlatSheathingSupplyPosition.setSupplyPosition(supplyPosition);

        // Update the entity
        restFlatSheathingSupplyPositionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFlatSheathingSupplyPosition.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFlatSheathingSupplyPosition))
            )
            .andExpect(status().isOk());

        // Validate the FlatSheathingSupplyPosition in the database
        List<FlatSheathingSupplyPosition> flatSheathingSupplyPositionList = flatSheathingSupplyPositionRepository.findAll();
        assertThat(flatSheathingSupplyPositionList).hasSize(databaseSizeBeforeCreate);
        FlatSheathingSupplyPosition testFlatSheathingSupplyPosition = flatSheathingSupplyPositionList.get(
            flatSheathingSupplyPositionList.size() - 1
        );
        // Validate the id for MapsId, the ids must be same
        // Uncomment the following line for assertion. However, please note that there is a known issue and uncommenting will fail the test.
        // Please look at https://github.com/jhipster/generator-jhipster/issues/9100. You can modify this test as necessary.
        // assertThat(testFlatSheathingSupplyPosition.getId()).isEqualTo(testFlatSheathingSupplyPosition.getSupplyPosition().getId());
    }

    @Test
    @Transactional
    void checkLocationInOwnerFlatSheathingIsRequired() throws Exception {
        int databaseSizeBeforeTest = flatSheathingSupplyPositionRepository.findAll().size();
        // set the field null
        flatSheathingSupplyPosition.setLocationInOwnerFlatSheathing(null);

        // Create the FlatSheathingSupplyPosition, which fails.

        restFlatSheathingSupplyPositionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(flatSheathingSupplyPosition))
            )
            .andExpect(status().isBadRequest());

        List<FlatSheathingSupplyPosition> flatSheathingSupplyPositionList = flatSheathingSupplyPositionRepository.findAll();
        assertThat(flatSheathingSupplyPositionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFlatSheathingSupplyPositions() throws Exception {
        // Initialize the database
        flatSheathingSupplyPositionRepository.saveAndFlush(flatSheathingSupplyPosition);

        // Get all the flatSheathingSupplyPositionList
        restFlatSheathingSupplyPositionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(flatSheathingSupplyPosition.getId().intValue())))
            .andExpect(jsonPath("$.[*].locationInOwnerFlatSheathing").value(hasItem(DEFAULT_LOCATION_IN_OWNER_FLAT_SHEATHING.intValue())));
    }

    @Test
    @Transactional
    void getFlatSheathingSupplyPosition() throws Exception {
        // Initialize the database
        flatSheathingSupplyPositionRepository.saveAndFlush(flatSheathingSupplyPosition);

        // Get the flatSheathingSupplyPosition
        restFlatSheathingSupplyPositionMockMvc
            .perform(get(ENTITY_API_URL_ID, flatSheathingSupplyPosition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(flatSheathingSupplyPosition.getId().intValue()))
            .andExpect(jsonPath("$.locationInOwnerFlatSheathing").value(DEFAULT_LOCATION_IN_OWNER_FLAT_SHEATHING.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingFlatSheathingSupplyPosition() throws Exception {
        // Get the flatSheathingSupplyPosition
        restFlatSheathingSupplyPositionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFlatSheathingSupplyPosition() throws Exception {
        // Initialize the database
        flatSheathingSupplyPositionRepository.saveAndFlush(flatSheathingSupplyPosition);

        int databaseSizeBeforeUpdate = flatSheathingSupplyPositionRepository.findAll().size();

        // Update the flatSheathingSupplyPosition
        FlatSheathingSupplyPosition updatedFlatSheathingSupplyPosition = flatSheathingSupplyPositionRepository
            .findById(flatSheathingSupplyPosition.getId())
            .get();
        // Disconnect from session so that the updates on updatedFlatSheathingSupplyPosition are not directly saved in db
        em.detach(updatedFlatSheathingSupplyPosition);
        updatedFlatSheathingSupplyPosition.locationInOwnerFlatSheathing(UPDATED_LOCATION_IN_OWNER_FLAT_SHEATHING);

        restFlatSheathingSupplyPositionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFlatSheathingSupplyPosition.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFlatSheathingSupplyPosition))
            )
            .andExpect(status().isOk());

        // Validate the FlatSheathingSupplyPosition in the database
        List<FlatSheathingSupplyPosition> flatSheathingSupplyPositionList = flatSheathingSupplyPositionRepository.findAll();
        assertThat(flatSheathingSupplyPositionList).hasSize(databaseSizeBeforeUpdate);
        FlatSheathingSupplyPosition testFlatSheathingSupplyPosition = flatSheathingSupplyPositionList.get(
            flatSheathingSupplyPositionList.size() - 1
        );
        assertThat(testFlatSheathingSupplyPosition.getLocationInOwnerFlatSheathing()).isEqualTo(UPDATED_LOCATION_IN_OWNER_FLAT_SHEATHING);
    }

    @Test
    @Transactional
    void putNonExistingFlatSheathingSupplyPosition() throws Exception {
        int databaseSizeBeforeUpdate = flatSheathingSupplyPositionRepository.findAll().size();
        flatSheathingSupplyPosition.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFlatSheathingSupplyPositionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, flatSheathingSupplyPosition.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(flatSheathingSupplyPosition))
            )
            .andExpect(status().isBadRequest());

        // Validate the FlatSheathingSupplyPosition in the database
        List<FlatSheathingSupplyPosition> flatSheathingSupplyPositionList = flatSheathingSupplyPositionRepository.findAll();
        assertThat(flatSheathingSupplyPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFlatSheathingSupplyPosition() throws Exception {
        int databaseSizeBeforeUpdate = flatSheathingSupplyPositionRepository.findAll().size();
        flatSheathingSupplyPosition.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFlatSheathingSupplyPositionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(flatSheathingSupplyPosition))
            )
            .andExpect(status().isBadRequest());

        // Validate the FlatSheathingSupplyPosition in the database
        List<FlatSheathingSupplyPosition> flatSheathingSupplyPositionList = flatSheathingSupplyPositionRepository.findAll();
        assertThat(flatSheathingSupplyPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFlatSheathingSupplyPosition() throws Exception {
        int databaseSizeBeforeUpdate = flatSheathingSupplyPositionRepository.findAll().size();
        flatSheathingSupplyPosition.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFlatSheathingSupplyPositionMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(flatSheathingSupplyPosition))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FlatSheathingSupplyPosition in the database
        List<FlatSheathingSupplyPosition> flatSheathingSupplyPositionList = flatSheathingSupplyPositionRepository.findAll();
        assertThat(flatSheathingSupplyPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFlatSheathingSupplyPositionWithPatch() throws Exception {
        // Initialize the database
        flatSheathingSupplyPositionRepository.saveAndFlush(flatSheathingSupplyPosition);

        int databaseSizeBeforeUpdate = flatSheathingSupplyPositionRepository.findAll().size();

        // Update the flatSheathingSupplyPosition using partial update
        FlatSheathingSupplyPosition partialUpdatedFlatSheathingSupplyPosition = new FlatSheathingSupplyPosition();
        partialUpdatedFlatSheathingSupplyPosition.setId(flatSheathingSupplyPosition.getId());

        restFlatSheathingSupplyPositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFlatSheathingSupplyPosition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFlatSheathingSupplyPosition))
            )
            .andExpect(status().isOk());

        // Validate the FlatSheathingSupplyPosition in the database
        List<FlatSheathingSupplyPosition> flatSheathingSupplyPositionList = flatSheathingSupplyPositionRepository.findAll();
        assertThat(flatSheathingSupplyPositionList).hasSize(databaseSizeBeforeUpdate);
        FlatSheathingSupplyPosition testFlatSheathingSupplyPosition = flatSheathingSupplyPositionList.get(
            flatSheathingSupplyPositionList.size() - 1
        );
        assertThat(testFlatSheathingSupplyPosition.getLocationInOwnerFlatSheathing()).isEqualTo(DEFAULT_LOCATION_IN_OWNER_FLAT_SHEATHING);
    }

    @Test
    @Transactional
    void fullUpdateFlatSheathingSupplyPositionWithPatch() throws Exception {
        // Initialize the database
        flatSheathingSupplyPositionRepository.saveAndFlush(flatSheathingSupplyPosition);

        int databaseSizeBeforeUpdate = flatSheathingSupplyPositionRepository.findAll().size();

        // Update the flatSheathingSupplyPosition using partial update
        FlatSheathingSupplyPosition partialUpdatedFlatSheathingSupplyPosition = new FlatSheathingSupplyPosition();
        partialUpdatedFlatSheathingSupplyPosition.setId(flatSheathingSupplyPosition.getId());

        partialUpdatedFlatSheathingSupplyPosition.locationInOwnerFlatSheathing(UPDATED_LOCATION_IN_OWNER_FLAT_SHEATHING);

        restFlatSheathingSupplyPositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFlatSheathingSupplyPosition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFlatSheathingSupplyPosition))
            )
            .andExpect(status().isOk());

        // Validate the FlatSheathingSupplyPosition in the database
        List<FlatSheathingSupplyPosition> flatSheathingSupplyPositionList = flatSheathingSupplyPositionRepository.findAll();
        assertThat(flatSheathingSupplyPositionList).hasSize(databaseSizeBeforeUpdate);
        FlatSheathingSupplyPosition testFlatSheathingSupplyPosition = flatSheathingSupplyPositionList.get(
            flatSheathingSupplyPositionList.size() - 1
        );
        assertThat(testFlatSheathingSupplyPosition.getLocationInOwnerFlatSheathing()).isEqualTo(UPDATED_LOCATION_IN_OWNER_FLAT_SHEATHING);
    }

    @Test
    @Transactional
    void patchNonExistingFlatSheathingSupplyPosition() throws Exception {
        int databaseSizeBeforeUpdate = flatSheathingSupplyPositionRepository.findAll().size();
        flatSheathingSupplyPosition.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFlatSheathingSupplyPositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, flatSheathingSupplyPosition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(flatSheathingSupplyPosition))
            )
            .andExpect(status().isBadRequest());

        // Validate the FlatSheathingSupplyPosition in the database
        List<FlatSheathingSupplyPosition> flatSheathingSupplyPositionList = flatSheathingSupplyPositionRepository.findAll();
        assertThat(flatSheathingSupplyPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFlatSheathingSupplyPosition() throws Exception {
        int databaseSizeBeforeUpdate = flatSheathingSupplyPositionRepository.findAll().size();
        flatSheathingSupplyPosition.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFlatSheathingSupplyPositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(flatSheathingSupplyPosition))
            )
            .andExpect(status().isBadRequest());

        // Validate the FlatSheathingSupplyPosition in the database
        List<FlatSheathingSupplyPosition> flatSheathingSupplyPositionList = flatSheathingSupplyPositionRepository.findAll();
        assertThat(flatSheathingSupplyPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFlatSheathingSupplyPosition() throws Exception {
        int databaseSizeBeforeUpdate = flatSheathingSupplyPositionRepository.findAll().size();
        flatSheathingSupplyPosition.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFlatSheathingSupplyPositionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(flatSheathingSupplyPosition))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FlatSheathingSupplyPosition in the database
        List<FlatSheathingSupplyPosition> flatSheathingSupplyPositionList = flatSheathingSupplyPositionRepository.findAll();
        assertThat(flatSheathingSupplyPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFlatSheathingSupplyPosition() throws Exception {
        // Initialize the database
        flatSheathingSupplyPositionRepository.saveAndFlush(flatSheathingSupplyPosition);

        int databaseSizeBeforeDelete = flatSheathingSupplyPositionRepository.findAll().size();

        // Delete the flatSheathingSupplyPosition
        restFlatSheathingSupplyPositionMockMvc
            .perform(delete(ENTITY_API_URL_ID, flatSheathingSupplyPosition.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FlatSheathingSupplyPosition> flatSheathingSupplyPositionList = flatSheathingSupplyPositionRepository.findAll();
        assertThat(flatSheathingSupplyPositionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
