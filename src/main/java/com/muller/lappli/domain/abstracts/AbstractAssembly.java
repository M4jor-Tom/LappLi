package com.muller.lappli.domain.abstracts;

import javax.persistence.MappedSuperclass;

/**
 * This class represents AbstractOperations which are Assemblies
 */
@MappedSuperclass
public abstract class AbstractAssembly<T extends AbstractAssembly<T>> extends AbstractOperation<T> {

    public abstract Long getAssemblyLayer();

    @Override
    public Double getMilimeterDiameterIncidency() {
        try {
            return getOwnerStrand().getSuppliedComponentsAverageMilimeterDiameter();
        } catch (NullPointerException e) {
            return Double.NaN;
        }
    }

    @Override
    public Long getOperationLayer() {
        return Long.valueOf(0);
    }
}
