package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.ContinuityWire;
import com.muller.lappli.domain.enumeration.Flexibility;
import com.muller.lappli.domain.enumeration.MetalFiberKind;
import com.muller.lappli.repository.ContinuityWireRepository;
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
 * Integration tests for the {@link ContinuityWireResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContinuityWireResourceIT {

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final Double DEFAULT_GRAM_PER_METER_LINEAR_MASS = 1D;
    private static final Double UPDATED_GRAM_PER_METER_LINEAR_MASS = 2D;

    private static final MetalFiberKind DEFAULT_METAL_FIBER_KIND = MetalFiberKind.RED_COPPER;
    private static final MetalFiberKind UPDATED_METAL_FIBER_KIND = MetalFiberKind.TINNED_COPPER;

    private static final Double DEFAULT_MILIMETER_DIAMETER = 1D;
    private static final Double UPDATED_MILIMETER_DIAMETER = 2D;

    private static final Flexibility DEFAULT_FLEXIBILITY = Flexibility.S;
    private static final Flexibility UPDATED_FLEXIBILITY = Flexibility.ES;

    private static final String ENTITY_API_URL = "/api/continuity-wires";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ContinuityWireRepository continuityWireRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContinuityWireMockMvc;

    private ContinuityWire continuityWire;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContinuityWire createEntity(EntityManager em) {
        ContinuityWire continuityWire = new ContinuityWire()
            .designation(DEFAULT_DESIGNATION)
            .gramPerMeterLinearMass(DEFAULT_GRAM_PER_METER_LINEAR_MASS)
            .metalFiberKind(DEFAULT_METAL_FIBER_KIND)
            .milimeterDiameter(DEFAULT_MILIMETER_DIAMETER)
            .flexibility(DEFAULT_FLEXIBILITY);
        return continuityWire;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContinuityWire createUpdatedEntity(EntityManager em) {
        ContinuityWire continuityWire = new ContinuityWire()
            .designation(UPDATED_DESIGNATION)
            .gramPerMeterLinearMass(UPDATED_GRAM_PER_METER_LINEAR_MASS)
            .metalFiberKind(UPDATED_METAL_FIBER_KIND)
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER)
            .flexibility(UPDATED_FLEXIBILITY);
        return continuityWire;
    }

    @BeforeEach
    public void initTest() {
        continuityWire = createEntity(em);
    }

    @Test
    @Transactional
    void createContinuityWire() throws Exception {
        int databaseSizeBeforeCreate = continuityWireRepository.findAll().size();
        // Create the ContinuityWire
        restContinuityWireMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(continuityWire))
            )
            .andExpect(status().isCreated());

        // Validate the ContinuityWire in the database
        List<ContinuityWire> continuityWireList = continuityWireRepository.findAll();
        assertThat(continuityWireList).hasSize(databaseSizeBeforeCreate + 1);
        ContinuityWire testContinuityWire = continuityWireList.get(continuityWireList.size() - 1);
        assertThat(testContinuityWire.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testContinuityWire.getGramPerMeterLinearMass()).isEqualTo(DEFAULT_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testContinuityWire.getMetalFiberKind()).isEqualTo(DEFAULT_METAL_FIBER_KIND);
        assertThat(testContinuityWire.getMilimeterDiameter()).isEqualTo(DEFAULT_MILIMETER_DIAMETER);
        assertThat(testContinuityWire.getFlexibility()).isEqualTo(DEFAULT_FLEXIBILITY);
    }

    @Test
    @Transactional
    void createContinuityWireWithExistingId() throws Exception {
        // Create the ContinuityWire with an existing ID
        continuityWire.setId(1L);

        int databaseSizeBeforeCreate = continuityWireRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContinuityWireMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(continuityWire))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContinuityWire in the database
        List<ContinuityWire> continuityWireList = continuityWireRepository.findAll();
        assertThat(continuityWireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = continuityWireRepository.findAll().size();
        // set the field null
        continuityWire.setDesignation(null);

        // Create the ContinuityWire, which fails.

        restContinuityWireMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(continuityWire))
            )
            .andExpect(status().isBadRequest());

        List<ContinuityWire> continuityWireList = continuityWireRepository.findAll();
        assertThat(continuityWireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGramPerMeterLinearMassIsRequired() throws Exception {
        int databaseSizeBeforeTest = continuityWireRepository.findAll().size();
        // set the field null
        continuityWire.setGramPerMeterLinearMass(null);

        // Create the ContinuityWire, which fails.

        restContinuityWireMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(continuityWire))
            )
            .andExpect(status().isBadRequest());

        List<ContinuityWire> continuityWireList = continuityWireRepository.findAll();
        assertThat(continuityWireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMetalFiberKindIsRequired() throws Exception {
        int databaseSizeBeforeTest = continuityWireRepository.findAll().size();
        // set the field null
        continuityWire.setMetalFiberKind(null);

        // Create the ContinuityWire, which fails.

        restContinuityWireMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(continuityWire))
            )
            .andExpect(status().isBadRequest());

        List<ContinuityWire> continuityWireList = continuityWireRepository.findAll();
        assertThat(continuityWireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMilimeterDiameterIsRequired() throws Exception {
        int databaseSizeBeforeTest = continuityWireRepository.findAll().size();
        // set the field null
        continuityWire.setMilimeterDiameter(null);

        // Create the ContinuityWire, which fails.

        restContinuityWireMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(continuityWire))
            )
            .andExpect(status().isBadRequest());

        List<ContinuityWire> continuityWireList = continuityWireRepository.findAll();
        assertThat(continuityWireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFlexibilityIsRequired() throws Exception {
        int databaseSizeBeforeTest = continuityWireRepository.findAll().size();
        // set the field null
        continuityWire.setFlexibility(null);

        // Create the ContinuityWire, which fails.

        restContinuityWireMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(continuityWire))
            )
            .andExpect(status().isBadRequest());

        List<ContinuityWire> continuityWireList = continuityWireRepository.findAll();
        assertThat(continuityWireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllContinuityWires() throws Exception {
        // Initialize the database
        continuityWireRepository.saveAndFlush(continuityWire);

        // Get all the continuityWireList
        restContinuityWireMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(continuityWire.getId().intValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].gramPerMeterLinearMass").value(hasItem(DEFAULT_GRAM_PER_METER_LINEAR_MASS.doubleValue())))
            .andExpect(jsonPath("$.[*].metalFiberKind").value(hasItem(DEFAULT_METAL_FIBER_KIND.toString())))
            .andExpect(jsonPath("$.[*].milimeterDiameter").value(hasItem(DEFAULT_MILIMETER_DIAMETER.doubleValue())))
            .andExpect(jsonPath("$.[*].flexibility").value(hasItem(DEFAULT_FLEXIBILITY.toString())));
    }

    @Test
    @Transactional
    void getContinuityWire() throws Exception {
        // Initialize the database
        continuityWireRepository.saveAndFlush(continuityWire);

        // Get the continuityWire
        restContinuityWireMockMvc
            .perform(get(ENTITY_API_URL_ID, continuityWire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(continuityWire.getId().intValue()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.gramPerMeterLinearMass").value(DEFAULT_GRAM_PER_METER_LINEAR_MASS.doubleValue()))
            .andExpect(jsonPath("$.metalFiberKind").value(DEFAULT_METAL_FIBER_KIND.toString()))
            .andExpect(jsonPath("$.milimeterDiameter").value(DEFAULT_MILIMETER_DIAMETER.doubleValue()))
            .andExpect(jsonPath("$.flexibility").value(DEFAULT_FLEXIBILITY.toString()));
    }

    @Test
    @Transactional
    void getNonExistingContinuityWire() throws Exception {
        // Get the continuityWire
        restContinuityWireMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewContinuityWire() throws Exception {
        // Initialize the database
        continuityWireRepository.saveAndFlush(continuityWire);

        int databaseSizeBeforeUpdate = continuityWireRepository.findAll().size();

        // Update the continuityWire
        ContinuityWire updatedContinuityWire = continuityWireRepository.findById(continuityWire.getId()).get();
        // Disconnect from session so that the updates on updatedContinuityWire are not directly saved in db
        em.detach(updatedContinuityWire);
        updatedContinuityWire
            .designation(UPDATED_DESIGNATION)
            .gramPerMeterLinearMass(UPDATED_GRAM_PER_METER_LINEAR_MASS)
            .metalFiberKind(UPDATED_METAL_FIBER_KIND)
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER)
            .flexibility(UPDATED_FLEXIBILITY);

        restContinuityWireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedContinuityWire.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedContinuityWire))
            )
            .andExpect(status().isOk());

        // Validate the ContinuityWire in the database
        List<ContinuityWire> continuityWireList = continuityWireRepository.findAll();
        assertThat(continuityWireList).hasSize(databaseSizeBeforeUpdate);
        ContinuityWire testContinuityWire = continuityWireList.get(continuityWireList.size() - 1);
        assertThat(testContinuityWire.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testContinuityWire.getGramPerMeterLinearMass()).isEqualTo(UPDATED_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testContinuityWire.getMetalFiberKind()).isEqualTo(UPDATED_METAL_FIBER_KIND);
        assertThat(testContinuityWire.getMilimeterDiameter()).isEqualTo(UPDATED_MILIMETER_DIAMETER);
        assertThat(testContinuityWire.getFlexibility()).isEqualTo(UPDATED_FLEXIBILITY);
    }

    @Test
    @Transactional
    void putNonExistingContinuityWire() throws Exception {
        int databaseSizeBeforeUpdate = continuityWireRepository.findAll().size();
        continuityWire.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContinuityWireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, continuityWire.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(continuityWire))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContinuityWire in the database
        List<ContinuityWire> continuityWireList = continuityWireRepository.findAll();
        assertThat(continuityWireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContinuityWire() throws Exception {
        int databaseSizeBeforeUpdate = continuityWireRepository.findAll().size();
        continuityWire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContinuityWireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(continuityWire))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContinuityWire in the database
        List<ContinuityWire> continuityWireList = continuityWireRepository.findAll();
        assertThat(continuityWireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContinuityWire() throws Exception {
        int databaseSizeBeforeUpdate = continuityWireRepository.findAll().size();
        continuityWire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContinuityWireMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(continuityWire)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContinuityWire in the database
        List<ContinuityWire> continuityWireList = continuityWireRepository.findAll();
        assertThat(continuityWireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContinuityWireWithPatch() throws Exception {
        // Initialize the database
        continuityWireRepository.saveAndFlush(continuityWire);

        int databaseSizeBeforeUpdate = continuityWireRepository.findAll().size();

        // Update the continuityWire using partial update
        ContinuityWire partialUpdatedContinuityWire = new ContinuityWire();
        partialUpdatedContinuityWire.setId(continuityWire.getId());

        partialUpdatedContinuityWire.gramPerMeterLinearMass(UPDATED_GRAM_PER_METER_LINEAR_MASS).metalFiberKind(UPDATED_METAL_FIBER_KIND);

        restContinuityWireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContinuityWire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContinuityWire))
            )
            .andExpect(status().isOk());

        // Validate the ContinuityWire in the database
        List<ContinuityWire> continuityWireList = continuityWireRepository.findAll();
        assertThat(continuityWireList).hasSize(databaseSizeBeforeUpdate);
        ContinuityWire testContinuityWire = continuityWireList.get(continuityWireList.size() - 1);
        assertThat(testContinuityWire.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testContinuityWire.getGramPerMeterLinearMass()).isEqualTo(UPDATED_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testContinuityWire.getMetalFiberKind()).isEqualTo(UPDATED_METAL_FIBER_KIND);
        assertThat(testContinuityWire.getMilimeterDiameter()).isEqualTo(DEFAULT_MILIMETER_DIAMETER);
        assertThat(testContinuityWire.getFlexibility()).isEqualTo(DEFAULT_FLEXIBILITY);
    }

    @Test
    @Transactional
    void fullUpdateContinuityWireWithPatch() throws Exception {
        // Initialize the database
        continuityWireRepository.saveAndFlush(continuityWire);

        int databaseSizeBeforeUpdate = continuityWireRepository.findAll().size();

        // Update the continuityWire using partial update
        ContinuityWire partialUpdatedContinuityWire = new ContinuityWire();
        partialUpdatedContinuityWire.setId(continuityWire.getId());

        partialUpdatedContinuityWire
            .designation(UPDATED_DESIGNATION)
            .gramPerMeterLinearMass(UPDATED_GRAM_PER_METER_LINEAR_MASS)
            .metalFiberKind(UPDATED_METAL_FIBER_KIND)
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER)
            .flexibility(UPDATED_FLEXIBILITY);

        restContinuityWireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContinuityWire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContinuityWire))
            )
            .andExpect(status().isOk());

        // Validate the ContinuityWire in the database
        List<ContinuityWire> continuityWireList = continuityWireRepository.findAll();
        assertThat(continuityWireList).hasSize(databaseSizeBeforeUpdate);
        ContinuityWire testContinuityWire = continuityWireList.get(continuityWireList.size() - 1);
        assertThat(testContinuityWire.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testContinuityWire.getGramPerMeterLinearMass()).isEqualTo(UPDATED_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testContinuityWire.getMetalFiberKind()).isEqualTo(UPDATED_METAL_FIBER_KIND);
        assertThat(testContinuityWire.getMilimeterDiameter()).isEqualTo(UPDATED_MILIMETER_DIAMETER);
        assertThat(testContinuityWire.getFlexibility()).isEqualTo(UPDATED_FLEXIBILITY);
    }

    @Test
    @Transactional
    void patchNonExistingContinuityWire() throws Exception {
        int databaseSizeBeforeUpdate = continuityWireRepository.findAll().size();
        continuityWire.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContinuityWireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, continuityWire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(continuityWire))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContinuityWire in the database
        List<ContinuityWire> continuityWireList = continuityWireRepository.findAll();
        assertThat(continuityWireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContinuityWire() throws Exception {
        int databaseSizeBeforeUpdate = continuityWireRepository.findAll().size();
        continuityWire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContinuityWireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(continuityWire))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContinuityWire in the database
        List<ContinuityWire> continuityWireList = continuityWireRepository.findAll();
        assertThat(continuityWireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContinuityWire() throws Exception {
        int databaseSizeBeforeUpdate = continuityWireRepository.findAll().size();
        continuityWire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContinuityWireMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(continuityWire))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContinuityWire in the database
        List<ContinuityWire> continuityWireList = continuityWireRepository.findAll();
        assertThat(continuityWireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContinuityWire() throws Exception {
        // Initialize the database
        continuityWireRepository.saveAndFlush(continuityWire);

        int databaseSizeBeforeDelete = continuityWireRepository.findAll().size();

        // Delete the continuityWire
        restContinuityWireMockMvc
            .perform(delete(ENTITY_API_URL_ID, continuityWire.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContinuityWire> continuityWireList = continuityWireRepository.findAll();
        assertThat(continuityWireList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
