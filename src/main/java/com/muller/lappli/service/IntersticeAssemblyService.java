package com.muller.lappli.service;

import com.muller.lappli.domain.IntersticeAssembly;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link IntersticeAssembly}.
 */
public interface IntersticeAssemblyService {
    /**
     * Save a intersticeAssembly.
     *
     * @param intersticeAssembly the entity to save.
     * @return the persisted entity.
     */
    IntersticeAssembly save(IntersticeAssembly intersticeAssembly);

    /**
     * Partially updates a intersticeAssembly.
     *
     * @param intersticeAssembly the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IntersticeAssembly> partialUpdate(IntersticeAssembly intersticeAssembly);

    /**
     * Get all the intersticeAssemblies.
     *
     * @return the list of entities.
     */
    List<IntersticeAssembly> findAll();

    /**
     * Get the "id" intersticeAssembly.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IntersticeAssembly> findOne(Long id);

    /**
     * Delete the "id" intersticeAssembly.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
