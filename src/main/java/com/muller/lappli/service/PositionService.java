package com.muller.lappli.service;

import com.muller.lappli.domain.Position;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Position}.
 */
public interface PositionService extends IPositionCheckingService<Position> {
    /**
     * Get all the positions.
     *
     * @return the list of entities.
     */
    List<Position> findAll();
    /**
     * Get all the Position where OwnerCentralAssembly is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Position> findAllWhereOwnerCentralAssemblyIsNull();

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
