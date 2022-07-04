package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.MyNewComponent;
import com.muller.lappli.repository.MyNewComponentRepository;
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
 * Integration tests for the {@link MyNewComponentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MyNewComponentResourceIT {

    private static final Long DEFAULT_NUMBER = 1L;
    private static final Long UPDATED_NUMBER = 2L;

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final Double DEFAULT_DATA = 1D;
    private static final Double UPDATED_DATA = 2D;

    private static final String ENTITY_API_URL = "/api/my-new-components";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MyNewComponentRepository myNewComponentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMyNewComponentMockMvc;

    private MyNewComponent myNewComponent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MyNewComponent createEntity(EntityManager em) {
        MyNewComponent myNewComponent = new MyNewComponent().number(DEFAULT_NUMBER).designation(DEFAULT_DESIGNATION).data(DEFAULT_DATA);
        return myNewComponent;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MyNewComponent createUpdatedEntity(EntityManager em) {
        MyNewComponent myNewComponent = new MyNewComponent().number(UPDATED_NUMBER).designation(UPDATED_DESIGNATION).data(UPDATED_DATA);
        return myNewComponent;
    }

    @BeforeEach
    public void initTest() {
        myNewComponent = createEntity(em);
    }

    @Test
    @Transactional
    void createMyNewComponent() throws Exception {
        int databaseSizeBeforeCreate = myNewComponentRepository.findAll().size();
        // Create the MyNewComponent
        restMyNewComponentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(myNewComponent))
            )
            .andExpect(status().isCreated());

        // Validate the MyNewComponent in the database
        List<MyNewComponent> myNewComponentList = myNewComponentRepository.findAll();
        assertThat(myNewComponentList).hasSize(databaseSizeBeforeCreate + 1);
        MyNewComponent testMyNewComponent = myNewComponentList.get(myNewComponentList.size() - 1);
        assertThat(testMyNewComponent.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testMyNewComponent.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testMyNewComponent.getData()).isEqualTo(DEFAULT_DATA);
    }

    @Test
    @Transactional
    void createMyNewComponentWithExistingId() throws Exception {
        // Create the MyNewComponent with an existing ID
        myNewComponent.setId(1L);

        int databaseSizeBeforeCreate = myNewComponentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMyNewComponentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(myNewComponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the MyNewComponent in the database
        List<MyNewComponent> myNewComponentList = myNewComponentRepository.findAll();
        assertThat(myNewComponentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = myNewComponentRepository.findAll().size();
        // set the field null
        myNewComponent.setNumber(null);

        // Create the MyNewComponent, which fails.

        restMyNewComponentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(myNewComponent))
            )
            .andExpect(status().isBadRequest());

        List<MyNewComponent> myNewComponentList = myNewComponentRepository.findAll();
        assertThat(myNewComponentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = myNewComponentRepository.findAll().size();
        // set the field null
        myNewComponent.setDesignation(null);

        // Create the MyNewComponent, which fails.

        restMyNewComponentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(myNewComponent))
            )
            .andExpect(status().isBadRequest());

        List<MyNewComponent> myNewComponentList = myNewComponentRepository.findAll();
        assertThat(myNewComponentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = myNewComponentRepository.findAll().size();
        // set the field null
        myNewComponent.setData(null);

        // Create the MyNewComponent, which fails.

        restMyNewComponentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(myNewComponent))
            )
            .andExpect(status().isBadRequest());

        List<MyNewComponent> myNewComponentList = myNewComponentRepository.findAll();
        assertThat(myNewComponentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMyNewComponents() throws Exception {
        // Initialize the database
        myNewComponentRepository.saveAndFlush(myNewComponent);

        // Get all the myNewComponentList
        restMyNewComponentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(myNewComponent.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.doubleValue())));
    }

    @Test
    @Transactional
    void getMyNewComponent() throws Exception {
        // Initialize the database
        myNewComponentRepository.saveAndFlush(myNewComponent);

        // Get the myNewComponent
        restMyNewComponentMockMvc
            .perform(get(ENTITY_API_URL_ID, myNewComponent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(myNewComponent.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.intValue()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingMyNewComponent() throws Exception {
        // Get the myNewComponent
        restMyNewComponentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMyNewComponent() throws Exception {
        // Initialize the database
        myNewComponentRepository.saveAndFlush(myNewComponent);

        int databaseSizeBeforeUpdate = myNewComponentRepository.findAll().size();

        // Update the myNewComponent
        MyNewComponent updatedMyNewComponent = myNewComponentRepository.findById(myNewComponent.getId()).get();
        // Disconnect from session so that the updates on updatedMyNewComponent are not directly saved in db
        em.detach(updatedMyNewComponent);
        updatedMyNewComponent.number(UPDATED_NUMBER).designation(UPDATED_DESIGNATION).data(UPDATED_DATA);

        restMyNewComponentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMyNewComponent.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMyNewComponent))
            )
            .andExpect(status().isOk());

        // Validate the MyNewComponent in the database
        List<MyNewComponent> myNewComponentList = myNewComponentRepository.findAll();
        assertThat(myNewComponentList).hasSize(databaseSizeBeforeUpdate);
        MyNewComponent testMyNewComponent = myNewComponentList.get(myNewComponentList.size() - 1);
        assertThat(testMyNewComponent.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testMyNewComponent.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testMyNewComponent.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    @Transactional
    void putNonExistingMyNewComponent() throws Exception {
        int databaseSizeBeforeUpdate = myNewComponentRepository.findAll().size();
        myNewComponent.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMyNewComponentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, myNewComponent.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(myNewComponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the MyNewComponent in the database
        List<MyNewComponent> myNewComponentList = myNewComponentRepository.findAll();
        assertThat(myNewComponentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMyNewComponent() throws Exception {
        int databaseSizeBeforeUpdate = myNewComponentRepository.findAll().size();
        myNewComponent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMyNewComponentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(myNewComponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the MyNewComponent in the database
        List<MyNewComponent> myNewComponentList = myNewComponentRepository.findAll();
        assertThat(myNewComponentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMyNewComponent() throws Exception {
        int databaseSizeBeforeUpdate = myNewComponentRepository.findAll().size();
        myNewComponent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMyNewComponentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(myNewComponent)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MyNewComponent in the database
        List<MyNewComponent> myNewComponentList = myNewComponentRepository.findAll();
        assertThat(myNewComponentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMyNewComponentWithPatch() throws Exception {
        // Initialize the database
        myNewComponentRepository.saveAndFlush(myNewComponent);

        int databaseSizeBeforeUpdate = myNewComponentRepository.findAll().size();

        // Update the myNewComponent using partial update
        MyNewComponent partialUpdatedMyNewComponent = new MyNewComponent();
        partialUpdatedMyNewComponent.setId(myNewComponent.getId());

        partialUpdatedMyNewComponent.data(UPDATED_DATA);

        restMyNewComponentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMyNewComponent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMyNewComponent))
            )
            .andExpect(status().isOk());

        // Validate the MyNewComponent in the database
        List<MyNewComponent> myNewComponentList = myNewComponentRepository.findAll();
        assertThat(myNewComponentList).hasSize(databaseSizeBeforeUpdate);
        MyNewComponent testMyNewComponent = myNewComponentList.get(myNewComponentList.size() - 1);
        assertThat(testMyNewComponent.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testMyNewComponent.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testMyNewComponent.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    @Transactional
    void fullUpdateMyNewComponentWithPatch() throws Exception {
        // Initialize the database
        myNewComponentRepository.saveAndFlush(myNewComponent);

        int databaseSizeBeforeUpdate = myNewComponentRepository.findAll().size();

        // Update the myNewComponent using partial update
        MyNewComponent partialUpdatedMyNewComponent = new MyNewComponent();
        partialUpdatedMyNewComponent.setId(myNewComponent.getId());

        partialUpdatedMyNewComponent.number(UPDATED_NUMBER).designation(UPDATED_DESIGNATION).data(UPDATED_DATA);

        restMyNewComponentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMyNewComponent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMyNewComponent))
            )
            .andExpect(status().isOk());

        // Validate the MyNewComponent in the database
        List<MyNewComponent> myNewComponentList = myNewComponentRepository.findAll();
        assertThat(myNewComponentList).hasSize(databaseSizeBeforeUpdate);
        MyNewComponent testMyNewComponent = myNewComponentList.get(myNewComponentList.size() - 1);
        assertThat(testMyNewComponent.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testMyNewComponent.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testMyNewComponent.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    @Transactional
    void patchNonExistingMyNewComponent() throws Exception {
        int databaseSizeBeforeUpdate = myNewComponentRepository.findAll().size();
        myNewComponent.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMyNewComponentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, myNewComponent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(myNewComponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the MyNewComponent in the database
        List<MyNewComponent> myNewComponentList = myNewComponentRepository.findAll();
        assertThat(myNewComponentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMyNewComponent() throws Exception {
        int databaseSizeBeforeUpdate = myNewComponentRepository.findAll().size();
        myNewComponent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMyNewComponentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(myNewComponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the MyNewComponent in the database
        List<MyNewComponent> myNewComponentList = myNewComponentRepository.findAll();
        assertThat(myNewComponentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMyNewComponent() throws Exception {
        int databaseSizeBeforeUpdate = myNewComponentRepository.findAll().size();
        myNewComponent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMyNewComponentMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(myNewComponent))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MyNewComponent in the database
        List<MyNewComponent> myNewComponentList = myNewComponentRepository.findAll();
        assertThat(myNewComponentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMyNewComponent() throws Exception {
        // Initialize the database
        myNewComponentRepository.saveAndFlush(myNewComponent);

        int databaseSizeBeforeDelete = myNewComponentRepository.findAll().size();

        // Delete the myNewComponent
        restMyNewComponentMockMvc
            .perform(delete(ENTITY_API_URL_ID, myNewComponent.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MyNewComponent> myNewComponentList = myNewComponentRepository.findAll();
        assertThat(myNewComponentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
