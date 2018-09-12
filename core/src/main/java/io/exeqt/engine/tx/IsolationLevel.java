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
    READ_UNCOMMITTED(Connection.TRANSACTION_READ_UNCOMMITTED),
    READ_COMMITTED(Connection.TRANSACTION_READ_COMMITTED),
    REPEATABLE_READ(Connection.TRANSACTION_REPEATABLE_READ),
    SERIALIZABLE(Connection.TRANSACTION_SERIALIZABLE);

    private final int value;
}
