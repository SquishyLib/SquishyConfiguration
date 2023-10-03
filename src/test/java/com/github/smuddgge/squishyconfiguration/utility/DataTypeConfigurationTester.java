package com.github.smuddgge.squishyconfiguration.utility;

import com.github.smuddgge.squishyconfiguration.PreparedConfigurationFactory;
import com.github.smuddgge.squishyconfiguration.console.Console;
import com.github.smuddgge.squishyconfiguration.interfaces.Configuration;
import com.github.smuddgge.squishyconfiguration.results.ResultChecker;
import com.github.smuddgge.squishyconfiguration.results.types.ResultNotNull;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * Represents the test class
     * used to test saving and loading classes.
     */
    private class TestClass {

        public int value = 1;

    }

    @Override
    public void testAll() {
        this.testObjects();
        this.testStrings();
        this.testLongs();
        this.testDoubles();
        this.testBooleans();
        this.testStringList();
        this.testIntegerList();
        this.testStringToObjectMap();
        this.testClass();
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

    /**
     * Used to test getting and setting string lists.
     */
    public void testStringList() {
        Configuration configuration = this.create();
        configuration.load();

        List<String> testList = new ArrayList<>();
        testList.add("testItem1");
        testList.add("testItem2");

        configuration.set("testStringList", testList);

        configuration.save();
        Configuration testLoad = this.create();
        testLoad.load();

        new ResultChecker()
                .fallBack(() -> Console.log("&eFailed &7String List"))
                .expect(testLoad.getListString("testStringList").get(0), testList.get(0))
                .then(() -> Console.log("&aPassed &7String List"));
    }

    /**
     * Used to test getting and setting integer lists.
     */
    public void testIntegerList() {
        Configuration configuration = this.create();
        configuration.load();

        List<Integer> testList = new ArrayList<>();
        testList.add(2);
        testList.add(3);

        configuration.set("testIntegerList", testList);

        configuration.save();
        Configuration testLoad = this.create();
        testLoad.load();

        new ResultChecker()
                .fallBack(() -> Console.log("&eFailed &7Integer List"))
                .expect(testLoad.getListInteger("testIntegerList").get(0), testList.get(0))
                .then(() -> Console.log("&aPassed &7Integer List"));
    }

    /**
     * Used to test getting and setting string to object maps.
     */
    public void testStringToObjectMap() {
        Configuration configuration = this.create();
        configuration.load();

        Map<String, Object> map = new HashMap<>();
        map.put("key1", "value2");
        map.put("key2", "value2");

        configuration.set("testStringToObjectMap", map);

        configuration.save();
        Configuration testLoad = this.create();
        testLoad.load();

        new ResultChecker()
                .fallBack(() -> Console.log("&eFailed &7String to Object Map"))
                .expect(testLoad.getMap("testStringToObjectMap"), map)
                .then(() -> Console.log("&aPassed &7String to Object Map"));
    }

    /**
     * Used to test getting and setting classes.
     */
    public void testClass() {
        Configuration configuration = this.create();
        configuration.load();

        // Create a test class.
        TestClass testClass = new TestClass();

        configuration.set("testClass", testClass);

        configuration.save();
        Configuration testLoad = this.create();
        testLoad.load();

        new ResultChecker()
                .fallBack(() -> Console.log("&eFailed &7Classes"))
                .expect(testLoad.getClass("testClass", TestClass.class).value, testClass.value)
                .then(() -> Console.log("&aPassed &7Classes"));
    }
}
