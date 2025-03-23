package magma.error;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public int computeDepth() {
        return 1 + children.stream()
                .mapToInt(CompileError::computeDepth)
                .max()
                .orElse(0);
    }

    @Override
    public String display() {
        return format(0);
    }

    private String format(int depth) {
        List<CompileError> sorted = children.stream()
                .sorted(Comparator.comparingInt(CompileError::computeDepth))
                .toList();

        String joinedChildren = IntStream.range(0, sorted.size())
                .mapToObj(index -> "\n" + "\t".repeat(depth + 1) + index + ": " + sorted.get(index).format(depth + 1))
                .collect(Collectors.joining());

        return  message + ": " + context + joinedChildren;
    }
}
