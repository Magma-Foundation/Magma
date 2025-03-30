package magma.compile.rule;

import magma.compile.CompileError;
import magma.compile.Node;
import magma.compile.context.Context;
import magma.compile.context.NodeContext;
import magma.compile.context.StringContext;
import magma.option.None;
import magma.option.Option;
import magma.option.Some;
import magma.result.Err;
import magma.result.Result;

import java.util.function.Function;

public class LazyRule implements Rule {
    private Option<Rule> child = new None<>();

    @Override
    public Result<Node, CompileError> parse(String input) {
        return withChildSet(inner -> inner.parse(input), new StringContext(input));
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return withChildSet(inner -> inner.generate(node), new NodeContext(node));
    }

    private <T> Result<T, CompileError> withChildSet(Function<Rule, Result<T, CompileError>> mapper, Context context) {
        return child.map(mapper).orElseGet(() -> new Err<>(new CompileError("Child not set", context)));
    }

    public void set(Rule child) {
        this.child = new Some<>(child);
    }
}
