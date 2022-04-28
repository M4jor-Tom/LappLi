package com.muller.lappli.service;

import com.muller.lappli.domain.CarrierPlait;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CarrierPlait}.
 */
public interface CarrierPlaitService extends INonCentralOperationService<CarrierPlait> {
    /**
     * Partially updates a carrierPlait.
     *
     * @param carrierPlait the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CarrierPlait> partialUpdate(CarrierPlait carrierPlait);

    /**
     * Get all the carrierPlaits.
     *
     * @return the list of entities.
     */
    List<CarrierPlait> findAll();

    /**
     * Get the "id" carrierPlait.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CarrierPlait> findOne(Long id);

    /**
     * Delete the "id" carrierPlait.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
