package com.github.smuddgge.squishyconfiguration;

import com.github.smuddgge.squishyconfiguration.implementation.yaml.YamlConfiguration;
import com.github.smuddgge.squishyconfiguration.interfaces.Configuration;

import java.io.File;

public class Test {

    @org.junit.jupiter.api.Test
    public void test() {
        // Creating a configuration factory.
        ConfigurationFactory factory = ConfigurationFactory.YAML;
        Configuration configuration = factory.create(
                new File("src/main/resources"), "test4"
        );

        // Load the configuration.
        configuration.load();

        // Setting a list.
        configuration.set("list", new int[]{2, 4, 6, 8});

        for (int number : configuration.getListInteger("list")) {
            System.out.println(number);
        }

        // Save the configuration to the file.
        configuration.save();
    }

    @org.junit.jupiter.api.Test
    public void test2() {
        Configuration configuration = new YamlConfiguration(
                new File("src/main/resources"),
                "test2.yaml"
        );

        configuration.load();
        configuration.set("test", "test");
        configuration.save();
    }
}
