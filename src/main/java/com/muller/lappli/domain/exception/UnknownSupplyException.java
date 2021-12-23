package com.muller.lappli.domain.exception;

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
