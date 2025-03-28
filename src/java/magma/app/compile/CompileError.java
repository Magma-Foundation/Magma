package magma.app.compile;

import jvm.api.collect.Lists;
import magma.api.collect.Joiner;
import magma.api.collect.List_;
import magma.api.error.Error;

public record CompileError(String message, String context, List_<CompileError> errors) implements Error {
    public CompileError(String message, String context) {
        this(message, context, Lists.empty());
    }

    @Override
    public String display() {
        String joined = errors.stream()
                .map(CompileError::display)
                .collect(new Joiner(""))
                .orElse("");

        return message + ": " + context + joined;
    }
}
