package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.StrandSupply;
import com.muller.lappli.domain.Study;
import com.muller.lappli.domain.UserData;
import com.muller.lappli.repository.StudyRepository;
//import com.muller.lappli.service.criteria.StudyCriteria;
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link StudyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StudyResourceIT {

    private static final Long DEFAULT_NUMBER = 1L;
    private static final Long UPDATED_NUMBER = 2L;

    //private static final Instant DEFAULT_LAST_EDITION_INSTANT = Instant.ofEpochMilli(0L);
    //private static final Instant UPDATED_LAST_EDITION_INSTANT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/studies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStudyMockMvc;

    private Study study;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Study createEntity(EntityManager em) {
        Study study = new Study().number(DEFAULT_NUMBER);
        // Add required entity
        UserData userData;
        if (TestUtil.findAll(em, UserData.class).isEmpty()) {
            userData = UserDataResourceIT.createEntity(em);
            em.persist(userData);
            em.flush();
        } else {
            userData = TestUtil.findAll(em, UserData.class).get(0);
        }
        study.setAuthor(userData);
        return study;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Study createUpdatedEntity(EntityManager em) {
        Study study = new Study().number(UPDATED_NUMBER); //.lastEditionInstant(UPDATED_LAST_EDITION_INSTANT);
        // Add required entity
        UserData userData;
        if (TestUtil.findAll(em, UserData.class).isEmpty()) {
            userData = UserDataResourceIT.createUpdatedEntity(em);
            em.persist(userData);
            em.flush();
        } else {
            userData = TestUtil.findAll(em, UserData.class).get(0);
        }
        study.setAuthor(userData);
        return study;
    }

    @BeforeEach
    public void initTest() {
        study = createEntity(em);
    }

    @Test
    @Transactional
    void createStudy() throws Exception {
        int databaseSizeBeforeCreate = studyRepository.findAll().size();

        int expectedDatabaseSizeAfterCreate = databaseSizeBeforeCreate;

        // Create the Study
        if (!study.isAuthored()) {
            restStudyMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(study)))
                .andExpect(status().isCreated());

            expectedDatabaseSizeAfterCreate += 1;
        } else {
            restStudyMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(study)))
                .andExpect(status().isBadRequest());
        }

        // Validate the Study in the database
        List<Study> studyList = studyRepository.findAll();
        assertThat(studyList).hasSize(expectedDatabaseSizeAfterCreate);
        if (studyList.size() > 0) {
            Study testStudy = studyList.get(studyList.size() - 1);
            assertThat(testStudy.getNumber()).isEqualTo(DEFAULT_NUMBER);
            //assertThat(testStudy.getLastEditionInstant()).isEqualTo(DEFAULT_LAST_EDITION_INSTANT);
        }
    }

    @Test
    @Transactional
    void createStudyWithExistingId() throws Exception {
        // Create the Study with an existing ID
        study.setId(1L);

        int databaseSizeBeforeCreate = studyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(study)))
            .andExpect(status().isBadRequest());

        // Validate the Study in the database
        List<Study> studyList = studyRepository.findAll();
        assertThat(studyList).hasSize(databaseSizeBeforeCreate);
    }

    /*@Test
    @Transactional
    void checkLastEditionInstantIsRequired() throws Exception {
        int databaseSizeBeforeTest = studyRepository.findAll().size();
        // set the field null
        study.setLastEditionInstant(null);

        // Create the Study, which fails.

        restStudyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(study)))
            .andExpect(status().isBadRequest());

        List<Study> studyList = studyRepository.findAll();
        assertThat(studyList).hasSize(databaseSizeBeforeTest);
    }*/

    @Test
    @Transactional
    void getAllStudies() throws Exception {
        // Initialize the database
        studyRepository.saveAndFlush(study);

        // Get all the studyList
        restStudyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(study.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.intValue())));
        //.andExpect(jsonPath("$.[*].lastEditionInstant").value(hasItem(DEFAULT_LAST_EDITION_INSTANT.toString())));
    }

    @Test
    @Transactional
    void getStudy() throws Exception {
        // Initialize the database
        studyRepository.saveAndFlush(study);

        // Get the study
        restStudyMockMvc
            .perform(get(ENTITY_API_URL_ID, study.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(study.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.intValue()));
        //.andExpect(jsonPath("$.lastEditionInstant").value(DEFAULT_LAST_EDITION_INSTANT.toString()));
    }

    /*@Test
    @Transactional
    void getAllStudiesByLastEditionInstantIsEqualToSomething() throws Exception {
        // Initialize the database
        studyRepository.saveAndFlush(study);

        // Get all the studyList where lastEditionInstant equals to DEFAULT_LAST_EDITION_INSTANT
        defaultStudyShouldBeFound("lastEditionInstant.equals=" + DEFAULT_LAST_EDITION_INSTANT);

        // Get all the studyList where lastEditionInstant equals to UPDATED_LAST_EDITION_INSTANT
        defaultStudyShouldNotBeFound("lastEditionInstant.equals=" + UPDATED_LAST_EDITION_INSTANT);
    }*/

    /*@Test
    @Transactional
    void getAllStudiesByLastEditionInstantIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studyRepository.saveAndFlush(study);

        // Get all the studyList where lastEditionInstant not equals to DEFAULT_LAST_EDITION_INSTANT
        defaultStudyShouldNotBeFound("lastEditionInstant.notEquals=" + DEFAULT_LAST_EDITION_INSTANT);

        // Get all the studyList where lastEditionInstant not equals to UPDATED_LAST_EDITION_INSTANT
        defaultStudyShouldBeFound("lastEditionInstant.notEquals=" + UPDATED_LAST_EDITION_INSTANT);
    }*/

    /*@Test
    @Transactional
    void getAllStudiesByLastEditionInstantIsInShouldWork() throws Exception {
        // Initialize the database
        studyRepository.saveAndFlush(study);

        // Get all the studyList where lastEditionInstant in DEFAULT_LAST_EDITION_INSTANT or UPDATED_LAST_EDITION_INSTANT
        defaultStudyShouldBeFound("lastEditionInstant.in=" + DEFAULT_LAST_EDITION_INSTANT + "," + UPDATED_LAST_EDITION_INSTANT);

        // Get all the studyList where lastEditionInstant equals to UPDATED_LAST_EDITION_INSTANT
        defaultStudyShouldNotBeFound("lastEditionInstant.in=" + UPDATED_LAST_EDITION_INSTANT);
    }*/

    @Test
    @Transactional
    void getNonExistingStudy() throws Exception {
        // Get the study
        restStudyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStudy() throws Exception {
        // Initialize the database
        studyRepository.saveAndFlush(study);

        int databaseSizeBeforeUpdate = studyRepository.findAll().size();

        // Update the study
        Study httpSentUpdatedStudy = studyRepository.findById(study.getId()).get();
        // Disconnect from session so that the updates on httpSentUpdatedStudy are not directly saved in db
        em.detach(httpSentUpdatedStudy);
        httpSentUpdatedStudy.number(UPDATED_NUMBER);

        if (!httpSentUpdatedStudy.isAuthored()) {
            restStudyMockMvc
                .perform(
                    put(ENTITY_API_URL_ID, httpSentUpdatedStudy.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(httpSentUpdatedStudy))
                )
                .andExpect(status().isOk());

            // Validate the Study in the database
            List<Study> studyList = studyRepository.findAll();
            assertThat(studyList).hasSize(databaseSizeBeforeUpdate);
            Study testStudy = studyList.get(studyList.size() - 1);
            assertThat(testStudy.getNumber()).isEqualTo(UPDATED_NUMBER);
            //assertThat(testStudy.getLastEditionInstant()).isEqualTo(UPDATED_LAST_EDITION_INSTANT);
        } else {
            restStudyMockMvc
                .perform(
                    put(ENTITY_API_URL_ID, httpSentUpdatedStudy.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(httpSentUpdatedStudy))
                )
                .andExpect(status().isBadRequest());
        }
    }

    @Test
    @Transactional
    void putNonExistingStudy() throws Exception {
        int databaseSizeBeforeUpdate = studyRepository.findAll().size();
        study.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, study.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(study))
            )
            .andExpect(status().isBadRequest());

        // Validate the Study in the database
        List<Study> studyList = studyRepository.findAll();
        assertThat(studyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStudy() throws Exception {
        int databaseSizeBeforeUpdate = studyRepository.findAll().size();
        study.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(study))
            )
            .andExpect(status().isBadRequest());

        // Validate the Study in the database
        List<Study> studyList = studyRepository.findAll();
        assertThat(studyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStudy() throws Exception {
        int databaseSizeBeforeUpdate = studyRepository.findAll().size();
        study.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(study)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Study in the database
        List<Study> studyList = studyRepository.findAll();
        assertThat(studyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStudyWithPatch() throws Exception {
        // Initialize the database
        studyRepository.saveAndFlush(study);

        int databaseSizeBeforeUpdate = studyRepository.findAll().size();

        // Update the study using partial update
        Study partialUpdatedStudy = new Study();
        partialUpdatedStudy.setId(study.getId());

        partialUpdatedStudy.number(UPDATED_NUMBER);

        restStudyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStudy.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStudy))
            )
            .andExpect(status().isOk());

        // Validate the Study in the database
        List<Study> studyList = studyRepository.findAll();
        assertThat(studyList).hasSize(databaseSizeBeforeUpdate);
        Study testStudy = studyList.get(studyList.size() - 1);
        assertThat(testStudy.getNumber()).isEqualTo(UPDATED_NUMBER);
        //assertThat(testStudy.getLastEditionInstant()).isEqualTo(UPDATED_LAST_EDITION_INSTANT);
    }

    @Test
    @Transactional
    void fullUpdateStudyWithPatch() throws Exception {
        // Initialize the database
        studyRepository.saveAndFlush(study);

        int databaseSizeBeforeUpdate = studyRepository.findAll().size();

        // Update the study using partial update
        Study partialUpdatedStudy = new Study();
        partialUpdatedStudy.setId(study.getId());

        partialUpdatedStudy.number(UPDATED_NUMBER);

        restStudyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStudy.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStudy))
            )
            .andExpect(status().isOk());

        // Validate the Study in the database
        List<Study> studyList = studyRepository.findAll();
        assertThat(studyList).hasSize(databaseSizeBeforeUpdate);
        Study testStudy = studyList.get(studyList.size() - 1);
        assertThat(testStudy.getNumber()).isEqualTo(UPDATED_NUMBER);
        //assertThat(testStudy.getLastEditionInstant()).isEqualTo(UPDATED_LAST_EDITION_INSTANT);
    }

    @Test
    @Transactional
    void patchNonExistingStudy() throws Exception {
        int databaseSizeBeforeUpdate = studyRepository.findAll().size();
        study.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, study.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(study))
            )
            .andExpect(status().isBadRequest());

        // Validate the Study in the database
        List<Study> studyList = studyRepository.findAll();
        assertThat(studyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStudy() throws Exception {
        int databaseSizeBeforeUpdate = studyRepository.findAll().size();
        study.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(study))
            )
            .andExpect(status().isBadRequest());

        // Validate the Study in the database
        List<Study> studyList = studyRepository.findAll();
        assertThat(studyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStudy() throws Exception {
        int databaseSizeBeforeUpdate = studyRepository.findAll().size();
        study.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudyMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(study)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Study in the database
        List<Study> studyList = studyRepository.findAll();
        assertThat(studyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStudy() throws Exception {
        // Initialize the database
        studyRepository.saveAndFlush(study);

        int databaseSizeBeforeDelete = studyRepository.findAll().size();

        // Delete the study
        restStudyMockMvc
            .perform(delete(ENTITY_API_URL_ID, study.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Study> studyList = studyRepository.findAll();
        assertThat(studyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
