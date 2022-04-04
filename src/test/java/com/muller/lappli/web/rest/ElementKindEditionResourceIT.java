package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.ElementKind;
import com.muller.lappli.domain.ElementKindEdition;
import com.muller.lappli.repository.ElementKindEditionRepository;
//import com.muller.lappli.service.criteria.ElementKindEditionCriteria;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link ElementKindEditionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
@Deprecated
class ElementKindEditionResourceIT {

    //private static final Instant DEFAULT_EDITION_DATE_TIME = Instant.ofEpochMilli(0L);
    //private static final Instant UPDATED_EDITION_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_NEW_GRAM_PER_METER_LINEAR_MASS = 1D;
    private static final Double UPDATED_NEW_GRAM_PER_METER_LINEAR_MASS = 2D;

    private static final Double DEFAULT_NEW_MILIMETER_DIAMETER = 1D;
    private static final Double UPDATED_NEW_MILIMETER_DIAMETER = 2D;

    private static final Double DEFAULT_NEW_MILIMETER_INSULATION_THICKNESS = 1D;
    private static final Double UPDATED_NEW_MILIMETER_INSULATION_THICKNESS = 2D;

    private static final String ENTITY_API_URL = "/api/element-kind-editions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    //private static Random random = new Random();
    //private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ElementKindEditionRepository elementKindEditionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restElementKindEditionMockMvc;

    private ElementKindEdition elementKindEdition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ElementKindEdition createEntity(EntityManager em) {
        ElementKindEdition elementKindEdition = new ElementKindEdition()
            .newGramPerMeterLinearMass(DEFAULT_NEW_GRAM_PER_METER_LINEAR_MASS)
            .newMilimeterDiameter(DEFAULT_NEW_MILIMETER_DIAMETER)
            .newMilimeterInsulationThickness(DEFAULT_NEW_MILIMETER_INSULATION_THICKNESS);
        // Add required entity
        ElementKind elementKind;
        if (TestUtil.findAll(em, ElementKind.class).isEmpty()) {
            elementKind = ElementKindResourceIT.createEntity(em);
            em.persist(elementKind);
            em.flush();
        } else {
            elementKind = TestUtil.findAll(em, ElementKind.class).get(0);
        }
        elementKindEdition.setEditedElementKind(elementKind);
        return elementKindEdition;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ElementKindEdition createUpdatedEntity(EntityManager em) {
        ElementKindEdition elementKindEdition = new ElementKindEdition()
            .newGramPerMeterLinearMass(UPDATED_NEW_GRAM_PER_METER_LINEAR_MASS)
            .newMilimeterDiameter(UPDATED_NEW_MILIMETER_DIAMETER)
            .newMilimeterInsulationThickness(UPDATED_NEW_MILIMETER_INSULATION_THICKNESS);
        // Add required entity
        ElementKind elementKind;
        if (TestUtil.findAll(em, ElementKind.class).isEmpty()) {
            elementKind = ElementKindResourceIT.createUpdatedEntity(em);
            em.persist(elementKind);
            em.flush();
        } else {
            elementKind = TestUtil.findAll(em, ElementKind.class).get(0);
        }
        elementKindEdition.setEditedElementKind(elementKind);
        return elementKindEdition;
    }

    @BeforeEach
    public void initTest() {
        elementKindEdition = createEntity(em);
    }

    @Test
    @Transactional
    void createElementKindEdition() throws Exception {
        int databaseSizeBeforeCreate = elementKindEditionRepository.findAll().size();
        // Create the ElementKindEdition
        restElementKindEditionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(elementKindEdition))
            )
            .andExpect(status().isCreated());

        // Validate the ElementKindEdition in the database
        List<ElementKindEdition> elementKindEditionList = elementKindEditionRepository.findAll();
        assertThat(elementKindEditionList).hasSize(databaseSizeBeforeCreate + 1);
        ElementKindEdition testElementKindEdition = elementKindEditionList.get(elementKindEditionList.size() - 1);
        //assertThat(testElementKindEdition.getEditionDateTime()).isEqualTo(DEFAULT_EDITION_DATE_TIME);
        assertThat(testElementKindEdition.getNewGramPerMeterLinearMass()).isEqualTo(DEFAULT_NEW_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testElementKindEdition.getNewMilimeterDiameter()).isEqualTo(DEFAULT_NEW_MILIMETER_DIAMETER);
        assertThat(testElementKindEdition.getNewMilimeterInsulationThickness()).isEqualTo(DEFAULT_NEW_MILIMETER_INSULATION_THICKNESS);
    }

    @Test
    @Transactional
    void createElementKindEditionWithExistingId() throws Exception {
        // Create the ElementKindEdition with an existing ID
        elementKindEdition.setId(1L);

        int databaseSizeBeforeCreate = elementKindEditionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restElementKindEditionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(elementKindEdition))
            )
            .andExpect(status().isBadRequest());

        // Validate the ElementKindEdition in the database
        List<ElementKindEdition> elementKindEditionList = elementKindEditionRepository.findAll();
        assertThat(elementKindEditionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllElementKindEditions() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        // Get all the elementKindEditionList
        restElementKindEditionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(elementKindEdition.getId().intValue())))
            //.andExpect(jsonPath("$.[*].editionDateTime").value(hasItem(DEFAULT_EDITION_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].newGramPerMeterLinearMass").value(hasItem(DEFAULT_NEW_GRAM_PER_METER_LINEAR_MASS.doubleValue())))
            .andExpect(jsonPath("$.[*].newMilimeterDiameter").value(hasItem(DEFAULT_NEW_MILIMETER_DIAMETER.doubleValue())))
            .andExpect(
                jsonPath("$.[*].newMilimeterInsulationThickness").value(hasItem(DEFAULT_NEW_MILIMETER_INSULATION_THICKNESS.doubleValue()))
            );
    }

    @Test
    @Transactional
    void getElementKindEdition() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        // Get the elementKindEdition
        restElementKindEditionMockMvc
            .perform(get(ENTITY_API_URL_ID, elementKindEdition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(elementKindEdition.getId().intValue()))
            //.andExpect(jsonPath("$.editionDateTime").value(DEFAULT_EDITION_DATE_TIME.toString()))
            .andExpect(jsonPath("$.newGramPerMeterLinearMass").value(DEFAULT_NEW_GRAM_PER_METER_LINEAR_MASS.doubleValue()))
            .andExpect(jsonPath("$.newMilimeterDiameter").value(DEFAULT_NEW_MILIMETER_DIAMETER.doubleValue()))
            .andExpect(jsonPath("$.newMilimeterInsulationThickness").value(DEFAULT_NEW_MILIMETER_INSULATION_THICKNESS.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingElementKindEdition() throws Exception {
        // Get the elementKindEdition
        restElementKindEditionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewElementKindEdition() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        int databaseSizeBeforeUpdate = elementKindEditionRepository.findAll().size();

        // Update the elementKindEdition
        ElementKindEdition updatedElementKindEdition = elementKindEditionRepository.findById(elementKindEdition.getId()).get();
        // Disconnect from session so that the updates on updatedElementKindEdition are not directly saved in db
        em.detach(updatedElementKindEdition);
        updatedElementKindEdition
            .newGramPerMeterLinearMass(UPDATED_NEW_GRAM_PER_METER_LINEAR_MASS)
            .newMilimeterDiameter(UPDATED_NEW_MILIMETER_DIAMETER)
            .newMilimeterInsulationThickness(UPDATED_NEW_MILIMETER_INSULATION_THICKNESS);

        restElementKindEditionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedElementKindEdition.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedElementKindEdition))
            )
            //.andExpect(status().isOk());
            .andExpect(status().isBadRequest());

        // Validate the ElementKindEdition in the database
        List<ElementKindEdition> elementKindEditionList = elementKindEditionRepository.findAll();
        assertThat(elementKindEditionList).hasSize(databaseSizeBeforeUpdate);
        ElementKindEdition testElementKindEdition = elementKindEditionList.get(elementKindEditionList.size() - 1);
        assertThat(testElementKindEdition.getNewGramPerMeterLinearMass()).isEqualTo(DEFAULT_NEW_GRAM_PER_METER_LINEAR_MASS); //UPDATED_NEW_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testElementKindEdition.getNewMilimeterDiameter()).isEqualTo(DEFAULT_NEW_MILIMETER_DIAMETER); //UPDATED_NEW_MILIMETER_DIAMETER);
        assertThat(testElementKindEdition.getNewMilimeterInsulationThickness()).isEqualTo(DEFAULT_NEW_MILIMETER_INSULATION_THICKNESS); //UPDATED_NEW_INSULATION_THICKNESS);
    }
    /*
    @Test
    @Transactional
    void putNonExistingElementKindEdition() throws Exception {
        int databaseSizeBeforeUpdate = elementKindEditionRepository.findAll().size();
        elementKindEdition.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restElementKindEditionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, elementKindEdition.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(elementKindEdition))
            )
            .andExpect(status().isBadRequest());

        // Validate the ElementKindEdition in the database
        List<ElementKindEdition> elementKindEditionList = elementKindEditionRepository.findAll();
        assertThat(elementKindEditionList).hasSize(databaseSizeBeforeUpdate);
    }*/
    /*
    @Test
    @Transactional
    void putWithIdMismatchElementKindEdition() throws Exception {
        int databaseSizeBeforeUpdate = elementKindEditionRepository.findAll().size();
        elementKindEdition.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restElementKindEditionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(elementKindEdition))
            )
            .andExpect(status().isBadRequest());

        // Validate the ElementKindEdition in the database
        List<ElementKindEdition> elementKindEditionList = elementKindEditionRepository.findAll();
        assertThat(elementKindEditionList).hasSize(databaseSizeBeforeUpdate);
    }*/
    /*
    @Test
    @Transactional
    void putWithMissingIdPathParamElementKindEdition() throws Exception {
        int databaseSizeBeforeUpdate = elementKindEditionRepository.findAll().size();
        elementKindEdition.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restElementKindEditionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(elementKindEdition))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ElementKindEdition in the database
        List<ElementKindEdition> elementKindEditionList = elementKindEditionRepository.findAll();
        assertThat(elementKindEditionList).hasSize(databaseSizeBeforeUpdate);
    }*/
    /*
    @Test
    @Transactional
    void partialUpdateElementKindEditionWithPatch() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        int databaseSizeBeforeUpdate = elementKindEditionRepository.findAll().size();

        // Update the elementKindEdition using partial update
        ElementKindEdition partialUpdatedElementKindEdition = new ElementKindEdition();
        partialUpdatedElementKindEdition.setId(elementKindEdition.getId());

        partialUpdatedElementKindEdition.newMilimeterDiameter(UPDATED_NEW_MILIMETER_DIAMETER);

        restElementKindEditionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedElementKindEdition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedElementKindEdition))
            )
            .andExpect(status().isOk());

        // Validate the ElementKindEdition in the database
        List<ElementKindEdition> elementKindEditionList = elementKindEditionRepository.findAll();
        assertThat(elementKindEditionList).hasSize(databaseSizeBeforeUpdate);
        ElementKindEdition testElementKindEdition = elementKindEditionList.get(elementKindEditionList.size() - 1);
        assertThat(testElementKindEdition.getNewGramPerMeterLinearMass()).isEqualTo(DEFAULT_NEW_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testElementKindEdition.getNewMilimeterDiameter()).isEqualTo(UPDATED_NEW_MILIMETER_DIAMETER);
        assertThat(testElementKindEdition.getNewInsulationThickness()).isEqualTo(DEFAULT_NEW_INSULATION_THICKNESS);
    }*/
    /*
    @Test
    @Transactional
    void fullUpdateElementKindEditionWithPatch() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        int databaseSizeBeforeUpdate = elementKindEditionRepository.findAll().size();

        // Update the elementKindEdition using partial update
        ElementKindEdition partialUpdatedElementKindEdition = new ElementKindEdition();
        partialUpdatedElementKindEdition.setId(elementKindEdition.getId());

        partialUpdatedElementKindEdition
            .newGramPerMeterLinearMass(UPDATED_NEW_GRAM_PER_METER_LINEAR_MASS)
            .newMilimeterDiameter(UPDATED_NEW_MILIMETER_DIAMETER)
            .newMilimeterInsulationThickness(UPDATED_NEW_MILIMETER_INSULATION_THICKNESS);

        restElementKindEditionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedElementKindEdition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedElementKindEdition))
            )
            .andExpect(status().isOk());

        // Validate the ElementKindEdition in the database
        List<ElementKindEdition> elementKindEditionList = elementKindEditionRepository.findAll();
        assertThat(elementKindEditionList).hasSize(databaseSizeBeforeUpdate);
        ElementKindEdition testElementKindEdition = elementKindEditionList.get(elementKindEditionList.size() - 1);
        assertThat(testElementKindEdition.getNewGramPerMeterLinearMass()).isEqualTo(UPDATED_NEW_GRAM_PER_METER_LINEAR_MASS);
        assertThat(testElementKindEdition.getNewMilimeterDiameter()).isEqualTo(UPDATED_NEW_MILIMETER_DIAMETER);
        assertThat(testElementKindEdition.getNewInsulationThickness()).isEqualTo(UPDATED_NEW_INSULATION_THICKNESS);
    }*/
    /*
    @Test
    @Transactional
    void patchNonExistingElementKindEdition() throws Exception {
        int databaseSizeBeforeUpdate = elementKindEditionRepository.findAll().size();
        elementKindEdition.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restElementKindEditionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, elementKindEdition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(elementKindEdition))
            )
            .andExpect(status().isBadRequest());

        // Validate the ElementKindEdition in the database
        List<ElementKindEdition> elementKindEditionList = elementKindEditionRepository.findAll();
        assertThat(elementKindEditionList).hasSize(databaseSizeBeforeUpdate);
    }*/
    /*
    @Test
    @Transactional
    void patchWithIdMismatchElementKindEdition() throws Exception {
        int databaseSizeBeforeUpdate = elementKindEditionRepository.findAll().size();
        elementKindEdition.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restElementKindEditionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(elementKindEdition))
            )
            .andExpect(status().isBadRequest());

        // Validate the ElementKindEdition in the database
        List<ElementKindEdition> elementKindEditionList = elementKindEditionRepository.findAll();
        assertThat(elementKindEditionList).hasSize(databaseSizeBeforeUpdate);
    }*/
    /*
    @Test
    @Transactional
    void patchWithMissingIdPathParamElementKindEdition() throws Exception {
        int databaseSizeBeforeUpdate = elementKindEditionRepository.findAll().size();
        elementKindEdition.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restElementKindEditionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(elementKindEdition))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ElementKindEdition in the database
        List<ElementKindEdition> elementKindEditionList = elementKindEditionRepository.findAll();
        assertThat(elementKindEditionList).hasSize(databaseSizeBeforeUpdate);
    }*/
    /*
    @Test
    @Transactional
    void deleteElementKindEdition() throws Exception {
        // Initialize the database
        elementKindEditionRepository.saveAndFlush(elementKindEdition);

        int databaseSizeBeforeDelete = elementKindEditionRepository.findAll().size();

        // Delete the elementKindEdition
        restElementKindEditionMockMvc
            .perform(delete(ENTITY_API_URL_ID, elementKindEdition.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ElementKindEdition> elementKindEditionList = elementKindEditionRepository.findAll();
        assertThat(elementKindEditionList).hasSize(databaseSizeBeforeDelete - 1);
    }*/
}
