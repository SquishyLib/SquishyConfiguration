package com.github.smuddgge.squishyconfiguration.results;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * <h2>Represents a result checker</h2>
 * Used to check results to make sure they are correct
 */
public class ResultChecker {

    private Runnable fallBack;
    private boolean hasFailed = false;

    /**
     * Used to set the fallback.
     * This will be executed before the error is thrown.
     *
     * @param runnable The instance of the runnable.
     * @return This instance.
     */
    public @NotNull ResultChecker fallBack(Runnable runnable) {
        this.fallBack = runnable;
        return this;
    }

    /**
     * Used to check a {@link Result} type
     *
     * @param value  The value to check
     * @param result The result to check against
     * @return This instance to chain multiple checks
     */
    public ResultChecker expect(Object value, Result result) {
        if (!result.check(value)) {
            this.hasFailed = true;
            this.fallBack.run();
            throw new RuntimeException(value + " != " + result.getClass().getName());
        }
        return this;
    }

    /**
     * Used to check an exact value
     *
     * @param value The value to check
     * @param match The value to match
     * @return This instance to chain multiple checks
     */
    public ResultChecker expect(Object value, Object match) {
        if (!Objects.equals(value, match)) {
            this.hasFailed = true;
            this.fallBack.run();
            throw new RuntimeException(value + " != " + match);
        }
        return this;
    }

    /**
     * Used to check if the value is true.
     *
     * @param value The value to check.
     * @return This instance to chain multiple checks.
     */
    public ResultChecker expect(boolean value) {
        if (!value) {
            this.hasFailed = true;
            this.fallBack.run();
            throw new RuntimeException("Value returned was false");
        }
        return this;
    }

    /**
     * Runs the runnable if no expect
     * statements have failed.
     *
     * @param runnable The instance of the runnable.
     * @return This instance.
     */
    public @NotNull ResultChecker then(@NotNull Runnable runnable) {
        if (!this.hasFailed) runnable.run();
        return this;
    }
}
