package com.muller.lappli.service;

import com.muller.lappli.domain.CentralAssembly;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CentralAssembly}.
 */
public interface CentralAssemblyService extends IAssemblyService<CentralAssembly> {
    /**
     * Get all the centralAssemblies.
     *
     * @return the list of entities.
     */
    List<CentralAssembly> findAll();

    /**
     * Get the "id" centralAssembly.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CentralAssembly> findOne(Long id);

    /**
     * Delete the "id" centralAssembly.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
