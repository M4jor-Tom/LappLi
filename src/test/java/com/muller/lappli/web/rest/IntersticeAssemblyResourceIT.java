package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.IntersticeAssembly;
import com.muller.lappli.domain.Position;
import com.muller.lappli.domain.Strand;
import com.muller.lappli.repository.IntersticeAssemblyRepository;
import com.muller.lappli.service.criteria.IntersticeAssemblyCriteria;
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
 * Integration tests for the {@link IntersticeAssemblyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IntersticeAssemblyResourceIT {

    private static final String ENTITY_API_URL = "/api/interstice-assemblies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IntersticeAssemblyRepository intersticeAssemblyRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIntersticeAssemblyMockMvc;

    private IntersticeAssembly intersticeAssembly;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IntersticeAssembly createEntity(EntityManager em) {
        IntersticeAssembly intersticeAssembly = new IntersticeAssembly();
        // Add required entity
        Strand strand;
        if (TestUtil.findAll(em, Strand.class).isEmpty()) {
            strand = StrandResourceIT.createEntity(em);
            em.persist(strand);
            em.flush();
        } else {
            strand = TestUtil.findAll(em, Strand.class).get(0);
        }
        intersticeAssembly.setStrand(strand);
        return intersticeAssembly;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IntersticeAssembly createUpdatedEntity(EntityManager em) {
        IntersticeAssembly intersticeAssembly = new IntersticeAssembly();
        // Add required entity
        Strand strand;
        if (TestUtil.findAll(em, Strand.class).isEmpty()) {
            strand = StrandResourceIT.createUpdatedEntity(em);
            em.persist(strand);
            em.flush();
        } else {
            strand = TestUtil.findAll(em, Strand.class).get(0);
        }
        intersticeAssembly.setStrand(strand);
        return intersticeAssembly;
    }

    @BeforeEach
    public void initTest() {
        intersticeAssembly = createEntity(em);
    }

    @Test
    @Transactional
    void createIntersticeAssembly() throws Exception {
        int databaseSizeBeforeCreate = intersticeAssemblyRepository.findAll().size();
        // Create the IntersticeAssembly
        restIntersticeAssemblyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(intersticeAssembly))
            )
            .andExpect(status().isCreated());

        // Validate the IntersticeAssembly in the database
        List<IntersticeAssembly> intersticeAssemblyList = intersticeAssemblyRepository.findAll();
        assertThat(intersticeAssemblyList).hasSize(databaseSizeBeforeCreate + 1);
        IntersticeAssembly testIntersticeAssembly = intersticeAssemblyList.get(intersticeAssemblyList.size() - 1);
    }

    @Test
    @Transactional
    void createIntersticeAssemblyWithExistingId() throws Exception {
        // Create the IntersticeAssembly with an existing ID
        intersticeAssembly.setId(1L);

        int databaseSizeBeforeCreate = intersticeAssemblyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIntersticeAssemblyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(intersticeAssembly))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntersticeAssembly in the database
        List<IntersticeAssembly> intersticeAssemblyList = intersticeAssemblyRepository.findAll();
        assertThat(intersticeAssemblyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIntersticeAssemblies() throws Exception {
        // Initialize the database
        intersticeAssemblyRepository.saveAndFlush(intersticeAssembly);

        // Get all the intersticeAssemblyList
        restIntersticeAssemblyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(intersticeAssembly.getId().intValue())));
    }

    @Test
    @Transactional
    void getIntersticeAssembly() throws Exception {
        // Initialize the database
        intersticeAssemblyRepository.saveAndFlush(intersticeAssembly);

        // Get the intersticeAssembly
        restIntersticeAssemblyMockMvc
            .perform(get(ENTITY_API_URL_ID, intersticeAssembly.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(intersticeAssembly.getId().intValue()));
    }

    @Test
    @Transactional
    void getIntersticeAssembliesByIdFiltering() throws Exception {
        // Initialize the database
        intersticeAssemblyRepository.saveAndFlush(intersticeAssembly);

        Long id = intersticeAssembly.getId();

        defaultIntersticeAssemblyShouldBeFound("id.equals=" + id);
        defaultIntersticeAssemblyShouldNotBeFound("id.notEquals=" + id);

        defaultIntersticeAssemblyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultIntersticeAssemblyShouldNotBeFound("id.greaterThan=" + id);

        defaultIntersticeAssemblyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultIntersticeAssemblyShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllIntersticeAssembliesByPositionsIsEqualToSomething() throws Exception {
        // Initialize the database
        intersticeAssemblyRepository.saveAndFlush(intersticeAssembly);
        Position positions;
        if (TestUtil.findAll(em, Position.class).isEmpty()) {
            positions = PositionResourceIT.createEntity(em);
            em.persist(positions);
            em.flush();
        } else {
            positions = TestUtil.findAll(em, Position.class).get(0);
        }
        em.persist(positions);
        em.flush();
        intersticeAssembly.addPositions(positions);
        intersticeAssemblyRepository.saveAndFlush(intersticeAssembly);
        Long positionsId = positions.getId();

        // Get all the intersticeAssemblyList where positions equals to positionsId
        defaultIntersticeAssemblyShouldBeFound("positionsId.equals=" + positionsId);

        // Get all the intersticeAssemblyList where positions equals to (positionsId + 1)
        defaultIntersticeAssemblyShouldNotBeFound("positionsId.equals=" + (positionsId + 1));
    }

    @Test
    @Transactional
    void getAllIntersticeAssembliesByStrandIsEqualToSomething() throws Exception {
        // Initialize the database
        intersticeAssemblyRepository.saveAndFlush(intersticeAssembly);
        Strand strand;
        if (TestUtil.findAll(em, Strand.class).isEmpty()) {
            strand = StrandResourceIT.createEntity(em);
            em.persist(strand);
            em.flush();
        } else {
            strand = TestUtil.findAll(em, Strand.class).get(0);
        }
        em.persist(strand);
        em.flush();
        intersticeAssembly.setStrand(strand);
        intersticeAssemblyRepository.saveAndFlush(intersticeAssembly);
        Long strandId = strand.getId();

        // Get all the intersticeAssemblyList where strand equals to strandId
        defaultIntersticeAssemblyShouldBeFound("strandId.equals=" + strandId);

        // Get all the intersticeAssemblyList where strand equals to (strandId + 1)
        defaultIntersticeAssemblyShouldNotBeFound("strandId.equals=" + (strandId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultIntersticeAssemblyShouldBeFound(String filter) throws Exception {
        restIntersticeAssemblyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(intersticeAssembly.getId().intValue())));

        // Check, that the count call also returns 1
        restIntersticeAssemblyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultIntersticeAssemblyShouldNotBeFound(String filter) throws Exception {
        restIntersticeAssemblyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restIntersticeAssemblyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingIntersticeAssembly() throws Exception {
        // Get the intersticeAssembly
        restIntersticeAssemblyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewIntersticeAssembly() throws Exception {
        // Initialize the database
        intersticeAssemblyRepository.saveAndFlush(intersticeAssembly);

        int databaseSizeBeforeUpdate = intersticeAssemblyRepository.findAll().size();

        // Update the intersticeAssembly
        IntersticeAssembly updatedIntersticeAssembly = intersticeAssemblyRepository.findById(intersticeAssembly.getId()).get();
        // Disconnect from session so that the updates on updatedIntersticeAssembly are not directly saved in db
        em.detach(updatedIntersticeAssembly);

        ResultMatcher expectedResult = updatedIntersticeAssembly.positionsAreRight() ? status().isOk() : status().isBadRequest();

        restIntersticeAssemblyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIntersticeAssembly.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedIntersticeAssembly))
            )
            .andExpect(expectedResult);

        // Validate the IntersticeAssembly in the database
        List<IntersticeAssembly> intersticeAssemblyList = intersticeAssemblyRepository.findAll();
        assertThat(intersticeAssemblyList).hasSize(databaseSizeBeforeUpdate);
        IntersticeAssembly testIntersticeAssembly = intersticeAssemblyList.get(intersticeAssemblyList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingIntersticeAssembly() throws Exception {
        int databaseSizeBeforeUpdate = intersticeAssemblyRepository.findAll().size();
        intersticeAssembly.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntersticeAssemblyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intersticeAssembly.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(intersticeAssembly))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntersticeAssembly in the database
        List<IntersticeAssembly> intersticeAssemblyList = intersticeAssemblyRepository.findAll();
        assertThat(intersticeAssemblyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIntersticeAssembly() throws Exception {
        int databaseSizeBeforeUpdate = intersticeAssemblyRepository.findAll().size();
        intersticeAssembly.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntersticeAssemblyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(intersticeAssembly))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntersticeAssembly in the database
        List<IntersticeAssembly> intersticeAssemblyList = intersticeAssemblyRepository.findAll();
        assertThat(intersticeAssemblyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIntersticeAssembly() throws Exception {
        int databaseSizeBeforeUpdate = intersticeAssemblyRepository.findAll().size();
        intersticeAssembly.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntersticeAssemblyMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(intersticeAssembly))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IntersticeAssembly in the database
        List<IntersticeAssembly> intersticeAssemblyList = intersticeAssemblyRepository.findAll();
        assertThat(intersticeAssemblyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIntersticeAssemblyWithPatch() throws Exception {
        // Initialize the database
        intersticeAssemblyRepository.saveAndFlush(intersticeAssembly);

        int databaseSizeBeforeUpdate = intersticeAssemblyRepository.findAll().size();

        // Update the intersticeAssembly using partial update
        IntersticeAssembly partialUpdatedIntersticeAssembly = new IntersticeAssembly();
        partialUpdatedIntersticeAssembly.setId(intersticeAssembly.getId());

        restIntersticeAssemblyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIntersticeAssembly.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIntersticeAssembly))
            )
            .andExpect(status().isOk());

        // Validate the IntersticeAssembly in the database
        List<IntersticeAssembly> intersticeAssemblyList = intersticeAssemblyRepository.findAll();
        assertThat(intersticeAssemblyList).hasSize(databaseSizeBeforeUpdate);
        IntersticeAssembly testIntersticeAssembly = intersticeAssemblyList.get(intersticeAssemblyList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateIntersticeAssemblyWithPatch() throws Exception {
        // Initialize the database
        intersticeAssemblyRepository.saveAndFlush(intersticeAssembly);

        int databaseSizeBeforeUpdate = intersticeAssemblyRepository.findAll().size();

        // Update the intersticeAssembly using partial update
        IntersticeAssembly partialUpdatedIntersticeAssembly = new IntersticeAssembly();
        partialUpdatedIntersticeAssembly.setId(intersticeAssembly.getId());

        restIntersticeAssemblyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIntersticeAssembly.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIntersticeAssembly))
            )
            .andExpect(status().isOk());

        // Validate the IntersticeAssembly in the database
        List<IntersticeAssembly> intersticeAssemblyList = intersticeAssemblyRepository.findAll();
        assertThat(intersticeAssemblyList).hasSize(databaseSizeBeforeUpdate);
        IntersticeAssembly testIntersticeAssembly = intersticeAssemblyList.get(intersticeAssemblyList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingIntersticeAssembly() throws Exception {
        int databaseSizeBeforeUpdate = intersticeAssemblyRepository.findAll().size();
        intersticeAssembly.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntersticeAssemblyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intersticeAssembly.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(intersticeAssembly))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntersticeAssembly in the database
        List<IntersticeAssembly> intersticeAssemblyList = intersticeAssemblyRepository.findAll();
        assertThat(intersticeAssemblyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIntersticeAssembly() throws Exception {
        int databaseSizeBeforeUpdate = intersticeAssemblyRepository.findAll().size();
        intersticeAssembly.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntersticeAssemblyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(intersticeAssembly))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntersticeAssembly in the database
        List<IntersticeAssembly> intersticeAssemblyList = intersticeAssemblyRepository.findAll();
        assertThat(intersticeAssemblyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIntersticeAssembly() throws Exception {
        int databaseSizeBeforeUpdate = intersticeAssemblyRepository.findAll().size();
        intersticeAssembly.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntersticeAssemblyMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(intersticeAssembly))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IntersticeAssembly in the database
        List<IntersticeAssembly> intersticeAssemblyList = intersticeAssemblyRepository.findAll();
        assertThat(intersticeAssemblyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIntersticeAssembly() throws Exception {
        // Initialize the database
        intersticeAssemblyRepository.saveAndFlush(intersticeAssembly);

        int databaseSizeBeforeDelete = intersticeAssemblyRepository.findAll().size();

        // Delete the intersticeAssembly
        restIntersticeAssemblyMockMvc
            .perform(delete(ENTITY_API_URL_ID, intersticeAssembly.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IntersticeAssembly> intersticeAssemblyList = intersticeAssemblyRepository.findAll();
        assertThat(intersticeAssemblyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
