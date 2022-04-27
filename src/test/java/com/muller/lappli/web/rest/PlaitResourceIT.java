package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.Plait;
import com.muller.lappli.domain.StrandSupply;
import com.muller.lappli.domain.enumeration.MetalFiberKind;
import com.muller.lappli.repository.PlaitRepository;
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
 * Integration tests for the {@link PlaitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlaitResourceIT {

    private static final Long DEFAULT_OPERATION_LAYER = 1L;
    private static final Long UPDATED_OPERATION_LAYER = 2L;

    private static final Double DEFAULT_TARGET_COVERING_RATE = 0D;
    private static final Double UPDATED_TARGET_COVERING_RATE = 1D;

    private static final Double DEFAULT_TARGET_DEGREE_ANGLE = 1D;
    private static final Double UPDATED_TARGET_DEGREE_ANGLE = 2D;

    private static final Boolean DEFAULT_TARGETING_COVERING_RATE_NOT_ANGLE = false;
    private static final Boolean UPDATED_TARGETING_COVERING_RATE_NOT_ANGLE = true;

    private static final Long DEFAULT_ANONYMOUS_METAL_FIBER_NUMBER = 1L;
    private static final Long UPDATED_ANONYMOUS_METAL_FIBER_NUMBER = 2L;

    private static final String DEFAULT_ANONYMOUS_METAL_FIBER_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_ANONYMOUS_METAL_FIBER_DESIGNATION = "BBBBBBBBBB";

    private static final MetalFiberKind DEFAULT_ANONYMOUS_METAL_FIBER_METAL_FIBER_KIND = MetalFiberKind.RED_COPPER;
    private static final MetalFiberKind UPDATED_ANONYMOUS_METAL_FIBER_METAL_FIBER_KIND = MetalFiberKind.TINNED_COPPER;

    private static final Double DEFAULT_ANONYMOUS_METAL_FIBER_MILIMETER_DIAMETER = 1D;
    private static final Double UPDATED_ANONYMOUS_METAL_FIBER_MILIMETER_DIAMETER = 2D;

    private static final String ENTITY_API_URL = "/api/plaits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlaitRepository plaitRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlaitMockMvc;

    private Plait plait;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plait createEntity(EntityManager em) {
        Plait plait = new Plait()
            .operationLayer(DEFAULT_OPERATION_LAYER)
            .targetCoveringRate(DEFAULT_TARGET_COVERING_RATE)
            .targetDegreeAngle(DEFAULT_TARGET_DEGREE_ANGLE)
            .targetingCoveringRateNotAngle(DEFAULT_TARGETING_COVERING_RATE_NOT_ANGLE)
            .anonymousMetalFiberNumber(DEFAULT_ANONYMOUS_METAL_FIBER_NUMBER)
            .anonymousMetalFiberDesignation(DEFAULT_ANONYMOUS_METAL_FIBER_DESIGNATION)
            .anonymousMetalFiberMetalFiberKind(DEFAULT_ANONYMOUS_METAL_FIBER_METAL_FIBER_KIND)
            .anonymousMetalFiberMilimeterDiameter(DEFAULT_ANONYMOUS_METAL_FIBER_MILIMETER_DIAMETER);
        // Add required entity
        StrandSupply strandSupply;
        if (TestUtil.findAll(em, StrandSupply.class).isEmpty()) {
            strandSupply = StrandSupplyResourceIT.createEntity(em);
            em.persist(strandSupply);
            em.flush();
        } else {
            strandSupply = TestUtil.findAll(em, StrandSupply.class).get(0);
        }
        plait.setOwnerStrandSupply(strandSupply);
        return plait;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plait createUpdatedEntity(EntityManager em) {
        Plait plait = new Plait()
            .operationLayer(UPDATED_OPERATION_LAYER)
            .targetCoveringRate(UPDATED_TARGET_COVERING_RATE)
            .targetDegreeAngle(UPDATED_TARGET_DEGREE_ANGLE)
            .targetingCoveringRateNotAngle(UPDATED_TARGETING_COVERING_RATE_NOT_ANGLE)
            .anonymousMetalFiberNumber(UPDATED_ANONYMOUS_METAL_FIBER_NUMBER)
            .anonymousMetalFiberDesignation(UPDATED_ANONYMOUS_METAL_FIBER_DESIGNATION)
            .anonymousMetalFiberMetalFiberKind(UPDATED_ANONYMOUS_METAL_FIBER_METAL_FIBER_KIND)
            .anonymousMetalFiberMilimeterDiameter(UPDATED_ANONYMOUS_METAL_FIBER_MILIMETER_DIAMETER);
        // Add required entity
        StrandSupply strandSupply;
        if (TestUtil.findAll(em, StrandSupply.class).isEmpty()) {
            strandSupply = StrandSupplyResourceIT.createUpdatedEntity(em);
            em.persist(strandSupply);
            em.flush();
        } else {
            strandSupply = TestUtil.findAll(em, StrandSupply.class).get(0);
        }
        plait.setOwnerStrandSupply(strandSupply);
        return plait;
    }

    @BeforeEach
    public void initTest() {
        plait = createEntity(em);
    }

    @Test
    @Transactional
    void createPlait() throws Exception {
        int databaseSizeBeforeCreate = plaitRepository.findAll().size();
        // Create the Plait
        restPlaitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plait)))
            .andExpect(status().isCreated());

        // Validate the Plait in the database
        List<Plait> plaitList = plaitRepository.findAll();
        assertThat(plaitList).hasSize(databaseSizeBeforeCreate + 1);
        Plait testPlait = plaitList.get(plaitList.size() - 1);
        assertThat(testPlait.getOperationLayer()).isEqualTo(DEFAULT_OPERATION_LAYER);
        assertThat(testPlait.getTargetCoveringRate()).isEqualTo(DEFAULT_TARGET_COVERING_RATE);
        assertThat(testPlait.getTargetDegreeAngle()).isEqualTo(DEFAULT_TARGET_DEGREE_ANGLE);
        assertThat(testPlait.getTargetingCoveringRateNotAngle()).isEqualTo(DEFAULT_TARGETING_COVERING_RATE_NOT_ANGLE);
        assertThat(testPlait.getAnonymousMetalFiberNumber()).isEqualTo(DEFAULT_ANONYMOUS_METAL_FIBER_NUMBER);
        assertThat(testPlait.getAnonymousMetalFiberDesignation()).isEqualTo(DEFAULT_ANONYMOUS_METAL_FIBER_DESIGNATION);
        assertThat(testPlait.getAnonymousMetalFiberMetalFiberKind()).isEqualTo(DEFAULT_ANONYMOUS_METAL_FIBER_METAL_FIBER_KIND);
        assertThat(testPlait.getAnonymousMetalFiberMilimeterDiameter()).isEqualTo(DEFAULT_ANONYMOUS_METAL_FIBER_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void createPlaitWithExistingId() throws Exception {
        // Create the Plait with an existing ID
        plait.setId(1L);

        int databaseSizeBeforeCreate = plaitRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlaitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plait)))
            .andExpect(status().isBadRequest());

        // Validate the Plait in the database
        List<Plait> plaitList = plaitRepository.findAll();
        assertThat(plaitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkOperationLayerIsRequired() throws Exception {
        int databaseSizeBeforeTest = plaitRepository.findAll().size();
        // set the field null
        plait.setOperationLayer(null);

        // Create the Plait, which fails.

        restPlaitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plait)))
            .andExpect(status().isBadRequest());

        List<Plait> plaitList = plaitRepository.findAll();
        assertThat(plaitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTargetingCoveringRateNotAngleIsRequired() throws Exception {
        int databaseSizeBeforeTest = plaitRepository.findAll().size();
        // set the field null
        plait.setTargetingCoveringRateNotAngle(null);

        // Create the Plait, which fails.

        restPlaitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plait)))
            .andExpect(status().isBadRequest());

        List<Plait> plaitList = plaitRepository.findAll();
        assertThat(plaitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPlaits() throws Exception {
        // Initialize the database
        plaitRepository.saveAndFlush(plait);

        // Get all the plaitList
        restPlaitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plait.getId().intValue())))
            .andExpect(jsonPath("$.[*].operationLayer").value(hasItem(DEFAULT_OPERATION_LAYER.intValue())))
            .andExpect(jsonPath("$.[*].targetCoveringRate").value(hasItem(DEFAULT_TARGET_COVERING_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].targetDegreeAngle").value(hasItem(DEFAULT_TARGET_DEGREE_ANGLE.doubleValue())))
            .andExpect(
                jsonPath("$.[*].targetingCoveringRateNotAngle").value(hasItem(DEFAULT_TARGETING_COVERING_RATE_NOT_ANGLE.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].anonymousMetalFiberNumber").value(hasItem(DEFAULT_ANONYMOUS_METAL_FIBER_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].anonymousMetalFiberDesignation").value(hasItem(DEFAULT_ANONYMOUS_METAL_FIBER_DESIGNATION)))
            .andExpect(
                jsonPath("$.[*].anonymousMetalFiberMetalFiberKind")
                    .value(hasItem(DEFAULT_ANONYMOUS_METAL_FIBER_METAL_FIBER_KIND.toString()))
            )
            .andExpect(
                jsonPath("$.[*].anonymousMetalFiberMilimeterDiameter")
                    .value(hasItem(DEFAULT_ANONYMOUS_METAL_FIBER_MILIMETER_DIAMETER.doubleValue()))
            );
    }

    @Test
    @Transactional
    void getPlait() throws Exception {
        // Initialize the database
        plaitRepository.saveAndFlush(plait);

        // Get the plait
        restPlaitMockMvc
            .perform(get(ENTITY_API_URL_ID, plait.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plait.getId().intValue()))
            .andExpect(jsonPath("$.operationLayer").value(DEFAULT_OPERATION_LAYER.intValue()))
            .andExpect(jsonPath("$.targetCoveringRate").value(DEFAULT_TARGET_COVERING_RATE.doubleValue()))
            .andExpect(jsonPath("$.targetDegreeAngle").value(DEFAULT_TARGET_DEGREE_ANGLE.doubleValue()))
            .andExpect(jsonPath("$.targetingCoveringRateNotAngle").value(DEFAULT_TARGETING_COVERING_RATE_NOT_ANGLE.booleanValue()))
            .andExpect(jsonPath("$.anonymousMetalFiberNumber").value(DEFAULT_ANONYMOUS_METAL_FIBER_NUMBER.intValue()))
            .andExpect(jsonPath("$.anonymousMetalFiberDesignation").value(DEFAULT_ANONYMOUS_METAL_FIBER_DESIGNATION))
            .andExpect(jsonPath("$.anonymousMetalFiberMetalFiberKind").value(DEFAULT_ANONYMOUS_METAL_FIBER_METAL_FIBER_KIND.toString()))
            .andExpect(
                jsonPath("$.anonymousMetalFiberMilimeterDiameter").value(DEFAULT_ANONYMOUS_METAL_FIBER_MILIMETER_DIAMETER.doubleValue())
            );
    }

    @Test
    @Transactional
    void getNonExistingPlait() throws Exception {
        // Get the plait
        restPlaitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPlait() throws Exception {
        // Initialize the database
        plaitRepository.saveAndFlush(plait);

        int databaseSizeBeforeUpdate = plaitRepository.findAll().size();

        // Update the plait
        Plait updatedPlait = plaitRepository.findById(plait.getId()).get();
        // Disconnect from session so that the updates on updatedPlait are not directly saved in db
        em.detach(updatedPlait);
        updatedPlait
            .operationLayer(UPDATED_OPERATION_LAYER)
            .targetCoveringRate(UPDATED_TARGET_COVERING_RATE)
            .targetDegreeAngle(UPDATED_TARGET_DEGREE_ANGLE)
            .targetingCoveringRateNotAngle(UPDATED_TARGETING_COVERING_RATE_NOT_ANGLE)
            .anonymousMetalFiberNumber(UPDATED_ANONYMOUS_METAL_FIBER_NUMBER)
            .anonymousMetalFiberDesignation(UPDATED_ANONYMOUS_METAL_FIBER_DESIGNATION)
            .anonymousMetalFiberMetalFiberKind(UPDATED_ANONYMOUS_METAL_FIBER_METAL_FIBER_KIND)
            .anonymousMetalFiberMilimeterDiameter(UPDATED_ANONYMOUS_METAL_FIBER_MILIMETER_DIAMETER);

        restPlaitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPlait.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPlait))
            )
            .andExpect(status().isOk());

        // Validate the Plait in the database
        List<Plait> plaitList = plaitRepository.findAll();
        assertThat(plaitList).hasSize(databaseSizeBeforeUpdate);
        Plait testPlait = plaitList.get(plaitList.size() - 1);
        assertThat(testPlait.getOperationLayer()).isEqualTo(DEFAULT_OPERATION_LAYER);
        assertThat(testPlait.getTargetCoveringRate()).isEqualTo(UPDATED_TARGET_COVERING_RATE);
        assertThat(testPlait.getTargetDegreeAngle()).isEqualTo(UPDATED_TARGET_DEGREE_ANGLE);
        assertThat(testPlait.getTargetingCoveringRateNotAngle()).isEqualTo(UPDATED_TARGETING_COVERING_RATE_NOT_ANGLE);
        assertThat(testPlait.getAnonymousMetalFiberNumber()).isEqualTo(UPDATED_ANONYMOUS_METAL_FIBER_NUMBER);
        assertThat(testPlait.getAnonymousMetalFiberDesignation()).isEqualTo(UPDATED_ANONYMOUS_METAL_FIBER_DESIGNATION);
        assertThat(testPlait.getAnonymousMetalFiberMetalFiberKind()).isEqualTo(UPDATED_ANONYMOUS_METAL_FIBER_METAL_FIBER_KIND);
        assertThat(testPlait.getAnonymousMetalFiberMilimeterDiameter()).isEqualTo(UPDATED_ANONYMOUS_METAL_FIBER_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void putNonExistingPlait() throws Exception {
        int databaseSizeBeforeUpdate = plaitRepository.findAll().size();
        plait.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlaitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, plait.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plait))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plait in the database
        List<Plait> plaitList = plaitRepository.findAll();
        assertThat(plaitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlait() throws Exception {
        int databaseSizeBeforeUpdate = plaitRepository.findAll().size();
        plait.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plait))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plait in the database
        List<Plait> plaitList = plaitRepository.findAll();
        assertThat(plaitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlait() throws Exception {
        int databaseSizeBeforeUpdate = plaitRepository.findAll().size();
        plait.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plait)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Plait in the database
        List<Plait> plaitList = plaitRepository.findAll();
        assertThat(plaitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlaitWithPatch() throws Exception {
        // Initialize the database
        plaitRepository.saveAndFlush(plait);

        int databaseSizeBeforeUpdate = plaitRepository.findAll().size();

        // Update the plait using partial update
        Plait partialUpdatedPlait = new Plait();
        partialUpdatedPlait.setId(plait.getId());

        partialUpdatedPlait
            .operationLayer(UPDATED_OPERATION_LAYER)
            .targetingCoveringRateNotAngle(UPDATED_TARGETING_COVERING_RATE_NOT_ANGLE)
            .anonymousMetalFiberDesignation(UPDATED_ANONYMOUS_METAL_FIBER_DESIGNATION)
            .anonymousMetalFiberMetalFiberKind(UPDATED_ANONYMOUS_METAL_FIBER_METAL_FIBER_KIND)
            .anonymousMetalFiberMilimeterDiameter(UPDATED_ANONYMOUS_METAL_FIBER_MILIMETER_DIAMETER);

        restPlaitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlait.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlait))
            )
            .andExpect(status().isOk());

        // Validate the Plait in the database
        List<Plait> plaitList = plaitRepository.findAll();
        assertThat(plaitList).hasSize(databaseSizeBeforeUpdate);
        Plait testPlait = plaitList.get(plaitList.size() - 1);
        assertThat(testPlait.getOperationLayer()).isEqualTo(UPDATED_OPERATION_LAYER);
        assertThat(testPlait.getTargetCoveringRate()).isEqualTo(DEFAULT_TARGET_COVERING_RATE);
        assertThat(testPlait.getTargetDegreeAngle()).isEqualTo(DEFAULT_TARGET_DEGREE_ANGLE);
        assertThat(testPlait.getTargetingCoveringRateNotAngle()).isEqualTo(UPDATED_TARGETING_COVERING_RATE_NOT_ANGLE);
        assertThat(testPlait.getAnonymousMetalFiberNumber()).isEqualTo(DEFAULT_ANONYMOUS_METAL_FIBER_NUMBER);
        assertThat(testPlait.getAnonymousMetalFiberDesignation()).isEqualTo(UPDATED_ANONYMOUS_METAL_FIBER_DESIGNATION);
        assertThat(testPlait.getAnonymousMetalFiberMetalFiberKind()).isEqualTo(UPDATED_ANONYMOUS_METAL_FIBER_METAL_FIBER_KIND);
        assertThat(testPlait.getAnonymousMetalFiberMilimeterDiameter()).isEqualTo(UPDATED_ANONYMOUS_METAL_FIBER_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void fullUpdatePlaitWithPatch() throws Exception {
        // Initialize the database
        plaitRepository.saveAndFlush(plait);

        int databaseSizeBeforeUpdate = plaitRepository.findAll().size();

        // Update the plait using partial update
        Plait partialUpdatedPlait = new Plait();
        partialUpdatedPlait.setId(plait.getId());

        partialUpdatedPlait
            .operationLayer(UPDATED_OPERATION_LAYER)
            .targetCoveringRate(UPDATED_TARGET_COVERING_RATE)
            .targetDegreeAngle(UPDATED_TARGET_DEGREE_ANGLE)
            .targetingCoveringRateNotAngle(UPDATED_TARGETING_COVERING_RATE_NOT_ANGLE)
            .anonymousMetalFiberNumber(UPDATED_ANONYMOUS_METAL_FIBER_NUMBER)
            .anonymousMetalFiberDesignation(UPDATED_ANONYMOUS_METAL_FIBER_DESIGNATION)
            .anonymousMetalFiberMetalFiberKind(UPDATED_ANONYMOUS_METAL_FIBER_METAL_FIBER_KIND)
            .anonymousMetalFiberMilimeterDiameter(UPDATED_ANONYMOUS_METAL_FIBER_MILIMETER_DIAMETER);

        restPlaitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlait.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlait))
            )
            .andExpect(status().isOk());

        // Validate the Plait in the database
        List<Plait> plaitList = plaitRepository.findAll();
        assertThat(plaitList).hasSize(databaseSizeBeforeUpdate);
        Plait testPlait = plaitList.get(plaitList.size() - 1);
        assertThat(testPlait.getOperationLayer()).isEqualTo(UPDATED_OPERATION_LAYER);
        assertThat(testPlait.getTargetCoveringRate()).isEqualTo(UPDATED_TARGET_COVERING_RATE);
        assertThat(testPlait.getTargetDegreeAngle()).isEqualTo(UPDATED_TARGET_DEGREE_ANGLE);
        assertThat(testPlait.getTargetingCoveringRateNotAngle()).isEqualTo(UPDATED_TARGETING_COVERING_RATE_NOT_ANGLE);
        assertThat(testPlait.getAnonymousMetalFiberNumber()).isEqualTo(UPDATED_ANONYMOUS_METAL_FIBER_NUMBER);
        assertThat(testPlait.getAnonymousMetalFiberDesignation()).isEqualTo(UPDATED_ANONYMOUS_METAL_FIBER_DESIGNATION);
        assertThat(testPlait.getAnonymousMetalFiberMetalFiberKind()).isEqualTo(UPDATED_ANONYMOUS_METAL_FIBER_METAL_FIBER_KIND);
        assertThat(testPlait.getAnonymousMetalFiberMilimeterDiameter()).isEqualTo(UPDATED_ANONYMOUS_METAL_FIBER_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void patchNonExistingPlait() throws Exception {
        int databaseSizeBeforeUpdate = plaitRepository.findAll().size();
        plait.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlaitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, plait.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plait))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plait in the database
        List<Plait> plaitList = plaitRepository.findAll();
        assertThat(plaitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlait() throws Exception {
        int databaseSizeBeforeUpdate = plaitRepository.findAll().size();
        plait.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plait))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plait in the database
        List<Plait> plaitList = plaitRepository.findAll();
        assertThat(plaitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlait() throws Exception {
        int databaseSizeBeforeUpdate = plaitRepository.findAll().size();
        plait.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaitMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(plait)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Plait in the database
        List<Plait> plaitList = plaitRepository.findAll();
        assertThat(plaitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlait() throws Exception {
        // Initialize the database
        plaitRepository.saveAndFlush(plait);

        int databaseSizeBeforeDelete = plaitRepository.findAll().size();

        // Delete the plait
        restPlaitMockMvc
            .perform(delete(ENTITY_API_URL_ID, plait.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Plait> plaitList = plaitRepository.findAll();
        assertThat(plaitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
