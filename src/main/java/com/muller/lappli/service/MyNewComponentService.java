package com.muller.lappli.service;

import com.muller.lappli.domain.MyNewComponent;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link MyNewComponent}.
 */
public interface MyNewComponentService {
    /**
     * Save a myNewComponent.
     *
     * @param myNewComponent the entity to save.
     * @return the persisted entity.
     */
    MyNewComponent save(MyNewComponent myNewComponent);

    /**
     * Partially updates a myNewComponent.
     *
     * @param myNewComponent the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MyNewComponent> partialUpdate(MyNewComponent myNewComponent);

    /**
     * Get all the myNewComponents.
     *
     * @return the list of entities.
     */
    List<MyNewComponent> findAll();

    /**
     * Get the "id" myNewComponent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MyNewComponent> findOne(Long id);

    /**
     * Delete the "id" myNewComponent.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
