/*
 * CozyGamesAPI - The api used to interface with the cozy game system.
 * Copyright (C) 2024 Smuddgge
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.github.smuddgge.squishyconfiguration.directory;

import com.github.smuddgge.squishyconfiguration.implementation.TomlConfiguration;
import com.github.smuddgge.squishyconfiguration.implementation.YamlConfiguration;
import com.github.smuddgge.squishyconfiguration.interfaces.Configuration;
import com.github.smuddgge.squishyconfiguration.interfaces.ConfigurationSection;
import com.github.smuddgge.squishyconfiguration.memory.MemoryConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

/**
 * A directory that contains configuration files.
 * <p>
 * This is used to view all the configuration files
 * in the directory as one big configuration directory.
 * <p>
 * Unfortunately there isn't a way to save changed data.
 * However, you can get the instance of the specific configuration
 * file where a key is located.
 */
public class ConfigurationDirectory extends MemoryConfigurationSection {

    /**
     * The file extension used to store data about the
     * configuration files in the configuration directory.
     * <p>
     * Files with this extension will not be parsed when the
     * {@link ConfigurationDirectory#reload()} method is called.
     * <pre>{@code
     * DATA_FILE_EXTENSION = ".squishystore"
     * }</pre>
     */
    public static final @NotNull String DATA_FILE_EXTENSION = ".squishystore";

    private final @NotNull File directory;
    private final @NotNull String directoryName;

    private final @NotNull List<String> resourcePaths;
    private final @NotNull Class<?> resourceClass;

    private final @NotNull List<Listener> listenerList;

    /**
     * Used to create a new instance of a configuration directory.
     * <p>
     * The path to the directory will just be the directory's name.
     * <pre>{@code
     * /<directoryName>
     * }</pre>
     *
     * @param directoryName The name of the directory.
     * @param resourceClass A main class in the module.
     *                      This class will be used to get the resource
     *                      files, with {@link Class#getResourceAsStream(String)}.
     */
    public ConfigurationDirectory(@NotNull String directoryName, @NotNull Class<?> resourceClass) {
        super(new LinkedHashMap<>());

        this.directory = new File(directoryName);
        this.directoryName = directoryName;
        this.resourcePaths = new ArrayList<>();
        this.resourceClass = resourceClass;
        this.listenerList = new ArrayList<>();
    }

    /**
     * Used to create a new instance of a configuration directory.
     * <p>
     * The parent path is combined with the directory's name to create the path.
     * <pre>{@code
     * /<parentPath>/<directoryName>
     * }</pre>
     *
     * @param parentPath    The parent path.
     * @param directoryName The directory's name.
     * @param resourceClass A main class in the module.
     *                      This class will be used to get the resource
     *                      files, with {@link Class#getResourceAsStream(String)}.
     */
    public ConfigurationDirectory(@NotNull String parentPath, @NotNull String directoryName, @NotNull Class<?> resourceClass) {
        super(new LinkedHashMap<>());

        this.directory = new File(parentPath + File.separator + directoryName);
        this.directoryName = directoryName;
        this.resourcePaths = new ArrayList<>();
        this.resourceClass = resourceClass;
        this.listenerList = new ArrayList<>();
    }

    /**
     * Used to create a new instance of a configuration directory.
     * <p>
     * The name of the directory will be set to the file's
     * name without the extensions.
     *
     * @param directory     The instance of the directory as a file.
     * @param resourceClass A main class in the module.
     *                      This class will be used to get the resource
     *                      files, with {@link Class#getResourceAsStream(String)}.
     */
    public ConfigurationDirectory(@NotNull File directory, @NotNull Class<?> resourceClass) {
        super(new LinkedHashMap<>());

        this.directory = directory;
        this.directoryName = directory.getName().split("\\.")[0];
        this.resourcePaths = new ArrayList<>();
        this.resourceClass = resourceClass;
        this.listenerList = new ArrayList<>();
    }

    /**
     * The configuration directory exception.
     * <p>
     * Called when an exception occurs within this class.
     */
    private class ConfigurationDirectoryException extends RuntimeException {

        /**
         * Used to create a default configuration
         * directory exception.
         */
        public ConfigurationDirectoryException() {
            super("Error occurred in configuration directory with name '" + directoryName + "'.");
        }

        /**
         * Used to create a configuration directory exception
         * with a specific message.
         *
         * @param message The instance of the message.
         */
        public ConfigurationDirectoryException(String message) {
            super("Error occurred in configuration directory with name '" + directoryName + "'.\n" + message);
        }
    }

    /**
     * Represents a configuration directory listener.
     * <p>
     * Used to listen to configuration directory events.
     */
    public interface Listener {

        /**
         * Called when the configuration directory
         * is fully reloaded.
         *
         * @param directory The directory instance.
         */
        void onReload(@NotNull ConfigurationDirectory directory);
    }

    public @NotNull File getDirectory() {
        return this.directory;
    }

    /**
     * Used to get the directory within this directory given a path.
     * <p>
     * Example path:
     * <pre>{@code
     * untitled_folder.untitled_folder_2
     * }</pre>
     * <p>
     * Returns empty if the folder doesn't exist.
     *
     * @param path The dot path.
     * @return The folder instance.
     */
    public @NotNull Optional<File> getDirectory(@Nullable String path) {
        return Optional.ofNullable(this.getDirectory0(this.getDirectory(), path));
    }

    private @Nullable File getDirectory0(@NotNull File file, @Nullable String path) {
        if (path == null || path.equals("")) return file;

        // Check if there is a dot at the start.
        if (path.startsWith(".")) path = path.substring(1);

        String folderName = path.split("\\.")[0];
        File[] fileList = file.listFiles();
        if (fileList == null) return null;

        for (File temp : fileList) {
            if (!temp.getName().equals(folderName)) continue;
            return this.getDirectory0(temp, path.substring(folderName.length()));
        }

        return null;
    }

    public @NotNull String getDirectoryName() {
        return this.directoryName;
    }

    /**
     * The list of default resource file paths.
     * <p>
     * Resources in the list will be loaded if the directory
     * is empty and the {@link ConfigurationDirectory#reload()}
     * method is called.
     * <p>
     * The path is from the resource folder.
     *
     * @return The list of resource names.
     */
    public @NotNull List<String> getResourcePaths() {
        return this.resourcePaths;
    }

    /**
     * The class instance that is used to load
     * the resource files.
     *
     * @return The resource class instance.
     */
    public @NotNull Class<?> getResourceClass() {
        return this.resourceClass;
    }

    /**
     * Used to get the list of this directory's listeners.
     *
     * @return The list of configuration directory listeners.
     */
    public @NotNull List<Listener> getListeners() {
        return this.listenerList;
    }

    /**
     * Used to get the instance of the directories data store file.
     * <p>
     * If the file doesn't exist it will create it.
     * <p>
     * This configuration file can be used to store data about
     * this specific configuration directory.
     *
     * @return The data store file.
     */
    public @NotNull Configuration getDataStore() {

        // Loop for the directories data store file.
        for (File file : this.getFiles()) {
            if (!file.getName().endsWith(ConfigurationDirectory.DATA_FILE_EXTENSION)) continue;
            YamlConfiguration configuration = new YamlConfiguration(file);
            configuration.load();
            return configuration;
        }

        // Otherwise, create the data store file.
        YamlConfiguration configuration = new YamlConfiguration(
                this.getDirectory(),
                ConfigurationDirectory.DATA_FILE_EXTENSION
        );
        configuration.load();
        return configuration;
    }

    /**
     * Used to get the list of files in this directory.
     * <p>
     * This will also return the file's in each folder
     * of the directory.
     * <p>
     * However, this will ignore any files begining with
     * a dot, or if the file is a data store file.
     *
     * @return The file's in the directory.
     */
    public @NotNull List<File> getFiles() {
        return this.getFiles0(this.getDirectory());
    }

    private @NotNull List<File> getFiles0(@NotNull File folder) {
        File[] fileList = folder.listFiles();
        if (fileList == null) return new ArrayList<>();

        List<File> finalFileList = new ArrayList<>();
        for (File file : fileList) {

            // Ignore system files.
            if (file.getName().startsWith(".")) continue;
            if (file.getName().endsWith(ConfigurationDirectory.DATA_FILE_EXTENSION)) continue;

            List<File> filesInFile = this.getFiles0(file);
            if (filesInFile.isEmpty()) {
                finalFileList.add(file);
                continue;
            }

            finalFileList.addAll(filesInFile);
        }

        return finalFileList;
    }

    /**
     * Used to get the names of the file's from the
     * {@link ConfigurationDirectory#getFiles()} method
     * with extensions.
     *
     * @return The list of file name's with extensions.
     */
    public @NotNull List<String> getFileNames() {
        return this.getFiles().stream().map(File::getName).toList();
    }

    /**
     * Used to get the names of the file's from the
     * {@link ConfigurationDirectory#getFiles()} method
     * and then remove the file extensions.
     *
     * @return The list of file name's without extensions.
     */
    public @NotNull List<String> getFileNamesWithoutExtension() {
        return this.getFiles().stream()
                .map(file -> file.getName().split("\\.")[0])
                .toList();
    }

    /**
     * Used to get the configuration file that contains
     * a certain key.
     * <p>
     * This is used to save data as you cannot save data
     * in a configuration directory.
     * <p>
     * The instance of the configuration will be loaded.
     *
     * @param key The key to look for.
     * @return The configuration file instance.
     * Empty if the key doesn't exist.
     */
    public @NotNull Optional<Configuration> getConfigurationWithKey(@NotNull String key) {
        for (File file : this.getFiles()) {
            YamlConfiguration configuration = new YamlConfiguration(file);
            configuration.load();
            if (configuration.getKeys().contains(key)) return Optional.of(configuration);
        }

        return Optional.empty();
    }

    /**
     * Used to add a new default resource path.
     * <p>
     * Resources added to this list will be loaded if the directory
     * is empty and the {@link ConfigurationDirectory#reload()}
     * method is called.
     * <p>
     * The path is from the resource folder.
     *
     * @param path The resource file's name.
     * @return This instance.
     */
    public @NotNull ConfigurationDirectory addResourcePath(@NotNull String path) {
        this.resourcePaths.add(path);
        return this;
    }

    /**
     * Used to add a new default resource file names.
     * <p>
     * Resources added to this list will be loaded if the directory
     * is empty and the {@link ConfigurationDirectory#reload()}
     * method is called.
     * <p>
     * The path is from the resource folder.
     *
     * @param pathList The list of file names.
     * @return This instance.
     */
    public @NotNull ConfigurationDirectory addResourcePathList(@NotNull List<String> pathList) {
        this.resourcePaths.addAll(pathList);
        return this;
    }

    /**
     * Used to add a configuration directory listener
     * to this configuration directory.
     *
     * @param listener The instance of the listener.
     * @return This instance.
     */
    public @NotNull ConfigurationDirectory addListener(@NotNull Listener listener) {
        this.listenerList.add(listener);
        return this;
    }

    /**
     * Used to append a configuration section to
     * this configuration section.
     * <p>
     * This will override any duplicated keys.
     *
     * @param section The instance of the configuration section.
     * @return This instance.
     */
    public @NotNull ConfigurationDirectory appendConfiguration(@NotNull ConfigurationSection section) {
        this.data.putAll(section.getMap());
        return this;
    }

    /**
     * Used to append a configuration file to
     * this configuration section.
     * <p>
     * This will override any duplicated keys.
     * <p>
     * If the file is not a yaml toml or data store file, and
     * it doesn't start with a dot, an exception will be thrown.
     *
     * @param file The instance of the configuration file to append.
     * @return This instance.
     * @throws ConfigurationDirectoryException When a file is not a supported configuration file.
     */
    public @NotNull ConfigurationDirectory appendConfiguration(@NotNull File file) {

        // Check if the file is a yaml file.
        if (file.getName().endsWith(".yaml") || file.getName().endsWith(".yml")) {
            YamlConfiguration configuration = new YamlConfiguration(file);
            configuration.load();
            this.appendConfiguration(configuration);
            return this;
        }

        // Check if the file is a toml file.
        if (file.getName().endsWith(".toml")) {
            TomlConfiguration configuration = new TomlConfiguration(file);
            configuration.load();
            this.appendConfiguration(configuration);
            return this;
        }

        // Check if the file is a hidden file.
        if (file.getName().startsWith(".")) return this;

        // Check if the file is a data store.
        if (file.getName().endsWith(ConfigurationDirectory.DATA_FILE_EXTENSION)) return this;

        // Otherwise, the file is not supported.
        throw new ConfigurationDirectoryException(
                "Configuration file is not supported. The file should end in yaml, yml or toml. {file_name: " + file.getName() + "}"
        );
    }

    /**
     * Used to check if the directory is empty.
     * <p>
     * True if the directory has no files inside.
     *
     * @return True if the directory has no files inside.
     */
    public boolean isEmpty() {
        File[] files = this.directory.listFiles();
        if (files == null) return true;
        return files.length == 0;
    }

    /**
     * Used to check if the directory has no configuration files.
     * <p>
     * This will ignore any files beginning with a dot or if the
     * file is a data store file.
     *
     * @return True if there are no configuration files
     * in the directory.
     */
    public boolean hasNoConfigurationFiles() {
        return this.getFiles().isEmpty();
    }

    /**
     * Used to create the directory folder.
     * <p>
     * If the folder already exists, it will do nothing.
     *
     * @return This instance.
     */
    public @NotNull ConfigurationDirectory createDirectory() {
        this.directory.mkdirs();
        return this;
    }

    /**
     * Used to copy a resource file into the directory.
     *
     * @param resourcePath The path to the resource from the resource folder.
     * @return This instance.
     */
    public @NotNull ConfigurationDirectory createResource(@NotNull String resourcePath) {

        // Attempt to get the resource input stream.
        try (InputStream input = this.resourceClass.getResourceAsStream("/" + resourcePath)) {

            // Get the name of the file.
            final String name = resourcePath
                    .substring(resourcePath.lastIndexOf('/') + 1);

            // Create new file instance.
            File file = new File(this.getDirectory(), name);

            if (input != null) {
                Files.copy(input, file.toPath());
                return this;
            }

        } catch (IOException exception) {
            throw new ConfigurationDirectoryException("Error occurred while trying to create the resource " + resourcePath + ".");
        }

        return this;
    }

    /**
     * Used to create all the default resource files.
     * <p>
     * This will use the {@link ConfigurationDirectory#createResource(String)}
     * for each resource.
     *
     * @return This instance.
     */
    public @NotNull ConfigurationDirectory createResourceFiles() {

        for (String resourcePath : this.resourcePaths) {
            this.createResource(resourcePath);
        }

        return this;
    }

    /**
     * Used to reset the class's internal map and then load
     * the data from the configuration files located in the
     * directory into the internal map.
     * <p>
     * If the directory doesn't exist, it will be created.
     * <p>
     * If the directory is empty, the default resource files
     * will be created.
     *
     * @return This instance.
     */
    public @NotNull ConfigurationDirectory reload() {

        // Reset the configuration section data.
        this.data = new LinkedHashMap<>();

        // Attempt to create the directory.
        this.createDirectory();

        // Get the list of files in the directory.
        final List<File> files = this.getFiles();

        // Check if there are no files.
        if (files.isEmpty()) {
            this.createResourceFiles();

            for (File file : this.getFiles()) {
                this.appendConfiguration(file);
            }

            this.listenerList.forEach(listener -> listener.onReload(this));
            return this;
        }

        // Add all the files.
        files.forEach(this::appendConfiguration);
        this.listenerList.forEach(listener -> listener.onReload(this));
        return this;
    }
}
