package com.muller.lappli.domain.exception;

public class UnknownMetalFiberException extends Exception {

    public UnknownMetalFiberException() {
        this(null, null);
    }

    public UnknownMetalFiberException(String message) {
        this(message, null);
    }

    public UnknownMetalFiberException(String message, Throwable cause) {
        super(message, cause);
    }
}
