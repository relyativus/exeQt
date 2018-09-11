package io.exeqt.rxjava.adapter;

import io.reactivex.Flowable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.Flow;

/**
 * Acts as both {@link Flowable} and {@link java.util.concurrent.Flow.Publisher}
 *
 * @author anatolii vakaliuk
 */
public class FlowableJdkPublisherAdapter<T> extends Flowable<T> implements Flow.Publisher<T> {

    private Flow.Publisher<T> publisher;

    private FlowableJdkPublisherAdapter(final Flow.Publisher<T> publisher) {
        this.publisher = publisher;
    }

    public static  <R> FlowableJdkPublisherAdapter<R> fromJdkPublisher(final Flow.Publisher<R> publisher) {
        return new FlowableJdkPublisherAdapter<>(publisher);
    }

    @Override
    protected void subscribeActual(final Subscriber<? super T> rxSubscriber) {
        publisher.subscribe(new Flow.Subscriber<>() {
            @Override
            public void onSubscribe(final Flow.Subscription jdkSubscription) {
                rxSubscriber.onSubscribe(new Subscription() {
                    @Override
                    public void request(final long n) {
                        jdkSubscription.request(n);
                    }

                    @Override
                    public void cancel() {
                        jdkSubscription.cancel();
                    }
                });
            }

            @Override
            public void onNext(final T item) {
                rxSubscriber.onNext(item);
            }

            @Override
            public void onError(final Throwable throwable) {
                rxSubscriber.onError(throwable);
            }

            @Override
            public void onComplete() {
                rxSubscriber.onComplete();
            }
        });
    }

    @Override
    public void subscribe(final Flow.Subscriber<? super T> subscriber) {
        publisher.subscribe(subscriber);
    }
}
