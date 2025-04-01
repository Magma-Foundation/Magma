package magma.process;

import jvm.result.Results;

public record ExceptionalProcessError(InterruptedException exception) implements ProcessError {
    @Override
    public String display() {
        return Results.displayThrowable(exception);
    }
}
