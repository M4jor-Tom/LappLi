package com.muller.lappli.service;

import com.muller.lappli.domain.interfaces.IDomainObject;
import java.util.Optional;

@FunctionalInterface
public interface FindOneService<T extends IDomainObject<T>> {
    /**
     * Get the "id" domain object.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<T> findOne(Long id);
}
