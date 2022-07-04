package com.muller.lappli.domain.interfaces;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muller.lappli.domain.enumeration.CylindricComponentKind;

/**
 * A component which is cylindric, often assembled
 * via {@link com.muller.lappli.domain.interfaces.AssemblableOperation}s
 */
public interface CylindricComponent extends Designable {
    public Double getMilimeterDiameter();

    public Double getGramPerMeterLinearMass();

    public Boolean isUtility();

    public CylindricComponentKind getCylindricComponentKind();

    public default Boolean isCompletion() {
        return !isUtility();
    }

    @JsonIgnore
    default Double getSquareMilimeterSurface() {
        return Math.PI * Math.pow(getMilimeterDiameter(), 2) / 4;
    }
}
