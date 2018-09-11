package io.exeqt.engine.execution;

import io.exeqt.engine.execution.result.Row;

import java.util.concurrent.Flow.Publisher;

/**
 * Responsible for execution of read only queries.
 *
 * @author anatolii vakaliuk
 */
public interface ReadQueryExecution {
    /**
     * Performs select based query
     *
     * @param query describes query needs to be executed
     * @return query result stream
     */
    Publisher<Row> read(final Query query);

    /**
     * Shortcut for {@link #read(Query)} for queries without parameters and special execution characteristics
     *
     * @param query query string
     * @return query result stream
     */
    default Publisher<Row> read(final String query) {
        return read(query, null);
    }

    /**
     * Shortcut for {@link #read(Query)} for queries with prepared parameters which do no need special execution characteristics
     *
     * @param query     query string
     * @param arguments prepared arguments
     * @return query result stream
     */
    default Publisher<Row> read(final String query, Object[] arguments) {
        return read(Query.builder().setSql(query).setArguments(arguments).build());
    }

}
