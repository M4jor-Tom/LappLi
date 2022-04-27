package com.muller.lappli.domain.exception;

public class RatioBoundExceedingException extends Exception {

    private static final String DEFAULT_ERROR_VARIABLE_NAME = "Ratio value";

    private static final String DEFAULT_ERROR_MESSAGE_MIDDLE = " should be between 0.0 and 1.0";

    private static final String DEFAULT_ERROR_MESSAGE_SUFFIX = ", but is equal to ";

    public RatioBoundExceedingException() {
        super(getDefaultErrorMessagePrefix(DEFAULT_ERROR_VARIABLE_NAME));
    }

    public RatioBoundExceedingException(String errorVariableName, Double errorVariableValue) {
        super(getDefaultErrorMessage(errorVariableName, errorVariableValue));
    }

    public RatioBoundExceedingException(Double errorVariableValue) {
        super(getDefaultErrorMessage(DEFAULT_ERROR_VARIABLE_NAME, errorVariableValue));
    }

    public RatioBoundExceedingException(String message) {
        super(message);
    }

    public RatioBoundExceedingException(String message, Throwable cause) {
        super(message, cause);
    }

    private static String getDefaultErrorMessagePrefix(String errorVariableName) {
        return errorVariableName + DEFAULT_ERROR_MESSAGE_MIDDLE;
    }

    private static String getDefaultErrorMessage(String errorVariableName, Double errorVariableValue) {
        if (errorVariableName == null) {
            errorVariableName = DEFAULT_ERROR_VARIABLE_NAME;
        }

        return getDefaultErrorMessagePrefix(errorVariableName) + DEFAULT_ERROR_MESSAGE_SUFFIX + errorVariableValue;
    }
}
