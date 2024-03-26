package com.meti.rule;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

public class Rules {
    public static final Rule DIGIT = new RangeRule('0', '9');
    public static final Rule ALPHABETIC_LOWER = new RangeRule('a', 'z');
    public static final Rule ALPHABETIC_UPPER = new RangeRule('A', 'Z');
    public static final Rule ALPHABETIC = new OrRule(ALPHABETIC_LOWER, ALPHABETIC_UPPER);
    public static final Rule ALPHANUMERIC = new OrRule(ALPHABETIC, DIGIT);

    public static Rule EMPTY = input -> {
        if (input.isEmpty()) return Optional.of(Collections.emptyMap());
        else return Optional.empty();
    };
    public static Rule Any = input -> Optional.of(new HashMap<>());

    public static Rule ExtractSymbol(String name) {
        return new ExtractTextRule(name, new AndRule(ALPHABETIC, new ListRule(ALPHANUMERIC)));
    }

    public static Rule Enum(String first, String... more) {
        return OrRule.Or(new RequireRule(first), Arrays.stream(more)
                .map(RequireRule::new)
                .toList()
                .toArray(RequireRule[]::new));
    }
}
