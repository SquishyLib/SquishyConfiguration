package com.github.smuddgge.squishyconfiguration.interfaces;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

/**
 * Represents a configuration file interface.
 */
public interface Configuration extends ConfigurationSection {

    /**
     * Used to get the absolute path.
     * Combines the absolute path from the folder and path.
     *
     * @return The absolute path.
     */
    @NotNull String getAbsolutePath();

    /**
     * Used to get the instance of the file.
     *
     * @return The instance of the file.
     */
    @NotNull File getFile();

    /**
     * Used to get the file name without extensions.
     *
     * @return The file's name.
     */
    default @NotNull String getFileName() {
        return this.getFile().getName().split("\\.")[0];
    }

    /**
     * Used to get the file's extension.
     * <li>For example: "yaml"</li>
     *
     * @return The file extension.
     */
    default @NotNull String getFileExtension() {
        return this.getFile().getName().split("\\.")[1];
    }

    /**
     * Used to load the configuration file to the
     * class instance.
     *
     * @return True if successful.
     */
    boolean load();

    /**
     * Used to save the instance of the data to
     * the configuration file.
     *
     * @return True if successful.
     */
    boolean save();

    /**
     * Used to attempt to create the file.
     *
     * @return True if a file was created.
     */
    @SuppressWarnings("all")
    default boolean createFile() {
        try {

            // Attempt to create a new file.
            return this.getFile().createNewFile();

        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }
}
