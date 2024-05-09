package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.NodeAttributes;

import java.util.Map;
import java.util.Optional;

public record NamingRule(String name, Rule rule) implements Rule {

    public static NamingRule Named(String name, Rule left) {
        return new NamingRule(name, left);
    }

    private Optional<Tuple<Map<String, String>, Optional<String>>> apply2(String stripped) {
        return rule.apply(stripped).map(tuple1 -> tuple1.mapLeft(NodeAttributes::map)).map(tuple -> tuple.replaceRight(Optional.of(name)));
    }

    @Override
    public Optional<Tuple<NodeAttributes, Optional<String>>> apply(String value) {
        return apply2(value).map(tuple -> tuple.mapLeft(NodeAttributes::new));
    }
}