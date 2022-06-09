package com.muller.lappli.service;

import com.muller.lappli.domain.FlatSheathingSupplyPosition;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link FlatSheathingSupplyPosition}.
 */
public interface FlatSheathingSupplyPositionService {
    /**
     * Save a flatSheathingSupplyPosition.
     *
     * @param flatSheathingSupplyPosition the entity to save.
     * @return the persisted entity.
     */
    FlatSheathingSupplyPosition save(FlatSheathingSupplyPosition flatSheathingSupplyPosition);

    /**
     * Partially updates a flatSheathingSupplyPosition.
     *
     * @param flatSheathingSupplyPosition the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FlatSheathingSupplyPosition> partialUpdate(FlatSheathingSupplyPosition flatSheathingSupplyPosition);

    /**
     * Get all the flatSheathingSupplyPositions.
     *
     * @return the list of entities.
     */
    List<FlatSheathingSupplyPosition> findAll();

    /**
     * Get the "id" flatSheathingSupplyPosition.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FlatSheathingSupplyPosition> findOne(Long id);

    /**
     * Delete the "id" flatSheathingSupplyPosition.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
