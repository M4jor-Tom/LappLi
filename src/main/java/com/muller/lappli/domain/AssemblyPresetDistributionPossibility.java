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
