package com.github.smuddgge.squishyconfiguration;

import com.github.smuddgge.squishyconfiguration.implementation.yaml.YamlConfiguration;
import com.github.smuddgge.squishyconfiguration.interfaces.Configuration;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class YamlConfigurationTest {

    public static Configuration getConfiguration() {
        ConfigurationFactory configurationFactory = ConfigurationFactory.YAML;
        return configurationFactory.create(new File("src/main/resources"), "test.yaml");
    }

    @Test
    public void testGetKeys() throws Exception {
        Configuration configuration = YamlConfigurationTest.getConfiguration();

        configuration.load();
        configuration.set("parent.child1", "string");
        configuration.set("parent.child2", "string");
        configuration.save();

        YamlConfiguration configuration2 = new YamlConfiguration(new File("src/main/resources"), "test.yaml");
        configuration2.load();

        for (String key : configuration2.getKeys("parent")) {
            if (!key.contains("child")) {
                throw new Exception();
            }
        }
    }

    @Test
    public void testGetListString() throws Exception {
        Configuration configuration = YamlConfigurationTest.getConfiguration();

        configuration.load();
        configuration.set("listString", new String[]{"string", "string"});
        configuration.save();

        YamlConfiguration configuration2 = new YamlConfiguration(new File("src/main/resources"), "test.yaml");
        configuration2.load();

        List<String> list = new ArrayList<>();
        list.add("string");
        list.add("string");

        if (!Objects.equals(configuration2.getListString("listString"), list)) {
            throw new Exception();
        }
    }

    @Test
    public void testGetString() throws Exception {
        Configuration configuration = YamlConfigurationTest.getConfiguration();

        configuration.load();
        configuration.set("string", "string");
        configuration.save();

        YamlConfiguration configuration2 = new YamlConfiguration(new File("src/main/resources"), "test.yaml");
        configuration2.load();

        if (!Objects.equals(configuration2.getString("string"), "string")) {
            throw new Exception();
        }
    }

    @Test
    public void testGetInteger() throws Exception {
        Configuration configuration = YamlConfigurationTest.getConfiguration();

        configuration.load();
        configuration.set("integer", 1);
        configuration.save();

        Configuration configuration2 = YamlConfigurationTest.getConfiguration();
        configuration2.load();

        if (!Objects.equals(configuration2.getInteger("integer"), 1)) {
            throw new Exception();
        }
    }
}