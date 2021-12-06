package com.muller.lappli.service;

import com.muller.lappli.domain.Bangle;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Bangle}.
 */
public interface BangleService {
    /**
     * Save a bangle.
     *
     * @param bangle the entity to save.
     * @return the persisted entity.
     */
    Bangle save(Bangle bangle);

    /**
     * Partially updates a bangle.
     *
     * @param bangle the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Bangle> partialUpdate(Bangle bangle);

    /**
     * Get all the bangles.
     *
     * @return the list of entities.
     */
    List<Bangle> findAll();

    /**
     * Get the "id" bangle.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Bangle> findOne(Long id);

    /**
     * Delete the "id" bangle.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
