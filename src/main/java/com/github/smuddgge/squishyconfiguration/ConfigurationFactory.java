package com.github.smuddgge.squishyconfiguration;

import com.github.smuddgge.squishyconfiguration.implementation.toml.TomlConfiguration;
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
            return this.create(new File(path + ".yml"));
        }

        @Override
        public Configuration create(File folder, String path) {
            return new YamlConfiguration(folder, path + ".yml");
        }

        @Override
        public Configuration create(File file) {
            return new YamlConfiguration(file);
        }
    },
    TOML {
        @Override
        public Configuration create(String path) {
            return this.create(new File(path + ".toml"));
        }

        @Override
        public Configuration create(File folder, String path) {
            return new TomlConfiguration(folder, path + ".toml");
        }

        @Override
        public Configuration create(File file) {
            return new TomlConfiguration(file);
        }
    };

    /**
     * Used to create a configuration file.
     *
     * @param path The location of this file without extensions.
     * @return The instance of a new configuration file instance.
     */
    public abstract Configuration create(String path);

    /**
     * Used to create a configuration file.
     *
     * @param folder The instance of the parent folder.
     * @param path   The location in the file without the extensions.
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