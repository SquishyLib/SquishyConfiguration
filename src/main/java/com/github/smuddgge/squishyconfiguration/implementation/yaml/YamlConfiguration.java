package com.github.smuddgge.squishyconfiguration.implementation.yaml;

import com.github.smuddgge.squishyconfiguration.memory.MemoryConfigurationSection;
import com.github.smuddgge.squishyconfiguration.interfaces.Configuration;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;

import static org.yaml.snakeyaml.DumperOptions.FlowStyle.BLOCK;

/**
 * Represents a yaml configuration file.
 */
public class YamlConfiguration extends MemoryConfigurationSection implements Configuration {

    private @NotNull File file;

    /**
     * Used to create a representation of a configuration file.
     *
     * @param file The instance of the configuration file.
     */
    public YamlConfiguration(@NotNull File file) {
        super(new HashMap<>());
        this.file = file;
    }

    /**
     * Used to create a representation of a configuration file.
     *
     * @param folder The instance of the folder location.
     * @param path   The path within the folder including the extension name.
     */
    public YamlConfiguration(@NotNull File folder, @NotNull String path) {
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
    @SuppressWarnings("all")
    public boolean load() {
        this.file = new File(this.getAbsolutePath());

        // Check if the folder doesn't exist
        if (!this.file.getParentFile().exists()
                && !this.file.getParentFile().mkdirs()) {

            return false;
        }

        // Check if the file doesn't exist
        // and attempt to create it.
        if (!this.file.exists() & !this.createFile()) return false;

        // Load the file content.
        try (InputStream inputStream = Files.newInputStream(this.file.toPath())) {

            // Load the data.
            Yaml yaml = new Yaml();
            this.data = yaml.load(inputStream);

            // Check if the data is null.
            if (this.data == null) this.data = new HashMap<>();
            return true;

        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    @Override
    @SuppressWarnings("all")
    public boolean save() {
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setPrettyFlow(true);
        dumperOptions.setDefaultFlowStyle(BLOCK);

        // Create a yaml object.
        Yaml yaml = new Yaml(dumperOptions);

        try {

            // Write to the file.
            FileWriter writer = new FileWriter(this.file);
            yaml.dump(this.data, writer);

        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }

        return true;
    }
}
