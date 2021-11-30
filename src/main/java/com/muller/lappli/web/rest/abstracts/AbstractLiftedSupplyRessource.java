package com.muller.lappli.web.rest.abstracts;

import com.muller.lappli.domain.abstracts.AbstractLiftedSupply;
import com.muller.lappli.service.LifterService;
import java.util.List;
import java.util.Optional;

public abstract class AbstractLiftedSupplyRessource<T extends AbstractLiftedSupply> {

    private final LifterService lifterService;

    public AbstractLiftedSupplyRessource(LifterService lifterService) {
        this.lifterService = lifterService;
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
