package com.github.smuddgge.squishyyaml.implementation.toml;

/**
 * Represents a yaml configuration file
 */
public class TomlConfiguration extends MemoryConfigurationSection implements Configuration {

    /**
     * Represents the configuration file instance
     */
    private File file;

    /**
     * Represents the parent folder location
     */
    private final File folder;

    /**
     * The path within the parent folder
     */
    private final String path;

    /**
     * Used to create a representation of a configuration file
     *
     * @param file Instance of the configuration file
     */
    public TomlConfiguration(File file) {
        super(new HashMap<>());
        this.file = file;

        this.folder = file.getParentFile();
        this.path = file.getName();
    }

    /**
     * Used to create a representation of a configuration file
     *
     * @param folder The instance of the folder location
     * @param path   The path within the folder
     *               You should include the extension name
     */
    public TomlConfiguration(File folder, String path) {
        super(new HashMap<>());

        this.folder = folder;
        this.path = path;
    }

    @Override
    public String getAbsolutePath() {
        if (this.path == null || this.path.equals("")) {
            return this.folder.getAbsolutePath();
        }

        return this.folder.getAbsolutePath() + "/" + this.path;
    }

    @Override
    public boolean load() {
        this.file = new File(this.getAbsolutePath());

        // Check if the folder doesn't exist
        if (!this.file.getParentFile().exists()) {
            boolean sucsess = this.file.getParentFile().mkdirs();

            if (!sucsess) return false;
        }

        // Check if the file doesn't exist
        if (!this.file.exists()) {
            try {

                boolean success = this.file.createNewFile();

                if (!success) return false;

            } catch (IOException exception) {
                exception.printStackTrace();
                return false;
            }
        }

        // Load the file content
        Toml toml = new Toml().read(this.file);
        this.data = toml.toMap();

        return true;
    }

    @Override
    public boolean save() {
        TomlWriter tomlWriter = new TomlWriter();
        tomlWriter.write(this.data, this.file);

        return true;
    }
}