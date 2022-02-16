package com.muller.lappli.domain.interfaces;

import com.muller.lappli.domain.CalculatorEmptyImpl;
import com.muller.lappli.domain.DomainManager;
import com.muller.lappli.domain.Strand;
import com.muller.lappli.domain.enumeration.AssemblyDistributionPreset;
import java.lang.reflect.InvocationTargetException;

public interface ICalculator {
    public static ICalculator getInstance() {
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

    public default Boolean instanceIsCompagnySecret() {
        return getClass().getSimpleName().equals(DomainManager.TARGET_CALCULATOR_INSTANCE_CLASS_NAME);
    }

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

    public Boolean assemblyDistributionPresetMustHaveCentralComponent(AssemblyDistributionPreset assemblyDistributionPreset);

    public Long getSuppliesCountAtAssembly(
        Strand strandIfNoSuppliesCountProvided,
        Long suppliesCountIfNoStrandProvided,
        Long assemblyIndex
    ) throws IllegalArgumentException;
}
