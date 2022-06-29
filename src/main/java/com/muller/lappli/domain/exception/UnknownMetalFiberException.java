package com.muller.lappli.domain.exception;

/**
 * This exception must be triggered when
 * the type of {@link com.muller.lappli.domain.abstracts.AbstractMetalFiber}
 * requeste by a process is not determinable
 */
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
