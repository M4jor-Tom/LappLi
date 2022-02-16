package com.muller.lappli.domain;

import java.util.List;

public class AssemblyPresetDistributionPossibility {

    private List<AssemblyPreset> assemblyPresets;
    private Double milimeterDiameterBeforeCentralCompletionComponent;

    public AssemblyPresetDistributionPossibility() {
        this(Double.NaN, new AssemblyPreset());
    }

    public AssemblyPresetDistributionPossibility(
        Double milimeterDiameterBeforeCentralCompletionComponent,
        AssemblyPreset... assemblyPreset
    ) {
        setMilimeterDiameterBeforeCentralCompletionComponent(milimeterDiameterBeforeCentralCompletionComponent);
        setAssemblyPresets(assemblyPresets);
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
