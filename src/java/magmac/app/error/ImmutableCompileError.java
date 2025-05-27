package magmac.app.error;

import magmac.app.compile.Context;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public record ImmutableCompileError(String message, Context context,
                                    List<CompileError> errors) implements CompileError {
    public ImmutableCompileError(String message, Context context) {
        this(message, context, new ArrayList<>());
    }

    @Override
    public String display() {
        return this.format(0);
    }

    @Override
    public String format(int depth) {
        List<CompileError> copy = new ArrayList<>(this.errors);
        copy.sort(Comparator.comparingInt(CompileError::computeMaxDepth));

        String joined = copy.stream()
                .map(compileError -> compileError.format(depth + 1))
                .map(display -> "\n" + "\t".repeat(depth) + display)
                .collect(Collectors.joining());

        return this.message + ": " + this.context.display() + joined;
    }

    @Override
    public int computeMaxDepth() {
        return 1 + this.errors.stream()
                .mapToInt(CompileError::computeMaxDepth)
                .max()
                .orElse(0);
    }
}
