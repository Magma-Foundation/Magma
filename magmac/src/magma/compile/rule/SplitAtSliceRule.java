package magma.compile.rule;

import magma.compile.AdaptiveRuleResult;
import magma.compile.RuleResult;
import magma.compile.attribute.Attributes;

import java.util.Optional;

public record SplitAtSliceRule(Rule leftRule, String slice, Rule rightRule) implements Rule {
    private Optional<Attributes> toNode0(String input) {
        return Rules.splitAtSlice(input, slice()).flatMap(contentStart -> {
            var left = contentStart.left();
            var right = contentStart.right();

            return this.leftRule().toNode(left).findAttributes().flatMap(leftResult -> this.rightRule().toNode(right).findAttributes().map(inner -> inner.merge(leftResult)));
        });
    }

    @Override
    public Optional<String> fromNode(Attributes attributes) {
        return leftRule.fromNode(attributes).flatMap(left -> rightRule.fromNode(attributes).map(right -> left + slice + right));
    }

    @Override
    public RuleResult toNode(String input) {
        return new AdaptiveRuleResult(Optional.empty(), toNode0(input));
    }
}