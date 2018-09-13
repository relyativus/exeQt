package io.exeqt.engine.session;

import io.exeqt.engine.rx.Completion;
import io.exeqt.engine.tx.TransactionAttributes;

import java.util.concurrent.Flow.Publisher;
import java.util.function.Function;

/**
 * Database session manager. This is an entry point to start database interaction.
 * API is divided into two logical parts: transactional execution and autocommit one
 *
 * @author anatolii vakaliuk
 */
public interface SessionFactory {
    /**
     * Release resources encapsulated in session factory. After this method is called
     * session factory instance should not be used anymore
     *
     * @return close completion result
     */
    Completion close();

    /**
     * Provides a way to execute {@code txSessionExpression} operation within transaction bound.
     * Transaction management lifecycle is handled by session factory implementation itself
     *
     * @param attributes          transaction attributes
     * @param txSessionExpression expression to execute within transaction
     * @return result of executed transaction
     */
    <T> Publisher<T> txExpression(final TransactionAttributes attributes,
                                  final Function<TransactionalSession, Publisher<T>> txSessionExpression);

    /**
     * Similar to {@link #txExpression(TransactionAttributes, Function)} but does not return result
     */
    Completion txStatement(final TransactionAttributes attributes,
                           final Function<TransactionalSession, Completion> txSessionStatement);

    /**
     * Provides a way to execute {@code sessionExpression} operation in autocommit mode.
     *
     * @param sessionExpression expression to execute within session
     * @return result of executed transaction
     */
    <T> Publisher<T> sessionExpression(final Function<Session, Publisher<T>> sessionExpression);

    /**
     * Similar to {@link #sessionStatement(Function)} but does not return result
     */
    Completion sessionStatement(final Function<Session, Completion> txSessionStatement);

    /**
     * Shortcut for {@link #txExpression(TransactionAttributes, Function)} with default transaction attributes
     */
    default <T> Publisher<T> txExpression(final Function<TransactionalSession, Publisher<T>> txSessionExpression) {
        return txExpression(null, txSessionExpression);
    }

    /**
     * Shortcut for {@link #txStatement(Function)} with default transaction attributes
     */
    default Completion txStatement(final Function<TransactionalSession, Completion> txSessionStatement) {
        return txStatement(null, txSessionStatement);
    }

}
