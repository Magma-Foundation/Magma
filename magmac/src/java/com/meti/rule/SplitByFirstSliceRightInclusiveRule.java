package com.meti.rule;

public final class SplitByFirstSliceRightInclusiveRule extends SplitBySliceRule {
    public SplitByFirstSliceRightInclusiveRule(Rule leftRule, String slice, Rule rightRule) {
        super(leftRule, slice, rightRule);
    }

    public static Rule FirstIncludeRight(Rule parent, String slice, Rule child) {
        return new SplitByFirstSliceRightInclusiveRule(parent, slice, child);
    }

    @Override
    protected int applyOperation(String value) {
        return value.indexOf(slice);
    }

    @Override
    protected int computeRightOffset() {
        return 0;
    }

    @Override
    protected String computeRight(String rightResult) {
        return rightResult;
    }

    @Override
    protected int computeLeftOffset() {
        return 0;
    }
}