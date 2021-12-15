package com.muller.lappli.service.interfaces;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

/**
 * Must be implemented on extensions of AbstractSpecificationExecutorService
 * @see com.muller.lappli.service.abstracts.AbstractSpecificationExecutorService
 *
 * @author Tom VAUTRAY (https://github.com/M4jor-Tom)
 */
public interface ISpecificationExecutorService<T> {
    /**
     * Normaly a repository's method.
     * <p>
     * Implemented to use services at most places.
     * <p>
     * Returns all entities matching the given {@link Specification}.
     *
     * @param spec can be {@literal null}.
     * @return never {@literal null}.
     */
    public List<T> findAll(@Nullable Specification<T> spec);

    /**
     * Normaly a repository's method.
     * <p>
     * Implemented to use services at most places.
     * <p>
     * Returns a {@link Page} of entities matching the given {@link Specification}.
     *
     * @param spec can be {@literal null}.
     * @param pageable must not be {@literal null}.
     * @return never {@literal null}.
     */
    public Page<T> findAll(@Nullable Specification<T> spec, Pageable pageable);

    /**
     * Normaly a repository's method.
     * <p>
     * Implemented to use services at most places.
     * <p>
     * Get all the domainObjects.
     *
     * @return the list of entities.
     */
    public List<T> findAll();

    /**
     * Normaly a repository's method.
     * <p>
     * Implemented to use services at most places.
     * <p>
     * Get the "id" domainObject.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<T> findOne(Long id);

    /**
     * Normaly a repository's method.
     * <p>
     * Implemented to use services at most places.
     * <p>
     * Returns the number of instances that the given {@link Specification} will return.
     *
     * @param spec the {@link Specification} to count instances for. Can be {@literal null}.
     * @return the number of instances.
     */
    public long count(@Nullable Specification<T> spec);
}
