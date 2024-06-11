package magma.compile.rule;

import magma.compile.AdaptiveRuleResult;
import magma.compile.RuleResult;
import magma.compile.attribute.Attributes;

import java.util.Optional;

public record LeftRule(String slice, Rule child) implements Rule {
    private Optional<Attributes> toNode0(String input) {
        if (!input.startsWith(slice)) return Optional.empty();
        var content = input.substring(slice.length());
        return child.toNode(content).findAttributes();
    }

    @Override
    public Optional<String> fromNode(Attributes attributes) {
        return child.fromNode(attributes).map(inner -> slice + inner);
    }

    @Override
    public RuleResult toNode(String input) {
        return new AdaptiveRuleResult(Optional.empty(), toNode0(input));
    }
}