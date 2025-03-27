package magma;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CompileError implements Error {
    private final String message;
    private final String context;
    private final List<CompileError> children;

    public CompileError(String message, String context) {
        this(message, context, Collections.emptyList());
    }

    public CompileError(String message, String context, List<CompileError> children) {
        this.message = message;
        this.context = context;
        this.children = children;
    }

    @Override
    public String display() {
        return format();
    }

    private String format() {
        String joined = children.stream()
                .map(CompileError::format)
                .map(displayed -> "\n" + displayed)
                .collect(Collectors.joining());

        return message + ": " + context + joined;
    }
}
