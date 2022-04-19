package com.muller.lappli.domain.enumeration;

import com.muller.lappli.domain.CopperFiber;
import com.muller.lappli.domain.SteelFiber;

/**
 * The MetalFiberKind enumeration.
 */
public enum MetalFiberKind {
    //[METAL_FIBER]
    RED_COPPER(true, true, true),
    TINNED_COPPER(true, true, true),
    STEEL(true, false, true);

    private Boolean supportedForContinuityWire;

    private Boolean supportedForScreen;

    private Boolean supportedForPlait;

    private MetalFiberKind(Boolean supportedForContinuityWire, Boolean supportedForScreen, Boolean supportedForPlait) {
        setSupportedForContinuityWire(supportedForContinuityWire);
        setSupportedForScreen(supportedForScreen);
        setSupportedForPlait(supportedForPlait);
    }

    public Boolean isForMetalFiber(Class<?> metalFiberClass) {
        //[METAL_FIBER]
        if (CopperFiber.class.equals(metalFiberClass)) {
            return this.equals(RED_COPPER) || this.equals(TINNED_COPPER);
        } else if (SteelFiber.class.equals(metalFiberClass)) {
            return this.equals(STEEL);
        }

        return false;
    }

    public Boolean getSupportedForContinuityWire() {
        return this.supportedForContinuityWire;
    }

    private void setSupportedForContinuityWire(Boolean supportedForContinuityWire) {
        this.supportedForContinuityWire = supportedForContinuityWire;
    }

    public Boolean getSupportedForScreen() {
        return this.supportedForScreen;
    }

    private void setSupportedForScreen(Boolean supportedForScreen) {
        this.supportedForScreen = supportedForScreen;
    }

    public Boolean getSupportedForPlait() {
        return this.supportedForPlait;
    }

    private void setSupportedForPlait(Boolean supportedForPlait) {
        this.supportedForPlait = supportedForPlait;
    }
}
