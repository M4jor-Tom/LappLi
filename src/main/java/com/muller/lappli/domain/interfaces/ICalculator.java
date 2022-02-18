package com.muller.lappli.domain.interfaces;

import com.muller.lappli.domain.*;
import com.muller.lappli.domain.enumeration.AssemblyPresetDistribution;
import com.muller.lappli.domain.exception.ImpossibleAssemblyPresetDistributionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface ICalculator {
    /**
     * Tries to find the class in targetCalculatorInstanceClassName
     *
     * If it is found, instanciate it.
     *
     * Otherwise returns instanceReturnedIfTargetNotFound
     *
     * @param targetCalculatorInstanceClassName the class name which is tried
     * to instanciate
     * @param instanceReturnedIfTargetNotFound the object returned if
     * no class was found with targetCalculatorInstanceClassName
     * @return the instance of ICalculator
     */
    public static ICalculator getNewInstance(String targetCalculatorInstanceClassName, ICalculator instanceReturnedIfTargetNotFound) {
        try {
            return (ICalculator) Class.forName(targetCalculatorInstanceClassName).getConstructor().newInstance();
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

        return instanceReturnedIfTargetNotFound;
    }

    /**
     * Returns true from instance of {@link CalculatorManager#TARGET_CALCULATOR_INSTANCE_CLASS_NAME},
     * and false from empty implementations
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
    public default Double getMilimeterCentralVoidDiameter(Strand strand, Boolean forceCentralUtilityComponent)
        throws ImpossibleAssemblyPresetDistributionException {
        return (
            strand.getSuppliedComponentsAverageMilimeterDiameter() *
            getSuppliedComponentsAverageDiameterCentralVoidDiameter(
                getSuppliesCountAtAssembly(strand, Long.valueOf(1), true, forceCentralUtilityComponent),
                strand.getDiameterAssemblyStep()
            )
        );
    }

    /**
     * Calculates the amount of supplied components at a given assembly
     *
     * @param strand the strand in which we have to calculate
     * the amount of supplied components in a given assembly
     *
     * @param assemblyIndex the index of the given assembly
     *
     * @param useAssemblyPresetDistributionPossibilities true states that we shall refer
     * to the AssemblyPresetDistributions, when false states that each Assembly will be
     * calculated independently
     *
     * @param forceCentralUtilityComponent to set to true if we force a utility supplied
     * component to be at the center of assemblies
     *
     * @return the amount of supplied components at a given assembly index
     */
    public Long getSuppliesCountAtAssembly(
        Strand strand,
        Long assemblyIndex,
        Boolean useAssemblyPresetDistributionPossibilities,
        Boolean forceCentralUtilityComponent
    ) throws ImpossibleAssemblyPresetDistributionException;

    public List<AssemblyPresetDistributionPossibility> getAssemblyPresetDistributionPossibilitiesForAssemblyPresetDistribution(
        AssemblyPresetDistribution assemblyPresetDistribution
    );

    public IAssemblyPresetDistributionCalculator getCorrespondingAssemblyPresetDistributionCalculator(
        AssemblyPresetDistribution assemblyPresetDistribution
    );

    public Long getAssemblyPresetDistributionCalculatorCount();
}
