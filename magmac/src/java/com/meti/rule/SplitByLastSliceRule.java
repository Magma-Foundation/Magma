package com.meti.rule;

public final class SplitByLastSliceRule extends SplitBySliceRule {
    public SplitByLastSliceRule(Rule leftRule, String slice, Rule rightRule) {
        super(leftRule, slice, rightRule);
    }

    public static Rule Last(Rule parent, String slice, Rule child) {
        return new SplitByLastSliceRule(parent, slice, child);
    }

    @Override
    protected int applyOperation(String value) {
        return value.lastIndexOf(slice);
    }

    @Override
    protected int computeOffset() {
        return slice.length();
    }

    @Override
    protected String computeRight(String rightResult) {
        return slice + rightResult;
    }
}