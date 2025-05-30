package magmac.app.compile.rule;

import magmac.api.collect.list.List;
import magmac.api.iter.Iter;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.context.Context;
import magmac.app.compile.error.context.NodeContext;
import magmac.app.compile.error.context.StringContext;
import magmac.app.compile.node.Node;

import java.util.function.Function;

public record OrRule(List<Rule> rules) implements Rule {
    private static <T> OrState<T> foldElement(OrState<T> state, Rule rule, Function<Rule, CompileResult<T>> mapper) {
        return mapper.apply(rule).match(state::withValue, state::withError);
    }

    private <T> CompileResult<T> foldAll(Function<Rule, CompileResult<T>> mapper, Context context) {
        Iter<Rule> ruleIter = this.rules.iter();
        OrState<T> initial = new OrState<T>();
        return ruleIter.fold(initial, (OrState<T> state, Rule rule) -> OrRule.foldElement(state, rule, mapper))
                .toResult(context);
    }

    @Override
    public CompileResult<Node> lex(String input) {
        return this.foldAll((Rule rule1) -> rule1.lex(input), new StringContext(input));
    }

    @Override
    public CompileResult<String> generate(Node node) {
        return this.foldAll((Rule rule1) -> rule1.generate(node), new NodeContext(node));
    }
}
