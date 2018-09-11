package io.exeqt.result;

import java.util.concurrent.Flow;

/**
 * Simple container for {@code DML} query result
 *
 * @author anatolii vakaliuk
 */
public interface ModificationResult {
    /**
     * Count of rows affected by {@code DML} operation
     *
     * @return rows count
     */
    int affectedRows();

    /**
     * Returns rows generated by {@code DML} operation
     *
     * @return generated rows
     */
    Flow.Publisher<Row> streamGeneratedKeys();
}