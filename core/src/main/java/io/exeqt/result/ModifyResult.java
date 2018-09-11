package io.exeqt.result;

import lombok.Value;

/**
 * Simple container for modification query result
 *
 * @author anatolii vakaliuk
 */
@Value
public class ModifyResult {
    private int affectedRows;
}
