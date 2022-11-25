package com.github.smuddgge.squishyyaml;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a yaml configuration section
 *
 * <ul>
 *   <li>Base Section: The instance of the root configuration file section</li>
 *   <li>Section: A section of configuration</li>
 * </ul>
 */
public class YamlConfigurationSection implements ConfigurationSection {

    /**
     * Represents the base configuration section
     */
    protected final ConfigurationSection baseSection;

    /**
     * Represents this configuration section
     */
    protected Map<String, Object> data;

    /**
     * Represents the location of the section in the base section
     */
    protected final String rootPath;

    /**
     * Used to create a new base configuration section
     *
     * @param base The data located in the base configuration section
     */
    public YamlConfigurationSection(Map<String, Object> base) {
        this.data = base;
        this.baseSection = this;
        this.rootPath = null;
    }

    /**
     * Used to create a new configuration section that represents
     * a subsection of the config file
     *
     * @param base The data of the base configuration section
     * @param path The location of the subsection in the base section
     */
    public YamlConfigurationSection(Map<String, Object> base, String path) {
        this.baseSection = new YamlConfigurationSection(base);
        this.rootPath = path;

        this.data = base;
        this.data = this.getSection(path).getData();
    }

    /**
     * Used to create a new configuration section that represents
     * a subsection of the config file
     *
     * @param base The data of the base configuration section
     * @param path The location of the subsection in the base section
     * @param data The data located in the subsection
     */
    public YamlConfigurationSection(Map<String, Object> base, String path, Map<String, Object> data) {
        this.baseSection = new YamlConfigurationSection(base);
        this.rootPath = path;
        this.data = data;
    }

    @Override
    public Map<String, Object> getData() {
        return this.data;
    }

    @Override
    public String getRootPath() {
        return this.rootPath;
    }

    @Override
    public String getRootPath(String path) {
        if (this.rootPath == null) return path;
        if (path == null) return this.rootPath;

        return this.rootPath + "." + path;
    }

    @Override
    public void set(Object value) {
        this.baseSection.setInSection(this.getRootPath(), value);
    }

    @Override
    public void set(String path, Object value) {
        this.baseSection.setInSection(this.getRootPath(path), value);
    }

    @Override
    public void setInSection(String path, Object value) {
        // If the value is located in a subsection
        if (path != null && path.contains(".")) {
            // Get the child path
            String key = path.split("\\.")[0];
            // Get the remaining children
            String remainingPath = path.substring(key.length() + 1);

            // Create the section if it doesn't exist
            if (this.getSection(key) == null) {
                this.data.put(key, new HashMap<String, Object>());
            }

            // Get the subsection
            YamlConfigurationSection section = new YamlConfigurationSection(
                    this.baseSection.getData(),
                    getRootPath(key),
                    this.getSection(key).getData()
            );

            // Set the value in the section
            section.setInSection(remainingPath, value);

            // Get the updated section and update this section to the new data
            this.data.put(key, section.getData());
            return;
        }

        // If the value is being set to null
        if (value == null) {

            // If the path location is in this section
            if (path == null) {
                this.data = new HashMap<>();
                return;
            }

            this.data.remove(path);
            return;
        }

        this.data.put(path, value);
    }

    @Override
    public Object get(String path) {
        if (path.contains(".")) {
            // Get the child path
            String key = path.split("\\.")[0];
            // Get the remaining children
            String remainingPath = path.substring(key.length() + 1);

            // Create the section if it doesn't exist
            if (this.getSection(key) == null) {
                this.data.put(key, new HashMap<String, Object>());
            }

            return this.getSection(key).get(remainingPath);
        }

        return this.data.get(path);
    }

    @Override
    public ConfigurationSection getSection(String path) {
        if (path == null) return this;

        // Return a new empty section if it isn't a section
        if (!(this.get(path) instanceof Map)) {
            return new YamlConfigurationSection(this.baseSection.getData(), this.getRootPath(path), new HashMap<>());
        }

        Map<?, ?> unknownMap = (Map<?, ?>) this.get(path);
        Map<String, Object> knownMap = new HashMap<>();

        for (Map.Entry<?, ?> entry : unknownMap.entrySet()) {
            knownMap.put((String) entry.getKey(), entry.getValue());
        }

        return new YamlConfigurationSection(this.baseSection.getData(), this.getRootPath(path), knownMap);
    }
}
