package com.muller.lappli.service;

import com.muller.lappli.domain.TapeKind;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TapeKind}.
 */
public interface TapeKindService {
    /**
     * Save a tapeKind.
     *
     * @param tapeKind the entity to save.
     * @return the persisted entity.
     */
    TapeKind save(TapeKind tapeKind);

    /**
     * Partially updates a tapeKind.
     *
     * @param tapeKind the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TapeKind> partialUpdate(TapeKind tapeKind);

    /**
     * Get all the tapeKinds.
     *
     * @return the list of entities.
     */
    List<TapeKind> findAll();

    /**
     * Get the "id" tapeKind.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TapeKind> findOne(Long id);

    /**
     * Delete the "id" tapeKind.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
