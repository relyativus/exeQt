package io.exeqt.rxjava.engine.execution;

import io.exeqt.engine.execution.Query;
import io.exeqt.engine.execution.QueryExecution;
import io.exeqt.engine.execution.ReadQueryExecution;
import io.exeqt.engine.execution.result.ModificationResult;
import io.exeqt.engine.execution.result.Row;
import io.exeqt.rxjava.adapter.FlowableJdkPublisherAdapter;
import lombok.AllArgsConstructor;

import java.util.concurrent.Flow;

/**
 * Adapts {@link ReadQueryExecution} to rx java types
 *
 * @author anatolii vakaliuk
 */
@AllArgsConstructor
public class RxQueryExecution implements QueryExecution {
    private final QueryExecution delegate;

    @Override
    public FlowableJdkPublisherAdapter<Row> read(final Query query) {
        return FlowableJdkPublisherAdapter.fromJdkPublisher(delegate.read(query));
    }

    @Override
    public FlowableJdkPublisherAdapter<Row> read(final String query) {
        return FlowableJdkPublisherAdapter.fromJdkPublisher(delegate.read(query));
    }

    @Override
    public Flow.Publisher<Row> read(final String query, final Object[] arguments) {
        return FlowableJdkPublisherAdapter.fromJdkPublisher(delegate.read(query, arguments));
    }

    @Override
    public FlowableJdkPublisherAdapter<ModificationResult> modify(final Query query) {
        return FlowableJdkPublisherAdapter.fromJdkPublisher(delegate.modify(query));
    }

    @Override
    public FlowableJdkPublisherAdapter<ModificationResult> modify(final String query, final Object[] arguments) {
        return FlowableJdkPublisherAdapter.fromJdkPublisher(delegate.modify(query, arguments));
    }

    @Override
    public FlowableJdkPublisherAdapter<ModificationResult> modify(final String query) {
        return FlowableJdkPublisherAdapter.fromJdkPublisher(delegate.modify(query));
    }
}
