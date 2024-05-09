package com.meti.rule;

import com.meti.Tuple;

import java.util.Map;
import java.util.Optional;

public record NamingRule(String name, Rule rule) implements Rule {

    public static NamingRule Named(String name, Rule left) {
        return new NamingRule(name, left);
    }

    @Override
    public Optional<Tuple<Map<String, String>, Optional<String>>> apply(String stripped) {
        return rule.apply(stripped).map(tuple -> tuple.replaceRight(Optional.of(name)));
    }
}