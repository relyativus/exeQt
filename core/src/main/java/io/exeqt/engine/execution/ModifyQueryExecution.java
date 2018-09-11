package io.exeqt.engine.execution;

import io.exeqt.result.ModifyResult;

import java.util.concurrent.Flow.Publisher;

/**
 * @author anatolii vakaliuk
 */
public interface ModifyQueryExecution<T extends Publisher<ModifyResult>> {

    default T modify(final String query, Object[] arguments) {
        return modify(Query.builder().setSql(query).setArguments(arguments).build());
    }

    default T modify(final String query) {
        return modify(query, null);
    }

    T modify(final Query query);

}
