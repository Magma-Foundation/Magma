package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.MapNode;
import com.meti.node.NodeAttributes;

import java.util.Optional;

public record RequireRightRule(Rule right, String slice) implements Rule {
    public static Rule Right(Rule right, String slice) {
        return new RequireRightRule(right, slice);
    }

    @Override
    public Optional<Tuple<NodeAttributes, Optional<String>>> fromString(String value) {
        if (!value.endsWith(this.slice)) return Optional.empty();

        try {
            var endIndex = value.length() - this.slice.length();
            var segments = value.substring(0, endIndex);
            return this.right.fromString(segments);
        } catch (RuleException e) {
            throw new RuleException(value, e);
        }
    }

    @Override
    public Optional<String> toString(MapNode node) {
        return right.toString(node).map(value -> value + slice);
    }
}