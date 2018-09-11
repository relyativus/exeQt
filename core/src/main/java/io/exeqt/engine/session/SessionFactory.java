package io.exeqt.engine.session;

import io.exeqt.engine.rx.Completion;

import java.util.concurrent.Flow.Publisher;

/**
 * Responsible for managing {@link Session} objects
 *
 * @author anatolii vakaliuk
 */
public interface SessionFactory extends AutoCloseable {

    /**
     * Creates new session
     *
     * @return session emitter
     */
    Publisher<Session> createSession();

    /**
     * Closes session
     *
     * @param session session to close
     * @return clean up result
     */
    Completion release(final Session session);
}
