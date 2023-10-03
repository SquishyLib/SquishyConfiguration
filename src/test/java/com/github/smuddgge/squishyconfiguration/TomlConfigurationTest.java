package com.github.smuddgge.squishyconfiguration;

import com.github.smuddgge.squishyconfiguration.interfaces.Configuration;
import com.github.smuddgge.squishyconfiguration.utility.AllConfigurationTester;
import com.github.smuddgge.squishyconfiguration.utility.ConfigurationTester;
import org.junit.jupiter.api.Test;

import java.io.File;

public class TomlConfigurationTest {

    @Test
    public void testToml() {
        ConfigurationTester tester = new AllConfigurationTester(
                ConfigurationFactory.TOML.createPreparedFactory(
                        new File("src/main/resources"), "test"
                )
        );

        tester.testAll();
    }
}
