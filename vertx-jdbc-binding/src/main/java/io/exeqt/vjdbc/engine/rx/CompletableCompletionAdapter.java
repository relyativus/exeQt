package io.exeqt.vjdbc.engine.rx;

import io.exeqt.engine.rx.Completion;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import lombok.Value;

/**
 * Adapter between {@link Completable} and {@link Completion}
 *
 * @author anatolii vakaliuk
 */
@Value
public class CompletableCompletionAdapter extends Completable implements Completion {

    private final Completable completable;

    @Override
    public void subscribe(final CompletionSubscriber subscription) {
        completable.subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(final Disposable d) {
                subscription.onSubscribe(new CompletionSubscription() {
                    @Override
                    public boolean isCancelled() {
                        return d.isDisposed();
                    }

                    @Override
                    public void cancel() {
                        d.dispose();
                    }
                });
            }

            @Override
            public void onComplete() {
                subscription.onComplete();
            }

            @Override
            public void onError(final Throwable e) {
                subscription.onError(e);
            }
        });
    }

    @Override
    protected void subscribeActual(final CompletableObserver observer) {
        completable.subscribe(observer);
    }
}
