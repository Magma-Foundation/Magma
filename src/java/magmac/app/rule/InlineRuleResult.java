package magmac.app.rule;

import magmac.app.node.Node;

import java.util.Optional;
import java.util.function.Supplier;

public final class InlineRuleResult implements RuleResult {
    private final Optional<Node> optional;

    public InlineRuleResult(Optional<Node> optional) {
        this.optional = optional;
    }

    static RuleResult createEmpty() {
        return new InlineRuleResult(Optional.empty());
    }

    public static RuleResult from(Node value) {
        return new InlineRuleResult(Optional.of(value));
    }

    @Override
    public Optional<Node> toOptional() {
        return this.optional;
    }

    @Override
    public RuleResult and(Supplier<RuleResult> other) {
        return new InlineRuleResult(this.optional.flatMap(inner -> {
            return other.get().toOptional().map(otherValue -> {
                return inner.merge(otherValue);
            });
        }));
    }
}
