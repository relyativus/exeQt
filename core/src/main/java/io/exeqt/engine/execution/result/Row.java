package io.exeqt.engine.execution.result;

import java.util.List;

/**
 * Encapsulates result item row.
 * Defines simple accessors for query returned columns and responsible for conversion between SQL specific types
 * to Java ones.
 *
 * @author anatolii vakaliuk
 */
public interface Row {
    /**
     * List all columns names included in returned row. Column name might be either name or alias if specified
     *
     * @return list of column names
     */
    List<String> columnLabels();

    /**
     * Returns primitive int value for column by name
     *
     * @param columnName column name for associated value
     * @return int value
     */
    int getInt(final String columnName);

    /**
     * Returns primitive long value for column by name
     *
     * @param columnName column name for associated value
     * @return long value
     */
    long getLong(final String columnName);

    /**
     * Returns primitive double value for column by name
     *
     * @param columnName column name for associated value
     * @return double value
     */
    double getDouble(final String columnName);

    /**
     * Returns string value for column by name
     *
     * @param columnName column name for associated value
     * @return string value
     */
    String getString(final String columnName);

    /**
     * Returns primitive byte value for column by name
     *
     * @param columnName column name for associated value
     * @return byte value
     */
    byte getByte(final String columnName);

    /**
     * Returns byte array value for column by name
     *
     * @param columnName column name for associated value
     * @return byte array value
     */
    byte[] getBytes(final String columnName);

    /**
     * Returns requested object value for column name. Value will be resolved with configured data converter
     *
     * @param columnName  column name
     * @param targetClass requested target type
     * @return requested value
     */
    <T> T getObject(final String columnName, final Class<T> targetClass);
}
