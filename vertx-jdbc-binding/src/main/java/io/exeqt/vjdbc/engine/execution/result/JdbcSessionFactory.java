package io.exeqt.vjdbc.engine.execution.result;

import io.exeqt.engine.execution.conversion.ConversionService;
import io.exeqt.engine.rx.Completion;
import io.exeqt.engine.session.Session;
import io.exeqt.engine.session.SessionFactory;
import io.exeqt.engine.session.TransactionalSession;
import io.exeqt.engine.tx.TransactionAttributes;
import io.exeqt.rxjava.adapter.FlowableJdkPublisherAdapter;
import io.exeqt.rxjava.adapter.JdkPublisherFlowableAdapter;
import io.reactivex.Flowable;
import io.vertx.ext.sql.TransactionIsolation;
import io.vertx.reactivex.ext.jdbc.JDBCClient;
import lombok.Value;

import java.util.concurrent.Flow;
import java.util.function.Function;

/**
 * @author anatolii vakaliuk
 */
@Value
public class JdbcSessionFactory implements SessionFactory {

    private final JDBCClient jdbcClient;
    private final ConversionService conversionService;

    @Override
    public <T> Flow.Publisher<T> txExpression(final TransactionAttributes attributes, final Function<TransactionalSession, Flow.Publisher<T>> txSessionExpression) {
        Flowable<T> resultFlowable = jdbcClient.rxGetConnection()
                .flatMap(connection -> connection.rxSetAutoCommit(false)
                        .andThen(connection.rxSetTransactionIsolation(TransactionIsolation.from(attributes.getIsolationLevel().getValue()))
                                .toSingleDefault(connection)
                        )
                )
                .flatMapPublisher(connection -> {
                    JdbcTransactionalSession transactionalSession = new JdbcTransactionalSession(attributes, new JdbcConnectionSession(conversionService, connection));
                    FlowableJdkPublisherAdapter<T> publisher = FlowableJdkPublisherAdapter.fromJdkPublisher(txSessionExpression.apply(transactionalSession));
                    return publisher
                            .doOnComplete(() -> transactionalSession.commit().subscribe(null)) //todo: implement proper release of resources
                            .doOnError(error -> transactionalSession.rollback().subscribe(null))
                            .doOnCancel(() -> transactionalSession.rollback().subscribe(null))
                            .doOnComplete(connection::close);
                });
        return JdkPublisherFlowableAdapter.fromFlowable(resultFlowable);

    }

    @Override
    public Completion txStatement(final TransactionAttributes attributes, final Function<TransactionalSession, Completion> txSessionStatement) {
        return null;
    }

    @Override
    public <T> Flow.Publisher<T> sessionExpression(final Function<Session, Flow.Publisher<T>> sessionExpression) {
        return null;
    }

    @Override
    public Completion sessionStatement(final Function<Session, Completion> txSessionStatement) {
        return null;
    }

    @Override
    public void close() throws Exception {

    }
}
