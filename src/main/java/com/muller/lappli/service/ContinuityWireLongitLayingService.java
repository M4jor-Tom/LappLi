package com.muller.lappli.service;

import com.muller.lappli.domain.ContinuityWireLongitLaying;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ContinuityWireLongitLaying}.
 */
public interface ContinuityWireLongitLayingService extends INonCentralOperationService<ContinuityWireLongitLaying> {
    /**
     * Partially updates a continuityWireLongitLaying.
     *
     * @param continuityWireLongitLaying the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ContinuityWireLongitLaying> partialUpdate(ContinuityWireLongitLaying continuityWireLongitLaying);

    /**
     * Get all the continuityWireLongitLayings.
     *
     * @return the list of entities.
     */
    List<ContinuityWireLongitLaying> findAll();

    /**
     * Get the "id" continuityWireLongitLaying.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ContinuityWireLongitLaying> findOne(Long id);

    /**
     * Delete the "id" continuityWireLongitLaying.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
