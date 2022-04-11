package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.MetalFiber;
import com.muller.lappli.domain.enumeration.MetalFiberKind;
import com.muller.lappli.repository.MetalFiberRepository;
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
 * Integration tests for the {@link MetalFiberResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MetalFiberResourceIT {

    private static final Long DEFAULT_NUMBER = 1L;
    private static final Long UPDATED_NUMBER = 2L;

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final MetalFiberKind DEFAULT_METAL_FIBER_KIND = MetalFiberKind.RED_COPPER;
    private static final MetalFiberKind UPDATED_METAL_FIBER_KIND = MetalFiberKind.TINNED_COPPER;

    private static final Double DEFAULT_MILIMETER_DIAMETER = 1D;
    private static final Double UPDATED_MILIMETER_DIAMETER = 2D;

    private static final String ENTITY_API_URL = "/api/metal-fibers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MetalFiberRepository metalFiberRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMetalFiberMockMvc;

    private MetalFiber metalFiber;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MetalFiber createEntity(EntityManager em) {
        MetalFiber metalFiber = new MetalFiber()
            .number(DEFAULT_NUMBER)
            .designation(DEFAULT_DESIGNATION)
            .metalFiberKind(DEFAULT_METAL_FIBER_KIND)
            .milimeterDiameter(DEFAULT_MILIMETER_DIAMETER);
        return metalFiber;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MetalFiber createUpdatedEntity(EntityManager em) {
        MetalFiber metalFiber = new MetalFiber()
            .number(UPDATED_NUMBER)
            .designation(UPDATED_DESIGNATION)
            .metalFiberKind(UPDATED_METAL_FIBER_KIND)
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER);
        return metalFiber;
    }

    @BeforeEach
    public void initTest() {
        metalFiber = createEntity(em);
    }

    @Test
    @Transactional
    void createMetalFiber() throws Exception {
        int databaseSizeBeforeCreate = metalFiberRepository.findAll().size();
        // Create the MetalFiber
        restMetalFiberMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metalFiber)))
            .andExpect(status().isCreated());

        // Validate the MetalFiber in the database
        List<MetalFiber> metalFiberList = metalFiberRepository.findAll();
        assertThat(metalFiberList).hasSize(databaseSizeBeforeCreate + 1);
        MetalFiber testMetalFiber = metalFiberList.get(metalFiberList.size() - 1);
        assertThat(testMetalFiber.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testMetalFiber.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testMetalFiber.getMetalFiberKind()).isEqualTo(DEFAULT_METAL_FIBER_KIND);
        assertThat(testMetalFiber.getMilimeterDiameter()).isEqualTo(DEFAULT_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void createMetalFiberWithExistingId() throws Exception {
        // Create the MetalFiber with an existing ID
        metalFiber.setId(1L);

        int databaseSizeBeforeCreate = metalFiberRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMetalFiberMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metalFiber)))
            .andExpect(status().isBadRequest());

        // Validate the MetalFiber in the database
        List<MetalFiber> metalFiberList = metalFiberRepository.findAll();
        assertThat(metalFiberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMetalFiberKindIsRequired() throws Exception {
        int databaseSizeBeforeTest = metalFiberRepository.findAll().size();
        // set the field null
        metalFiber.setMetalFiberKind(null);

        // Create the MetalFiber, which fails.

        restMetalFiberMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metalFiber)))
            .andExpect(status().isBadRequest());

        List<MetalFiber> metalFiberList = metalFiberRepository.findAll();
        assertThat(metalFiberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMilimeterDiameterIsRequired() throws Exception {
        int databaseSizeBeforeTest = metalFiberRepository.findAll().size();
        // set the field null
        metalFiber.setMilimeterDiameter(null);

        // Create the MetalFiber, which fails.

        restMetalFiberMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metalFiber)))
            .andExpect(status().isBadRequest());

        List<MetalFiber> metalFiberList = metalFiberRepository.findAll();
        assertThat(metalFiberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMetalFibers() throws Exception {
        // Initialize the database
        metalFiberRepository.saveAndFlush(metalFiber);

        // Get all the metalFiberList
        restMetalFiberMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(metalFiber.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].metalFiberKind").value(hasItem(DEFAULT_METAL_FIBER_KIND.toString())))
            .andExpect(jsonPath("$.[*].milimeterDiameter").value(hasItem(DEFAULT_MILIMETER_DIAMETER.doubleValue())));
    }

    @Test
    @Transactional
    void getMetalFiber() throws Exception {
        // Initialize the database
        metalFiberRepository.saveAndFlush(metalFiber);

        // Get the metalFiber
        restMetalFiberMockMvc
            .perform(get(ENTITY_API_URL_ID, metalFiber.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(metalFiber.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.intValue()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.metalFiberKind").value(DEFAULT_METAL_FIBER_KIND.toString()))
            .andExpect(jsonPath("$.milimeterDiameter").value(DEFAULT_MILIMETER_DIAMETER.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingMetalFiber() throws Exception {
        // Get the metalFiber
        restMetalFiberMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMetalFiber() throws Exception {
        // Initialize the database
        metalFiberRepository.saveAndFlush(metalFiber);

        int databaseSizeBeforeUpdate = metalFiberRepository.findAll().size();

        // Update the metalFiber
        MetalFiber updatedMetalFiber = metalFiberRepository.findById(metalFiber.getId()).get();
        // Disconnect from session so that the updates on updatedMetalFiber are not directly saved in db
        em.detach(updatedMetalFiber);
        updatedMetalFiber
            .number(UPDATED_NUMBER)
            .designation(UPDATED_DESIGNATION)
            .metalFiberKind(UPDATED_METAL_FIBER_KIND)
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER);

        restMetalFiberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMetalFiber.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMetalFiber))
            )
            .andExpect(status().isOk());

        // Validate the MetalFiber in the database
        List<MetalFiber> metalFiberList = metalFiberRepository.findAll();
        assertThat(metalFiberList).hasSize(databaseSizeBeforeUpdate);
        MetalFiber testMetalFiber = metalFiberList.get(metalFiberList.size() - 1);
        assertThat(testMetalFiber.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testMetalFiber.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testMetalFiber.getMetalFiberKind()).isEqualTo(UPDATED_METAL_FIBER_KIND);
        assertThat(testMetalFiber.getMilimeterDiameter()).isEqualTo(UPDATED_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void putNonExistingMetalFiber() throws Exception {
        int databaseSizeBeforeUpdate = metalFiberRepository.findAll().size();
        metalFiber.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMetalFiberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, metalFiber.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(metalFiber))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetalFiber in the database
        List<MetalFiber> metalFiberList = metalFiberRepository.findAll();
        assertThat(metalFiberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMetalFiber() throws Exception {
        int databaseSizeBeforeUpdate = metalFiberRepository.findAll().size();
        metalFiber.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetalFiberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(metalFiber))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetalFiber in the database
        List<MetalFiber> metalFiberList = metalFiberRepository.findAll();
        assertThat(metalFiberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMetalFiber() throws Exception {
        int databaseSizeBeforeUpdate = metalFiberRepository.findAll().size();
        metalFiber.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetalFiberMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(metalFiber)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MetalFiber in the database
        List<MetalFiber> metalFiberList = metalFiberRepository.findAll();
        assertThat(metalFiberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMetalFiberWithPatch() throws Exception {
        // Initialize the database
        metalFiberRepository.saveAndFlush(metalFiber);

        int databaseSizeBeforeUpdate = metalFiberRepository.findAll().size();

        // Update the metalFiber using partial update
        MetalFiber partialUpdatedMetalFiber = new MetalFiber();
        partialUpdatedMetalFiber.setId(metalFiber.getId());

        restMetalFiberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMetalFiber.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMetalFiber))
            )
            .andExpect(status().isOk());

        // Validate the MetalFiber in the database
        List<MetalFiber> metalFiberList = metalFiberRepository.findAll();
        assertThat(metalFiberList).hasSize(databaseSizeBeforeUpdate);
        MetalFiber testMetalFiber = metalFiberList.get(metalFiberList.size() - 1);
        assertThat(testMetalFiber.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testMetalFiber.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testMetalFiber.getMetalFiberKind()).isEqualTo(DEFAULT_METAL_FIBER_KIND);
        assertThat(testMetalFiber.getMilimeterDiameter()).isEqualTo(DEFAULT_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void fullUpdateMetalFiberWithPatch() throws Exception {
        // Initialize the database
        metalFiberRepository.saveAndFlush(metalFiber);

        int databaseSizeBeforeUpdate = metalFiberRepository.findAll().size();

        // Update the metalFiber using partial update
        MetalFiber partialUpdatedMetalFiber = new MetalFiber();
        partialUpdatedMetalFiber.setId(metalFiber.getId());

        partialUpdatedMetalFiber
            .number(UPDATED_NUMBER)
            .designation(UPDATED_DESIGNATION)
            .metalFiberKind(UPDATED_METAL_FIBER_KIND)
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER);

        restMetalFiberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMetalFiber.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMetalFiber))
            )
            .andExpect(status().isOk());

        // Validate the MetalFiber in the database
        List<MetalFiber> metalFiberList = metalFiberRepository.findAll();
        assertThat(metalFiberList).hasSize(databaseSizeBeforeUpdate);
        MetalFiber testMetalFiber = metalFiberList.get(metalFiberList.size() - 1);
        assertThat(testMetalFiber.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testMetalFiber.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testMetalFiber.getMetalFiberKind()).isEqualTo(UPDATED_METAL_FIBER_KIND);
        assertThat(testMetalFiber.getMilimeterDiameter()).isEqualTo(UPDATED_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void patchNonExistingMetalFiber() throws Exception {
        int databaseSizeBeforeUpdate = metalFiberRepository.findAll().size();
        metalFiber.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMetalFiberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, metalFiber.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(metalFiber))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetalFiber in the database
        List<MetalFiber> metalFiberList = metalFiberRepository.findAll();
        assertThat(metalFiberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMetalFiber() throws Exception {
        int databaseSizeBeforeUpdate = metalFiberRepository.findAll().size();
        metalFiber.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetalFiberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(metalFiber))
            )
            .andExpect(status().isBadRequest());

        // Validate the MetalFiber in the database
        List<MetalFiber> metalFiberList = metalFiberRepository.findAll();
        assertThat(metalFiberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMetalFiber() throws Exception {
        int databaseSizeBeforeUpdate = metalFiberRepository.findAll().size();
        metalFiber.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMetalFiberMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(metalFiber))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MetalFiber in the database
        List<MetalFiber> metalFiberList = metalFiberRepository.findAll();
        assertThat(metalFiberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMetalFiber() throws Exception {
        // Initialize the database
        metalFiberRepository.saveAndFlush(metalFiber);

        int databaseSizeBeforeDelete = metalFiberRepository.findAll().size();

        // Delete the metalFiber
        restMetalFiberMockMvc
            .perform(delete(ENTITY_API_URL_ID, metalFiber.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MetalFiber> metalFiberList = metalFiberRepository.findAll();
        assertThat(metalFiberList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
