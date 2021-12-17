package com.muller.lappli.service;

import com.muller.lappli.domain.BangleSupply;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link BangleSupply}.
 */
public interface BangleSupplyService extends ReadTriggerableService<BangleSupply> {
    /**
     * Save a bangleSupply.
     *
     * @param bangleSupply the entity to save.
     * @return the persisted entity.
     */
    BangleSupply save(BangleSupply bangleSupply);

    /**
     * Partially updates a bangleSupply.
     *
     * @param bangleSupply the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BangleSupply> partialUpdate(BangleSupply bangleSupply);

    /**
     * Get all the bangleSupplies.
     *
     * @return the list of entities.
     */
    List<BangleSupply> findAll();

    /**
     * Get the "id" bangleSupply.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BangleSupply> findOne(Long id);

    /**
     * Delete the "id" bangleSupply.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
