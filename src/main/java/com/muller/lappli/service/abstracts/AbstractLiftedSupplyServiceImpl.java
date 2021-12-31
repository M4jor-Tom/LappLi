package com.muller.lappli.service.abstracts;

import com.muller.lappli.domain.abstracts.AbstractLiftedSupply;
import com.muller.lappli.service.LifterService;

public abstract class AbstractLiftedSupplyServiceImpl<T extends AbstractLiftedSupply<T>> {

    LifterService lifterService;

    public AbstractLiftedSupplyServiceImpl(LifterService lifterService) {
        this.lifterService = lifterService;
    }

    public T onRead(T domainObject) {
        domainObject.setBestLifterList(lifterService.findBestLifterList(domainObject));

        return domainObject;
    }
}
