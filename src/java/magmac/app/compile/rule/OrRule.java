package magmac.app.compile.rule;

import magmac.app.compile.node.Node;
import magmac.app.compile.rule.result.InlineRuleResult;
import magmac.app.compile.rule.result.RuleResult;

import java.util.List;
import java.util.Optional;

public record OrRule(List<Rule> rules) implements Rule {
    private record State(Optional<Node> maybeValue) {
        private State() {
            this(Optional.empty());
        }

        State withValue(Node value) {
            if (this.maybeValue.isPresent()) {
                return this;
            }
            return new State(Optional.of(value));
        }

        RuleResult<Node> toResult() {
            return new InlineRuleResult<>(this.maybeValue);
        }
    }

    private static State foldRule(State state, Rule rule, String input) {
        return rule.lex(input)
                .map(state::withValue)
                .toOptional()
                .orElse(state);
    }

    @Override
    public RuleResult<Node> lex(String input) {
        return this.rules.stream()
                .reduce(new State(),
                        (state, rule) -> OrRule.foldRule(state, rule, input),
                        (_, next) -> next)
                .toResult();
    }
}
