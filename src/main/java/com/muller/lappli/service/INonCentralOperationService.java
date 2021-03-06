package com.muller.lappli.service;

import com.muller.lappli.domain.interfaces.INonCentralOperation;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link INonCentralOperation}.
 */
public interface INonCentralOperationService<T extends INonCentralOperation<T>> extends FindOneService<T> {
    /**
     * Save a nonCentralOperation.
     *
     * @param nonCentralOperation the entity to save.
     * @param actualizeOwnerStrandSupply true if the ownerStrandSupply is to be acutalized on saving a domain object
     * @param rollbackOperationLayerIfUpdate true if updates on operationLayer must get rolled backed
     * @return the persisted entity.
     */
    T save(T nonCentralOperation, Boolean actualizeOwnerStrandSupply, Boolean rollbackOperationLayerIfUpdate);

    /**
     * Partially updates a nonCentralOperation.
     *
     * @param nonCentralOperation the entity to update partially.
     * @return the persisted entity.
     */
    Optional<T> partialUpdate(T nonCentralOperation);

    /**
     * Get all the nonCentralOperations.
     *
     * @return the list of entities.
     */
    List<T> findAll();

    /**
     * Get the "id" nonCentralOperation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<T> findOne(Long id);

    /**
     * Delete the "id" nonCentralOperation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
