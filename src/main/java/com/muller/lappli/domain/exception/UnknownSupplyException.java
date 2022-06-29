package com.muller.lappli.domain.exception;

/**
 * This exception must be triggered when
 * requesting an unknown kind of {@link com.muller.lappli.domain.abstracts.AbstractSupply}
 */
public class UnknownSupplyException extends Exception {

    public UnknownSupplyException() {
        this(null, null);
    }

    public UnknownSupplyException(String message) {
        this(message, null);
    }

    public UnknownSupplyException(String message, Throwable cause) {
        super(message, cause);
    }
}
