package com.muller.lappli.domain.converter;

import com.muller.lappli.domain.Material;
import com.muller.lappli.domain.enumeration.CylindricComponentKind;
import com.muller.lappli.domain.interfaces.CylindricComponent;
import com.muller.lappli.domain.interfaces.PlasticAspectCylindricComponent;

public class CylindricComponentConverter {

    public static PlasticAspectCylindricComponent toPlasticAspectCylindricComponent(
        CylindricComponent cylindricComponent,
        Material surfaceMaterial
    ) {
        if (cylindricComponent == null) {
            throw new NullPointerException("Attempt to convert a null entity");
        }

        return new PlasticAspectCylindricComponent() {
            @Override
            public Double getMilimeterDiameter() {
                return cylindricComponent.getMilimeterDiameter();
            }

            @Override
            public Double getGramPerMeterLinearMass() {
                return cylindricComponent.getGramPerMeterLinearMass();
            }

            @Override
            public Boolean isUtility() {
                return cylindricComponent.isUtility();
            }

            @Override
            public CylindricComponentKind getCylindricComponentKind() {
                return cylindricComponent.getCylindricComponentKind();
            }

            @Override
            public String getDesignation() {
                return cylindricComponent.getDesignation();
            }

            @Override
            public Material getSurfaceMaterial() {
                return surfaceMaterial;
            }
        };
    }
}
