package com.muller.lappli.service;

import com.muller.lappli.domain.MyNewOperation;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link MyNewOperation}.
 */
public interface MyNewOperationService {
    /**
     * Save a myNewOperation.
     *
     * @param myNewOperation the entity to save.
     * @return the persisted entity.
     */
    MyNewOperation save(MyNewOperation myNewOperation);

    /**
     * Partially updates a myNewOperation.
     *
     * @param myNewOperation the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MyNewOperation> partialUpdate(MyNewOperation myNewOperation);

    /**
     * Get all the myNewOperations.
     *
     * @return the list of entities.
     */
    List<MyNewOperation> findAll();

    /**
     * Get the "id" myNewOperation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MyNewOperation> findOne(Long id);

    /**
     * Delete the "id" myNewOperation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
