package com.github.smuddgge.squishyconfiguration.utility;

import com.github.smuddgge.squishyconfiguration.PreparedConfigurationFactory;
import com.github.smuddgge.squishyconfiguration.console.Console;
import com.github.smuddgge.squishyconfiguration.interfaces.Configuration;
import com.github.smuddgge.squishyconfiguration.results.ResultChecker;
import com.github.smuddgge.squishyconfiguration.results.types.ResultNotNull;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a tester for getting values
 * from the configuration instance.
 */
public class DataTypeConfigurationTester extends ConfigurationCreator implements ConfigurationTester {

    /**
     * Used to create a configuration creator.
     *
     * @param factory The instance of the prepared
     *                configuration factory.
     */
    public DataTypeConfigurationTester(@NotNull PreparedConfigurationFactory factory) {
        super(factory);
    }

    @Override
    public void testAll() {
        this.testObjects();
        this.testStrings();
    }

    /**
     * Used to test getting and setting objects.
     */
    public void testObjects() {
        Configuration configuration = this.create();
        configuration.load();

        Object object = "testObjects";
        configuration.set("testObjects", object);

        configuration.save();
        Configuration testLoad = this.create();
        testLoad.load();

        new ResultChecker()
                .fallBack(() -> Console.log("&eFailed &7Objects"))
                .expect(testLoad.get("testObjects"), new ResultNotNull())
                .expect(testLoad.get("testObjects"), "testObjects")
                .then(() -> Console.log("&aPassed &7Objects"));
    }

    /**
     * Used to test getting and setting strings.
     */
    public void testStrings() {
        Configuration configuration = this.create();
        configuration.load();

        configuration.set("testStrings", "testStrings");

        configuration.save();
        Configuration testLoad = this.create();
        testLoad.load();

        new ResultChecker()
                .fallBack(() -> Console.log("&eFailed &7Strings"))
                .expect(testLoad.get("testStrings"), new ResultNotNull())
                .expect(testLoad.get("testStrings"), "testStrings")
                .then(() -> Console.log("&aPassed &7Strings"));
    }
}
