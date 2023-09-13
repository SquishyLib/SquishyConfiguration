package com.github.smuddgge.squishyconfiguration;

import com.github.smuddgge.squishyconfiguration.interfaces.Configuration;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * Represents a prepared configuration factory.
 * Used to prepare the creation of a {@link Configuration}
 * so it can be created in multiple instances
 * using this class.
 */
public class PreparedConfigurationFactory {

    private final @NotNull ConfigurationFactory factory;
    private final @NotNull File file;

    /**
     * Used to create aa prepared configuration factory.
     *
     * @param factory The instance of the type factory.
     * @param file The instance of the config file.
     */
    public PreparedConfigurationFactory(@NotNull ConfigurationFactory factory, @NotNull File file) {
        this.factory = factory;
        this.file = file;
    }

    /**
     * Used to create a new instance of
     * the prepared configuration.
     *
     * @return The new instance of the configuration.
     */
    public @NotNull Configuration create() {
        return factory.create(this.file);
    }
}
