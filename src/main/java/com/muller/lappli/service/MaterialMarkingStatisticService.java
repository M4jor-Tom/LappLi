package com.muller.lappli.service;

import com.muller.lappli.domain.MaterialMarkingStatistic;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link MaterialMarkingStatistic}.
 */
public interface MaterialMarkingStatisticService {
    /**
     * Save a materialMarkingStatistic.
     *
     * @param materialMarkingStatistic the entity to save.
     * @return the persisted entity.
     */
    MaterialMarkingStatistic save(MaterialMarkingStatistic materialMarkingStatistic);

    /**
     * Partially updates a materialMarkingStatistic.
     *
     * @param materialMarkingStatistic the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MaterialMarkingStatistic> partialUpdate(MaterialMarkingStatistic materialMarkingStatistic);

    /**
     * Get all the materialMarkingStatistics.
     *
     * @return the list of entities.
     */
    List<MaterialMarkingStatistic> findAll();

    /**
     * Get the "id" materialMarkingStatistic.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MaterialMarkingStatistic> findOne(Long id);

    /**
     * Delete the "id" materialMarkingStatistic.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
