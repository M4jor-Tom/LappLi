package com.muller.lappli.domain.exception;

public class PositionAlreadyInAssemblyException extends PositionSettingException {

    public PositionAlreadyInAssemblyException() {
        this(null, null);
    }

    public PositionAlreadyInAssemblyException(String message) {
        this(message, null);
    }

    public PositionAlreadyInAssemblyException(String message, Throwable cause) {
        super(message, cause);
    }
}
