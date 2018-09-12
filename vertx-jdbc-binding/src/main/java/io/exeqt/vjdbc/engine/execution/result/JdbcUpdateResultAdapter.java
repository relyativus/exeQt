package io.exeqt.vjdbc.engine.execution.result;

import io.exeqt.engine.execution.conversion.ConversionService;
import io.exeqt.engine.execution.result.ModificationResult;
import io.exeqt.engine.execution.result.Row;
import io.exeqt.rxjava.adapter.JdkPublisherFlowableAdapter;
import io.reactivex.Flowable;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.sql.UpdateResult;
import lombok.Value;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Flow;

/**
 * @author anatolii vakaliuk
 */
@Value
public class JdbcUpdateResultAdapter implements ModificationResult {
    private UpdateResult updateResult;
    private ConversionService conversionService;

    @Override
    public int affectedRows() {
        return updateResult.getUpdated();
    }

    @Override
    public Flow.Publisher<Row> streamGeneratedKeys() {
        Flowable<Row> rowFlowable = Flowable.fromIterable(updateResult.getKeys().getList())
                .map(qwe -> new ResultSetRowAdapter(conversionService, Map.of("id", 0), new JsonArray(List.of(qwe))));
        return JdkPublisherFlowableAdapter.fromFlowable(rowFlowable);
    }
}
