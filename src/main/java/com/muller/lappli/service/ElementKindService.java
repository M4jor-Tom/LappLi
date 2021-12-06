package com.muller.lappli.service;

import com.muller.lappli.domain.ElementKind;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ElementKind}.
 */
public interface ElementKindService {
    /**
     * Save a elementKind.
     *
     * @param elementKind the entity to save.
     * @return the persisted entity.
     */
    ElementKind save(ElementKind elementKind);

    /**
     * Partially updates a elementKind.
     *
     * @param elementKind the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ElementKind> partialUpdate(ElementKind elementKind);

    /**
     * Get all the elementKinds.
     *
     * @return the list of entities.
     */
    List<ElementKind> findAll();

    /**
     * Get the "id" elementKind.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ElementKind> findOne(Long id);

    /**
     * Delete the "id" elementKind.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
