package io.exeqt.vjdbc.engine.session.cleanup;

import io.exeqt.engine.rx.Completion;
import io.exeqt.engine.session.Session;
import lombok.RequiredArgsConstructor;

/**
 * Responsible for closing/releasing {@link Session}
 *
 * @author anatolii vakaliuk
 */
@RequiredArgsConstructor
public final class ReleaseConnectionSubscriber implements Completion.CompletionSubscriber {
    private final Session session;

    @Override
    public void onSubscribe(Completion.CompletionSubscription subscription) {
//            do nothing
    }

    @Override
    public void onError(Throwable throwable) {
        session.release();
    }

    @Override
    public void onComplete() {
        session.release();
    }
}
