package io.exeqt.rxjava.adapter;

import io.reactivex.Flowable;
import lombok.Value;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.Flow;

/**
 * Adapter between {@link Flowable} and {@link java.util.concurrent.Flow.Publisher}
 *
 * @author anatolii vakaliuk
 */
@Value(staticConstructor = "fromFlowable")
public class JdkPublisherFlowableAdapter<T> extends Flowable<T> implements Flow.Publisher<T> {
    private final Flowable<T> flowable;

    @Override
    protected void subscribeActual(final Subscriber<? super T> flowableSubscriber) {
        flowable.subscribe(flowableSubscriber);
    }

    @Override
    public void subscribe(final Flow.Subscriber<? super T> subscriber) {
        flowable.subscribe(new Subscriber<T>() {
            @Override
            public void onSubscribe(final Subscription flowableSubscription) {
                subscriber.onSubscribe(new Flow.Subscription() {
                    @Override
                    public void request(final long n) {
                        flowableSubscription.request(n);
                    }

                    @Override
                    public void cancel() {
                        flowableSubscription.cancel();
                    }
                });
            }

            @Override
            public void onNext(final T t) {
                subscriber.onNext(t);
            }

            @Override
            public void onError(final Throwable t) {
                subscriber.onError(t);
            }

            @Override
            public void onComplete() {
                subscriber.onComplete();
            }
        });
    }
}
