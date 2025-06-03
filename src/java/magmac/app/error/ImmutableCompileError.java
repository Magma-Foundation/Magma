package magmac.app.error;

import magmac.api.Tuple2;
import magmac.api.collect.list.List;
import magmac.api.collect.list.Lists;
import magmac.api.iter.collect.Joiner;
import magmac.api.iter.collect.Max;
import magmac.app.compile.error.context.Context;
import magmac.app.compile.error.error.CompileError;

public record ImmutableCompileError(String message, Context context,
                                    List<CompileError> errors) implements CompileError {
    public ImmutableCompileError(String message, Context context) {
        this(message, context, Lists.empty());
    }

    private static String formatEntry(int depth, String display) {
        var repeated = "| ".repeat(depth + 1);
        return "\n" + repeated + display;
    }

    private static String joinSorted(int depth, List<CompileError> copy, List<Integer> indices) {
        return copy.iterWithIndices()
                .map((tuple) -> {
                    var indices1 = ImmutableCompileError.getIntegerList(copy, indices, tuple);
                    return tuple.right().format(depth + 1, indices1);
                })
                .map((String display) -> ImmutableCompileError.formatEntry(depth, display))
                .collect(new Joiner())
                .orElse("");
    }

    private static List<Integer> getIntegerList(List<CompileError> copy, List<Integer> indices, Tuple2<Integer, CompileError> tuple) {
        if (1 >= copy.size()) {
            return indices;
        }
        else {
            return indices.addLast(tuple.left());
        }
    }

    @Override
    public String display() {
        return this.format(0, Lists.empty());
    }

    @Override
    public String format(int depth, List<Integer> indices) {
        var copy = this.errors.sort((CompileError first, CompileError second) -> first.computeMaxDepth() - second.computeMaxDepth());
        var joined = ImmutableCompileError.joinSorted(depth, copy, indices);

        var joinedIndices = indices.iter()
                .map(String::valueOf)
                .collect(new Joiner("."))
                .orElse("");

        return joinedIndices + ") " + this.message + ": " + this.context.display() + joined;
    }

    @Override
    public int computeMaxDepth() {
        var compileErrorIter = this.errors.iter();
        return 1 + compileErrorIter.map(CompileError::computeMaxDepth)
                .collect(new Max())
                .orElse(0);
    }
}
