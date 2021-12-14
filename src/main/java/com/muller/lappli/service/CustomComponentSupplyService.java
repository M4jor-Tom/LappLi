package com.muller.lappli.service;

import com.muller.lappli.domain.CustomComponentSupply;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CustomComponentSupply}.
 */
public interface CustomComponentSupplyService {
    /**
     * Save a customComponentSupply.
     *
     * @param customComponentSupply the entity to save.
     * @return the persisted entity.
     */
    CustomComponentSupply save(CustomComponentSupply customComponentSupply);

    /**
     * Partially updates a customComponentSupply.
     *
     * @param customComponentSupply the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustomComponentSupply> partialUpdate(CustomComponentSupply customComponentSupply);

    /**
     * Get all the customComponentSupplies.
     *
     * @return the list of entities.
     */
    List<CustomComponentSupply> findAll();

    /**
     * Get the "id" customComponentSupply.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustomComponentSupply> findOne(Long id);

    /**
     * Delete the "id" customComponentSupply.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
