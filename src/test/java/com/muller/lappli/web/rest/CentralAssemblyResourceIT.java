package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.CentralAssembly;
import com.muller.lappli.domain.Position;
import com.muller.lappli.domain.Strand;
import com.muller.lappli.repository.CentralAssemblyRepository;
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
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CentralAssemblyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CentralAssemblyResourceIT {

    private static final String ENTITY_API_URL = "/api/central-assemblies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CentralAssemblyRepository centralAssemblyRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCentralAssemblyMockMvc;

    private CentralAssembly centralAssembly;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CentralAssembly createEntity(EntityManager em) {
        CentralAssembly centralAssembly = new CentralAssembly();
        // Add required entity
        Strand strand;
        if (TestUtil.findAll(em, Strand.class).isEmpty()) {
            strand = StrandResourceIT.createEntity(em);
            em.persist(strand);
            em.flush();
        } else {
            strand = TestUtil.findAll(em, Strand.class).get(0);
        }
        centralAssembly.setStrand(strand);
        return centralAssembly;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CentralAssembly createUpdatedEntity(EntityManager em) {
        CentralAssembly centralAssembly = new CentralAssembly();
        // Add required entity
        Strand strand;
        if (TestUtil.findAll(em, Strand.class).isEmpty()) {
            strand = StrandResourceIT.createUpdatedEntity(em);
            em.persist(strand);
            em.flush();
        } else {
            strand = TestUtil.findAll(em, Strand.class).get(0);
        }
        centralAssembly.setStrand(strand);
        return centralAssembly;
    }

    @BeforeEach
    public void initTest() {
        centralAssembly = createEntity(em);
    }

    @Test
    @Transactional
    void createCentralAssembly() throws Exception {
        int databaseSizeBeforeCreate = centralAssemblyRepository.findAll().size();
        // Create the CentralAssembly
        ResultMatcher expectedResult = centralAssembly.positionsAreRight() ? status().isCreated() : status().isBadRequest();
        restCentralAssemblyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(centralAssembly))
            )
            .andExpect(expectedResult);

        // Validate the CentralAssembly in the database
        List<CentralAssembly> centralAssemblyList = centralAssemblyRepository.findAll();
        assertThat(centralAssemblyList).hasSize(databaseSizeBeforeCreate + 1);
        CentralAssembly testCentralAssembly = centralAssemblyList.get(centralAssemblyList.size() - 1);

        // Validate the id for MapsId, the ids must be same
        assertThat(testCentralAssembly.getId()).isEqualTo(testCentralAssembly.getStrand().getId());
    }

    @Test
    @Transactional
    void createCentralAssemblyWithExistingId() throws Exception {
        // Create the CentralAssembly with an existing ID
        centralAssembly.setId(1L);

        int databaseSizeBeforeCreate = centralAssemblyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCentralAssemblyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(centralAssembly))
            )
            .andExpect(status().isBadRequest());

        // Validate the CentralAssembly in the database
        List<CentralAssembly> centralAssemblyList = centralAssemblyRepository.findAll();
        assertThat(centralAssemblyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void updateCentralAssemblyMapsIdAssociationWithNewId() throws Exception {
        // Initialize the database
        centralAssemblyRepository.saveAndFlush(centralAssembly);
        int databaseSizeBeforeCreate = centralAssemblyRepository.findAll().size();

        // Add a new parent entity
        Strand strand = StrandResourceIT.createUpdatedEntity(em);
        em.persist(strand);
        em.flush();

        // Load the centralAssembly
        CentralAssembly updatedCentralAssembly = centralAssemblyRepository.findById(centralAssembly.getId()).get();
        assertThat(updatedCentralAssembly).isNotNull();
        // Disconnect from session so that the updates on updatedCentralAssembly are not directly saved in db
        em.detach(updatedCentralAssembly);

        // Update the Strand with new association value
        updatedCentralAssembly.setStrand(strand);

        ResultMatcher expectedResult = updatedCentralAssembly.positionsAreRight() ? status().isOk() : status().isBadRequest();

        // Update the entity
        restCentralAssemblyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCentralAssembly.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCentralAssembly))
            )
            .andExpect(expectedResult);

        // Validate the CentralAssembly in the database
        List<CentralAssembly> centralAssemblyList = centralAssemblyRepository.findAll();
        assertThat(centralAssemblyList).hasSize(databaseSizeBeforeCreate);
        CentralAssembly testCentralAssembly = centralAssemblyList.get(centralAssemblyList.size() - 1);
        // Validate the id for MapsId, the ids must be same
        // Uncomment the following line for assertion. However, please note that there is a known issue and uncommenting will fail the test.
        // Please look at https://github.com/jhipster/generator-jhipster/issues/9100. You can modify this test as necessary.
        // assertThat(testCentralAssembly.getId()).isEqualTo(testCentralAssembly.getStrand().getId());
    }

    @Test
    @Transactional
    void getAllCentralAssemblies() throws Exception {
        // Initialize the database
        centralAssemblyRepository.saveAndFlush(centralAssembly);

        // Get all the centralAssemblyList
        restCentralAssemblyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(centralAssembly.getId().intValue())));
    }

    @Test
    @Transactional
    void getCentralAssembly() throws Exception {
        // Initialize the database
        centralAssemblyRepository.saveAndFlush(centralAssembly);

        // Get the centralAssembly
        restCentralAssemblyMockMvc
            .perform(get(ENTITY_API_URL_ID, centralAssembly.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(centralAssembly.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingCentralAssembly() throws Exception {
        // Get the centralAssembly
        restCentralAssemblyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCentralAssembly() throws Exception {
        // Initialize the database
        centralAssemblyRepository.saveAndFlush(centralAssembly);

        int databaseSizeBeforeUpdate = centralAssemblyRepository.findAll().size();

        // Update the centralAssembly
        CentralAssembly updatedCentralAssembly = centralAssemblyRepository.findById(centralAssembly.getId()).get();
        // Disconnect from session so that the updates on updatedCentralAssembly are not directly saved in db
        em.detach(updatedCentralAssembly);

        ResultMatcher expectedResult = updatedCentralAssembly.positionsAreRight() ? status().isOk() : status().isBadRequest();

        restCentralAssemblyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCentralAssembly.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCentralAssembly))
            )
            .andExpect(expectedResult);

        // Validate the CentralAssembly in the database
        List<CentralAssembly> centralAssemblyList = centralAssemblyRepository.findAll();
        assertThat(centralAssemblyList).hasSize(databaseSizeBeforeUpdate);
        CentralAssembly testCentralAssembly = centralAssemblyList.get(centralAssemblyList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingCentralAssembly() throws Exception {
        int databaseSizeBeforeUpdate = centralAssemblyRepository.findAll().size();
        centralAssembly.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCentralAssemblyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, centralAssembly.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(centralAssembly))
            )
            .andExpect(status().isBadRequest());

        // Validate the CentralAssembly in the database
        List<CentralAssembly> centralAssemblyList = centralAssemblyRepository.findAll();
        assertThat(centralAssemblyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCentralAssembly() throws Exception {
        int databaseSizeBeforeUpdate = centralAssemblyRepository.findAll().size();
        centralAssembly.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentralAssemblyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(centralAssembly))
            )
            .andExpect(status().isBadRequest());

        // Validate the CentralAssembly in the database
        List<CentralAssembly> centralAssemblyList = centralAssemblyRepository.findAll();
        assertThat(centralAssemblyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCentralAssembly() throws Exception {
        int databaseSizeBeforeUpdate = centralAssemblyRepository.findAll().size();
        centralAssembly.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentralAssemblyMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(centralAssembly))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CentralAssembly in the database
        List<CentralAssembly> centralAssemblyList = centralAssemblyRepository.findAll();
        assertThat(centralAssemblyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCentralAssemblyWithPatch() throws Exception {
        // Initialize the database
        centralAssemblyRepository.saveAndFlush(centralAssembly);

        int databaseSizeBeforeUpdate = centralAssemblyRepository.findAll().size();

        // Update the centralAssembly using partial update
        CentralAssembly partialUpdatedCentralAssembly = new CentralAssembly();
        partialUpdatedCentralAssembly.setId(centralAssembly.getId());

        restCentralAssemblyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCentralAssembly.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCentralAssembly))
            )
            .andExpect(status().isOk());

        // Validate the CentralAssembly in the database
        List<CentralAssembly> centralAssemblyList = centralAssemblyRepository.findAll();
        assertThat(centralAssemblyList).hasSize(databaseSizeBeforeUpdate);
        CentralAssembly testCentralAssembly = centralAssemblyList.get(centralAssemblyList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateCentralAssemblyWithPatch() throws Exception {
        // Initialize the database
        centralAssemblyRepository.saveAndFlush(centralAssembly);

        int databaseSizeBeforeUpdate = centralAssemblyRepository.findAll().size();

        // Update the centralAssembly using partial update
        CentralAssembly partialUpdatedCentralAssembly = new CentralAssembly();
        partialUpdatedCentralAssembly.setId(centralAssembly.getId());

        restCentralAssemblyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCentralAssembly.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCentralAssembly))
            )
            .andExpect(status().isOk());

        // Validate the CentralAssembly in the database
        List<CentralAssembly> centralAssemblyList = centralAssemblyRepository.findAll();
        assertThat(centralAssemblyList).hasSize(databaseSizeBeforeUpdate);
        CentralAssembly testCentralAssembly = centralAssemblyList.get(centralAssemblyList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingCentralAssembly() throws Exception {
        int databaseSizeBeforeUpdate = centralAssemblyRepository.findAll().size();
        centralAssembly.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCentralAssemblyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, centralAssembly.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(centralAssembly))
            )
            .andExpect(status().isBadRequest());

        // Validate the CentralAssembly in the database
        List<CentralAssembly> centralAssemblyList = centralAssemblyRepository.findAll();
        assertThat(centralAssemblyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCentralAssembly() throws Exception {
        int databaseSizeBeforeUpdate = centralAssemblyRepository.findAll().size();
        centralAssembly.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentralAssemblyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(centralAssembly))
            )
            .andExpect(status().isBadRequest());

        // Validate the CentralAssembly in the database
        List<CentralAssembly> centralAssemblyList = centralAssemblyRepository.findAll();
        assertThat(centralAssemblyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCentralAssembly() throws Exception {
        int databaseSizeBeforeUpdate = centralAssemblyRepository.findAll().size();
        centralAssembly.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentralAssemblyMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(centralAssembly))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CentralAssembly in the database
        List<CentralAssembly> centralAssemblyList = centralAssemblyRepository.findAll();
        assertThat(centralAssemblyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCentralAssembly() throws Exception {
        // Initialize the database
        centralAssemblyRepository.saveAndFlush(centralAssembly);

        int databaseSizeBeforeDelete = centralAssemblyRepository.findAll().size();

        // Delete the centralAssembly
        restCentralAssemblyMockMvc
            .perform(delete(ENTITY_API_URL_ID, centralAssembly.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CentralAssembly> centralAssemblyList = centralAssemblyRepository.findAll();
        assertThat(centralAssemblyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
