package com.muller.lappli.service;

import com.muller.lappli.domain.Strand;
import com.muller.lappli.service.interfaces.ISpecificationExecutorService;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Strand}.
 */
public interface StrandService extends ISpecificationExecutorService<Strand> {
    /**
     * Save a strand.
     *
     * @param strand the entity to save.
     * @return the persisted entity.
     */
    Strand save(Strand strand);

    /**
     * Partially updates a strand.
     *
     * @param strand the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Strand> partialUpdate(Strand strand);

    /**
     * Delete the "id" strand.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
