package com.muller.lappli.service.abstracts;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;

/**
 * There is no need of caring about this class if:
 * <ul>
 * <li>You don't use filtering into your jdl file</li>
 * <li>You don't want to eagerly fetch domain objects lists into domain objects</li>
 * </ul>
 * <p>
 * <p>
 * This class has been created to allow the use of service
 * <p>
 * classes into resource web controller classes instead of
 * <p>
 * repository interfaces in the context of criteria filtered getting.
 * <p>
 * Such a thing allows changings in service classes such as list eager fetching
 * <p>
 * to be taken account into resource web controller classes.
 * <p>
 * <p>
 * Then, if you:
 * <ul>
 * <li>Make a JHipser serviced class</li>
 * <li>Make the service class (or impl) an extension of this class</li>
 * <li>Feed super constructor with service's repository, extension of {@Link JpaSpecificationExecutor}</li>
 * <li>Make sure to use Service class into Resource web controller classes</li>
 *  instead of repository interfaces directly,
 * </ul>
 * <p>
 * You can then be sure that any modification made into the process
 * of data accessing through your services will be dealing okay with criterias
 *
 * @author Tom VAUTRAY (https://github.com/M4jor-Tom)
 */
public abstract class AbstractSpecificationExecutorService<T> {

    private JpaSpecificationExecutor<T> repository;

    public AbstractSpecificationExecutorService(JpaSpecificationExecutor<T> repository) {
        this.repository = repository;
    }

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
    public List<T> findAll(@Nullable Specification<T> spec) {
        return repository.findAll(spec);
    }

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
    public Page<T> findAll(@Nullable Specification<T> spec, Pageable pageable) {
        return repository.findAll(spec, pageable);
    }

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
    public long count(@Nullable Specification<T> spec) {
        return repository.count(spec);
    }
}
