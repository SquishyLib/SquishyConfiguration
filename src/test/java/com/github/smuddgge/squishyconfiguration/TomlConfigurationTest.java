package com.github.smuddgge.squishyconfiguration;

import com.github.smuddgge.squishyconfiguration.implementation.toml.TomlConfiguration;
import com.github.smuddgge.squishyconfiguration.implementation.yaml.YamlConfiguration;
import com.github.smuddgge.squishyconfiguration.interfaces.Configuration;
import org.junit.jupiter.api.Test;

import java.io.File;

public class TomlConfigurationTest {

    public static Configuration getConfiguration() {
        ConfigurationFactory configurationFactory = ConfigurationFactory.TOML;
        return configurationFactory.create(new File("src/main/resources"), "test");
    }

    @Test
    public void testGetKeys() throws Exception {
        Configuration configuration = TomlConfigurationTest.getConfiguration();

        configuration.load();
        configuration.set("parent.child1", "string");
        configuration.set("parent.child2", "string");
        configuration.save();

        TomlConfiguration configuration2 = new TomlConfiguration(new File("src/main/resources"), "test.toml");
        configuration2.load();

        for (String key : configuration2.getKeys("parent")) {
            if (!key.contains("child")) {
                throw new Exception();
            }
        }
    }
}
