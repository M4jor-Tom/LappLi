package com.muller.lappli.service;

import com.muller.lappli.domain.LifterRunMeasure;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link LifterRunMeasure}.
 */
public interface LifterRunMeasureService {
    /**
     * Save a lifterRunMeasure.
     *
     * @param lifterRunMeasure the entity to save.
     * @return the persisted entity.
     */
    LifterRunMeasure save(LifterRunMeasure lifterRunMeasure);

    /**
     * Partially updates a lifterRunMeasure.
     *
     * @param lifterRunMeasure the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LifterRunMeasure> partialUpdate(LifterRunMeasure lifterRunMeasure);

    /**
     * Get all the lifterRunMeasures.
     *
     * @return the list of entities.
     */
    List<LifterRunMeasure> findAll();

    /**
     * Get the "id" lifterRunMeasure.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LifterRunMeasure> findOne(Long id);

    /**
     * Delete the "id" lifterRunMeasure.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
