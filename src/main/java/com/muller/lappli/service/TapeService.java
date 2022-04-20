package com.muller.lappli.service;

import com.muller.lappli.domain.Tape;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Tape}.
 */
public interface TapeService {
    /**
     * Save a tape.
     *
     * @param tape the entity to save.
     * @return the persisted entity.
     */
    Tape save(Tape tape);

    /**
     * Partially updates a tape.
     *
     * @param tape the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Tape> partialUpdate(Tape tape);

    /**
     * Get all the tapes.
     *
     * @return the list of entities.
     */
    List<Tape> findAll();

    /**
     * Get the "id" tape.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Tape> findOne(Long id);

    /**
     * Delete the "id" tape.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
