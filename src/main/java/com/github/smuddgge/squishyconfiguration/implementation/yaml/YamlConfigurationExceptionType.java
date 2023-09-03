package com.github.smuddgge.squishyconfiguration.implementation.yaml;

public enum YamlConfigurationExceptionType {
    CREATE_FILE("Attempted to create the file <var> but an error occurred.");

    /**
     * Represents the exception message
     */
    private final String message;

    /**
     * Used to create a new yaml configuration exception enum
     *
     * @param message The error message
     */
    YamlConfigurationExceptionType(String message) {
        this.message = message;
    }

    /**
     * Used to get the exception message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Used to get the message and replace the placeholder with a variable
     *
     * @param variable To replace with the placeholder
     * @return The message formatted with the variable
     */
    public String getMessage(String variable) {
        return this.message.replace("<var>", variable);
    }
}