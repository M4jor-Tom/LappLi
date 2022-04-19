package com.muller.lappli.service;

import com.muller.lappli.domain.SteelFiber;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link SteelFiber}.
 */
public interface SteelFiberService {
    /**
     * Save a steelFiber.
     *
     * @param steelFiber the entity to save.
     * @return the persisted entity.
     */
    SteelFiber save(SteelFiber steelFiber);

    /**
     * Partially updates a steelFiber.
     *
     * @param steelFiber the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SteelFiber> partialUpdate(SteelFiber steelFiber);

    /**
     * Get all the steelFibers.
     *
     * @return the list of entities.
     */
    List<SteelFiber> findAll();

    /**
     * Get the "id" steelFiber.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SteelFiber> findOne(Long id);

    /**
     * Delete the "id" steelFiber.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
