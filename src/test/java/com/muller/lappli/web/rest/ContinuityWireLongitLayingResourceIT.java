package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.ContinuityWireLongitLaying;
import com.muller.lappli.domain.StrandSupply;
import com.muller.lappli.domain.enumeration.Flexibility;
import com.muller.lappli.domain.enumeration.MetalFiberKind;
import com.muller.lappli.repository.ContinuityWireLongitLayingRepository;
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
 * Integration tests for the {@link ContinuityWireLongitLayingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContinuityWireLongitLayingResourceIT {

    private static final Long DEFAULT_OPERATION_LAYER = 1L;
    private static final Long UPDATED_OPERATION_LAYER = 2L;

    private static final MetalFiberKind DEFAULT_ANONYMOUS_CONTINUITY_WIRE_METAL_FIBER_KIND = MetalFiberKind.RED_COPPER;
    private static final MetalFiberKind UPDATED_ANONYMOUS_CONTINUITY_WIRE_METAL_FIBER_KIND = MetalFiberKind.TINNED_COPPER;

    private static final Double DEFAULT_ANONYMOUS_CONTINUITY_WIRE_MILIMETER_DIAMETER = 1D;
    private static final Double UPDATED_ANONYMOUS_CONTINUITY_WIRE_MILIMETER_DIAMETER = 2D;

    private static final Flexibility DEFAULT_ANONYMOUS_CONTINUITY_WIRE_FLEXIBILITY = Flexibility.S;
    private static final Flexibility UPDATED_ANONYMOUS_CONTINUITY_WIRE_FLEXIBILITY = Flexibility.ES;

    private static final String ENTITY_API_URL = "/api/continuity-wire-longit-layings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ContinuityWireLongitLayingRepository continuityWireLongitLayingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContinuityWireLongitLayingMockMvc;

    private ContinuityWireLongitLaying continuityWireLongitLaying;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContinuityWireLongitLaying createEntity(EntityManager em) {
        ContinuityWireLongitLaying continuityWireLongitLaying = new ContinuityWireLongitLaying()
            .operationLayer(DEFAULT_OPERATION_LAYER)
            .anonymousContinuityWireMetalFiberKind(DEFAULT_ANONYMOUS_CONTINUITY_WIRE_METAL_FIBER_KIND)
            .anonymousContinuityWireMilimeterDiameter(DEFAULT_ANONYMOUS_CONTINUITY_WIRE_MILIMETER_DIAMETER)
            .anonymousContinuityWireFlexibility(DEFAULT_ANONYMOUS_CONTINUITY_WIRE_FLEXIBILITY);
        // Add required entity
        StrandSupply strandSupply;
        if (TestUtil.findAll(em, StrandSupply.class).isEmpty()) {
            strandSupply = StrandSupplyResourceIT.createEntity(em);
            em.persist(strandSupply);
            em.flush();
        } else {
            strandSupply = TestUtil.findAll(em, StrandSupply.class).get(0);
        }
        continuityWireLongitLaying.setOwnerStrandSupply(strandSupply);
        return continuityWireLongitLaying;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContinuityWireLongitLaying createUpdatedEntity(EntityManager em) {
        ContinuityWireLongitLaying continuityWireLongitLaying = new ContinuityWireLongitLaying()
            .operationLayer(UPDATED_OPERATION_LAYER)
            .anonymousContinuityWireMetalFiberKind(UPDATED_ANONYMOUS_CONTINUITY_WIRE_METAL_FIBER_KIND)
            .anonymousContinuityWireMilimeterDiameter(UPDATED_ANONYMOUS_CONTINUITY_WIRE_MILIMETER_DIAMETER)
            .anonymousContinuityWireFlexibility(UPDATED_ANONYMOUS_CONTINUITY_WIRE_FLEXIBILITY);
        // Add required entity
        StrandSupply strandSupply;
        if (TestUtil.findAll(em, StrandSupply.class).isEmpty()) {
            strandSupply = StrandSupplyResourceIT.createUpdatedEntity(em);
            em.persist(strandSupply);
            em.flush();
        } else {
            strandSupply = TestUtil.findAll(em, StrandSupply.class).get(0);
        }
        continuityWireLongitLaying.setOwnerStrandSupply(strandSupply);
        return continuityWireLongitLaying;
    }

    @BeforeEach
    public void initTest() {
        continuityWireLongitLaying = createEntity(em);
    }

    @Test
    @Transactional
    void createContinuityWireLongitLaying() throws Exception {
        int databaseSizeBeforeCreate = continuityWireLongitLayingRepository.findAll().size();
        // Create the ContinuityWireLongitLaying
        restContinuityWireLongitLayingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(continuityWireLongitLaying))
            )
            .andExpect(status().isCreated());

        // Validate the ContinuityWireLongitLaying in the database
        List<ContinuityWireLongitLaying> continuityWireLongitLayingList = continuityWireLongitLayingRepository.findAll();
        assertThat(continuityWireLongitLayingList).hasSize(databaseSizeBeforeCreate + 1);
        ContinuityWireLongitLaying testContinuityWireLongitLaying = continuityWireLongitLayingList.get(
            continuityWireLongitLayingList.size() - 1
        );
        assertThat(testContinuityWireLongitLaying.getOperationLayer()).isEqualTo(DEFAULT_OPERATION_LAYER);
        assertThat(testContinuityWireLongitLaying.getAnonymousContinuityWireMetalFiberKind())
            .isEqualTo(DEFAULT_ANONYMOUS_CONTINUITY_WIRE_METAL_FIBER_KIND);
        assertThat(testContinuityWireLongitLaying.getAnonymousContinuityWireMilimeterDiameter())
            .isEqualTo(DEFAULT_ANONYMOUS_CONTINUITY_WIRE_MILIMETER_DIAMETER);
        assertThat(testContinuityWireLongitLaying.getAnonymousContinuityWireFlexibility())
            .isEqualTo(DEFAULT_ANONYMOUS_CONTINUITY_WIRE_FLEXIBILITY);
    }

    @Test
    @Transactional
    void createContinuityWireLongitLayingWithExistingId() throws Exception {
        // Create the ContinuityWireLongitLaying with an existing ID
        continuityWireLongitLaying.setId(1L);

        int databaseSizeBeforeCreate = continuityWireLongitLayingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContinuityWireLongitLayingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(continuityWireLongitLaying))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContinuityWireLongitLaying in the database
        List<ContinuityWireLongitLaying> continuityWireLongitLayingList = continuityWireLongitLayingRepository.findAll();
        assertThat(continuityWireLongitLayingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkOperationLayerIsRequired() throws Exception {
        int databaseSizeBeforeTest = continuityWireLongitLayingRepository.findAll().size();
        // set the field null
        continuityWireLongitLaying.setOperationLayer(null);

        // Create the ContinuityWireLongitLaying, which fails.

        restContinuityWireLongitLayingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(continuityWireLongitLaying))
            )
            .andExpect(status().isBadRequest());

        List<ContinuityWireLongitLaying> continuityWireLongitLayingList = continuityWireLongitLayingRepository.findAll();
        assertThat(continuityWireLongitLayingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllContinuityWireLongitLayings() throws Exception {
        // Initialize the database
        continuityWireLongitLayingRepository.saveAndFlush(continuityWireLongitLaying);

        // Get all the continuityWireLongitLayingList
        restContinuityWireLongitLayingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(continuityWireLongitLaying.getId().intValue())))
            .andExpect(jsonPath("$.[*].operationLayer").value(hasItem(DEFAULT_OPERATION_LAYER.intValue())))
            .andExpect(
                jsonPath("$.[*].anonymousContinuityWireMetalFiberKind")
                    .value(hasItem(DEFAULT_ANONYMOUS_CONTINUITY_WIRE_METAL_FIBER_KIND.toString()))
            )
            .andExpect(
                jsonPath("$.[*].anonymousContinuityWireMilimeterDiameter")
                    .value(hasItem(DEFAULT_ANONYMOUS_CONTINUITY_WIRE_MILIMETER_DIAMETER.doubleValue()))
            )
            .andExpect(
                jsonPath("$.[*].anonymousContinuityWireFlexibility")
                    .value(hasItem(DEFAULT_ANONYMOUS_CONTINUITY_WIRE_FLEXIBILITY.toString()))
            );
    }

    @Test
    @Transactional
    void getContinuityWireLongitLaying() throws Exception {
        // Initialize the database
        continuityWireLongitLayingRepository.saveAndFlush(continuityWireLongitLaying);

        // Get the continuityWireLongitLaying
        restContinuityWireLongitLayingMockMvc
            .perform(get(ENTITY_API_URL_ID, continuityWireLongitLaying.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(continuityWireLongitLaying.getId().intValue()))
            .andExpect(jsonPath("$.operationLayer").value(DEFAULT_OPERATION_LAYER.intValue()))
            .andExpect(
                jsonPath("$.anonymousContinuityWireMetalFiberKind").value(DEFAULT_ANONYMOUS_CONTINUITY_WIRE_METAL_FIBER_KIND.toString())
            )
            .andExpect(
                jsonPath("$.anonymousContinuityWireMilimeterDiameter")
                    .value(DEFAULT_ANONYMOUS_CONTINUITY_WIRE_MILIMETER_DIAMETER.doubleValue())
            )
            .andExpect(jsonPath("$.anonymousContinuityWireFlexibility").value(DEFAULT_ANONYMOUS_CONTINUITY_WIRE_FLEXIBILITY.toString()));
    }

    @Test
    @Transactional
    void getNonExistingContinuityWireLongitLaying() throws Exception {
        // Get the continuityWireLongitLaying
        restContinuityWireLongitLayingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewContinuityWireLongitLaying() throws Exception {
        // Initialize the database
        continuityWireLongitLayingRepository.saveAndFlush(continuityWireLongitLaying);

        int databaseSizeBeforeUpdate = continuityWireLongitLayingRepository.findAll().size();

        // Update the continuityWireLongitLaying
        ContinuityWireLongitLaying updatedContinuityWireLongitLaying = continuityWireLongitLayingRepository
            .findById(continuityWireLongitLaying.getId())
            .get();
        // Disconnect from session so that the updates on updatedContinuityWireLongitLaying are not directly saved in db
        em.detach(updatedContinuityWireLongitLaying);
        updatedContinuityWireLongitLaying
            .operationLayer(UPDATED_OPERATION_LAYER)
            .anonymousContinuityWireMetalFiberKind(UPDATED_ANONYMOUS_CONTINUITY_WIRE_METAL_FIBER_KIND)
            .anonymousContinuityWireMilimeterDiameter(UPDATED_ANONYMOUS_CONTINUITY_WIRE_MILIMETER_DIAMETER)
            .anonymousContinuityWireFlexibility(UPDATED_ANONYMOUS_CONTINUITY_WIRE_FLEXIBILITY);

        restContinuityWireLongitLayingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedContinuityWireLongitLaying.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedContinuityWireLongitLaying))
            )
            .andExpect(status().isOk());

        // Validate the ContinuityWireLongitLaying in the database
        List<ContinuityWireLongitLaying> continuityWireLongitLayingList = continuityWireLongitLayingRepository.findAll();
        assertThat(continuityWireLongitLayingList).hasSize(databaseSizeBeforeUpdate);
        ContinuityWireLongitLaying testContinuityWireLongitLaying = continuityWireLongitLayingList.get(
            continuityWireLongitLayingList.size() - 1
        );
        assertThat(testContinuityWireLongitLaying.getOperationLayer()).isEqualTo(UPDATED_OPERATION_LAYER);
        assertThat(testContinuityWireLongitLaying.getAnonymousContinuityWireMetalFiberKind())
            .isEqualTo(UPDATED_ANONYMOUS_CONTINUITY_WIRE_METAL_FIBER_KIND);
        assertThat(testContinuityWireLongitLaying.getAnonymousContinuityWireMilimeterDiameter())
            .isEqualTo(UPDATED_ANONYMOUS_CONTINUITY_WIRE_MILIMETER_DIAMETER);
        assertThat(testContinuityWireLongitLaying.getAnonymousContinuityWireFlexibility())
            .isEqualTo(UPDATED_ANONYMOUS_CONTINUITY_WIRE_FLEXIBILITY);
    }

    @Test
    @Transactional
    void putNonExistingContinuityWireLongitLaying() throws Exception {
        int databaseSizeBeforeUpdate = continuityWireLongitLayingRepository.findAll().size();
        continuityWireLongitLaying.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContinuityWireLongitLayingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, continuityWireLongitLaying.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(continuityWireLongitLaying))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContinuityWireLongitLaying in the database
        List<ContinuityWireLongitLaying> continuityWireLongitLayingList = continuityWireLongitLayingRepository.findAll();
        assertThat(continuityWireLongitLayingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContinuityWireLongitLaying() throws Exception {
        int databaseSizeBeforeUpdate = continuityWireLongitLayingRepository.findAll().size();
        continuityWireLongitLaying.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContinuityWireLongitLayingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(continuityWireLongitLaying))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContinuityWireLongitLaying in the database
        List<ContinuityWireLongitLaying> continuityWireLongitLayingList = continuityWireLongitLayingRepository.findAll();
        assertThat(continuityWireLongitLayingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContinuityWireLongitLaying() throws Exception {
        int databaseSizeBeforeUpdate = continuityWireLongitLayingRepository.findAll().size();
        continuityWireLongitLaying.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContinuityWireLongitLayingMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(continuityWireLongitLaying))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContinuityWireLongitLaying in the database
        List<ContinuityWireLongitLaying> continuityWireLongitLayingList = continuityWireLongitLayingRepository.findAll();
        assertThat(continuityWireLongitLayingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContinuityWireLongitLayingWithPatch() throws Exception {
        // Initialize the database
        continuityWireLongitLayingRepository.saveAndFlush(continuityWireLongitLaying);

        int databaseSizeBeforeUpdate = continuityWireLongitLayingRepository.findAll().size();

        // Update the continuityWireLongitLaying using partial update
        ContinuityWireLongitLaying partialUpdatedContinuityWireLongitLaying = new ContinuityWireLongitLaying();
        partialUpdatedContinuityWireLongitLaying.setId(continuityWireLongitLaying.getId());

        restContinuityWireLongitLayingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContinuityWireLongitLaying.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContinuityWireLongitLaying))
            )
            .andExpect(status().isOk());

        // Validate the ContinuityWireLongitLaying in the database
        List<ContinuityWireLongitLaying> continuityWireLongitLayingList = continuityWireLongitLayingRepository.findAll();
        assertThat(continuityWireLongitLayingList).hasSize(databaseSizeBeforeUpdate);
        ContinuityWireLongitLaying testContinuityWireLongitLaying = continuityWireLongitLayingList.get(
            continuityWireLongitLayingList.size() - 1
        );
        assertThat(testContinuityWireLongitLaying.getOperationLayer()).isEqualTo(DEFAULT_OPERATION_LAYER);
        assertThat(testContinuityWireLongitLaying.getAnonymousContinuityWireMetalFiberKind())
            .isEqualTo(DEFAULT_ANONYMOUS_CONTINUITY_WIRE_METAL_FIBER_KIND);
        assertThat(testContinuityWireLongitLaying.getAnonymousContinuityWireMilimeterDiameter())
            .isEqualTo(DEFAULT_ANONYMOUS_CONTINUITY_WIRE_MILIMETER_DIAMETER);
        assertThat(testContinuityWireLongitLaying.getAnonymousContinuityWireFlexibility())
            .isEqualTo(DEFAULT_ANONYMOUS_CONTINUITY_WIRE_FLEXIBILITY);
    }

    @Test
    @Transactional
    void fullUpdateContinuityWireLongitLayingWithPatch() throws Exception {
        // Initialize the database
        continuityWireLongitLayingRepository.saveAndFlush(continuityWireLongitLaying);

        int databaseSizeBeforeUpdate = continuityWireLongitLayingRepository.findAll().size();

        // Update the continuityWireLongitLaying using partial update
        ContinuityWireLongitLaying partialUpdatedContinuityWireLongitLaying = new ContinuityWireLongitLaying();
        partialUpdatedContinuityWireLongitLaying.setId(continuityWireLongitLaying.getId());

        partialUpdatedContinuityWireLongitLaying
            .operationLayer(UPDATED_OPERATION_LAYER)
            .anonymousContinuityWireMetalFiberKind(UPDATED_ANONYMOUS_CONTINUITY_WIRE_METAL_FIBER_KIND)
            .anonymousContinuityWireMilimeterDiameter(UPDATED_ANONYMOUS_CONTINUITY_WIRE_MILIMETER_DIAMETER)
            .anonymousContinuityWireFlexibility(UPDATED_ANONYMOUS_CONTINUITY_WIRE_FLEXIBILITY);

        restContinuityWireLongitLayingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContinuityWireLongitLaying.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContinuityWireLongitLaying))
            )
            .andExpect(status().isOk());

        // Validate the ContinuityWireLongitLaying in the database
        List<ContinuityWireLongitLaying> continuityWireLongitLayingList = continuityWireLongitLayingRepository.findAll();
        assertThat(continuityWireLongitLayingList).hasSize(databaseSizeBeforeUpdate);
        ContinuityWireLongitLaying testContinuityWireLongitLaying = continuityWireLongitLayingList.get(
            continuityWireLongitLayingList.size() - 1
        );
        assertThat(testContinuityWireLongitLaying.getOperationLayer()).isEqualTo(UPDATED_OPERATION_LAYER);
        assertThat(testContinuityWireLongitLaying.getAnonymousContinuityWireMetalFiberKind())
            .isEqualTo(UPDATED_ANONYMOUS_CONTINUITY_WIRE_METAL_FIBER_KIND);
        assertThat(testContinuityWireLongitLaying.getAnonymousContinuityWireMilimeterDiameter())
            .isEqualTo(UPDATED_ANONYMOUS_CONTINUITY_WIRE_MILIMETER_DIAMETER);
        assertThat(testContinuityWireLongitLaying.getAnonymousContinuityWireFlexibility())
            .isEqualTo(UPDATED_ANONYMOUS_CONTINUITY_WIRE_FLEXIBILITY);
    }

    @Test
    @Transactional
    void patchNonExistingContinuityWireLongitLaying() throws Exception {
        int databaseSizeBeforeUpdate = continuityWireLongitLayingRepository.findAll().size();
        continuityWireLongitLaying.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContinuityWireLongitLayingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, continuityWireLongitLaying.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(continuityWireLongitLaying))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContinuityWireLongitLaying in the database
        List<ContinuityWireLongitLaying> continuityWireLongitLayingList = continuityWireLongitLayingRepository.findAll();
        assertThat(continuityWireLongitLayingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContinuityWireLongitLaying() throws Exception {
        int databaseSizeBeforeUpdate = continuityWireLongitLayingRepository.findAll().size();
        continuityWireLongitLaying.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContinuityWireLongitLayingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(continuityWireLongitLaying))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContinuityWireLongitLaying in the database
        List<ContinuityWireLongitLaying> continuityWireLongitLayingList = continuityWireLongitLayingRepository.findAll();
        assertThat(continuityWireLongitLayingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContinuityWireLongitLaying() throws Exception {
        int databaseSizeBeforeUpdate = continuityWireLongitLayingRepository.findAll().size();
        continuityWireLongitLaying.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContinuityWireLongitLayingMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(continuityWireLongitLaying))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContinuityWireLongitLaying in the database
        List<ContinuityWireLongitLaying> continuityWireLongitLayingList = continuityWireLongitLayingRepository.findAll();
        assertThat(continuityWireLongitLayingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContinuityWireLongitLaying() throws Exception {
        // Initialize the database
        continuityWireLongitLayingRepository.saveAndFlush(continuityWireLongitLaying);

        int databaseSizeBeforeDelete = continuityWireLongitLayingRepository.findAll().size();

        // Delete the continuityWireLongitLaying
        restContinuityWireLongitLayingMockMvc
            .perform(delete(ENTITY_API_URL_ID, continuityWireLongitLaying.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContinuityWireLongitLaying> continuityWireLongitLayingList = continuityWireLongitLayingRepository.findAll();
        assertThat(continuityWireLongitLayingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
