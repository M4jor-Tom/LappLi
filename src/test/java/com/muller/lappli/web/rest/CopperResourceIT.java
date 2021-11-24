package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.Copper;
import com.muller.lappli.repository.CopperRepository;
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
 * Integration tests for the {@link CopperResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CopperResourceIT {

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/coppers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CopperRepository copperRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCopperMockMvc;

    private Copper copper;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Copper createEntity(EntityManager em) {
        Copper copper = new Copper().designation(DEFAULT_DESIGNATION);
        return copper;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Copper createUpdatedEntity(EntityManager em) {
        Copper copper = new Copper().designation(UPDATED_DESIGNATION);
        return copper;
    }

    @BeforeEach
    public void initTest() {
        copper = createEntity(em);
    }

    @Test
    @Transactional
    void createCopper() throws Exception {
        int databaseSizeBeforeCreate = copperRepository.findAll().size();
        // Create the Copper
        restCopperMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(copper)))
            .andExpect(status().isCreated());

        // Validate the Copper in the database
        List<Copper> copperList = copperRepository.findAll();
        assertThat(copperList).hasSize(databaseSizeBeforeCreate + 1);
        Copper testCopper = copperList.get(copperList.size() - 1);
        assertThat(testCopper.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
    }

    @Test
    @Transactional
    void createCopperWithExistingId() throws Exception {
        // Create the Copper with an existing ID
        copper.setId(1L);

        int databaseSizeBeforeCreate = copperRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCopperMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(copper)))
            .andExpect(status().isBadRequest());

        // Validate the Copper in the database
        List<Copper> copperList = copperRepository.findAll();
        assertThat(copperList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = copperRepository.findAll().size();
        // set the field null
        copper.setDesignation(null);

        // Create the Copper, which fails.

        restCopperMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(copper)))
            .andExpect(status().isBadRequest());

        List<Copper> copperList = copperRepository.findAll();
        assertThat(copperList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCoppers() throws Exception {
        // Initialize the database
        copperRepository.saveAndFlush(copper);

        // Get all the copperList
        restCopperMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(copper.getId().intValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)));
    }

    @Test
    @Transactional
    void getCopper() throws Exception {
        // Initialize the database
        copperRepository.saveAndFlush(copper);

        // Get the copper
        restCopperMockMvc
            .perform(get(ENTITY_API_URL_ID, copper.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(copper.getId().intValue()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION));
    }

    @Test
    @Transactional
    void getNonExistingCopper() throws Exception {
        // Get the copper
        restCopperMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCopper() throws Exception {
        // Initialize the database
        copperRepository.saveAndFlush(copper);

        int databaseSizeBeforeUpdate = copperRepository.findAll().size();

        // Update the copper
        Copper updatedCopper = copperRepository.findById(copper.getId()).get();
        // Disconnect from session so that the updates on updatedCopper are not directly saved in db
        em.detach(updatedCopper);
        updatedCopper.designation(UPDATED_DESIGNATION);

        restCopperMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCopper.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCopper))
            )
            .andExpect(status().isOk());

        // Validate the Copper in the database
        List<Copper> copperList = copperRepository.findAll();
        assertThat(copperList).hasSize(databaseSizeBeforeUpdate);
        Copper testCopper = copperList.get(copperList.size() - 1);
        assertThat(testCopper.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void putNonExistingCopper() throws Exception {
        int databaseSizeBeforeUpdate = copperRepository.findAll().size();
        copper.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCopperMockMvc
            .perform(
                put(ENTITY_API_URL_ID, copper.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(copper))
            )
            .andExpect(status().isBadRequest());

        // Validate the Copper in the database
        List<Copper> copperList = copperRepository.findAll();
        assertThat(copperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCopper() throws Exception {
        int databaseSizeBeforeUpdate = copperRepository.findAll().size();
        copper.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCopperMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(copper))
            )
            .andExpect(status().isBadRequest());

        // Validate the Copper in the database
        List<Copper> copperList = copperRepository.findAll();
        assertThat(copperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCopper() throws Exception {
        int databaseSizeBeforeUpdate = copperRepository.findAll().size();
        copper.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCopperMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(copper)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Copper in the database
        List<Copper> copperList = copperRepository.findAll();
        assertThat(copperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCopperWithPatch() throws Exception {
        // Initialize the database
        copperRepository.saveAndFlush(copper);

        int databaseSizeBeforeUpdate = copperRepository.findAll().size();

        // Update the copper using partial update
        Copper partialUpdatedCopper = new Copper();
        partialUpdatedCopper.setId(copper.getId());

        restCopperMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCopper.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCopper))
            )
            .andExpect(status().isOk());

        // Validate the Copper in the database
        List<Copper> copperList = copperRepository.findAll();
        assertThat(copperList).hasSize(databaseSizeBeforeUpdate);
        Copper testCopper = copperList.get(copperList.size() - 1);
        assertThat(testCopper.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
    }

    @Test
    @Transactional
    void fullUpdateCopperWithPatch() throws Exception {
        // Initialize the database
        copperRepository.saveAndFlush(copper);

        int databaseSizeBeforeUpdate = copperRepository.findAll().size();

        // Update the copper using partial update
        Copper partialUpdatedCopper = new Copper();
        partialUpdatedCopper.setId(copper.getId());

        partialUpdatedCopper.designation(UPDATED_DESIGNATION);

        restCopperMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCopper.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCopper))
            )
            .andExpect(status().isOk());

        // Validate the Copper in the database
        List<Copper> copperList = copperRepository.findAll();
        assertThat(copperList).hasSize(databaseSizeBeforeUpdate);
        Copper testCopper = copperList.get(copperList.size() - 1);
        assertThat(testCopper.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void patchNonExistingCopper() throws Exception {
        int databaseSizeBeforeUpdate = copperRepository.findAll().size();
        copper.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCopperMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, copper.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(copper))
            )
            .andExpect(status().isBadRequest());

        // Validate the Copper in the database
        List<Copper> copperList = copperRepository.findAll();
        assertThat(copperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCopper() throws Exception {
        int databaseSizeBeforeUpdate = copperRepository.findAll().size();
        copper.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCopperMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(copper))
            )
            .andExpect(status().isBadRequest());

        // Validate the Copper in the database
        List<Copper> copperList = copperRepository.findAll();
        assertThat(copperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCopper() throws Exception {
        int databaseSizeBeforeUpdate = copperRepository.findAll().size();
        copper.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCopperMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(copper)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Copper in the database
        List<Copper> copperList = copperRepository.findAll();
        assertThat(copperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCopper() throws Exception {
        // Initialize the database
        copperRepository.saveAndFlush(copper);

        int databaseSizeBeforeDelete = copperRepository.findAll().size();

        // Delete the copper
        restCopperMockMvc
            .perform(delete(ENTITY_API_URL_ID, copper.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Copper> copperList = copperRepository.findAll();
        assertThat(copperList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
