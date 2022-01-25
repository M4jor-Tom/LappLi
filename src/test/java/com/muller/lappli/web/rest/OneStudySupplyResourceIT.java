package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.Material;
import com.muller.lappli.domain.OneStudySupply;
import com.muller.lappli.domain.SupplyPosition;
import com.muller.lappli.domain.enumeration.Color;
import com.muller.lappli.domain.enumeration.MarkingType;
import com.muller.lappli.repository.OneStudySupplyRepository;
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
 * Integration tests for the {@link OneStudySupplyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OneStudySupplyResourceIT {

    private static final Long DEFAULT_APPARITIONS = 1L;
    private static final Long UPDATED_APPARITIONS = 2L;

    private static final Long DEFAULT_NUMBER = 1L;
    private static final Long UPDATED_NUMBER = 2L;

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final MarkingType DEFAULT_MARKING_TYPE = MarkingType.LIFTING;
    private static final MarkingType UPDATED_MARKING_TYPE = MarkingType.SPIRALLY_COLORED;

    private static final Double DEFAULT_GRAM_PER_METER_LINEAR_MASS = 1D;
    private static final Double UPDATED_GRAM_PER_METER_LINEAR_MASS = 2D;

    private static final Double DEFAULT_MILIMETER_DIAMETER = 1D;
    private static final Double UPDATED_MILIMETER_DIAMETER = 2D;

    private static final Color DEFAULT_SURFACE_COLOR = Color.NATURAL;
    private static final Color UPDATED_SURFACE_COLOR = Color.WHITE;

    private static final String ENTITY_API_URL = "/api/one-study-supplies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OneStudySupplyRepository oneStudySupplyRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOneStudySupplyMockMvc;

    private OneStudySupply oneStudySupply;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OneStudySupply createEntity(EntityManager em) {
        OneStudySupply oneStudySupply = new OneStudySupply()
            .apparitions(DEFAULT_APPARITIONS)
            .number(DEFAULT_NUMBER)
            .designation(DEFAULT_DESIGNATION)
            .description(DEFAULT_DESCRIPTION)
            .markingType(DEFAULT_MARKING_TYPE)
            .gramPerMeterLinearMass(DEFAULT_GRAM_PER_METER_LINEAR_MASS)
            .milimeterDiameter(DEFAULT_MILIMETER_DIAMETER)
            .surfaceColor(DEFAULT_SURFACE_COLOR);
        // Add required entity
        SupplyPosition supplyPosition;
        if (TestUtil.findAll(em, SupplyPosition.class).isEmpty()) {
            supplyPosition = SupplyPositionResourceIT.createEntity(em);
            em.persist(supplyPosition);
            em.flush();
        } else {
            supplyPosition = TestUtil.findAll(em, SupplyPosition.class).get(0);
        }
        oneStudySupply.getOwnerSupplyPositions().add(supplyPosition);
        // Add required entity
        Material material;
        if (TestUtil.findAll(em, Material.class).isEmpty()) {
            material = MaterialResourceIT.createEntity(em);
            em.persist(material);
            em.flush();
        } else {
            material = TestUtil.findAll(em, Material.class).get(0);
        }
        oneStudySupply.setSurfaceMaterial(material);
        return oneStudySupply;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OneStudySupply createUpdatedEntity(EntityManager em) {
        OneStudySupply oneStudySupply = new OneStudySupply()
            .apparitions(UPDATED_APPARITIONS)
            .number(UPDATED_NUMBER)
            .designation(UPDATED_DESIGNATION)
            .description(UPDATED_DESCRIPTION)
            .markingType(UPDATED_MARKING_TYPE)
            .gramPerMeterLinearMass(UPDATED_GRAM_PER_METER_LINEAR_MASS)
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER)
            .surfaceColor(UPDATED_SURFACE_COLOR);
        // Add required entity
        SupplyPosition supplyPosition;
        if (TestUtil.findAll(em, SupplyPosition.class).isEmpty()) {
            supplyPosition = SupplyPositionResourceIT.createUpdatedEntity(em);
            em.persist(supplyPosition);
            em.flush();
        } else {
            supplyPosition = TestUtil.findAll(em, SupplyPosition.class).get(0);
        }
        oneStudySupply.getOwnerSupplyPositions().add(supplyPosition);
        // Add required entity
        Material material;
        if (TestUtil.findAll(em, Material.class).isEmpty()) {
            material = MaterialResourceIT.createUpdatedEntity(em);
            em.persist(material);
            em.flush();
        } else {
            material = TestUtil.findAll(em, Material.class).get(0);
        }
        oneStudySupply.setSurfaceMaterial(material);
        return oneStudySupply;
    }

    @BeforeEach
    public void initTest() {
        oneStudySupply = createEntity(em);
    }

    @Test
    @Transactional
    void createOneStudySupply() throws Exception {
        int databaseSizeBeforeCreate = oneStudySupplyRepository.findAll().size();
        // Create the OneStudySupply
        restOneStudySupplyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oneStudySupply))
            )
            .andExpect(status().isCreated());

        // Validate the OneStudySupply in the database
        List<OneStudySupply> oneStudySupplyList = oneStudySupplyRepository.findAll();
        assertThat(oneStudySupplyList).hasSize(databaseSizeBeforeCreate + 1);
        OneStudySupply testOneStudySupply = oneStudySupplyList.get(oneStudySupplyList.size() - 1);
        assertThat(testOneStudySupply.getApparitions()).isEqualTo(DEFAULT_APPARITIONS);
        assertThat(testOneStudySupply.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testOneStudySupply.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testOneStudySupply.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOneStudySupply.getMarkingType()).isEqualTo(DEFAULT_MARKING_TYPE);
        assertThat(testOneStudySupply.getGramPerMeterLinearMass()).isEqualTo(DEFAULT_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testOneStudySupply.getMilimeterDiameter()).isEqualTo(DEFAULT_MILIMETER_DIAMETER);
        assertThat(testOneStudySupply.getSurfaceColor()).isEqualTo(DEFAULT_SURFACE_COLOR);
    }

    @Test
    @Transactional
    void createOneStudySupplyWithExistingId() throws Exception {
        // Create the OneStudySupply with an existing ID
        oneStudySupply.setId(1L);

        int databaseSizeBeforeCreate = oneStudySupplyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOneStudySupplyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oneStudySupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the OneStudySupply in the database
        List<OneStudySupply> oneStudySupplyList = oneStudySupplyRepository.findAll();
        assertThat(oneStudySupplyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMarkingTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = oneStudySupplyRepository.findAll().size();
        // set the field null
        oneStudySupply.setMarkingType(null);

        // Create the OneStudySupply, which fails.

        restOneStudySupplyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oneStudySupply))
            )
            .andExpect(status().isBadRequest());

        List<OneStudySupply> oneStudySupplyList = oneStudySupplyRepository.findAll();
        assertThat(oneStudySupplyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGramPerMeterLinearMassIsRequired() throws Exception {
        int databaseSizeBeforeTest = oneStudySupplyRepository.findAll().size();
        // set the field null
        oneStudySupply.setGramPerMeterLinearMass(null);

        // Create the OneStudySupply, which fails.

        restOneStudySupplyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oneStudySupply))
            )
            .andExpect(status().isBadRequest());

        List<OneStudySupply> oneStudySupplyList = oneStudySupplyRepository.findAll();
        assertThat(oneStudySupplyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMilimeterDiameterIsRequired() throws Exception {
        int databaseSizeBeforeTest = oneStudySupplyRepository.findAll().size();
        // set the field null
        oneStudySupply.setMilimeterDiameter(null);

        // Create the OneStudySupply, which fails.

        restOneStudySupplyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oneStudySupply))
            )
            .andExpect(status().isBadRequest());

        List<OneStudySupply> oneStudySupplyList = oneStudySupplyRepository.findAll();
        assertThat(oneStudySupplyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSurfaceColorIsRequired() throws Exception {
        int databaseSizeBeforeTest = oneStudySupplyRepository.findAll().size();
        // set the field null
        oneStudySupply.setSurfaceColor(null);

        // Create the OneStudySupply, which fails.

        restOneStudySupplyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oneStudySupply))
            )
            .andExpect(status().isBadRequest());

        List<OneStudySupply> oneStudySupplyList = oneStudySupplyRepository.findAll();
        assertThat(oneStudySupplyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOneStudySupplies() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get all the oneStudySupplyList
        restOneStudySupplyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(oneStudySupply.getId().intValue())))
            .andExpect(jsonPath("$.[*].apparitions").value(hasItem(DEFAULT_APPARITIONS.intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].markingType").value(hasItem(DEFAULT_MARKING_TYPE.toString())))
            .andExpect(jsonPath("$.[*].gramPerMeterLinearMass").value(hasItem(DEFAULT_GRAM_PER_METER_LINEAR_MASS.doubleValue())))
            .andExpect(jsonPath("$.[*].milimeterDiameter").value(hasItem(DEFAULT_MILIMETER_DIAMETER.doubleValue())))
            .andExpect(jsonPath("$.[*].surfaceColor").value(hasItem(DEFAULT_SURFACE_COLOR.toString())));
    }

    @Test
    @Transactional
    void getOneStudySupply() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        // Get the oneStudySupply
        restOneStudySupplyMockMvc
            .perform(get(ENTITY_API_URL_ID, oneStudySupply.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(oneStudySupply.getId().intValue()))
            .andExpect(jsonPath("$.apparitions").value(DEFAULT_APPARITIONS.intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.intValue()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.markingType").value(DEFAULT_MARKING_TYPE.toString()))
            .andExpect(jsonPath("$.gramPerMeterLinearMass").value(DEFAULT_GRAM_PER_METER_LINEAR_MASS.doubleValue()))
            .andExpect(jsonPath("$.milimeterDiameter").value(DEFAULT_MILIMETER_DIAMETER.doubleValue()))
            .andExpect(jsonPath("$.surfaceColor").value(DEFAULT_SURFACE_COLOR.toString()));
    }

    @Test
    @Transactional
    void getNonExistingOneStudySupply() throws Exception {
        // Get the oneStudySupply
        restOneStudySupplyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOneStudySupply() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        int databaseSizeBeforeUpdate = oneStudySupplyRepository.findAll().size();

        // Update the oneStudySupply
        OneStudySupply updatedOneStudySupply = oneStudySupplyRepository.findById(oneStudySupply.getId()).get();
        // Disconnect from session so that the updates on updatedOneStudySupply are not directly saved in db
        em.detach(updatedOneStudySupply);
        updatedOneStudySupply
            .apparitions(UPDATED_APPARITIONS)
            .number(UPDATED_NUMBER)
            .designation(UPDATED_DESIGNATION)
            .description(UPDATED_DESCRIPTION)
            .markingType(UPDATED_MARKING_TYPE)
            .gramPerMeterLinearMass(UPDATED_GRAM_PER_METER_LINEAR_MASS)
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER)
            .surfaceColor(UPDATED_SURFACE_COLOR);

        restOneStudySupplyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOneStudySupply.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOneStudySupply))
            )
            .andExpect(status().isOk());

        // Validate the OneStudySupply in the database
        List<OneStudySupply> oneStudySupplyList = oneStudySupplyRepository.findAll();
        assertThat(oneStudySupplyList).hasSize(databaseSizeBeforeUpdate);
        OneStudySupply testOneStudySupply = oneStudySupplyList.get(oneStudySupplyList.size() - 1);
        assertThat(testOneStudySupply.getApparitions()).isEqualTo(UPDATED_APPARITIONS);
        assertThat(testOneStudySupply.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testOneStudySupply.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testOneStudySupply.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOneStudySupply.getMarkingType()).isEqualTo(UPDATED_MARKING_TYPE);
        assertThat(testOneStudySupply.getGramPerMeterLinearMass()).isEqualTo(UPDATED_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testOneStudySupply.getMilimeterDiameter()).isEqualTo(UPDATED_MILIMETER_DIAMETER);
        assertThat(testOneStudySupply.getSurfaceColor()).isEqualTo(UPDATED_SURFACE_COLOR);
    }

    @Test
    @Transactional
    void putNonExistingOneStudySupply() throws Exception {
        int databaseSizeBeforeUpdate = oneStudySupplyRepository.findAll().size();
        oneStudySupply.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOneStudySupplyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, oneStudySupply.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(oneStudySupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the OneStudySupply in the database
        List<OneStudySupply> oneStudySupplyList = oneStudySupplyRepository.findAll();
        assertThat(oneStudySupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOneStudySupply() throws Exception {
        int databaseSizeBeforeUpdate = oneStudySupplyRepository.findAll().size();
        oneStudySupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOneStudySupplyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(oneStudySupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the OneStudySupply in the database
        List<OneStudySupply> oneStudySupplyList = oneStudySupplyRepository.findAll();
        assertThat(oneStudySupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOneStudySupply() throws Exception {
        int databaseSizeBeforeUpdate = oneStudySupplyRepository.findAll().size();
        oneStudySupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOneStudySupplyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oneStudySupply)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OneStudySupply in the database
        List<OneStudySupply> oneStudySupplyList = oneStudySupplyRepository.findAll();
        assertThat(oneStudySupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOneStudySupplyWithPatch() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        int databaseSizeBeforeUpdate = oneStudySupplyRepository.findAll().size();

        // Update the oneStudySupply using partial update
        OneStudySupply partialUpdatedOneStudySupply = new OneStudySupply();
        partialUpdatedOneStudySupply.setId(oneStudySupply.getId());

        partialUpdatedOneStudySupply.surfaceColor(UPDATED_SURFACE_COLOR);

        restOneStudySupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOneStudySupply.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOneStudySupply))
            )
            .andExpect(status().isOk());

        // Validate the OneStudySupply in the database
        List<OneStudySupply> oneStudySupplyList = oneStudySupplyRepository.findAll();
        assertThat(oneStudySupplyList).hasSize(databaseSizeBeforeUpdate);
        OneStudySupply testOneStudySupply = oneStudySupplyList.get(oneStudySupplyList.size() - 1);
        assertThat(testOneStudySupply.getApparitions()).isEqualTo(DEFAULT_APPARITIONS);
        assertThat(testOneStudySupply.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testOneStudySupply.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testOneStudySupply.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOneStudySupply.getMarkingType()).isEqualTo(DEFAULT_MARKING_TYPE);
        assertThat(testOneStudySupply.getGramPerMeterLinearMass()).isEqualTo(DEFAULT_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testOneStudySupply.getMilimeterDiameter()).isEqualTo(DEFAULT_MILIMETER_DIAMETER);
        assertThat(testOneStudySupply.getSurfaceColor()).isEqualTo(UPDATED_SURFACE_COLOR);
    }

    @Test
    @Transactional
    void fullUpdateOneStudySupplyWithPatch() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        int databaseSizeBeforeUpdate = oneStudySupplyRepository.findAll().size();

        // Update the oneStudySupply using partial update
        OneStudySupply partialUpdatedOneStudySupply = new OneStudySupply();
        partialUpdatedOneStudySupply.setId(oneStudySupply.getId());

        partialUpdatedOneStudySupply
            .apparitions(UPDATED_APPARITIONS)
            .number(UPDATED_NUMBER)
            .designation(UPDATED_DESIGNATION)
            .description(UPDATED_DESCRIPTION)
            .markingType(UPDATED_MARKING_TYPE)
            .gramPerMeterLinearMass(UPDATED_GRAM_PER_METER_LINEAR_MASS)
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER)
            .surfaceColor(UPDATED_SURFACE_COLOR);

        restOneStudySupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOneStudySupply.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOneStudySupply))
            )
            .andExpect(status().isOk());

        // Validate the OneStudySupply in the database
        List<OneStudySupply> oneStudySupplyList = oneStudySupplyRepository.findAll();
        assertThat(oneStudySupplyList).hasSize(databaseSizeBeforeUpdate);
        OneStudySupply testOneStudySupply = oneStudySupplyList.get(oneStudySupplyList.size() - 1);
        assertThat(testOneStudySupply.getApparitions()).isEqualTo(UPDATED_APPARITIONS);
        assertThat(testOneStudySupply.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testOneStudySupply.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testOneStudySupply.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOneStudySupply.getMarkingType()).isEqualTo(UPDATED_MARKING_TYPE);
        assertThat(testOneStudySupply.getGramPerMeterLinearMass()).isEqualTo(UPDATED_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testOneStudySupply.getMilimeterDiameter()).isEqualTo(UPDATED_MILIMETER_DIAMETER);
        assertThat(testOneStudySupply.getSurfaceColor()).isEqualTo(UPDATED_SURFACE_COLOR);
    }

    @Test
    @Transactional
    void patchNonExistingOneStudySupply() throws Exception {
        int databaseSizeBeforeUpdate = oneStudySupplyRepository.findAll().size();
        oneStudySupply.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOneStudySupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, oneStudySupply.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(oneStudySupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the OneStudySupply in the database
        List<OneStudySupply> oneStudySupplyList = oneStudySupplyRepository.findAll();
        assertThat(oneStudySupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOneStudySupply() throws Exception {
        int databaseSizeBeforeUpdate = oneStudySupplyRepository.findAll().size();
        oneStudySupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOneStudySupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(oneStudySupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the OneStudySupply in the database
        List<OneStudySupply> oneStudySupplyList = oneStudySupplyRepository.findAll();
        assertThat(oneStudySupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOneStudySupply() throws Exception {
        int databaseSizeBeforeUpdate = oneStudySupplyRepository.findAll().size();
        oneStudySupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOneStudySupplyMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(oneStudySupply))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OneStudySupply in the database
        List<OneStudySupply> oneStudySupplyList = oneStudySupplyRepository.findAll();
        assertThat(oneStudySupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOneStudySupply() throws Exception {
        // Initialize the database
        oneStudySupplyRepository.saveAndFlush(oneStudySupply);

        int databaseSizeBeforeDelete = oneStudySupplyRepository.findAll().size();

        // Delete the oneStudySupply
        restOneStudySupplyMockMvc
            .perform(delete(ENTITY_API_URL_ID, oneStudySupply.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OneStudySupply> oneStudySupplyList = oneStudySupplyRepository.findAll();
        assertThat(oneStudySupplyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
