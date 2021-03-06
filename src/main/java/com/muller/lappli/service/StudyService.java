package com.muller.lappli.service;

import com.muller.lappli.domain.Study;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Study}.
 */
public interface StudyService {
    /**
     * Save a study.
     *
     * @param study the entity to save.
     * @param shouldBeAuthored sepcifies if study should be authored on save or not
     * @throws SaveException if shoudlBeAuthored is not satisfied by the study
     * @return the persisted entity.
     */
    Study save(Study study, Boolean shouldBeAuthored) throws SaveException;

    /**
     * Partially updates a study.
     *
     * @param study the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Study> partialUpdate(Study study);

    /**
     * Get all the studies.
     *
     * @return the list of entities.
     */
    List<Study> findAll();

    /**
     * Get the "id" study.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Study> findOne(Long id);

    /**
     * Delete the "id" study.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
