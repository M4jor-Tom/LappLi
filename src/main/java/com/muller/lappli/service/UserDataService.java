package com.muller.lappli.service;

import com.muller.lappli.domain.UserData;
import java.util.Optional;

/**
 * Service Interface for managing {@link UserData}.
 */
public interface UserDataService extends IService<UserData> {
    /**
     * Finds an UserData from an User
     *
     * @param userLogin the user's login
     * @return the userData of the user
     */
    Optional<UserData> findOrCreateUserDataByLogin(String userLogin);
}
