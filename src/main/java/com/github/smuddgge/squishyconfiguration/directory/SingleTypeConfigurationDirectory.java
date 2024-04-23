package com.github.smuddgge.squishyconfiguration.directory;

import com.github.smuddgge.squishyconfiguration.implementation.YamlConfiguration;
import com.github.smuddgge.squishyconfiguration.indicator.ConfigurationConvertable;
import com.github.smuddgge.squishyconfiguration.interfaces.Configuration;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A configuration directory that only holds a
 * single type of configuration convertable class.
 * <p>
 * For example if the directory is only used for
 * storing configuration for commands. This can be
 * used to quickly get the basic methods for getting
 * and inserting commands in the configuration directory.
 *
 * @param <T> The convertable object this directory will
 *            be containing instances of.
 */
public abstract class SingleTypeConfigurationDirectory<T extends ConfigurationConvertable<T>> extends ConfigurationDirectory {

    /**
     * Used to create a new instance of a single type configuration directory.
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
    public SingleTypeConfigurationDirectory(@NotNull String directoryName, @NotNull Class<?> resourceClass) {
        super(directoryName, resourceClass);
    }

    /**
     * Used to create a new instance of a single type configuration directory.
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
    public SingleTypeConfigurationDirectory(@NotNull String parentPath, @NotNull String directoryName, @NotNull Class<?> resourceClass) {
        super(parentPath, directoryName, resourceClass);
    }

    /**
     * Used to create a new instance of a single type configuration directory.
     * <p>
     * The name of the directory will be set to the file's
     * name without the extensions.
     *
     * @param directory     The instance of the directory as a file.
     * @param resourceClass A main class in the module.
     *                      This class will be used to get the resource
     *                      files, with {@link Class#getResourceAsStream(String)}.
     */
    public SingleTypeConfigurationDirectory(@NotNull File directory, @NotNull Class<?> resourceClass) {
        super(directory, resourceClass);
    }

    /**
     * Used to create an empty instance of the
     * configuration convertable object.
     *
     * @param identifier The identifier to use.
     * @return The instance of the configuration convertable object.
     */
    public abstract @NotNull T createEmpty(@NotNull String identifier);

    /**
     * Used to get a type object from the configuration.
     *
     * @param identifier The object's identifier.
     * @return The instance of the object.
     * Empty if it isn't in the configuration directory.
     */
    public @NotNull Optional<T> getType(@NotNull String identifier) {

        // Check if the type exists.
        if (this.getKeys().contains(identifier)) {
            return Optional.of(this.createEmpty(identifier).convert(this.getSection(identifier)));
        }

        // Otherwise the object does not exist.
        return Optional.empty();
    }

    /**
     * Used to get the list of all the types in
     * the configuration directory.
     *
     * @return The list of types.
     */
    public @NotNull List<T> getAllTypes() {
        List<T> typeList = new ArrayList<>();

        for (String identifier : this.getKeys()) {
            typeList.add(this.getType(identifier).orElseThrow());
        }

        return typeList;
    }

    /**
     * Used to get the configuration instance that should
     * be used with a specific identifier.
     * <p>
     * If the identifier already exists, it will return
     * that configuration file instance. Otherwise, it
     * will add the object to one of the configuration
     * files that already exist. If there are no configuration
     * files, it will create a new one.
     * <p>
     * The configuration instance will be loaded.
     *
     * @param identifier The identifier of the object.
     * @return The configuration instance.
     */
    public @NotNull Configuration getConfigurationForIdentifier(@NotNull String identifier) {

        // Get the local configuration file.
        Optional<Configuration> optionalConfiguration = this.getConfigurationWithKey(identifier);

        // Check if the configuration file already exist.
        if (optionalConfiguration.isPresent()) {
            return optionalConfiguration.get();
        }

        // Check if there is a configuration file that can be used.
        if (!this.hasNoConfigurationFiles()) {
            YamlConfiguration configuration = new YamlConfiguration(this.getFiles().get(0));
            configuration.load();
            return configuration;
        }

        // Otherwise, create a new configuration instance.
        YamlConfiguration configuration = new YamlConfiguration(this.getDirectory(), "default.yml");
        configuration.load();
        return configuration;
    }

    /**
     * Used to insert a type into the directory.
     * <p>
     * If the identifier already exists, it will update that converted object.
     * Otherwise, it will add the object to one of the configuration files.
     * If there are no configuration files, it will create a new one.
     *
     * @param identifier The instance of the identifier.
     * @param type       The type to insert.
     * @return This instance.
     */
    public @NotNull SingleTypeConfigurationDirectory<T> insertType(@NotNull String identifier, @NotNull T type) {

        // Get the instance of the configuration to use.
        Configuration configuration = this.getConfigurationForIdentifier(identifier);
        configuration.set(identifier, type.convert().getMap());
        configuration.save();

        // Reload the directory.
        this.reload();
        return this;
    }

    /**
     * Used to remove a type from the configuration directory.
     *
     * @param identifier The instance of the identifier.
     * @return This instance.
     */
    public @NotNull SingleTypeConfigurationDirectory<T> removeType(@NotNull String identifier) {

        // Get the local configuration file.
        Optional<Configuration> optionalConfiguration = this.getConfigurationWithKey(identifier);

        // Check if the configuration exists.
        if (optionalConfiguration.isEmpty()) return this;
        Configuration configuration = optionalConfiguration.get();

        // Remove the identifier and data.
        configuration.set(identifier, null);
        configuration.save();

        // Reload the directory.
        this.reload();
        return this;
    }

    /**
     * Used to check if the configuration directory
     * contains a certain identifier.
     *
     * @param identifier The identifier to check for.
     * @return True if it exists in the configuration directory.
     */
    public boolean contains(@NotNull String identifier) {
        return this.getKeys().contains(identifier);
    }
}
