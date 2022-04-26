package com.muller.lappli.service.abstracts;

import com.muller.lappli.domain.interfaces.INonCentralOperation;
import com.muller.lappli.service.FindOneService;
import com.muller.lappli.service.StrandSupplyService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public abstract class AbstractNonCentralOperationServiceImpl<T extends INonCentralOperation<T>> implements FindOneService<T> {

    private final StrandSupplyService strandSupplyService;

    private final JpaRepository<T, Long> jpaRepository;

    protected Boolean operationLayerIsUnchanged(INonCentralOperation<?> nonCentralOperation) {
        AtomicLong nonCentralOperationOriginalOperationLayer = new AtomicLong();

        findOne(nonCentralOperation.getId())
            .ifPresent(
                new Consumer<T>() {
                    @Override
                    public void accept(T t) {
                        nonCentralOperationOriginalOperationLayer.set(t.getOperationLayer());
                    }
                }
            );

        return nonCentralOperationOriginalOperationLayer.get() == nonCentralOperation.getOperationLayer();
    }

    public AbstractNonCentralOperationServiceImpl(StrandSupplyService strandSupplyService, JpaRepository<T, Long> jpaRepository) {
        this.strandSupplyService = strandSupplyService;
        this.jpaRepository = jpaRepository;
    }

    protected abstract Logger getLogger();

    protected abstract String getDomainClassName();

    protected void actualizeOwnerStrandSupply(T toInsert) {
        strandSupplyService.actualizeNonCentralOperationsFor(toInsert.getOwnerStrandSupply(), toInsert);
    }

    public T save(T domainObject, Boolean actualizeOwnerStrandSupply) {
        getLogger().debug("Request to save " + getDomainClassName() + " : {}", domainObject);

        if (actualizeOwnerStrandSupply) {
            actualizeOwnerStrandSupply(domainObject);
        }

        return getJpaRepository().save(domainObject);
    }

    @Transactional(readOnly = true)
    public List<T> findAll() {
        getLogger().debug("Request to get all" + getDomainClassName());
        return getJpaRepository().findAll();
    }

    @Transactional(readOnly = true)
    public Optional<T> findOne(Long id) {
        getLogger().debug("Request to get " + getDomainClassName() + " : {}", id);
        return getJpaRepository().findById(id);
    }

    public void delete(Long id) {
        getLogger().debug("Request to delete " + getDomainClassName() + " : {}", id);
        getJpaRepository().deleteById(id);
    }

    protected JpaRepository<T, Long> getJpaRepository() {
        return jpaRepository;
    }
}
