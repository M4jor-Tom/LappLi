package com.muller.lappli.service;

import com.muller.lappli.domain.ElementKindEdition;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ElementKindEdition}.
 */
public interface ElementKindEditionService {
    /**
     * Finds elementKindEditions for an given elementKind's id
     * checked out at a given editionDateTime
     *
     * @param elementKindId the elementKind's id
     * @param editionDateTime the instant
     * @return the checked out elementKind
     */
    public List<ElementKindEdition> findByEditedElementKindIdAndEditionDateTimeBefore(Long elementKindId, Instant editionDateTime);

    /**
     * Save a elementKindEdition.
     *
     * @param elementKindEdition the entity to save.
     * @return the persisted entity.
     */
    ElementKindEdition save(ElementKindEdition elementKindEdition);

    /**
     * Partially updates a elementKindEdition.
     *
     * @param elementKindEdition the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ElementKindEdition> partialUpdate(ElementKindEdition elementKindEdition);

    /**
     * Get all the elementKindEditions.
     *
     * @return the list of entities.
     */
    List<ElementKindEdition> findAll();

    /**
     * Get the "id" elementKindEdition.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ElementKindEdition> findOne(Long id);

    /**
     * Delete the "id" elementKindEdition.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
