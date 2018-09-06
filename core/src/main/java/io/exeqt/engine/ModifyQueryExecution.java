package io.exeqt.engine;

import io.exeqt.result.ModifyResult;

import java.util.concurrent.Flow;

/**
 * @author anatolii vakaliuk
 */
public interface ModifyQueryExecution {
    default Flow.Publisher<ModifyResult> modify(final String query, Object[] arguments) {
        return modify(Query.builder().setSql(query).setArguments(arguments).build());
    }

    default Flow.Publisher<ModifyResult> modify(final String query) {
        return modify(query, null);
    }

    Flow.Publisher<ModifyResult> modify(final Query query);

}
