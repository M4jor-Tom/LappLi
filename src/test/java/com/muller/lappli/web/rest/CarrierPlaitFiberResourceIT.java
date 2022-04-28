package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.CarrierPlaitFiber;
import com.muller.lappli.repository.CarrierPlaitFiberRepository;
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
 * Integration tests for the {@link CarrierPlaitFiberResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CarrierPlaitFiberResourceIT {

    private static final Long DEFAULT_NUMBER = 0L;
    private static final Long UPDATED_NUMBER = 1L;

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final Double DEFAULT_SQUARE_MILIMETER_SECTION = 0D;
    private static final Double UPDATED_SQUARE_MILIMETER_SECTION = 1D;

    private static final Double DEFAULT_DECA_NEWTON_LOAD = 0D;
    private static final Double UPDATED_DECA_NEWTON_LOAD = 1D;

    private static final String ENTITY_API_URL = "/api/carrier-plait-fibers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CarrierPlaitFiberRepository carrierPlaitFiberRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCarrierPlaitFiberMockMvc;

    private CarrierPlaitFiber carrierPlaitFiber;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarrierPlaitFiber createEntity(EntityManager em) {
        CarrierPlaitFiber carrierPlaitFiber = new CarrierPlaitFiber()
            .number(DEFAULT_NUMBER)
            .designation(DEFAULT_DESIGNATION)
            .squareMilimeterSection(DEFAULT_SQUARE_MILIMETER_SECTION)
            .decaNewtonLoad(DEFAULT_DECA_NEWTON_LOAD);
        return carrierPlaitFiber;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarrierPlaitFiber createUpdatedEntity(EntityManager em) {
        CarrierPlaitFiber carrierPlaitFiber = new CarrierPlaitFiber()
            .number(UPDATED_NUMBER)
            .designation(UPDATED_DESIGNATION)
            .squareMilimeterSection(UPDATED_SQUARE_MILIMETER_SECTION)
            .decaNewtonLoad(UPDATED_DECA_NEWTON_LOAD);
        return carrierPlaitFiber;
    }

    @BeforeEach
    public void initTest() {
        carrierPlaitFiber = createEntity(em);
    }

    @Test
    @Transactional
    void createCarrierPlaitFiber() throws Exception {
        int databaseSizeBeforeCreate = carrierPlaitFiberRepository.findAll().size();
        // Create the CarrierPlaitFiber
        restCarrierPlaitFiberMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carrierPlaitFiber))
            )
            .andExpect(status().isCreated());

        // Validate the CarrierPlaitFiber in the database
        List<CarrierPlaitFiber> carrierPlaitFiberList = carrierPlaitFiberRepository.findAll();
        assertThat(carrierPlaitFiberList).hasSize(databaseSizeBeforeCreate + 1);
        CarrierPlaitFiber testCarrierPlaitFiber = carrierPlaitFiberList.get(carrierPlaitFiberList.size() - 1);
        assertThat(testCarrierPlaitFiber.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testCarrierPlaitFiber.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testCarrierPlaitFiber.getSquareMilimeterSection()).isEqualTo(DEFAULT_SQUARE_MILIMETER_SECTION);
        assertThat(testCarrierPlaitFiber.getDecaNewtonLoad()).isEqualTo(DEFAULT_DECA_NEWTON_LOAD);
    }

    @Test
    @Transactional
    void createCarrierPlaitFiberWithExistingId() throws Exception {
        // Create the CarrierPlaitFiber with an existing ID
        carrierPlaitFiber.setId(1L);

        int databaseSizeBeforeCreate = carrierPlaitFiberRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarrierPlaitFiberMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carrierPlaitFiber))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarrierPlaitFiber in the database
        List<CarrierPlaitFiber> carrierPlaitFiberList = carrierPlaitFiberRepository.findAll();
        assertThat(carrierPlaitFiberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSquareMilimeterSectionIsRequired() throws Exception {
        int databaseSizeBeforeTest = carrierPlaitFiberRepository.findAll().size();
        // set the field null
        carrierPlaitFiber.setSquareMilimeterSection(null);

        // Create the CarrierPlaitFiber, which fails.

        restCarrierPlaitFiberMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carrierPlaitFiber))
            )
            .andExpect(status().isBadRequest());

        List<CarrierPlaitFiber> carrierPlaitFiberList = carrierPlaitFiberRepository.findAll();
        assertThat(carrierPlaitFiberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDecaNewtonLoadIsRequired() throws Exception {
        int databaseSizeBeforeTest = carrierPlaitFiberRepository.findAll().size();
        // set the field null
        carrierPlaitFiber.setDecaNewtonLoad(null);

        // Create the CarrierPlaitFiber, which fails.

        restCarrierPlaitFiberMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carrierPlaitFiber))
            )
            .andExpect(status().isBadRequest());

        List<CarrierPlaitFiber> carrierPlaitFiberList = carrierPlaitFiberRepository.findAll();
        assertThat(carrierPlaitFiberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCarrierPlaitFibers() throws Exception {
        // Initialize the database
        carrierPlaitFiberRepository.saveAndFlush(carrierPlaitFiber);

        // Get all the carrierPlaitFiberList
        restCarrierPlaitFiberMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carrierPlaitFiber.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].squareMilimeterSection").value(hasItem(DEFAULT_SQUARE_MILIMETER_SECTION.doubleValue())))
            .andExpect(jsonPath("$.[*].decaNewtonLoad").value(hasItem(DEFAULT_DECA_NEWTON_LOAD.doubleValue())));
    }

    @Test
    @Transactional
    void getCarrierPlaitFiber() throws Exception {
        // Initialize the database
        carrierPlaitFiberRepository.saveAndFlush(carrierPlaitFiber);

        // Get the carrierPlaitFiber
        restCarrierPlaitFiberMockMvc
            .perform(get(ENTITY_API_URL_ID, carrierPlaitFiber.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(carrierPlaitFiber.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.intValue()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.squareMilimeterSection").value(DEFAULT_SQUARE_MILIMETER_SECTION.doubleValue()))
            .andExpect(jsonPath("$.decaNewtonLoad").value(DEFAULT_DECA_NEWTON_LOAD.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingCarrierPlaitFiber() throws Exception {
        // Get the carrierPlaitFiber
        restCarrierPlaitFiberMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCarrierPlaitFiber() throws Exception {
        // Initialize the database
        carrierPlaitFiberRepository.saveAndFlush(carrierPlaitFiber);

        int databaseSizeBeforeUpdate = carrierPlaitFiberRepository.findAll().size();

        // Update the carrierPlaitFiber
        CarrierPlaitFiber updatedCarrierPlaitFiber = carrierPlaitFiberRepository.findById(carrierPlaitFiber.getId()).get();
        // Disconnect from session so that the updates on updatedCarrierPlaitFiber are not directly saved in db
        em.detach(updatedCarrierPlaitFiber);
        updatedCarrierPlaitFiber
            .number(UPDATED_NUMBER)
            .designation(UPDATED_DESIGNATION)
            .squareMilimeterSection(UPDATED_SQUARE_MILIMETER_SECTION)
            .decaNewtonLoad(UPDATED_DECA_NEWTON_LOAD);

        restCarrierPlaitFiberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCarrierPlaitFiber.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCarrierPlaitFiber))
            )
            .andExpect(status().isOk());

        // Validate the CarrierPlaitFiber in the database
        List<CarrierPlaitFiber> carrierPlaitFiberList = carrierPlaitFiberRepository.findAll();
        assertThat(carrierPlaitFiberList).hasSize(databaseSizeBeforeUpdate);
        CarrierPlaitFiber testCarrierPlaitFiber = carrierPlaitFiberList.get(carrierPlaitFiberList.size() - 1);
        assertThat(testCarrierPlaitFiber.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testCarrierPlaitFiber.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testCarrierPlaitFiber.getSquareMilimeterSection()).isEqualTo(UPDATED_SQUARE_MILIMETER_SECTION);
        assertThat(testCarrierPlaitFiber.getDecaNewtonLoad()).isEqualTo(UPDATED_DECA_NEWTON_LOAD);
    }

    @Test
    @Transactional
    void putNonExistingCarrierPlaitFiber() throws Exception {
        int databaseSizeBeforeUpdate = carrierPlaitFiberRepository.findAll().size();
        carrierPlaitFiber.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarrierPlaitFiberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, carrierPlaitFiber.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(carrierPlaitFiber))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarrierPlaitFiber in the database
        List<CarrierPlaitFiber> carrierPlaitFiberList = carrierPlaitFiberRepository.findAll();
        assertThat(carrierPlaitFiberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCarrierPlaitFiber() throws Exception {
        int databaseSizeBeforeUpdate = carrierPlaitFiberRepository.findAll().size();
        carrierPlaitFiber.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarrierPlaitFiberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(carrierPlaitFiber))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarrierPlaitFiber in the database
        List<CarrierPlaitFiber> carrierPlaitFiberList = carrierPlaitFiberRepository.findAll();
        assertThat(carrierPlaitFiberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCarrierPlaitFiber() throws Exception {
        int databaseSizeBeforeUpdate = carrierPlaitFiberRepository.findAll().size();
        carrierPlaitFiber.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarrierPlaitFiberMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carrierPlaitFiber))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CarrierPlaitFiber in the database
        List<CarrierPlaitFiber> carrierPlaitFiberList = carrierPlaitFiberRepository.findAll();
        assertThat(carrierPlaitFiberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCarrierPlaitFiberWithPatch() throws Exception {
        // Initialize the database
        carrierPlaitFiberRepository.saveAndFlush(carrierPlaitFiber);

        int databaseSizeBeforeUpdate = carrierPlaitFiberRepository.findAll().size();

        // Update the carrierPlaitFiber using partial update
        CarrierPlaitFiber partialUpdatedCarrierPlaitFiber = new CarrierPlaitFiber();
        partialUpdatedCarrierPlaitFiber.setId(carrierPlaitFiber.getId());

        partialUpdatedCarrierPlaitFiber.designation(UPDATED_DESIGNATION).decaNewtonLoad(UPDATED_DECA_NEWTON_LOAD);

        restCarrierPlaitFiberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarrierPlaitFiber.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCarrierPlaitFiber))
            )
            .andExpect(status().isOk());

        // Validate the CarrierPlaitFiber in the database
        List<CarrierPlaitFiber> carrierPlaitFiberList = carrierPlaitFiberRepository.findAll();
        assertThat(carrierPlaitFiberList).hasSize(databaseSizeBeforeUpdate);
        CarrierPlaitFiber testCarrierPlaitFiber = carrierPlaitFiberList.get(carrierPlaitFiberList.size() - 1);
        assertThat(testCarrierPlaitFiber.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testCarrierPlaitFiber.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testCarrierPlaitFiber.getSquareMilimeterSection()).isEqualTo(DEFAULT_SQUARE_MILIMETER_SECTION);
        assertThat(testCarrierPlaitFiber.getDecaNewtonLoad()).isEqualTo(UPDATED_DECA_NEWTON_LOAD);
    }

    @Test
    @Transactional
    void fullUpdateCarrierPlaitFiberWithPatch() throws Exception {
        // Initialize the database
        carrierPlaitFiberRepository.saveAndFlush(carrierPlaitFiber);

        int databaseSizeBeforeUpdate = carrierPlaitFiberRepository.findAll().size();

        // Update the carrierPlaitFiber using partial update
        CarrierPlaitFiber partialUpdatedCarrierPlaitFiber = new CarrierPlaitFiber();
        partialUpdatedCarrierPlaitFiber.setId(carrierPlaitFiber.getId());

        partialUpdatedCarrierPlaitFiber
            .number(UPDATED_NUMBER)
            .designation(UPDATED_DESIGNATION)
            .squareMilimeterSection(UPDATED_SQUARE_MILIMETER_SECTION)
            .decaNewtonLoad(UPDATED_DECA_NEWTON_LOAD);

        restCarrierPlaitFiberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarrierPlaitFiber.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCarrierPlaitFiber))
            )
            .andExpect(status().isOk());

        // Validate the CarrierPlaitFiber in the database
        List<CarrierPlaitFiber> carrierPlaitFiberList = carrierPlaitFiberRepository.findAll();
        assertThat(carrierPlaitFiberList).hasSize(databaseSizeBeforeUpdate);
        CarrierPlaitFiber testCarrierPlaitFiber = carrierPlaitFiberList.get(carrierPlaitFiberList.size() - 1);
        assertThat(testCarrierPlaitFiber.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testCarrierPlaitFiber.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testCarrierPlaitFiber.getSquareMilimeterSection()).isEqualTo(UPDATED_SQUARE_MILIMETER_SECTION);
        assertThat(testCarrierPlaitFiber.getDecaNewtonLoad()).isEqualTo(UPDATED_DECA_NEWTON_LOAD);
    }

    @Test
    @Transactional
    void patchNonExistingCarrierPlaitFiber() throws Exception {
        int databaseSizeBeforeUpdate = carrierPlaitFiberRepository.findAll().size();
        carrierPlaitFiber.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarrierPlaitFiberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, carrierPlaitFiber.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(carrierPlaitFiber))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarrierPlaitFiber in the database
        List<CarrierPlaitFiber> carrierPlaitFiberList = carrierPlaitFiberRepository.findAll();
        assertThat(carrierPlaitFiberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCarrierPlaitFiber() throws Exception {
        int databaseSizeBeforeUpdate = carrierPlaitFiberRepository.findAll().size();
        carrierPlaitFiber.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarrierPlaitFiberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(carrierPlaitFiber))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarrierPlaitFiber in the database
        List<CarrierPlaitFiber> carrierPlaitFiberList = carrierPlaitFiberRepository.findAll();
        assertThat(carrierPlaitFiberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCarrierPlaitFiber() throws Exception {
        int databaseSizeBeforeUpdate = carrierPlaitFiberRepository.findAll().size();
        carrierPlaitFiber.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarrierPlaitFiberMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(carrierPlaitFiber))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CarrierPlaitFiber in the database
        List<CarrierPlaitFiber> carrierPlaitFiberList = carrierPlaitFiberRepository.findAll();
        assertThat(carrierPlaitFiberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCarrierPlaitFiber() throws Exception {
        // Initialize the database
        carrierPlaitFiberRepository.saveAndFlush(carrierPlaitFiber);

        int databaseSizeBeforeDelete = carrierPlaitFiberRepository.findAll().size();

        // Delete the carrierPlaitFiber
        restCarrierPlaitFiberMockMvc
            .perform(delete(ENTITY_API_URL_ID, carrierPlaitFiber.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CarrierPlaitFiber> carrierPlaitFiberList = carrierPlaitFiberRepository.findAll();
        assertThat(carrierPlaitFiberList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
