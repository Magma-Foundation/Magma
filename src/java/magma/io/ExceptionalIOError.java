package magma.io;

import magma.result.Results;

import java.io.IOException;

public record ExceptionalIOError(IOException exception) implements IOError {
    @Override
    public String display() {
        return Results.displayThrowable(exception);
    }
}
