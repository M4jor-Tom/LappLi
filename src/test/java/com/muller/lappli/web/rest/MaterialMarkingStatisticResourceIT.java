package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.Material;
import com.muller.lappli.domain.MaterialMarkingStatistic;
import com.muller.lappli.domain.enumeration.MarkingTechnique;
import com.muller.lappli.domain.enumeration.MarkingType;
import com.muller.lappli.repository.MaterialMarkingStatisticRepository;
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
 * Integration tests for the {@link MaterialMarkingStatisticResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MaterialMarkingStatisticResourceIT {

    private static final MarkingType DEFAULT_MARKING_TYPE = MarkingType.LIFTING;
    private static final MarkingType UPDATED_MARKING_TYPE = MarkingType.SPIRALLY_COLORED;

    private static final MarkingTechnique DEFAULT_MARKING_TECHNIQUE = MarkingTechnique.NONE;
    private static final MarkingTechnique UPDATED_MARKING_TECHNIQUE = MarkingTechnique.NONE_SUITABLE;

    private static final Long DEFAULT_METER_PER_HOUR_SPEED = 1L;
    private static final Long UPDATED_METER_PER_HOUR_SPEED = 2L;

    private static final String ENTITY_API_URL = "/api/material-marking-statistics";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MaterialMarkingStatisticRepository materialMarkingStatisticRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMaterialMarkingStatisticMockMvc;

    private MaterialMarkingStatistic materialMarkingStatistic;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MaterialMarkingStatistic createEntity(EntityManager em) {
        MaterialMarkingStatistic materialMarkingStatistic = new MaterialMarkingStatistic()
            .markingType(DEFAULT_MARKING_TYPE)
            .markingTechnique(DEFAULT_MARKING_TECHNIQUE)
            .meterPerHourSpeed(DEFAULT_METER_PER_HOUR_SPEED);
        // Add required entity
        Material material;
        if (TestUtil.findAll(em, Material.class).isEmpty()) {
            material = MaterialResourceIT.createEntity(em);
            em.persist(material);
            em.flush();
        } else {
            material = TestUtil.findAll(em, Material.class).get(0);
        }
        materialMarkingStatistic.setMaterial(material);
        return materialMarkingStatistic;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MaterialMarkingStatistic createUpdatedEntity(EntityManager em) {
        MaterialMarkingStatistic materialMarkingStatistic = new MaterialMarkingStatistic()
            .markingType(UPDATED_MARKING_TYPE)
            .markingTechnique(UPDATED_MARKING_TECHNIQUE)
            .meterPerHourSpeed(UPDATED_METER_PER_HOUR_SPEED);
        // Add required entity
        Material material;
        if (TestUtil.findAll(em, Material.class).isEmpty()) {
            material = MaterialResourceIT.createUpdatedEntity(em);
            em.persist(material);
            em.flush();
        } else {
            material = TestUtil.findAll(em, Material.class).get(0);
        }
        materialMarkingStatistic.setMaterial(material);
        return materialMarkingStatistic;
    }

    @BeforeEach
    public void initTest() {
        materialMarkingStatistic = createEntity(em);
    }

    @Test
    @Transactional
    void createMaterialMarkingStatistic() throws Exception {
        int databaseSizeBeforeCreate = materialMarkingStatisticRepository.findAll().size();
        // Create the MaterialMarkingStatistic
        restMaterialMarkingStatisticMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(materialMarkingStatistic))
            )
            .andExpect(status().isCreated());

        // Validate the MaterialMarkingStatistic in the database
        List<MaterialMarkingStatistic> materialMarkingStatisticList = materialMarkingStatisticRepository.findAll();
        assertThat(materialMarkingStatisticList).hasSize(databaseSizeBeforeCreate + 1);
        MaterialMarkingStatistic testMaterialMarkingStatistic = materialMarkingStatisticList.get(materialMarkingStatisticList.size() - 1);
        assertThat(testMaterialMarkingStatistic.getMarkingType()).isEqualTo(DEFAULT_MARKING_TYPE);
        assertThat(testMaterialMarkingStatistic.getMarkingTechnique()).isEqualTo(DEFAULT_MARKING_TECHNIQUE);
        assertThat(testMaterialMarkingStatistic.getMeterPerHourSpeed()).isEqualTo(DEFAULT_METER_PER_HOUR_SPEED);
    }

    @Test
    @Transactional
    void createMaterialMarkingStatisticWithExistingId() throws Exception {
        // Create the MaterialMarkingStatistic with an existing ID
        materialMarkingStatistic.setId(1L);

        int databaseSizeBeforeCreate = materialMarkingStatisticRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaterialMarkingStatisticMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(materialMarkingStatistic))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaterialMarkingStatistic in the database
        List<MaterialMarkingStatistic> materialMarkingStatisticList = materialMarkingStatisticRepository.findAll();
        assertThat(materialMarkingStatisticList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMarkingTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = materialMarkingStatisticRepository.findAll().size();
        // set the field null
        materialMarkingStatistic.setMarkingType(null);

        // Create the MaterialMarkingStatistic, which fails.

        restMaterialMarkingStatisticMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(materialMarkingStatistic))
            )
            .andExpect(status().isBadRequest());

        List<MaterialMarkingStatistic> materialMarkingStatisticList = materialMarkingStatisticRepository.findAll();
        assertThat(materialMarkingStatisticList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMarkingTechniqueIsRequired() throws Exception {
        int databaseSizeBeforeTest = materialMarkingStatisticRepository.findAll().size();
        // set the field null
        materialMarkingStatistic.setMarkingTechnique(null);

        // Create the MaterialMarkingStatistic, which fails.

        restMaterialMarkingStatisticMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(materialMarkingStatistic))
            )
            .andExpect(status().isBadRequest());

        List<MaterialMarkingStatistic> materialMarkingStatisticList = materialMarkingStatisticRepository.findAll();
        assertThat(materialMarkingStatisticList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMeterPerHourSpeedIsRequired() throws Exception {
        int databaseSizeBeforeTest = materialMarkingStatisticRepository.findAll().size();
        // set the field null
        materialMarkingStatistic.setMeterPerHourSpeed(null);

        // Create the MaterialMarkingStatistic, which fails.

        restMaterialMarkingStatisticMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(materialMarkingStatistic))
            )
            .andExpect(status().isBadRequest());

        List<MaterialMarkingStatistic> materialMarkingStatisticList = materialMarkingStatisticRepository.findAll();
        assertThat(materialMarkingStatisticList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMaterialMarkingStatistics() throws Exception {
        // Initialize the database
        materialMarkingStatisticRepository.saveAndFlush(materialMarkingStatistic);

        // Get all the materialMarkingStatisticList
        restMaterialMarkingStatisticMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materialMarkingStatistic.getId().intValue())))
            .andExpect(jsonPath("$.[*].markingType").value(hasItem(DEFAULT_MARKING_TYPE.toString())))
            .andExpect(jsonPath("$.[*].markingTechnique").value(hasItem(DEFAULT_MARKING_TECHNIQUE.toString())))
            .andExpect(jsonPath("$.[*].meterPerHourSpeed").value(hasItem(DEFAULT_METER_PER_HOUR_SPEED.intValue())));
    }

    @Test
    @Transactional
    void getMaterialMarkingStatistic() throws Exception {
        // Initialize the database
        materialMarkingStatisticRepository.saveAndFlush(materialMarkingStatistic);

        // Get the materialMarkingStatistic
        restMaterialMarkingStatisticMockMvc
            .perform(get(ENTITY_API_URL_ID, materialMarkingStatistic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(materialMarkingStatistic.getId().intValue()))
            .andExpect(jsonPath("$.markingType").value(DEFAULT_MARKING_TYPE.toString()))
            .andExpect(jsonPath("$.markingTechnique").value(DEFAULT_MARKING_TECHNIQUE.toString()))
            .andExpect(jsonPath("$.meterPerHourSpeed").value(DEFAULT_METER_PER_HOUR_SPEED.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingMaterialMarkingStatistic() throws Exception {
        // Get the materialMarkingStatistic
        restMaterialMarkingStatisticMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMaterialMarkingStatistic() throws Exception {
        // Initialize the database
        materialMarkingStatisticRepository.saveAndFlush(materialMarkingStatistic);

        int databaseSizeBeforeUpdate = materialMarkingStatisticRepository.findAll().size();

        // Update the materialMarkingStatistic
        MaterialMarkingStatistic updatedMaterialMarkingStatistic = materialMarkingStatisticRepository
            .findById(materialMarkingStatistic.getId())
            .get();
        // Disconnect from session so that the updates on updatedMaterialMarkingStatistic are not directly saved in db
        em.detach(updatedMaterialMarkingStatistic);
        updatedMaterialMarkingStatistic
            .markingType(UPDATED_MARKING_TYPE)
            .markingTechnique(UPDATED_MARKING_TECHNIQUE)
            .meterPerHourSpeed(UPDATED_METER_PER_HOUR_SPEED);

        restMaterialMarkingStatisticMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMaterialMarkingStatistic.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMaterialMarkingStatistic))
            )
            .andExpect(status().isOk());

        // Validate the MaterialMarkingStatistic in the database
        List<MaterialMarkingStatistic> materialMarkingStatisticList = materialMarkingStatisticRepository.findAll();
        assertThat(materialMarkingStatisticList).hasSize(databaseSizeBeforeUpdate);
        MaterialMarkingStatistic testMaterialMarkingStatistic = materialMarkingStatisticList.get(materialMarkingStatisticList.size() - 1);
        assertThat(testMaterialMarkingStatistic.getMarkingType()).isEqualTo(UPDATED_MARKING_TYPE);
        assertThat(testMaterialMarkingStatistic.getMarkingTechnique()).isEqualTo(UPDATED_MARKING_TECHNIQUE);
        assertThat(testMaterialMarkingStatistic.getMeterPerHourSpeed()).isEqualTo(UPDATED_METER_PER_HOUR_SPEED);
    }

    @Test
    @Transactional
    void putNonExistingMaterialMarkingStatistic() throws Exception {
        int databaseSizeBeforeUpdate = materialMarkingStatisticRepository.findAll().size();
        materialMarkingStatistic.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaterialMarkingStatisticMockMvc
            .perform(
                put(ENTITY_API_URL_ID, materialMarkingStatistic.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(materialMarkingStatistic))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaterialMarkingStatistic in the database
        List<MaterialMarkingStatistic> materialMarkingStatisticList = materialMarkingStatisticRepository.findAll();
        assertThat(materialMarkingStatisticList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMaterialMarkingStatistic() throws Exception {
        int databaseSizeBeforeUpdate = materialMarkingStatisticRepository.findAll().size();
        materialMarkingStatistic.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialMarkingStatisticMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(materialMarkingStatistic))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaterialMarkingStatistic in the database
        List<MaterialMarkingStatistic> materialMarkingStatisticList = materialMarkingStatisticRepository.findAll();
        assertThat(materialMarkingStatisticList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMaterialMarkingStatistic() throws Exception {
        int databaseSizeBeforeUpdate = materialMarkingStatisticRepository.findAll().size();
        materialMarkingStatistic.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialMarkingStatisticMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(materialMarkingStatistic))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MaterialMarkingStatistic in the database
        List<MaterialMarkingStatistic> materialMarkingStatisticList = materialMarkingStatisticRepository.findAll();
        assertThat(materialMarkingStatisticList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMaterialMarkingStatisticWithPatch() throws Exception {
        // Initialize the database
        materialMarkingStatisticRepository.saveAndFlush(materialMarkingStatistic);

        int databaseSizeBeforeUpdate = materialMarkingStatisticRepository.findAll().size();

        // Update the materialMarkingStatistic using partial update
        MaterialMarkingStatistic partialUpdatedMaterialMarkingStatistic = new MaterialMarkingStatistic();
        partialUpdatedMaterialMarkingStatistic.setId(materialMarkingStatistic.getId());

        partialUpdatedMaterialMarkingStatistic.markingTechnique(UPDATED_MARKING_TECHNIQUE);

        restMaterialMarkingStatisticMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMaterialMarkingStatistic.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMaterialMarkingStatistic))
            )
            .andExpect(status().isOk());

        // Validate the MaterialMarkingStatistic in the database
        List<MaterialMarkingStatistic> materialMarkingStatisticList = materialMarkingStatisticRepository.findAll();
        assertThat(materialMarkingStatisticList).hasSize(databaseSizeBeforeUpdate);
        MaterialMarkingStatistic testMaterialMarkingStatistic = materialMarkingStatisticList.get(materialMarkingStatisticList.size() - 1);
        assertThat(testMaterialMarkingStatistic.getMarkingType()).isEqualTo(DEFAULT_MARKING_TYPE);
        assertThat(testMaterialMarkingStatistic.getMarkingTechnique()).isEqualTo(UPDATED_MARKING_TECHNIQUE);
        assertThat(testMaterialMarkingStatistic.getMeterPerHourSpeed()).isEqualTo(DEFAULT_METER_PER_HOUR_SPEED);
    }

    @Test
    @Transactional
    void fullUpdateMaterialMarkingStatisticWithPatch() throws Exception {
        // Initialize the database
        materialMarkingStatisticRepository.saveAndFlush(materialMarkingStatistic);

        int databaseSizeBeforeUpdate = materialMarkingStatisticRepository.findAll().size();

        // Update the materialMarkingStatistic using partial update
        MaterialMarkingStatistic partialUpdatedMaterialMarkingStatistic = new MaterialMarkingStatistic();
        partialUpdatedMaterialMarkingStatistic.setId(materialMarkingStatistic.getId());

        partialUpdatedMaterialMarkingStatistic
            .markingType(UPDATED_MARKING_TYPE)
            .markingTechnique(UPDATED_MARKING_TECHNIQUE)
            .meterPerHourSpeed(UPDATED_METER_PER_HOUR_SPEED);

        restMaterialMarkingStatisticMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMaterialMarkingStatistic.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMaterialMarkingStatistic))
            )
            .andExpect(status().isOk());

        // Validate the MaterialMarkingStatistic in the database
        List<MaterialMarkingStatistic> materialMarkingStatisticList = materialMarkingStatisticRepository.findAll();
        assertThat(materialMarkingStatisticList).hasSize(databaseSizeBeforeUpdate);
        MaterialMarkingStatistic testMaterialMarkingStatistic = materialMarkingStatisticList.get(materialMarkingStatisticList.size() - 1);
        assertThat(testMaterialMarkingStatistic.getMarkingType()).isEqualTo(UPDATED_MARKING_TYPE);
        assertThat(testMaterialMarkingStatistic.getMarkingTechnique()).isEqualTo(UPDATED_MARKING_TECHNIQUE);
        assertThat(testMaterialMarkingStatistic.getMeterPerHourSpeed()).isEqualTo(UPDATED_METER_PER_HOUR_SPEED);
    }

    @Test
    @Transactional
    void patchNonExistingMaterialMarkingStatistic() throws Exception {
        int databaseSizeBeforeUpdate = materialMarkingStatisticRepository.findAll().size();
        materialMarkingStatistic.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaterialMarkingStatisticMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, materialMarkingStatistic.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(materialMarkingStatistic))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaterialMarkingStatistic in the database
        List<MaterialMarkingStatistic> materialMarkingStatisticList = materialMarkingStatisticRepository.findAll();
        assertThat(materialMarkingStatisticList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMaterialMarkingStatistic() throws Exception {
        int databaseSizeBeforeUpdate = materialMarkingStatisticRepository.findAll().size();
        materialMarkingStatistic.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialMarkingStatisticMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(materialMarkingStatistic))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaterialMarkingStatistic in the database
        List<MaterialMarkingStatistic> materialMarkingStatisticList = materialMarkingStatisticRepository.findAll();
        assertThat(materialMarkingStatisticList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMaterialMarkingStatistic() throws Exception {
        int databaseSizeBeforeUpdate = materialMarkingStatisticRepository.findAll().size();
        materialMarkingStatistic.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialMarkingStatisticMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(materialMarkingStatistic))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MaterialMarkingStatistic in the database
        List<MaterialMarkingStatistic> materialMarkingStatisticList = materialMarkingStatisticRepository.findAll();
        assertThat(materialMarkingStatisticList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMaterialMarkingStatistic() throws Exception {
        // Initialize the database
        materialMarkingStatisticRepository.saveAndFlush(materialMarkingStatistic);

        int databaseSizeBeforeDelete = materialMarkingStatisticRepository.findAll().size();

        // Delete the materialMarkingStatistic
        restMaterialMarkingStatisticMockMvc
            .perform(delete(ENTITY_API_URL_ID, materialMarkingStatistic.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MaterialMarkingStatistic> materialMarkingStatisticList = materialMarkingStatisticRepository.findAll();
        assertThat(materialMarkingStatisticList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
