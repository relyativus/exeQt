package io.exeqt.engine.execution;

import io.exeqt.result.Row;

import java.util.concurrent.Flow.Publisher;

/**
 * @author anatolii vakaliuk
 */
public interface ReadQueryExecution<T extends Publisher<Row>> {
    T read(final Query query);

    default T read(final String query) {
        return read(query, null);
    }

    default T read(final String query, Object[] arguments) {
        return read(Query.builder().setSql(query).setArguments(arguments).build());
    }

}
