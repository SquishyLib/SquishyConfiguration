package com.github.smuddgge.squishyyaml;

import org.junit.jupiter.api.Test;

import java.io.File;

public class TestYamlConfiguration {

    @Test
    public void test() {
        YamlConfiguration configuration = new YamlConfiguration(new File("src/main/resources"), "test.yaml");
        configuration.load();
        configuration.set(null);
        configuration.save();

        configuration.getSection("test.test").set("testing");

        System.out.println(configuration.get("test.test"));

        configuration.save();
    }
}
