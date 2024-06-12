package magma.compile.rule;

import magma.compile.rule.result.EmptyRuleResult;
import magma.compile.rule.result.RuleResult;
import magma.compile.rule.result.TypedRuleResult;

import java.util.Optional;

public record TypeRule(String type, Rule child) implements Rule {
    @Override
    public RuleResult toNode(String input) {
        return child.toNode(input)
                .findAttributes()
                .<RuleResult>map(attributes -> new TypedRuleResult(type, attributes))
                .orElse(new EmptyRuleResult());
    }

    @Override
    public Optional<String> fromNode(Node attributes) {
        return attributes.type().equals(type)
                ? child.fromNode(attributes)
                : Optional.empty();
    }
}
