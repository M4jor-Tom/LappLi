package com.muller.lappli.domain.interfaces;

public interface CylindricComponent extends Designable {
    Double getMilimeterDiameter();
    Double getGramPerMeterLinearMass();

    default Double getSquareMilimeterSurface() {
        return Math.PI * Math.pow(getGramPerMeterLinearMass(), 2) / 4;
    }
}
