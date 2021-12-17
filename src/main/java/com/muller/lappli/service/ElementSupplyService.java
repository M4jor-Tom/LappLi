package com.muller.lappli.service;

import com.muller.lappli.domain.ElementSupply;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ElementSupply}.
 */
public interface ElementSupplyService extends ReadTriggerableService<ElementSupply> {
    /**
     * Save a elementSupply.
     *
     * @param elementSupply the entity to save.
     * @return the persisted entity.
     */
    ElementSupply save(ElementSupply elementSupply);

    /**
     * Partially updates a elementSupply.
     *
     * @param elementSupply the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ElementSupply> partialUpdate(ElementSupply elementSupply);

    /**
     * Get all the elementSupplies.
     *
     * @return the list of entities.
     */
    List<ElementSupply> findAll();

    /**
     * Get the "id" elementSupply.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ElementSupply> findOne(Long id);

    /**
     * Delete the "id" elementSupply.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
