package com.muller.lappli.domain.interfaces;

import com.muller.lappli.domain.*;
import com.muller.lappli.domain.enumeration.AssemblyPresetDistribution;
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
     * @return the central diameter
     */
    public default Double getMilimeterCentralVoidDiameter(StrandSupply strandSupply) {
        AssemblyPresetDistributionPossibility assemblyPresetDistributionPossibility = strandSupply.getAssemblyPresetDistributionPossibility();

        if (assemblyPresetDistributionPossibility == null) {
            return Double.NaN;
        }

        Long indexOfFirst3OrMoreComposedCoreAssembly = Long.valueOf(0);

        if (assemblyPresetDistributionPossibility.getFirstAssemblyPreset().isCentralAccordingToTotalComponentsCount()) {
            indexOfFirst3OrMoreComposedCoreAssembly = Long.valueOf(1);
        }

        return (
            strandSupply.getStrand().getSuppliedComponentsAverageMilimeterDiameter() *
            getSuppliedComponentsAverageDiameterCentralVoidDiameter(
                getSuppliesCountAtAssembly(strandSupply, indexOfFirst3OrMoreComposedCoreAssembly),
                strandSupply.getDiameterAssemblyStep()
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
    public Long getSuppliesCountAtAssembly(StrandSupply strandSupply, Long assemblyIndex);

    /**
     * Generates a new AssemblyPresetDistributionPossibility which represents
     * the best fit for the cable
     *
     * @param strand the Strand to describe
     * @return an AssemblyPresetDistributionPossibility which describes its Assemblies
     * at best
     */
    public AssemblyPresetDistributionPossibility getAssemblyPresetDistributionPossibility(StrandSupply strandSupply);

    /**
     * Calculates the assembly void at the given Assembly for the Strand
     *
     * @param strand the Strand to analyse
     * @param assemblyIndex the index of the Assembly to analyse
     * @return the assembly void in the analysed Assembly
     */
    public Double getSuppliedComponentsAverageDiameterAssemblyVoid(StrandSupply strandSupply, Long assemblyIndex);

    /**
     * Same as {@link ICalculator#getSuppliedComponentsAverageDiameterAssemblyVoid},
     * but the result is in milimeters
     *
     * @return the assembly void in the analysed Assembly in milimeters
     */
    public default Double getMilimeterAssemblyVoid(StrandSupply strandSupply, Long assemblyIndex) {
        return (
            getSuppliedComponentsAverageDiameterAssemblyVoid(strandSupply, assemblyIndex) *
            strandSupply.getStrand().getSuppliedComponentsAverageMilimeterDiameter()
        );
    }

    public Double suggestSuppliedComponentsCount(Double centralDiameterInRoundComponentsDiameter, Double diameterAssemblyStep);

    public Double suggestSuppliedComponentsCountWithMilimeterDiameters(
        Double centralMilimeterDiameter,
        Double roundComponentsAverageDiameter,
        Double diameterAssemblyStep
    );

    public List<AssemblyPresetDistributionPossibility> getAssemblyPresetDistributionPossibilitiesForAssemblyPresetDistribution(
        AssemblyPresetDistribution assemblyPresetDistribution
    );

    public IAssemblyPresetDistributionCalculator getCorrespondingAssemblyPresetDistributionCalculator(
        AssemblyPresetDistribution assemblyPresetDistribution
    );

    public Long getAssemblyPresetDistributionCalculatorCount();
}
