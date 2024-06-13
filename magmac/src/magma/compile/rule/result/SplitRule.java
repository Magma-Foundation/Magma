package magma.compile.rule.result;

import magma.api.Result;
import magma.api.Tuple;
import magma.compile.CompileException;
import magma.compile.rule.Node;
import magma.compile.rule.Rule;

import java.util.Optional;

public abstract class SplitRule implements Rule {
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

            var leftResult = leftRule.toNode(left);
            var rightResult = rightRule.toNode(right);
            return leftResult.findAttributes().flatMap(leftAttributes -> rightResult.findAttributes().map(rightAttributes -> rightAttributes.merge(leftAttributes)));
        });

        return new AdaptiveRuleResult(Optional.empty(), attributes);
    }

    protected abstract Optional<Integer> computeIndex(String input);

    @Override
    public Result<String, CompileException> fromNode(Node node) {
        return leftRule.fromNode(node).flatMapValue(left -> rightRule.fromNode(node).mapValue(right -> left + slice + right));
    }
}
