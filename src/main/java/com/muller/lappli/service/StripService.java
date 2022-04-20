package com.muller.lappli.service;

import com.muller.lappli.domain.Strip;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Strip}.
 */
public interface StripService {
    /**
     * Save a strip.
     *
     * @param strip the entity to save.
     * @return the persisted entity.
     */
    Strip save(Strip strip);

    /**
     * Partially updates a strip.
     *
     * @param strip the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Strip> partialUpdate(Strip strip);

    /**
     * Get all the strips.
     *
     * @return the list of entities.
     */
    List<Strip> findAll();

    /**
     * Get the "id" strip.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Strip> findOne(Long id);

    /**
     * Delete the "id" strip.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
