package com.meti.compile.rule;

import com.meti.collect.stream.Stream;
import com.meti.collect.stream.Streams;
import com.meti.java.JavaString;

public class DisjunctionRule implements Rule {
    private final Rule left;
    private final Rule right;

    private DisjunctionRule(Rule left, Rule right) {
        this.left = left;
        this.right = right;
    }

    public static Rule Or(Rule first, Rule... more) {
        return Streams.from(more).foldRight(first, (rule, rule2) -> new DisjunctionRule(rule2, rule2));
    }

    @Override
    public Stream<RuleResult> apply(JavaString input) {
        return left.apply(input).concat(right.apply(input));
    }
}
