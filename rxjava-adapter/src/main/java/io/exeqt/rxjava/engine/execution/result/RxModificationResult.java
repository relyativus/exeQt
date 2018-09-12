package io.exeqt.rxjava.engine.execution.result;

import io.exeqt.engine.execution.result.ModificationResult;
import io.exeqt.engine.execution.result.Row;
import io.exeqt.rxjava.adapter.FlowableJdkPublisherAdapter;
import lombok.Value;

/**
 * Rx adapter for {@link ModificationResult}
 *
 * @author anatolii vakaliuk
 */
@Value(staticConstructor = "fromDelegate")
public class RxModificationResult implements ModificationResult {
    private final ModificationResult delegate;

    @Override
    public int affectedRows() {
        return delegate.affectedRows();
    }

    @Override
    public FlowableJdkPublisherAdapter<Row> streamGeneratedKeys() {
        return FlowableJdkPublisherAdapter.fromJdkPublisher(delegate.streamGeneratedKeys());
    }
}
