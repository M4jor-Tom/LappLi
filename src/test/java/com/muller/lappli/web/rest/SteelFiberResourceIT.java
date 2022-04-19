package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.SteelFiber;
import com.muller.lappli.domain.enumeration.MetalFiberKind;
import com.muller.lappli.repository.SteelFiberRepository;
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
 * Integration tests for the {@link SteelFiberResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SteelFiberResourceIT {

    private static final Long DEFAULT_NUMBER = 1L;
    private static final Long UPDATED_NUMBER = 2L;

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final MetalFiberKind DEFAULT_METAL_FIBER_KIND = MetalFiberKind.RED_COPPER;
    private static final MetalFiberKind UPDATED_METAL_FIBER_KIND = MetalFiberKind.TINNED_COPPER;

    private static final Double DEFAULT_MILIMETER_DIAMETER = 1D;
    private static final Double UPDATED_MILIMETER_DIAMETER = 2D;

    private static final String ENTITY_API_URL = "/api/steel-fibers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SteelFiberRepository steelFiberRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSteelFiberMockMvc;

    private SteelFiber steelFiber;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SteelFiber createEntity(EntityManager em) {
        SteelFiber steelFiber = new SteelFiber()
            .number(DEFAULT_NUMBER)
            .designation(DEFAULT_DESIGNATION)
            .metalFiberKind(DEFAULT_METAL_FIBER_KIND)
            .milimeterDiameter(DEFAULT_MILIMETER_DIAMETER);
        return steelFiber;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SteelFiber createUpdatedEntity(EntityManager em) {
        SteelFiber steelFiber = new SteelFiber()
            .number(UPDATED_NUMBER)
            .designation(UPDATED_DESIGNATION)
            .metalFiberKind(UPDATED_METAL_FIBER_KIND)
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER);
        return steelFiber;
    }

    @BeforeEach
    public void initTest() {
        steelFiber = createEntity(em);
    }

    @Test
    @Transactional
    void createSteelFiber() throws Exception {
        int databaseSizeBeforeCreate = steelFiberRepository.findAll().size();
        // Create the SteelFiber
        restSteelFiberMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(steelFiber)))
            .andExpect(status().isCreated());

        // Validate the SteelFiber in the database
        List<SteelFiber> steelFiberList = steelFiberRepository.findAll();
        assertThat(steelFiberList).hasSize(databaseSizeBeforeCreate + 1);
        SteelFiber testSteelFiber = steelFiberList.get(steelFiberList.size() - 1);
        assertThat(testSteelFiber.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testSteelFiber.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testSteelFiber.getMetalFiberKind()).isEqualTo(DEFAULT_METAL_FIBER_KIND);
        assertThat(testSteelFiber.getMilimeterDiameter()).isEqualTo(DEFAULT_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void createSteelFiberWithExistingId() throws Exception {
        // Create the SteelFiber with an existing ID
        steelFiber.setId(1L);

        int databaseSizeBeforeCreate = steelFiberRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSteelFiberMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(steelFiber)))
            .andExpect(status().isBadRequest());

        // Validate the SteelFiber in the database
        List<SteelFiber> steelFiberList = steelFiberRepository.findAll();
        assertThat(steelFiberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMetalFiberKindIsRequired() throws Exception {
        int databaseSizeBeforeTest = steelFiberRepository.findAll().size();
        // set the field null
        steelFiber.setMetalFiberKind(null);

        // Create the SteelFiber, which fails.

        restSteelFiberMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(steelFiber)))
            .andExpect(status().isBadRequest());

        List<SteelFiber> steelFiberList = steelFiberRepository.findAll();
        assertThat(steelFiberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMilimeterDiameterIsRequired() throws Exception {
        int databaseSizeBeforeTest = steelFiberRepository.findAll().size();
        // set the field null
        steelFiber.setMilimeterDiameter(null);

        // Create the SteelFiber, which fails.

        restSteelFiberMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(steelFiber)))
            .andExpect(status().isBadRequest());

        List<SteelFiber> steelFiberList = steelFiberRepository.findAll();
        assertThat(steelFiberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSteelFibers() throws Exception {
        // Initialize the database
        steelFiberRepository.saveAndFlush(steelFiber);

        // Get all the steelFiberList
        restSteelFiberMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(steelFiber.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].metalFiberKind").value(hasItem(DEFAULT_METAL_FIBER_KIND.toString())))
            .andExpect(jsonPath("$.[*].milimeterDiameter").value(hasItem(DEFAULT_MILIMETER_DIAMETER.doubleValue())));
    }

    @Test
    @Transactional
    void getSteelFiber() throws Exception {
        // Initialize the database
        steelFiberRepository.saveAndFlush(steelFiber);

        // Get the steelFiber
        restSteelFiberMockMvc
            .perform(get(ENTITY_API_URL_ID, steelFiber.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(steelFiber.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.intValue()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.metalFiberKind").value(DEFAULT_METAL_FIBER_KIND.toString()))
            .andExpect(jsonPath("$.milimeterDiameter").value(DEFAULT_MILIMETER_DIAMETER.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingSteelFiber() throws Exception {
        // Get the steelFiber
        restSteelFiberMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSteelFiber() throws Exception {
        // Initialize the database
        steelFiberRepository.saveAndFlush(steelFiber);

        int databaseSizeBeforeUpdate = steelFiberRepository.findAll().size();

        // Update the steelFiber
        SteelFiber updatedSteelFiber = steelFiberRepository.findById(steelFiber.getId()).get();
        // Disconnect from session so that the updates on updatedSteelFiber are not directly saved in db
        em.detach(updatedSteelFiber);
        updatedSteelFiber
            .number(UPDATED_NUMBER)
            .designation(UPDATED_DESIGNATION)
            .metalFiberKind(UPDATED_METAL_FIBER_KIND)
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER);

        restSteelFiberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSteelFiber.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSteelFiber))
            )
            .andExpect(status().isOk());

        // Validate the SteelFiber in the database
        List<SteelFiber> steelFiberList = steelFiberRepository.findAll();
        assertThat(steelFiberList).hasSize(databaseSizeBeforeUpdate);
        SteelFiber testSteelFiber = steelFiberList.get(steelFiberList.size() - 1);
        assertThat(testSteelFiber.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testSteelFiber.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testSteelFiber.getMetalFiberKind()).isEqualTo(UPDATED_METAL_FIBER_KIND);
        assertThat(testSteelFiber.getMilimeterDiameter()).isEqualTo(UPDATED_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void putNonExistingSteelFiber() throws Exception {
        int databaseSizeBeforeUpdate = steelFiberRepository.findAll().size();
        steelFiber.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSteelFiberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, steelFiber.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(steelFiber))
            )
            .andExpect(status().isBadRequest());

        // Validate the SteelFiber in the database
        List<SteelFiber> steelFiberList = steelFiberRepository.findAll();
        assertThat(steelFiberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSteelFiber() throws Exception {
        int databaseSizeBeforeUpdate = steelFiberRepository.findAll().size();
        steelFiber.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSteelFiberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(steelFiber))
            )
            .andExpect(status().isBadRequest());

        // Validate the SteelFiber in the database
        List<SteelFiber> steelFiberList = steelFiberRepository.findAll();
        assertThat(steelFiberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSteelFiber() throws Exception {
        int databaseSizeBeforeUpdate = steelFiberRepository.findAll().size();
        steelFiber.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSteelFiberMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(steelFiber)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SteelFiber in the database
        List<SteelFiber> steelFiberList = steelFiberRepository.findAll();
        assertThat(steelFiberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSteelFiberWithPatch() throws Exception {
        // Initialize the database
        steelFiberRepository.saveAndFlush(steelFiber);

        int databaseSizeBeforeUpdate = steelFiberRepository.findAll().size();

        // Update the steelFiber using partial update
        SteelFiber partialUpdatedSteelFiber = new SteelFiber();
        partialUpdatedSteelFiber.setId(steelFiber.getId());

        partialUpdatedSteelFiber.number(UPDATED_NUMBER).metalFiberKind(UPDATED_METAL_FIBER_KIND);

        restSteelFiberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSteelFiber.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSteelFiber))
            )
            .andExpect(status().isOk());

        // Validate the SteelFiber in the database
        List<SteelFiber> steelFiberList = steelFiberRepository.findAll();
        assertThat(steelFiberList).hasSize(databaseSizeBeforeUpdate);
        SteelFiber testSteelFiber = steelFiberList.get(steelFiberList.size() - 1);
        assertThat(testSteelFiber.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testSteelFiber.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testSteelFiber.getMetalFiberKind()).isEqualTo(UPDATED_METAL_FIBER_KIND);
        assertThat(testSteelFiber.getMilimeterDiameter()).isEqualTo(DEFAULT_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void fullUpdateSteelFiberWithPatch() throws Exception {
        // Initialize the database
        steelFiberRepository.saveAndFlush(steelFiber);

        int databaseSizeBeforeUpdate = steelFiberRepository.findAll().size();

        // Update the steelFiber using partial update
        SteelFiber partialUpdatedSteelFiber = new SteelFiber();
        partialUpdatedSteelFiber.setId(steelFiber.getId());

        partialUpdatedSteelFiber
            .number(UPDATED_NUMBER)
            .designation(UPDATED_DESIGNATION)
            .metalFiberKind(UPDATED_METAL_FIBER_KIND)
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER);

        restSteelFiberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSteelFiber.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSteelFiber))
            )
            .andExpect(status().isOk());

        // Validate the SteelFiber in the database
        List<SteelFiber> steelFiberList = steelFiberRepository.findAll();
        assertThat(steelFiberList).hasSize(databaseSizeBeforeUpdate);
        SteelFiber testSteelFiber = steelFiberList.get(steelFiberList.size() - 1);
        assertThat(testSteelFiber.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testSteelFiber.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testSteelFiber.getMetalFiberKind()).isEqualTo(UPDATED_METAL_FIBER_KIND);
        assertThat(testSteelFiber.getMilimeterDiameter()).isEqualTo(UPDATED_MILIMETER_DIAMETER);
    }

    @Test
    @Transactional
    void patchNonExistingSteelFiber() throws Exception {
        int databaseSizeBeforeUpdate = steelFiberRepository.findAll().size();
        steelFiber.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSteelFiberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, steelFiber.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(steelFiber))
            )
            .andExpect(status().isBadRequest());

        // Validate the SteelFiber in the database
        List<SteelFiber> steelFiberList = steelFiberRepository.findAll();
        assertThat(steelFiberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSteelFiber() throws Exception {
        int databaseSizeBeforeUpdate = steelFiberRepository.findAll().size();
        steelFiber.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSteelFiberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(steelFiber))
            )
            .andExpect(status().isBadRequest());

        // Validate the SteelFiber in the database
        List<SteelFiber> steelFiberList = steelFiberRepository.findAll();
        assertThat(steelFiberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSteelFiber() throws Exception {
        int databaseSizeBeforeUpdate = steelFiberRepository.findAll().size();
        steelFiber.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSteelFiberMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(steelFiber))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SteelFiber in the database
        List<SteelFiber> steelFiberList = steelFiberRepository.findAll();
        assertThat(steelFiberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSteelFiber() throws Exception {
        // Initialize the database
        steelFiberRepository.saveAndFlush(steelFiber);

        int databaseSizeBeforeDelete = steelFiberRepository.findAll().size();

        // Delete the steelFiber
        restSteelFiberMockMvc
            .perform(delete(ENTITY_API_URL_ID, steelFiber.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SteelFiber> steelFiberList = steelFiberRepository.findAll();
        assertThat(steelFiberList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
