package magma.compile.rule.result;

import magma.api.Tuple;
import magma.compile.rule.Node;
import magma.compile.rule.Rule;

import java.util.Optional;

public abstract class SplitRule {
    protected final Rule leftRule;
    protected final String slice;
    protected final Rule rightRule;

    public SplitRule(Rule leftRule, String slice, Rule rightRule) {
        this.leftRule = leftRule;
        this.slice = slice;
        this.rightRule = rightRule;
    }

    public RuleResult toNode(String input) {
        var tuple = computeIndex(input).map(keywordIndex -> {
            var left1 = input.substring(0, keywordIndex);
            var right1 = input.substring(keywordIndex + slice.length());
            return new Tuple<>(left1, right1);
        });

        var attributes = tuple.flatMap(contentStart -> {
            var left = contentStart.left();
            var right = contentStart.right();

            return leftRule.toNode(left).findAttributes().flatMap(leftResult -> rightRule.toNode(right).findAttributes().map(inner -> inner.merge(leftResult)));
        });

        return new AdaptiveRuleResult(Optional.empty(), attributes);
    }

    protected abstract Optional<Integer> computeIndex(String input);

    public Optional<String> fromNode(Node attributes) {
        return leftRule.fromNode(attributes).flatMap(left -> rightRule.fromNode(attributes).map(right -> left + slice + right));
    }
}
