package com.muller.lappli.domain.exception;

public class AppartionDivisionNonNullRemainderException extends Exception {

    public AppartionDivisionNonNullRemainderException() {
        this(null, null);
    }

    public AppartionDivisionNonNullRemainderException(String message) {
        this(message, null);
    }

    public AppartionDivisionNonNullRemainderException(String message, Throwable cause) {
        super(message, cause);
    }
}
