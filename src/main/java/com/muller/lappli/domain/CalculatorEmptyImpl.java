package com.muller.lappli.domain;

import com.muller.lappli.domain.enumeration.AssemblyPresetDistribution;
import com.muller.lappli.domain.interfaces.IAssemblyPresetDistributionCalculator;
import com.muller.lappli.domain.interfaces.ICalculator;
import java.util.ArrayList;
import java.util.List;

public class CalculatorEmptyImpl implements ICalculator {

    public CalculatorEmptyImpl() {}

    @Override
    public Double getSuppliedComponentsAverageDiameterCentralVoidDiameter(Long componentsCount, Double diameterAssemblyStep) {
        return Double.NaN;
    }

    @Override
    public Boolean assemblyPresetDistributionMustHaveCentralComponent(AssemblyPresetDistribution assemblyPresetDistribution) {
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

    @Override
    public List<AssemblyPresetDistributionPossibility> getAssemblyPresetDistributionPossibilitiesForAssemblyPresetDistribution(
        AssemblyPresetDistribution assemblyPresetDistribution
    ) {
        return new ArrayList<AssemblyPresetDistributionPossibility>();
    }

    @Override
    public IAssemblyPresetDistributionCalculator getCorrespondingAssemblyPresetDistributionCalculator(
        AssemblyPresetDistribution assemblyPresetDistribution
    ) {
        return new IAssemblyPresetDistributionCalculator() {
            @Override
            public List<AssemblyPresetDistributionPossibility> getAssemblyPresetDistributionPossibilities() {
                return new ArrayList<>();
            }
        };
    }

    @Override
    public Boolean isTargetCalculatorInstance() {
        return false;
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
