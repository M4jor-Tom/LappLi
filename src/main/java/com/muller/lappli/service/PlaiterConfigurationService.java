package com.muller.lappli.service;

import com.muller.lappli.domain.PlaiterConfiguration;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PlaiterConfiguration}.
 */
public interface PlaiterConfigurationService {
    /**
     * Save a plaiterConfiguration.
     *
     * @param plaiterConfiguration the entity to save.
     * @return the persisted entity.
     */
    PlaiterConfiguration save(PlaiterConfiguration plaiterConfiguration);

    /**
     * Partially updates a plaiterConfiguration.
     *
     * @param plaiterConfiguration the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PlaiterConfiguration> partialUpdate(PlaiterConfiguration plaiterConfiguration);

    /**
     * Get all the plaiterConfigurations.
     *
     * @return the list of entities.
     */
    List<PlaiterConfiguration> findAll();

    /**
     * Get the "id" plaiterConfiguration.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PlaiterConfiguration> findOne(Long id);

    /**
     * Delete the "id" plaiterConfiguration.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
