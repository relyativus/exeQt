package io.exeqt.engine.tx;

import io.exeqt.engine.execution.QueryExecution;
import io.exeqt.engine.rx.Completion;

/**
 * Allows multiple query executions to act as database transaction. This is extension of {@link QueryExecution} which
 * additionally provides transaction attributes and transaction lifecycle
 *
 * @author anatolii vakaliuk
 */
public interface TransactionalExecution extends QueryExecution {
    /**
     * Provides metadata about underlying transaction
     *
     * @return transaction attributes
     */
    TransactionAttributes attributes();

    /**
     * Commits transaction. This method executes appropriate SQL query to commit transaction. After completion result
     * arrives internal state of implementation will be changed and any attempts to execute queries will fail
     *
     * @return result of commit operation
     */
    Completion commit();

    /**
     * Rollbacks transaction. This method executes appropriate SQL query to commit transaction. After completion result
     * arrives internal state of implementation will be changed and any attempts to execute queries will fail
     *
     * @return result of commit operation
     */
    Completion rollback();
}
