package com.muller.lappli.domain.interfaces;

import com.muller.lappli.domain.DomainManager;

/**
 * An {@link com.muller.lappli.domain.interfaces.IOperation} which has
 * an assembly step
 */
public interface AssemblableOperation<T extends AssemblableOperation<T>> extends IOperation<T> {
    /**
     * @return the assembly step measured in {@link IOperation#getAfterThisMilimeterDiameter}s
     */
    public Double getDiameterAssemblyStep();

    public default String getMullerStandardizedFormatDiameterAssemblyStep() {
        return DomainManager.mullerStandardizedFormat(getDiameterAssemblyStep());
    }
}
