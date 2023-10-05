package com.meti.compile.rule;

import com.meti.api.collect.JavaString;
import com.meti.api.iterate.Iterators;
import com.meti.api.option.Option;

import static com.meti.api.option.Options.$$;
import static com.meti.api.option.Options.$Option;

public record ConjunctionRule(Rule left, Rule right) implements Rule {
    public static Rule of(Rule first, Rule... more) {
        return Iterators.from(more).foldRight(first, ConjunctionRule::new);
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
                    var extractedLeft = left.extract(leftSlice);
                    var extractedRight = right.extract(rightSlice);
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
