package com.muller.lappli.service.factory;

public class ServiceNotFoundException extends Exception {

    public ServiceNotFoundException() {
        this(null, null);
    }

    public ServiceNotFoundException(String message) {
        this(message, null);
    }

    public ServiceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
