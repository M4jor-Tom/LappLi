package com.muller.lappli.service;

import com.muller.lappli.domain.MyNewComponentSupply;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link MyNewComponentSupply}.
 */
public interface MyNewComponentSupplyService {
    /**
     * Save a myNewComponentSupply.
     *
     * @param myNewComponentSupply the entity to save.
     * @return the persisted entity.
     */
    MyNewComponentSupply save(MyNewComponentSupply myNewComponentSupply);

    /**
     * Partially updates a myNewComponentSupply.
     *
     * @param myNewComponentSupply the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MyNewComponentSupply> partialUpdate(MyNewComponentSupply myNewComponentSupply);

    /**
     * Get all the myNewComponentSupplies.
     *
     * @return the list of entities.
     */
    List<MyNewComponentSupply> findAll();

    /**
     * Get the "id" myNewComponentSupply.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MyNewComponentSupply> findOne(Long id);

    /**
     * Delete the "id" myNewComponentSupply.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
