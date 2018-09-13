package io.exeqt.vjdbc.engine.session;

import io.exeqt.engine.execution.Query;
import io.exeqt.engine.execution.result.ModificationResult;
import io.exeqt.engine.execution.result.Row;
import io.exeqt.engine.rx.Completion;
import io.exeqt.engine.session.TransactionalSession;
import io.exeqt.engine.tx.TransactionAttributes;
import io.exeqt.vjdbc.engine.rx.CompletableCompletionAdapter;

import java.util.concurrent.Flow;

/**
 * @author anatolii vakaliuk
 */
public class JdbcTransactionalSession implements TransactionalSession {
    private final JdbcConnectionSession session;
    private final TransactionAttributes transactionAttributes;

    public JdbcTransactionalSession(final TransactionAttributes transactionAttributes,
                                    final JdbcConnectionSession session) {
        this.transactionAttributes = transactionAttributes;
        this.session = session;
    }

    @Override
    public TransactionAttributes attributes() {
        return transactionAttributes;
    }

    @Override
    public Completion commit() {
        return new CompletableCompletionAdapter(session.getRawConnection().rxCommit());
    }

    @Override
    public Completion rollback() {
        return new CompletableCompletionAdapter(session.getRawConnection().rxRollback());
    }

    @Override
    public void release() {
        session.release();
    }

    @Override
    public Flow.Publisher<ModificationResult> modify(final Query query) {
        return session.modify(query);
    }

    @Override
    public Flow.Publisher<Row> read(final Query query) {
        return session.read(query);
    }
}
