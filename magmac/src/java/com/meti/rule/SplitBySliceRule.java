package com.meti.rule;

import com.meti.Tuple;
import com.meti.api.Result;
import com.meti.api.ThrowableOption;
import com.meti.node.MapNode;
import com.meti.api.Options;

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

    private Optional<String> toString1(MapNode node) {
        return Options.toNative(leftRule.toString(node).findValue()).flatMap(leftResult ->
                Options.toNative(rightRule.toString(node).findValue()).map(rightResult ->
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

    @Override
    public Result<String, RuleException> toString(MapNode node) {
        return Options.fromNative(toString1(node))
                .into(ThrowableOption::new)
                .orElseThrow(() -> new RuleException("No value present."));
    }
}
