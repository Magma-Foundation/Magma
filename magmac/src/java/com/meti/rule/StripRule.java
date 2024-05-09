package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.MapNode;
import com.meti.node.NodeAttributes;

import java.util.Optional;

public record StripRule(Rule next) implements Rule {
    public static StripRule Strip(Rule rule) {
        return new StripRule(rule);
    }

    @Override
    public Optional<Tuple<NodeAttributes, Optional<String>>> fromString(String value) {
        return next.fromString(value.strip());
    }

    @Override
    public Optional<String> toString(MapNode node) {
        throw new UnsupportedOperationException();
    }
}