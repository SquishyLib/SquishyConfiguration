package com.github.smuddgge.squishyconfiguration.utility;

import com.github.smuddgge.squishyconfiguration.PreparedConfigurationFactory;
import com.github.smuddgge.squishyconfiguration.console.Console;
import com.github.smuddgge.squishyconfiguration.interfaces.Configuration;
import com.github.smuddgge.squishyconfiguration.results.ResultChecker;
import com.github.smuddgge.squishyconfiguration.results.types.ResultNotNull;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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
        this.testLongs();
        this.testDoubles();
        this.testBooleans();
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
                .expect(testLoad.getString("testStrings"), new ResultNotNull())
                .expect(testLoad.getString("testStrings"), "testStrings")
                .then(() -> Console.log("&aPassed &7Strings"));
    }

    /**
     * Used to test getting and setting longs.
     */
    public void testLongs() {
        Configuration configuration = this.create();
        configuration.load();

        configuration.set("testLongs", 100L);

        configuration.save();
        Configuration testLoad = this.create();
        testLoad.load();

        new ResultChecker()
                .fallBack(() -> Console.log("&eFailed &7Longs"))
                .expect(testLoad.getLong("testLongs"), new ResultNotNull())
                .expect(testLoad.getLong("testLongs"), 100L)
                .then(() -> Console.log("&aPassed &7Longs"));
    }

    /**
     * Used to test getting and setting doubles.
     */
    public void testDoubles() {
        Configuration configuration = this.create();
        configuration.load();

        configuration.set("testDoubles", 10.1D);

        configuration.save();
        Configuration testLoad = this.create();
        testLoad.load();

        new ResultChecker()
                .fallBack(() -> Console.log("&eFailed &7Doubles"))
                .expect(testLoad.getDouble("testDoubles"), new ResultNotNull())
                .expect(testLoad.getDouble("testDoubles"), 10.1D)
                .then(() -> Console.log("&aPassed &7Doubles"));
    }

    /**
     * Used to test getting and setting booleans.
     */
    public void testBooleans() {
        Configuration configuration = this.create();
        configuration.load();

        configuration.set("testBooleans", true);

        configuration.save();
        Configuration testLoad = this.create();
        testLoad.load();

        new ResultChecker()
                .fallBack(() -> Console.log("&eFailed &7Booleans"))
                .expect(testLoad.getBoolean("testBooleans", false), true)
                .then(() -> Console.log("&aPassed &7Booleans"));
    }

    public void testGenericLists() {
        Configuration configuration = this.create();
        configuration.load();

        List<?> test = new ArrayList<>();

        configuration.set("testBooleans", true);

        configuration.save();
        Configuration testLoad = this.create();
        testLoad.load();

        new ResultChecker()
                .fallBack(() -> Console.log("&eFailed &7Booleans"))
                .expect(testLoad.getBoolean("testBooleans", false), true)
                .then(() -> Console.log("&aPassed &7Booleans"));
    }
}
