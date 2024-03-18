package com.meti.compile.rule;

import com.meti.collect.JavaMap;
import com.meti.java.JavaString;

public class ExclusiveDelimiterRule implements Rule {
    private final String delimiter;
    private final Rule left;
    private final Rule right;

    public ExclusiveDelimiterRule(String delimiter, Rule left, Rule right) {
        this.delimiter = delimiter;
        this.left = left;
        this.right = right;
    }

    @Override
    public RuleResult apply(JavaString input) {
        return input.firstRangeOfSlice(delimiter).<RuleResult>map(range -> {
            var leftString = input.sliceTo(range.startIndex());
            var rightString = input.sliceFrom(range.endIndex());

            var leftResult = left.apply(leftString);
            var rightResult = right.apply(rightString);

            return MapRuleResult.merge(leftResult, rightResult);
        }).orElse(EmptyRuleResult.Empty);
    }
}
