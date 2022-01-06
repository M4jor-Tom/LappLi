package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.Lifter;
import com.muller.lappli.domain.LifterRunMeasure;
import com.muller.lappli.domain.enumeration.MarkingTechnique;
import com.muller.lappli.domain.enumeration.MarkingType;
import com.muller.lappli.repository.LifterRunMeasureRepository;
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
 * Integration tests for the {@link LifterRunMeasureResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LifterRunMeasureResourceIT {

    private static final Double DEFAULT_MILIMETER_DIAMETER = 1D;
    private static final Double UPDATED_MILIMETER_DIAMETER = 2D;

    private static final Double DEFAULT_METER_PER_HOUR_SPEED = 1D;
    private static final Double UPDATED_METER_PER_HOUR_SPEED = 2D;

    private static final MarkingType DEFAULT_MARKING_TYPE = MarkingType.LIFTING;
    private static final MarkingType UPDATED_MARKING_TYPE = MarkingType.SPIRALLY_COLORED;

    private static final MarkingTechnique DEFAULT_MARKING_TECHNIQUE = MarkingTechnique.NONE;
    private static final MarkingTechnique UPDATED_MARKING_TECHNIQUE = MarkingTechnique.NONE_SUITABLE;

    private static final Double DEFAULT_HOUR_PREPARATION_TIME = 1D;
    private static final Double UPDATED_HOUR_PREPARATION_TIME = 2D;

    private static final String ENTITY_API_URL = "/api/lifter-run-measures";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LifterRunMeasureRepository lifterRunMeasureRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLifterRunMeasureMockMvc;

    private LifterRunMeasure lifterRunMeasure;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LifterRunMeasure createEntity(EntityManager em) {
        LifterRunMeasure lifterRunMeasure = new LifterRunMeasure()
            .milimeterDiameter(DEFAULT_MILIMETER_DIAMETER)
            .meterPerHourSpeed(DEFAULT_METER_PER_HOUR_SPEED)
            .markingType(DEFAULT_MARKING_TYPE)
            .markingTechnique(DEFAULT_MARKING_TECHNIQUE)
            .hourPreparationTime(DEFAULT_HOUR_PREPARATION_TIME);
        // Add required entity
        Lifter lifter;
        if (TestUtil.findAll(em, Lifter.class).isEmpty()) {
            lifter = LifterResourceIT.createEntity(em);
            em.persist(lifter);
            em.flush();
        } else {
            lifter = TestUtil.findAll(em, Lifter.class).get(0);
        }
        lifterRunMeasure.setLifter(lifter);
        return lifterRunMeasure;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LifterRunMeasure createUpdatedEntity(EntityManager em) {
        LifterRunMeasure lifterRunMeasure = new LifterRunMeasure()
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER)
            .meterPerHourSpeed(UPDATED_METER_PER_HOUR_SPEED)
            .markingType(UPDATED_MARKING_TYPE)
            .markingTechnique(UPDATED_MARKING_TECHNIQUE)
            .hourPreparationTime(UPDATED_HOUR_PREPARATION_TIME);
        // Add required entity
        Lifter lifter;
        if (TestUtil.findAll(em, Lifter.class).isEmpty()) {
            lifter = LifterResourceIT.createUpdatedEntity(em);
            em.persist(lifter);
            em.flush();
        } else {
            lifter = TestUtil.findAll(em, Lifter.class).get(0);
        }
        lifterRunMeasure.setLifter(lifter);
        return lifterRunMeasure;
    }

    @BeforeEach
    public void initTest() {
        lifterRunMeasure = createEntity(em);
    }

    @Test
    @Transactional
    void createLifterRunMeasure() throws Exception {
        int databaseSizeBeforeCreate = lifterRunMeasureRepository.findAll().size();
        // Create the LifterRunMeasure
        restLifterRunMeasureMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lifterRunMeasure))
            )
            .andExpect(status().isCreated());

        // Validate the LifterRunMeasure in the database
        List<LifterRunMeasure> lifterRunMeasureList = lifterRunMeasureRepository.findAll();
        assertThat(lifterRunMeasureList).hasSize(databaseSizeBeforeCreate + 1);
        LifterRunMeasure testLifterRunMeasure = lifterRunMeasureList.get(lifterRunMeasureList.size() - 1);
        assertThat(testLifterRunMeasure.getMilimeterDiameter()).isEqualTo(DEFAULT_MILIMETER_DIAMETER);
        assertThat(testLifterRunMeasure.getMeterPerHourSpeed()).isEqualTo(DEFAULT_METER_PER_HOUR_SPEED);
        assertThat(testLifterRunMeasure.getMarkingType()).isEqualTo(DEFAULT_MARKING_TYPE);
        assertThat(testLifterRunMeasure.getMarkingTechnique()).isEqualTo(DEFAULT_MARKING_TECHNIQUE);
        assertThat(testLifterRunMeasure.getHourPreparationTime()).isEqualTo(DEFAULT_HOUR_PREPARATION_TIME);
    }

    @Test
    @Transactional
    void createLifterRunMeasureWithExistingId() throws Exception {
        // Create the LifterRunMeasure with an existing ID
        lifterRunMeasure.setId(1L);

        int databaseSizeBeforeCreate = lifterRunMeasureRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLifterRunMeasureMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lifterRunMeasure))
            )
            .andExpect(status().isBadRequest());

        // Validate the LifterRunMeasure in the database
        List<LifterRunMeasure> lifterRunMeasureList = lifterRunMeasureRepository.findAll();
        assertThat(lifterRunMeasureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMarkingTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = lifterRunMeasureRepository.findAll().size();
        // set the field null
        lifterRunMeasure.setMarkingType(null);

        // Create the LifterRunMeasure, which fails.

        restLifterRunMeasureMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lifterRunMeasure))
            )
            .andExpect(status().isBadRequest());

        List<LifterRunMeasure> lifterRunMeasureList = lifterRunMeasureRepository.findAll();
        assertThat(lifterRunMeasureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMarkingTechniqueIsRequired() throws Exception {
        int databaseSizeBeforeTest = lifterRunMeasureRepository.findAll().size();
        // set the field null
        lifterRunMeasure.setMarkingTechnique(null);

        // Create the LifterRunMeasure, which fails.

        restLifterRunMeasureMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lifterRunMeasure))
            )
            .andExpect(status().isBadRequest());

        List<LifterRunMeasure> lifterRunMeasureList = lifterRunMeasureRepository.findAll();
        assertThat(lifterRunMeasureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLifterRunMeasures() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        // Get all the lifterRunMeasureList
        restLifterRunMeasureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lifterRunMeasure.getId().intValue())))
            .andExpect(jsonPath("$.[*].milimeterDiameter").value(hasItem(DEFAULT_MILIMETER_DIAMETER.doubleValue())))
            .andExpect(jsonPath("$.[*].meterPerHourSpeed").value(hasItem(DEFAULT_METER_PER_HOUR_SPEED.doubleValue())))
            .andExpect(jsonPath("$.[*].markingType").value(hasItem(DEFAULT_MARKING_TYPE.toString())))
            .andExpect(jsonPath("$.[*].markingTechnique").value(hasItem(DEFAULT_MARKING_TECHNIQUE.toString())))
            .andExpect(jsonPath("$.[*].hourPreparationTime").value(hasItem(DEFAULT_HOUR_PREPARATION_TIME.doubleValue())));
    }

    @Test
    @Transactional
    void getLifterRunMeasure() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        // Get the lifterRunMeasure
        restLifterRunMeasureMockMvc
            .perform(get(ENTITY_API_URL_ID, lifterRunMeasure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lifterRunMeasure.getId().intValue()))
            .andExpect(jsonPath("$.milimeterDiameter").value(DEFAULT_MILIMETER_DIAMETER.doubleValue()))
            .andExpect(jsonPath("$.meterPerHourSpeed").value(DEFAULT_METER_PER_HOUR_SPEED.doubleValue()))
            .andExpect(jsonPath("$.markingType").value(DEFAULT_MARKING_TYPE.toString()))
            .andExpect(jsonPath("$.markingTechnique").value(DEFAULT_MARKING_TECHNIQUE.toString()))
            .andExpect(jsonPath("$.hourPreparationTime").value(DEFAULT_HOUR_PREPARATION_TIME.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingLifterRunMeasure() throws Exception {
        // Get the lifterRunMeasure
        restLifterRunMeasureMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLifterRunMeasure() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        int databaseSizeBeforeUpdate = lifterRunMeasureRepository.findAll().size();

        // Update the lifterRunMeasure
        LifterRunMeasure updatedLifterRunMeasure = lifterRunMeasureRepository.findById(lifterRunMeasure.getId()).get();
        // Disconnect from session so that the updates on updatedLifterRunMeasure are not directly saved in db
        em.detach(updatedLifterRunMeasure);
        updatedLifterRunMeasure
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER)
            .meterPerHourSpeed(UPDATED_METER_PER_HOUR_SPEED)
            .markingType(UPDATED_MARKING_TYPE)
            .markingTechnique(UPDATED_MARKING_TECHNIQUE)
            .hourPreparationTime(UPDATED_HOUR_PREPARATION_TIME);

        restLifterRunMeasureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLifterRunMeasure.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLifterRunMeasure))
            )
            .andExpect(status().isOk());

        // Validate the LifterRunMeasure in the database
        List<LifterRunMeasure> lifterRunMeasureList = lifterRunMeasureRepository.findAll();
        assertThat(lifterRunMeasureList).hasSize(databaseSizeBeforeUpdate);
        LifterRunMeasure testLifterRunMeasure = lifterRunMeasureList.get(lifterRunMeasureList.size() - 1);
        assertThat(testLifterRunMeasure.getMilimeterDiameter()).isEqualTo(UPDATED_MILIMETER_DIAMETER);
        assertThat(testLifterRunMeasure.getMeterPerHourSpeed()).isEqualTo(UPDATED_METER_PER_HOUR_SPEED);
        assertThat(testLifterRunMeasure.getMarkingType()).isEqualTo(UPDATED_MARKING_TYPE);
        assertThat(testLifterRunMeasure.getMarkingTechnique()).isEqualTo(UPDATED_MARKING_TECHNIQUE);
        assertThat(testLifterRunMeasure.getHourPreparationTime()).isEqualTo(UPDATED_HOUR_PREPARATION_TIME);
    }

    @Test
    @Transactional
    void putNonExistingLifterRunMeasure() throws Exception {
        int databaseSizeBeforeUpdate = lifterRunMeasureRepository.findAll().size();
        lifterRunMeasure.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLifterRunMeasureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, lifterRunMeasure.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lifterRunMeasure))
            )
            .andExpect(status().isBadRequest());

        // Validate the LifterRunMeasure in the database
        List<LifterRunMeasure> lifterRunMeasureList = lifterRunMeasureRepository.findAll();
        assertThat(lifterRunMeasureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLifterRunMeasure() throws Exception {
        int databaseSizeBeforeUpdate = lifterRunMeasureRepository.findAll().size();
        lifterRunMeasure.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLifterRunMeasureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lifterRunMeasure))
            )
            .andExpect(status().isBadRequest());

        // Validate the LifterRunMeasure in the database
        List<LifterRunMeasure> lifterRunMeasureList = lifterRunMeasureRepository.findAll();
        assertThat(lifterRunMeasureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLifterRunMeasure() throws Exception {
        int databaseSizeBeforeUpdate = lifterRunMeasureRepository.findAll().size();
        lifterRunMeasure.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLifterRunMeasureMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lifterRunMeasure))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LifterRunMeasure in the database
        List<LifterRunMeasure> lifterRunMeasureList = lifterRunMeasureRepository.findAll();
        assertThat(lifterRunMeasureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLifterRunMeasureWithPatch() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        int databaseSizeBeforeUpdate = lifterRunMeasureRepository.findAll().size();

        // Update the lifterRunMeasure using partial update
        LifterRunMeasure partialUpdatedLifterRunMeasure = new LifterRunMeasure();
        partialUpdatedLifterRunMeasure.setId(lifterRunMeasure.getId());

        partialUpdatedLifterRunMeasure
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER)
            .meterPerHourSpeed(UPDATED_METER_PER_HOUR_SPEED)
            .markingType(UPDATED_MARKING_TYPE)
            .hourPreparationTime(UPDATED_HOUR_PREPARATION_TIME);

        restLifterRunMeasureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLifterRunMeasure.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLifterRunMeasure))
            )
            .andExpect(status().isOk());

        // Validate the LifterRunMeasure in the database
        List<LifterRunMeasure> lifterRunMeasureList = lifterRunMeasureRepository.findAll();
        assertThat(lifterRunMeasureList).hasSize(databaseSizeBeforeUpdate);
        LifterRunMeasure testLifterRunMeasure = lifterRunMeasureList.get(lifterRunMeasureList.size() - 1);
        assertThat(testLifterRunMeasure.getMilimeterDiameter()).isEqualTo(UPDATED_MILIMETER_DIAMETER);
        assertThat(testLifterRunMeasure.getMeterPerHourSpeed()).isEqualTo(UPDATED_METER_PER_HOUR_SPEED);
        assertThat(testLifterRunMeasure.getMarkingType()).isEqualTo(UPDATED_MARKING_TYPE);
        assertThat(testLifterRunMeasure.getMarkingTechnique()).isEqualTo(DEFAULT_MARKING_TECHNIQUE);
        assertThat(testLifterRunMeasure.getHourPreparationTime()).isEqualTo(UPDATED_HOUR_PREPARATION_TIME);
    }

    @Test
    @Transactional
    void fullUpdateLifterRunMeasureWithPatch() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        int databaseSizeBeforeUpdate = lifterRunMeasureRepository.findAll().size();

        // Update the lifterRunMeasure using partial update
        LifterRunMeasure partialUpdatedLifterRunMeasure = new LifterRunMeasure();
        partialUpdatedLifterRunMeasure.setId(lifterRunMeasure.getId());

        partialUpdatedLifterRunMeasure
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER)
            .meterPerHourSpeed(UPDATED_METER_PER_HOUR_SPEED)
            .markingType(UPDATED_MARKING_TYPE)
            .markingTechnique(UPDATED_MARKING_TECHNIQUE)
            .hourPreparationTime(UPDATED_HOUR_PREPARATION_TIME);

        restLifterRunMeasureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLifterRunMeasure.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLifterRunMeasure))
            )
            .andExpect(status().isOk());

        // Validate the LifterRunMeasure in the database
        List<LifterRunMeasure> lifterRunMeasureList = lifterRunMeasureRepository.findAll();
        assertThat(lifterRunMeasureList).hasSize(databaseSizeBeforeUpdate);
        LifterRunMeasure testLifterRunMeasure = lifterRunMeasureList.get(lifterRunMeasureList.size() - 1);
        assertThat(testLifterRunMeasure.getMilimeterDiameter()).isEqualTo(UPDATED_MILIMETER_DIAMETER);
        assertThat(testLifterRunMeasure.getMeterPerHourSpeed()).isEqualTo(UPDATED_METER_PER_HOUR_SPEED);
        assertThat(testLifterRunMeasure.getMarkingType()).isEqualTo(UPDATED_MARKING_TYPE);
        assertThat(testLifterRunMeasure.getMarkingTechnique()).isEqualTo(UPDATED_MARKING_TECHNIQUE);
        assertThat(testLifterRunMeasure.getHourPreparationTime()).isEqualTo(UPDATED_HOUR_PREPARATION_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingLifterRunMeasure() throws Exception {
        int databaseSizeBeforeUpdate = lifterRunMeasureRepository.findAll().size();
        lifterRunMeasure.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLifterRunMeasureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, lifterRunMeasure.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lifterRunMeasure))
            )
            .andExpect(status().isBadRequest());

        // Validate the LifterRunMeasure in the database
        List<LifterRunMeasure> lifterRunMeasureList = lifterRunMeasureRepository.findAll();
        assertThat(lifterRunMeasureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLifterRunMeasure() throws Exception {
        int databaseSizeBeforeUpdate = lifterRunMeasureRepository.findAll().size();
        lifterRunMeasure.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLifterRunMeasureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lifterRunMeasure))
            )
            .andExpect(status().isBadRequest());

        // Validate the LifterRunMeasure in the database
        List<LifterRunMeasure> lifterRunMeasureList = lifterRunMeasureRepository.findAll();
        assertThat(lifterRunMeasureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLifterRunMeasure() throws Exception {
        int databaseSizeBeforeUpdate = lifterRunMeasureRepository.findAll().size();
        lifterRunMeasure.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLifterRunMeasureMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lifterRunMeasure))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LifterRunMeasure in the database
        List<LifterRunMeasure> lifterRunMeasureList = lifterRunMeasureRepository.findAll();
        assertThat(lifterRunMeasureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLifterRunMeasure() throws Exception {
        // Initialize the database
        lifterRunMeasureRepository.saveAndFlush(lifterRunMeasure);

        int databaseSizeBeforeDelete = lifterRunMeasureRepository.findAll().size();

        // Delete the lifterRunMeasure
        restLifterRunMeasureMockMvc
            .perform(delete(ENTITY_API_URL_ID, lifterRunMeasure.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LifterRunMeasure> lifterRunMeasureList = lifterRunMeasureRepository.findAll();
        assertThat(lifterRunMeasureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
