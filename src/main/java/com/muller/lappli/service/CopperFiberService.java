package com.muller.lappli.service;

import com.muller.lappli.domain.CopperFiber;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CopperFiber}.
 */
public interface CopperFiberService {
    /**
     * Save a copperFiber.
     *
     * @param copperFiber the entity to save.
     * @return the persisted entity.
     */
    CopperFiber save(CopperFiber copperFiber);

    /**
     * Partially updates a copperFiber.
     *
     * @param copperFiber the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CopperFiber> partialUpdate(CopperFiber copperFiber);

    /**
     * Get all the copperFibers.
     *
     * @return the list of entities.
     */
    List<CopperFiber> findAll();

    /**
     * Get the "id" copperFiber.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CopperFiber> findOne(Long id);

    /**
     * Delete the "id" copperFiber.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
