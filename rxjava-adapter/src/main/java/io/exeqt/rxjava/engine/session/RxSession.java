package io.exeqt.rxjava.engine.session;

import io.exeqt.engine.execution.Query;
import io.exeqt.engine.execution.result.ModificationResult;
import io.exeqt.engine.execution.result.Row;
import io.exeqt.engine.session.Session;
import io.exeqt.rxjava.adapter.FlowableJdkPublisherAdapter;
import io.exeqt.rxjava.engine.execution.result.RxModificationResult;
import io.reactivex.Flowable;
import lombok.Value;

import java.util.concurrent.Flow;

/**
 * Adapter for {@link Session} to {@code RxJava} types
 *
 * @author anatolii vakaliuk
 */
@Value(staticConstructor = "fromDelegate")
public class RxSession {
    private final Session delegate;

    public void release() {
        delegate.release();
    }

    public Flowable<RxModificationResult> modify(final Query query) {
        Flow.Publisher<ModificationResult> modificationResultPublisher = delegate.modify(query);
        FlowableJdkPublisherAdapter<ModificationResult> jdkPublisherAdapter = FlowableJdkPublisherAdapter.fromJdkPublisher(modificationResultPublisher);
        return jdkPublisherAdapter.map(RxModificationResult::fromDelegate);
    }

    public Flowable<RxModificationResult> modify(final String query, final Object[] arguments) {
        FlowableJdkPublisherAdapter<ModificationResult> jdkPublisherAdapter = FlowableJdkPublisherAdapter.fromJdkPublisher(delegate.modify(query, arguments));
        return jdkPublisherAdapter.map(RxModificationResult::fromDelegate);
    }

    public Flowable<RxModificationResult> modify(final String query) {
        FlowableJdkPublisherAdapter<ModificationResult> jdkPublisherAdapter = FlowableJdkPublisherAdapter.fromJdkPublisher(delegate.modify(query));
        return jdkPublisherAdapter.map(RxModificationResult::fromDelegate);
    }

    public Flowable<Row> read(final Query query) {
        return FlowableJdkPublisherAdapter.fromJdkPublisher(delegate.read(query));
    }

    public Flowable<Row> read(final String query) {
        return FlowableJdkPublisherAdapter.fromJdkPublisher(delegate.read(query));
    }

    public Flowable<Row> read(final String query, final Object[] arguments) {
        return FlowableJdkPublisherAdapter.fromJdkPublisher(delegate.read(query, arguments));
    }
}
