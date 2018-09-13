package io.exeqt.vjdbc.engine.session;

import io.exeqt.engine.tx.TransactionAttributes;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.vertx.ext.sql.TransactionIsolation;
import io.vertx.reactivex.ext.sql.SQLConnection;
import lombok.Value;

import static java.util.Objects.nonNull;

/**
 * @author anatolii vakaliuk
 */
@Value
public class SetupTransactionTransformer implements SingleTransformer<SQLConnection, SQLConnection> {
    private final TransactionAttributes attributes;

    @Override
    public SingleSource<SQLConnection> apply(Single<SQLConnection> upstream) {
        return upstream.flatMap(connection -> connection.rxSetAutoCommit(false)
                .andThen((
                        nonNull(attributes) ?
                                connection.rxSetTransactionIsolation(TransactionIsolation.from(attributes.getIsolationLevel().getValue()))
                                : Completable.complete()
                        ).toSingleDefault(connection)
                )
        );
    }
}
