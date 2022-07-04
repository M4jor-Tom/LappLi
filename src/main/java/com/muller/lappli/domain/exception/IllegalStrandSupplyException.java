package com.muller.lappli.domain.exception;

/**
 * This exception must throw if
 * a Supply's ObserverStrandSupply connexion
 * is corrupted
 */
public class IllegalStrandSupplyException extends Exception {

    public IllegalStrandSupplyException() {
        this(null, null);
    }

    public IllegalStrandSupplyException(String message) {
        this(message, null);
    }

    public IllegalStrandSupplyException(String message, Throwable cause) {
        super(message, cause);
    }
}
