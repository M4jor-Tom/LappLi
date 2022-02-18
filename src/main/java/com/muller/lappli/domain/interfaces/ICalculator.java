package com.muller.lappli.domain.interfaces;

import com.muller.lappli.domain.*;
import com.muller.lappli.domain.enumeration.AssemblyPresetDistribution;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface ICalculator {
    /**
     * Tries to find the class in {@link DomainManager#TARGET_CALCULATOR_INSTANCE_CLASS_NAME}.
     *
     * If it is found, instanciate it.
     *
     * Otherwise instanciate {@link CalculatorEmptyImpl}.
     *
     * @return the instance of ICalculator
     */
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

    /**
     * Returns true from instance of {@link DomainManager#TARGET_CALCULATOR_INSTANCE_CLASS_NAME},
     * and false from {@link CalculatorEmptyImpl}
     *
     * @return a Boolean
     */
    public Boolean isTargetCalculatorInstance();

    /**
     * Calculates the diameter of the central void generated by an assembly,
     * mesured in components average diameter.
     *
     * @param suppliedComponentsCount the count of suppliedComponents which are
     * just around the center
     *
     * @param diameterAssemblyStep the assembly step in diameters with which the
     * assembly is done
     *
     * @return the central diameter
     */
    public Double getSuppliedComponentsAverageDiameterCentralVoidDiameter(Long suppliedComponentsCount, Double diameterAssemblyStep);

    /**
     * Calculates the diameter of the central void generated by the assembly
     * mesured in milimeters which will be resulted from Strand's
     * supplied components counts and the forcing of a central utility component
     *
     * @param strand the protagonist strand
     * @param forceCentralUtilityComponent true means we force a strand's utility component
     * to be at the center of assemblies
     * @return the central diameter
     */
    public default Double getMilimeterCentralVoidDiameter(Strand strand, Boolean forceCentralUtilityComponent) {
        return (
            strand.getSuppliedComponentsAverageMilimeterDiameter() *
            getSuppliedComponentsAverageDiameterCentralVoidDiameter(
                getSuppliesCountAtAssembly(null/*strand*/, strand.getSuppliesCount(), Long.valueOf(1), forceCentralUtilityComponent),
                strand.getDiameterAssemblyStep()
            )
        );
    }

    /**
     * Calculates the amount of supplied components at a given assembly
     *
     * @param strandIfNoSuppliesCountProvided the strand in which we have to calculate
     * the amount of supplied components in a given assembly
     * Supplying this argument means we use formulas and calculation to get a result
     *
     * @param suppliesCountIfNoStrandProvided the total amount of supplied components
     * amongst which, only some will be into the given assembly
     *
     * @param assemblyIndex the index of the given assembly
     *
     * @param forceCentralUtilityComponent to set to true if we force a utility supplied
     * component to be at the center of assemblies
     *
     * @return the amount of supplied components at a given assembly index
     *
     * @throws IllegalArgumentException if both or nor strandIfNoSuppliesCountProvided
     * and suppliesCountIfNoStrandProvided are null
     */
    public Long getSuppliesCountAtAssembly(
        Strand strandIfNoSuppliesCountProvided,
        Long suppliesCountIfNoStrandProvided,
        Long assemblyIndex,
        Boolean forceCentralUtilityComponent
    ) throws IllegalArgumentException;

    public List<AssemblyPresetDistributionPossibility> getAssemblyPresetDistributionPossibilitiesForAssemblyPresetDistribution(
        AssemblyPresetDistribution assemblyPresetDistribution
    );

    public IAssemblyPresetDistributionCalculator getCorrespondingAssemblyPresetDistributionCalculator(
        AssemblyPresetDistribution assemblyPresetDistribution
    );

    public Long getAssemblyPresetDistributionCalculatorCount();
}
