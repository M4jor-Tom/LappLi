package com.muller.lappli.domain.abstracts;

import com.muller.lappli.domain.AssemblyPreset;
import com.muller.lappli.domain.AssemblyPresetDistributionPossibility;
import javax.persistence.MappedSuperclass;

/**
 * This class represents AbstractOperations which are Assemblies
 */
@MappedSuperclass
public abstract class AbstractAssembly<T extends AbstractAssembly<T>> extends AbstractOperation<T> {

    public abstract Long getAssemblyLayer();

    @Override
    public abstract Double getMilimeterDiameterIncidency();

    public abstract Long getComponentsCount();

    public AssemblyPreset suggestAssemblyPreset() {
        try {
            if (getOwnerStrandSupply() == null) {
                return AssemblyPreset.forError();
            } else if (Long.valueOf(0).equals(getOwnerStrandSupply().getSuppliedComponentsDividedCount())) {
                return AssemblyPreset.forError();
            }

            AssemblyPresetDistributionPossibility assemblyPresetDistributionPossibility = getOwnerStrandSupply()
                .getAssemblyPresetDistributionPossibility();

            Integer indexOfFirstCoreAssembly = assemblyPresetDistributionPossibility.hasCentralComponent() ? 1 : 0;

            return assemblyPresetDistributionPossibility
                .getAssemblyPresets()
                .get(indexOfFirstCoreAssembly + getAssemblyLayer().intValue() - 1);
        } /*catch (NullPointerException e) {}*/catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return AssemblyPreset.forError();
    }

    public Long getCompletionComponentsCount() {
        return suggestAssemblyPreset().getCompletionComponentsCount();
    }

    public Long getUtilityComponentsCount() {
        return suggestAssemblyPreset().getUtilityComponentsCount();
    }

    @Override
    public Long getOperationLayer() {
        return Long.valueOf(0);
    }
}
