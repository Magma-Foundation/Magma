package com.meti.compile.rule;

import com.meti.collect.JavaList;
import com.meti.collect.JavaMap;
import com.meti.java.JavaString;

public class InclusiveDelimiterRule implements Rule {
    private final String delimiter;
    private final String left;
    private final String right;

    public InclusiveDelimiterRule(String delimiter, String left, String right) {
        this.delimiter = delimiter;
        this.left = left;
        this.right = right;
    }

    @Override
    public RuleResult apply(JavaString input) {
        return input.firstRangeOfSlice(delimiter).<RuleResult>map(range -> {
            var leftString = input.sliceTo(range.startIndex());
            var rightString = input.sliceFrom(range.startIndex());

            return new MapRuleResult(new JavaMap<JavaString, JavaString>()
                    .put(new JavaString(left), leftString)
                    .put(new JavaString(right), rightString),
                    new JavaMap<>());
        }).orElse(EmptyRuleResult.Empty);
    }
}
