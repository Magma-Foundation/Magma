package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.NodeAttributes;

import java.util.Map;
import java.util.Optional;

public record RequireRightRule(Rule right, String slice) implements Rule {
    public static Rule Right(Rule right, String slice) {
        return new RequireRightRule(right, slice);
    }

    private Optional<Tuple<Map<String, String>, Optional<String>>> apply2(String value) {
        if (!value.endsWith(this.slice)) return Optional.empty();

        var endIndex = value.length() - this.slice.length() - 1;
        var segments = value.substring(0, endIndex);
        return this.right.apply(segments).map(tuple -> tuple.mapLeft(NodeAttributes::map));
    }

    @Override
    public Optional<Tuple<NodeAttributes, Optional<String>>> apply(String value) {
        return apply2(value).map(tuple -> tuple.mapLeft(NodeAttributes::fromStrings));
    }
}