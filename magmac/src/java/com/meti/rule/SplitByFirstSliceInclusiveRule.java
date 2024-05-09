package com.meti.rule;

public final class SplitByFirstSliceInclusiveRule extends SplitBySliceRule {
    public SplitByFirstSliceInclusiveRule(Rule leftRule, String slice, Rule rightRule) {
        super(leftRule, slice, rightRule);
    }

    public static Rule FirstInclusive(Rule parent, String slice, Rule child) {
        return new SplitByFirstSliceInclusiveRule(parent, slice, child);
    }

    @Override
    protected int applyOperation(String value) {
        return value.indexOf(slice);
    }

    @Override
    protected int computeOffset() {
        return 0;
    }

    @Override
    protected String computeRight(String rightResult) {
        return rightResult;
    }
}