package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.Attribute;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public class OrRule implements Rule {
    private final Rule first;
    private final Rule second;

    public OrRule(Rule first, Rule second) {
        this.first = first;
        this.second = second;
    }

    public static Rule Or(Rule first, Rule... more) {
        return Arrays.stream(more).reduce(first, OrRule::new);
    }

    @Override
    public Optional<Tuple<Optional<String>, Map<String, Attribute>>> apply(String input) {
        return first.apply(input).or(() -> second.apply(input));
    }
}