package com.muller.lappli.domain.enumeration;

import com.muller.lappli.domain.AssemblyPresetDistributionPossibility;
import com.muller.lappli.domain.interfaces.IAssemblyDistributionPreset;
import com.muller.lappli.domain.interfaces.ICalculator;
import java.util.List;

public enum AssemblyDistributionPreset implements IAssemblyDistributionPreset {
    FOR_1(),
    FOR_2(),
    FOR_3(),
    FOR_4(),
    FOR_5(),
    FOR_6(),
    FOR_7(),
    FOR_8(),
    FOR_9(),
    FOR_10(),
    FOR_11(),
    FOR_12(),
    FOR_13(),
    FOR_14(),
    FOR_15(),
    FOR_16(),
    FOR_17(),
    FOR_18(),
    FOR_19(),
    FOR_20(),
    FOR_21(),
    FOR_22(),
    FOR_23(),
    FOR_24(),
    FOR_25(),
    FOR_26(),
    FOR_27(),
    FOR_28(),
    FOR_29(),
    FOR_30(),
    FOR_31(),
    FOR_32(),
    FOR_33(),
    FOR_34(),
    FOR_35(),
    FOR_36(),
    FOR_37(),
    FOR_38(),
    FOR_39(),
    FOR_40(),
    FOR_41(),
    FOR_42(),
    FOR_43(),
    FOR_44(),
    FOR_45(),
    FOR_46(),
    FOR_47(),
    FOR_48(),
    FOR_49(),
    FOR_50(),
    FOR_51(),
    FOR_52(),
    FOR_53(),
    FOR_54(),
    FOR_55(),
    FOR_56(),
    FOR_57(),
    FOR_58(),
    FOR_59(),
    FOR_60(),
    FOR_61(),
    FOR_62(),
    FOR_63(),
    FOR_64(),
    FOR_65(),
    FOR_66(),
    FOR_67(),
    FOR_68(),
    FOR_69(),
    FOR_70();

    private AssemblyDistributionPreset() {}

    public Long getAssembliesCount() {
        return null;
    }

    public Long getSuppliesCountAtAssembly(Long assemblyIndex) {
        return ICalculator.getInstance().getSuppliesCountAtAssembly(null, getAssembliesCount(), assemblyIndex);
    }

    public Double getFinalMilimeterDiameter() {
        return getMilimeterDiameterAtAssembly(getAssembliesCount());
    }

    public Double getMilimeterDiameterAtAssembly(Long assemblyIndex) {
        return Double.NaN;
    }

    public Long getComponentsCount() {
        return Long.valueOf(ordinal() + 1);
    }

    public Boolean mustCentralComponent() {
        return ICalculator.getInstance().assemblyDistributionPresetMustHaveCentralComponent(this);
    }

    public List<AssemblyPresetDistributionPossibility> getAssemblyPresetDistributionPossibilities() {
        return ICalculator.getInstance().getAssemblyPresetDistributionPossibilitiesForComponentsCount(getComponentsCount());
    }
}
