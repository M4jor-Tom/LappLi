package com.muller.lappli.domain;

import com.muller.lappli.domain.enumeration.AssemblyDistributionPreset;
import com.muller.lappli.domain.interfaces.ICalculator;

public class CalculatorEmptyImpl implements ICalculator {

    public CalculatorEmptyImpl() {}

    @Override
    public Double getSuppliedComponentsAverageDiameterCentralVoidDiameter(Long componentsCount, Double diameterAssemblyStep) {
        return Double.NaN;
    }

    @Override
    public Boolean assemblyDistributionPresetMustHaveCentralComponent(AssemblyDistributionPreset assemblyDistributionPreset) {
        return false;
    }

    @Override
    public Long getSuppliesCountAtAssembly(
        Strand strandIfNoSuppliesCountProvided,
        Long suppliesCountIfNoStrandProvided,
        Long assemblyIndex
    ) throws IllegalArgumentException {
        return DomainManager.ERROR_LONG_POSITIVE_VALUE;
    }
}
