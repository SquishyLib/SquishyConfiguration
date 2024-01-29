package com.github.smuddgge.squishyconfiguration.interfaces;

import java.util.List;
import java.util.Map;

/**
 * Represents a configuration section interface.
 */
public interface ConfigurationSection {

    /**
     * Used to get the location of the configuration
     * section from the base section.
     *
     * @return The base path.
     */
    String getBasePath();

    /**
     * Used to get the location of the configuration
     * section from the base section.
     *
     * @param path The location from theis configuration section
     *             to another nested section.
     * @return The root path.
     */
    String getBasePath(String path);

    /**
     * Used to set the configuration section to a value.
     * <ul>
     *     <li>If the value is set in the base section, the path will be null</li>
     * </ul>
     *
     * @param value The value to set the configuration section to.
     */
    void set(Object value);

    /**
     * Used to set or create a value in the configuration section
     * and save it in the base configuration section.
     * <ul>
     *     <li>If the value is null it will remove the key and value.</li>
     *     <li>If the path is null, it will be set as the configuration section.</li>
     * </ul>
     *
     * @param path  The location to set the value.
     * @param value The value to be set in the config.
     */
    void set(String path, Object value);

    /**
     * Used to set or create a value in the configuration section
     * and not save it to the base section.
     * <ul>
     *     <li>This will only be saved in this configuration section
     *         and not update the base section.</li>
     *     <li>If the value is null it will remove the key and value.</li>
     *     <li>If the path is null, it will be set as the configuration section.</li>
     * </ul>
     *
     * @param path  The location to set the value.
     * @param value The value to be set in the section.
     */
    void setInSection(String path, Object value);

    /**
     * Used to get any value from the configuration file.
     * <ul>
     *     <li>If the path does not exist it will return the alternative value.</li>
     * </ul>
     *
     * @param path        The location of the value
     * @param alternative The alternative value.
     * @return The requested value.
     */
    Object get(String path, Object alternative);

    /**
     * Used to get any value from the configuration section.
     * <ul>
     *     <li>If the path does not exist it will return null.</li>
     * </ul>
     *
     * @param path The location of the value.
     * @return The requested value.
     */
    Object get(String path);

    /**
     * Used to fill a class with the configuration values.
     * The section will be mapped on to the class.
     * <ul>
     *     <li>If the path does not exist the alternative value will be returned.</li>
     * </ul>
     *
     * @param <T>         The class type.
     * @param path        The location of the value.
     * @param clazz       The class to map the values onto.
     * @param alternative The alternative value.
     * @return An instance of the class.
     */
    <T> T getClass(String path, Class<T> clazz, T alternative);

    /**
     * Used to fill a class with the configuration values.
     * The section will be mapped on to the class.
     * <ul>
     *     <li>If the path does not exist null will be returned.</li>
     * </ul>
     *
     * @param <T>   The class type.
     * @param path  The location of the value.
     * @param clazz The class to map the values onto.
     * @return An instance of the class.
     */
    <T> T getClass(String path, Class<T> clazz);

    /**
     * Used to get a configuration section.
     * <ul>
     *     <li>If the path does not exist it will create a temporary empty section.</li>
     * </ul>
     *
     * @param path The location of the configuration section.
     * @return An instance of the configuration section.
     */
    ConfigurationSection getSection(String path);

    /**
     * Used to get the keys in this configuration section.
     * <ul>
     *     <li>If there are no keys, it will return null.</li>
     * </ul>
     *
     * @return The configuration sections keys.
     */
    List<String> getKeys();

    /**
     * Used to get the keys in a configuration section.
     * <ul>
     *     <li>If there are no keys, it will return null.</li>
     * </ul>
     *
     * @param path The location of the configuration section.
     * @return The configuration sections keys.
     */
    List<String> getKeys(String path);

    /**
     * Used to get a string.
     * <ul>
     *     <li>If the path does not exist it will return the alternative value.</li>
     *     <li>If the value is not a string it will return the alternative value.</li>
     * </ul>
     *
     * @param path        The location of the string in the configuration section.
     * @param alternative The alternative value.
     * @return The requested string.
     */
    String getString(String path, String alternative);

    /**
     * Used to get a string.
     * <ul>
     *     <li>If the path does not exist it will return null.</li>
     *     <li>If the value is not a string it will return null.</li>
     * </ul>
     *
     * @param path The location of the string in the configuration section.
     * @return The requested string.
     */
    String getString(String path);

    /**
     * Used to check if a value is a string.
     *
     * @param path The instance of the path.
     * @return True if the value is a string.
     */
    boolean isString(String path);

    /**
     * Used to get the value as a string.
     * <ul>
     *     <li>If the path does not exist it will return null.</li>
     *     <li>If the value is a list it will join the items with the join string.</li>
     *     <li>If the value is any other object it will use {@link Object#toString()}.</li>
     * </ul>
     *
     * @param path The location of the value.
     * @param join The string to join lists with.
     * @return The adapted string.
     */
    String getAdaptedString(String path, String join);

    /**
     * Used to get the value as a string.
     * <ul>
     *      <li>If the path does not exist it will return the alternative.</li>
     *      <li>If the value is a list it will join the items with the join string.</li>
     *      <li>If the value is any other object it will use {@link Object#toString()}.</li>
     * </ul>
     *
     * @param path        The location of the value.
     * @param join        The string to join lists with.
     * @param alternative The alternative value.
     * @return The adapted string.
     */
    String getAdaptedString(String path, String join, String alternative);

    /**
     * Used to get an integer.
     * Will attempt to convert doubles and longs to int.
     * <ul>
     *     <li>If the path does not exist it will return the alternative value.</li>
     *     <li>If the value is not a integer it will return the alternative value.</li>
     * </ul>
     *
     * @param path        The location of the integer in the configuration section.
     * @param alternative The alternative value.
     * @return The requested integer.
     */
    int getInteger(String path, int alternative);

    /**
     * Used to get an integer.
     * Will attempt to convert doubles and longs to int.
     * <ul>
     *     <li>If the path does not exist it will return -1.</li>
     *     <li>If the value is not a integer it will return -1.</li>
     * </ul>
     *
     * @param path The location of the integer in the configuration section.
     * @return The requested integer.
     */
    int getInteger(String path);

    /**
     * Used to check if a value is an integer.
     * This won't return true if its double or long but
     * this library will try to convert it if the
     * {@link this#getInteger(String)} is called.
     *
     * @param path The instance of the path.
     * @return True if the value is an integer.
     */
    boolean isInteger(String path);

    /**
     * Used to get a long.
     * <ul>
     *      <li>If the path does not exist it will return the alternative value.</li>
     *      <li>If the value is not a integer it will return the alternative value.</li>
     *     <li>If the value is a integer it will be converted into a long</li>
     * </ul>
     *
     * @param path        The location of the long in the configuration section.
     * @param alternative The alternative value.
     * @return The requested long.
     */
    long getLong(String path, long alternative);

    /**
     * Used to get a long.
     * <ul>
     *     <li>If the path does not exist it will return -1.</li>
     *     <li>If the value is not a integer it will return -1.</li>
     *     <li>If the value is a integer it will be converted into a long</li>
     * </ul>
     *
     * @param path The location of the long in the configuration section.
     * @return The requested long.
     */
    long getLong(String path);

    /**
     * Used to check if a value is a long.
     *
     * @param path The instance of the path.
     * @return True if the value is a long.
     */
    boolean isLong(String path);

    /**
     * Used to get a double.
     * <ul>
     *      <li>If the path does not exist it will return the alternative value.</li>
     *      <li>If the value is not a integer it will return the alternative value.</li>
     *      <li>If the value is a integer it will be converted into a double</li>
     * </ul>
     *
     * @param path        The location of the double in the configuration section.
     * @param alternative The alternative value.
     * @return The requested double.
     */
    double getDouble(String path, double alternative);

    /**
     * Used to get a double.
     * <ul>
     *     <li>If the path does not exist it will return -1.</li>
     *     <li>If the value is not a integer it will return -1.</li>
     *     <li>If the value is a integer it will be converted into a double</li>
     * </ul>
     *
     * @param path The location of the double in the configuration section.
     * @return The requested double.
     */
    double getDouble(String path);

    /**
     * Used to check if a value is a double.
     *
     * @param path The instance of the path.
     * @return True if the value is a double.
     */
    boolean isDouble(String path);

    /**
     * Used to get a boolean.
     * <ul>
     *     <li>If the path does not exist it will return the alternative value.</li>
     *     <li>If the value is not a boolean it will return the alternative value.</li>
     * </ul>
     *
     * @param path        The location of the boolean in the configuration section.
     * @param alternative The alternative value.
     * @return The requested boolean.
     */
    boolean getBoolean(String path, boolean alternative);

    /**
     * Used to get a boolean.
     * <ul>
     *     <li>If the path does not exist it will return false.</li>
     *     <li>If the value is not a boolean it will return false.</li>
     * </ul>
     *
     * @param path The location of the boolean in the configuration section.
     * @return The requested boolean.
     */
    boolean getBoolean(String path);

    /**
     * Used to check if a value is a boolean.
     *
     * @param path The instance of the path.
     * @return True if the value is a boolean.
     */
    boolean isBoolean(String path);

    /**
     * Used to get a list.
     * <ul>
     *     <li>If the path does not exist it will return the alternative value.</li>
     *     <li>If the value is not a list it will return the alternative value.</li>
     * </ul>
     *
     * @param path        The location of the list in this configuration section.
     * @param alternative The alternative list.
     * @return The requested list.
     */
    List<?> getList(String path, List<?> alternative);

    /**
     * Used to get a list.
     * <ul>
     *     <li>If the path does not exist it will return null.</li>
     *     <li>If the value is not a list it will return null.</li>
     * </ul>
     *
     * @param path The location of the list in this configuration section.
     * @return The requested list.
     */
    List<?> getList(String path);

    /**
     * Used to check if a value is a list.
     *
     * @param path The instance of the path.
     * @return True if the value is a list.
     */
    boolean isList(String path);

    /**
     * Used to get a list of strings.
     * <ul>
     *     <li>If the path does not exist it will return the alternative value.</li>
     *     <li>If the value is not a string list it will return the alternative value.</li>
     * </ul>
     *
     * @param path        The location of the string list in this configuration section.
     * @param alternative The alternative string list.
     * @return The requested list of strings.
     */
    List<String> getListString(String path, List<String> alternative);

    /**
     * Used to get a list of strings.
     * <ul>
     *     <li>If the path does not exist it will return null.</li>
     *     <li>If the value is not a string list it will return null.</li>
     * </ul>
     *
     * @param path The location of the string list in this configuration section.
     * @return The requested list of strings.
     */
    List<String> getListString(String path);

    /**
     * Used to get a list of integers.
     * <ul>
     *     <li>If the path does not exist it will return the alternative value.</li>
     *     <li>If the value is not a integer list it will return the alternative value.</li>
     * </ul>
     *
     * @param path        The location of the integer list in this configuration section.
     * @param alternative The alternative value.
     * @return The requested list of integers.
     */
    List<Integer> getListInteger(String path, List<Integer> alternative);

    /**
     * Used to get a list of integers.
     * <ul>
     *     <li>If the path does not exist it will return null.</li>
     *     <li>If the value is not a integer list it will return null.</li>
     * </ul>
     *
     * @param path The location of the integer list in this configuration section.
     * @return The requested list of integers.
     */
    List<Integer> getListInteger(String path);

    /**
     * Used to get the configuration section as a map.
     *
     * @return A map representing the configuration section.
     */
    Map<String, Object> getMap();

    /**
     * Used to get the configuration section as a map.
     * <ul>
     *     <li>If the path does not exist it will return the alternative value.</li>
     *     <li>If the value is not a map it will return the alternative value.</li>
     * </ul>
     *
     * @param alternative The alternative value.
     * @return A map representing the configuration section.
     */
    Map<String, Object> getMap(String path, Map<String, Object> alternative);

    /**
     * Used to get the configuration section as a map.
     * <ul>
     *     <li>If the path does not exist it will return null.</li>
     *     <li>If the value is not a map it will return null.</li>
     * </ul>
     *
     * @return A map representing the configuration section.
     */
    Map<String, Object> getMap(String path);

    /**
     * Used to check if a value is a map.
     *
     * @param path The instance of the path.
     * @return True if the value is a map.
     */
    boolean isMap(String path);
}
