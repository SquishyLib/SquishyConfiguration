package com.github.smuddgge.squishyconfiguration.utility;

import com.github.smuddgge.squishyconfiguration.PreparedConfigurationFactory;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a configuration tester.
 * Used to test a configuration instance.
 */
public class AllConfigurationTester extends ConfigurationCreator implements ConfigurationTester {

    /**
     * Used to create a configuration creator.
     *
     * @param factory The instance of the prepared
     *                configuration factory.
     */
    public AllConfigurationTester(@NotNull PreparedConfigurationFactory factory) {
        super(factory);
    }

    @Override
    public void testAll() {
        this.testDataTypes();
    }

    /**
     * Used to test all data types.
     */
    public void testDataTypes() {
        ConfigurationTester tester = new DataTypeConfigurationTester(this.getFactory());
        tester.testAll();
    }
}
