package magmac.app.error;

import magmac.app.Error;
import magmac.app.compile.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record CompileError(String message, Context context, List<CompileError> errors) implements Error {
    public CompileError(String message, Context context) {
        this(message, context, new ArrayList<>());
    }

    @Override
    public String display() {
        return format(0);
    }

    private String format(int depth) {
        String joined = this.errors.stream()
                .map(compileError -> compileError.format(depth + 1))
                .map(display -> "\n" + "\t".repeat(depth) + display)
                .collect(Collectors.joining());

        return this.message + ": " + this.context.display() + joined;
    }
}
