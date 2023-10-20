package com.meti.compile.rule;

import com.meti.api.collect.JavaString;
import com.meti.api.iterate.Iterators;
import com.meti.api.option.Option;
import com.meti.api.option.Some;

import static com.meti.api.option.Options.$$;
import static com.meti.api.option.Options.$Option;

public record ConjunctionRule(Rule actualLeft, Rule actualRight) implements Rule {
    public static Rule of(Rule first, Rule... more) {
        return Iterators.from(more).foldRight(first, ConjunctionRule::new);
    }

    @Override
    public Option<Rule> left() {
        return Some.apply(actualLeft);
    }

    @Override
    public Option<Rule> right() {
        return Some.apply(actualRight);
    }

    @Override
    public Rule withLeft(Rule rule) {
        return new ConjunctionRule(rule, actualRight);
    }

    @Override
    public Rule withRight(Rule rule) {
        return new ConjunctionRule(actualLeft, rule);
    }

    @Override
    public Option<RuleResult> extract(JavaString value) {
        return $Option(() -> {
            for (int leftSliceSize = 0; leftSliceSize < value.length(); leftSliceSize++) {
                var windowCount = value.length() - leftSliceSize + 1;
                for (int temp = 0; temp < windowCount; temp++) {
                    var rightIndexStart = value.validateIndex(temp).$();
                    var leftSlice = value.sliceTo(rightIndexStart);
                    var rightSlice = value.sliceFrom(rightIndexStart);
                    var extractedLeft = actualLeft.extract(leftSlice);
                    var extractedRight = actualRight.extract(rightSlice);
                    var combined = extractedLeft.and(extractedRight).map(tuple -> tuple.a().add(tuple.b()));
                    if (combined.isPresent()) {
                        return combined.$();
                    }
                }
            }

            return $$();
        });
    }
}
