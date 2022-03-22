package com.muller.lappli.domain.abstracts;

import com.muller.lappli.domain.enumeration.AssemblyMean;
import com.muller.lappli.domain.interfaces.INonCentralOperation;
import com.muller.lappli.domain.interfaces.IOperation;

/**
 * This class represents an assembly, which is a layer of the {@link com.muller.lappli.domain.Strand} in {@link AbstractAssembly#getOwnerStrand()}
 */
public abstract class AbstractNonCentralAssembly<T extends AbstractNonCentralAssembly<T>>
    extends AbstractAssembly<T>
    implements INonCentralOperation<T> {

    public AbstractNonCentralAssembly() {
        super();
    }

    /**
     * @return the assembly step measured in {@link IOperation#getAfterThisMilimeterDiameter}s
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

    public IOperation<T> toOperation() {
        return this;
    }
}
