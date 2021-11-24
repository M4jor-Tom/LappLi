package com.muller.lappli.domain.interfaces;

import com.muller.lappli.domain.Dimension;

public interface CylindricComponent extends Article {
    Dimension getMilimeterDiameter();
    Dimension getGramPerMeterLinearMass();
}
