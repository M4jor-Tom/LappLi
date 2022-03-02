package com.muller.lappli.service;

import com.muller.lappli.domain.StrandSupply;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link StrandSupply}.
 */
public interface StrandSupplyService extends ReadTriggerableService<StrandSupply> {
    /**
     * Save a strandSupply.
     *
     * @param strandSupply the entity to save.
     * @return the persisted entity.
     */
    StrandSupply save(StrandSupply strandSupply);

    /**
     * Partially updates a strandSupply.
     *
     * @param strandSupply the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StrandSupply> partialUpdate(StrandSupply strandSupply);

    /**
     * Get all the strandSupplies.
     *
     * @return the list of entities.
     */
    List<StrandSupply> findAll();
    /**
     * Get all the StrandSupply where CentralAssembly is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<StrandSupply> findAllWhereCentralAssemblyIsNull();

    /**
     * Get the "id" strandSupply.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StrandSupply> findOne(Long id);

    /**
     * Delete the "id" strandSupply.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
