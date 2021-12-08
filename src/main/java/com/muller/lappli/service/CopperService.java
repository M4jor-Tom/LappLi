package com.muller.lappli.service;

import com.muller.lappli.domain.Copper;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Copper}.
 */
public interface CopperService {
    /**
     * Save a copper.
     *
     * @param copper the entity to save.
     * @return the persisted entity.
     */
    Copper save(Copper copper);

    /**
     * Partially updates a copper.
     *
     * @param copper the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Copper> partialUpdate(Copper copper);

    /**
     * Get all the coppers.
     *
     * @return the list of entities.
     */
    List<Copper> findAll();

    /**
     * Get the "id" copper.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Copper> findOne(Long id);

    /**
     * Delete the "id" copper.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
