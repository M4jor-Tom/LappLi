package com.muller.lappli.domain.interfaces;

import com.muller.lappli.domain.enumeration.AssemblyMean;

public interface MeanedAssemblableOperation<T extends MeanedAssemblableOperation<T>> extends AssemblableOperation<T> {
    /**
     * @return the assembly's mean
     */
    public AssemblyMean getAssemblyMean();
}
