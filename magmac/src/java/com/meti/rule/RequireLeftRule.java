package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.MapNode;
import com.meti.node.NodeAttributes;

import java.util.Optional;

public record RequireLeftRule(String slice, Rule right) implements Rule {
    public static Rule Left(String slice, Rule right) {
        return new RequireLeftRule(slice, right);
    }

    @Override
    public Optional<Tuple<NodeAttributes, Optional<String>>> fromString(String value) {
        if (!value.startsWith(this.slice)) return Optional.empty();

        var segments = value.substring(this.slice.length());
        return this.right.fromString(segments);
    }

    @Override
    public Optional<String> toString(MapNode node) {
        return right.toString(node).map(value -> slice + value);
    }
}