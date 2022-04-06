package com.muller.lappli.domain.abstracts;

import com.muller.lappli.domain.interfaces.INonCentralOperation;
import com.muller.lappli.domain.interfaces.IOperation;
import com.muller.lappli.domain.interfaces.MeanedAssemblableOperation;

/**
 * This class represents an assembly, which is a layer of the {@link com.muller.lappli.domain.Strand} in {@link AbstractAssembly#getOwnerStrand()}
 */
public abstract class AbstractNonCentralAssembly<T extends AbstractNonCentralAssembly<T>>
    extends AbstractAssembly<T>
    implements INonCentralOperation<T>, MeanedAssemblableOperation<T> {

    public AbstractNonCentralAssembly() {
        super();
    }

    @Override
    public String getProductDesignation() {
        return "";
    }

    public IOperation<T> toOperation() {
        return this;
    }
}
