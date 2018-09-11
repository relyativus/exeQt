package io.exeqt.engine.execution.conversion;

/**
 * Responsible for performing conversion between sql specific types to java
 *
 * @author anatolii vakaliuk
 */
@FunctionalInterface
public interface SQLValueConversion<R> {
    /**
     * Converts {@code source} to target type
     *
     * @param sqlValue sql value to convert
     * @return converted value
     */
    R convert(final Object sqlValue);
}
