package com.github.smuddgge.squishyyaml;

import java.util.Map;

/**
 * Represents a configuration section interface
 */
public interface ConfigurationSection {

    /**
     * Used to get the configuration section as a map
     *
     * @return A map representing the configuration section
     */
    Map<String, Object> getData();

    /**
     * Used to get the location of this configuration
     * section from the root
     *
     * @return The root path
     */
    String getRootPath();

    /**
     * Used to get the location of this configuration
     * section from the root
     *
     * @param path The location from this configuration section
     *             to another nested section
     * @return The root path
     */
    String getRootPath(String path);

    /**
     * Used to set or create a value in the configuration section
     *
     * @param value The value to set the configuration section to
     */
    void set(Object value);

    /**
     * Used to set or create a value in the configuration section
     * and saved in the base configuration section
     *
     * @param path The location to set the value
     * @param value The value to be set in the config
     */
    void set(String path, Object value);

    /**
     * Used to set or create a value in the configuration section
     * This will only be saved in the configuration section
     * and not specifically in the base section
     *
     * @param path The location to set the value
     * @param value The value to be set in the section
     */
    void setInSection(String path, Object value);

    /**
     * Used to get any value from the configuration file
     *
     * @param path The location of the value
     * @return The value set at the location
     */
    Object get(String path);

    /**
     * Used to get a configuration section from the configuration file
     *
     * @param path The location of the configuration section
     * @return An instance of the configuration section
     */
    ConfigurationSection getSection(String path);
}
