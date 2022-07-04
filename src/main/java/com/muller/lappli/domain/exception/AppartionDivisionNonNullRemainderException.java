package com.muller.lappli.domain.exception;

/**
 * When computing a strand's repartition,
 * this exception must trigger if the remain
 * of its division is non-null
 */
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
