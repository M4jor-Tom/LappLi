package com.muller.lappli.service;

import com.muller.lappli.domain.Screen;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Screen}.
 */
public interface ScreenService {
    /**
     * Save a screen.
     *
     * @param screen the entity to save.
     * @return the persisted entity.
     */
    Screen save(Screen screen);

    /**
     * Partially updates a screen.
     *
     * @param screen the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Screen> partialUpdate(Screen screen, Boolean actualizeOwnerStrandSupply);

    /**
     * Get all the screens.
     *
     * @return the list of entities.
     */
    List<Screen> findAll();

    /**
     * Get the "id" screen.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Screen> findOne(Long id);

    /**
     * Delete the "id" screen.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
