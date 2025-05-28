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

    @Override
    public String display() {
        return this.format(0);
    }

    @Override
    public String format(int depth) {
        List<CompileError> copy = this.errors.sort((first, second) -> first.computeMaxDepth() - second.computeMaxDepth());

        String joined = copy.iter()
                .map(compileError -> compileError.format(depth + 1))
                .map(display -> "\n" + "| ".repeat(depth + 1) + display)
                .collect(new Joiner())
                .orElse("");

        return this.message + ": " + this.context.display() + joined;
    }

    @Override
    public int computeMaxDepth() {
        Iter<CompileError> compileErrorIter = this.errors.iter();
        return 1 + compileErrorIter.map(CompileError::computeMaxDepth)
                .collect(new Max())
                .orElse(0);
    }
}
