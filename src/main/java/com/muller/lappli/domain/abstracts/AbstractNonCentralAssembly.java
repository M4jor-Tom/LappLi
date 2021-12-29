package com.muller.lappli.domain.abstracts;

import com.muller.lappli.domain.enumeration.AssemblyMean;

public abstract class AbstractNonCentralAssembly<T extends AbstractNonCentralAssembly<T>> extends AbstractAssembly<T> {

    public abstract Double getDiameterAssemblyStep();

    public abstract AssemblyMean getAssemblyMean();
}
