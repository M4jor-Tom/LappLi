package com.muller.lappli.service;

import com.muller.lappli.domain.MetalFiber;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link MetalFiber}.
 */
public interface MetalFiberService {
    /**
     * Save a metalFiber.
     *
     * @param metalFiber the entity to save.
     * @return the persisted entity.
     */
    MetalFiber save(MetalFiber metalFiber);

    /**
     * Partially updates a metalFiber.
     *
     * @param metalFiber the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MetalFiber> partialUpdate(MetalFiber metalFiber);

    /**
     * Get all the metalFibers.
     *
     * @return the list of entities.
     */
    List<MetalFiber> findAll();

    /**
     * Get the "id" metalFiber.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MetalFiber> findOne(Long id);

    /**
     * Delete the "id" metalFiber.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
