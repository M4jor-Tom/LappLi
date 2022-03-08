package com.muller.lappli.domain.abstracts;

import com.muller.lappli.domain.enumeration.AssemblyMean;

/**
 * This class represents an assembly, which is a layer of the {@link com.muller.lappli.domain.Strand} in {@link AbstractAssembly#getOwnerStrand()}
 */
public abstract class AbstractNonCentralAssembly<T extends AbstractNonCentralAssembly<T>> extends AbstractAssembly<T> {

    public AbstractNonCentralAssembly() {}

    /**
     * @return the assembly step measured in {@link AbstractOperation#getAfterThisMilimeterDiameter}s
     */
    public abstract Double getDiameterAssemblyStep();

    /**
     * @return the assembly's mean
     */
    public abstract AssemblyMean getAssemblyMean();

    @Override
    public String getProductDesignation() {
        return "";
    }
}
