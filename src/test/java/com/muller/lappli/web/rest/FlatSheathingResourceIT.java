package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.FlatSheathing;
import com.muller.lappli.domain.Material;
import com.muller.lappli.domain.StrandSupply;
import com.muller.lappli.repository.FlatSheathingRepository;
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
 * Integration tests for the {@link FlatSheathingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FlatSheathingResourceIT {

    private static final Long DEFAULT_OPERATION_LAYER = 1L;
    private static final Long UPDATED_OPERATION_LAYER = 2L;

    private static final Double DEFAULT_MILIMETER_WIDTH = 1D;
    private static final Double UPDATED_MILIMETER_WIDTH = 2D;

    private static final Double DEFAULT_MILIMETER_HEIGHT = 1D;
    private static final Double UPDATED_MILIMETER_HEIGHT = 2D;

    private static final String ENTITY_API_URL = "/api/flat-sheathings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FlatSheathingRepository flatSheathingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFlatSheathingMockMvc;

    private FlatSheathing flatSheathing;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FlatSheathing createEntity(EntityManager em) {
        FlatSheathing flatSheathing = new FlatSheathing()
            .operationLayer(DEFAULT_OPERATION_LAYER)
            .milimeterWidth(DEFAULT_MILIMETER_WIDTH)
            .milimeterHeight(DEFAULT_MILIMETER_HEIGHT);
        // Add required entity
        Material material;
        if (TestUtil.findAll(em, Material.class).isEmpty()) {
            material = MaterialResourceIT.createEntity(em);
            em.persist(material);
            em.flush();
        } else {
            material = TestUtil.findAll(em, Material.class).get(0);
        }
        flatSheathing.setMaterial(material);
        // Add required entity
        StrandSupply strandSupply;
        if (TestUtil.findAll(em, StrandSupply.class).isEmpty()) {
            strandSupply = StrandSupplyResourceIT.createEntity(em);
            em.persist(strandSupply);
            em.flush();
        } else {
            strandSupply = TestUtil.findAll(em, StrandSupply.class).get(0);
        }
        flatSheathing.setOwnerStrandSupply(strandSupply);
        return flatSheathing;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FlatSheathing createUpdatedEntity(EntityManager em) {
        FlatSheathing flatSheathing = new FlatSheathing()
            .operationLayer(UPDATED_OPERATION_LAYER)
            .milimeterWidth(UPDATED_MILIMETER_WIDTH)
            .milimeterHeight(UPDATED_MILIMETER_HEIGHT);
        // Add required entity
        Material material;
        if (TestUtil.findAll(em, Material.class).isEmpty()) {
            material = MaterialResourceIT.createUpdatedEntity(em);
            em.persist(material);
            em.flush();
        } else {
            material = TestUtil.findAll(em, Material.class).get(0);
        }
        flatSheathing.setMaterial(material);
        // Add required entity
        StrandSupply strandSupply;
        if (TestUtil.findAll(em, StrandSupply.class).isEmpty()) {
            strandSupply = StrandSupplyResourceIT.createUpdatedEntity(em);
            em.persist(strandSupply);
            em.flush();
        } else {
            strandSupply = TestUtil.findAll(em, StrandSupply.class).get(0);
        }
        flatSheathing.setOwnerStrandSupply(strandSupply);
        return flatSheathing;
    }

    @BeforeEach
    public void initTest() {
        flatSheathing = createEntity(em);
    }

    @Test
    @Transactional
    void createFlatSheathing() throws Exception {
        int databaseSizeBeforeCreate = flatSheathingRepository.findAll().size();
        // Create the FlatSheathing
        restFlatSheathingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(flatSheathing)))
            .andExpect(status().isCreated());

        // Validate the FlatSheathing in the database
        List<FlatSheathing> flatSheathingList = flatSheathingRepository.findAll();
        assertThat(flatSheathingList).hasSize(databaseSizeBeforeCreate + 1);
        FlatSheathing testFlatSheathing = flatSheathingList.get(flatSheathingList.size() - 1);
        assertThat(testFlatSheathing.getOperationLayer()).isEqualTo(DEFAULT_OPERATION_LAYER);
        assertThat(testFlatSheathing.getMilimeterWidth()).isEqualTo(DEFAULT_MILIMETER_WIDTH);
        assertThat(testFlatSheathing.getMilimeterHeight()).isEqualTo(DEFAULT_MILIMETER_HEIGHT);
    }

    @Test
    @Transactional
    void createFlatSheathingWithExistingId() throws Exception {
        // Create the FlatSheathing with an existing ID
        flatSheathing.setId(1L);

        int databaseSizeBeforeCreate = flatSheathingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFlatSheathingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(flatSheathing)))
            .andExpect(status().isBadRequest());

        // Validate the FlatSheathing in the database
        List<FlatSheathing> flatSheathingList = flatSheathingRepository.findAll();
        assertThat(flatSheathingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkOperationLayerIsRequired() throws Exception {
        int databaseSizeBeforeTest = flatSheathingRepository.findAll().size();
        // set the field null
        flatSheathing.setOperationLayer(null);

        // Create the FlatSheathing, which fails.

        restFlatSheathingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(flatSheathing)))
            .andExpect(status().isBadRequest());

        List<FlatSheathing> flatSheathingList = flatSheathingRepository.findAll();
        assertThat(flatSheathingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMilimeterWidthIsRequired() throws Exception {
        int databaseSizeBeforeTest = flatSheathingRepository.findAll().size();
        // set the field null
        flatSheathing.setMilimeterWidth(null);

        // Create the FlatSheathing, which fails.

        restFlatSheathingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(flatSheathing)))
            .andExpect(status().isBadRequest());

        List<FlatSheathing> flatSheathingList = flatSheathingRepository.findAll();
        assertThat(flatSheathingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMilimeterHeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = flatSheathingRepository.findAll().size();
        // set the field null
        flatSheathing.setMilimeterHeight(null);

        // Create the FlatSheathing, which fails.

        restFlatSheathingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(flatSheathing)))
            .andExpect(status().isBadRequest());

        List<FlatSheathing> flatSheathingList = flatSheathingRepository.findAll();
        assertThat(flatSheathingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFlatSheathings() throws Exception {
        // Initialize the database
        flatSheathingRepository.saveAndFlush(flatSheathing);

        // Get all the flatSheathingList
        restFlatSheathingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(flatSheathing.getId().intValue())))
            .andExpect(jsonPath("$.[*].operationLayer").value(hasItem(DEFAULT_OPERATION_LAYER.intValue())))
            .andExpect(jsonPath("$.[*].milimeterWidth").value(hasItem(DEFAULT_MILIMETER_WIDTH.doubleValue())))
            .andExpect(jsonPath("$.[*].milimeterHeight").value(hasItem(DEFAULT_MILIMETER_HEIGHT.doubleValue())));
    }

    @Test
    @Transactional
    void getFlatSheathing() throws Exception {
        // Initialize the database
        flatSheathingRepository.saveAndFlush(flatSheathing);

        // Get the flatSheathing
        restFlatSheathingMockMvc
            .perform(get(ENTITY_API_URL_ID, flatSheathing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(flatSheathing.getId().intValue()))
            .andExpect(jsonPath("$.operationLayer").value(DEFAULT_OPERATION_LAYER.intValue()))
            .andExpect(jsonPath("$.milimeterWidth").value(DEFAULT_MILIMETER_WIDTH.doubleValue()))
            .andExpect(jsonPath("$.milimeterHeight").value(DEFAULT_MILIMETER_HEIGHT.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingFlatSheathing() throws Exception {
        // Get the flatSheathing
        restFlatSheathingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFlatSheathing() throws Exception {
        // Initialize the database
        flatSheathingRepository.saveAndFlush(flatSheathing);

        int databaseSizeBeforeUpdate = flatSheathingRepository.findAll().size();

        // Update the flatSheathing
        FlatSheathing updatedFlatSheathing = flatSheathingRepository.findById(flatSheathing.getId()).get();
        // Disconnect from session so that the updates on updatedFlatSheathing are not directly saved in db
        em.detach(updatedFlatSheathing);
        updatedFlatSheathing
            .operationLayer(UPDATED_OPERATION_LAYER)
            .milimeterWidth(UPDATED_MILIMETER_WIDTH)
            .milimeterHeight(UPDATED_MILIMETER_HEIGHT);

        restFlatSheathingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFlatSheathing.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFlatSheathing))
            )
            .andExpect(status().isOk());

        // Validate the FlatSheathing in the database
        List<FlatSheathing> flatSheathingList = flatSheathingRepository.findAll();
        assertThat(flatSheathingList).hasSize(databaseSizeBeforeUpdate);
        FlatSheathing testFlatSheathing = flatSheathingList.get(flatSheathingList.size() - 1);
        assertThat(testFlatSheathing.getOperationLayer()).isEqualTo(DEFAULT_OPERATION_LAYER);
        assertThat(testFlatSheathing.getMilimeterWidth()).isEqualTo(UPDATED_MILIMETER_WIDTH);
        assertThat(testFlatSheathing.getMilimeterHeight()).isEqualTo(UPDATED_MILIMETER_HEIGHT);
    }

    @Test
    @Transactional
    void putNonExistingFlatSheathing() throws Exception {
        int databaseSizeBeforeUpdate = flatSheathingRepository.findAll().size();
        flatSheathing.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFlatSheathingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, flatSheathing.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(flatSheathing))
            )
            .andExpect(status().isBadRequest());

        // Validate the FlatSheathing in the database
        List<FlatSheathing> flatSheathingList = flatSheathingRepository.findAll();
        assertThat(flatSheathingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFlatSheathing() throws Exception {
        int databaseSizeBeforeUpdate = flatSheathingRepository.findAll().size();
        flatSheathing.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFlatSheathingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(flatSheathing))
            )
            .andExpect(status().isBadRequest());

        // Validate the FlatSheathing in the database
        List<FlatSheathing> flatSheathingList = flatSheathingRepository.findAll();
        assertThat(flatSheathingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFlatSheathing() throws Exception {
        int databaseSizeBeforeUpdate = flatSheathingRepository.findAll().size();
        flatSheathing.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFlatSheathingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(flatSheathing)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FlatSheathing in the database
        List<FlatSheathing> flatSheathingList = flatSheathingRepository.findAll();
        assertThat(flatSheathingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFlatSheathingWithPatch() throws Exception {
        // Initialize the database
        flatSheathingRepository.saveAndFlush(flatSheathing);

        int databaseSizeBeforeUpdate = flatSheathingRepository.findAll().size();

        // Update the flatSheathing using partial update
        FlatSheathing partialUpdatedFlatSheathing = new FlatSheathing();
        partialUpdatedFlatSheathing.setId(flatSheathing.getId());

        partialUpdatedFlatSheathing.operationLayer(UPDATED_OPERATION_LAYER);

        restFlatSheathingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFlatSheathing.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFlatSheathing))
            )
            .andExpect(status().isOk());

        // Validate the FlatSheathing in the database
        List<FlatSheathing> flatSheathingList = flatSheathingRepository.findAll();
        assertThat(flatSheathingList).hasSize(databaseSizeBeforeUpdate);
        FlatSheathing testFlatSheathing = flatSheathingList.get(flatSheathingList.size() - 1);
        assertThat(testFlatSheathing.getOperationLayer()).isEqualTo(UPDATED_OPERATION_LAYER);
        assertThat(testFlatSheathing.getMilimeterWidth()).isEqualTo(DEFAULT_MILIMETER_WIDTH);
        assertThat(testFlatSheathing.getMilimeterHeight()).isEqualTo(DEFAULT_MILIMETER_HEIGHT);
    }

    @Test
    @Transactional
    void fullUpdateFlatSheathingWithPatch() throws Exception {
        // Initialize the database
        flatSheathingRepository.saveAndFlush(flatSheathing);

        int databaseSizeBeforeUpdate = flatSheathingRepository.findAll().size();

        // Update the flatSheathing using partial update
        FlatSheathing partialUpdatedFlatSheathing = new FlatSheathing();
        partialUpdatedFlatSheathing.setId(flatSheathing.getId());

        partialUpdatedFlatSheathing
            .operationLayer(UPDATED_OPERATION_LAYER)
            .milimeterWidth(UPDATED_MILIMETER_WIDTH)
            .milimeterHeight(UPDATED_MILIMETER_HEIGHT);

        restFlatSheathingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFlatSheathing.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFlatSheathing))
            )
            .andExpect(status().isOk());

        // Validate the FlatSheathing in the database
        List<FlatSheathing> flatSheathingList = flatSheathingRepository.findAll();
        assertThat(flatSheathingList).hasSize(databaseSizeBeforeUpdate);
        FlatSheathing testFlatSheathing = flatSheathingList.get(flatSheathingList.size() - 1);
        assertThat(testFlatSheathing.getOperationLayer()).isEqualTo(DEFAULT_OPERATION_LAYER);
        assertThat(testFlatSheathing.getMilimeterWidth()).isEqualTo(UPDATED_MILIMETER_WIDTH);
        assertThat(testFlatSheathing.getMilimeterHeight()).isEqualTo(UPDATED_MILIMETER_HEIGHT);
    }

    @Test
    @Transactional
    void patchNonExistingFlatSheathing() throws Exception {
        int databaseSizeBeforeUpdate = flatSheathingRepository.findAll().size();
        flatSheathing.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFlatSheathingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, flatSheathing.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(flatSheathing))
            )
            .andExpect(status().isBadRequest());

        // Validate the FlatSheathing in the database
        List<FlatSheathing> flatSheathingList = flatSheathingRepository.findAll();
        assertThat(flatSheathingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFlatSheathing() throws Exception {
        int databaseSizeBeforeUpdate = flatSheathingRepository.findAll().size();
        flatSheathing.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFlatSheathingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(flatSheathing))
            )
            .andExpect(status().isBadRequest());

        // Validate the FlatSheathing in the database
        List<FlatSheathing> flatSheathingList = flatSheathingRepository.findAll();
        assertThat(flatSheathingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFlatSheathing() throws Exception {
        int databaseSizeBeforeUpdate = flatSheathingRepository.findAll().size();
        flatSheathing.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFlatSheathingMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(flatSheathing))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FlatSheathing in the database
        List<FlatSheathing> flatSheathingList = flatSheathingRepository.findAll();
        assertThat(flatSheathingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFlatSheathing() throws Exception {
        // Initialize the database
        flatSheathingRepository.saveAndFlush(flatSheathing);

        int databaseSizeBeforeDelete = flatSheathingRepository.findAll().size();

        // Delete the flatSheathing
        restFlatSheathingMockMvc
            .perform(delete(ENTITY_API_URL_ID, flatSheathing.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FlatSheathing> flatSheathingList = flatSheathingRepository.findAll();
        assertThat(flatSheathingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
