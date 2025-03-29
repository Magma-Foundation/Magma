package magma.compile.rule;

import magma.collect.list.List_;
import magma.compile.CompileError;
import magma.compile.Node;
import magma.compile.context.Context;
import magma.compile.context.NodeContext;
import magma.compile.context.StringContext;
import magma.result.Err;
import magma.result.Ok;
import magma.result.Result;

import java.util.function.Function;
import java.util.function.Supplier;

public record OrRule(List_<Rule> rules) implements Rule {
    private <T> Result<T, CompileError> apply(Function<Rule, Result<T, CompileError>> applicator, Supplier<Context> context) {
        return rules.stream()
                .foldWithInitial(new OrState<T>(), (orState, rule) -> applicator.apply(rule).match(orState::withValue, orState::withError))
                .toResult()
                .<Result<T, CompileError>>match(Ok::new, errors -> new Err<>(new CompileError("Invalid root segment", context.get(), errors)));
    }

    @Override
    public Result<Node, CompileError> parse(String input) {
        return apply(rule -> rule.parse(input), () -> new StringContext(input));
    }

    @Override
    public Result<String, CompileError> generate(Node input) {
        return apply(rule -> rule.generate(input), () -> new NodeContext(input));
    }
}