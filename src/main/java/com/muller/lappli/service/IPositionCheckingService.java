package com.muller.lappli.service;

import com.muller.lappli.domain.abstracts.AbstractAssembly;
import com.muller.lappli.domain.exception.PositionHasSeveralSupplyException;
import com.muller.lappli.domain.exception.PositionInSeveralAssemblyException;
import java.util.Optional;

public interface IPositionCheckingService<T> {
    /**
     * Save a assembly.
     *
     * @param assembly the entity to save.
     * @return the persisted entity.
     */
    T save(T assembly) throws PositionHasSeveralSupplyException, PositionInSeveralAssemblyException;

    /**
     * Partially updates a assembly.
     *
     * @param assembly the entity to update partially.
     * @return the persisted entity.
     */
    Optional<T> partialUpdate(T assembly) throws PositionHasSeveralSupplyException, PositionInSeveralAssemblyException;
}
