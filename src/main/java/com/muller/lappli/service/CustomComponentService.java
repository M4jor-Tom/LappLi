package com.muller.lappli.service;

import com.muller.lappli.domain.CustomComponent;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CustomComponent}.
 */
public interface CustomComponentService {
    /**
     * Save a customComponent.
     *
     * @param customComponent the entity to save.
     * @return the persisted entity.
     */
    CustomComponent save(CustomComponent customComponent);

    /**
     * Partially updates a customComponent.
     *
     * @param customComponent the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustomComponent> partialUpdate(CustomComponent customComponent);

    /**
     * Get all the customComponents.
     *
     * @return the list of entities.
     */
    List<CustomComponent> findAll();

    /**
     * Get the "id" customComponent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustomComponent> findOne(Long id);

    /**
     * Delete the "id" customComponent.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
