package com.muller.lappli.domain.interfaces;

public interface AssemblableOperation<T extends AssemblableOperation<T>> extends IOperation<T> {
    /**
     * @return the assembly step measured in {@link IOperation#getAfterThisMilimeterDiameter}s
     */
    public Double getDiameterAssemblyStep();
}
