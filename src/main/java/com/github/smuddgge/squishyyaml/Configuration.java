package com.github.smuddgge.squishyyaml;

/**
 * Represents a configuration file interface
 */
public interface Configuration {

    /**
     * Used to load the configuration file to the class instance
     *
     * @return True if successful
     */
    boolean load();
}
