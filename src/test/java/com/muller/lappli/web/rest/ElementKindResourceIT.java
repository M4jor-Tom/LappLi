package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.Copper;
import com.muller.lappli.domain.ElementKind;
import com.muller.lappli.domain.Material;
import com.muller.lappli.repository.ElementKindRepository;
//import com.muller.lappli.service.criteria.ElementKindCriteria;
import java.util.List;
//import java.util.Random;
//import java.util.concurrent.atomic.AtomicLong;
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
 * Integration tests for the {@link ElementKindResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ElementKindResourceIT {

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final Double DEFAULT_GRAM_PER_METER_LINEAR_MASS = 1D;
    private static final Double UPDATED_GRAM_PER_METER_LINEAR_MASS = 2D;

    private static final Double DEFAULT_MILIMETER_DIAMETER = 1D;
    private static final Double UPDATED_MILIMETER_DIAMETER = 2D;

    private static final Double DEFAULT_MILIMETER_INSULATION_THICKNESS = 1D;
    private static final Double UPDATED_MILIMETER_INSULATION_THICKNESS = 2D;

    private static final String ENTITY_API_URL = "/api/element-kinds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    //private static Random random = new Random();
    //private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ElementKindRepository elementKindRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restElementKindMockMvc;

    private ElementKind elementKind;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ElementKind createEntity(EntityManager em) {
        ElementKind elementKind = new ElementKind()
            .designation(DEFAULT_DESIGNATION)
            .gramPerMeterLinearMass(DEFAULT_GRAM_PER_METER_LINEAR_MASS)
            .milimeterDiameter(DEFAULT_MILIMETER_DIAMETER)
            .milimeterInsulationThickness(DEFAULT_MILIMETER_INSULATION_THICKNESS);
        // Add required entity
        Copper copper;
        if (TestUtil.findAll(em, Copper.class).isEmpty()) {
            copper = CopperResourceIT.createEntity(em);
            em.persist(copper);
            em.flush();
        } else {
            copper = TestUtil.findAll(em, Copper.class).get(0);
        }
        elementKind.setCopper(copper);
        // Add required entity
        Material material;
        if (TestUtil.findAll(em, Material.class).isEmpty()) {
            material = MaterialResourceIT.createEntity(em);
            em.persist(material);
            em.flush();
        } else {
            material = TestUtil.findAll(em, Material.class).get(0);
        }
        elementKind.setInsulationMaterial(material);
        return elementKind;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ElementKind createUpdatedEntity(EntityManager em) {
        ElementKind elementKind = new ElementKind()
            .designation(UPDATED_DESIGNATION)
            .gramPerMeterLinearMass(UPDATED_GRAM_PER_METER_LINEAR_MASS)
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER)
            .milimeterInsulationThickness(UPDATED_MILIMETER_INSULATION_THICKNESS);
        // Add required entity
        Copper copper;
        if (TestUtil.findAll(em, Copper.class).isEmpty()) {
            copper = CopperResourceIT.createUpdatedEntity(em);
            em.persist(copper);
            em.flush();
        } else {
            copper = TestUtil.findAll(em, Copper.class).get(0);
        }
        elementKind.setCopper(copper);
        // Add required entity
        Material material;
        if (TestUtil.findAll(em, Material.class).isEmpty()) {
            material = MaterialResourceIT.createUpdatedEntity(em);
            em.persist(material);
            em.flush();
        } else {
            material = TestUtil.findAll(em, Material.class).get(0);
        }
        elementKind.setInsulationMaterial(material);
        return elementKind;
    }

    @BeforeEach
    public void initTest() {
        elementKind = createEntity(em);
    }

    @Test
    @Transactional
    void createElementKind() throws Exception {
        int databaseSizeBeforeCreate = elementKindRepository.findAll().size();
        // Create the ElementKind
        restElementKindMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(elementKind)))
            .andExpect(status().isCreated());

        // Validate the ElementKind in the database
        List<ElementKind> elementKindList = elementKindRepository.findAll();
        assertThat(elementKindList).hasSize(databaseSizeBeforeCreate + 1);
        ElementKind testElementKind = elementKindList.get(elementKindList.size() - 1);
        assertThat(testElementKind.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testElementKind.getGramPerMeterLinearMass()).isEqualTo(DEFAULT_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testElementKind.getMilimeterDiameter()).isEqualTo(DEFAULT_MILIMETER_DIAMETER);
        assertThat(testElementKind.getMilimeterInsulationThickness()).isEqualTo(DEFAULT_MILIMETER_INSULATION_THICKNESS);
    }

    @Test
    @Transactional
    void createElementKindWithExistingId() throws Exception {
        // Create the ElementKind with an existing ID
        elementKind.setId(1L);

        int databaseSizeBeforeCreate = elementKindRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restElementKindMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(elementKind)))
            .andExpect(status().isBadRequest());

        // Validate the ElementKind in the database
        List<ElementKind> elementKindList = elementKindRepository.findAll();
        assertThat(elementKindList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = elementKindRepository.findAll().size();
        // set the field null
        elementKind.setDesignation(null);

        // Create the ElementKind, which fails.

        restElementKindMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(elementKind)))
            .andExpect(status().isBadRequest());

        List<ElementKind> elementKindList = elementKindRepository.findAll();
        assertThat(elementKindList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGramPerMeterLinearMassIsRequired() throws Exception {
        int databaseSizeBeforeTest = elementKindRepository.findAll().size();
        // set the field null
        elementKind.setGramPerMeterLinearMass(null);

        // Create the ElementKind, which fails.

        restElementKindMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(elementKind)))
            .andExpect(status().isBadRequest());

        List<ElementKind> elementKindList = elementKindRepository.findAll();
        assertThat(elementKindList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMilimeterDiameterIsRequired() throws Exception {
        int databaseSizeBeforeTest = elementKindRepository.findAll().size();
        // set the field null
        elementKind.setMilimeterDiameter(null);

        // Create the ElementKind, which fails.

        restElementKindMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(elementKind)))
            .andExpect(status().isBadRequest());

        List<ElementKind> elementKindList = elementKindRepository.findAll();
        assertThat(elementKindList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMilimeterInsulationThicknessIsRequired() throws Exception {
        int databaseSizeBeforeTest = elementKindRepository.findAll().size();
        // set the field null
        elementKind.setMilimeterInsulationThickness(null);

        // Create the ElementKind, which fails.

        restElementKindMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(elementKind)))
            .andExpect(status().isBadRequest());

        List<ElementKind> elementKindList = elementKindRepository.findAll();
        assertThat(elementKindList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllElementKinds() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        // Get all the elementKindList
        restElementKindMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(elementKind.getId().intValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].gramPerMeterLinearMass").value(hasItem(DEFAULT_GRAM_PER_METER_LINEAR_MASS.doubleValue())))
            .andExpect(jsonPath("$.[*].milimeterDiameter").value(hasItem(DEFAULT_MILIMETER_DIAMETER.doubleValue())))
            .andExpect(jsonPath("$.[*].milimeterInsulationThickness").value(hasItem(DEFAULT_MILIMETER_INSULATION_THICKNESS.doubleValue())));
    }

    @Test
    @Transactional
    void getElementKind() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        // Get the elementKind
        restElementKindMockMvc
            .perform(get(ENTITY_API_URL_ID, elementKind.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(elementKind.getId().intValue()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.gramPerMeterLinearMass").value(DEFAULT_GRAM_PER_METER_LINEAR_MASS.doubleValue()))
            .andExpect(jsonPath("$.milimeterDiameter").value(DEFAULT_MILIMETER_DIAMETER.doubleValue()))
            .andExpect(jsonPath("$.milimeterInsulationThickness").value(DEFAULT_MILIMETER_INSULATION_THICKNESS.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingElementKind() throws Exception {
        // Get the elementKind
        restElementKindMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewElementKind() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        int databaseSizeBeforeUpdate = elementKindRepository.findAll().size();

        // Update the elementKind
        ElementKind updatedElementKind = elementKindRepository.findById(elementKind.getId()).get();
        // Disconnect from session so that the updates on updatedElementKind are not directly saved in db
        em.detach(updatedElementKind);
        updatedElementKind
            .designation(UPDATED_DESIGNATION)
            .gramPerMeterLinearMass(UPDATED_GRAM_PER_METER_LINEAR_MASS)
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER)
            .milimeterInsulationThickness(UPDATED_MILIMETER_INSULATION_THICKNESS);

        restElementKindMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedElementKind.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedElementKind))
            )
            //.andExpect(status().isOk());
            .andExpect(status().isBadRequest());

        // Validate the ElementKind in the database
        List<ElementKind> elementKindList = elementKindRepository.findAll();
        assertThat(elementKindList).hasSize(databaseSizeBeforeUpdate);
        ElementKind testElementKind = elementKindList.get(elementKindList.size() - 1);
        assertThat(testElementKind.getDesignation()).isEqualTo(DEFAULT_DESIGNATION); //UPDATED_DESIGNATION);
        assertThat(testElementKind.getGramPerMeterLinearMass()).isEqualTo(DEFAULT_GRAM_PER_METER_LINEAR_MASS); //UPDATED_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testElementKind.getMilimeterDiameter()).isEqualTo(DEFAULT_MILIMETER_DIAMETER); //UPDATED_MILIMETER_DIAMETER);
        assertThat(testElementKind.getMilimeterInsulationThickness()).isEqualTo(DEFAULT_MILIMETER_INSULATION_THICKNESS); //UPDATED_INSULATION_THICKNESS);
    }

    /*
    @Test
    @Transactional
    void putNonExistingElementKind() throws Exception {
        int databaseSizeBeforeUpdate = elementKindRepository.findAll().size();
        elementKind.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restElementKindMockMvc
            .perform(
                put(ENTITY_API_URL_ID, elementKind.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(elementKind))
            )
            .andExpect(status().isBadRequest());

        // Validate the ElementKind in the database
        List<ElementKind> elementKindList = elementKindRepository.findAll();
        assertThat(elementKindList).hasSize(databaseSizeBeforeUpdate);
    }*/
    /*
    @Test
    @Transactional
    void putWithIdMismatchElementKind() throws Exception {
        int databaseSizeBeforeUpdate = elementKindRepository.findAll().size();
        elementKind.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restElementKindMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(elementKind))
            )
            .andExpect(status().isBadRequest());

        // Validate the ElementKind in the database
        List<ElementKind> elementKindList = elementKindRepository.findAll();
        assertThat(elementKindList).hasSize(databaseSizeBeforeUpdate);
    }*/
    /*
    @Test
    @Transactional
    void putWithMissingIdPathParamElementKind() throws Exception {
        int databaseSizeBeforeUpdate = elementKindRepository.findAll().size();
        elementKind.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restElementKindMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(elementKind)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ElementKind in the database
        List<ElementKind> elementKindList = elementKindRepository.findAll();
        assertThat(elementKindList).hasSize(databaseSizeBeforeUpdate);
    }*/
    /*
    @Test
    @Transactional
    void partialUpdateElementKindWithPatch() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        int databaseSizeBeforeUpdate = elementKindRepository.findAll().size();

        // Update the elementKind using partial update
        ElementKind partialUpdatedElementKind = new ElementKind();
        partialUpdatedElementKind.setId(elementKind.getId());

        partialUpdatedElementKind
            .designation(UPDATED_DESIGNATION)
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER)
            .insulationThickness(UPDATED_INSULATION_THICKNESS);

        restElementKindMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedElementKind.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedElementKind))
            )
            .andExpect(status().isOk());

        // Validate the ElementKind in the database
        List<ElementKind> elementKindList = elementKindRepository.findAll();
        assertThat(elementKindList).hasSize(databaseSizeBeforeUpdate);
        ElementKind testElementKind = elementKindList.get(elementKindList.size() - 1);
        assertThat(testElementKind.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testElementKind.getGramPerMeterLinearMass()).isEqualTo(DEFAULT_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testElementKind.getMilimeterDiameter()).isEqualTo(UPDATED_MILIMETER_DIAMETER);
        assertThat(testElementKind.getInsulationThickness()).isEqualTo(UPDATED_INSULATION_THICKNESS);
    }*/
    /*
    @Test
    @Transactional
    void fullUpdateElementKindWithPatch() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        int databaseSizeBeforeUpdate = elementKindRepository.findAll().size();

        // Update the elementKind using partial update
        ElementKind partialUpdatedElementKind = new ElementKind();
        partialUpdatedElementKind.setId(elementKind.getId());

        partialUpdatedElementKind
            .designation(UPDATED_DESIGNATION)
            .gramPerMeterLinearMass(UPDATED_GRAM_PER_METER_LINEAR_MASS)
            .milimeterDiameter(UPDATED_MILIMETER_DIAMETER)
            .insulationThickness(UPDATED_INSULATION_THICKNESS);

        restElementKindMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedElementKind.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedElementKind))
            )
            .andExpect(status().isOk());

        // Validate the ElementKind in the database
        List<ElementKind> elementKindList = elementKindRepository.findAll();
        assertThat(elementKindList).hasSize(databaseSizeBeforeUpdate);
        ElementKind testElementKind = elementKindList.get(elementKindList.size() - 1);
        assertThat(testElementKind.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testElementKind.getGramPerMeterLinearMass()).isEqualTo(UPDATED_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testElementKind.getMilimeterDiameter()).isEqualTo(UPDATED_MILIMETER_DIAMETER);
        assertThat(testElementKind.getInsulationThickness()).isEqualTo(UPDATED_INSULATION_THICKNESS);
    }*/
    /*
    @Test
    @Transactional
    void patchNonExistingElementKind() throws Exception {
        int databaseSizeBeforeUpdate = elementKindRepository.findAll().size();
        elementKind.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restElementKindMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, elementKind.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(elementKind))
            )
            .andExpect(status().isBadRequest());

        // Validate the ElementKind in the database
        List<ElementKind> elementKindList = elementKindRepository.findAll();
        assertThat(elementKindList).hasSize(databaseSizeBeforeUpdate);
    }*/
    /*
    @Test
    @Transactional
    void patchWithIdMismatchElementKind() throws Exception {
        int databaseSizeBeforeUpdate = elementKindRepository.findAll().size();
        elementKind.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restElementKindMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(elementKind))
            )
            .andExpect(status().isBadRequest());

        // Validate the ElementKind in the database
        List<ElementKind> elementKindList = elementKindRepository.findAll();
        assertThat(elementKindList).hasSize(databaseSizeBeforeUpdate);
    }*/
    /*
    @Test
    @Transactional
    void patchWithMissingIdPathParamElementKind() throws Exception {
        int databaseSizeBeforeUpdate = elementKindRepository.findAll().size();
        elementKind.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restElementKindMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(elementKind))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ElementKind in the database
        List<ElementKind> elementKindList = elementKindRepository.findAll();
        assertThat(elementKindList).hasSize(databaseSizeBeforeUpdate);
    }*/

    @Test
    @Transactional
    void deleteElementKind() throws Exception {
        // Initialize the database
        elementKindRepository.saveAndFlush(elementKind);

        int databaseSizeBeforeDelete = elementKindRepository.findAll().size();

        // Delete the elementKind
        restElementKindMockMvc
            .perform(delete(ENTITY_API_URL_ID, elementKind.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ElementKind> elementKindList = elementKindRepository.findAll();
        assertThat(elementKindList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
