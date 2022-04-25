package com.muller.lappli.service;

import com.muller.lappli.domain.abstracts.AbstractDomainObject;
import java.util.List;
import java.util.Optional;

public interface IService<T extends AbstractDomainObject<T>> {
    /**
     * Save a domain object.
     *
     * @param domain object the entity to save.
     * @return the persisted entity.
     */
    public T save(T domainObject);

    /**
     * Partially updates a domain object.
     *
     * @param domain object the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<T> partialUpdate(T domainObject);

    /**
     * Get all the domain objects.
     *
     * @return the list of entities.
     */
    public List<T> findAll();

    /**
     * Get the "id" domain object.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<T> findOne(Long id);

    /**
     * Delete the "id" domain object.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id);
}
