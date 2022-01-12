package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.Material;
import com.muller.lappli.domain.Sheathing;
import com.muller.lappli.domain.Strand;
import com.muller.lappli.domain.enumeration.SheathingKind;
import com.muller.lappli.repository.SheathingRepository;
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
 * Integration tests for the {@link SheathingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SheathingResourceIT {

    private static final Double DEFAULT_THICKNESS = 1D;
    private static final Double UPDATED_THICKNESS = 2D;

    private static final SheathingKind DEFAULT_SHEATHING_KIND = SheathingKind.TUBE;
    private static final SheathingKind UPDATED_SHEATHING_KIND = SheathingKind.FLOATING_TUBE;

    private static final String ENTITY_API_URL = "/api/sheathings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SheathingRepository sheathingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSheathingMockMvc;

    private Sheathing sheathing;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sheathing createEntity(EntityManager em) {
        Sheathing sheathing = new Sheathing().thickness(DEFAULT_THICKNESS).sheathingKind(DEFAULT_SHEATHING_KIND);
        // Add required entity
        Material material;
        if (TestUtil.findAll(em, Material.class).isEmpty()) {
            material = MaterialResourceIT.createEntity(em);
            em.persist(material);
            em.flush();
        } else {
            material = TestUtil.findAll(em, Material.class).get(0);
        }
        sheathing.setMaterial(material);
        // Add required entity
        Strand strand;
        if (TestUtil.findAll(em, Strand.class).isEmpty()) {
            strand = StrandResourceIT.createEntity(em);
            em.persist(strand);
            em.flush();
        } else {
            strand = TestUtil.findAll(em, Strand.class).get(0);
        }
        sheathing.setOwnerStrand(strand);
        return sheathing;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sheathing createUpdatedEntity(EntityManager em) {
        Sheathing sheathing = new Sheathing().thickness(UPDATED_THICKNESS).sheathingKind(UPDATED_SHEATHING_KIND);
        // Add required entity
        Material material;
        if (TestUtil.findAll(em, Material.class).isEmpty()) {
            material = MaterialResourceIT.createUpdatedEntity(em);
            em.persist(material);
            em.flush();
        } else {
            material = TestUtil.findAll(em, Material.class).get(0);
        }
        sheathing.setMaterial(material);
        // Add required entity
        Strand strand;
        if (TestUtil.findAll(em, Strand.class).isEmpty()) {
            strand = StrandResourceIT.createUpdatedEntity(em);
            em.persist(strand);
            em.flush();
        } else {
            strand = TestUtil.findAll(em, Strand.class).get(0);
        }
        sheathing.setOwnerStrand(strand);
        return sheathing;
    }

    @BeforeEach
    public void initTest() {
        sheathing = createEntity(em);
    }

    @Test
    @Transactional
    void createSheathing() throws Exception {
        int databaseSizeBeforeCreate = sheathingRepository.findAll().size();
        // Create the Sheathing
        restSheathingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sheathing)))
            .andExpect(status().isCreated());

        // Validate the Sheathing in the database
        List<Sheathing> sheathingList = sheathingRepository.findAll();
        assertThat(sheathingList).hasSize(databaseSizeBeforeCreate + 1);
        Sheathing testSheathing = sheathingList.get(sheathingList.size() - 1);
        assertThat(testSheathing.getThickness()).isEqualTo(DEFAULT_THICKNESS);
        assertThat(testSheathing.getSheathingKind()).isEqualTo(DEFAULT_SHEATHING_KIND);
    }

    @Test
    @Transactional
    void createSheathingWithExistingId() throws Exception {
        // Create the Sheathing with an existing ID
        sheathing.setId(1L);

        int databaseSizeBeforeCreate = sheathingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSheathingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sheathing)))
            .andExpect(status().isBadRequest());

        // Validate the Sheathing in the database
        List<Sheathing> sheathingList = sheathingRepository.findAll();
        assertThat(sheathingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkThicknessIsRequired() throws Exception {
        int databaseSizeBeforeTest = sheathingRepository.findAll().size();
        // set the field null
        sheathing.setThickness(null);

        // Create the Sheathing, which fails.

        restSheathingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sheathing)))
            .andExpect(status().isBadRequest());

        List<Sheathing> sheathingList = sheathingRepository.findAll();
        assertThat(sheathingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSheathingKindIsRequired() throws Exception {
        int databaseSizeBeforeTest = sheathingRepository.findAll().size();
        // set the field null
        sheathing.setSheathingKind(null);

        // Create the Sheathing, which fails.

        restSheathingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sheathing)))
            .andExpect(status().isBadRequest());

        List<Sheathing> sheathingList = sheathingRepository.findAll();
        assertThat(sheathingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSheathings() throws Exception {
        // Initialize the database
        sheathingRepository.saveAndFlush(sheathing);

        // Get all the sheathingList
        restSheathingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sheathing.getId().intValue())))
            .andExpect(jsonPath("$.[*].thickness").value(hasItem(DEFAULT_THICKNESS.doubleValue())))
            .andExpect(jsonPath("$.[*].sheathingKind").value(hasItem(DEFAULT_SHEATHING_KIND.toString())));
    }

    @Test
    @Transactional
    void getSheathing() throws Exception {
        // Initialize the database
        sheathingRepository.saveAndFlush(sheathing);

        // Get the sheathing
        restSheathingMockMvc
            .perform(get(ENTITY_API_URL_ID, sheathing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sheathing.getId().intValue()))
            .andExpect(jsonPath("$.thickness").value(DEFAULT_THICKNESS.doubleValue()))
            .andExpect(jsonPath("$.sheathingKind").value(DEFAULT_SHEATHING_KIND.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSheathing() throws Exception {
        // Get the sheathing
        restSheathingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSheathing() throws Exception {
        // Initialize the database
        sheathingRepository.saveAndFlush(sheathing);

        int databaseSizeBeforeUpdate = sheathingRepository.findAll().size();

        // Update the sheathing
        Sheathing updatedSheathing = sheathingRepository.findById(sheathing.getId()).get();
        // Disconnect from session so that the updates on updatedSheathing are not directly saved in db
        em.detach(updatedSheathing);
        updatedSheathing.thickness(UPDATED_THICKNESS).sheathingKind(UPDATED_SHEATHING_KIND);

        restSheathingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSheathing.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSheathing))
            )
            .andExpect(status().isOk());

        // Validate the Sheathing in the database
        List<Sheathing> sheathingList = sheathingRepository.findAll();
        assertThat(sheathingList).hasSize(databaseSizeBeforeUpdate);
        Sheathing testSheathing = sheathingList.get(sheathingList.size() - 1);
        assertThat(testSheathing.getThickness()).isEqualTo(UPDATED_THICKNESS);
        assertThat(testSheathing.getSheathingKind()).isEqualTo(UPDATED_SHEATHING_KIND);
    }

    @Test
    @Transactional
    void putNonExistingSheathing() throws Exception {
        int databaseSizeBeforeUpdate = sheathingRepository.findAll().size();
        sheathing.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSheathingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sheathing.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sheathing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sheathing in the database
        List<Sheathing> sheathingList = sheathingRepository.findAll();
        assertThat(sheathingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSheathing() throws Exception {
        int databaseSizeBeforeUpdate = sheathingRepository.findAll().size();
        sheathing.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSheathingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sheathing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sheathing in the database
        List<Sheathing> sheathingList = sheathingRepository.findAll();
        assertThat(sheathingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSheathing() throws Exception {
        int databaseSizeBeforeUpdate = sheathingRepository.findAll().size();
        sheathing.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSheathingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sheathing)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sheathing in the database
        List<Sheathing> sheathingList = sheathingRepository.findAll();
        assertThat(sheathingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSheathingWithPatch() throws Exception {
        // Initialize the database
        sheathingRepository.saveAndFlush(sheathing);

        int databaseSizeBeforeUpdate = sheathingRepository.findAll().size();

        // Update the sheathing using partial update
        Sheathing partialUpdatedSheathing = new Sheathing();
        partialUpdatedSheathing.setId(sheathing.getId());

        partialUpdatedSheathing.sheathingKind(UPDATED_SHEATHING_KIND);

        restSheathingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSheathing.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSheathing))
            )
            .andExpect(status().isOk());

        // Validate the Sheathing in the database
        List<Sheathing> sheathingList = sheathingRepository.findAll();
        assertThat(sheathingList).hasSize(databaseSizeBeforeUpdate);
        Sheathing testSheathing = sheathingList.get(sheathingList.size() - 1);
        assertThat(testSheathing.getThickness()).isEqualTo(DEFAULT_THICKNESS);
        assertThat(testSheathing.getSheathingKind()).isEqualTo(UPDATED_SHEATHING_KIND);
    }

    @Test
    @Transactional
    void fullUpdateSheathingWithPatch() throws Exception {
        // Initialize the database
        sheathingRepository.saveAndFlush(sheathing);

        int databaseSizeBeforeUpdate = sheathingRepository.findAll().size();

        // Update the sheathing using partial update
        Sheathing partialUpdatedSheathing = new Sheathing();
        partialUpdatedSheathing.setId(sheathing.getId());

        partialUpdatedSheathing.thickness(UPDATED_THICKNESS).sheathingKind(UPDATED_SHEATHING_KIND);

        restSheathingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSheathing.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSheathing))
            )
            .andExpect(status().isOk());

        // Validate the Sheathing in the database
        List<Sheathing> sheathingList = sheathingRepository.findAll();
        assertThat(sheathingList).hasSize(databaseSizeBeforeUpdate);
        Sheathing testSheathing = sheathingList.get(sheathingList.size() - 1);
        assertThat(testSheathing.getThickness()).isEqualTo(UPDATED_THICKNESS);
        assertThat(testSheathing.getSheathingKind()).isEqualTo(UPDATED_SHEATHING_KIND);
    }

    @Test
    @Transactional
    void patchNonExistingSheathing() throws Exception {
        int databaseSizeBeforeUpdate = sheathingRepository.findAll().size();
        sheathing.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSheathingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sheathing.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sheathing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sheathing in the database
        List<Sheathing> sheathingList = sheathingRepository.findAll();
        assertThat(sheathingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSheathing() throws Exception {
        int databaseSizeBeforeUpdate = sheathingRepository.findAll().size();
        sheathing.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSheathingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sheathing))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sheathing in the database
        List<Sheathing> sheathingList = sheathingRepository.findAll();
        assertThat(sheathingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSheathing() throws Exception {
        int databaseSizeBeforeUpdate = sheathingRepository.findAll().size();
        sheathing.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSheathingMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(sheathing))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sheathing in the database
        List<Sheathing> sheathingList = sheathingRepository.findAll();
        assertThat(sheathingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSheathing() throws Exception {
        // Initialize the database
        sheathingRepository.saveAndFlush(sheathing);

        int databaseSizeBeforeDelete = sheathingRepository.findAll().size();

        // Delete the sheathing
        restSheathingMockMvc
            .perform(delete(ENTITY_API_URL_ID, sheathing.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sheathing> sheathingList = sheathingRepository.findAll();
        assertThat(sheathingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
