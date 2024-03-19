package com.meti.compile.rule;

import com.meti.collect.option.Option;
import com.meti.collect.stream.Streams;
import com.meti.java.JavaString;

import static com.meti.compile.rule.ConjunctionRule.Join;
import static com.meti.compile.rule.DisjunctionRule.Or;
import static com.meti.compile.rule.EmptyRule.Empty;

public class ListRule implements Rule {
    private final Rule value;
    private final Rule delimiter;

    private ListRule(Rule value, Rule delimiter) {
        this.value = value;
        this.delimiter = delimiter;
    }

    public static ListRule List(Rule value, Rule delimiter) {
        return new ListRule(value, delimiter);
    }

    @Override
    public Option<RuleResult> apply(JavaString input) {
        var lazy = new LazyRule();
        var more = Join(value, delimiter, lazy);
        var rule = Or(Empty, value, more);
        lazy.setCurrent(rule);

        return rule.apply(input);
    }
}
