package magmac.app.error;

import magmac.api.collect.list.List;
import magmac.api.collect.list.Lists;
import magmac.api.iter.Iter;
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
        String repeated = "| ".repeat(depth + 1);
        return "\n" + repeated + display;
    }

    @Override
    public String display() {
        return this.format(0);
    }

    @Override
    public String format(int depth) {
        List<CompileError> copy = this.errors.sort((CompileError first, CompileError second) -> first.computeMaxDepth() - second.computeMaxDepth());
        String joined = ImmutableCompileError.joinSorted(depth, copy);
        return this.message + ": " + this.context.display() + joined;
    }

    private static String joinSorted(int depth, List<CompileError> copy) {
        return copy.iter()
                .map((CompileError compileError) -> compileError.format(depth + 1))
                .map((String display) -> ImmutableCompileError.formatEntry(depth, display))
                .collect(new Joiner())
                .orElse("");
    }

    @Override
    public int computeMaxDepth() {
        Iter<CompileError> compileErrorIter = this.errors.iter();
        return 1 + compileErrorIter.map(CompileError::computeMaxDepth)
                .collect(new Max())
                .orElse(0);
    }
}
