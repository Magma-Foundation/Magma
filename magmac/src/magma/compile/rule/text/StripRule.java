package magma.compile.rule.text;

import magma.compile.attribute.Attributes;
import magma.compile.rule.Node;
import magma.compile.rule.Rule;
import magma.compile.rule.result.AdaptiveRuleResult;
import magma.compile.rule.result.RuleResult;

import java.util.Optional;

public record StripRule(Rule child) implements Rule{
    private Optional<Attributes> toNode0(String input) {
        return child.toNode(input.strip()).findAttributes();
    }

    private Optional<String> fromNode0(Attributes attributes) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RuleResult toNode(String input) {
        return new AdaptiveRuleResult(Optional.empty(), toNode0(input));
    }

    @Override
    public Optional<String> fromNode(Node attributes) {
        return fromNode0(attributes.attributes());
    }
}