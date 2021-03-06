package com.muller.lappli.domain;

import com.muller.lappli.domain.enumeration.AssemblyPresetDistribution;
import java.util.ArrayList;
import java.util.List;

/**
 * A class representing ways of distributing
 * {@link com.muller.lappli.domain.AssemblyPreset}s
 * along a Strand
 */
public class AssemblyPresetDistributionPossibility implements Cloneable {

    private List<AssemblyPreset> assemblyPresets;
    private Double milimeterDiameterBeforeCentralCompletionComponent;

    public AssemblyPresetDistributionPossibility() {
        this(Double.NaN, new AssemblyPreset());
    }

    public AssemblyPresetDistributionPossibility(
        Double milimeterDiameterBeforeCentralCompletionComponent,
        AssemblyPreset... assemblyPresets
    ) {
        this(milimeterDiameterBeforeCentralCompletionComponent, List.of(assemblyPresets));
    }

    public AssemblyPresetDistributionPossibility(
        Double milimeterDiameterBeforeCentralCompletionComponent,
        List<AssemblyPreset> assemblyPresets
    ) {
        setMilimeterDiameterBeforeCentralCompletionComponent(milimeterDiameterBeforeCentralCompletionComponent);
        setAssemblyPresets(assemblyPresets);
    }

    public static AssemblyPresetDistributionPossibility forError() {
        return new AssemblyPresetDistributionPossibility(Double.NaN, AssemblyPreset.forError());
    }

    public static AssemblyPresetDistributionPossibility forStrandSupply(StrandSupply strandSupply) {
        AssemblyPresetDistribution assemblyPresetDistribution = AssemblyPresetDistribution.forStrandSupply(strandSupply);

        if (assemblyPresetDistribution == null) {
            return null;
        }

        return assemblyPresetDistribution.getAssemblyPresetDistributionPossibility(strandSupply.getForceCentralUtilityComponent());
    }

    @Override
    public AssemblyPresetDistributionPossibility clone() throws CloneNotSupportedException {
        List<AssemblyPreset> assemblyPresetsClones = new ArrayList<AssemblyPreset>();

        for (AssemblyPreset assemblyPreset : getAssemblyPresets()) {
            assemblyPresetsClones.add(assemblyPreset.clone());
        }

        return new AssemblyPresetDistributionPossibility(getMilimeterDiameterBeforeCentralCompletionComponent(), assemblyPresetsClones);
    }

    public AssemblyPresetDistributionPossibility getCloneWithCalculatedCentralComponent(
        Double suppliedComponentsAverageMilimeterDiameter,
        Double diameterAssemblyStep
    ) {
        AssemblyPresetDistributionPossibility clone = null;

        try {
            clone = (AssemblyPresetDistributionPossibility) clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        if (forcesCentralComponentToBeUtility()) {
            //If the central component is forced as a utility component,
            //there is no need to think about putting a central component.
            //That means that the AssemblyPresetDistributionPossibility is
            //in its final, presentable shape.
            return clone;
        }

        Double suppliedComponentsAverageDiameterCentralVoidDiameter = CalculatorManager
            .getCalculatorInstance()
            .getSuppliedComponentsAverageDiameterCentralVoidDiameter(
                getAssemblyPresets().get(0).getTotalComponentsCount(),
                diameterAssemblyStep
            );

        Double milimeterCentralVoidDiameter =
            suppliedComponentsAverageDiameterCentralVoidDiameter * suppliedComponentsAverageMilimeterDiameter;

        if (milimeterCentralVoidDiameter < getMilimeterDiameterBeforeCentralCompletionComponent()) {
            //If the milimeterCentralVoidDiameter is smaller than
            //getMilimeterDiameterBeforeCentralCompletionComponent,
            //there is no need to think about putting a central component.
            //That means that the AssemblyPresetDistributionPossibility is
            //in its final, presentable shape.
            return clone;
        }

        //In this case, milimeterCentralVoidDiameter is so big that we have to use a
        //central completion component to hold it.
        //This is characterized by a new AssemblyPreset(0, 1)
        List<AssemblyPreset> centerCompletionComponentAssemblyPresets = new ArrayList<AssemblyPreset>();
        centerCompletionComponentAssemblyPresets.add(new AssemblyPreset(0, 1));

        //Once the centerCompletionComponentAssemblyPresets is created, we need to
        //add the natives assembly presets around its central completion component
        centerCompletionComponentAssemblyPresets.addAll(clone.getAssemblyPresets());

        //Finally, we update the clone's AssemblyPresets list
        clone.setAssemblyPresets(centerCompletionComponentAssemblyPresets);

        return clone;
    }

    public AssemblyPreset getLastAssemblyPreset() {
        AssemblyPreset lastAssemblyPreset = null;

        for (AssemblyPreset assemblyPreset : getAssemblyPresets()) {
            lastAssemblyPreset = assemblyPreset;
        }

        return lastAssemblyPreset;
    }

    public Boolean forcesCentralComponentToBeUtility() {
        return getMilimeterDiameterBeforeCentralCompletionComponent().isNaN();
    }

    public AssemblyPreset getFirstAssemblyPreset() {
        if (getAssemblyPresets().isEmpty()) {
            return AssemblyPreset.forError();
        }

        return getAssemblyPresets().get(0);
    }

    public Boolean hasCentralComponent() {
        AssemblyPreset firstAssemblyPreset = getFirstAssemblyPreset();

        if (firstAssemblyPreset == null || AssemblyPreset.forError().equals(firstAssemblyPreset)) {
            return false;
        }

        return firstAssemblyPreset.isCentralAccordingToTotalComponentsCount();
    }

    public Boolean isConform(Long componentsCountToCheckEqual) {
        Long componentsSum = Long.valueOf(0);

        for (AssemblyPreset assemblyPreset : getAssemblyPresets()) {
            componentsSum += assemblyPreset.getUtilityComponentsCount();
        }

        return componentsCountToCheckEqual == componentsSum;
    }

    public List<AssemblyPreset> getAssemblyPresetsAfterCentral() {
        if (hasCentralComponent()) {
            List<AssemblyPreset> assemblyPresetsToReturn = new ArrayList<AssemblyPreset>();
            for (AssemblyPreset existingAssemblyPreset : getAssemblyPresets()) {
                if (!existingAssemblyPreset.isCentralAccordingToTotalComponentsCount()) {
                    assemblyPresetsToReturn.add(existingAssemblyPreset);
                }
            }

            return assemblyPresetsToReturn;
        }

        return getAssemblyPresets();
    }

    public List<AssemblyPreset> getAssemblyPresets() {
        return this.assemblyPresets;
    }

    public void setAssemblyPresets(List<AssemblyPreset> assemblyPresets) {
        this.assemblyPresets = assemblyPresets;
    }

    public Double getMilimeterDiameterBeforeCentralCompletionComponent() {
        return this.milimeterDiameterBeforeCentralCompletionComponent;
    }

    public void setMilimeterDiameterBeforeCentralCompletionComponent(Double milimeterDiameterBeforeCentralCompletionComponent) {
        this.milimeterDiameterBeforeCentralCompletionComponent = milimeterDiameterBeforeCentralCompletionComponent;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AssemblyPresetDistributionPossibility)) {
            return false;
        }

        return (
            this.getAssemblyPresets().equals(((AssemblyPresetDistributionPossibility) o).getAssemblyPresets()) &&
            this.getMilimeterDiameterBeforeCentralCompletionComponent() ==
            ((AssemblyPresetDistributionPossibility) o).getMilimeterDiameterBeforeCentralCompletionComponent()
        );
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "AssemblyPresetDistributionPossibility{" +
            "assemblyPresets=" +
            getAssemblyPresets() +
            ", milimeterDiameterBeforeCentralCompletionComponent=" +
            getMilimeterDiameterBeforeCentralCompletionComponent() +
            "}"
        );
    }
}
