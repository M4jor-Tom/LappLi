package com.muller.lappli.domain.exception;

public class ImpossibleAssemblyPresetDistributionException extends Exception {

    public ImpossibleAssemblyPresetDistributionException() {
        this(null, null);
    }

    public ImpossibleAssemblyPresetDistributionException(String message) {
        this(message, null);
    }

    public ImpossibleAssemblyPresetDistributionException(String message, Throwable cause) {
        super(message, cause);
    }
}
