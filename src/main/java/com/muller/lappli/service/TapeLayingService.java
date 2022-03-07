package com.muller.lappli.service;

import com.muller.lappli.domain.TapeLaying;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TapeLaying}.
 */
public interface TapeLayingService {
    /**
     * Save a tapeLaying.
     *
     * @param tapeLaying the entity to save.
     * @return the persisted entity.
     */
    TapeLaying save(TapeLaying tapeLaying);

    /**
     * Partially updates a tapeLaying.
     *
     * @param tapeLaying the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TapeLaying> partialUpdate(TapeLaying tapeLaying);

    /**
     * Get all the tapeLayings.
     *
     * @return the list of entities.
     */
    List<TapeLaying> findAll();

    /**
     * Get the "id" tapeLaying.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TapeLaying> findOne(Long id);

    /**
     * Delete the "id" tapeLaying.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
