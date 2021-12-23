package com.muller.lappli.domain.exception;

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
