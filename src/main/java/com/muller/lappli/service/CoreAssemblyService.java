package com.muller.lappli.service;

import com.muller.lappli.domain.CoreAssembly;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CoreAssembly}.
 */
public interface CoreAssemblyService extends IAssemblyService<CoreAssembly> {
    /**
     * Save a coreAssembly.
     *
     * @param coreAssembly the entity to save.
     * @return the persisted entity.
     */
    CoreAssembly save(CoreAssembly coreAssembly);

    /**
     * Partially updates a coreAssembly.
     *
     * @param coreAssembly the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CoreAssembly> partialUpdate(CoreAssembly coreAssembly);

    /**
     * Get all the coreAssemblies.
     *
     * @return the list of entities.
     */
    List<CoreAssembly> findAll();

    /**
     * Get the "id" coreAssembly.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CoreAssembly> findOne(Long id);

    /**
     * Delete the "id" coreAssembly.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
