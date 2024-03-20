package com.meti.compile.rule;

import com.meti.collect.option.Option;
import com.meti.collect.stream.Streams;
import com.meti.java.JavaString;

public class ConjunctionRule implements Rule {
    private final Rule left;
    private final Rule right;

    public ConjunctionRule(Rule left, Rule right) {
        this.left = left;
        this.right = right;
    }

    public static Rule Join(Rule first, Rule... more) {
        return Streams.from(more).foldRightFromInitial(first, ConjunctionRule::new);
    }

    @Override
    public Option<RuleResult> apply(JavaString input) {
        var map = input.streamIndices().map(length -> {
            var left = input.sliceTo(length);
            var right = input.sliceFrom(length);

            return this.left.apply(left)
                    .andLazy(() -> this.right.apply(right))
                    .map(tuple -> MapRuleResult.merge(tuple.a(), tuple.b()));
        });
        var next = map.flatMap(Streams::fromOption).next();
        return next;
    }
}
