package com.muller.lappli.domain.exception;

/**
 * This exception must trigger if a {@link com.muller.lappli.domain.StrandSupply}
 * requests an {@link com.muller.lappli.domain.enumeration.AssemblyPresetDistribution} which is not
 * possible to make
 */
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
