package com.muller.lappli.domain.exception;

public class PositionSettingException extends Exception {

    public PositionSettingException() {
        this(null, null);
    }

    public PositionSettingException(String message) {
        this(message, null);
    }

    public PositionSettingException(String message, Throwable cause) {
        super(message, cause);
    }
}
