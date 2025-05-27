package magmac.app.compile.rule;

import magmac.api.result.Result;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.context.Context;
import magmac.app.compile.error.context.NodeContext;
import magmac.app.compile.error.context.StringContext;
import magmac.app.compile.error.error.CompileError;
import magmac.app.compile.node.Node;

import java.util.List;
import java.util.function.Function;

public record OrRule(List<Rule> rules) implements Rule {
    private static <T> OrState<T> foldElement(OrState<T> state, Rule rule, Function<Rule, CompileResult<T>> mapper) {
        return mapper.apply(rule).match(state::withValue, state::withError);
    }

    private <T> CompileResult<T> foldAll(Function<Rule, CompileResult<T>> mapper, Context context) {
        return this.rules.stream().reduce(new OrState<T>(),
                        (state, rule) -> OrRule.foldElement(state, rule, mapper),
                        (_, next) -> next)
                .toResult(context);
    }

    @Override
    public CompileResult<Node> lex(String input) {
        return this.foldAll(rule1 -> rule1.lex(input), new StringContext(input));
    }

    @Override
    public CompileResult<String> generate(Node node) {
        return this.foldAll(rule1 -> rule1.generate(node), new NodeContext(node));
    }
}
