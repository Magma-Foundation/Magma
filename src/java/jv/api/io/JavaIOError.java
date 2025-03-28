package jv.api.io;

import jv.api.result.JavaResults;
import magma.api.io.IOError;

import java.io.IOException;

public record JavaIOError(IOException exception) implements IOError {
    @Override
    public String display() {
        return JavaResults.convertThrowableToString(exception);
    }
}
