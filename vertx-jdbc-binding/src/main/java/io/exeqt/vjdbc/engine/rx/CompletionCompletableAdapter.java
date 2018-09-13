package io.exeqt.vjdbc.engine.rx;

import io.exeqt.engine.rx.Completion;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * Adapts {@link Completion} to {@code RxJava} {@link Completable}
 *
 * @author anatolii vakaliuk
 */
@AllArgsConstructor
public class CompletionCompletableAdapter extends Completable implements Completion {

    private final Completion completion;

    @Override
    public void subscribe(final CompletionSubscriber subscriber) {
        completion.subscribe(new CompletionSubscriber() {
            @Override
            public void onSubscribe(final CompletionSubscription subscription) {
                subscriber.onSubscribe(subscription);
            }

            @Override
            public void onError(final Throwable throwable) {
                subscriber.onError(throwable);
            }

            @Override
            public void onComplete() {
                subscriber.onComplete();
            }
        });
    }

    @Override
    protected void subscribeActual(final CompletableObserver observer) {
        completion.subscribe(new CompletionSubscriber() {
            @Override
            public void onSubscribe(final CompletionSubscription subscription) {
                observer.onSubscribe(new Disposable() {
                    @Override
                    public void dispose() {
                        subscription.cancel();
                    }

                    @Override
                    public boolean isDisposed() {
                        return subscription.isCancelled();
                    }
                });
            }

            @Override
            public void onError(final Throwable throwable) {
                observer.onError(throwable);
            }

            @Override
            public void onComplete() {
                observer.onComplete();
            }
        });
    }
}
