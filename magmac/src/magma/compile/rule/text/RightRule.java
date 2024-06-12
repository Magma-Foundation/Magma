package magma.compile.rule.text;

import magma.compile.attribute.Attributes;
import magma.compile.rule.Node;
import magma.compile.rule.Rule;
import magma.compile.rule.result.AdaptiveRuleResult;
import magma.compile.rule.result.RuleResult;

import java.util.Optional;

public record RightRule(Rule child, String slice) implements Rule {
    private Optional<Attributes> toNode0(String input) {
        if (!input.endsWith(slice)) return Optional.empty();

        var contentEnd = input.length() - slice.length();
        var content = input.substring(0, contentEnd);
        return child.toNode(content).findAttributes();
    }

    @Override
    public RuleResult toNode(String input) {
        return new AdaptiveRuleResult(Optional.empty(), toNode0(input));
    }

    @Override
    public Optional<String> fromNode(Node attributes) {
        return child.fromNode(attributes).map(inner -> inner + slice);
    }
}