package magmac.app.compile.rule;

import magmac.api.result.Result;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.context.Context;
import magmac.app.compile.error.context.NodeContext;
import magmac.app.compile.node.Node;
import magmac.app.compile.error.context.StringContext;
import magmac.app.compile.error.CompileError;

import java.util.List;
import java.util.function.Function;

public record OrRule(List<Rule> rules) implements Rule {
    private static <T> OrState<T> foldElement(OrState<T> state, Rule rule, Function<Rule, Result<T, CompileError>> mapper) {
        return mapper.apply(rule).match(state::withValue, state::withError);
    }

    private Result<Node, CompileError> lex0(String input) {
        return this.foldAll(rule1 -> rule1.lex(input).result(), new StringContext(input));
    }

    private <T> Result<T, CompileError> foldAll(Function<Rule, Result<T, CompileError>> mapper, Context context) {
        return this.rules.stream().reduce(new OrState<T>(),
                        (state, rule) -> OrRule.foldElement(state, rule, mapper),
                        (_, next) -> next)
                .toResult(context);
    }

    private Result<String, CompileError> generate0(Node node) {
        return this.foldAll(rule1 -> rule1.generate(node).result(), new NodeContext(node));
    }

    @Override
    public CompileResult<Node> lex(String input) {
        return new CompileResult<>(this.lex0(input));
    }

    @Override
    public CompileResult<String> generate(Node node) {
        return new CompileResult<>(this.generate0(node));
    }
}
