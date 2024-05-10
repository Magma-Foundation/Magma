package com.meti.rule;

import com.meti.node.MapNode;

import java.util.Optional;

public record TypeRule(String type, Rule rule) implements Rule {

    public static TypeRule Type(String type, Rule left) {
        return new TypeRule(type, left);
    }

    @Override
    public Optional<String> toString(MapNode node) {
        return node.type().equals(type) ? rule.toString(node) : Optional.empty();
    }

    @Override
    public RuleResult fromString(String value) {
        return rule.fromString(value).withType(type);
    }
}