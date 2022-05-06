package com.muller.lappli.service;

import com.muller.lappli.domain.Plaiter;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Plaiter}.
 */
public interface PlaiterService {
    /**
     * Save a plaiter.
     *
     * @param plaiter the entity to save.
     * @return the persisted entity.
     */
    Plaiter save(Plaiter plaiter);

    /**
     * Partially updates a plaiter.
     *
     * @param plaiter the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Plaiter> partialUpdate(Plaiter plaiter);

    /**
     * Get all the plaiters.
     *
     * @return the list of entities.
     */
    List<Plaiter> findAll();

    /**
     * Get the "id" plaiter.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Plaiter> findOne(Long id);

    /**
     * Delete the "id" plaiter.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
