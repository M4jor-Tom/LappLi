package com.muller.lappli.service;

import com.muller.lappli.domain.Position;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Position}.
 */
public interface PositionService {
    /**
     * Save a position.
     *
     * @param position the entity to save.
     * @return the persisted entity.
     */
    Position save(Position position);

    /**
     * Partially updates a position.
     *
     * @param position the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Position> partialUpdate(Position position);

    /**
     * Get all the positions.
     *
     * @return the list of entities.
     */
    List<Position> findAll();

    /**
     * Get the "id" position.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Position> findOne(Long id);

    /**
     * Delete the "id" position.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
