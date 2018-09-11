package io.exeqt.engine.rx;

/**
 * Special form of {@link java.util.concurrent.Flow.Publisher} which do not produce any items but represents void operation
 * result
 *
 * @author anatolii vakaliuk
 * @see java.util.concurrent.Flow.Publisher
 */
public interface Completion {
    /**
     * Makes subscription on completion result
     *
     * @param subscription completion subscription
     */
    void onSubscribe(final CompletionSubscriber subscription);

    /**
     * @see java.util.concurrent.Flow.Subscriber
     */
    interface CompletionSubscriber {

        void onSubscribe(final CompletionSubscription subscription);

        void onError(final Throwable throwable);

        void onComplete();
    }

    /**
     * Api to cancel subscription
     *
     * @see java.util.concurrent.Flow.Subscription
     */
    interface CompletionSubscription {
        void cancel();
    }
}
