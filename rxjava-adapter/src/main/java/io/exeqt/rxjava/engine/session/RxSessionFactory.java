package io.exeqt.rxjava.engine.session;

import io.exeqt.engine.rx.Completion;
import io.exeqt.engine.session.SessionFactory;
import io.exeqt.engine.tx.TransactionAttributes;
import io.exeqt.rxjava.adapter.CompletableCompletionAdapter;
import io.exeqt.rxjava.adapter.CompletionCompletableAdapter;
import io.exeqt.rxjava.adapter.FlowableJdkPublisherAdapter;
import io.exeqt.rxjava.adapter.JdkPublisherFlowableAdapter;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import lombok.Value;

import java.util.concurrent.Flow;
import java.util.function.Function;

/**
 * Adapter for {@link SessionFactory} to {@code RxJava} types
 *
 * @author anatolii vakaliuk
 */
@Value(staticConstructor = "fromDelegate")
public class RxSessionFactory implements AutoCloseable {

    private final SessionFactory delegate;

    public <T> Flowable<T> txExpression(final TransactionAttributes attributes,
                                        final Function<RxTransactionalSession, Flowable<T>> txSessionExpression) {
        final Flow.Publisher<T> jdkPublisher = delegate.txExpression(attributes, txSession ->
                JdkPublisherFlowableAdapter.fromFlowable(txSessionExpression.apply(RxTransactionalSession.fromDelegate(txSession))));
        return FlowableJdkPublisherAdapter.fromJdkPublisher(jdkPublisher);
    }

    public <T> Flowable<T> txExpression(final Function<RxTransactionalSession, Flowable<T>> txSessionExpression) {
        final Flow.Publisher<T> jdkPublisher = delegate.txExpression(null, txSession ->
                JdkPublisherFlowableAdapter.fromFlowable(txSessionExpression.apply(RxTransactionalSession.fromDelegate(txSession))));
        return FlowableJdkPublisherAdapter.fromJdkPublisher(jdkPublisher);
    }

    public Completable txStatement(final TransactionAttributes attributes,
                                   final Function<RxTransactionalSession, Completable> txSessionStatement) {
        final Completion completion = delegate.txStatement(attributes, txSession ->
                CompletableCompletionAdapter.fromCompletable(txSessionStatement.apply(RxTransactionalSession.fromDelegate(txSession))));
        return CompletionCompletableAdapter.fromCompletion(completion);
    }

    public Completable txStatement(final Function<RxTransactionalSession, Completable> txSessionStatement) {
        final Completion completion = delegate.txStatement(null, txSession ->
                CompletableCompletionAdapter.fromCompletable(txSessionStatement.apply(RxTransactionalSession.fromDelegate(txSession))));
        return CompletionCompletableAdapter.fromCompletion(completion);
    }

    public <T> Flowable<T> sessionExpression(final Function<RxSession, Flowable<T>> sessionExpression) {
        final Flow.Publisher<T> jdkPublisher = delegate.txExpression(null, txSession ->
                JdkPublisherFlowableAdapter.fromFlowable(sessionExpression.apply(RxSession.fromDelegate(txSession))));
        return FlowableJdkPublisherAdapter.fromJdkPublisher(jdkPublisher);
    }

    public Completable sessionStatement(final Function<RxSession, Completable> txSessionStatement) {
        final Completion completion = delegate.txStatement(null, txSession ->
                CompletableCompletionAdapter.fromCompletable(txSessionStatement.apply(RxSession.fromDelegate(txSession))));
        return CompletionCompletableAdapter.fromCompletion(completion);
    }

    @Override
    public void close() throws Exception {
        delegate.close();
    }
}
