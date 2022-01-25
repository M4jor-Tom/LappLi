package com.muller.lappli.service;

import com.muller.lappli.domain.Strand;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Strand}.
 */
public interface StrandService extends ReadTriggerableService<Strand> {
    /**
     * Save a strand.
     *
     * @param strand the entity to save.
     * @return the persisted entity.
     */
    Strand save(Strand strand);

    /**
     * Partially updates a strand.
     *
     * @param strand the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Strand> partialUpdate(Strand strand);

    /**
     * Get all the strands.
     *
     * @return the list of entities.
     */
    List<Strand> findAll();
    /**
     * Get all the Strand where CentralAssembly is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Strand> findAllWhereCentralAssemblyIsNull();

    /**
     * Get the "id" strand.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Strand> findOne(Long id);

    /**
     * Delete the "id" strand.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
