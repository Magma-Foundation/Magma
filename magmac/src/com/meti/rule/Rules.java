package com.meti.rule;

import java.util.Arrays;

public class Rules {
    public static final Rule DIGIT = new RangeRule('0', '9');
    public static final Rule ALPHABETIC_LOWER = new RangeRule('a', 'z');
    public static final Rule ALPHABETIC_UPPER = new RangeRule('A', 'Z');
    public static final Rule ALPHABETIC = new OrRule(ALPHABETIC_LOWER, ALPHABETIC_UPPER);
    public static final Rule ALPHANUMERIC = new OrRule(ALPHABETIC, DIGIT);
    public static final Rule SYMBOL = new AndRule(ALPHABETIC, new ListRule(ALPHANUMERIC));

    public static Rule ExtractSymbol(String name) {
        return new ExtractTextRule(name, SYMBOL);
    }

    public static Rule Enum(String first, String second, String... more) {
        return OrRule.Or(new RequireRule(first), new RequireRule(second), Arrays.stream(more)
                .map(RequireRule::new)
                .toList()
                .toArray(RequireRule[]::new));
    }

    public static Rule Optional(Rule rule) {
        return new OrRule(EmptyRule.EMPTY, rule);
    }

}
