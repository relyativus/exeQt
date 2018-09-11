package io.exeqt.engine.execution.conversion;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.lang.String.format;

/**
 * Registry for all supported conversions
 *
 * @author anatolii vakaliuk
 */
public class ConversionService {

    private Map<Class<?>, SQLValueConversion> registeredConversions = new HashMap<>();

    public <T> ConversionService registerConversion(final Class<T> targetClass, final SQLValueConversion<T> conversion) {
        this.registeredConversions.put(targetClass, conversion);
        return this;
    }

    public <R> R convert(final Object source, final Class<R> targetClass) {
        return Optional.ofNullable(registeredConversions.get(targetClass))
                .map(conversion -> (R) conversion.convert(source))
                .orElseThrow(() -> new IllegalArgumentException(format("Could not find appropriate conversion for type %s", targetClass.getName())));
    }
}
