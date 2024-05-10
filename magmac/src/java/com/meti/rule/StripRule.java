package com.meti.rule;

import com.meti.node.MapNode;

import java.util.Optional;

public record StripRule(Rule next) implements Rule {
    public static StripRule Strip(Rule rule) {
        return new StripRule(rule);
    }

    @Override
    public Optional<String> toString(MapNode node) {
        return next.toString(node);
    }

    @Override
    public RuleResult fromString(String value) {
        return next.fromString(value.strip());
    }
}