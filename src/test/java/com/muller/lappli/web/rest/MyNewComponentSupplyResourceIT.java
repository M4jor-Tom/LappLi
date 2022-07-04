package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.MyNewComponent;
import com.muller.lappli.domain.MyNewComponentSupply;
import com.muller.lappli.domain.SupplyPosition;
import com.muller.lappli.domain.enumeration.MarkingType;
import com.muller.lappli.repository.MyNewComponentSupplyRepository;
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
 * Integration tests for the {@link MyNewComponentSupplyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MyNewComponentSupplyResourceIT {

    private static final Long DEFAULT_APPARITIONS = 1L;
    private static final Long UPDATED_APPARITIONS = 2L;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final MarkingType DEFAULT_MARKING_TYPE = MarkingType.LIFTING;
    private static final MarkingType UPDATED_MARKING_TYPE = MarkingType.SPIRALLY_COLORED;

    private static final String ENTITY_API_URL = "/api/my-new-component-supplies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MyNewComponentSupplyRepository myNewComponentSupplyRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMyNewComponentSupplyMockMvc;

    private MyNewComponentSupply myNewComponentSupply;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MyNewComponentSupply createEntity(EntityManager em) {
        MyNewComponentSupply myNewComponentSupply = new MyNewComponentSupply()
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
        myNewComponentSupply.getOwnerSupplyPositions().add(supplyPosition);
        // Add required entity
        MyNewComponent myNewComponent;
        if (TestUtil.findAll(em, MyNewComponent.class).isEmpty()) {
            myNewComponent = MyNewComponentResourceIT.createEntity(em);
            em.persist(myNewComponent);
            em.flush();
        } else {
            myNewComponent = TestUtil.findAll(em, MyNewComponent.class).get(0);
        }
        myNewComponentSupply.setMyNewComponent(myNewComponent);
        return myNewComponentSupply;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MyNewComponentSupply createUpdatedEntity(EntityManager em) {
        MyNewComponentSupply myNewComponentSupply = new MyNewComponentSupply()
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
        myNewComponentSupply.getOwnerSupplyPositions().add(supplyPosition);
        // Add required entity
        MyNewComponent myNewComponent;
        if (TestUtil.findAll(em, MyNewComponent.class).isEmpty()) {
            myNewComponent = MyNewComponentResourceIT.createUpdatedEntity(em);
            em.persist(myNewComponent);
            em.flush();
        } else {
            myNewComponent = TestUtil.findAll(em, MyNewComponent.class).get(0);
        }
        myNewComponentSupply.setMyNewComponent(myNewComponent);
        return myNewComponentSupply;
    }

    @BeforeEach
    public void initTest() {
        myNewComponentSupply = createEntity(em);
    }

    @Test
    @Transactional
    void createMyNewComponentSupply() throws Exception {
        int databaseSizeBeforeCreate = myNewComponentSupplyRepository.findAll().size();
        // Create the MyNewComponentSupply
        restMyNewComponentSupplyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(myNewComponentSupply))
            )
            .andExpect(status().isCreated());

        // Validate the MyNewComponentSupply in the database
        List<MyNewComponentSupply> myNewComponentSupplyList = myNewComponentSupplyRepository.findAll();
        assertThat(myNewComponentSupplyList).hasSize(databaseSizeBeforeCreate + 1);
        MyNewComponentSupply testMyNewComponentSupply = myNewComponentSupplyList.get(myNewComponentSupplyList.size() - 1);
        assertThat(testMyNewComponentSupply.getApparitions()).isEqualTo(DEFAULT_APPARITIONS);
        assertThat(testMyNewComponentSupply.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMyNewComponentSupply.getMarkingType()).isEqualTo(DEFAULT_MARKING_TYPE);
    }

    @Test
    @Transactional
    void createMyNewComponentSupplyWithExistingId() throws Exception {
        // Create the MyNewComponentSupply with an existing ID
        myNewComponentSupply.setId(1L);

        int databaseSizeBeforeCreate = myNewComponentSupplyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMyNewComponentSupplyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(myNewComponentSupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the MyNewComponentSupply in the database
        List<MyNewComponentSupply> myNewComponentSupplyList = myNewComponentSupplyRepository.findAll();
        assertThat(myNewComponentSupplyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkApparitionsIsRequired() throws Exception {
        int databaseSizeBeforeTest = myNewComponentSupplyRepository.findAll().size();
        // set the field null
        myNewComponentSupply.setApparitions(null);

        // Create the MyNewComponentSupply, which fails.

        restMyNewComponentSupplyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(myNewComponentSupply))
            )
            .andExpect(status().isBadRequest());

        List<MyNewComponentSupply> myNewComponentSupplyList = myNewComponentSupplyRepository.findAll();
        assertThat(myNewComponentSupplyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMarkingTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = myNewComponentSupplyRepository.findAll().size();
        // set the field null
        myNewComponentSupply.setMarkingType(null);

        // Create the MyNewComponentSupply, which fails.

        restMyNewComponentSupplyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(myNewComponentSupply))
            )
            .andExpect(status().isBadRequest());

        List<MyNewComponentSupply> myNewComponentSupplyList = myNewComponentSupplyRepository.findAll();
        assertThat(myNewComponentSupplyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMyNewComponentSupplies() throws Exception {
        // Initialize the database
        myNewComponentSupplyRepository.saveAndFlush(myNewComponentSupply);

        // Get all the myNewComponentSupplyList
        restMyNewComponentSupplyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(myNewComponentSupply.getId().intValue())))
            .andExpect(jsonPath("$.[*].apparitions").value(hasItem(DEFAULT_APPARITIONS.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].markingType").value(hasItem(DEFAULT_MARKING_TYPE.toString())));
    }

    @Test
    @Transactional
    void getMyNewComponentSupply() throws Exception {
        // Initialize the database
        myNewComponentSupplyRepository.saveAndFlush(myNewComponentSupply);

        // Get the myNewComponentSupply
        restMyNewComponentSupplyMockMvc
            .perform(get(ENTITY_API_URL_ID, myNewComponentSupply.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(myNewComponentSupply.getId().intValue()))
            .andExpect(jsonPath("$.apparitions").value(DEFAULT_APPARITIONS.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.markingType").value(DEFAULT_MARKING_TYPE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMyNewComponentSupply() throws Exception {
        // Get the myNewComponentSupply
        restMyNewComponentSupplyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMyNewComponentSupply() throws Exception {
        // Initialize the database
        myNewComponentSupplyRepository.saveAndFlush(myNewComponentSupply);

        int databaseSizeBeforeUpdate = myNewComponentSupplyRepository.findAll().size();

        // Update the myNewComponentSupply
        MyNewComponentSupply updatedMyNewComponentSupply = myNewComponentSupplyRepository.findById(myNewComponentSupply.getId()).get();
        // Disconnect from session so that the updates on updatedMyNewComponentSupply are not directly saved in db
        em.detach(updatedMyNewComponentSupply);
        updatedMyNewComponentSupply.apparitions(UPDATED_APPARITIONS).description(UPDATED_DESCRIPTION).markingType(UPDATED_MARKING_TYPE);

        restMyNewComponentSupplyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMyNewComponentSupply.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMyNewComponentSupply))
            )
            .andExpect(status().isOk());

        // Validate the MyNewComponentSupply in the database
        List<MyNewComponentSupply> myNewComponentSupplyList = myNewComponentSupplyRepository.findAll();
        assertThat(myNewComponentSupplyList).hasSize(databaseSizeBeforeUpdate);
        MyNewComponentSupply testMyNewComponentSupply = myNewComponentSupplyList.get(myNewComponentSupplyList.size() - 1);
        assertThat(testMyNewComponentSupply.getApparitions()).isEqualTo(UPDATED_APPARITIONS);
        assertThat(testMyNewComponentSupply.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMyNewComponentSupply.getMarkingType()).isEqualTo(UPDATED_MARKING_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingMyNewComponentSupply() throws Exception {
        int databaseSizeBeforeUpdate = myNewComponentSupplyRepository.findAll().size();
        myNewComponentSupply.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMyNewComponentSupplyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, myNewComponentSupply.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(myNewComponentSupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the MyNewComponentSupply in the database
        List<MyNewComponentSupply> myNewComponentSupplyList = myNewComponentSupplyRepository.findAll();
        assertThat(myNewComponentSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMyNewComponentSupply() throws Exception {
        int databaseSizeBeforeUpdate = myNewComponentSupplyRepository.findAll().size();
        myNewComponentSupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMyNewComponentSupplyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(myNewComponentSupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the MyNewComponentSupply in the database
        List<MyNewComponentSupply> myNewComponentSupplyList = myNewComponentSupplyRepository.findAll();
        assertThat(myNewComponentSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMyNewComponentSupply() throws Exception {
        int databaseSizeBeforeUpdate = myNewComponentSupplyRepository.findAll().size();
        myNewComponentSupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMyNewComponentSupplyMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(myNewComponentSupply))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MyNewComponentSupply in the database
        List<MyNewComponentSupply> myNewComponentSupplyList = myNewComponentSupplyRepository.findAll();
        assertThat(myNewComponentSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMyNewComponentSupplyWithPatch() throws Exception {
        // Initialize the database
        myNewComponentSupplyRepository.saveAndFlush(myNewComponentSupply);

        int databaseSizeBeforeUpdate = myNewComponentSupplyRepository.findAll().size();

        // Update the myNewComponentSupply using partial update
        MyNewComponentSupply partialUpdatedMyNewComponentSupply = new MyNewComponentSupply();
        partialUpdatedMyNewComponentSupply.setId(myNewComponentSupply.getId());

        partialUpdatedMyNewComponentSupply.apparitions(UPDATED_APPARITIONS).description(UPDATED_DESCRIPTION);

        restMyNewComponentSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMyNewComponentSupply.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMyNewComponentSupply))
            )
            .andExpect(status().isOk());

        // Validate the MyNewComponentSupply in the database
        List<MyNewComponentSupply> myNewComponentSupplyList = myNewComponentSupplyRepository.findAll();
        assertThat(myNewComponentSupplyList).hasSize(databaseSizeBeforeUpdate);
        MyNewComponentSupply testMyNewComponentSupply = myNewComponentSupplyList.get(myNewComponentSupplyList.size() - 1);
        assertThat(testMyNewComponentSupply.getApparitions()).isEqualTo(UPDATED_APPARITIONS);
        assertThat(testMyNewComponentSupply.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMyNewComponentSupply.getMarkingType()).isEqualTo(DEFAULT_MARKING_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateMyNewComponentSupplyWithPatch() throws Exception {
        // Initialize the database
        myNewComponentSupplyRepository.saveAndFlush(myNewComponentSupply);

        int databaseSizeBeforeUpdate = myNewComponentSupplyRepository.findAll().size();

        // Update the myNewComponentSupply using partial update
        MyNewComponentSupply partialUpdatedMyNewComponentSupply = new MyNewComponentSupply();
        partialUpdatedMyNewComponentSupply.setId(myNewComponentSupply.getId());

        partialUpdatedMyNewComponentSupply
            .apparitions(UPDATED_APPARITIONS)
            .description(UPDATED_DESCRIPTION)
            .markingType(UPDATED_MARKING_TYPE);

        restMyNewComponentSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMyNewComponentSupply.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMyNewComponentSupply))
            )
            .andExpect(status().isOk());

        // Validate the MyNewComponentSupply in the database
        List<MyNewComponentSupply> myNewComponentSupplyList = myNewComponentSupplyRepository.findAll();
        assertThat(myNewComponentSupplyList).hasSize(databaseSizeBeforeUpdate);
        MyNewComponentSupply testMyNewComponentSupply = myNewComponentSupplyList.get(myNewComponentSupplyList.size() - 1);
        assertThat(testMyNewComponentSupply.getApparitions()).isEqualTo(UPDATED_APPARITIONS);
        assertThat(testMyNewComponentSupply.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMyNewComponentSupply.getMarkingType()).isEqualTo(UPDATED_MARKING_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingMyNewComponentSupply() throws Exception {
        int databaseSizeBeforeUpdate = myNewComponentSupplyRepository.findAll().size();
        myNewComponentSupply.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMyNewComponentSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, myNewComponentSupply.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(myNewComponentSupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the MyNewComponentSupply in the database
        List<MyNewComponentSupply> myNewComponentSupplyList = myNewComponentSupplyRepository.findAll();
        assertThat(myNewComponentSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMyNewComponentSupply() throws Exception {
        int databaseSizeBeforeUpdate = myNewComponentSupplyRepository.findAll().size();
        myNewComponentSupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMyNewComponentSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(myNewComponentSupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the MyNewComponentSupply in the database
        List<MyNewComponentSupply> myNewComponentSupplyList = myNewComponentSupplyRepository.findAll();
        assertThat(myNewComponentSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMyNewComponentSupply() throws Exception {
        int databaseSizeBeforeUpdate = myNewComponentSupplyRepository.findAll().size();
        myNewComponentSupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMyNewComponentSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(myNewComponentSupply))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MyNewComponentSupply in the database
        List<MyNewComponentSupply> myNewComponentSupplyList = myNewComponentSupplyRepository.findAll();
        assertThat(myNewComponentSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMyNewComponentSupply() throws Exception {
        // Initialize the database
        myNewComponentSupplyRepository.saveAndFlush(myNewComponentSupply);

        int databaseSizeBeforeDelete = myNewComponentSupplyRepository.findAll().size();

        // Delete the myNewComponentSupply
        restMyNewComponentSupplyMockMvc
            .perform(delete(ENTITY_API_URL_ID, myNewComponentSupply.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MyNewComponentSupply> myNewComponentSupplyList = myNewComponentSupplyRepository.findAll();
        assertThat(myNewComponentSupplyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
