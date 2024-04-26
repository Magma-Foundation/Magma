package com.meti.rule;

import com.meti.collect.JavaString;
import com.meti.node.NodePrototype;
import com.meti.option.Option;

public class SplitAtFirstCharRule implements Rule {
    private final Rule left;
    private final Rule right;
    private final char c;

    public SplitAtFirstCharRule(char c, Rule left, Rule right) {
        this.left = left;
        this.right = right;
        this.c = c;
    }

    @Override
    public Option<NodePrototype> apply(JavaString input) {
        return input.splitAtFirstIndexOfCharExclusive(c)
                .flatMap(tuple -> left.apply(tuple.a()).and(right.apply(tuple.b())))
                .map(tuple -> tuple.a().merge(tuple.b()));
    }
}
