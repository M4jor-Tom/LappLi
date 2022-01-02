package com.muller.lappli.service;

import com.muller.lappli.domain.StrandSupply;
import com.muller.lappli.domain.exception.AppartionDivisionNonNullRemainderException;
import com.muller.lappli.domain.exception.IllegalStrandSupplyException;
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
     * @throws AppartionDivisionNonNullRemainderException
     * @throws IllegalStrandSupplyException
     */
    StrandSupply save(StrandSupply strandSupply) throws AppartionDivisionNonNullRemainderException, IllegalStrandSupplyException;

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
