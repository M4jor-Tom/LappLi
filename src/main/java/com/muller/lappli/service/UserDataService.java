package com.muller.lappli.service;

import com.muller.lappli.domain.UserData;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link UserData}.
 */
public interface UserDataService {
    /**
     * Save a userData.
     *
     * @param userData the entity to save.
     * @return the persisted entity.
     */
    UserData save(UserData userData);

    /**
     * Partially updates a userData.
     *
     * @param userData the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UserData> partialUpdate(UserData userData);

    /**
     * Get all the userData.
     *
     * @return the list of entities.
     */
    List<UserData> findAll();

    /**
     * Get the "id" userData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserData> findOne(Long id);

    /**
     * Delete the "id" userData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Finds an UserData from an User
     *
     * @param userLogin the user's login
     * @return the userData of the user
     */
    Optional<UserData> findOrCreateUserDataByLogin(String userLogin);
}
