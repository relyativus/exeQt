package io.exeqt.vjdbc.engine.session.cleanup;

import io.exeqt.engine.session.TransactionalSession;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.CompletableTransformer;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import lombok.Value;
import org.reactivestreams.Publisher;


/**
 * Responsible for registering hooks to release {@link TransactionalSession}
 *
 * @author anatolii vakaliuk
 */
@Value
public class TxReleaseTransformer<T> implements FlowableTransformer<T, T>, CompletableTransformer {
    private TransactionalSession transactionalSession;

    @Override
    public Publisher<T> apply(Flowable<T> upstream) {
        return upstream
                .doOnError(error -> transactionalSession.rollback().subscribe(new ReleaseConnectionSubscriber(transactionalSession)))
                .doOnCancel(() -> transactionalSession.rollback().subscribe(new ReleaseConnectionSubscriber(transactionalSession)))
                .doOnComplete(() -> transactionalSession.commit().subscribe(new ReleaseConnectionSubscriber(transactionalSession)));
    }

    @Override
    public CompletableSource apply(Completable upstream) {
        return upstream
                .doOnError(error -> transactionalSession.rollback().subscribe(new ReleaseConnectionSubscriber(transactionalSession)))
                .doOnComplete(() -> transactionalSession.commit().subscribe(new ReleaseConnectionSubscriber(transactionalSession)));
    }
}
