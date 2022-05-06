package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.Plaiter;
import com.muller.lappli.domain.PlaiterConfiguration;
import com.muller.lappli.repository.PlaiterConfigurationRepository;
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
 * Integration tests for the {@link PlaiterConfigurationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlaiterConfigurationResourceIT {

    private static final Long DEFAULT_USED_BOBINS_COUNT = 0L;
    private static final Long UPDATED_USED_BOBINS_COUNT = 1L;

    private static final String ENTITY_API_URL = "/api/plaiter-configurations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlaiterConfigurationRepository plaiterConfigurationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlaiterConfigurationMockMvc;

    private PlaiterConfiguration plaiterConfiguration;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlaiterConfiguration createEntity(EntityManager em) {
        PlaiterConfiguration plaiterConfiguration = new PlaiterConfiguration().usedBobinsCount(DEFAULT_USED_BOBINS_COUNT);
        // Add required entity
        Plaiter plaiter;
        if (TestUtil.findAll(em, Plaiter.class).isEmpty()) {
            plaiter = PlaiterResourceIT.createEntity(em);
            em.persist(plaiter);
            em.flush();
        } else {
            plaiter = TestUtil.findAll(em, Plaiter.class).get(0);
        }
        plaiterConfiguration.setPlaiter(plaiter);
        return plaiterConfiguration;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlaiterConfiguration createUpdatedEntity(EntityManager em) {
        PlaiterConfiguration plaiterConfiguration = new PlaiterConfiguration().usedBobinsCount(UPDATED_USED_BOBINS_COUNT);
        // Add required entity
        Plaiter plaiter;
        if (TestUtil.findAll(em, Plaiter.class).isEmpty()) {
            plaiter = PlaiterResourceIT.createUpdatedEntity(em);
            em.persist(plaiter);
            em.flush();
        } else {
            plaiter = TestUtil.findAll(em, Plaiter.class).get(0);
        }
        plaiterConfiguration.setPlaiter(plaiter);
        return plaiterConfiguration;
    }

    @BeforeEach
    public void initTest() {
        plaiterConfiguration = createEntity(em);
    }

    @Test
    @Transactional
    void createPlaiterConfiguration() throws Exception {
        int databaseSizeBeforeCreate = plaiterConfigurationRepository.findAll().size();
        // Create the PlaiterConfiguration
        restPlaiterConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plaiterConfiguration))
            )
            .andExpect(status().isCreated());

        // Validate the PlaiterConfiguration in the database
        List<PlaiterConfiguration> plaiterConfigurationList = plaiterConfigurationRepository.findAll();
        assertThat(plaiterConfigurationList).hasSize(databaseSizeBeforeCreate + 1);
        PlaiterConfiguration testPlaiterConfiguration = plaiterConfigurationList.get(plaiterConfigurationList.size() - 1);
        assertThat(testPlaiterConfiguration.getUsedBobinsCount()).isEqualTo(DEFAULT_USED_BOBINS_COUNT);
    }

    @Test
    @Transactional
    void createPlaiterConfigurationWithExistingId() throws Exception {
        // Create the PlaiterConfiguration with an existing ID
        plaiterConfiguration.setId(1L);

        int databaseSizeBeforeCreate = plaiterConfigurationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlaiterConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plaiterConfiguration))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlaiterConfiguration in the database
        List<PlaiterConfiguration> plaiterConfigurationList = plaiterConfigurationRepository.findAll();
        assertThat(plaiterConfigurationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkUsedBobinsCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = plaiterConfigurationRepository.findAll().size();
        // set the field null
        plaiterConfiguration.setUsedBobinsCount(null);

        // Create the PlaiterConfiguration, which fails.

        restPlaiterConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plaiterConfiguration))
            )
            .andExpect(status().isBadRequest());

        List<PlaiterConfiguration> plaiterConfigurationList = plaiterConfigurationRepository.findAll();
        assertThat(plaiterConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPlaiterConfigurations() throws Exception {
        // Initialize the database
        plaiterConfigurationRepository.saveAndFlush(plaiterConfiguration);

        // Get all the plaiterConfigurationList
        restPlaiterConfigurationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plaiterConfiguration.getId().intValue())))
            .andExpect(jsonPath("$.[*].usedBobinsCount").value(hasItem(DEFAULT_USED_BOBINS_COUNT.intValue())));
    }

    @Test
    @Transactional
    void getPlaiterConfiguration() throws Exception {
        // Initialize the database
        plaiterConfigurationRepository.saveAndFlush(plaiterConfiguration);

        // Get the plaiterConfiguration
        restPlaiterConfigurationMockMvc
            .perform(get(ENTITY_API_URL_ID, plaiterConfiguration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plaiterConfiguration.getId().intValue()))
            .andExpect(jsonPath("$.usedBobinsCount").value(DEFAULT_USED_BOBINS_COUNT.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingPlaiterConfiguration() throws Exception {
        // Get the plaiterConfiguration
        restPlaiterConfigurationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPlaiterConfiguration() throws Exception {
        // Initialize the database
        plaiterConfigurationRepository.saveAndFlush(plaiterConfiguration);

        int databaseSizeBeforeUpdate = plaiterConfigurationRepository.findAll().size();

        // Update the plaiterConfiguration
        PlaiterConfiguration updatedPlaiterConfiguration = plaiterConfigurationRepository.findById(plaiterConfiguration.getId()).get();
        // Disconnect from session so that the updates on updatedPlaiterConfiguration are not directly saved in db
        em.detach(updatedPlaiterConfiguration);
        updatedPlaiterConfiguration.usedBobinsCount(UPDATED_USED_BOBINS_COUNT);

        restPlaiterConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPlaiterConfiguration.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPlaiterConfiguration))
            )
            .andExpect(status().isOk());

        // Validate the PlaiterConfiguration in the database
        List<PlaiterConfiguration> plaiterConfigurationList = plaiterConfigurationRepository.findAll();
        assertThat(plaiterConfigurationList).hasSize(databaseSizeBeforeUpdate);
        PlaiterConfiguration testPlaiterConfiguration = plaiterConfigurationList.get(plaiterConfigurationList.size() - 1);
        assertThat(testPlaiterConfiguration.getUsedBobinsCount()).isEqualTo(UPDATED_USED_BOBINS_COUNT);
    }

    @Test
    @Transactional
    void putNonExistingPlaiterConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = plaiterConfigurationRepository.findAll().size();
        plaiterConfiguration.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlaiterConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, plaiterConfiguration.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plaiterConfiguration))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlaiterConfiguration in the database
        List<PlaiterConfiguration> plaiterConfigurationList = plaiterConfigurationRepository.findAll();
        assertThat(plaiterConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlaiterConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = plaiterConfigurationRepository.findAll().size();
        plaiterConfiguration.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaiterConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plaiterConfiguration))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlaiterConfiguration in the database
        List<PlaiterConfiguration> plaiterConfigurationList = plaiterConfigurationRepository.findAll();
        assertThat(plaiterConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlaiterConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = plaiterConfigurationRepository.findAll().size();
        plaiterConfiguration.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaiterConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plaiterConfiguration))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlaiterConfiguration in the database
        List<PlaiterConfiguration> plaiterConfigurationList = plaiterConfigurationRepository.findAll();
        assertThat(plaiterConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlaiterConfigurationWithPatch() throws Exception {
        // Initialize the database
        plaiterConfigurationRepository.saveAndFlush(plaiterConfiguration);

        int databaseSizeBeforeUpdate = plaiterConfigurationRepository.findAll().size();

        // Update the plaiterConfiguration using partial update
        PlaiterConfiguration partialUpdatedPlaiterConfiguration = new PlaiterConfiguration();
        partialUpdatedPlaiterConfiguration.setId(plaiterConfiguration.getId());

        restPlaiterConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlaiterConfiguration.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlaiterConfiguration))
            )
            .andExpect(status().isOk());

        // Validate the PlaiterConfiguration in the database
        List<PlaiterConfiguration> plaiterConfigurationList = plaiterConfigurationRepository.findAll();
        assertThat(plaiterConfigurationList).hasSize(databaseSizeBeforeUpdate);
        PlaiterConfiguration testPlaiterConfiguration = plaiterConfigurationList.get(plaiterConfigurationList.size() - 1);
        assertThat(testPlaiterConfiguration.getUsedBobinsCount()).isEqualTo(DEFAULT_USED_BOBINS_COUNT);
    }

    @Test
    @Transactional
    void fullUpdatePlaiterConfigurationWithPatch() throws Exception {
        // Initialize the database
        plaiterConfigurationRepository.saveAndFlush(plaiterConfiguration);

        int databaseSizeBeforeUpdate = plaiterConfigurationRepository.findAll().size();

        // Update the plaiterConfiguration using partial update
        PlaiterConfiguration partialUpdatedPlaiterConfiguration = new PlaiterConfiguration();
        partialUpdatedPlaiterConfiguration.setId(plaiterConfiguration.getId());

        partialUpdatedPlaiterConfiguration.usedBobinsCount(UPDATED_USED_BOBINS_COUNT);

        restPlaiterConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlaiterConfiguration.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlaiterConfiguration))
            )
            .andExpect(status().isOk());

        // Validate the PlaiterConfiguration in the database
        List<PlaiterConfiguration> plaiterConfigurationList = plaiterConfigurationRepository.findAll();
        assertThat(plaiterConfigurationList).hasSize(databaseSizeBeforeUpdate);
        PlaiterConfiguration testPlaiterConfiguration = plaiterConfigurationList.get(plaiterConfigurationList.size() - 1);
        assertThat(testPlaiterConfiguration.getUsedBobinsCount()).isEqualTo(UPDATED_USED_BOBINS_COUNT);
    }

    @Test
    @Transactional
    void patchNonExistingPlaiterConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = plaiterConfigurationRepository.findAll().size();
        plaiterConfiguration.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlaiterConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, plaiterConfiguration.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plaiterConfiguration))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlaiterConfiguration in the database
        List<PlaiterConfiguration> plaiterConfigurationList = plaiterConfigurationRepository.findAll();
        assertThat(plaiterConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlaiterConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = plaiterConfigurationRepository.findAll().size();
        plaiterConfiguration.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaiterConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plaiterConfiguration))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlaiterConfiguration in the database
        List<PlaiterConfiguration> plaiterConfigurationList = plaiterConfigurationRepository.findAll();
        assertThat(plaiterConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlaiterConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = plaiterConfigurationRepository.findAll().size();
        plaiterConfiguration.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaiterConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plaiterConfiguration))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlaiterConfiguration in the database
        List<PlaiterConfiguration> plaiterConfigurationList = plaiterConfigurationRepository.findAll();
        assertThat(plaiterConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlaiterConfiguration() throws Exception {
        // Initialize the database
        plaiterConfigurationRepository.saveAndFlush(plaiterConfiguration);

        int databaseSizeBeforeDelete = plaiterConfigurationRepository.findAll().size();

        // Delete the plaiterConfiguration
        restPlaiterConfigurationMockMvc
            .perform(delete(ENTITY_API_URL_ID, plaiterConfiguration.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlaiterConfiguration> plaiterConfigurationList = plaiterConfigurationRepository.findAll();
        assertThat(plaiterConfigurationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
