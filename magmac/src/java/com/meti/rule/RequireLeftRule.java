package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.NodeAttributes;

import java.util.Map;
import java.util.Optional;

public record RequireLeftRule(String slice, Rule right) implements Rule {
    public static Rule Left(String slice, Rule right) {
        return new RequireLeftRule(slice, right);
    }

    private Optional<Tuple<Map<String, String>, Optional<String>>> apply2(String value) {
        if (!value.startsWith(this.slice)) return Optional.empty();

        var segments = value.substring(this.slice.length());
        return this.right.apply(segments).map(tuple -> tuple.mapLeft(NodeAttributes::map));
    }

    @Override
    public Optional<Tuple<NodeAttributes, Optional<String>>> apply(String value) {
        return apply2(value).map(tuple -> tuple.mapLeft(NodeAttributes::new));
    }
}