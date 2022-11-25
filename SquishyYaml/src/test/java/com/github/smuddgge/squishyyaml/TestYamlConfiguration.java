package com.github.smuddgge.squishyyaml;

import org.junit.jupiter.api.Test;

import java.io.File;

public class TestYamlConfiguration {

    @Test
    public void testLoadAndSave() throws Exception {
        YamlConfiguration configuration = new YamlConfiguration(new File("src/main/resources"), "test.yaml");

        configuration.load();
        configuration.set("test", 1);
        configuration.save();

        YamlConfiguration configuration2 = new YamlConfiguration(new File("src/main/resources"), "test.yaml");
        configuration2.load();

        if ((int) configuration2.get("test") != 1) {
            throw new Exception();
        }
    }
}
