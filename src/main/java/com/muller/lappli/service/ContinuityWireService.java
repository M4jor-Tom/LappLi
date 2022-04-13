package com.muller.lappli.service;

import com.muller.lappli.domain.ContinuityWire;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ContinuityWire}.
 */
public interface ContinuityWireService {
    /**
     * Save a continuityWire.
     *
     * @param continuityWire the entity to save.
     * @return the persisted entity.
     */
    ContinuityWire save(ContinuityWire continuityWire);

    /**
     * Partially updates a continuityWire.
     *
     * @param continuityWire the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ContinuityWire> partialUpdate(ContinuityWire continuityWire);

    /**
     * Get all the continuityWires.
     *
     * @return the list of entities.
     */
    List<ContinuityWire> findAll();

    /**
     * Get the "id" continuityWire.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ContinuityWire> findOne(Long id);

    /**
     * Delete the "id" continuityWire.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
