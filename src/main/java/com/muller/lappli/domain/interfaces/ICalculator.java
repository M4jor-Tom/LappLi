package com.muller.lappli.domain.interfaces;

import com.muller.lappli.domain.*;
import com.muller.lappli.domain.enumeration.AssemblyPresetDistribution;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface ICalculator {
    public static ICalculator getNewInstance() {
        try {
            return (ICalculator) Class.forName(DomainManager.TARGET_CALCULATOR_INSTANCE_CLASS_NAME).getConstructor().newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (
            InstantiationException
            | IllegalAccessException
            | IllegalArgumentException
            | InvocationTargetException
            | NoSuchMethodException
            | SecurityException e
        ) {
            e.printStackTrace();
        }

        return new CalculatorEmptyImpl();
    }

    public Boolean isTargetCalculatorInstance();

    public Double getSuppliedComponentsAverageDiameterCentralVoidDiameter(Long componentsCount, Double diameterAssemblyStep);

    public default Double getMilimeterCentralVoidDiameter(Strand strand) {
        return (
            strand.getSuppliedComponentsAverageMilimeterDiameter() *
            getSuppliedComponentsAverageDiameterCentralVoidDiameter(
                getSuppliesCountAtAssembly(null/*strand*/, strand.getSuppliesCount(), Long.valueOf(1)),
                strand.getDiameterAssemblyStep()
            )
        );
    }

    public Long getSuppliesCountAtAssembly(
        Strand strandIfNoSuppliesCountProvided,
        Long suppliesCountIfNoStrandProvided,
        Long assemblyIndex
    ) throws IllegalArgumentException;

    public List<AssemblyPresetDistributionPossibility> getAssemblyPresetDistributionPossibilitiesForAssemblyPresetDistribution(
        AssemblyPresetDistribution assemblyPresetDistribution
    );

    public IAssemblyPresetDistributionCalculator getCorrespondingAssemblyPresetDistributionCalculator(
        AssemblyPresetDistribution assemblyPresetDistribution
    );

    public Long assemblyPresetDistributionCalculatorCount();
}
