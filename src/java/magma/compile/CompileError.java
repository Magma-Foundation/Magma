package magma.compile;

import jvm.collect.list.Lists;
import magma.collect.Joiner;
import magma.collect.list.List_;
import magma.compile.context.Context;
import magma.error.Error;

public class CompileError implements Error {
    private final String message;
    private final Context context;
    private final List_<CompileError> errors;

    public CompileError(String message, Context context) {
        this(message, context, Lists.empty());
    }

    public CompileError(String message, Context context, List_<CompileError> errors) {
        this.message = message;
        this.context = context;
        this.errors = errors;
    }

    @Override
    public String display() {
        String joined = errors.stream()
                .map(CompileError::display)
                .map(value -> "\n" + value)
                .collect(new Joiner(""))
                .orElse("");

        return message + ": " + context.display() + joined;
    }
}
