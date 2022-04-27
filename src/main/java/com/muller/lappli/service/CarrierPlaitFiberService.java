package com.muller.lappli.service;

import com.muller.lappli.domain.CarrierPlaitFiber;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CarrierPlaitFiber}.
 */
public interface CarrierPlaitFiberService {
    /**
     * Save a carrierPlaitFiber.
     *
     * @param carrierPlaitFiber the entity to save.
     * @return the persisted entity.
     */
    CarrierPlaitFiber save(CarrierPlaitFiber carrierPlaitFiber);

    /**
     * Partially updates a carrierPlaitFiber.
     *
     * @param carrierPlaitFiber the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CarrierPlaitFiber> partialUpdate(CarrierPlaitFiber carrierPlaitFiber);

    /**
     * Get all the carrierPlaitFibers.
     *
     * @return the list of entities.
     */
    List<CarrierPlaitFiber> findAll();

    /**
     * Get the "id" carrierPlaitFiber.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CarrierPlaitFiber> findOne(Long id);

    /**
     * Delete the "id" carrierPlaitFiber.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
