package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.Material;
import com.muller.lappli.domain.MaterialMarkingStatistic;
import com.muller.lappli.repository.MaterialRepository;
import com.muller.lappli.service.criteria.MaterialCriteria;
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
 * Integration tests for the {@link MaterialResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MaterialResourceIT {

    private static final Long DEFAULT_NUMBER = 1L;
    private static final Long UPDATED_NUMBER = 2L;
    private static final Long SMALLER_NUMBER = 1L - 1L;

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/materials";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMaterialMockMvc;

    private Material material;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Material createEntity(EntityManager em) {
        Material material = new Material().number(DEFAULT_NUMBER).designation(DEFAULT_DESIGNATION);
        return material;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Material createUpdatedEntity(EntityManager em) {
        Material material = new Material().number(UPDATED_NUMBER).designation(UPDATED_DESIGNATION);
        return material;
    }

    @BeforeEach
    public void initTest() {
        material = createEntity(em);
    }

    @Test
    @Transactional
    void createMaterial() throws Exception {
        int databaseSizeBeforeCreate = materialRepository.findAll().size();
        // Create the Material
        restMaterialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(material)))
            .andExpect(status().isCreated());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeCreate + 1);
        Material testMaterial = materialList.get(materialList.size() - 1);
        assertThat(testMaterial.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testMaterial.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
    }

    @Test
    @Transactional
    void createMaterialWithExistingId() throws Exception {
        // Create the Material with an existing ID
        material.setId(1L);

        int databaseSizeBeforeCreate = materialRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaterialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(material)))
            .andExpect(status().isBadRequest());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = materialRepository.findAll().size();
        // set the field null
        material.setNumber(null);

        // Create the Material, which fails.

        restMaterialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(material)))
            .andExpect(status().isBadRequest());

        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = materialRepository.findAll().size();
        // set the field null
        material.setDesignation(null);

        // Create the Material, which fails.

        restMaterialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(material)))
            .andExpect(status().isBadRequest());

        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMaterials() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList
        restMaterialMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(material.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)));
    }

    @Test
    @Transactional
    void getMaterial() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get the material
        restMaterialMockMvc
            .perform(get(ENTITY_API_URL_ID, material.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(material.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.intValue()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION));
    }

    @Test
    @Transactional
    void getMaterialsByIdFiltering() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        Long id = material.getId();

        defaultMaterialShouldBeFound("id.equals=" + id);
        defaultMaterialShouldNotBeFound("id.notEquals=" + id);

        defaultMaterialShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMaterialShouldNotBeFound("id.greaterThan=" + id);

        defaultMaterialShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMaterialShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMaterialsByNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where number equals to DEFAULT_NUMBER
        defaultMaterialShouldBeFound("number.equals=" + DEFAULT_NUMBER);

        // Get all the materialList where number equals to UPDATED_NUMBER
        defaultMaterialShouldNotBeFound("number.equals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllMaterialsByNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where number not equals to DEFAULT_NUMBER
        defaultMaterialShouldNotBeFound("number.notEquals=" + DEFAULT_NUMBER);

        // Get all the materialList where number not equals to UPDATED_NUMBER
        defaultMaterialShouldBeFound("number.notEquals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllMaterialsByNumberIsInShouldWork() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where number in DEFAULT_NUMBER or UPDATED_NUMBER
        defaultMaterialShouldBeFound("number.in=" + DEFAULT_NUMBER + "," + UPDATED_NUMBER);

        // Get all the materialList where number equals to UPDATED_NUMBER
        defaultMaterialShouldNotBeFound("number.in=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllMaterialsByNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where number is not null
        defaultMaterialShouldBeFound("number.specified=true");

        // Get all the materialList where number is null
        defaultMaterialShouldNotBeFound("number.specified=false");
    }

    @Test
    @Transactional
    void getAllMaterialsByNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where number is greater than or equal to DEFAULT_NUMBER
        defaultMaterialShouldBeFound("number.greaterThanOrEqual=" + DEFAULT_NUMBER);

        // Get all the materialList where number is greater than or equal to UPDATED_NUMBER
        defaultMaterialShouldNotBeFound("number.greaterThanOrEqual=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllMaterialsByNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where number is less than or equal to DEFAULT_NUMBER
        defaultMaterialShouldBeFound("number.lessThanOrEqual=" + DEFAULT_NUMBER);

        // Get all the materialList where number is less than or equal to SMALLER_NUMBER
        defaultMaterialShouldNotBeFound("number.lessThanOrEqual=" + SMALLER_NUMBER);
    }

    @Test
    @Transactional
    void getAllMaterialsByNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where number is less than DEFAULT_NUMBER
        defaultMaterialShouldNotBeFound("number.lessThan=" + DEFAULT_NUMBER);

        // Get all the materialList where number is less than UPDATED_NUMBER
        defaultMaterialShouldBeFound("number.lessThan=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllMaterialsByNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where number is greater than DEFAULT_NUMBER
        defaultMaterialShouldNotBeFound("number.greaterThan=" + DEFAULT_NUMBER);

        // Get all the materialList where number is greater than SMALLER_NUMBER
        defaultMaterialShouldBeFound("number.greaterThan=" + SMALLER_NUMBER);
    }

    @Test
    @Transactional
    void getAllMaterialsByDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where designation equals to DEFAULT_DESIGNATION
        defaultMaterialShouldBeFound("designation.equals=" + DEFAULT_DESIGNATION);

        // Get all the materialList where designation equals to UPDATED_DESIGNATION
        defaultMaterialShouldNotBeFound("designation.equals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllMaterialsByDesignationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where designation not equals to DEFAULT_DESIGNATION
        defaultMaterialShouldNotBeFound("designation.notEquals=" + DEFAULT_DESIGNATION);

        // Get all the materialList where designation not equals to UPDATED_DESIGNATION
        defaultMaterialShouldBeFound("designation.notEquals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllMaterialsByDesignationIsInShouldWork() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where designation in DEFAULT_DESIGNATION or UPDATED_DESIGNATION
        defaultMaterialShouldBeFound("designation.in=" + DEFAULT_DESIGNATION + "," + UPDATED_DESIGNATION);

        // Get all the materialList where designation equals to UPDATED_DESIGNATION
        defaultMaterialShouldNotBeFound("designation.in=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllMaterialsByDesignationIsNullOrNotNull() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where designation is not null
        defaultMaterialShouldBeFound("designation.specified=true");

        // Get all the materialList where designation is null
        defaultMaterialShouldNotBeFound("designation.specified=false");
    }

    @Test
    @Transactional
    void getAllMaterialsByDesignationContainsSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where designation contains DEFAULT_DESIGNATION
        defaultMaterialShouldBeFound("designation.contains=" + DEFAULT_DESIGNATION);

        // Get all the materialList where designation contains UPDATED_DESIGNATION
        defaultMaterialShouldNotBeFound("designation.contains=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllMaterialsByDesignationNotContainsSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList where designation does not contain DEFAULT_DESIGNATION
        defaultMaterialShouldNotBeFound("designation.doesNotContain=" + DEFAULT_DESIGNATION);

        // Get all the materialList where designation does not contain UPDATED_DESIGNATION
        defaultMaterialShouldBeFound("designation.doesNotContain=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllMaterialsByMaterialMarkingStatisticListIsEqualToSomething() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);
        MaterialMarkingStatistic materialMarkingStatisticList;
        if (TestUtil.findAll(em, MaterialMarkingStatistic.class).isEmpty()) {
            materialMarkingStatisticList = MaterialMarkingStatisticResourceIT.createEntity(em);
            em.persist(materialMarkingStatisticList);
            em.flush();
        } else {
            materialMarkingStatisticList = TestUtil.findAll(em, MaterialMarkingStatistic.class).get(0);
        }
        em.persist(materialMarkingStatisticList);
        em.flush();
        material.addMaterialMarkingStatisticList(materialMarkingStatisticList);
        materialRepository.saveAndFlush(material);
        Long materialMarkingStatisticListId = materialMarkingStatisticList.getId();

        // Get all the materialList where materialMarkingStatisticList equals to materialMarkingStatisticListId
        defaultMaterialShouldBeFound("materialMarkingStatisticListId.equals=" + materialMarkingStatisticListId);

        // Get all the materialList where materialMarkingStatisticList equals to (materialMarkingStatisticListId + 1)
        defaultMaterialShouldNotBeFound("materialMarkingStatisticListId.equals=" + (materialMarkingStatisticListId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMaterialShouldBeFound(String filter) throws Exception {
        restMaterialMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(material.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)));

        // Check, that the count call also returns 1
        restMaterialMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMaterialShouldNotBeFound(String filter) throws Exception {
        restMaterialMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMaterialMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMaterial() throws Exception {
        // Get the material
        restMaterialMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMaterial() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        int databaseSizeBeforeUpdate = materialRepository.findAll().size();

        // Update the material
        Material updatedMaterial = materialRepository.findById(material.getId()).get();
        // Disconnect from session so that the updates on updatedMaterial are not directly saved in db
        em.detach(updatedMaterial);
        updatedMaterial.number(UPDATED_NUMBER).designation(UPDATED_DESIGNATION);

        restMaterialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMaterial.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMaterial))
            )
            .andExpect(status().isOk());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeUpdate);
        Material testMaterial = materialList.get(materialList.size() - 1);
        assertThat(testMaterial.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testMaterial.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void putNonExistingMaterial() throws Exception {
        int databaseSizeBeforeUpdate = materialRepository.findAll().size();
        material.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaterialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, material.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(material))
            )
            .andExpect(status().isBadRequest());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMaterial() throws Exception {
        int databaseSizeBeforeUpdate = materialRepository.findAll().size();
        material.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(material))
            )
            .andExpect(status().isBadRequest());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMaterial() throws Exception {
        int databaseSizeBeforeUpdate = materialRepository.findAll().size();
        material.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(material)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMaterialWithPatch() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        int databaseSizeBeforeUpdate = materialRepository.findAll().size();

        // Update the material using partial update
        Material partialUpdatedMaterial = new Material();
        partialUpdatedMaterial.setId(material.getId());

        partialUpdatedMaterial.number(UPDATED_NUMBER).designation(UPDATED_DESIGNATION);

        restMaterialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMaterial.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMaterial))
            )
            .andExpect(status().isOk());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeUpdate);
        Material testMaterial = materialList.get(materialList.size() - 1);
        assertThat(testMaterial.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testMaterial.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void fullUpdateMaterialWithPatch() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        int databaseSizeBeforeUpdate = materialRepository.findAll().size();

        // Update the material using partial update
        Material partialUpdatedMaterial = new Material();
        partialUpdatedMaterial.setId(material.getId());

        partialUpdatedMaterial.number(UPDATED_NUMBER).designation(UPDATED_DESIGNATION);

        restMaterialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMaterial.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMaterial))
            )
            .andExpect(status().isOk());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeUpdate);
        Material testMaterial = materialList.get(materialList.size() - 1);
        assertThat(testMaterial.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testMaterial.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void patchNonExistingMaterial() throws Exception {
        int databaseSizeBeforeUpdate = materialRepository.findAll().size();
        material.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaterialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, material.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(material))
            )
            .andExpect(status().isBadRequest());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMaterial() throws Exception {
        int databaseSizeBeforeUpdate = materialRepository.findAll().size();
        material.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(material))
            )
            .andExpect(status().isBadRequest());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMaterial() throws Exception {
        int databaseSizeBeforeUpdate = materialRepository.findAll().size();
        material.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(material)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMaterial() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        int databaseSizeBeforeDelete = materialRepository.findAll().size();

        // Delete the material
        restMaterialMockMvc
            .perform(delete(ENTITY_API_URL_ID, material.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
