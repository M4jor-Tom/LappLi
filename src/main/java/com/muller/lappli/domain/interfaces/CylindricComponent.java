package com.muller.lappli.domain.interfaces;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface CylindricComponent extends Designable {
    public Double getMilimeterDiameter();

    public Double getGramPerMeterLinearMass();

    @JsonIgnore
    default Double getSquareMilimeterSurface() {
        return Math.PI * Math.pow(getGramPerMeterLinearMass(), 2) / 4;
    }
}
