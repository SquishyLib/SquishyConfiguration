package com.github.smuddgge.squishyconfiguration.interfaces;

/**
 * Represents a configuration file interface
 */
public interface Configuration extends ConfigurationSection {

    /**
     * Used to get the absolute path
     * Combines the absolute path from the folder and path.
     *
     * @return The absolute path.
     */
    String getAbsolutePath();

    /**
     * Used to load the configuration file to the class instance.
     *
     * @return True if successful.
     */
    boolean load();

    /**
     * Used to save the instance of the data to the configuration file
     *
     * @return True if successful
     */
    boolean save();
}
