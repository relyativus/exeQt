package io.exeqt.engine.execution;

import io.exeqt.result.ModificationResult;

import java.util.concurrent.Flow.Publisher;

/**
 * Responsible for execution of queries which will modify database state. In simple words: insert, update and delete queries
 *
 * @author anatolii vakaliuk
 */
public interface ModifyQueryExecution {

    /**
     * Executes modification query and return modification result
     *
     * @param query query to execute
     * @return modification result stream.
     */
    Publisher<ModificationResult> modify(final Query query);

    /**
     * Shortcut {@link #modify(Query)} for queries with prepared arguments but without special characteristics
     *
     * @param query     query string
     * @param arguments prepared arguments
     * @return modification result stream
     */
    default Publisher<ModificationResult> modify(final String query, Object[] arguments) {
        return modify(Query.builder().setSql(query).setArguments(arguments).build());
    }

    /**
     * Shortcut for {@link #modify(Query)} for queries without prepared arguments and special characteristics
     *
     * @param query query string
     * @return modification result stream
     */
    default Publisher<ModificationResult> modify(final String query) {
        return modify(query, null);
    }

}
