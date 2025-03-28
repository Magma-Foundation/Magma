package jvm.api.concurrent;

import jvm.api.result.JavaResults;
import magma.api.concurrent.InterruptedError;

public record JavaInterruptedError(InterruptedException exception) implements InterruptedError {
    @Override
    public String display() {
        return JavaResults.convertThrowableToString(exception);
    }
}
