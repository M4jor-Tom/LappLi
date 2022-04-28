package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.CarrierPlait;
import com.muller.lappli.domain.CarrierPlaitFiber;
import com.muller.lappli.domain.StrandSupply;
import com.muller.lappli.repository.CarrierPlaitRepository;
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
 * Integration tests for the {@link CarrierPlaitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CarrierPlaitResourceIT {

    private static final Long DEFAULT_OPERATION_LAYER = 1L;
    private static final Long UPDATED_OPERATION_LAYER = 2L;

    private static final Double DEFAULT_MINIMUM_DECA_NEWTON_LOAD = 0D;
    private static final Double UPDATED_MINIMUM_DECA_NEWTON_LOAD = 1D;

    private static final Long DEFAULT_DEGREE_ASSEMBLY_ANGLE = 0L;
    private static final Long UPDATED_DEGREE_ASSEMBLY_ANGLE = 1L;

    private static final Long DEFAULT_FORCED_END_PER_BOBINS_COUNT = 0L;
    private static final Long UPDATED_FORCED_END_PER_BOBINS_COUNT = 1L;

    private static final String ENTITY_API_URL = "/api/carrier-plaits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CarrierPlaitRepository carrierPlaitRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCarrierPlaitMockMvc;

    private CarrierPlait carrierPlait;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarrierPlait createEntity(EntityManager em) {
        CarrierPlait carrierPlait = new CarrierPlait()
            .operationLayer(DEFAULT_OPERATION_LAYER)
            .minimumDecaNewtonLoad(DEFAULT_MINIMUM_DECA_NEWTON_LOAD)
            .degreeAssemblyAngle(DEFAULT_DEGREE_ASSEMBLY_ANGLE)
            .forcedEndPerBobinsCount(DEFAULT_FORCED_END_PER_BOBINS_COUNT);
        // Add required entity
        CarrierPlaitFiber carrierPlaitFiber;
        if (TestUtil.findAll(em, CarrierPlaitFiber.class).isEmpty()) {
            carrierPlaitFiber = CarrierPlaitFiberResourceIT.createEntity(em);
            em.persist(carrierPlaitFiber);
            em.flush();
        } else {
            carrierPlaitFiber = TestUtil.findAll(em, CarrierPlaitFiber.class).get(0);
        }
        carrierPlait.setCarrierPlaitFiber(carrierPlaitFiber);
        // Add required entity
        StrandSupply strandSupply;
        if (TestUtil.findAll(em, StrandSupply.class).isEmpty()) {
            strandSupply = StrandSupplyResourceIT.createEntity(em);
            em.persist(strandSupply);
            em.flush();
        } else {
            strandSupply = TestUtil.findAll(em, StrandSupply.class).get(0);
        }
        carrierPlait.setOwnerStrandSupply(strandSupply);
        return carrierPlait;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarrierPlait createUpdatedEntity(EntityManager em) {
        CarrierPlait carrierPlait = new CarrierPlait()
            .operationLayer(UPDATED_OPERATION_LAYER)
            .minimumDecaNewtonLoad(UPDATED_MINIMUM_DECA_NEWTON_LOAD)
            .degreeAssemblyAngle(UPDATED_DEGREE_ASSEMBLY_ANGLE)
            .forcedEndPerBobinsCount(UPDATED_FORCED_END_PER_BOBINS_COUNT);
        // Add required entity
        CarrierPlaitFiber carrierPlaitFiber;
        if (TestUtil.findAll(em, CarrierPlaitFiber.class).isEmpty()) {
            carrierPlaitFiber = CarrierPlaitFiberResourceIT.createUpdatedEntity(em);
            em.persist(carrierPlaitFiber);
            em.flush();
        } else {
            carrierPlaitFiber = TestUtil.findAll(em, CarrierPlaitFiber.class).get(0);
        }
        carrierPlait.setCarrierPlaitFiber(carrierPlaitFiber);
        // Add required entity
        StrandSupply strandSupply;
        if (TestUtil.findAll(em, StrandSupply.class).isEmpty()) {
            strandSupply = StrandSupplyResourceIT.createUpdatedEntity(em);
            em.persist(strandSupply);
            em.flush();
        } else {
            strandSupply = TestUtil.findAll(em, StrandSupply.class).get(0);
        }
        carrierPlait.setOwnerStrandSupply(strandSupply);
        return carrierPlait;
    }

    @BeforeEach
    public void initTest() {
        carrierPlait = createEntity(em);
    }

    @Test
    @Transactional
    void createCarrierPlait() throws Exception {
        int databaseSizeBeforeCreate = carrierPlaitRepository.findAll().size();
        // Create the CarrierPlait
        restCarrierPlaitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carrierPlait)))
            .andExpect(status().isCreated());

        // Validate the CarrierPlait in the database
        List<CarrierPlait> carrierPlaitList = carrierPlaitRepository.findAll();
        assertThat(carrierPlaitList).hasSize(databaseSizeBeforeCreate + 1);
        CarrierPlait testCarrierPlait = carrierPlaitList.get(carrierPlaitList.size() - 1);
        assertThat(testCarrierPlait.getOperationLayer()).isEqualTo(DEFAULT_OPERATION_LAYER);
        assertThat(testCarrierPlait.getMinimumDecaNewtonLoad()).isEqualTo(DEFAULT_MINIMUM_DECA_NEWTON_LOAD);
        assertThat(testCarrierPlait.getDegreeAssemblyAngle()).isEqualTo(DEFAULT_DEGREE_ASSEMBLY_ANGLE);
        assertThat(testCarrierPlait.getForcedEndPerBobinsCount()).isEqualTo(DEFAULT_FORCED_END_PER_BOBINS_COUNT);
    }

    @Test
    @Transactional
    void createCarrierPlaitWithExistingId() throws Exception {
        // Create the CarrierPlait with an existing ID
        carrierPlait.setId(1L);

        int databaseSizeBeforeCreate = carrierPlaitRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarrierPlaitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carrierPlait)))
            .andExpect(status().isBadRequest());

        // Validate the CarrierPlait in the database
        List<CarrierPlait> carrierPlaitList = carrierPlaitRepository.findAll();
        assertThat(carrierPlaitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkOperationLayerIsRequired() throws Exception {
        int databaseSizeBeforeTest = carrierPlaitRepository.findAll().size();
        // set the field null
        carrierPlait.setOperationLayer(null);

        // Create the CarrierPlait, which fails.

        restCarrierPlaitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carrierPlait)))
            .andExpect(status().isBadRequest());

        List<CarrierPlait> carrierPlaitList = carrierPlaitRepository.findAll();
        assertThat(carrierPlaitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMinimumDecaNewtonLoadIsRequired() throws Exception {
        int databaseSizeBeforeTest = carrierPlaitRepository.findAll().size();
        // set the field null
        carrierPlait.setMinimumDecaNewtonLoad(null);

        // Create the CarrierPlait, which fails.

        restCarrierPlaitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carrierPlait)))
            .andExpect(status().isBadRequest());

        List<CarrierPlait> carrierPlaitList = carrierPlaitRepository.findAll();
        assertThat(carrierPlaitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDegreeAssemblyAngleIsRequired() throws Exception {
        int databaseSizeBeforeTest = carrierPlaitRepository.findAll().size();
        // set the field null
        carrierPlait.setDegreeAssemblyAngle(null);

        // Create the CarrierPlait, which fails.

        restCarrierPlaitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carrierPlait)))
            .andExpect(status().isBadRequest());

        List<CarrierPlait> carrierPlaitList = carrierPlaitRepository.findAll();
        assertThat(carrierPlaitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCarrierPlaits() throws Exception {
        // Initialize the database
        carrierPlaitRepository.saveAndFlush(carrierPlait);

        // Get all the carrierPlaitList
        restCarrierPlaitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carrierPlait.getId().intValue())))
            .andExpect(jsonPath("$.[*].operationLayer").value(hasItem(DEFAULT_OPERATION_LAYER.intValue())))
            .andExpect(jsonPath("$.[*].minimumDecaNewtonLoad").value(hasItem(DEFAULT_MINIMUM_DECA_NEWTON_LOAD.doubleValue())))
            .andExpect(jsonPath("$.[*].degreeAssemblyAngle").value(hasItem(DEFAULT_DEGREE_ASSEMBLY_ANGLE.intValue())))
            .andExpect(jsonPath("$.[*].forcedEndPerBobinsCount").value(hasItem(DEFAULT_FORCED_END_PER_BOBINS_COUNT.intValue())));
    }

    @Test
    @Transactional
    void getCarrierPlait() throws Exception {
        // Initialize the database
        carrierPlaitRepository.saveAndFlush(carrierPlait);

        // Get the carrierPlait
        restCarrierPlaitMockMvc
            .perform(get(ENTITY_API_URL_ID, carrierPlait.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(carrierPlait.getId().intValue()))
            .andExpect(jsonPath("$.operationLayer").value(DEFAULT_OPERATION_LAYER.intValue()))
            .andExpect(jsonPath("$.minimumDecaNewtonLoad").value(DEFAULT_MINIMUM_DECA_NEWTON_LOAD.doubleValue()))
            .andExpect(jsonPath("$.degreeAssemblyAngle").value(DEFAULT_DEGREE_ASSEMBLY_ANGLE.intValue()))
            .andExpect(jsonPath("$.forcedEndPerBobinsCount").value(DEFAULT_FORCED_END_PER_BOBINS_COUNT.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingCarrierPlait() throws Exception {
        // Get the carrierPlait
        restCarrierPlaitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCarrierPlait() throws Exception {
        // Initialize the database
        carrierPlaitRepository.saveAndFlush(carrierPlait);

        int databaseSizeBeforeUpdate = carrierPlaitRepository.findAll().size();

        // Update the carrierPlait
        CarrierPlait updatedCarrierPlait = carrierPlaitRepository.findById(carrierPlait.getId()).get();
        // Disconnect from session so that the updates on updatedCarrierPlait are not directly saved in db
        em.detach(updatedCarrierPlait);
        updatedCarrierPlait
            .operationLayer(UPDATED_OPERATION_LAYER)
            .minimumDecaNewtonLoad(UPDATED_MINIMUM_DECA_NEWTON_LOAD)
            .degreeAssemblyAngle(UPDATED_DEGREE_ASSEMBLY_ANGLE)
            .forcedEndPerBobinsCount(UPDATED_FORCED_END_PER_BOBINS_COUNT);

        restCarrierPlaitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCarrierPlait.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCarrierPlait))
            )
            .andExpect(status().isOk());

        // Validate the CarrierPlait in the database
        List<CarrierPlait> carrierPlaitList = carrierPlaitRepository.findAll();
        assertThat(carrierPlaitList).hasSize(databaseSizeBeforeUpdate);
        CarrierPlait testCarrierPlait = carrierPlaitList.get(carrierPlaitList.size() - 1);
        assertThat(testCarrierPlait.getOperationLayer()).isEqualTo(DEFAULT_OPERATION_LAYER);
        assertThat(testCarrierPlait.getMinimumDecaNewtonLoad()).isEqualTo(UPDATED_MINIMUM_DECA_NEWTON_LOAD);
        assertThat(testCarrierPlait.getDegreeAssemblyAngle()).isEqualTo(UPDATED_DEGREE_ASSEMBLY_ANGLE);
        assertThat(testCarrierPlait.getForcedEndPerBobinsCount()).isEqualTo(UPDATED_FORCED_END_PER_BOBINS_COUNT);
    }

    @Test
    @Transactional
    void putNonExistingCarrierPlait() throws Exception {
        int databaseSizeBeforeUpdate = carrierPlaitRepository.findAll().size();
        carrierPlait.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarrierPlaitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, carrierPlait.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(carrierPlait))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarrierPlait in the database
        List<CarrierPlait> carrierPlaitList = carrierPlaitRepository.findAll();
        assertThat(carrierPlaitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCarrierPlait() throws Exception {
        int databaseSizeBeforeUpdate = carrierPlaitRepository.findAll().size();
        carrierPlait.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarrierPlaitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(carrierPlait))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarrierPlait in the database
        List<CarrierPlait> carrierPlaitList = carrierPlaitRepository.findAll();
        assertThat(carrierPlaitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCarrierPlait() throws Exception {
        int databaseSizeBeforeUpdate = carrierPlaitRepository.findAll().size();
        carrierPlait.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarrierPlaitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carrierPlait)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CarrierPlait in the database
        List<CarrierPlait> carrierPlaitList = carrierPlaitRepository.findAll();
        assertThat(carrierPlaitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCarrierPlaitWithPatch() throws Exception {
        // Initialize the database
        carrierPlaitRepository.saveAndFlush(carrierPlait);

        int databaseSizeBeforeUpdate = carrierPlaitRepository.findAll().size();

        // Update the carrierPlait using partial update
        CarrierPlait partialUpdatedCarrierPlait = new CarrierPlait();
        partialUpdatedCarrierPlait.setId(carrierPlait.getId());

        partialUpdatedCarrierPlait.operationLayer(UPDATED_OPERATION_LAYER).minimumDecaNewtonLoad(UPDATED_MINIMUM_DECA_NEWTON_LOAD);

        restCarrierPlaitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarrierPlait.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCarrierPlait))
            )
            .andExpect(status().isOk());

        // Validate the CarrierPlait in the database
        List<CarrierPlait> carrierPlaitList = carrierPlaitRepository.findAll();
        assertThat(carrierPlaitList).hasSize(databaseSizeBeforeUpdate);
        CarrierPlait testCarrierPlait = carrierPlaitList.get(carrierPlaitList.size() - 1);
        assertThat(testCarrierPlait.getOperationLayer()).isEqualTo(DEFAULT_OPERATION_LAYER);
        assertThat(testCarrierPlait.getMinimumDecaNewtonLoad()).isEqualTo(UPDATED_MINIMUM_DECA_NEWTON_LOAD);
        assertThat(testCarrierPlait.getDegreeAssemblyAngle()).isEqualTo(DEFAULT_DEGREE_ASSEMBLY_ANGLE);
        assertThat(testCarrierPlait.getForcedEndPerBobinsCount()).isEqualTo(DEFAULT_FORCED_END_PER_BOBINS_COUNT);
    }

    @Test
    @Transactional
    void fullUpdateCarrierPlaitWithPatch() throws Exception {
        // Initialize the database
        carrierPlaitRepository.saveAndFlush(carrierPlait);

        int databaseSizeBeforeUpdate = carrierPlaitRepository.findAll().size();

        // Update the carrierPlait using partial update
        CarrierPlait partialUpdatedCarrierPlait = new CarrierPlait();
        partialUpdatedCarrierPlait.setId(carrierPlait.getId());

        partialUpdatedCarrierPlait
            .operationLayer(UPDATED_OPERATION_LAYER)
            .minimumDecaNewtonLoad(UPDATED_MINIMUM_DECA_NEWTON_LOAD)
            .degreeAssemblyAngle(UPDATED_DEGREE_ASSEMBLY_ANGLE)
            .forcedEndPerBobinsCount(UPDATED_FORCED_END_PER_BOBINS_COUNT);

        restCarrierPlaitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarrierPlait.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCarrierPlait))
            )
            .andExpect(status().isOk());

        // Validate the CarrierPlait in the database
        List<CarrierPlait> carrierPlaitList = carrierPlaitRepository.findAll();
        assertThat(carrierPlaitList).hasSize(databaseSizeBeforeUpdate);
        CarrierPlait testCarrierPlait = carrierPlaitList.get(carrierPlaitList.size() - 1);
        assertThat(testCarrierPlait.getOperationLayer()).isEqualTo(DEFAULT_OPERATION_LAYER);
        assertThat(testCarrierPlait.getMinimumDecaNewtonLoad()).isEqualTo(UPDATED_MINIMUM_DECA_NEWTON_LOAD);
        assertThat(testCarrierPlait.getDegreeAssemblyAngle()).isEqualTo(UPDATED_DEGREE_ASSEMBLY_ANGLE);
        assertThat(testCarrierPlait.getForcedEndPerBobinsCount()).isEqualTo(UPDATED_FORCED_END_PER_BOBINS_COUNT);
    }

    @Test
    @Transactional
    void patchNonExistingCarrierPlait() throws Exception {
        int databaseSizeBeforeUpdate = carrierPlaitRepository.findAll().size();
        carrierPlait.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarrierPlaitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, carrierPlait.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(carrierPlait))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarrierPlait in the database
        List<CarrierPlait> carrierPlaitList = carrierPlaitRepository.findAll();
        assertThat(carrierPlaitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCarrierPlait() throws Exception {
        int databaseSizeBeforeUpdate = carrierPlaitRepository.findAll().size();
        carrierPlait.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarrierPlaitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(carrierPlait))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarrierPlait in the database
        List<CarrierPlait> carrierPlaitList = carrierPlaitRepository.findAll();
        assertThat(carrierPlaitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCarrierPlait() throws Exception {
        int databaseSizeBeforeUpdate = carrierPlaitRepository.findAll().size();
        carrierPlait.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarrierPlaitMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(carrierPlait))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CarrierPlait in the database
        List<CarrierPlait> carrierPlaitList = carrierPlaitRepository.findAll();
        assertThat(carrierPlaitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCarrierPlait() throws Exception {
        // Initialize the database
        carrierPlaitRepository.saveAndFlush(carrierPlait);

        int databaseSizeBeforeDelete = carrierPlaitRepository.findAll().size();

        // Delete the carrierPlait
        restCarrierPlaitMockMvc
            .perform(delete(ENTITY_API_URL_ID, carrierPlait.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CarrierPlait> carrierPlaitList = carrierPlaitRepository.findAll();
        assertThat(carrierPlaitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
