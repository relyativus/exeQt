package io.exeqt.engine.execution;

/**
 * Combines {@link ReadQueryExecution} and {@link ModifyQueryExecution} under single interface
 *
 * @author anatolii vakaliuk
 */
public interface QueryExecution extends ReadQueryExecution, ModifyQueryExecution {
}
