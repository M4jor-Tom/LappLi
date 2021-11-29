package com.muller.lappli.service;

import com.muller.lappli.domain.ElementSupply;
import com.muller.lappli.domain.Lifter;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Lifter}.
 */
public interface LifterService {
    /**
     * Get eligible lifters for @param elementSupply
     *
     * @return the list of eligible lifters
     */
    List<Lifter> findEligibleLifterList(ElementSupply elementSupply);

    /**
     * Save a lifter.
     *
     * @param lifter the entity to save.
     * @return the persisted entity.
     */
    Lifter save(Lifter lifter);

    /**
     * Partially updates a lifter.
     *
     * @param lifter the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Lifter> partialUpdate(Lifter lifter);

    /**
     * Get all the lifters.
     *
     * @return the list of entities.
     */
    List<Lifter> findAll();

    /**
     * Get the "id" lifter.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Lifter> findOne(Long id);

    /**
     * Delete the "id" lifter.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
