package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.Attribute;

import java.util.Map;
import java.util.Optional;

public class NamedRule implements Rule {
    private final Rule inner;
    private final String name;

    public NamedRule(String name, Rule inner) {
        this.inner = inner;
        this.name = name;
    }

    @Override
    public Optional<Tuple<Optional<String>, Map<String, Attribute>>> apply(String input) {
        return inner.apply(input).map(value -> value.replaceLeft(Optional.of(name)));
    }
}
