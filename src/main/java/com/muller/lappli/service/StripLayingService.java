package com.muller.lappli.service;

import com.muller.lappli.domain.StripLaying;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link StripLaying}.
 */
public interface StripLayingService {
    /**
     * Save a stripLaying.
     *
     * @param stripLaying the entity to save.
     * @return the persisted entity.
     */
    StripLaying save(StripLaying stripLaying);

    /**
     * Partially updates a stripLaying.
     *
     * @param stripLaying the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StripLaying> partialUpdate(StripLaying stripLaying);

    /**
     * Get all the stripLayings.
     *
     * @return the list of entities.
     */
    List<StripLaying> findAll();

    /**
     * Get the "id" stripLaying.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StripLaying> findOne(Long id);

    /**
     * Delete the "id" stripLaying.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
