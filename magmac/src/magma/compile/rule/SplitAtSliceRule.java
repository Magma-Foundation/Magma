package magma.compile.rule;

import magma.compile.Attributes;

import java.util.Optional;

public record SplitAtSliceRule(Rule leftRule, String slice, Rule rightRule) implements Rule {
    @Override
    public Optional<Attributes> toNode(String input) {
        return Rules.splitAtSlice(input, slice()).flatMap(contentStart -> {
            var left = contentStart.left();
            var right = contentStart.right();

            return leftRule().toNode(left).flatMap(leftResult -> rightRule().toNode(right).map(inner -> inner.merge(leftResult)));
        });
    }

    @Override
    public Optional<String> fromNode(Attributes attributes) {
        return leftRule.fromNode(attributes).flatMap(left -> rightRule.fromNode(attributes).map(right -> left + slice + right));
    }
}