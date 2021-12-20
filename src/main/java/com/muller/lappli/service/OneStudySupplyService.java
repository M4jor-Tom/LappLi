package com.muller.lappli.service;

import com.muller.lappli.domain.OneStudySupply;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link OneStudySupply}.
 */
public interface OneStudySupplyService {
    /**
     * Save a oneStudySupply.
     *
     * @param oneStudySupply the entity to save.
     * @return the persisted entity.
     */
    OneStudySupply save(OneStudySupply oneStudySupply);

    /**
     * Partially updates a oneStudySupply.
     *
     * @param oneStudySupply the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OneStudySupply> partialUpdate(OneStudySupply oneStudySupply);

    /**
     * Get all the oneStudySupplies.
     *
     * @return the list of entities.
     */
    List<OneStudySupply> findAll();

    /**
     * Get the "id" oneStudySupply.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OneStudySupply> findOne(Long id);

    /**
     * Delete the "id" oneStudySupply.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
