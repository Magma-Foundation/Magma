package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.MapNode;
import com.meti.node.NodeAttributes;

import java.util.Optional;

public record NamingRule(String name, Rule rule) implements Rule {

    public static NamingRule Name(String name, Rule left) {
        return new NamingRule(name, left);
    }

    @Override
    public Optional<Tuple<NodeAttributes, Optional<String>>> fromString(String value) {
        return rule.fromString(value).map(tuple1 -> tuple1.replaceRight(Optional.of(name)));
    }

    @Override
    public Optional<String> toString(MapNode node) {
        throw new UnsupportedOperationException();
    }
}