package io.exeqt.engine.tx;

import io.exeqt.engine.execution.QueryExecution;
import io.exeqt.result.ModificationResult;
import io.exeqt.result.Row;

import java.util.concurrent.Flow;

/**
 * @author anatolii vakaliuk
 */
public interface TransactionalExecution<T extends Flow.Publisher<Row>, R extends Flow.Publisher<ModificationResult>> extends QueryExecution<T, R> {

}
