package magma.compile.rule;

import magma.compile.AdaptiveRuleResult;
import magma.compile.RuleResult;
import magma.compile.attribute.Attributes;

import java.util.Optional;

public record StripRule(Rule child) implements Rule{
    private Optional<Attributes> toNode0(String input) {
        return child.toNode(input.strip()).findAttributes();
    }

    @Override
    public Optional<String> fromNode(Attributes attributes) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RuleResult toNode(String input) {
        return new AdaptiveRuleResult(Optional.empty(), toNode0(input));
    }
}
