package com.muller.lappli.service.abstracts;

import io.jsonwebtoken.lang.UnknownClassException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

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

@Service
public abstract class AbstractSpecificationExecutorService<T> {

    private JpaSpecificationExecutor<T> jpaSpecificationExecutor;
    private CrudRepository<T, Long> crudRepository;

    @SuppressWarnings("unchecked")
    public AbstractSpecificationExecutorService(JpaSpecificationExecutor<T> repository) {
        this.jpaSpecificationExecutor = repository;

        if (!(repository instanceof CrudRepository<?, ?>)) {
            throw new UnknownClassException("repository must as well be a CrudRepository");
        }

        this.crudRepository = (CrudRepository<T, Long>) repository;
    }

    protected abstract T onDomainObjectGetting(T domainObject);

    public List<T> findAll(@Nullable Specification<T> spec) {
        List<T> domainObjectList = jpaSpecificationExecutor.findAll(spec);

        for (T domainObject : domainObjectList) {
            domainObject = onDomainObjectGetting(domainObject);
        }

        return domainObjectList;
    }

    public Page<T> findAll(@Nullable Specification<T> spec, Pageable pageable) {
        Page<T> domainObjectPage = jpaSpecificationExecutor.findAll(spec, pageable);

        for (T domainObject : domainObjectPage) {
            domainObject = onDomainObjectGetting(domainObject);
        }

        return domainObjectPage;
    }

    public List<T> findAll() {
        List<T> domainObjectList = new ArrayList<>();

        for (T domainObject : crudRepository.findAll()) {
            domainObjectList.add(onDomainObjectGetting(domainObject));
        }

        return domainObjectList;
    }

    public Optional<T> findOne(Long id) {
        Optional<T> domainObjectOptional = crudRepository.findById(id);

        if (domainObjectOptional.isPresent()) {
            domainObjectOptional = Optional.of(onDomainObjectGetting(domainObjectOptional.get()));
        }

        return domainObjectOptional;
    }

    public long count(@Nullable Specification<T> spec) {
        return jpaSpecificationExecutor.count(spec);
    }
}
