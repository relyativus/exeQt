package io.exeqt.engine.tx;

import lombok.Builder;
import lombok.Value;

/**
 * Represents transaction characteristics
 *
 * @author anatolii vakaliuk
 */
@Builder
@Value
public class TransactionAttributes {
    private IsolationLevel isolationLevel;
    private boolean readOnly = false;
}
