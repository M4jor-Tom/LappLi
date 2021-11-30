package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.Bangle;
import com.muller.lappli.domain.BangleSupply;
import com.muller.lappli.repository.BangleSupplyRepository;
import com.muller.lappli.service.criteria.BangleSupplyCriteria;
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
    private static final Long SMALLER_APPARITIONS = 1L - 1L;

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
        BangleSupply bangleSupply = new BangleSupply().apparitions(DEFAULT_APPARITIONS);
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
        return bangleSupply;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BangleSupply createUpdatedEntity(EntityManager em) {
        BangleSupply bangleSupply = new BangleSupply().apparitions(UPDATED_APPARITIONS);
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
            .andExpect(jsonPath("$.[*].apparitions").value(hasItem(DEFAULT_APPARITIONS.intValue())));
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
            .andExpect(jsonPath("$.apparitions").value(DEFAULT_APPARITIONS.intValue()));
    }

    @Test
    @Transactional
    void getBangleSuppliesByIdFiltering() throws Exception {
        // Initialize the database
        bangleSupplyRepository.saveAndFlush(bangleSupply);

        Long id = bangleSupply.getId();

        defaultBangleSupplyShouldBeFound("id.equals=" + id);
        defaultBangleSupplyShouldNotBeFound("id.notEquals=" + id);

        defaultBangleSupplyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBangleSupplyShouldNotBeFound("id.greaterThan=" + id);

        defaultBangleSupplyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBangleSupplyShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBangleSuppliesByApparitionsIsEqualToSomething() throws Exception {
        // Initialize the database
        bangleSupplyRepository.saveAndFlush(bangleSupply);

        // Get all the bangleSupplyList where apparitions equals to DEFAULT_APPARITIONS
        defaultBangleSupplyShouldBeFound("apparitions.equals=" + DEFAULT_APPARITIONS);

        // Get all the bangleSupplyList where apparitions equals to UPDATED_APPARITIONS
        defaultBangleSupplyShouldNotBeFound("apparitions.equals=" + UPDATED_APPARITIONS);
    }

    @Test
    @Transactional
    void getAllBangleSuppliesByApparitionsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bangleSupplyRepository.saveAndFlush(bangleSupply);

        // Get all the bangleSupplyList where apparitions not equals to DEFAULT_APPARITIONS
        defaultBangleSupplyShouldNotBeFound("apparitions.notEquals=" + DEFAULT_APPARITIONS);

        // Get all the bangleSupplyList where apparitions not equals to UPDATED_APPARITIONS
        defaultBangleSupplyShouldBeFound("apparitions.notEquals=" + UPDATED_APPARITIONS);
    }

    @Test
    @Transactional
    void getAllBangleSuppliesByApparitionsIsInShouldWork() throws Exception {
        // Initialize the database
        bangleSupplyRepository.saveAndFlush(bangleSupply);

        // Get all the bangleSupplyList where apparitions in DEFAULT_APPARITIONS or UPDATED_APPARITIONS
        defaultBangleSupplyShouldBeFound("apparitions.in=" + DEFAULT_APPARITIONS + "," + UPDATED_APPARITIONS);

        // Get all the bangleSupplyList where apparitions equals to UPDATED_APPARITIONS
        defaultBangleSupplyShouldNotBeFound("apparitions.in=" + UPDATED_APPARITIONS);
    }

    @Test
    @Transactional
    void getAllBangleSuppliesByApparitionsIsNullOrNotNull() throws Exception {
        // Initialize the database
        bangleSupplyRepository.saveAndFlush(bangleSupply);

        // Get all the bangleSupplyList where apparitions is not null
        defaultBangleSupplyShouldBeFound("apparitions.specified=true");

        // Get all the bangleSupplyList where apparitions is null
        defaultBangleSupplyShouldNotBeFound("apparitions.specified=false");
    }

    @Test
    @Transactional
    void getAllBangleSuppliesByApparitionsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bangleSupplyRepository.saveAndFlush(bangleSupply);

        // Get all the bangleSupplyList where apparitions is greater than or equal to DEFAULT_APPARITIONS
        defaultBangleSupplyShouldBeFound("apparitions.greaterThanOrEqual=" + DEFAULT_APPARITIONS);

        // Get all the bangleSupplyList where apparitions is greater than or equal to UPDATED_APPARITIONS
        defaultBangleSupplyShouldNotBeFound("apparitions.greaterThanOrEqual=" + UPDATED_APPARITIONS);
    }

    @Test
    @Transactional
    void getAllBangleSuppliesByApparitionsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bangleSupplyRepository.saveAndFlush(bangleSupply);

        // Get all the bangleSupplyList where apparitions is less than or equal to DEFAULT_APPARITIONS
        defaultBangleSupplyShouldBeFound("apparitions.lessThanOrEqual=" + DEFAULT_APPARITIONS);

        // Get all the bangleSupplyList where apparitions is less than or equal to SMALLER_APPARITIONS
        defaultBangleSupplyShouldNotBeFound("apparitions.lessThanOrEqual=" + SMALLER_APPARITIONS);
    }

    @Test
    @Transactional
    void getAllBangleSuppliesByApparitionsIsLessThanSomething() throws Exception {
        // Initialize the database
        bangleSupplyRepository.saveAndFlush(bangleSupply);

        // Get all the bangleSupplyList where apparitions is less than DEFAULT_APPARITIONS
        defaultBangleSupplyShouldNotBeFound("apparitions.lessThan=" + DEFAULT_APPARITIONS);

        // Get all the bangleSupplyList where apparitions is less than UPDATED_APPARITIONS
        defaultBangleSupplyShouldBeFound("apparitions.lessThan=" + UPDATED_APPARITIONS);
    }

    @Test
    @Transactional
    void getAllBangleSuppliesByApparitionsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bangleSupplyRepository.saveAndFlush(bangleSupply);

        // Get all the bangleSupplyList where apparitions is greater than DEFAULT_APPARITIONS
        defaultBangleSupplyShouldNotBeFound("apparitions.greaterThan=" + DEFAULT_APPARITIONS);

        // Get all the bangleSupplyList where apparitions is greater than SMALLER_APPARITIONS
        defaultBangleSupplyShouldBeFound("apparitions.greaterThan=" + SMALLER_APPARITIONS);
    }

    @Test
    @Transactional
    void getAllBangleSuppliesByBangleIsEqualToSomething() throws Exception {
        // Initialize the database
        bangleSupplyRepository.saveAndFlush(bangleSupply);
        Bangle bangle;
        if (TestUtil.findAll(em, Bangle.class).isEmpty()) {
            bangle = BangleResourceIT.createEntity(em);
            em.persist(bangle);
            em.flush();
        } else {
            bangle = TestUtil.findAll(em, Bangle.class).get(0);
        }
        em.persist(bangle);
        em.flush();
        bangleSupply.setBangle(bangle);
        bangleSupplyRepository.saveAndFlush(bangleSupply);
        Long bangleId = bangle.getId();

        // Get all the bangleSupplyList where bangle equals to bangleId
        defaultBangleSupplyShouldBeFound("bangleId.equals=" + bangleId);

        // Get all the bangleSupplyList where bangle equals to (bangleId + 1)
        defaultBangleSupplyShouldNotBeFound("bangleId.equals=" + (bangleId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBangleSupplyShouldBeFound(String filter) throws Exception {
        restBangleSupplyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bangleSupply.getId().intValue())))
            .andExpect(jsonPath("$.[*].apparitions").value(hasItem(DEFAULT_APPARITIONS.intValue())));

        // Check, that the count call also returns 1
        restBangleSupplyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBangleSupplyShouldNotBeFound(String filter) throws Exception {
        restBangleSupplyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBangleSupplyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
        updatedBangleSupply.apparitions(UPDATED_APPARITIONS);

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

        partialUpdatedBangleSupply.apparitions(UPDATED_APPARITIONS);

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
