package com.muller.lappli.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.muller.lappli.IntegrationTest;
import com.muller.lappli.domain.Study;
import com.muller.lappli.domain.User;
import com.muller.lappli.domain.UserData;
import com.muller.lappli.repository.UserDataRepository;
import com.muller.lappli.service.criteria.UserDataCriteria;
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
 * Integration tests for the {@link UserDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserDataResourceIT {

    private static final String ENTITY_API_URL = "/api/user-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserDataMockMvc;

    private UserData userData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserData createEntity(EntityManager em) {
        UserData userData = new UserData();
        return userData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserData createUpdatedEntity(EntityManager em) {
        UserData userData = new UserData();
        return userData;
    }

    @BeforeEach
    public void initTest() {
        userData = createEntity(em);
    }

    @Test
    @Transactional
    void createUserData() throws Exception {
        int databaseSizeBeforeCreate = userDataRepository.findAll().size();
        // Create the UserData
        restUserDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userData)))
            .andExpect(status().isCreated());

        // Validate the UserData in the database
        List<UserData> userDataList = userDataRepository.findAll();
        assertThat(userDataList).hasSize(databaseSizeBeforeCreate + 1);
        UserData testUserData = userDataList.get(userDataList.size() - 1);
    }

    @Test
    @Transactional
    void createUserDataWithExistingId() throws Exception {
        // Create the UserData with an existing ID
        userData.setId(1L);

        int databaseSizeBeforeCreate = userDataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userData)))
            .andExpect(status().isBadRequest());

        // Validate the UserData in the database
        List<UserData> userDataList = userDataRepository.findAll();
        assertThat(userDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUserData() throws Exception {
        // Initialize the database
        userDataRepository.saveAndFlush(userData);

        // Get all the userDataList
        restUserDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userData.getId().intValue())));
    }

    @Test
    @Transactional
    void getUserData() throws Exception {
        // Initialize the database
        userDataRepository.saveAndFlush(userData);

        // Get the userData
        restUserDataMockMvc
            .perform(get(ENTITY_API_URL_ID, userData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userData.getId().intValue()));
    }

    @Test
    @Transactional
    void getUserDataByIdFiltering() throws Exception {
        // Initialize the database
        userDataRepository.saveAndFlush(userData);

        Long id = userData.getId();

        defaultUserDataShouldBeFound("id.equals=" + id);
        defaultUserDataShouldNotBeFound("id.notEquals=" + id);

        defaultUserDataShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUserDataShouldNotBeFound("id.greaterThan=" + id);

        defaultUserDataShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUserDataShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllUserDataByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        userDataRepository.saveAndFlush(userData);
        User user;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            user = UserResourceIT.createEntity(em);
            em.persist(user);
            em.flush();
        } else {
            user = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(user);
        em.flush();
        userData.setUser(user);
        userDataRepository.saveAndFlush(userData);
        Long userId = user.getId();

        // Get all the userDataList where user equals to userId
        defaultUserDataShouldBeFound("userId.equals=" + userId);

        // Get all the userDataList where user equals to (userId + 1)
        defaultUserDataShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    @Test
    @Transactional
    void getAllUserDataByStudiesIsEqualToSomething() throws Exception {
        // Initialize the database
        userDataRepository.saveAndFlush(userData);
        Study studies;
        if (TestUtil.findAll(em, Study.class).isEmpty()) {
            studies = StudyResourceIT.createEntity(em);
            em.persist(studies);
            em.flush();
        } else {
            studies = TestUtil.findAll(em, Study.class).get(0);
        }
        em.persist(studies);
        em.flush();
        userData.addStudies(studies);
        userDataRepository.saveAndFlush(userData);
        Long studiesId = studies.getId();

        // Get all the userDataList where studies equals to studiesId
        defaultUserDataShouldBeFound("studiesId.equals=" + studiesId);

        // Get all the userDataList where studies equals to (studiesId + 1)
        defaultUserDataShouldNotBeFound("studiesId.equals=" + (studiesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserDataShouldBeFound(String filter) throws Exception {
        restUserDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userData.getId().intValue())));

        // Check, that the count call also returns 1
        restUserDataMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUserDataShouldNotBeFound(String filter) throws Exception {
        restUserDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserDataMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingUserData() throws Exception {
        // Get the userData
        restUserDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUserData() throws Exception {
        // Initialize the database
        userDataRepository.saveAndFlush(userData);

        int databaseSizeBeforeUpdate = userDataRepository.findAll().size();

        // Update the userData
        UserData updatedUserData = userDataRepository.findById(userData.getId()).get();
        // Disconnect from session so that the updates on updatedUserData are not directly saved in db
        em.detach(updatedUserData);

        restUserDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUserData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUserData))
            )
            .andExpect(status().isOk());

        // Validate the UserData in the database
        List<UserData> userDataList = userDataRepository.findAll();
        assertThat(userDataList).hasSize(databaseSizeBeforeUpdate);
        UserData testUserData = userDataList.get(userDataList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingUserData() throws Exception {
        int databaseSizeBeforeUpdate = userDataRepository.findAll().size();
        userData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userData.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userData))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserData in the database
        List<UserData> userDataList = userDataRepository.findAll();
        assertThat(userDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserData() throws Exception {
        int databaseSizeBeforeUpdate = userDataRepository.findAll().size();
        userData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userData))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserData in the database
        List<UserData> userDataList = userDataRepository.findAll();
        assertThat(userDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserData() throws Exception {
        int databaseSizeBeforeUpdate = userDataRepository.findAll().size();
        userData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserDataMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userData)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserData in the database
        List<UserData> userDataList = userDataRepository.findAll();
        assertThat(userDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserDataWithPatch() throws Exception {
        // Initialize the database
        userDataRepository.saveAndFlush(userData);

        int databaseSizeBeforeUpdate = userDataRepository.findAll().size();

        // Update the userData using partial update
        UserData partialUpdatedUserData = new UserData();
        partialUpdatedUserData.setId(userData.getId());

        restUserDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserData))
            )
            .andExpect(status().isOk());

        // Validate the UserData in the database
        List<UserData> userDataList = userDataRepository.findAll();
        assertThat(userDataList).hasSize(databaseSizeBeforeUpdate);
        UserData testUserData = userDataList.get(userDataList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateUserDataWithPatch() throws Exception {
        // Initialize the database
        userDataRepository.saveAndFlush(userData);

        int databaseSizeBeforeUpdate = userDataRepository.findAll().size();

        // Update the userData using partial update
        UserData partialUpdatedUserData = new UserData();
        partialUpdatedUserData.setId(userData.getId());

        restUserDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserData))
            )
            .andExpect(status().isOk());

        // Validate the UserData in the database
        List<UserData> userDataList = userDataRepository.findAll();
        assertThat(userDataList).hasSize(databaseSizeBeforeUpdate);
        UserData testUserData = userDataList.get(userDataList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingUserData() throws Exception {
        int databaseSizeBeforeUpdate = userDataRepository.findAll().size();
        userData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userData))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserData in the database
        List<UserData> userDataList = userDataRepository.findAll();
        assertThat(userDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserData() throws Exception {
        int databaseSizeBeforeUpdate = userDataRepository.findAll().size();
        userData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userData))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserData in the database
        List<UserData> userDataList = userDataRepository.findAll();
        assertThat(userDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserData() throws Exception {
        int databaseSizeBeforeUpdate = userDataRepository.findAll().size();
        userData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserDataMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(userData)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserData in the database
        List<UserData> userDataList = userDataRepository.findAll();
        assertThat(userDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserData() throws Exception {
        // Initialize the database
        userDataRepository.saveAndFlush(userData);

        int databaseSizeBeforeDelete = userDataRepository.findAll().size();

        // Delete the userData
        restUserDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, userData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserData> userDataList = userDataRepository.findAll();
        assertThat(userDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
