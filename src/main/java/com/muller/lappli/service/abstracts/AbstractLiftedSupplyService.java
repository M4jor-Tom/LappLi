package com.muller.lappli.service.abstracts;

import com.muller.lappli.domain.abstracts.AbstractLiftedSupply;
import com.muller.lappli.service.LifterService;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
public abstract class AbstractLiftedSupplyService<T extends AbstractLiftedSupply> extends AbstractSpecificationExecutorService<T> {

    private final LifterService lifterService;

    public AbstractLiftedSupplyService(JpaSpecificationExecutor<T> repository, LifterService lifterService) {
        super(repository);
        this.lifterService = lifterService;
    }

    @Override
    protected T onDomainObjectGetting(T domainObject) {
        //domainObject.setBestLifterList(lifterService.findBestLifterList(domainObject));
        return domainObject;
    }

    protected Optional<T> setBestLifterList(Optional<T> liftedSupply) {
        if (liftedSupply.isPresent()) {
            liftedSupply.get().setBestLifterList(lifterService.findBestLifterList(liftedSupply.get()));
        }

        return liftedSupply;
    }

    protected List<T> setBestLifterLists(List<T> liftedSupplyList) {
        for (T liftedSupply : liftedSupplyList) {
            liftedSupply.setBestLifterList(lifterService.findBestLifterList(liftedSupply));
        }

        return liftedSupplyList;
    }
}
