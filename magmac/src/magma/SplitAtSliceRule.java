package magma;

import java.util.Optional;

public record SplitAtSliceRule(Rule leftRule, String slice, Rule rightRule) implements Rule {
    @Override
    public Optional<Node> toNode(String input) {
        return Rules.splitAtSlice(input, slice()).flatMap(contentStart -> {
            var left = contentStart.left();
            var right = contentStart.right();

            return leftRule().toNode(left).flatMap(leftResult -> rightRule().toNode(right).map(inner -> inner.merge(leftResult)));
        });
    }

    @Override
    public Optional<String> fromNode(Node node) {
        return leftRule.fromNode(node).flatMap(left -> rightRule.fromNode(node).map(right -> left + slice + right));
    }
}