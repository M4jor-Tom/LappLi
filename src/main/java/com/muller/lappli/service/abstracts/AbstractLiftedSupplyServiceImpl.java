package com.muller.lappli.service.abstracts;

import com.muller.lappli.domain.abstracts.AbstractLiftedSupply;
import com.muller.lappli.service.LifterService;
import com.muller.lappli.service.ReadTriggerableService;

public abstract class AbstractLiftedSupplyServiceImpl<T extends AbstractLiftedSupply> { // implements ReadTriggerableService<T> {

    LifterService lifterService;

    public AbstractLiftedSupplyServiceImpl(LifterService lifterService) {
        this.lifterService = lifterService;
    }

    //@Override
    public T onRead(T domainObject) {
        domainObject.setBestLifterList(lifterService.findBestLifterList(domainObject));

        return domainObject;
    }
}
