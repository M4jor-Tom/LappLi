package com.muller.lappli.domain.exception;

/**
 * This exception must trigger if the user wants
 * to add supplies' components in {@link com.muller.lappli.domain.IntersticeAssembly}
 * when they are all filled
 */
public class NoIntersticeAvailableException extends Exception {

    public NoIntersticeAvailableException() {
        this(null, null);
    }

    public NoIntersticeAvailableException(String message) {
        this(message, null);
    }

    public NoIntersticeAvailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
