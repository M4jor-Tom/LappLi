package com.muller.lappli.service;

import com.muller.lappli.domain.Sheathing;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Sheathing}.
 */
public interface SheathingService {
    /**
     * Save a sheathing.
     *
     * @param sheathing the entity to save.
     * @return the persisted entity.
     */
    Sheathing save(Sheathing sheathing);

    /**
     * Partially updates a sheathing.
     *
     * @param sheathing the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Sheathing> partialUpdate(Sheathing sheathing);

    /**
     * Get all the sheathings.
     *
     * @return the list of entities.
     */
    List<Sheathing> findAll();

    /**
     * Get the "id" sheathing.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Sheathing> findOne(Long id);

    /**
     * Delete the "id" sheathing.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
