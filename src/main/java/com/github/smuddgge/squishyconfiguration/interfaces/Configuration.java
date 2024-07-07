package com.github.smuddgge.squishyconfiguration.interfaces;

import com.github.smuddgge.squishyconfiguration.ConfigurationFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

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
     * Used to get the resource path from the
     * resource folder.
     * <li>Example: "folder/test.yml"</li>
     *
     * @return The default path.
     */
    @Nullable String getResourcePath();

    /**
     * Used to set the resource path.
     * This file will be loaded if the config
     * file does not exist.
     * <li>Example: "folder/test.yml"</li>
     *
     * @param path The path from the resource folder.
     * @return This instance.
     */
    @NotNull Configuration setResourcePath(@NotNull String path);

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

            String defaultPath = this.getResourcePath();
            if (defaultPath != null) {

                // Attempt to copy the configuration file.
                try (InputStream input = ConfigurationFactory.class.getResourceAsStream("/" + defaultPath)) {

                    if (input != null) Files.copy(input, this.getFile().toPath());
                    return true;

                } catch (IOException exception) {
                    exception.printStackTrace();
                    return false;
                }
            }

            // Attempt to create a new file.
            return this.getFile().createNewFile();

        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }
}
