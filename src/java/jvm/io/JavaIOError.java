package jvm.io;

import magma.io.IOError;
import jvm.result.Results;

import java.io.IOException;

public record JavaIOError(IOException error) implements IOError {
    public static IOException unwrap(IOError error) {
        return new IOException(error.display());
    }

    @Override
    public String display() {
        return Results.printStackTraceToString(error);
    }

}
