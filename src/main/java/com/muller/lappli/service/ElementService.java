package com.muller.lappli.service;

import com.muller.lappli.domain.Element;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Element}.
 */
public interface ElementService {
    /**
     * Save a element.
     *
     * @param element the entity to save.
     * @return the persisted entity.
     */
    Element save(Element element);

    /**
     * Partially updates a element.
     *
     * @param element the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Element> partialUpdate(Element element);

    /**
     * Get all the elements.
     *
     * @return the list of entities.
     */
    List<Element> findAll();

    /**
     * Get the "id" element.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Element> findOne(Long id);

    /**
     * Delete the "id" element.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
