package com.muller.lappli.service;

import com.muller.lappli.domain.Lifter;
import com.muller.lappli.domain.abstracts.AbstractLiftedSupply;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Lifter}.
 */
public interface LifterService {
    /**
     * Get bests lifters
     * @param abstractLiftedSupply the targeted supply
     *
     * @return the list of bests lifters
     */
    List<Lifter> findBestLifterList(AbstractLiftedSupply abstractLiftedSupply);

    /**
     * Get eligibles lifters
     * @param abstractLiftedSupply the targeted supply
     *
     * @return the list of eligibles lifters
     */
    List<Lifter> findEligibleLifterList(AbstractLiftedSupply abstractLiftedSupply);

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
