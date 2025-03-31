package magma.compile;

import jvm.collect.list.Lists;
import jvm.collect.string.Strings;
import magma.collect.list.List_;
import magma.collect.stream.Joiner;
import magma.collect.stream.head.HeadedStream;
import magma.collect.stream.head.RangeHead;
import magma.compile.context.Context;
import magma.error.Error;
import magma.option.None;
import magma.option.Option;
import magma.option.Some;

import java.util.function.Function;

public class CompileError implements Error {
    private final String message;
    private final Option<Context> maybeContext;
    private final List_<CompileError> errors;

    public CompileError(String message, Context maybeContext) {
        this(message, maybeContext, Lists.empty());
    }

    public CompileError(String message, Context maybeContext, List_<CompileError> errors) {
        this.message = message;
        this.maybeContext = new Some<>(maybeContext);
        this.errors = errors;
    }

    public CompileError(String message) {
        this.message = message;
        this.maybeContext = new None<>();
        this.errors = Lists.empty();
    }

    private static String format(int depth, int index, List_<CompileError> sorted) {
        String format = "\n%s%d) %s";
        String spacer = "| ".repeat(depth);
        String child = sorted.get(index).format(depth + 1);
        return format.formatted(spacer, index + 1, child);
    }

    public int depth() {
        return 1 + errors.stream()
                .map(CompileError::depth)
                .foldMapping(Function.identity(), Math::max)
                .orElse(0);
    }

    @Override
    public String display() {
        return format(0);
    }

    private String format(int depth) {
        List_<CompileError> sorted = errors.sort((error, error2) -> error.depth() - error2.depth());

        String joined = new HeadedStream<>(new RangeHead(sorted.size()))
                .map(index -> format(depth, index, sorted))
                .collect(new Joiner(""))
                .orElse("");

        String contextString = maybeContext.map(Context::display)
                .map(result -> ": " + Strings.unwrap(result))
                .orElse("");

        return message + contextString + joined;
    }
}
