package com.muller.lappli.domain.interfaces;

import com.muller.lappli.domain.Material;

/**
 * A {@link com.muller.lappli.domain.interfaces.CylindricComponent} which
 * has a plastic aspect on its surface
 */
public interface PlasticAspectCylindricComponent extends CylindricComponent {
    public Material getSurfaceMaterial();
}
