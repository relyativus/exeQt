package io.exeqt.rxjava.engine.session;

import io.exeqt.engine.execution.Query;
import io.exeqt.engine.execution.result.Row;
import io.exeqt.engine.rx.Completion;
import io.exeqt.engine.session.TransactionalSession;
import io.exeqt.engine.tx.TransactionAttributes;
import io.exeqt.rxjava.adapter.CompletionCompletableAdapter;
import io.exeqt.rxjava.engine.execution.result.RxModificationResult;
import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Adapter for {@link TransactionalSession} for {@code RxJava} types
 *
 * @author anatolii vakaliuk
 */
public class RxTransactionalSession {
    private final TransactionalSession delegate;
    private final RxSession rxSession;

    private RxTransactionalSession(final TransactionalSession delegate) {
        this.delegate = delegate;
        this.rxSession = RxSession.fromDelegate(delegate);
    }

    public static RxTransactionalSession fromDelegate(final TransactionalSession delegate) {
        return new RxTransactionalSession(delegate);
    }

    public TransactionAttributes attributes() {
        return delegate.attributes();
    }

    public Completable commit() {
        return CompletionCompletableAdapter.fromCompletion(delegate.commit());
    }

    public Completion rollback() {
        return CompletionCompletableAdapter.fromCompletion(delegate.rollback());
    }

    public void release() {
        delegate.release();
    }

    public Flowable<RxModificationResult> modify(final Query query) {
        return rxSession.modify(query);
    }

    public Flowable<RxModificationResult> modify(final String query, final Object[] arguments) {
        return rxSession.modify(query, arguments);
    }

    public Flowable<RxModificationResult> modify(final String query) {
        return rxSession.modify(query);
    }

    public Flowable<Row> read(final Query query) {
        return rxSession.read(query);
    }

    public Flowable<Row> read(final String query) {
        return rxSession.read(query);
    }

    public Flowable<Row> read(final String query, final Object[] arguments) {
        return rxSession.read(query, arguments);
    }
}
