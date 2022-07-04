package com.muller.lappli.domain.interfaces;

import com.muller.lappli.domain.AssemblyPresetDistributionPossibility;
import java.util.List;

/**
 * An interface which must be implemented by
 * classes providing data about possible
 * {@link com.muller.lappli.domain.enumeration.AssemblyPresetDistribution}
 * to make depending on their data
 */
public interface IAssemblyPresetDistributionCalculator {
    public List<AssemblyPresetDistributionPossibility> getAssemblyPresetDistributionPossibilities();

    public AssemblyPresetDistributionPossibility getFaclutativeCentralCompletionComponentAssemblyPresetDistributionPossibilities();

    public AssemblyPresetDistributionPossibility getForcedCentralUtilityComponentAssemblyPresetDistributionPossibilities();
}
