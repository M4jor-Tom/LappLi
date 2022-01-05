package com.muller.lappli.domain.exception;

public class IllegalStrandSupplyException extends Exception {

    public IllegalStrandSupplyException() {
        this(null, null);
    }

    public IllegalStrandSupplyException(String message) {
        this(message, null);
    }

    public IllegalStrandSupplyException(String message, Throwable cause) {
        super(message, cause);
    }
}
