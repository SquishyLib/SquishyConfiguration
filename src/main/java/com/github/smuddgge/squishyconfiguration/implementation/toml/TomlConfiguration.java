package com.github.smuddgge.squishyconfiguration.implementation.toml;

import com.github.smuddgge.squishyconfiguration.memory.MemoryConfigurationSection;
import com.github.smuddgge.squishyconfiguration.interfaces.Configuration;
import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Represents a toml configuration file.
 */
public class TomlConfiguration extends MemoryConfigurationSection implements Configuration {

    private File file;

    /**
     * Used to create a representation of a configuration file.
     *
     * @param file Instance of the configuration file.
     */
    public TomlConfiguration(File file) {
        super(new HashMap<>());
        this.file = file;
    }

    /**
     * Used to create a representation of a configuration file.
     *
     * @param folder The instance of the folder location.
     * @param path   The path within the folder including the extension name.
     */
    public TomlConfiguration(File folder, String path) {
        super(new HashMap<>());
        this.file = new File(folder.getAbsolutePath() + File.separator + path);
    }

    @Override
    public @NotNull String getAbsolutePath() {
        return this.file.getAbsolutePath();
    }

    @Override
    public @NotNull File getFile() {
        return this.file;
    }

    @Override
    public boolean load() {

        // Check if the folder doesn't exist.
        if (!this.file.getParentFile().exists()
                && !this.file.getParentFile().mkdirs()) {

            return false;
        }

        // Check if the file doesn't exist
        // and attempt to create it.
        if (!this.file.exists() & !this.createFile()) return false;

        // Load the file content
        Toml toml = new Toml().read(this.file);
        this.data = toml.toMap();
        return true;
    }

    @Override
    @SuppressWarnings("all")
    public boolean save() {
        try {

            // Write to the file.
            TomlWriter tomlWriter = new TomlWriter();
            tomlWriter.write(this.data, this.file);
            return true;

        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }
}