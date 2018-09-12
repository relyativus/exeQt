package io.exeqt.engine.session;

import io.exeqt.engine.execution.QueryExecution;

/**
 * Represents interaction channel between application and database. Can be
 *
 * @author anatolii vakaliuk
 */
public interface Session extends QueryExecution {
    void release();
}
