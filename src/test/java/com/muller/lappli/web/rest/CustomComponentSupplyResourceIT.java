package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.CustomComponent;
import com.muller.lappli.domain.CustomComponentSupply;
import com.muller.lappli.domain.SupplyPosition;
import com.muller.lappli.domain.enumeration.MarkingType;
import com.muller.lappli.repository.CustomComponentSupplyRepository;
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
 * Integration tests for the {@link CustomComponentSupplyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustomComponentSupplyResourceIT {

    private static final Long DEFAULT_APPARITIONS = 1L;
    private static final Long UPDATED_APPARITIONS = 2L;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final MarkingType DEFAULT_MARKING_TYPE = MarkingType.LIFTING;
    private static final MarkingType UPDATED_MARKING_TYPE = MarkingType.SPIRALLY_COLORED;

    private static final String ENTITY_API_URL = "/api/custom-component-supplies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustomComponentSupplyRepository customComponentSupplyRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomComponentSupplyMockMvc;

    private CustomComponentSupply customComponentSupply;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomComponentSupply createEntity(EntityManager em) {
        CustomComponentSupply customComponentSupply = new CustomComponentSupply()
            .apparitions(DEFAULT_APPARITIONS)
            .description(DEFAULT_DESCRIPTION)
            .markingType(DEFAULT_MARKING_TYPE);
        // Add required entity
        SupplyPosition supplyPosition;
        if (TestUtil.findAll(em, SupplyPosition.class).isEmpty()) {
            supplyPosition = SupplyPositionResourceIT.createEntity(em);
            em.persist(supplyPosition);
            em.flush();
        } else {
            supplyPosition = TestUtil.findAll(em, SupplyPosition.class).get(0);
        }
        customComponentSupply.getOwnerSupplyPositions().add(supplyPosition);
        // Add required entity
        CustomComponent customComponent;
        if (TestUtil.findAll(em, CustomComponent.class).isEmpty()) {
            customComponent = CustomComponentResourceIT.createEntity(em);
            em.persist(customComponent);
            em.flush();
        } else {
            customComponent = TestUtil.findAll(em, CustomComponent.class).get(0);
        }
        customComponentSupply.setCustomComponent(customComponent);
        return customComponentSupply;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomComponentSupply createUpdatedEntity(EntityManager em) {
        CustomComponentSupply customComponentSupply = new CustomComponentSupply()
            .apparitions(UPDATED_APPARITIONS)
            .description(UPDATED_DESCRIPTION)
            .markingType(UPDATED_MARKING_TYPE);
        // Add required entity
        SupplyPosition supplyPosition;
        if (TestUtil.findAll(em, SupplyPosition.class).isEmpty()) {
            supplyPosition = SupplyPositionResourceIT.createUpdatedEntity(em);
            em.persist(supplyPosition);
            em.flush();
        } else {
            supplyPosition = TestUtil.findAll(em, SupplyPosition.class).get(0);
        }
        customComponentSupply.getOwnerSupplyPositions().add(supplyPosition);
        // Add required entity
        CustomComponent customComponent;
        if (TestUtil.findAll(em, CustomComponent.class).isEmpty()) {
            customComponent = CustomComponentResourceIT.createUpdatedEntity(em);
            em.persist(customComponent);
            em.flush();
        } else {
            customComponent = TestUtil.findAll(em, CustomComponent.class).get(0);
        }
        customComponentSupply.setCustomComponent(customComponent);
        return customComponentSupply;
    }

    @BeforeEach
    public void initTest() {
        customComponentSupply = createEntity(em);
    }

    @Test
    @Transactional
    void createCustomComponentSupply() throws Exception {
        int databaseSizeBeforeCreate = customComponentSupplyRepository.findAll().size();
        // Create the CustomComponentSupply
        restCustomComponentSupplyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customComponentSupply))
            )
            .andExpect(status().isCreated());

        // Validate the CustomComponentSupply in the database
        List<CustomComponentSupply> customComponentSupplyList = customComponentSupplyRepository.findAll();
        assertThat(customComponentSupplyList).hasSize(databaseSizeBeforeCreate + 1);
        CustomComponentSupply testCustomComponentSupply = customComponentSupplyList.get(customComponentSupplyList.size() - 1);
        assertThat(testCustomComponentSupply.getApparitions()).isEqualTo(DEFAULT_APPARITIONS);
        assertThat(testCustomComponentSupply.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCustomComponentSupply.getMarkingType()).isEqualTo(DEFAULT_MARKING_TYPE);
    }

    @Test
    @Transactional
    void createCustomComponentSupplyWithExistingId() throws Exception {
        // Create the CustomComponentSupply with an existing ID
        customComponentSupply.setId(1L);

        int databaseSizeBeforeCreate = customComponentSupplyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomComponentSupplyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customComponentSupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomComponentSupply in the database
        List<CustomComponentSupply> customComponentSupplyList = customComponentSupplyRepository.findAll();
        assertThat(customComponentSupplyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkApparitionsIsRequired() throws Exception {
        int databaseSizeBeforeTest = customComponentSupplyRepository.findAll().size();
        // set the field null
        customComponentSupply.setApparitions(null);

        // Create the CustomComponentSupply, which fails.

        restCustomComponentSupplyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customComponentSupply))
            )
            .andExpect(status().isBadRequest());

        List<CustomComponentSupply> customComponentSupplyList = customComponentSupplyRepository.findAll();
        assertThat(customComponentSupplyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMarkingTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = customComponentSupplyRepository.findAll().size();
        // set the field null
        customComponentSupply.setMarkingType(null);

        // Create the CustomComponentSupply, which fails.

        restCustomComponentSupplyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customComponentSupply))
            )
            .andExpect(status().isBadRequest());

        List<CustomComponentSupply> customComponentSupplyList = customComponentSupplyRepository.findAll();
        assertThat(customComponentSupplyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustomComponentSupplies() throws Exception {
        // Initialize the database
        customComponentSupplyRepository.saveAndFlush(customComponentSupply);

        // Get all the customComponentSupplyList
        restCustomComponentSupplyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customComponentSupply.getId().intValue())))
            .andExpect(jsonPath("$.[*].apparitions").value(hasItem(DEFAULT_APPARITIONS.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].markingType").value(hasItem(DEFAULT_MARKING_TYPE.toString())));
    }

    @Test
    @Transactional
    void getCustomComponentSupply() throws Exception {
        // Initialize the database
        customComponentSupplyRepository.saveAndFlush(customComponentSupply);

        // Get the customComponentSupply
        restCustomComponentSupplyMockMvc
            .perform(get(ENTITY_API_URL_ID, customComponentSupply.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customComponentSupply.getId().intValue()))
            .andExpect(jsonPath("$.apparitions").value(DEFAULT_APPARITIONS.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.markingType").value(DEFAULT_MARKING_TYPE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCustomComponentSupply() throws Exception {
        // Get the customComponentSupply
        restCustomComponentSupplyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustomComponentSupply() throws Exception {
        // Initialize the database
        customComponentSupplyRepository.saveAndFlush(customComponentSupply);

        int databaseSizeBeforeUpdate = customComponentSupplyRepository.findAll().size();

        // Update the customComponentSupply
        CustomComponentSupply updatedCustomComponentSupply = customComponentSupplyRepository.findById(customComponentSupply.getId()).get();
        // Disconnect from session so that the updates on updatedCustomComponentSupply are not directly saved in db
        em.detach(updatedCustomComponentSupply);
        updatedCustomComponentSupply.apparitions(UPDATED_APPARITIONS).description(UPDATED_DESCRIPTION).markingType(UPDATED_MARKING_TYPE);

        restCustomComponentSupplyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCustomComponentSupply.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCustomComponentSupply))
            )
            .andExpect(status().isOk());

        // Validate the CustomComponentSupply in the database
        List<CustomComponentSupply> customComponentSupplyList = customComponentSupplyRepository.findAll();
        assertThat(customComponentSupplyList).hasSize(databaseSizeBeforeUpdate);
        CustomComponentSupply testCustomComponentSupply = customComponentSupplyList.get(customComponentSupplyList.size() - 1);
        assertThat(testCustomComponentSupply.getApparitions()).isEqualTo(UPDATED_APPARITIONS);
        assertThat(testCustomComponentSupply.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCustomComponentSupply.getMarkingType()).isEqualTo(UPDATED_MARKING_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingCustomComponentSupply() throws Exception {
        int databaseSizeBeforeUpdate = customComponentSupplyRepository.findAll().size();
        customComponentSupply.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomComponentSupplyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, customComponentSupply.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customComponentSupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomComponentSupply in the database
        List<CustomComponentSupply> customComponentSupplyList = customComponentSupplyRepository.findAll();
        assertThat(customComponentSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustomComponentSupply() throws Exception {
        int databaseSizeBeforeUpdate = customComponentSupplyRepository.findAll().size();
        customComponentSupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomComponentSupplyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customComponentSupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomComponentSupply in the database
        List<CustomComponentSupply> customComponentSupplyList = customComponentSupplyRepository.findAll();
        assertThat(customComponentSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustomComponentSupply() throws Exception {
        int databaseSizeBeforeUpdate = customComponentSupplyRepository.findAll().size();
        customComponentSupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomComponentSupplyMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customComponentSupply))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomComponentSupply in the database
        List<CustomComponentSupply> customComponentSupplyList = customComponentSupplyRepository.findAll();
        assertThat(customComponentSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustomComponentSupplyWithPatch() throws Exception {
        // Initialize the database
        customComponentSupplyRepository.saveAndFlush(customComponentSupply);

        int databaseSizeBeforeUpdate = customComponentSupplyRepository.findAll().size();

        // Update the customComponentSupply using partial update
        CustomComponentSupply partialUpdatedCustomComponentSupply = new CustomComponentSupply();
        partialUpdatedCustomComponentSupply.setId(customComponentSupply.getId());

        partialUpdatedCustomComponentSupply.apparitions(UPDATED_APPARITIONS);

        restCustomComponentSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomComponentSupply.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomComponentSupply))
            )
            .andExpect(status().isOk());

        // Validate the CustomComponentSupply in the database
        List<CustomComponentSupply> customComponentSupplyList = customComponentSupplyRepository.findAll();
        assertThat(customComponentSupplyList).hasSize(databaseSizeBeforeUpdate);
        CustomComponentSupply testCustomComponentSupply = customComponentSupplyList.get(customComponentSupplyList.size() - 1);
        assertThat(testCustomComponentSupply.getApparitions()).isEqualTo(UPDATED_APPARITIONS);
        assertThat(testCustomComponentSupply.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCustomComponentSupply.getMarkingType()).isEqualTo(DEFAULT_MARKING_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateCustomComponentSupplyWithPatch() throws Exception {
        // Initialize the database
        customComponentSupplyRepository.saveAndFlush(customComponentSupply);

        int databaseSizeBeforeUpdate = customComponentSupplyRepository.findAll().size();

        // Update the customComponentSupply using partial update
        CustomComponentSupply partialUpdatedCustomComponentSupply = new CustomComponentSupply();
        partialUpdatedCustomComponentSupply.setId(customComponentSupply.getId());

        partialUpdatedCustomComponentSupply
            .apparitions(UPDATED_APPARITIONS)
            .description(UPDATED_DESCRIPTION)
            .markingType(UPDATED_MARKING_TYPE);

        restCustomComponentSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomComponentSupply.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomComponentSupply))
            )
            .andExpect(status().isOk());

        // Validate the CustomComponentSupply in the database
        List<CustomComponentSupply> customComponentSupplyList = customComponentSupplyRepository.findAll();
        assertThat(customComponentSupplyList).hasSize(databaseSizeBeforeUpdate);
        CustomComponentSupply testCustomComponentSupply = customComponentSupplyList.get(customComponentSupplyList.size() - 1);
        assertThat(testCustomComponentSupply.getApparitions()).isEqualTo(UPDATED_APPARITIONS);
        assertThat(testCustomComponentSupply.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCustomComponentSupply.getMarkingType()).isEqualTo(UPDATED_MARKING_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingCustomComponentSupply() throws Exception {
        int databaseSizeBeforeUpdate = customComponentSupplyRepository.findAll().size();
        customComponentSupply.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomComponentSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, customComponentSupply.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customComponentSupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomComponentSupply in the database
        List<CustomComponentSupply> customComponentSupplyList = customComponentSupplyRepository.findAll();
        assertThat(customComponentSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustomComponentSupply() throws Exception {
        int databaseSizeBeforeUpdate = customComponentSupplyRepository.findAll().size();
        customComponentSupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomComponentSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customComponentSupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomComponentSupply in the database
        List<CustomComponentSupply> customComponentSupplyList = customComponentSupplyRepository.findAll();
        assertThat(customComponentSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustomComponentSupply() throws Exception {
        int databaseSizeBeforeUpdate = customComponentSupplyRepository.findAll().size();
        customComponentSupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomComponentSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customComponentSupply))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomComponentSupply in the database
        List<CustomComponentSupply> customComponentSupplyList = customComponentSupplyRepository.findAll();
        assertThat(customComponentSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustomComponentSupply() throws Exception {
        // Initialize the database
        customComponentSupplyRepository.saveAndFlush(customComponentSupply);

        int databaseSizeBeforeDelete = customComponentSupplyRepository.findAll().size();

        // Delete the customComponentSupply
        restCustomComponentSupplyMockMvc
            .perform(delete(ENTITY_API_URL_ID, customComponentSupply.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomComponentSupply> customComponentSupplyList = customComponentSupplyRepository.findAll();
        assertThat(customComponentSupplyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
