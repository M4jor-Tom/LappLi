package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.Element;
import com.muller.lappli.domain.ElementSupply;
import com.muller.lappli.domain.Strand;
import com.muller.lappli.domain.enumeration.MarkingType;
import com.muller.lappli.domain.enumeration.SupplyState;
import com.muller.lappli.repository.ElementSupplyRepository;
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
 * Integration tests for the {@link ElementSupplyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ElementSupplyResourceIT {

    private static final SupplyState DEFAULT_SUPPLY_STATE = SupplyState.UNDIVIED;
    private static final SupplyState UPDATED_SUPPLY_STATE = SupplyState.DIVIDED_UNPLACED;

    private static final Long DEFAULT_APPARITIONS = 1L;
    private static final Long UPDATED_APPARITIONS = 2L;

    private static final MarkingType DEFAULT_MARKING_TYPE = MarkingType.LIFTING;
    private static final MarkingType UPDATED_MARKING_TYPE = MarkingType.SPIRALLY_COLORED;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/element-supplies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ElementSupplyRepository elementSupplyRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restElementSupplyMockMvc;

    private ElementSupply elementSupply;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ElementSupply createEntity(EntityManager em) {
        ElementSupply elementSupply = new ElementSupply()
            .supplyState(DEFAULT_SUPPLY_STATE)
            .apparitions(DEFAULT_APPARITIONS)
            .markingType(DEFAULT_MARKING_TYPE)
            .description(DEFAULT_DESCRIPTION);
        // Add required entity
        Element element;
        if (TestUtil.findAll(em, Element.class).isEmpty()) {
            element = ElementResourceIT.createEntity(em);
            em.persist(element);
            em.flush();
        } else {
            element = TestUtil.findAll(em, Element.class).get(0);
        }
        elementSupply.setElement(element);
        // Add required entity
        Strand strand;
        if (TestUtil.findAll(em, Strand.class).isEmpty()) {
            strand = StrandResourceIT.createEntity(em);
            em.persist(strand);
            em.flush();
        } else {
            strand = TestUtil.findAll(em, Strand.class).get(0);
        }
        elementSupply.setStrand(strand);
        return elementSupply;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ElementSupply createUpdatedEntity(EntityManager em) {
        ElementSupply elementSupply = new ElementSupply()
            .supplyState(UPDATED_SUPPLY_STATE)
            .apparitions(UPDATED_APPARITIONS)
            .markingType(UPDATED_MARKING_TYPE)
            .description(UPDATED_DESCRIPTION);
        // Add required entity
        Element element;
        if (TestUtil.findAll(em, Element.class).isEmpty()) {
            element = ElementResourceIT.createUpdatedEntity(em);
            em.persist(element);
            em.flush();
        } else {
            element = TestUtil.findAll(em, Element.class).get(0);
        }
        elementSupply.setElement(element);
        // Add required entity
        Strand strand;
        if (TestUtil.findAll(em, Strand.class).isEmpty()) {
            strand = StrandResourceIT.createUpdatedEntity(em);
            em.persist(strand);
            em.flush();
        } else {
            strand = TestUtil.findAll(em, Strand.class).get(0);
        }
        elementSupply.setStrand(strand);
        return elementSupply;
    }

    @BeforeEach
    public void initTest() {
        elementSupply = createEntity(em);
    }

    @Test
    @Transactional
    void createElementSupply() throws Exception {
        int databaseSizeBeforeCreate = elementSupplyRepository.findAll().size();
        // Create the ElementSupply
        restElementSupplyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(elementSupply)))
            .andExpect(status().isCreated());

        // Validate the ElementSupply in the database
        List<ElementSupply> elementSupplyList = elementSupplyRepository.findAll();
        assertThat(elementSupplyList).hasSize(databaseSizeBeforeCreate + 1);
        ElementSupply testElementSupply = elementSupplyList.get(elementSupplyList.size() - 1);
        assertThat(testElementSupply.getSupplyState()).isEqualTo(DEFAULT_SUPPLY_STATE);
        assertThat(testElementSupply.getApparitions()).isEqualTo(DEFAULT_APPARITIONS);
        assertThat(testElementSupply.getMarkingType()).isEqualTo(DEFAULT_MARKING_TYPE);
        assertThat(testElementSupply.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createElementSupplyWithExistingId() throws Exception {
        // Create the ElementSupply with an existing ID
        elementSupply.setId(1L);

        int databaseSizeBeforeCreate = elementSupplyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restElementSupplyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(elementSupply)))
            .andExpect(status().isBadRequest());

        // Validate the ElementSupply in the database
        List<ElementSupply> elementSupplyList = elementSupplyRepository.findAll();
        assertThat(elementSupplyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSupplyStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = elementSupplyRepository.findAll().size();
        // set the field null
        elementSupply.setSupplyState(null);

        // Create the ElementSupply, which fails.

        restElementSupplyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(elementSupply)))
            .andExpect(status().isBadRequest());

        List<ElementSupply> elementSupplyList = elementSupplyRepository.findAll();
        assertThat(elementSupplyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkApparitionsIsRequired() throws Exception {
        int databaseSizeBeforeTest = elementSupplyRepository.findAll().size();
        // set the field null
        elementSupply.setApparitions(null);

        // Create the ElementSupply, which fails.

        restElementSupplyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(elementSupply)))
            .andExpect(status().isBadRequest());

        List<ElementSupply> elementSupplyList = elementSupplyRepository.findAll();
        assertThat(elementSupplyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMarkingTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = elementSupplyRepository.findAll().size();
        // set the field null
        elementSupply.setMarkingType(null);

        // Create the ElementSupply, which fails.

        restElementSupplyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(elementSupply)))
            .andExpect(status().isBadRequest());

        List<ElementSupply> elementSupplyList = elementSupplyRepository.findAll();
        assertThat(elementSupplyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllElementSupplies() throws Exception {
        // Initialize the database
        elementSupplyRepository.saveAndFlush(elementSupply);

        // Get all the elementSupplyList
        restElementSupplyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(elementSupply.getId().intValue())))
            .andExpect(jsonPath("$.[*].supplyState").value(hasItem(DEFAULT_SUPPLY_STATE.toString())))
            .andExpect(jsonPath("$.[*].apparitions").value(hasItem(DEFAULT_APPARITIONS.intValue())))
            .andExpect(jsonPath("$.[*].markingType").value(hasItem(DEFAULT_MARKING_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getElementSupply() throws Exception {
        // Initialize the database
        elementSupplyRepository.saveAndFlush(elementSupply);

        // Get the elementSupply
        restElementSupplyMockMvc
            .perform(get(ENTITY_API_URL_ID, elementSupply.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(elementSupply.getId().intValue()))
            .andExpect(jsonPath("$.supplyState").value(DEFAULT_SUPPLY_STATE.toString()))
            .andExpect(jsonPath("$.apparitions").value(DEFAULT_APPARITIONS.intValue()))
            .andExpect(jsonPath("$.markingType").value(DEFAULT_MARKING_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingElementSupply() throws Exception {
        // Get the elementSupply
        restElementSupplyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewElementSupply() throws Exception {
        // Initialize the database
        elementSupplyRepository.saveAndFlush(elementSupply);

        int databaseSizeBeforeUpdate = elementSupplyRepository.findAll().size();

        // Update the elementSupply
        ElementSupply updatedElementSupply = elementSupplyRepository.findById(elementSupply.getId()).get();
        // Disconnect from session so that the updates on updatedElementSupply are not directly saved in db
        em.detach(updatedElementSupply);
        updatedElementSupply
            .supplyState(UPDATED_SUPPLY_STATE)
            .apparitions(UPDATED_APPARITIONS)
            .markingType(UPDATED_MARKING_TYPE)
            .description(UPDATED_DESCRIPTION);

        restElementSupplyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedElementSupply.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedElementSupply))
            )
            .andExpect(status().isOk());

        // Validate the ElementSupply in the database
        List<ElementSupply> elementSupplyList = elementSupplyRepository.findAll();
        assertThat(elementSupplyList).hasSize(databaseSizeBeforeUpdate);
        ElementSupply testElementSupply = elementSupplyList.get(elementSupplyList.size() - 1);
        assertThat(testElementSupply.getSupplyState()).isEqualTo(UPDATED_SUPPLY_STATE);
        assertThat(testElementSupply.getApparitions()).isEqualTo(UPDATED_APPARITIONS);
        assertThat(testElementSupply.getMarkingType()).isEqualTo(UPDATED_MARKING_TYPE);
        assertThat(testElementSupply.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingElementSupply() throws Exception {
        int databaseSizeBeforeUpdate = elementSupplyRepository.findAll().size();
        elementSupply.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restElementSupplyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, elementSupply.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(elementSupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the ElementSupply in the database
        List<ElementSupply> elementSupplyList = elementSupplyRepository.findAll();
        assertThat(elementSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchElementSupply() throws Exception {
        int databaseSizeBeforeUpdate = elementSupplyRepository.findAll().size();
        elementSupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restElementSupplyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(elementSupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the ElementSupply in the database
        List<ElementSupply> elementSupplyList = elementSupplyRepository.findAll();
        assertThat(elementSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamElementSupply() throws Exception {
        int databaseSizeBeforeUpdate = elementSupplyRepository.findAll().size();
        elementSupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restElementSupplyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(elementSupply)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ElementSupply in the database
        List<ElementSupply> elementSupplyList = elementSupplyRepository.findAll();
        assertThat(elementSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateElementSupplyWithPatch() throws Exception {
        // Initialize the database
        elementSupplyRepository.saveAndFlush(elementSupply);

        int databaseSizeBeforeUpdate = elementSupplyRepository.findAll().size();

        // Update the elementSupply using partial update
        ElementSupply partialUpdatedElementSupply = new ElementSupply();
        partialUpdatedElementSupply.setId(elementSupply.getId());

        partialUpdatedElementSupply.supplyState(UPDATED_SUPPLY_STATE).markingType(UPDATED_MARKING_TYPE);

        restElementSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedElementSupply.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedElementSupply))
            )
            .andExpect(status().isOk());

        // Validate the ElementSupply in the database
        List<ElementSupply> elementSupplyList = elementSupplyRepository.findAll();
        assertThat(elementSupplyList).hasSize(databaseSizeBeforeUpdate);
        ElementSupply testElementSupply = elementSupplyList.get(elementSupplyList.size() - 1);
        assertThat(testElementSupply.getSupplyState()).isEqualTo(UPDATED_SUPPLY_STATE);
        assertThat(testElementSupply.getApparitions()).isEqualTo(DEFAULT_APPARITIONS);
        assertThat(testElementSupply.getMarkingType()).isEqualTo(UPDATED_MARKING_TYPE);
        assertThat(testElementSupply.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateElementSupplyWithPatch() throws Exception {
        // Initialize the database
        elementSupplyRepository.saveAndFlush(elementSupply);

        int databaseSizeBeforeUpdate = elementSupplyRepository.findAll().size();

        // Update the elementSupply using partial update
        ElementSupply partialUpdatedElementSupply = new ElementSupply();
        partialUpdatedElementSupply.setId(elementSupply.getId());

        partialUpdatedElementSupply
            .supplyState(UPDATED_SUPPLY_STATE)
            .apparitions(UPDATED_APPARITIONS)
            .markingType(UPDATED_MARKING_TYPE)
            .description(UPDATED_DESCRIPTION);

        restElementSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedElementSupply.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedElementSupply))
            )
            .andExpect(status().isOk());

        // Validate the ElementSupply in the database
        List<ElementSupply> elementSupplyList = elementSupplyRepository.findAll();
        assertThat(elementSupplyList).hasSize(databaseSizeBeforeUpdate);
        ElementSupply testElementSupply = elementSupplyList.get(elementSupplyList.size() - 1);
        assertThat(testElementSupply.getSupplyState()).isEqualTo(UPDATED_SUPPLY_STATE);
        assertThat(testElementSupply.getApparitions()).isEqualTo(UPDATED_APPARITIONS);
        assertThat(testElementSupply.getMarkingType()).isEqualTo(UPDATED_MARKING_TYPE);
        assertThat(testElementSupply.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingElementSupply() throws Exception {
        int databaseSizeBeforeUpdate = elementSupplyRepository.findAll().size();
        elementSupply.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restElementSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, elementSupply.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(elementSupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the ElementSupply in the database
        List<ElementSupply> elementSupplyList = elementSupplyRepository.findAll();
        assertThat(elementSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchElementSupply() throws Exception {
        int databaseSizeBeforeUpdate = elementSupplyRepository.findAll().size();
        elementSupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restElementSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(elementSupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the ElementSupply in the database
        List<ElementSupply> elementSupplyList = elementSupplyRepository.findAll();
        assertThat(elementSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamElementSupply() throws Exception {
        int databaseSizeBeforeUpdate = elementSupplyRepository.findAll().size();
        elementSupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restElementSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(elementSupply))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ElementSupply in the database
        List<ElementSupply> elementSupplyList = elementSupplyRepository.findAll();
        assertThat(elementSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteElementSupply() throws Exception {
        // Initialize the database
        elementSupplyRepository.saveAndFlush(elementSupply);

        int databaseSizeBeforeDelete = elementSupplyRepository.findAll().size();

        // Delete the elementSupply
        restElementSupplyMockMvc
            .perform(delete(ENTITY_API_URL_ID, elementSupply.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ElementSupply> elementSupplyList = elementSupplyRepository.findAll();
        assertThat(elementSupplyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
