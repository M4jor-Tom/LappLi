package com.muller.lappli.domain.interfaces;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muller.lappli.domain.DomainManager;
import com.muller.lappli.domain.enumeration.CylindricComponentKind;

public interface CylindricComponent extends Designable {
    public default String getMullerStadardizedFormatMilimeterDiameter() {
        if (getMilimeterDiameter() == null) {
            return "";
        }
        return DomainManager.mullerStandardizedFormat(getMilimeterDiameter());
    }

    public Double getMilimeterDiameter();

    public default String getMullerStadardizedFormatGramPerMeterLinearMass() {
        if (getGramPerMeterLinearMass() == null) {
            return "";
        }
        return DomainManager.mullerStandardizedFormat(getGramPerMeterLinearMass());
    }

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
