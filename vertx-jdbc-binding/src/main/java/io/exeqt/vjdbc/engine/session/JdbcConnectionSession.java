package io.exeqt.vjdbc.engine.session;

import io.exeqt.engine.execution.Query;
import io.exeqt.engine.execution.conversion.ConversionService;
import io.exeqt.engine.execution.result.ModificationResult;
import io.exeqt.engine.execution.result.Row;
import io.exeqt.engine.session.Session;
import io.exeqt.vjdbc.engine.execution.result.JdbcUpdateResultAdapter;
import io.exeqt.vjdbc.engine.execution.result.ResultSetRowAdapter;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.sql.UpdateResult;
import io.vertx.reactivex.ext.sql.SQLConnection;
import io.vertx.reactivex.ext.sql.SQLRowStream;
import lombok.Value;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.Flow;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.reactivestreams.FlowAdapters.toFlowPublisher;

/**
 * @author anatolii vakaliuk
 */
@Value
public class JdbcConnectionSession implements Session {
    private final ConversionService conversionService;
    private final SQLConnection connection;

    public SQLConnection getRawConnection() {
        return connection;
    }

    @Override
    public void release() {
        connection.close();
    }

    @Override
    public Flow.Publisher<ModificationResult> modify(final Query query) {
        Single<UpdateResult> updateResult = connection
                .rxUpdateWithParams(query.getSql(), new JsonArray(Arrays.asList(query.getArguments())));
        Single<JdbcUpdateResultAdapter> modificationResultsEmission = updateResult.map(result -> new JdbcUpdateResultAdapter(result, conversionService));
        return toFlowPublisher(modificationResultsEmission.toFlowable());
    }

    @Override
    public Flow.Publisher<Row> read(final Query query) {
        final Single<SQLRowStream> sqlRowStreamSingle = connection
                .rxQueryStreamWithParams(query.getSql(), new JsonArray(Arrays.asList(query.getArguments())));
        final Flowable<ResultSetRowAdapter> rowsEmission = sqlRowStreamSingle
                .flatMapPublisher(stream -> stream.toFlowable().map(row -> new ResultSetRowAdapter(conversionService, getColumnIndexMapping(stream), row)));
        return toFlowPublisher(rowsEmission);
    }

    private Map<String, Integer> getColumnIndexMapping(final SQLRowStream stream) {
        return stream.columns().stream().collect(Collectors.toMap(Function.identity(), stream::column));
    }

}
