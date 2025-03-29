package jvm.io;

import magma.io.IOError;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public record JavaIOError(IOException error) implements IOError {
    public static IOException unwrap(IOError error) {
        return new IOException(error.display());
    }

    @Override
    public String display() {
        StringWriter writer = new StringWriter();
        error.printStackTrace(new PrintWriter(writer));
        return writer.toString();
    }
}
