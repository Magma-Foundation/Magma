package com.meti.compile.rule;

import com.meti.collect.Index;
import com.meti.collect.JavaList;
import com.meti.collect.option.Option;
import com.meti.collect.stream.Streams;
import com.meti.java.JavaString;

public class ConjunctionRule implements Rule {
    private final Rule left;
    private final Rule right;

    @Override
    public String toString() {
        return left.toString() + " " + right.toString();
    }

    public ConjunctionRule(Rule left, Rule right) {
        this.left = left;
        this.right = right;
    }

    public static Rule Join(Rule first, Rule... more) {
        try {
            var reversed = JavaList.from(first).addAll(JavaList.from(more))
                    .reverse();

            var tuple = reversed.popFirst().$();
            return tuple.b().stream().foldRightFromInitial(tuple.a(), (left1, right1) -> new ConjunctionRule(right1, left1));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Option<RuleResult> apply(JavaString input) {
        return input.streamIndices()
                .map(length -> splitAt(input, length))
                .flatMap(Streams::fromOption)
                .next();
    }

    private Option<RuleResult> splitAt(JavaString input, Index length) {
        var left = input.sliceTo(length);
        var right = input.sliceFrom(length);

        return this.left.apply(left)
                .andLazy(() -> this.right.apply(right))
                .map(tuple -> MapRuleResult.merge(tuple.a(), tuple.b()));
    }
}
