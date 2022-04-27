package com.muller.lappli.service;

import com.muller.lappli.domain.Plait;
import com.muller.lappli.domain.exception.RatioBoundExceedingException;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Plait}.
 */
public interface PlaitService extends INonCentralOperationService<Plait> {
    /**
     * Partially updates a plait.
     *
     * @param plait the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Plait> partialUpdate(Plait plait) throws RatioBoundExceedingException;

    /**
     * Get all the plaits.
     *
     * @return the list of entities.
     */
    List<Plait> findAll();

    /**
     * Get the "id" plait.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Plait> findOne(Long id);

    /**
     * Delete the "id" plait.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
