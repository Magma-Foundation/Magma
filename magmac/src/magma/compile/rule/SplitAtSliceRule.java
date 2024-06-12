package magma.compile.rule;

import magma.compile.attribute.Attributes;

import java.util.Optional;

public record SplitAtSliceRule(Rule leftRule, String slice, Rule rightRule) implements Rule {
    private Optional<String> fromNode0(Attributes attributes) {
        return leftRule.fromNode(new Node("", attributes)).flatMap(left -> rightRule.fromNode(new Node("", attributes)).map(right -> left + slice + right));
    }

    @Override
    public RuleResult toNode(String input) {
        return new AdaptiveRuleResult(Optional.empty(), Rules.splitAtSlice(input, this.slice()).flatMap(contentStart -> {
            var left = contentStart.left();
            var right = contentStart.right();

            return this.leftRule().toNode(left).findAttributes().flatMap(leftResult -> this.rightRule().toNode(right).findAttributes().map(inner -> inner.merge(leftResult)));
        }));
    }

    @Override
    public Optional<String> fromNode(Node attributes) {
        return fromNode0(attributes.attributes());
    }
}