package io.exeqt.engine.tx;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Connection;

/**
 * Constants for transaction isolation level
 *
 * @author anatolii vakaliuk
 * @see Connection
 */
@AllArgsConstructor
@Getter
public enum IsolationLevel {
    TRANSACTION_READ_UNCOMMITTED(Connection.TRANSACTION_READ_UNCOMMITTED),
    TRANSACTION_READ_COMMITTED(Connection.TRANSACTION_READ_COMMITTED),
    TRANSACTION_REPEATABLE_READ(Connection.TRANSACTION_REPEATABLE_READ),
    TRANSACTION_SERIALIZABLE(Connection.TRANSACTION_SERIALIZABLE);

    private final int value;
}
