package magma;

import magma.error.Error;
import jvm.result.Results;

public record JavaInterruptedError(InterruptedException error) implements Error {
    @Override
    public String display() {
        return Results.printStackTraceToString(error);
    }
}
