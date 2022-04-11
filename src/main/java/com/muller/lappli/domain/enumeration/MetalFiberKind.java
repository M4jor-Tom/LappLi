package com.muller.lappli.domain.enumeration;

/**
 * The MetalFiberKind enumeration.
 */
public enum MetalFiberKind {
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
