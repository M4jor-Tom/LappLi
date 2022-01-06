package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.Bangle;
import com.muller.lappli.domain.BangleSupply;
import com.muller.lappli.domain.Strand;
import com.muller.lappli.repository.BangleSupplyRepository;
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
 * Integration tests for the {@link BangleSupplyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BangleSupplyResourceIT {

    private static final Long DEFAULT_APPARITIONS = 1L;
    private static final Long UPDATED_APPARITIONS = 2L;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bangle-supplies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BangleSupplyRepository bangleSupplyRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBangleSupplyMockMvc;

    private BangleSupply bangleSupply;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BangleSupply createEntity(EntityManager em) {
        BangleSupply bangleSupply = new BangleSupply().apparitions(DEFAULT_APPARITIONS).description(DEFAULT_DESCRIPTION);
        // Add required entity
        Bangle bangle;
        if (TestUtil.findAll(em, Bangle.class).isEmpty()) {
            bangle = BangleResourceIT.createEntity(em);
            em.persist(bangle);
            em.flush();
        } else {
            bangle = TestUtil.findAll(em, Bangle.class).get(0);
        }
        bangleSupply.setBangle(bangle);
        // Add required entity
        Strand strand;
        if (TestUtil.findAll(em, Strand.class).isEmpty()) {
            strand = StrandResourceIT.createEntity(em);
            em.persist(strand);
            em.flush();
        } else {
            strand = TestUtil.findAll(em, Strand.class).get(0);
        }
        bangleSupply.setOwnerStrand(strand);
        return bangleSupply;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BangleSupply createUpdatedEntity(EntityManager em) {
        BangleSupply bangleSupply = new BangleSupply().apparitions(UPDATED_APPARITIONS).description(UPDATED_DESCRIPTION);
        // Add required entity
        Bangle bangle;
        if (TestUtil.findAll(em, Bangle.class).isEmpty()) {
            bangle = BangleResourceIT.createUpdatedEntity(em);
            em.persist(bangle);
            em.flush();
        } else {
            bangle = TestUtil.findAll(em, Bangle.class).get(0);
        }
        bangleSupply.setBangle(bangle);
        // Add required entity
        Strand strand;
        if (TestUtil.findAll(em, Strand.class).isEmpty()) {
            strand = StrandResourceIT.createUpdatedEntity(em);
            em.persist(strand);
            em.flush();
        } else {
            strand = TestUtil.findAll(em, Strand.class).get(0);
        }
        bangleSupply.setOwnerStrand(strand);
        return bangleSupply;
    }

    @BeforeEach
    public void initTest() {
        bangleSupply = createEntity(em);
    }

    @Test
    @Transactional
    void createBangleSupply() throws Exception {
        int databaseSizeBeforeCreate = bangleSupplyRepository.findAll().size();
        // Create the BangleSupply
        restBangleSupplyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bangleSupply)))
            .andExpect(status().isCreated());

        // Validate the BangleSupply in the database
        List<BangleSupply> bangleSupplyList = bangleSupplyRepository.findAll();
        assertThat(bangleSupplyList).hasSize(databaseSizeBeforeCreate + 1);
        BangleSupply testBangleSupply = bangleSupplyList.get(bangleSupplyList.size() - 1);
        assertThat(testBangleSupply.getApparitions()).isEqualTo(DEFAULT_APPARITIONS);
        assertThat(testBangleSupply.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createBangleSupplyWithExistingId() throws Exception {
        // Create the BangleSupply with an existing ID
        bangleSupply.setId(1L);

        int databaseSizeBeforeCreate = bangleSupplyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBangleSupplyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bangleSupply)))
            .andExpect(status().isBadRequest());

        // Validate the BangleSupply in the database
        List<BangleSupply> bangleSupplyList = bangleSupplyRepository.findAll();
        assertThat(bangleSupplyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkApparitionsIsRequired() throws Exception {
        int databaseSizeBeforeTest = bangleSupplyRepository.findAll().size();
        // set the field null
        bangleSupply.setApparitions(null);

        // Create the BangleSupply, which fails.

        restBangleSupplyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bangleSupply)))
            .andExpect(status().isBadRequest());

        List<BangleSupply> bangleSupplyList = bangleSupplyRepository.findAll();
        assertThat(bangleSupplyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBangleSupplies() throws Exception {
        // Initialize the database
        bangleSupplyRepository.saveAndFlush(bangleSupply);

        // Get all the bangleSupplyList
        restBangleSupplyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bangleSupply.getId().intValue())))
            .andExpect(jsonPath("$.[*].apparitions").value(hasItem(DEFAULT_APPARITIONS.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getBangleSupply() throws Exception {
        // Initialize the database
        bangleSupplyRepository.saveAndFlush(bangleSupply);

        // Get the bangleSupply
        restBangleSupplyMockMvc
            .perform(get(ENTITY_API_URL_ID, bangleSupply.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bangleSupply.getId().intValue()))
            .andExpect(jsonPath("$.apparitions").value(DEFAULT_APPARITIONS.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingBangleSupply() throws Exception {
        // Get the bangleSupply
        restBangleSupplyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBangleSupply() throws Exception {
        // Initialize the database
        bangleSupplyRepository.saveAndFlush(bangleSupply);

        int databaseSizeBeforeUpdate = bangleSupplyRepository.findAll().size();

        // Update the bangleSupply
        BangleSupply updatedBangleSupply = bangleSupplyRepository.findById(bangleSupply.getId()).get();
        // Disconnect from session so that the updates on updatedBangleSupply are not directly saved in db
        em.detach(updatedBangleSupply);
        updatedBangleSupply.apparitions(UPDATED_APPARITIONS).description(UPDATED_DESCRIPTION);

        restBangleSupplyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBangleSupply.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBangleSupply))
            )
            .andExpect(status().isOk());

        // Validate the BangleSupply in the database
        List<BangleSupply> bangleSupplyList = bangleSupplyRepository.findAll();
        assertThat(bangleSupplyList).hasSize(databaseSizeBeforeUpdate);
        BangleSupply testBangleSupply = bangleSupplyList.get(bangleSupplyList.size() - 1);
        assertThat(testBangleSupply.getApparitions()).isEqualTo(UPDATED_APPARITIONS);
        assertThat(testBangleSupply.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingBangleSupply() throws Exception {
        int databaseSizeBeforeUpdate = bangleSupplyRepository.findAll().size();
        bangleSupply.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBangleSupplyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bangleSupply.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bangleSupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the BangleSupply in the database
        List<BangleSupply> bangleSupplyList = bangleSupplyRepository.findAll();
        assertThat(bangleSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBangleSupply() throws Exception {
        int databaseSizeBeforeUpdate = bangleSupplyRepository.findAll().size();
        bangleSupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBangleSupplyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bangleSupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the BangleSupply in the database
        List<BangleSupply> bangleSupplyList = bangleSupplyRepository.findAll();
        assertThat(bangleSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBangleSupply() throws Exception {
        int databaseSizeBeforeUpdate = bangleSupplyRepository.findAll().size();
        bangleSupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBangleSupplyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bangleSupply)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BangleSupply in the database
        List<BangleSupply> bangleSupplyList = bangleSupplyRepository.findAll();
        assertThat(bangleSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBangleSupplyWithPatch() throws Exception {
        // Initialize the database
        bangleSupplyRepository.saveAndFlush(bangleSupply);

        int databaseSizeBeforeUpdate = bangleSupplyRepository.findAll().size();

        // Update the bangleSupply using partial update
        BangleSupply partialUpdatedBangleSupply = new BangleSupply();
        partialUpdatedBangleSupply.setId(bangleSupply.getId());

        partialUpdatedBangleSupply.description(UPDATED_DESCRIPTION);

        restBangleSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBangleSupply.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBangleSupply))
            )
            .andExpect(status().isOk());

        // Validate the BangleSupply in the database
        List<BangleSupply> bangleSupplyList = bangleSupplyRepository.findAll();
        assertThat(bangleSupplyList).hasSize(databaseSizeBeforeUpdate);
        BangleSupply testBangleSupply = bangleSupplyList.get(bangleSupplyList.size() - 1);
        assertThat(testBangleSupply.getApparitions()).isEqualTo(DEFAULT_APPARITIONS);
        assertThat(testBangleSupply.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateBangleSupplyWithPatch() throws Exception {
        // Initialize the database
        bangleSupplyRepository.saveAndFlush(bangleSupply);

        int databaseSizeBeforeUpdate = bangleSupplyRepository.findAll().size();

        // Update the bangleSupply using partial update
        BangleSupply partialUpdatedBangleSupply = new BangleSupply();
        partialUpdatedBangleSupply.setId(bangleSupply.getId());

        partialUpdatedBangleSupply.apparitions(UPDATED_APPARITIONS).description(UPDATED_DESCRIPTION);

        restBangleSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBangleSupply.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBangleSupply))
            )
            .andExpect(status().isOk());

        // Validate the BangleSupply in the database
        List<BangleSupply> bangleSupplyList = bangleSupplyRepository.findAll();
        assertThat(bangleSupplyList).hasSize(databaseSizeBeforeUpdate);
        BangleSupply testBangleSupply = bangleSupplyList.get(bangleSupplyList.size() - 1);
        assertThat(testBangleSupply.getApparitions()).isEqualTo(UPDATED_APPARITIONS);
        assertThat(testBangleSupply.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingBangleSupply() throws Exception {
        int databaseSizeBeforeUpdate = bangleSupplyRepository.findAll().size();
        bangleSupply.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBangleSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bangleSupply.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bangleSupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the BangleSupply in the database
        List<BangleSupply> bangleSupplyList = bangleSupplyRepository.findAll();
        assertThat(bangleSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBangleSupply() throws Exception {
        int databaseSizeBeforeUpdate = bangleSupplyRepository.findAll().size();
        bangleSupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBangleSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bangleSupply))
            )
            .andExpect(status().isBadRequest());

        // Validate the BangleSupply in the database
        List<BangleSupply> bangleSupplyList = bangleSupplyRepository.findAll();
        assertThat(bangleSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBangleSupply() throws Exception {
        int databaseSizeBeforeUpdate = bangleSupplyRepository.findAll().size();
        bangleSupply.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBangleSupplyMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bangleSupply))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BangleSupply in the database
        List<BangleSupply> bangleSupplyList = bangleSupplyRepository.findAll();
        assertThat(bangleSupplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBangleSupply() throws Exception {
        // Initialize the database
        bangleSupplyRepository.saveAndFlush(bangleSupply);

        int databaseSizeBeforeDelete = bangleSupplyRepository.findAll().size();

        // Delete the bangleSupply
        restBangleSupplyMockMvc
            .perform(delete(ENTITY_API_URL_ID, bangleSupply.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BangleSupply> bangleSupplyList = bangleSupplyRepository.findAll();
        assertThat(bangleSupplyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
