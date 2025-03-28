package jv.api.error;

import magma.api.error.Error;

import java.io.PrintWriter;
import java.io.StringWriter;

public record ThrowableError(Throwable throwable) implements Error {
    @Override
    public String display() {
        StringWriter writer = new StringWriter();
        throwable.printStackTrace(new PrintWriter(writer));
        return writer.toString();
    }
}
