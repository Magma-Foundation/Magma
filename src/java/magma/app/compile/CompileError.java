package magma.app.compile;

import jvm.api.collect.Lists;
import magma.api.collect.Joiner;
import magma.api.collect.List_;
import magma.api.error.Error;

public class CompileError implements Error {
    private final String message;
    private final String context;
    private final List_<CompileError> errors;

    public CompileError(String message, String context) {
        this(message, context, Lists.empty());
    }

    public CompileError(String message, String context, List_<CompileError> errors) {
        this.message = message;
        this.context = context;
        this.errors = errors;
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
