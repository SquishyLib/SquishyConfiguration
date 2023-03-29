package com.github.smuddgge.squishyyaml;

import com.github.smuddgge.squishyyaml.interfaces.Configuration;

/**
 * <h1>Represents the configuration factory</h1>
 * Used to create a configuration file.
 */
public enum ConfigurationFactory {
    YAML {
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
     * @param folder The instance of the parent folder.
     * @param path The location in the file with the exstention.
     * @return The instance of a new database.
     */
    public abstract Configuration create(File folder, String path);

    /**
     * Used to create a configuration file.
     *
     * @param file The instance of the file.
     * @return The instance of a new database.
     */
    public abstract Configuration create(File file);
}