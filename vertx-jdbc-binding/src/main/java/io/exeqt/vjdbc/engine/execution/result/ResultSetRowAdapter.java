package io.exeqt.vjdbc.engine.execution.result;

import io.exeqt.engine.execution.conversion.ConversionService;
import io.exeqt.engine.execution.result.Row;
import io.vertx.core.json.JsonArray;
import io.vertx.reactivex.ext.sql.SQLRowStream;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author anatolii vakaliuk
 */
@Value
public class ResultSetRowAdapter implements Row {
    private final ConversionService conversionService;
    private final Map<String, Integer> columnToIndexMapping;
    private final JsonArray row;

    @Override
    public List<String> columnLabels() {
        return new ArrayList<>(columnToIndexMapping.keySet());
    }

    @Override
    public int getInt(final String columnName) {
        int index = columnToIndexMapping.get(columnName);
        return row.getInteger(index);
    }

    @Override
    public long getLong(final String columnName) {
        int index = columnToIndexMapping.get(columnName);
        return row.getLong(index);
    }

    @Override
    public double getDouble(final String columnName) {
        int index = columnToIndexMapping.get(columnName);
        return row.getDouble(index);
    }

    @Override
    public String getString(final String columnName) {
        int index = columnToIndexMapping.get(columnName);
        return row.getString(index);
    }

    @Override
    public byte getByte(final String columnName) {
        int index = columnToIndexMapping.get(columnName);
        return row.getInteger(index).byteValue();
    }

    @Override
    public byte[] getBytes(final String columnName) {
        int index = columnToIndexMapping.get(columnName);
        return row.getBinary(index);
    }

    @Override
    public <T> T getObject(final String columnName, final Class<T> targetClass) {
        int index = columnToIndexMapping.get(columnName);
        return conversionService.convert(row.getValue(index), targetClass);
    }
}
