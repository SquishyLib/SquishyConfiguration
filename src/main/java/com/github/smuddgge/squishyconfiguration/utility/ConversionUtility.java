package com.github.smuddgge.squishyconfiguration.utility;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents the conversion utility.
 */
public class ConversionUtility {

    /**
     * Used to convert arrays to list objects.
     * <li>String[] -> List[String]</li>
     * <li>int[] -> List[Integer]</li>
     *
     * @param object The instance of the object.
     * @return The converted object.
     */
    public static @NotNull Object convertLists(@NotNull Object object) {

        // Check if the object is an int list.
        if (object instanceof int[]) {
            List<Integer> integerList = new ArrayList<>();
            for (int number : (int[]) object) integerList.add(number);
            return integerList;
        }

        if (object instanceof String[]) {
            return new ArrayList<>(Arrays.asList((String[]) object));
        }

        return object;
    }
}
