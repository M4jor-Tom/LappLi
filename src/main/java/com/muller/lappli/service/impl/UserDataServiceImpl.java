package com.muller.lappli.service.impl;

import com.muller.lappli.domain.User;
import com.muller.lappli.domain.UserData;
import com.muller.lappli.repository.UserDataRepository;
import com.muller.lappli.repository.UserRepository;
import com.muller.lappli.service.UserDataService;
import com.muller.lappli.service.UserService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserData}.
 */
@Service
@Transactional
public class UserDataServiceImpl implements UserDataService {

    private final Logger log = LoggerFactory.getLogger(UserDataServiceImpl.class);

    private final UserDataRepository userDataRepository;

    private final UserRepository userRepository;

    private final UserService userService;

    public UserDataServiceImpl(UserDataRepository userDataRepository, UserRepository userRepository, UserService userService) {
        this.userDataRepository = userDataRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public UserData save(UserData userData) {
        log.debug("Request to save UserData : {}", userData);
        Long userId = userData.getUser().getId();
        userRepository.findById(userId).ifPresent(userData::user);
        return userDataRepository.save(userData);
    }

    @Override
    public Optional<UserData> partialUpdate(UserData userData) {
        log.debug("Request to partially update UserData : {}", userData);

        return userDataRepository
            .findById(userData.getId())
            .map(existingUserData -> {
                return existingUserData;
            })
            .map(userDataRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserData> findAll() {
        log.debug("Request to get all UserData");
        return userDataRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserData> findOne(Long id) {
        log.debug("Request to get UserData : {}", id);
        return userDataRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserData : {}", id);
        userDataRepository.deleteById(id);
    }

    @Override
    public Optional<UserData> findOrCreateUserDataByLogin(String userLogin) {
        if (userLogin != null) {
            for (UserData userData : findAll()) {
                if (userLogin.equals(userData.getUser().getLogin())) {
                    return Optional.of(userData);
                }
            }

            UserData newUserData = new UserData();

            Optional<User> optionalUser = userService.getUserWithAuthoritiesByLogin(userLogin);

            if (optionalUser.isPresent()) {
                return Optional.of(newUserData.user(optionalUser.get()));
            }
        }

        return Optional.empty();
    }
}
