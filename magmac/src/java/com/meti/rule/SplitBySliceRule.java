package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.MapNode;

import java.util.Optional;

public abstract class SplitBySliceRule implements Rule {
    protected final Rule leftRule;
    protected final String slice;
    protected final Rule rightRule;

    public SplitBySliceRule(Rule leftRule, String slice, Rule rightRule) {
        this.leftRule = leftRule;
        this.slice = slice;
        this.rightRule = rightRule;
    }

    protected abstract int computeRightOffset();

    protected abstract int applyOperation(String value);

    protected abstract int computeLeftOffset();

    @Override
    public Optional<String> toString(MapNode node) {
        return leftRule.toString(node).flatMap(leftResult ->
                rightRule.toString(node).map(rightResult ->
                        leftResult + computeRight(rightResult)));
    }

    protected abstract String computeRight(String rightResult);

    @Override
    public RuleResult fromString(String value) {
        var separator = applyOperation(value);
        if (separator == -1)
            return new ErrorRuleResult("Delimiter '" + slice + "' could not be found in value.", value);

        var left = value.substring(0, separator + computeLeftOffset());
        var leftResult = leftRule.fromString(left);
        var leftMap = leftResult.getAttributes();
        if (leftMap.isEmpty()) return new ParentRuleResult("Left side is invalid.", value, leftResult);

        var right = value.substring(separator + computeRightOffset());
        var rightResult = rightRule.fromString(right);
        var rightMap = rightResult.getAttributes();
        if (rightMap.isEmpty()) return new ParentRuleResult("Right side is invalid.", value, rightResult);

        var attributes = leftMap.orElseThrow().add(rightMap.orElseThrow());
        return new NodeRuleResult(new Tuple<>(attributes, Optional.empty()));
    }
}
