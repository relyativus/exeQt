package io.exeqt.engine.session;

import io.exeqt.engine.execution.QueryExecution;
import io.exeqt.engine.tx.TransactionalExecution;

import java.util.concurrent.Flow.Publisher;

/**
 * Represents interaction channel between application and database. Can be
 *
 * @author anatolii vakaliuk
 */
public interface Session {

    QueryExecution execution();

    Publisher<TransactionalExecution> openTransaction();

    void release();
}
