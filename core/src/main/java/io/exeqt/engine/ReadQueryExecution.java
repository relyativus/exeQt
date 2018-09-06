package io.exeqt.engine;

import io.exeqt.result.Row;

import java.util.concurrent.Flow.Publisher;

/**
 * @author anatolii vakaliuk
 */
public interface ReadQueryExecution {
    Publisher<Row> read(final Query query);

    default Publisher<Row> read(final String query) {
        return read(query, null);
    }

    default Publisher<Row> read(final String query, Object[] arguments) {
        return read(Query.builder().setSql(query).setArguments(arguments).build());
    }

}
