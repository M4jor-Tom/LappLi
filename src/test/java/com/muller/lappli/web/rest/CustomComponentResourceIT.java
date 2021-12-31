package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.CustomComponent;
import com.muller.lappli.domain.Material;
import com.muller.lappli.domain.enumeration.Color;
import com.muller.lappli.repository.CustomComponentRepository;
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
 * Integration tests for the {@link CustomComponentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustomComponentResourceIT {

    private static final Long DEFAULT_NUMBER = 1L;
    private static final Long UPDATED_NUMBER = 2L;

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final Double DEFAULT_GRAM_PER_METER_LINEAR_MASS = 1D;
    private static final Double UPDATED_GRAM_PER_METER_LINEAR_MASS = 2D;

    private static final Double DEFAULT_MILIMETER_DIAMETER = 1D;
    private static final Double UPDATED_MILIMETER_DIAMETER = 2D;

    private static final Color DEFAULT_SURFACE_COLOR = Color.NATURAL;
    private static final Color UPDATED_SURFACE_COLOR = Color.WHITE;

    private static final String ENTITY_API_URL = "/api/custom-components";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustomComponentRepository customComponentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomComponentMockMvc;

    private CustomComponent customComponent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomComponent createEntity(EntityManager em) {
        CustomComponent customComponent = new CustomComponent()
            .number(DEFAULT_NUMBER)
            .designation(DEFAULT_DESIGNATION)
            .gramPerMeterLinearMass(DEFAULT_GRAM_PER_METER_LINEAR_MASS)
            .milimeterDiameter(DEFAULT_MILIMETER_DIAMETER)
            .surfaceColor(DEFAULT_SURFACE_COLOR);
        // Add required entity
        Material material;
        if (TestUtil.findAll(em, Material.class).isEmpty()) {
            material = MaterialResourceIT.createEntity(em);
            em.persist(material);
            em.flush();
        } else {
            material = TestUtil.findAll(em, Material.class).get(0);
        }
        customComponent.setSurfaceMaterial(material);
        return customComponent;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomComponent createUpdatedEntity(EntityManager em) {
        CustomComponent customComponent = new CustomComponent()
            .number(UPDATED_NUMBER)
            .designation(UPDATED_DESIGNATION)
            .gramPerMeterLinearMass(UPDATED_GRAM_PER_METER_LINEAR_MASS)
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER)
            .surfaceColor(UPDATED_SURFACE_COLOR);
        // Add required entity
        Material material;
        if (TestUtil.findAll(em, Material.class).isEmpty()) {
            material = MaterialResourceIT.createUpdatedEntity(em);
            em.persist(material);
            em.flush();
        } else {
            material = TestUtil.findAll(em, Material.class).get(0);
        }
        customComponent.setSurfaceMaterial(material);
        return customComponent;
    }

    @BeforeEach
    public void initTest() {
        customComponent = createEntity(em);
    }

    @Test
    @Transactional
    void createCustomComponent() throws Exception {
        int databaseSizeBeforeCreate = customComponentRepository.findAll().size();
        // Create the CustomComponent
        restCustomComponentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customComponent))
            )
            .andExpect(status().isCreated());

        // Validate the CustomComponent in the database
        List<CustomComponent> customComponentList = customComponentRepository.findAll();
        assertThat(customComponentList).hasSize(databaseSizeBeforeCreate + 1);
        CustomComponent testCustomComponent = customComponentList.get(customComponentList.size() - 1);
        assertThat(testCustomComponent.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testCustomComponent.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testCustomComponent.getGramPerMeterLinearMass()).isEqualTo(DEFAULT_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testCustomComponent.getMilimeterDiameter()).isEqualTo(DEFAULT_MILIMETER_DIAMETER);
        assertThat(testCustomComponent.getSurfaceColor()).isEqualTo(DEFAULT_SURFACE_COLOR);
    }

    @Test
    @Transactional
    void createCustomComponentWithExistingId() throws Exception {
        // Create the CustomComponent with an existing ID
        customComponent.setId(1L);

        int databaseSizeBeforeCreate = customComponentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomComponentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customComponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomComponent in the database
        List<CustomComponent> customComponentList = customComponentRepository.findAll();
        assertThat(customComponentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkGramPerMeterLinearMassIsRequired() throws Exception {
        int databaseSizeBeforeTest = customComponentRepository.findAll().size();
        // set the field null
        customComponent.setGramPerMeterLinearMass(null);

        // Create the CustomComponent, which fails.

        restCustomComponentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customComponent))
            )
            .andExpect(status().isBadRequest());

        List<CustomComponent> customComponentList = customComponentRepository.findAll();
        assertThat(customComponentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMilimeterDiameterIsRequired() throws Exception {
        int databaseSizeBeforeTest = customComponentRepository.findAll().size();
        // set the field null
        customComponent.setMilimeterDiameter(null);

        // Create the CustomComponent, which fails.

        restCustomComponentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customComponent))
            )
            .andExpect(status().isBadRequest());

        List<CustomComponent> customComponentList = customComponentRepository.findAll();
        assertThat(customComponentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSurfaceColorIsRequired() throws Exception {
        int databaseSizeBeforeTest = customComponentRepository.findAll().size();
        // set the field null
        customComponent.setSurfaceColor(null);

        // Create the CustomComponent, which fails.

        restCustomComponentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customComponent))
            )
            .andExpect(status().isBadRequest());

        List<CustomComponent> customComponentList = customComponentRepository.findAll();
        assertThat(customComponentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustomComponents() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        // Get all the customComponentList
        restCustomComponentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customComponent.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].gramPerMeterLinearMass").value(hasItem(DEFAULT_GRAM_PER_METER_LINEAR_MASS.doubleValue())))
            .andExpect(jsonPath("$.[*].milimeterDiameter").value(hasItem(DEFAULT_MILIMETER_DIAMETER.doubleValue())))
            .andExpect(jsonPath("$.[*].surfaceColor").value(hasItem(DEFAULT_SURFACE_COLOR.toString())));
    }

    @Test
    @Transactional
    void getCustomComponent() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        // Get the customComponent
        restCustomComponentMockMvc
            .perform(get(ENTITY_API_URL_ID, customComponent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customComponent.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.intValue()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.gramPerMeterLinearMass").value(DEFAULT_GRAM_PER_METER_LINEAR_MASS.doubleValue()))
            .andExpect(jsonPath("$.milimeterDiameter").value(DEFAULT_MILIMETER_DIAMETER.doubleValue()))
            .andExpect(jsonPath("$.surfaceColor").value(DEFAULT_SURFACE_COLOR.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCustomComponent() throws Exception {
        // Get the customComponent
        restCustomComponentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustomComponent() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        int databaseSizeBeforeUpdate = customComponentRepository.findAll().size();

        // Update the customComponent
        CustomComponent updatedCustomComponent = customComponentRepository.findById(customComponent.getId()).get();
        // Disconnect from session so that the updates on updatedCustomComponent are not directly saved in db
        em.detach(updatedCustomComponent);
        updatedCustomComponent
            .number(UPDATED_NUMBER)
            .designation(UPDATED_DESIGNATION)
            .gramPerMeterLinearMass(UPDATED_GRAM_PER_METER_LINEAR_MASS)
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER)
            .surfaceColor(UPDATED_SURFACE_COLOR);

        restCustomComponentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCustomComponent.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCustomComponent))
            )
            .andExpect(status().isOk());

        // Validate the CustomComponent in the database
        List<CustomComponent> customComponentList = customComponentRepository.findAll();
        assertThat(customComponentList).hasSize(databaseSizeBeforeUpdate);
        CustomComponent testCustomComponent = customComponentList.get(customComponentList.size() - 1);
        assertThat(testCustomComponent.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testCustomComponent.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testCustomComponent.getGramPerMeterLinearMass()).isEqualTo(UPDATED_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testCustomComponent.getMilimeterDiameter()).isEqualTo(UPDATED_MILIMETER_DIAMETER);
        assertThat(testCustomComponent.getSurfaceColor()).isEqualTo(UPDATED_SURFACE_COLOR);
    }

    @Test
    @Transactional
    void putNonExistingCustomComponent() throws Exception {
        int databaseSizeBeforeUpdate = customComponentRepository.findAll().size();
        customComponent.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomComponentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, customComponent.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customComponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomComponent in the database
        List<CustomComponent> customComponentList = customComponentRepository.findAll();
        assertThat(customComponentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustomComponent() throws Exception {
        int databaseSizeBeforeUpdate = customComponentRepository.findAll().size();
        customComponent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomComponentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customComponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomComponent in the database
        List<CustomComponent> customComponentList = customComponentRepository.findAll();
        assertThat(customComponentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustomComponent() throws Exception {
        int databaseSizeBeforeUpdate = customComponentRepository.findAll().size();
        customComponent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomComponentMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customComponent))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomComponent in the database
        List<CustomComponent> customComponentList = customComponentRepository.findAll();
        assertThat(customComponentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustomComponentWithPatch() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        int databaseSizeBeforeUpdate = customComponentRepository.findAll().size();

        // Update the customComponent using partial update
        CustomComponent partialUpdatedCustomComponent = new CustomComponent();
        partialUpdatedCustomComponent.setId(customComponent.getId());

        partialUpdatedCustomComponent.designation(UPDATED_DESIGNATION);

        restCustomComponentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomComponent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomComponent))
            )
            .andExpect(status().isOk());

        // Validate the CustomComponent in the database
        List<CustomComponent> customComponentList = customComponentRepository.findAll();
        assertThat(customComponentList).hasSize(databaseSizeBeforeUpdate);
        CustomComponent testCustomComponent = customComponentList.get(customComponentList.size() - 1);
        assertThat(testCustomComponent.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testCustomComponent.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testCustomComponent.getGramPerMeterLinearMass()).isEqualTo(DEFAULT_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testCustomComponent.getMilimeterDiameter()).isEqualTo(DEFAULT_MILIMETER_DIAMETER);
        assertThat(testCustomComponent.getSurfaceColor()).isEqualTo(DEFAULT_SURFACE_COLOR);
    }

    @Test
    @Transactional
    void fullUpdateCustomComponentWithPatch() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        int databaseSizeBeforeUpdate = customComponentRepository.findAll().size();

        // Update the customComponent using partial update
        CustomComponent partialUpdatedCustomComponent = new CustomComponent();
        partialUpdatedCustomComponent.setId(customComponent.getId());

        partialUpdatedCustomComponent
            .number(UPDATED_NUMBER)
            .designation(UPDATED_DESIGNATION)
            .gramPerMeterLinearMass(UPDATED_GRAM_PER_METER_LINEAR_MASS)
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER)
            .surfaceColor(UPDATED_SURFACE_COLOR);

        restCustomComponentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomComponent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomComponent))
            )
            .andExpect(status().isOk());

        // Validate the CustomComponent in the database
        List<CustomComponent> customComponentList = customComponentRepository.findAll();
        assertThat(customComponentList).hasSize(databaseSizeBeforeUpdate);
        CustomComponent testCustomComponent = customComponentList.get(customComponentList.size() - 1);
        assertThat(testCustomComponent.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testCustomComponent.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testCustomComponent.getGramPerMeterLinearMass()).isEqualTo(UPDATED_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testCustomComponent.getMilimeterDiameter()).isEqualTo(UPDATED_MILIMETER_DIAMETER);
        assertThat(testCustomComponent.getSurfaceColor()).isEqualTo(UPDATED_SURFACE_COLOR);
    }

    @Test
    @Transactional
    void patchNonExistingCustomComponent() throws Exception {
        int databaseSizeBeforeUpdate = customComponentRepository.findAll().size();
        customComponent.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomComponentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, customComponent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customComponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomComponent in the database
        List<CustomComponent> customComponentList = customComponentRepository.findAll();
        assertThat(customComponentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustomComponent() throws Exception {
        int databaseSizeBeforeUpdate = customComponentRepository.findAll().size();
        customComponent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomComponentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customComponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomComponent in the database
        List<CustomComponent> customComponentList = customComponentRepository.findAll();
        assertThat(customComponentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustomComponent() throws Exception {
        int databaseSizeBeforeUpdate = customComponentRepository.findAll().size();
        customComponent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomComponentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customComponent))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomComponent in the database
        List<CustomComponent> customComponentList = customComponentRepository.findAll();
        assertThat(customComponentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustomComponent() throws Exception {
        // Initialize the database
        customComponentRepository.saveAndFlush(customComponent);

        int databaseSizeBeforeDelete = customComponentRepository.findAll().size();

        // Delete the customComponent
        restCustomComponentMockMvc
            .perform(delete(ENTITY_API_URL_ID, customComponent.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomComponent> customComponentList = customComponentRepository.findAll();
        assertThat(customComponentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
