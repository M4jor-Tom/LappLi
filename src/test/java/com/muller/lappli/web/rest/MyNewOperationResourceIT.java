package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.MyNewOperation;
import com.muller.lappli.domain.StrandSupply;
import com.muller.lappli.repository.MyNewOperationRepository;
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
 * Integration tests for the {@link MyNewOperationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MyNewOperationResourceIT {

    private static final Long DEFAULT_OPERATION_LAYER = 1L;
    private static final Long UPDATED_OPERATION_LAYER = 2L;

    private static final Double DEFAULT_OPERATION_DATA = 1D;
    private static final Double UPDATED_OPERATION_DATA = 2D;

    private static final Long DEFAULT_ANONYMOUS_MY_NEW_COMPONENT_NUMBER = 1L;
    private static final Long UPDATED_ANONYMOUS_MY_NEW_COMPONENT_NUMBER = 2L;

    private static final String DEFAULT_ANONYMOUS_MY_NEW_COMPONENT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_ANONYMOUS_MY_NEW_COMPONENT_DESIGNATION = "BBBBBBBBBB";

    private static final Double DEFAULT_ANONYMOUS_MY_NEW_COMPONENT_DATA = 1D;
    private static final Double UPDATED_ANONYMOUS_MY_NEW_COMPONENT_DATA = 2D;

    private static final String ENTITY_API_URL = "/api/my-new-operations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MyNewOperationRepository myNewOperationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMyNewOperationMockMvc;

    private MyNewOperation myNewOperation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MyNewOperation createEntity(EntityManager em) {
        MyNewOperation myNewOperation = new MyNewOperation()
            .operationLayer(DEFAULT_OPERATION_LAYER)
            .operationData(DEFAULT_OPERATION_DATA)
            .anonymousMyNewComponentNumber(DEFAULT_ANONYMOUS_MY_NEW_COMPONENT_NUMBER)
            .anonymousMyNewComponentDesignation(DEFAULT_ANONYMOUS_MY_NEW_COMPONENT_DESIGNATION)
            .anonymousMyNewComponentData(DEFAULT_ANONYMOUS_MY_NEW_COMPONENT_DATA);
        // Add required entity
        StrandSupply strandSupply;
        if (TestUtil.findAll(em, StrandSupply.class).isEmpty()) {
            strandSupply = StrandSupplyResourceIT.createEntity(em);
            em.persist(strandSupply);
            em.flush();
        } else {
            strandSupply = TestUtil.findAll(em, StrandSupply.class).get(0);
        }
        myNewOperation.setOwnerStrandSupply(strandSupply);
        return myNewOperation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MyNewOperation createUpdatedEntity(EntityManager em) {
        MyNewOperation myNewOperation = new MyNewOperation()
            .operationLayer(UPDATED_OPERATION_LAYER)
            .operationData(UPDATED_OPERATION_DATA)
            .anonymousMyNewComponentNumber(UPDATED_ANONYMOUS_MY_NEW_COMPONENT_NUMBER)
            .anonymousMyNewComponentDesignation(UPDATED_ANONYMOUS_MY_NEW_COMPONENT_DESIGNATION)
            .anonymousMyNewComponentData(UPDATED_ANONYMOUS_MY_NEW_COMPONENT_DATA);
        // Add required entity
        StrandSupply strandSupply;
        if (TestUtil.findAll(em, StrandSupply.class).isEmpty()) {
            strandSupply = StrandSupplyResourceIT.createUpdatedEntity(em);
            em.persist(strandSupply);
            em.flush();
        } else {
            strandSupply = TestUtil.findAll(em, StrandSupply.class).get(0);
        }
        myNewOperation.setOwnerStrandSupply(strandSupply);
        return myNewOperation;
    }

    @BeforeEach
    public void initTest() {
        myNewOperation = createEntity(em);
    }

    @Test
    @Transactional
    void createMyNewOperation() throws Exception {
        int databaseSizeBeforeCreate = myNewOperationRepository.findAll().size();
        // Create the MyNewOperation
        restMyNewOperationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(myNewOperation))
            )
            .andExpect(status().isCreated());

        // Validate the MyNewOperation in the database
        List<MyNewOperation> myNewOperationList = myNewOperationRepository.findAll();
        assertThat(myNewOperationList).hasSize(databaseSizeBeforeCreate + 1);
        MyNewOperation testMyNewOperation = myNewOperationList.get(myNewOperationList.size() - 1);
        assertThat(testMyNewOperation.getOperationLayer()).isEqualTo(DEFAULT_OPERATION_LAYER);
        assertThat(testMyNewOperation.getOperationData()).isEqualTo(DEFAULT_OPERATION_DATA);
        assertThat(testMyNewOperation.getAnonymousMyNewComponentNumber()).isEqualTo(DEFAULT_ANONYMOUS_MY_NEW_COMPONENT_NUMBER);
        assertThat(testMyNewOperation.getAnonymousMyNewComponentDesignation()).isEqualTo(DEFAULT_ANONYMOUS_MY_NEW_COMPONENT_DESIGNATION);
        assertThat(testMyNewOperation.getAnonymousMyNewComponentData()).isEqualTo(DEFAULT_ANONYMOUS_MY_NEW_COMPONENT_DATA);
    }

    @Test
    @Transactional
    void createMyNewOperationWithExistingId() throws Exception {
        // Create the MyNewOperation with an existing ID
        myNewOperation.setId(1L);

        int databaseSizeBeforeCreate = myNewOperationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMyNewOperationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(myNewOperation))
            )
            .andExpect(status().isBadRequest());

        // Validate the MyNewOperation in the database
        List<MyNewOperation> myNewOperationList = myNewOperationRepository.findAll();
        assertThat(myNewOperationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkOperationLayerIsRequired() throws Exception {
        int databaseSizeBeforeTest = myNewOperationRepository.findAll().size();
        // set the field null
        myNewOperation.setOperationLayer(null);

        // Create the MyNewOperation, which fails.

        restMyNewOperationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(myNewOperation))
            )
            .andExpect(status().isBadRequest());

        List<MyNewOperation> myNewOperationList = myNewOperationRepository.findAll();
        assertThat(myNewOperationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOperationDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = myNewOperationRepository.findAll().size();
        // set the field null
        myNewOperation.setOperationData(null);

        // Create the MyNewOperation, which fails.

        restMyNewOperationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(myNewOperation))
            )
            .andExpect(status().isBadRequest());

        List<MyNewOperation> myNewOperationList = myNewOperationRepository.findAll();
        assertThat(myNewOperationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMyNewOperations() throws Exception {
        // Initialize the database
        myNewOperationRepository.saveAndFlush(myNewOperation);

        // Get all the myNewOperationList
        restMyNewOperationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(myNewOperation.getId().intValue())))
            .andExpect(jsonPath("$.[*].operationLayer").value(hasItem(DEFAULT_OPERATION_LAYER.intValue())))
            .andExpect(jsonPath("$.[*].operationData").value(hasItem(DEFAULT_OPERATION_DATA.doubleValue())))
            .andExpect(jsonPath("$.[*].anonymousMyNewComponentNumber").value(hasItem(DEFAULT_ANONYMOUS_MY_NEW_COMPONENT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].anonymousMyNewComponentDesignation").value(hasItem(DEFAULT_ANONYMOUS_MY_NEW_COMPONENT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].anonymousMyNewComponentData").value(hasItem(DEFAULT_ANONYMOUS_MY_NEW_COMPONENT_DATA.doubleValue())));
    }

    @Test
    @Transactional
    void getMyNewOperation() throws Exception {
        // Initialize the database
        myNewOperationRepository.saveAndFlush(myNewOperation);

        // Get the myNewOperation
        restMyNewOperationMockMvc
            .perform(get(ENTITY_API_URL_ID, myNewOperation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(myNewOperation.getId().intValue()))
            .andExpect(jsonPath("$.operationLayer").value(DEFAULT_OPERATION_LAYER.intValue()))
            .andExpect(jsonPath("$.operationData").value(DEFAULT_OPERATION_DATA.doubleValue()))
            .andExpect(jsonPath("$.anonymousMyNewComponentNumber").value(DEFAULT_ANONYMOUS_MY_NEW_COMPONENT_NUMBER.intValue()))
            .andExpect(jsonPath("$.anonymousMyNewComponentDesignation").value(DEFAULT_ANONYMOUS_MY_NEW_COMPONENT_DESIGNATION))
            .andExpect(jsonPath("$.anonymousMyNewComponentData").value(DEFAULT_ANONYMOUS_MY_NEW_COMPONENT_DATA.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingMyNewOperation() throws Exception {
        // Get the myNewOperation
        restMyNewOperationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMyNewOperation() throws Exception {
        // Initialize the database
        myNewOperationRepository.saveAndFlush(myNewOperation);

        int databaseSizeBeforeUpdate = myNewOperationRepository.findAll().size();

        // Update the myNewOperation
        MyNewOperation updatedMyNewOperation = myNewOperationRepository.findById(myNewOperation.getId()).get();
        // Disconnect from session so that the updates on updatedMyNewOperation are not directly saved in db
        em.detach(updatedMyNewOperation);
        updatedMyNewOperation
            .operationLayer(UPDATED_OPERATION_LAYER)
            .operationData(UPDATED_OPERATION_DATA)
            .anonymousMyNewComponentNumber(UPDATED_ANONYMOUS_MY_NEW_COMPONENT_NUMBER)
            .anonymousMyNewComponentDesignation(UPDATED_ANONYMOUS_MY_NEW_COMPONENT_DESIGNATION)
            .anonymousMyNewComponentData(UPDATED_ANONYMOUS_MY_NEW_COMPONENT_DATA);

        restMyNewOperationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMyNewOperation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMyNewOperation))
            )
            .andExpect(status().isOk());

        // Validate the MyNewOperation in the database
        List<MyNewOperation> myNewOperationList = myNewOperationRepository.findAll();
        assertThat(myNewOperationList).hasSize(databaseSizeBeforeUpdate);
        MyNewOperation testMyNewOperation = myNewOperationList.get(myNewOperationList.size() - 1);
        assertThat(testMyNewOperation.getOperationLayer()).isEqualTo(UPDATED_OPERATION_LAYER);
        assertThat(testMyNewOperation.getOperationData()).isEqualTo(UPDATED_OPERATION_DATA);
        assertThat(testMyNewOperation.getAnonymousMyNewComponentNumber()).isEqualTo(UPDATED_ANONYMOUS_MY_NEW_COMPONENT_NUMBER);
        assertThat(testMyNewOperation.getAnonymousMyNewComponentDesignation()).isEqualTo(UPDATED_ANONYMOUS_MY_NEW_COMPONENT_DESIGNATION);
        assertThat(testMyNewOperation.getAnonymousMyNewComponentData()).isEqualTo(UPDATED_ANONYMOUS_MY_NEW_COMPONENT_DATA);
    }

    @Test
    @Transactional
    void putNonExistingMyNewOperation() throws Exception {
        int databaseSizeBeforeUpdate = myNewOperationRepository.findAll().size();
        myNewOperation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMyNewOperationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, myNewOperation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(myNewOperation))
            )
            .andExpect(status().isBadRequest());

        // Validate the MyNewOperation in the database
        List<MyNewOperation> myNewOperationList = myNewOperationRepository.findAll();
        assertThat(myNewOperationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMyNewOperation() throws Exception {
        int databaseSizeBeforeUpdate = myNewOperationRepository.findAll().size();
        myNewOperation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMyNewOperationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(myNewOperation))
            )
            .andExpect(status().isBadRequest());

        // Validate the MyNewOperation in the database
        List<MyNewOperation> myNewOperationList = myNewOperationRepository.findAll();
        assertThat(myNewOperationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMyNewOperation() throws Exception {
        int databaseSizeBeforeUpdate = myNewOperationRepository.findAll().size();
        myNewOperation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMyNewOperationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(myNewOperation)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MyNewOperation in the database
        List<MyNewOperation> myNewOperationList = myNewOperationRepository.findAll();
        assertThat(myNewOperationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMyNewOperationWithPatch() throws Exception {
        // Initialize the database
        myNewOperationRepository.saveAndFlush(myNewOperation);

        int databaseSizeBeforeUpdate = myNewOperationRepository.findAll().size();

        // Update the myNewOperation using partial update
        MyNewOperation partialUpdatedMyNewOperation = new MyNewOperation();
        partialUpdatedMyNewOperation.setId(myNewOperation.getId());

        restMyNewOperationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMyNewOperation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMyNewOperation))
            )
            .andExpect(status().isOk());

        // Validate the MyNewOperation in the database
        List<MyNewOperation> myNewOperationList = myNewOperationRepository.findAll();
        assertThat(myNewOperationList).hasSize(databaseSizeBeforeUpdate);
        MyNewOperation testMyNewOperation = myNewOperationList.get(myNewOperationList.size() - 1);
        assertThat(testMyNewOperation.getOperationLayer()).isEqualTo(DEFAULT_OPERATION_LAYER);
        assertThat(testMyNewOperation.getOperationData()).isEqualTo(DEFAULT_OPERATION_DATA);
        assertThat(testMyNewOperation.getAnonymousMyNewComponentNumber()).isEqualTo(DEFAULT_ANONYMOUS_MY_NEW_COMPONENT_NUMBER);
        assertThat(testMyNewOperation.getAnonymousMyNewComponentDesignation()).isEqualTo(DEFAULT_ANONYMOUS_MY_NEW_COMPONENT_DESIGNATION);
        assertThat(testMyNewOperation.getAnonymousMyNewComponentData()).isEqualTo(DEFAULT_ANONYMOUS_MY_NEW_COMPONENT_DATA);
    }

    @Test
    @Transactional
    void fullUpdateMyNewOperationWithPatch() throws Exception {
        // Initialize the database
        myNewOperationRepository.saveAndFlush(myNewOperation);

        int databaseSizeBeforeUpdate = myNewOperationRepository.findAll().size();

        // Update the myNewOperation using partial update
        MyNewOperation partialUpdatedMyNewOperation = new MyNewOperation();
        partialUpdatedMyNewOperation.setId(myNewOperation.getId());

        partialUpdatedMyNewOperation
            .operationLayer(UPDATED_OPERATION_LAYER)
            .operationData(UPDATED_OPERATION_DATA)
            .anonymousMyNewComponentNumber(UPDATED_ANONYMOUS_MY_NEW_COMPONENT_NUMBER)
            .anonymousMyNewComponentDesignation(UPDATED_ANONYMOUS_MY_NEW_COMPONENT_DESIGNATION)
            .anonymousMyNewComponentData(UPDATED_ANONYMOUS_MY_NEW_COMPONENT_DATA);

        restMyNewOperationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMyNewOperation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMyNewOperation))
            )
            .andExpect(status().isOk());

        // Validate the MyNewOperation in the database
        List<MyNewOperation> myNewOperationList = myNewOperationRepository.findAll();
        assertThat(myNewOperationList).hasSize(databaseSizeBeforeUpdate);
        MyNewOperation testMyNewOperation = myNewOperationList.get(myNewOperationList.size() - 1);
        assertThat(testMyNewOperation.getOperationLayer()).isEqualTo(UPDATED_OPERATION_LAYER);
        assertThat(testMyNewOperation.getOperationData()).isEqualTo(UPDATED_OPERATION_DATA);
        assertThat(testMyNewOperation.getAnonymousMyNewComponentNumber()).isEqualTo(UPDATED_ANONYMOUS_MY_NEW_COMPONENT_NUMBER);
        assertThat(testMyNewOperation.getAnonymousMyNewComponentDesignation()).isEqualTo(UPDATED_ANONYMOUS_MY_NEW_COMPONENT_DESIGNATION);
        assertThat(testMyNewOperation.getAnonymousMyNewComponentData()).isEqualTo(UPDATED_ANONYMOUS_MY_NEW_COMPONENT_DATA);
    }

    @Test
    @Transactional
    void patchNonExistingMyNewOperation() throws Exception {
        int databaseSizeBeforeUpdate = myNewOperationRepository.findAll().size();
        myNewOperation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMyNewOperationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, myNewOperation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(myNewOperation))
            )
            .andExpect(status().isBadRequest());

        // Validate the MyNewOperation in the database
        List<MyNewOperation> myNewOperationList = myNewOperationRepository.findAll();
        assertThat(myNewOperationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMyNewOperation() throws Exception {
        int databaseSizeBeforeUpdate = myNewOperationRepository.findAll().size();
        myNewOperation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMyNewOperationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(myNewOperation))
            )
            .andExpect(status().isBadRequest());

        // Validate the MyNewOperation in the database
        List<MyNewOperation> myNewOperationList = myNewOperationRepository.findAll();
        assertThat(myNewOperationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMyNewOperation() throws Exception {
        int databaseSizeBeforeUpdate = myNewOperationRepository.findAll().size();
        myNewOperation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMyNewOperationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(myNewOperation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MyNewOperation in the database
        List<MyNewOperation> myNewOperationList = myNewOperationRepository.findAll();
        assertThat(myNewOperationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMyNewOperation() throws Exception {
        // Initialize the database
        myNewOperationRepository.saveAndFlush(myNewOperation);

        int databaseSizeBeforeDelete = myNewOperationRepository.findAll().size();

        // Delete the myNewOperation
        restMyNewOperationMockMvc
            .perform(delete(ENTITY_API_URL_ID, myNewOperation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MyNewOperation> myNewOperationList = myNewOperationRepository.findAll();
        assertThat(myNewOperationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
