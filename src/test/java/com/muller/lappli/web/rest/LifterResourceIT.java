package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.Lifter;
import com.muller.lappli.repository.LifterRepository;
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
 * Integration tests for the {@link LifterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LifterResourceIT {

    private static final Long DEFAULT_INDEX = 1L;
    private static final Long UPDATED_INDEX = 2L;

    private static final Double DEFAULT_MINIMUM_MILIMETER_DIAMETER = 1D;
    private static final Double UPDATED_MINIMUM_MILIMETER_DIAMETER = 2D;

    private static final Double DEFAULT_MAXIMUM_MILIMETER_DIAMETER = 1D;
    private static final Double UPDATED_MAXIMUM_MILIMETER_DIAMETER = 2D;

    private static final Boolean DEFAULT_SUPPORTS_SPIRALLY_COLORED_MARKING_TYPE = false;
    private static final Boolean UPDATED_SUPPORTS_SPIRALLY_COLORED_MARKING_TYPE = true;

    private static final Boolean DEFAULT_SUPPORTS_LONGITUDINALLY_COLORED_MARKING_TYPE = false;
    private static final Boolean UPDATED_SUPPORTS_LONGITUDINALLY_COLORED_MARKING_TYPE = true;

    private static final Boolean DEFAULT_SUPPORTS_NUMBERED_MARKING_TYPE = false;
    private static final Boolean UPDATED_SUPPORTS_NUMBERED_MARKING_TYPE = true;

    private static final Boolean DEFAULT_SUPPORTS_INK_JET_MARKING_TECHNIQUE = false;
    private static final Boolean UPDATED_SUPPORTS_INK_JET_MARKING_TECHNIQUE = true;

    private static final Boolean DEFAULT_SUPPORTS_RSD_MARKING_TECHNIQUE = false;
    private static final Boolean UPDATED_SUPPORTS_RSD_MARKING_TECHNIQUE = true;

    private static final String ENTITY_API_URL = "/api/lifters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LifterRepository lifterRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLifterMockMvc;

    private Lifter lifter;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lifter createEntity(EntityManager em) {
        Lifter lifter = new Lifter()
            .index(DEFAULT_INDEX)
            .minimumMilimeterDiameter(DEFAULT_MINIMUM_MILIMETER_DIAMETER)
            .maximumMilimeterDiameter(DEFAULT_MAXIMUM_MILIMETER_DIAMETER)
            .supportsSpirallyColoredMarkingType(DEFAULT_SUPPORTS_SPIRALLY_COLORED_MARKING_TYPE)
            .supportsLongitudinallyColoredMarkingType(DEFAULT_SUPPORTS_LONGITUDINALLY_COLORED_MARKING_TYPE)
            .supportsNumberedMarkingType(DEFAULT_SUPPORTS_NUMBERED_MARKING_TYPE)
            .supportsInkJetMarkingTechnique(DEFAULT_SUPPORTS_INK_JET_MARKING_TECHNIQUE)
            .supportsRsdMarkingTechnique(DEFAULT_SUPPORTS_RSD_MARKING_TECHNIQUE);
        return lifter;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lifter createUpdatedEntity(EntityManager em) {
        Lifter lifter = new Lifter()
            .index(UPDATED_INDEX)
            .minimumMilimeterDiameter(UPDATED_MINIMUM_MILIMETER_DIAMETER)
            .maximumMilimeterDiameter(UPDATED_MAXIMUM_MILIMETER_DIAMETER)
            .supportsSpirallyColoredMarkingType(UPDATED_SUPPORTS_SPIRALLY_COLORED_MARKING_TYPE)
            .supportsLongitudinallyColoredMarkingType(UPDATED_SUPPORTS_LONGITUDINALLY_COLORED_MARKING_TYPE)
            .supportsNumberedMarkingType(UPDATED_SUPPORTS_NUMBERED_MARKING_TYPE)
            .supportsInkJetMarkingTechnique(UPDATED_SUPPORTS_INK_JET_MARKING_TECHNIQUE)
            .supportsRsdMarkingTechnique(UPDATED_SUPPORTS_RSD_MARKING_TECHNIQUE);
        return lifter;
    }

    @BeforeEach
    public void initTest() {
        lifter = createEntity(em);
    }

    @Test
    @Transactional
    void createLifter() throws Exception {
        int databaseSizeBeforeCreate = lifterRepository.findAll().size();
        // Create the Lifter
        restLifterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lifter)))
            .andExpect(status().isCreated());

        // Validate the Lifter in the database
        List<Lifter> lifterList = lifterRepository.findAll();
        assertThat(lifterList).hasSize(databaseSizeBeforeCreate + 1);
        Lifter testLifter = lifterList.get(lifterList.size() - 1);
        assertThat(testLifter.getIndex()).isEqualTo(DEFAULT_INDEX);
        assertThat(testLifter.getMinimumMilimeterDiameter()).isEqualTo(DEFAULT_MINIMUM_MILIMETER_DIAMETER);
        assertThat(testLifter.getMaximumMilimeterDiameter()).isEqualTo(DEFAULT_MAXIMUM_MILIMETER_DIAMETER);
        assertThat(testLifter.getSupportsSpirallyColoredMarkingType()).isEqualTo(DEFAULT_SUPPORTS_SPIRALLY_COLORED_MARKING_TYPE);
        assertThat(testLifter.getSupportsLongitudinallyColoredMarkingType())
            .isEqualTo(DEFAULT_SUPPORTS_LONGITUDINALLY_COLORED_MARKING_TYPE);
        assertThat(testLifter.getSupportsNumberedMarkingType()).isEqualTo(DEFAULT_SUPPORTS_NUMBERED_MARKING_TYPE);
        assertThat(testLifter.getSupportsInkJetMarkingTechnique()).isEqualTo(DEFAULT_SUPPORTS_INK_JET_MARKING_TECHNIQUE);
        assertThat(testLifter.getSupportsRsdMarkingTechnique()).isEqualTo(DEFAULT_SUPPORTS_RSD_MARKING_TECHNIQUE);
    }

    @Test
    @Transactional
    void createLifterWithExistingId() throws Exception {
        // Create the Lifter with an existing ID
        lifter.setId(1L);

        int databaseSizeBeforeCreate = lifterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLifterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lifter)))
            .andExpect(status().isBadRequest());

        // Validate the Lifter in the database
        List<Lifter> lifterList = lifterRepository.findAll();
        assertThat(lifterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIndexIsRequired() throws Exception {
        int databaseSizeBeforeTest = lifterRepository.findAll().size();
        // set the field null
        lifter.setIndex(null);

        // Create the Lifter, which fails.

        restLifterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lifter)))
            .andExpect(status().isBadRequest());

        List<Lifter> lifterList = lifterRepository.findAll();
        assertThat(lifterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMinimumMilimeterDiameterIsRequired() throws Exception {
        int databaseSizeBeforeTest = lifterRepository.findAll().size();
        // set the field null
        lifter.setMinimumMilimeterDiameter(null);

        // Create the Lifter, which fails.

        restLifterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lifter)))
            .andExpect(status().isBadRequest());

        List<Lifter> lifterList = lifterRepository.findAll();
        assertThat(lifterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMaximumMilimeterDiameterIsRequired() throws Exception {
        int databaseSizeBeforeTest = lifterRepository.findAll().size();
        // set the field null
        lifter.setMaximumMilimeterDiameter(null);

        // Create the Lifter, which fails.

        restLifterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lifter)))
            .andExpect(status().isBadRequest());

        List<Lifter> lifterList = lifterRepository.findAll();
        assertThat(lifterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSupportsSpirallyColoredMarkingTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = lifterRepository.findAll().size();
        // set the field null
        lifter.setSupportsSpirallyColoredMarkingType(null);

        // Create the Lifter, which fails.

        restLifterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lifter)))
            .andExpect(status().isBadRequest());

        List<Lifter> lifterList = lifterRepository.findAll();
        assertThat(lifterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSupportsLongitudinallyColoredMarkingTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = lifterRepository.findAll().size();
        // set the field null
        lifter.setSupportsLongitudinallyColoredMarkingType(null);

        // Create the Lifter, which fails.

        restLifterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lifter)))
            .andExpect(status().isBadRequest());

        List<Lifter> lifterList = lifterRepository.findAll();
        assertThat(lifterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSupportsNumberedMarkingTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = lifterRepository.findAll().size();
        // set the field null
        lifter.setSupportsNumberedMarkingType(null);

        // Create the Lifter, which fails.

        restLifterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lifter)))
            .andExpect(status().isBadRequest());

        List<Lifter> lifterList = lifterRepository.findAll();
        assertThat(lifterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSupportsInkJetMarkingTechniqueIsRequired() throws Exception {
        int databaseSizeBeforeTest = lifterRepository.findAll().size();
        // set the field null
        lifter.setSupportsInkJetMarkingTechnique(null);

        // Create the Lifter, which fails.

        restLifterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lifter)))
            .andExpect(status().isBadRequest());

        List<Lifter> lifterList = lifterRepository.findAll();
        assertThat(lifterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSupportsRsdMarkingTechniqueIsRequired() throws Exception {
        int databaseSizeBeforeTest = lifterRepository.findAll().size();
        // set the field null
        lifter.setSupportsRsdMarkingTechnique(null);

        // Create the Lifter, which fails.

        restLifterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lifter)))
            .andExpect(status().isBadRequest());

        List<Lifter> lifterList = lifterRepository.findAll();
        assertThat(lifterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLifters() throws Exception {
        // Initialize the database
        lifterRepository.saveAndFlush(lifter);

        // Get all the lifterList
        restLifterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lifter.getId().intValue())))
            .andExpect(jsonPath("$.[*].index").value(hasItem(DEFAULT_INDEX.intValue())))
            .andExpect(jsonPath("$.[*].minimumMilimeterDiameter").value(hasItem(DEFAULT_MINIMUM_MILIMETER_DIAMETER.doubleValue())))
            .andExpect(jsonPath("$.[*].maximumMilimeterDiameter").value(hasItem(DEFAULT_MAXIMUM_MILIMETER_DIAMETER.doubleValue())))
            .andExpect(
                jsonPath("$.[*].supportsSpirallyColoredMarkingType")
                    .value(hasItem(DEFAULT_SUPPORTS_SPIRALLY_COLORED_MARKING_TYPE.booleanValue()))
            )
            .andExpect(
                jsonPath("$.[*].supportsLongitudinallyColoredMarkingType")
                    .value(hasItem(DEFAULT_SUPPORTS_LONGITUDINALLY_COLORED_MARKING_TYPE.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].supportsNumberedMarkingType").value(hasItem(DEFAULT_SUPPORTS_NUMBERED_MARKING_TYPE.booleanValue())))
            .andExpect(
                jsonPath("$.[*].supportsInkJetMarkingTechnique").value(hasItem(DEFAULT_SUPPORTS_INK_JET_MARKING_TECHNIQUE.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].supportsRsdMarkingTechnique").value(hasItem(DEFAULT_SUPPORTS_RSD_MARKING_TECHNIQUE.booleanValue())));
    }

    @Test
    @Transactional
    void getLifter() throws Exception {
        // Initialize the database
        lifterRepository.saveAndFlush(lifter);

        // Get the lifter
        restLifterMockMvc
            .perform(get(ENTITY_API_URL_ID, lifter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lifter.getId().intValue()))
            .andExpect(jsonPath("$.index").value(DEFAULT_INDEX.intValue()))
            .andExpect(jsonPath("$.minimumMilimeterDiameter").value(DEFAULT_MINIMUM_MILIMETER_DIAMETER.doubleValue()))
            .andExpect(jsonPath("$.maximumMilimeterDiameter").value(DEFAULT_MAXIMUM_MILIMETER_DIAMETER.doubleValue()))
            .andExpect(
                jsonPath("$.supportsSpirallyColoredMarkingType").value(DEFAULT_SUPPORTS_SPIRALLY_COLORED_MARKING_TYPE.booleanValue())
            )
            .andExpect(
                jsonPath("$.supportsLongitudinallyColoredMarkingType")
                    .value(DEFAULT_SUPPORTS_LONGITUDINALLY_COLORED_MARKING_TYPE.booleanValue())
            )
            .andExpect(jsonPath("$.supportsNumberedMarkingType").value(DEFAULT_SUPPORTS_NUMBERED_MARKING_TYPE.booleanValue()))
            .andExpect(jsonPath("$.supportsInkJetMarkingTechnique").value(DEFAULT_SUPPORTS_INK_JET_MARKING_TECHNIQUE.booleanValue()))
            .andExpect(jsonPath("$.supportsRsdMarkingTechnique").value(DEFAULT_SUPPORTS_RSD_MARKING_TECHNIQUE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingLifter() throws Exception {
        // Get the lifter
        restLifterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLifter() throws Exception {
        // Initialize the database
        lifterRepository.saveAndFlush(lifter);

        int databaseSizeBeforeUpdate = lifterRepository.findAll().size();

        // Update the lifter
        Lifter updatedLifter = lifterRepository.findById(lifter.getId()).get();
        // Disconnect from session so that the updates on updatedLifter are not directly saved in db
        em.detach(updatedLifter);
        updatedLifter
            .index(UPDATED_INDEX)
            .minimumMilimeterDiameter(UPDATED_MINIMUM_MILIMETER_DIAMETER)
            .maximumMilimeterDiameter(UPDATED_MAXIMUM_MILIMETER_DIAMETER)
            .supportsSpirallyColoredMarkingType(UPDATED_SUPPORTS_SPIRALLY_COLORED_MARKING_TYPE)
            .supportsLongitudinallyColoredMarkingType(UPDATED_SUPPORTS_LONGITUDINALLY_COLORED_MARKING_TYPE)
            .supportsNumberedMarkingType(UPDATED_SUPPORTS_NUMBERED_MARKING_TYPE)
            .supportsInkJetMarkingTechnique(UPDATED_SUPPORTS_INK_JET_MARKING_TECHNIQUE)
            .supportsRsdMarkingTechnique(UPDATED_SUPPORTS_RSD_MARKING_TECHNIQUE);

        restLifterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLifter.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLifter))
            )
            .andExpect(status().isOk());

        // Validate the Lifter in the database
        List<Lifter> lifterList = lifterRepository.findAll();
        assertThat(lifterList).hasSize(databaseSizeBeforeUpdate);
        Lifter testLifter = lifterList.get(lifterList.size() - 1);
        assertThat(testLifter.getIndex()).isEqualTo(UPDATED_INDEX);
        assertThat(testLifter.getMinimumMilimeterDiameter()).isEqualTo(UPDATED_MINIMUM_MILIMETER_DIAMETER);
        assertThat(testLifter.getMaximumMilimeterDiameter()).isEqualTo(UPDATED_MAXIMUM_MILIMETER_DIAMETER);
        assertThat(testLifter.getSupportsSpirallyColoredMarkingType()).isEqualTo(UPDATED_SUPPORTS_SPIRALLY_COLORED_MARKING_TYPE);
        assertThat(testLifter.getSupportsLongitudinallyColoredMarkingType())
            .isEqualTo(UPDATED_SUPPORTS_LONGITUDINALLY_COLORED_MARKING_TYPE);
        assertThat(testLifter.getSupportsNumberedMarkingType()).isEqualTo(UPDATED_SUPPORTS_NUMBERED_MARKING_TYPE);
        assertThat(testLifter.getSupportsInkJetMarkingTechnique()).isEqualTo(UPDATED_SUPPORTS_INK_JET_MARKING_TECHNIQUE);
        assertThat(testLifter.getSupportsRsdMarkingTechnique()).isEqualTo(UPDATED_SUPPORTS_RSD_MARKING_TECHNIQUE);
    }

    @Test
    @Transactional
    void putNonExistingLifter() throws Exception {
        int databaseSizeBeforeUpdate = lifterRepository.findAll().size();
        lifter.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLifterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, lifter.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lifter))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lifter in the database
        List<Lifter> lifterList = lifterRepository.findAll();
        assertThat(lifterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLifter() throws Exception {
        int databaseSizeBeforeUpdate = lifterRepository.findAll().size();
        lifter.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLifterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lifter))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lifter in the database
        List<Lifter> lifterList = lifterRepository.findAll();
        assertThat(lifterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLifter() throws Exception {
        int databaseSizeBeforeUpdate = lifterRepository.findAll().size();
        lifter.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLifterMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lifter)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Lifter in the database
        List<Lifter> lifterList = lifterRepository.findAll();
        assertThat(lifterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLifterWithPatch() throws Exception {
        // Initialize the database
        lifterRepository.saveAndFlush(lifter);

        int databaseSizeBeforeUpdate = lifterRepository.findAll().size();

        // Update the lifter using partial update
        Lifter partialUpdatedLifter = new Lifter();
        partialUpdatedLifter.setId(lifter.getId());

        partialUpdatedLifter
            .index(UPDATED_INDEX)
            .supportsLongitudinallyColoredMarkingType(UPDATED_SUPPORTS_LONGITUDINALLY_COLORED_MARKING_TYPE)
            .supportsNumberedMarkingType(UPDATED_SUPPORTS_NUMBERED_MARKING_TYPE)
            .supportsRsdMarkingTechnique(UPDATED_SUPPORTS_RSD_MARKING_TECHNIQUE);

        restLifterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLifter.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLifter))
            )
            .andExpect(status().isOk());

        // Validate the Lifter in the database
        List<Lifter> lifterList = lifterRepository.findAll();
        assertThat(lifterList).hasSize(databaseSizeBeforeUpdate);
        Lifter testLifter = lifterList.get(lifterList.size() - 1);
        assertThat(testLifter.getIndex()).isEqualTo(UPDATED_INDEX);
        assertThat(testLifter.getMinimumMilimeterDiameter()).isEqualTo(DEFAULT_MINIMUM_MILIMETER_DIAMETER);
        assertThat(testLifter.getMaximumMilimeterDiameter()).isEqualTo(DEFAULT_MAXIMUM_MILIMETER_DIAMETER);
        assertThat(testLifter.getSupportsSpirallyColoredMarkingType()).isEqualTo(DEFAULT_SUPPORTS_SPIRALLY_COLORED_MARKING_TYPE);
        assertThat(testLifter.getSupportsLongitudinallyColoredMarkingType())
            .isEqualTo(UPDATED_SUPPORTS_LONGITUDINALLY_COLORED_MARKING_TYPE);
        assertThat(testLifter.getSupportsNumberedMarkingType()).isEqualTo(UPDATED_SUPPORTS_NUMBERED_MARKING_TYPE);
        assertThat(testLifter.getSupportsInkJetMarkingTechnique()).isEqualTo(DEFAULT_SUPPORTS_INK_JET_MARKING_TECHNIQUE);
        assertThat(testLifter.getSupportsRsdMarkingTechnique()).isEqualTo(UPDATED_SUPPORTS_RSD_MARKING_TECHNIQUE);
    }

    @Test
    @Transactional
    void fullUpdateLifterWithPatch() throws Exception {
        // Initialize the database
        lifterRepository.saveAndFlush(lifter);

        int databaseSizeBeforeUpdate = lifterRepository.findAll().size();

        // Update the lifter using partial update
        Lifter partialUpdatedLifter = new Lifter();
        partialUpdatedLifter.setId(lifter.getId());

        partialUpdatedLifter
            .index(UPDATED_INDEX)
            .minimumMilimeterDiameter(UPDATED_MINIMUM_MILIMETER_DIAMETER)
            .maximumMilimeterDiameter(UPDATED_MAXIMUM_MILIMETER_DIAMETER)
            .supportsSpirallyColoredMarkingType(UPDATED_SUPPORTS_SPIRALLY_COLORED_MARKING_TYPE)
            .supportsLongitudinallyColoredMarkingType(UPDATED_SUPPORTS_LONGITUDINALLY_COLORED_MARKING_TYPE)
            .supportsNumberedMarkingType(UPDATED_SUPPORTS_NUMBERED_MARKING_TYPE)
            .supportsInkJetMarkingTechnique(UPDATED_SUPPORTS_INK_JET_MARKING_TECHNIQUE)
            .supportsRsdMarkingTechnique(UPDATED_SUPPORTS_RSD_MARKING_TECHNIQUE);

        restLifterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLifter.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLifter))
            )
            .andExpect(status().isOk());

        // Validate the Lifter in the database
        List<Lifter> lifterList = lifterRepository.findAll();
        assertThat(lifterList).hasSize(databaseSizeBeforeUpdate);
        Lifter testLifter = lifterList.get(lifterList.size() - 1);
        assertThat(testLifter.getIndex()).isEqualTo(UPDATED_INDEX);
        assertThat(testLifter.getMinimumMilimeterDiameter()).isEqualTo(UPDATED_MINIMUM_MILIMETER_DIAMETER);
        assertThat(testLifter.getMaximumMilimeterDiameter()).isEqualTo(UPDATED_MAXIMUM_MILIMETER_DIAMETER);
        assertThat(testLifter.getSupportsSpirallyColoredMarkingType()).isEqualTo(UPDATED_SUPPORTS_SPIRALLY_COLORED_MARKING_TYPE);
        assertThat(testLifter.getSupportsLongitudinallyColoredMarkingType())
            .isEqualTo(UPDATED_SUPPORTS_LONGITUDINALLY_COLORED_MARKING_TYPE);
        assertThat(testLifter.getSupportsNumberedMarkingType()).isEqualTo(UPDATED_SUPPORTS_NUMBERED_MARKING_TYPE);
        assertThat(testLifter.getSupportsInkJetMarkingTechnique()).isEqualTo(UPDATED_SUPPORTS_INK_JET_MARKING_TECHNIQUE);
        assertThat(testLifter.getSupportsRsdMarkingTechnique()).isEqualTo(UPDATED_SUPPORTS_RSD_MARKING_TECHNIQUE);
    }

    @Test
    @Transactional
    void patchNonExistingLifter() throws Exception {
        int databaseSizeBeforeUpdate = lifterRepository.findAll().size();
        lifter.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLifterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, lifter.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lifter))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lifter in the database
        List<Lifter> lifterList = lifterRepository.findAll();
        assertThat(lifterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLifter() throws Exception {
        int databaseSizeBeforeUpdate = lifterRepository.findAll().size();
        lifter.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLifterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lifter))
            )
            .andExpect(status().isBadRequest());

        // Validate the Lifter in the database
        List<Lifter> lifterList = lifterRepository.findAll();
        assertThat(lifterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLifter() throws Exception {
        int databaseSizeBeforeUpdate = lifterRepository.findAll().size();
        lifter.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLifterMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(lifter)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Lifter in the database
        List<Lifter> lifterList = lifterRepository.findAll();
        assertThat(lifterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLifter() throws Exception {
        // Initialize the database
        lifterRepository.saveAndFlush(lifter);

        int databaseSizeBeforeDelete = lifterRepository.findAll().size();

        // Delete the lifter
        restLifterMockMvc
            .perform(delete(ENTITY_API_URL_ID, lifter.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Lifter> lifterList = lifterRepository.findAll();
        assertThat(lifterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
