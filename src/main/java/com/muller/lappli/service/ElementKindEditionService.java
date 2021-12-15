package com.muller.lappli.service;

import com.muller.lappli.domain.ElementKindEdition;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ElementKindEdition}.
 */
public interface ElementKindEditionService {
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
