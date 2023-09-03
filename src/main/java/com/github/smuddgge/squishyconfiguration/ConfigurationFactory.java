package com.github.smuddgge.squishyconfiguration;

import com.github.smuddgge.squishyconfiguration.implementation.yaml.YamlConfiguration;
import com.github.smuddgge.squishyconfiguration.interfaces.Configuration;

import java.io.File;

/**
 * <h1>Represents the configuration factory</h1>
 * Used to create a configuration file.
 */
public enum ConfigurationFactory {
    YAML {
        @Override
        public Configuration create(String path) {
            return this.create(new File(path));
        }

        @Override
        public Configuration create(File folder, String path) {
            return new YamlConfiguration(folder, path);
        }

        @Override
        public Configuration create(File file) {
            return new YamlConfiguration(file);
        }
    };

    /**
     * Used to create a configuration file.
     *
     * @param path The location of this file with extensions.
     * @return The instance of a new configuration file instance.
     */
    public abstract Configuration create(String path);

    /**
     * Used to create a configuration file.
     *
     * @param folder The instance of the parent folder.
     * @param path The location in the file with the extensions.
     * @return The instance of a new configuration file instance.
     */
    public abstract Configuration create(File folder, String path);

    /**
     * Used to create a configuration file.
     *
     * @param file The instance of the file.
     * @return The instance of a new configuration file instance.
     */
    public abstract Configuration create(File file);
}