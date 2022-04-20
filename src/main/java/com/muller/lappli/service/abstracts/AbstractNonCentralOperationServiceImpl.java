package com.muller.lappli.service.abstracts;

import com.muller.lappli.domain.interfaces.INonCentralOperation;
import com.muller.lappli.service.StrandSupplyService;

public abstract class AbstractNonCentralOperationServiceImpl<T extends INonCentralOperation<?>> {

    private final StrandSupplyService strandSupplyService;

    public AbstractNonCentralOperationServiceImpl(StrandSupplyService strandSupplyService) {
        this.strandSupplyService = strandSupplyService;
    }

    protected void actualizeOwnerStrandSupply(T toInsert) {
        strandSupplyService.actualizeNonCentralOperationsFor(toInsert.getOwnerStrandSupply(), toInsert);
    }
}
