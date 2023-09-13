package com.github.smuddgge.squishyconfiguration.utility;

import com.github.smuddgge.squishyconfiguration.PreparedConfigurationFactory;
import com.github.smuddgge.squishyconfiguration.interfaces.Configuration;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a configuration creator.
 * Extend this class to allow creating
 * of a single configuration instance.
 */
public class ConfigurationCreator {

    private final @NotNull PreparedConfigurationFactory factory;

    /**
     * Used to create a configuration creator.
     *
     * @param factory The instance of the prepared
     *                configuration factory.
     */
    public ConfigurationCreator(@NotNull PreparedConfigurationFactory factory) {
        this.factory = factory;
    }

    /**
     * Used to get the instance of the factory.
     *
     * @return The instance of the factory.
     */
    public @NotNull PreparedConfigurationFactory getFactory() {
        return this.factory;
    }

    /**
     * Used to create a new instance of the configuration.
     *
     * @return The instance of the configuration.
     */
    public @NotNull Configuration create() {
        return this.factory.create();
    }
}
