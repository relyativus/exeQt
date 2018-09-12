package io.exeqt.engine.session;

import io.exeqt.engine.rx.Completion;
import io.exeqt.engine.tx.TransactionAttributes;

/**
 * @author anatolii vakaliuk
 */
public interface TransactionalSession extends Session {
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
