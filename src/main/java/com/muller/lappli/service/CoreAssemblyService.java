package com.muller.lappli.service;

import com.muller.lappli.domain.CoreAssembly;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CoreAssembly}.
 */
public interface CoreAssemblyService extends IAssemblyService<CoreAssembly> {
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
