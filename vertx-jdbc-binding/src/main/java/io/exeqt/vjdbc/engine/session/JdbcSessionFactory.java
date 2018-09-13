package io.exeqt.vjdbc.engine.session;

import io.exeqt.engine.execution.conversion.ConversionService;
import io.exeqt.engine.rx.Completion;
import io.exeqt.engine.session.Session;
import io.exeqt.engine.session.SessionFactory;
import io.exeqt.engine.session.TransactionalSession;
import io.exeqt.engine.tx.TransactionAttributes;
import io.exeqt.vjdbc.engine.rx.CompletableCompletionAdapter;
import io.exeqt.vjdbc.engine.rx.CompletionCompletableAdapter;
import io.exeqt.vjdbc.engine.session.cleanup.ReleaseConnectionSubscriber;
import io.exeqt.vjdbc.engine.session.cleanup.TxReleaseTransformer;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.vertx.reactivex.ext.jdbc.JDBCClient;
import lombok.Value;
import org.reactivestreams.FlowAdapters;
import org.reactivestreams.Publisher;

import java.util.concurrent.Flow;
import java.util.function.Function;

import static org.reactivestreams.FlowAdapters.toFlowPublisher;
import static org.reactivestreams.FlowAdapters.toPublisher;

/**
 * @author anatolii vakaliuk
 */
@Value
public class JdbcSessionFactory implements SessionFactory {
    private final JDBCClient jdbcClient;
    private final ConversionService conversionService;

    @Override
    public <T> Flow.Publisher<T> txExpression(final TransactionAttributes attributes,
                                              final Function<TransactionalSession, Flow.Publisher<T>> txSessionExpression) {
        final Flowable<T> operationResult = jdbcClient.rxGetConnection()
                .compose(new SetupTransactionTransformer(attributes))
                .flatMapPublisher(connection -> {
                    JdbcTransactionalSession transactionalSession = new JdbcTransactionalSession(attributes,
                            new JdbcConnectionSession(conversionService, connection));
                    Publisher<T> publisher = toPublisher(txSessionExpression.apply(transactionalSession));
                    return Flowable.fromPublisher(publisher)
                            .compose(new TxReleaseTransformer<>(transactionalSession))
                            .doOnCancel(() -> transactionalSession.rollback().subscribe(new ReleaseConnectionSubscriber(transactionalSession)));
                });
        return FlowAdapters.toFlowPublisher(operationResult);

    }

    @Override
    public Completion txStatement(final TransactionAttributes attributes, final Function<TransactionalSession, Completion> txSessionStatement) {
        Completable result = jdbcClient.rxGetConnection()
                .compose(new SetupTransactionTransformer(attributes))
                .flatMapCompletable(connection -> {
                    JdbcTransactionalSession transactionalSession = new JdbcTransactionalSession(attributes,
                            new JdbcConnectionSession(conversionService, connection));
                    Completable operationResult = new CompletionCompletableAdapter(txSessionStatement.apply(transactionalSession));
                    return operationResult.compose(new TxReleaseTransformer<>(transactionalSession));
                });
        return new CompletableCompletionAdapter(result);
    }

    @Override
    public <T> Flow.Publisher<T> sessionExpression(final Function<Session, Flow.Publisher<T>> sessionExpression) {
        final Flowable<T> operationResult = jdbcClient.rxGetConnection()
                .flatMapPublisher(connection -> {
                    final JdbcConnectionSession connectionSession = new JdbcConnectionSession(conversionService, connection);
                    final Publisher<T> publisher = toPublisher(sessionExpression.apply(connectionSession));
                    return Flowable.fromPublisher(publisher)
                            .doOnError(error -> connectionSession.release())
                            .doOnComplete(connectionSession::release);
                });
        return toFlowPublisher(operationResult);
    }

    @Override
    public Completion sessionStatement(final Function<Session, Completion> sessionStatement) {
        final Completable operationResult = jdbcClient.rxGetConnection()
                .flatMapCompletable(connection -> {
                    final JdbcConnectionSession connectionSession = new JdbcConnectionSession(conversionService, connection);
                    final Completable completable = new CompletionCompletableAdapter(sessionStatement.apply(connectionSession));
                    return completable
                            .doOnError(error -> connectionSession.release())
                            .doOnComplete(connectionSession::release);
                });
        return new CompletableCompletionAdapter(operationResult);
    }

    @Override
    public Completion close() {
        return new CompletableCompletionAdapter(jdbcClient.rxClose());
    }

}
