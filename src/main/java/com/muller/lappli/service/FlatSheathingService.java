package com.muller.lappli.service;

import com.muller.lappli.domain.FlatSheathing;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link FlatSheathing}.
 */
public interface FlatSheathingService {
    /**
     * Save a flatSheathing.
     *
     * @param flatSheathing the entity to save.
     * @return the persisted entity.
     */
    FlatSheathing save(FlatSheathing flatSheathing);

    /**
     * Partially updates a flatSheathing.
     *
     * @param flatSheathing the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FlatSheathing> partialUpdate(FlatSheathing flatSheathing);

    /**
     * Get all the flatSheathings.
     *
     * @return the list of entities.
     */
    List<FlatSheathing> findAll();

    /**
     * Get the "id" flatSheathing.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FlatSheathing> findOne(Long id);

    /**
     * Delete the "id" flatSheathing.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
