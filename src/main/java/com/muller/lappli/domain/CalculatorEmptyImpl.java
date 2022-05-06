package com.muller.lappli.domain;

import com.muller.lappli.domain.enumeration.AssemblyPresetDistribution;
import com.muller.lappli.domain.interfaces.IAssemblyPresetDistributionCalculator;
import com.muller.lappli.domain.interfaces.ICalculator;
import java.util.ArrayList;
import java.util.List;

public class CalculatorEmptyImpl implements ICalculator {

    public CalculatorEmptyImpl() {}

    //  ASSEMBLY PART

    @Override
    public Double getSuppliedComponentsAverageDiameterCentralVoidDiameter(Long componentsCount, Double diameterAssemblyStep) {
        return Double.NaN;
    }

    @Override
    public Long getSuppliesCountAtAssembly(StrandSupply strandSupply, Long assemblyIndex) {
        return DomainManager.ERROR_LONG_POSITIVE_VALUE;
    }

    @Override
    public List<AssemblyPresetDistributionPossibility> getAssemblyPresetDistributionPossibilitiesForAssemblyPresetDistribution(
        AssemblyPresetDistribution assemblyPresetDistribution
    ) {
        return new ArrayList<AssemblyPresetDistributionPossibility>();
    }

    @Override
    public AssemblyPresetDistributionPossibility getAssemblyPresetDistributionPossibility(StrandSupply strandSupply) {
        return AssemblyPresetDistributionPossibility.forError();
    }

    @Override
    public IAssemblyPresetDistributionCalculator getCorrespondingAssemblyPresetDistributionCalculator(
        AssemblyPresetDistribution assemblyPresetDistribution
    ) {
        return new IAssemblyPresetDistributionCalculator() {
            @Override
            public List<AssemblyPresetDistributionPossibility> getAssemblyPresetDistributionPossibilities() {
                return new ArrayList<AssemblyPresetDistributionPossibility>();
            }

            @Override
            public AssemblyPresetDistributionPossibility getFaclutativeCentralCompletionComponentAssemblyPresetDistributionPossibilities() {
                return AssemblyPresetDistributionPossibility.forError();
            }

            @Override
            public AssemblyPresetDistributionPossibility getForcedCentralUtilityComponentAssemblyPresetDistributionPossibilities() {
                return AssemblyPresetDistributionPossibility.forError();
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

    @Override
    public Long getAssemblyPresetDistributionCalculatorCount() {
        return Long.valueOf(DomainManager.ERROR_LONG_POSITIVE_VALUE);
    }

    @Override
    public Double getSuppliedComponentsAverageDiameterAssemblyVoid(StrandSupply strandSupply, Long assemblyIndex) {
        return Double.NaN;
    }

    @Override
    public Double suggestSuppliedComponentsCount(Double centralDiameterInRoundComponentsDiameter, Double diameterAssemblyStep) {
        return Double.NaN;
    }

    @Override
    public Double suggestSuppliedComponentsCountWithMilimeterDiameters(
        Double centralMilimeterDiameter,
        Double roundComponentsAverageDiameter,
        Double diameterAssemblyStep
    ) {
        return Double.NaN;
    }
}
