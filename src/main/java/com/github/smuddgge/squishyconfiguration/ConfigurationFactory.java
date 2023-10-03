package com.github.smuddgge.squishyconfiguration;

import com.github.smuddgge.squishyconfiguration.implementation.TomlConfiguration;
import com.github.smuddgge.squishyconfiguration.implementation.YamlConfiguration;
import com.github.smuddgge.squishyconfiguration.interfaces.Configuration;
import org.jetbrains.annotations.NotNull;

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

        @Override
        public @NotNull PreparedConfigurationFactory createPreparedFactory(@NotNull String path) {
            return new PreparedConfigurationFactory(this, new File(path + ".yml"));
        }

        @Override
        public @NotNull PreparedConfigurationFactory createPreparedFactory(@NotNull File folder, @NotNull String path) {
            return new PreparedConfigurationFactory(this, new File(folder, path + ".yml"));
        }

        @Override
        public @NotNull PreparedConfigurationFactory createPreparedFactory(@NotNull File file) {
            return new PreparedConfigurationFactory(this, file);
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

        @Override
        public @NotNull PreparedConfigurationFactory createPreparedFactory(@NotNull String path) {
            return new PreparedConfigurationFactory(this, new File(path + ".toml"));
        }

        @Override
        public @NotNull PreparedConfigurationFactory createPreparedFactory(@NotNull File folder, @NotNull String path) {
            return new PreparedConfigurationFactory(this, new File(folder, path + ".toml"));
        }

        @Override
        public @NotNull PreparedConfigurationFactory createPreparedFactory(@NotNull File file) {
            return new PreparedConfigurationFactory(this, file);
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

    /**
     * Used to create a prepared factory of a configuration file.
     * This can then be used to create the configuration instance later.
     *
     * @param path The location of this file without extensions.
     * @return The prepared configuration factory.
     */
    public abstract @NotNull PreparedConfigurationFactory createPreparedFactory(@NotNull String path);

    /**
     * Used to create a prepared factory of a configuration file.
     * This can then be used to create the configuration instance later.
     *
     * @param folder The instance of the folder containing the file.
     * @param path   The path from the folder to the file without the extensions.
     * @return The prepared configuration factory.
     */
    public abstract @NotNull PreparedConfigurationFactory createPreparedFactory(@NotNull File folder, @NotNull String path);

    /**
     * Used to create a prepared factory of a configuration file.
     * This can then be used to create the configuration instance later.
     *
     * @param file The instance of the config file.
     * @return The prepared configuration factory.
     */
    public abstract @NotNull PreparedConfigurationFactory createPreparedFactory(@NotNull File file);
}