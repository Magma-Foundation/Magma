package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.MapNode;
import com.meti.node.NodeAttributes;

import java.util.Optional;

public record TypeRule(String type, Rule rule) implements Rule {

    public static TypeRule Type(String type, Rule left) {
        return new TypeRule(type, left);
    }

    @Override
    public Optional<Tuple<NodeAttributes, Optional<String>>> fromString(String value) {
        return rule.fromString(value).map(tuple1 -> tuple1.replaceRight(Optional.of(type)));
    }

    @Override
    public Optional<String> toString(MapNode node) {
        if (node.type().equals(type)) {
            return rule.toString(node);
        }
        return Optional.empty();
    }
}