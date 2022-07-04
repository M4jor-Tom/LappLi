package com.muller.lappli.domain.exception;

import com.muller.lappli.domain.interfaces.IOperation;

/**
 * This exception must be triggered when
 * requesting an unknown kind of {@link com.muller.lappli.domain.interfaces.IOperation}
 */
public class UnknownOperationException extends Exception {

    public UnknownOperationException() {
        this(null, null);
    }

    public UnknownOperationException(IOperation<?> unknownOperation) {
        this(unknownOperation.getClass().getName(), null);
    }

    public UnknownOperationException(String message) {
        this(message, null);
    }

    public UnknownOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
