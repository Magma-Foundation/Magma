package magma.compile.rule;

import magma.compile.AdaptiveRuleResult;
import magma.compile.RuleResult;
import magma.compile.attribute.Attributes;

import java.util.Optional;

public record RightRule(Rule child, String slice) implements Rule {
    private Optional<Attributes> toNode0(String input) {
        if (!input.endsWith(slice)) return Optional.empty();

        var contentEnd = input.length() - slice.length();
        var content = input.substring(0, contentEnd);
        return child.toNode(content).findAttributes();
    }

    @Override
    public Optional<String> fromNode(Attributes attributes) {
        return child.fromNode(attributes).map(inner -> inner + slice);
    }

    @Override
    public RuleResult toNode(String input) {
        return new AdaptiveRuleResult(Optional.empty(), toNode0(input));
    }
}