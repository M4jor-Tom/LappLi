package com.muller.lappli.domain.exception;

public class PositionInSeveralAssemblyException extends Exception {

    public PositionInSeveralAssemblyException() {
        this(null, null);
    }

    public PositionInSeveralAssemblyException(String message) {
        this(message, null);
    }

    public PositionInSeveralAssemblyException(String message, Throwable cause) {
        super(message, cause);
    }
}
