package com.muller.lappli.domain.exception;

public class PositionHasSeveralSupplyException extends Exception {

    public PositionHasSeveralSupplyException() {
        this(null, null);
    }

    public PositionHasSeveralSupplyException(String message) {
        this(message, null);
    }

    public PositionHasSeveralSupplyException(String message, Throwable cause) {
        super(message, cause);
    }
}
