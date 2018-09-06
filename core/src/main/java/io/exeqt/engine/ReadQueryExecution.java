package io.exeqt.engine;

import io.exeqt.result.Row;

import java.util.concurrent.Flow;

/**
 * @author anatolii vakaliuk
 */
public interface ReadQueryExecution {
    Flow.Publisher<Row> read(final Query query);

    default Flow.Publisher<Row> read(final String query) {
        return read(query, null);
    }

    default Flow.Publisher<Row> read(final String query, Object[] arguments) {
        return read(Query.builder().setSql(query).setArguments(arguments).build());
    }

}
