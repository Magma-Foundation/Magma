package magma.app.compile.rule;

import magma.api.collect.List_;
import magma.api.result.Result;
import magma.app.compile.CompileError;
import magma.app.compile.Context;
import magma.app.compile.Node;
import magma.app.compile.NodeContext;
import magma.app.compile.ParseState;
import magma.app.compile.StringContext;

import java.util.function.Function;

public record OrRule(List_<Rule> rules) implements Rule {
    private static <T> OrState<T> foldRule(Function<Rule, Result<T, CompileError>> function, OrState<T> orState, Rule rule) {
        if (orState.isPresent()) return orState;

        Result<T, CompileError> apply = function.apply(rule);
        return apply.match(orState::withValue, orState::withErr);
    }

    @Override
    public Result<Node, CompileError> parse(ParseState state, String input) {
        return foldRules(new StringContext(input), rule -> rule.parse(state, input));
    }

    private <T> Result<T, CompileError> foldRules(Context context, Function<Rule, Result<T, CompileError>> function) {
        return this.rules.stream()
                .foldWithInitial(new OrState<T>(), (orState, rule) -> foldRule(function, orState, rule))
                .toResult()
                .mapErr(errors -> new CompileError("No valid combinations", context, errors));
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return foldRules(new NodeContext(node), rule -> rule.generate(node));
    }
}