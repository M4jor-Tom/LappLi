package com.muller.lappli.service;

import com.muller.lappli.domain.SupplyPosition;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link SupplyPosition}.
 */
public interface SupplyPositionService {
    /**
     * Save a supplyPosition.
     *
     * @param supplyPosition the entity to save.
     * @return the persisted entity.
     */
    SupplyPosition save(SupplyPosition supplyPosition);

    /**
     * Partially updates a supplyPosition.
     *
     * @param supplyPosition the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SupplyPosition> partialUpdate(SupplyPosition supplyPosition);

    /**
     * Get all the supplyPositions.
     *
     * @return the list of entities.
     */
    List<SupplyPosition> findAll();
    /**
     * Get all the SupplyPosition where OwnerCentralAssembly is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<SupplyPosition> findAllWhereOwnerCentralAssemblyIsNull();

    /**
     * Get the "id" supplyPosition.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SupplyPosition> findOne(Long id);

    /**
     * Delete the "id" supplyPosition.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
