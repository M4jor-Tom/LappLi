package com.muller.lappli.service.abstracts;

import io.jsonwebtoken.lang.UnknownClassException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
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
        setRepository(repository);
    }

    protected abstract T onDomainObjectGetting(T domainObject);

    public final List<T> findAll(@Nullable Specification<T> spec) {
        List<T> domainObjectList = repository.findAll(spec);

        for (T domainObject : domainObjectList) {
            domainObject = onDomainObjectGetting(domainObject);
        }

        return domainObjectList;
    }

    public final Page<T> findAll(@Nullable Specification<T> spec, Pageable pageable) {
        Page<T> domainObjectPage = repository.findAll(spec, pageable);

        for (T domainObject : domainObjectPage) {
            domainObject = onDomainObjectGetting(domainObject);
        }

        return domainObjectPage;
    }

    public final List<T> findAll() {
        if (!repositoryTypeIsOkay(this.repository)) {
            return null;
        }

        List<T> domainObjectList = new ArrayList<>();

        for (T domainObject : getRepositoryAsCrud().findAll()) {
            domainObjectList.add(onDomainObjectGetting(domainObject));
        }

        return domainObjectList;
    }

    public final Optional<T> findOne(Long id) {
        if (!repositoryTypeIsOkay(this.repository)) {
            return null;
        }

        Optional<T> domainObjectOptional = getRepositoryAsCrud().findById(id);

        if (domainObjectOptional.isPresent()) {
            domainObjectOptional = Optional.of(onDomainObjectGetting(domainObjectOptional.get()));
        }

        return domainObjectOptional;
    }

    public final long count(@Nullable Specification<T> spec) {
        return repository.count(spec);
    }

    private final Boolean repositoryTypeIsOkay(JpaSpecificationExecutor<T> repository) {
        return repository instanceof CrudRepository<?, ?>;
    }

    @SuppressWarnings("unchecked")
    public final CrudRepository<T, Long> getRepositoryAsCrud() {
        if (repositoryTypeIsOkay(repository)) {
            return (CrudRepository<T, Long>) repository;
        }
        return null;
    }

    public final void setRepository(JpaSpecificationExecutor<T> repository) {
        if (!repositoryTypeIsOkay(repository)) {
            (new UnknownClassException("repository must be JpaSpecificationExecutor as well as CrudRepository")).printStackTrace();
        }
        this.repository = repository;
    }
}
