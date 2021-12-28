package com.muller.lappli.domain.exception;

public class PositionAlreadyHasSupplyException extends PositionSettingException {

    public PositionAlreadyHasSupplyException() {
        this(null, null);
    }

    public PositionAlreadyHasSupplyException(String message) {
        this(message, null);
    }

    public PositionAlreadyHasSupplyException(String message, Throwable cause) {
        super(message, cause);
    }
}
