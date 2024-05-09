package com.meti.rule;

public final class SplitByFirstSliceLeftInclusiveRule extends SplitBySliceRule {
    public SplitByFirstSliceLeftInclusiveRule(Rule leftRule, String slice, Rule rightRule) {
        super(leftRule, slice, rightRule);
    }

    public static Rule FirstIncludeLeft(Rule parent, String slice, Rule child) {
        return new SplitByFirstSliceLeftInclusiveRule(parent, slice, child);
    }

    @Override
    protected int applyOperation(String value) {
        return value.indexOf(slice);
    }

    @Override
    protected int computeRightOffset() {
        return slice.length();
    }

    @Override
    protected String computeRight(String rightResult) {
        return rightResult;
    }

    @Override
    protected int computeLeftOffset() {
        return slice.length();
    }
}