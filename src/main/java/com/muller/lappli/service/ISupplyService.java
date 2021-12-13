package com.muller.lappli.service;

import com.muller.lappli.domain.ISupply;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ISupply}.
 */
public interface ISupplyService {
    /**
     * Save a iSupply.
     *
     * @param iSupply the entity to save.
     * @return the persisted entity.
     */
    ISupply save(ISupply iSupply);

    /**
     * Partially updates a iSupply.
     *
     * @param iSupply the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ISupply> partialUpdate(ISupply iSupply);

    /**
     * Get all the iSupplies.
     *
     * @return the list of entities.
     */
    List<ISupply> findAll();

    /**
     * Get the "id" iSupply.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ISupply> findOne(Long id);

    /**
     * Delete the "id" iSupply.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
