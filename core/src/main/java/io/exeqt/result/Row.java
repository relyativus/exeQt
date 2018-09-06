package io.exeqt.result;

import io.exeqt.result.metadata.QueryMetadata;

/**
 * @author anatolii vakaliuk
 */
public interface Row {
    QueryMetadata getMetadata();

    int getInt(final String columnName);

    long getLong(final String columnName);

    char getCharacter(final String columnName);

    double getDouble(final String columnName);

    String getString(final String columnName);

    byte getByte(final String columnName);

    byte[] getBytes(final String columnName);

    <T> T getObject(final String columnName, final Class<T> targetClass);
}
