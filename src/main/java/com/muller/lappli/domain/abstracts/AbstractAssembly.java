package com.muller.lappli.domain.abstracts;

import com.muller.lappli.domain.AssemblyPreset;
import com.muller.lappli.domain.AssemblyPresetDistributionPossibility;
import com.muller.lappli.domain.DomainManager;
import com.muller.lappli.domain.interfaces.IOperation;
import javax.persistence.MappedSuperclass;

/**
 * This class represents AbstractOperations which are Assemblies
 */
@MappedSuperclass
public abstract class AbstractAssembly<T extends AbstractAssembly<T>> extends AbstractOperation<T> {

    public AbstractAssembly() {
        super();
    }

    @Override
    public abstract Double getMilimeterDiameterIncidency();

    public Long getAssemblyCountUnderThisPlus1() {
        if (getOwnerStrandSupply() == null) {
            return DomainManager.ERROR_LONG_POSITIVE_VALUE;
        }

        Long count = Long.valueOf(0);

        for (IOperation<?> operation : getOwnerStrandSupply().getOperations()) {
            if (operation instanceof AbstractNonCentralAssembly<?>) {
                count++;
            }
            if (equals(operation)) {
                return count;
            }
        }

        return DomainManager.ERROR_LONG_POSITIVE_VALUE;
    }

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

            if (assemblyPresetDistributionPossibility == null) {
                return AssemblyPreset.forError();
            }

            Integer indexOfFirstCoreAssembly = assemblyPresetDistributionPossibility.hasCentralComponent() ? 1 : 0;

            return assemblyPresetDistributionPossibility
                .getAssemblyPresets()
                .get(indexOfFirstCoreAssembly + getAssemblyCountUnderThisPlus1().intValue() - 1);
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
}
