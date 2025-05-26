package magmac.app.compile.rule;

import magmac.app.compile.node.Node;
import magmac.app.compile.rule.result.InlineRuleResult;
import magmac.app.compile.rule.result.RuleResult;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public record OrRule(List<Rule> rules) implements Rule {
    private record State<T>(Optional<T> maybeValue) {
        private State() {
            this(Optional.empty());
        }

        State<T> withValue(T value) {
            if (this.maybeValue.isPresent()) {
                return this;
            }
            return new State<>(Optional.of(value));
        }

        RuleResult<T> toResult() {
            return new InlineRuleResult<>(this.maybeValue);
        }
    }

    @Override
    public RuleResult<Node> lex(String input) {
        return this.foldAll(rule1 -> rule1.lex(input));
    }

    private <T> RuleResult<T> foldAll(Function<Rule, RuleResult<T>> mapper) {
        return this.rules.stream().reduce(new State<T>(),
                        (state, rule) -> OrRule.foldElement(state, rule, mapper),
                        (_, next) -> next)
                .toResult();
    }

    private static <T> State<T> foldElement(State<T> state, Rule rule, Function<Rule, RuleResult<T>> mapper) {
        return mapper.apply(rule)
                .map(state::withValue)
                .toOptional()
                .orElse(state);
    }

    @Override
    public RuleResult<String> generate(Node node) {
        return this.foldAll(rule1 -> rule1.generate(node));
    }
}
