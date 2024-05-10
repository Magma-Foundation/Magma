package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.MapNode;
import com.meti.node.NodeAttributes;

import java.util.Optional;

public record TypeRule(String type, Rule rule) implements Rule {

    public static TypeRule Type(String type, Rule left) {
        return new TypeRule(type, left);
    }

    private Optional<Tuple<NodeAttributes, Optional<String>>> fromString1(String value) {
        try {
            return rule.fromString(value).unwrap().map(tuple1 -> tuple1.replaceRight(Optional.of(type)));
        } catch (RuleException e) {
            throw new RuleException("Failed to build node '" + type + "': " + value, e);
        }
    }

    @Override
    public Optional<String> toString(MapNode node) {
        if (node.type().equals(type)) {
            return rule.toString(node);
        }
        return Optional.empty();
    }

    @Override
    public RuleResult fromString(String value) {
        return new RuleResult(fromString1(value));
    }
}