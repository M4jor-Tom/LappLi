package com.muller.lappli.domain;

import java.util.ArrayList;
import java.util.List;

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
        setMilimeterDiameterBeforeCentralCompletionComponent(milimeterDiameterBeforeCentralCompletionComponent);
        setAssemblyPresets(List.of(assemblyPresets));
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        List<AssemblyPreset> assemblyPresetsClones = new ArrayList<AssemblyPreset>();

        for (AssemblyPreset assemblyPreset : getAssemblyPresets()) {
            assemblyPresetsClones.add((AssemblyPreset) assemblyPreset.clone());
        }

        return new AssemblyPresetDistributionPossibility(
            getMilimeterDiameterBeforeCentralCompletionComponent(),
            (AssemblyPreset[]) assemblyPresetsClones.toArray()
        );
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
        List<AssemblyPreset> centerCompletionComponentAssemblyPresets = List.of(new AssemblyPreset(0, 1));

        //Once the centerCompletionComponentAssemblyPresets is created, we need to
        //add the natives assembly presets around its central completion component
        centerCompletionComponentAssemblyPresets.addAll(clone.getAssemblyPresets());

        //Finally, we update the clone's AssemblyPresets list
        clone.setAssemblyPresets(centerCompletionComponentAssemblyPresets);

        return clone;
    }

    public Boolean forcesCentralComponentToBeUtility() {
        return getMilimeterDiameterBeforeCentralCompletionComponent().isNaN();
    }

    public Boolean isConform(Long componentsCountToCheckEqual) {
        Long componentsSum = Long.valueOf(0);

        for (AssemblyPreset assemblyPreset : getAssemblyPresets()) {
            componentsSum += assemblyPreset.getUtilityComponentsCount();
        }

        return componentsCountToCheckEqual == componentsSum;
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
}
