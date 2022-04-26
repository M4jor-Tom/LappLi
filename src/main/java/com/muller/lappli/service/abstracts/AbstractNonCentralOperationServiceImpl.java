package com.muller.lappli.service.abstracts;

import com.muller.lappli.domain.interfaces.INonCentralOperation;
import com.muller.lappli.service.FindOneService;
import com.muller.lappli.service.StrandSupplyService;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractNonCentralOperationServiceImpl<T extends INonCentralOperation<T>> implements FindOneService<T> {

    private final StrandSupplyService strandSupplyService;

    private final JpaRepository<T, Long> jpaRepository;

    public AbstractNonCentralOperationServiceImpl(StrandSupplyService strandSupplyService, JpaRepository<T, Long> jpaRepository) {
        this.strandSupplyService = strandSupplyService;
        this.jpaRepository = jpaRepository;
    }

    protected abstract Logger getLogger();

    protected abstract String getDomainClassName();

    protected Optional<Long> getOriginalOperationLayerIfNotNew(INonCentralOperation<?> nonCentralOperation) {
        AtomicLong nonCentralOperationOriginalOperationLayer = new AtomicLong();

        if (nonCentralOperation.getId() == null) {
            return Optional.empty();
        }

        findOne(nonCentralOperation.getId())
            .ifPresent(
                new Consumer<INonCentralOperation<?>>() {
                    @Override
                    public void accept(INonCentralOperation<?> t) {
                        nonCentralOperationOriginalOperationLayer.set(t.getOperationLayer());
                    }
                }
            );

        return Optional.of(nonCentralOperationOriginalOperationLayer.get());
    }

    protected Boolean operationLayerIsUnchanged(INonCentralOperation<?> nonCentralOperation) {
        Optional<Long> originalOperationLayerIfNotNew = getOriginalOperationLayerIfNotNew(nonCentralOperation);

        if (originalOperationLayerIfNotNew.isEmpty()) {
            return true;
        }

        return originalOperationLayerIfNotNew.get() == nonCentralOperation.getOperationLayer();
    }

    protected void actualizeOwnerStrandSupply(T toInsert) {
        strandSupplyService.actualizeNonCentralOperationsFor(toInsert.getOwnerStrandSupply(), toInsert);
    }

    protected void rollbackOperationLayerIfUpdate(T domainObject) {
        Optional<Long> originalOperationLayerIfNotNew = getOriginalOperationLayerIfNotNew(domainObject);

        originalOperationLayerIfNotNew.ifPresent(
            new Consumer<Long>() {
                @Override
                public void accept(Long t) {
                    domainObject.setOperationLayer(t);
                }
            }
        );
    }

    public T save(T domainObject, Boolean actualizeOwnerStrandSupply, Boolean rollbackOperationLayerIfUpdate) {
        if (rollbackOperationLayerIfUpdate) {
            rollbackOperationLayerIfUpdate(domainObject);
        }

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
