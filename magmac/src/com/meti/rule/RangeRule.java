package com.meti.rule;

import com.meti.node.Attribute;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class RangeRule implements Rule {
    private final char start;
    private final char endInclusive;

    public RangeRule(char start, char endInclusive) {
        this.start = start;
        this.endInclusive = endInclusive;
    }

    @Override
    public Optional<Map<String, Attribute>> apply(String input) {
        if (input.length() == 1) {
            var first = input.charAt(0);
            if (start <= first && first <= endInclusive) {
                return Optional.of(Collections.emptyMap());
            }
        }

        return Optional.empty();
    }
}
