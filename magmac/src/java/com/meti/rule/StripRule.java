package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.NodeAttributes;

import java.util.Map;
import java.util.Optional;

public record StripRule(Rule next) implements Rule {
    public static StripRule Strip(Rule rule) {
        return new StripRule(rule);
    }

    private Optional<Tuple<Map<String, String>, Optional<String>>> apply2(String value) {
        return next.apply(value.strip()).map(tuple -> tuple.mapLeft(NodeAttributes::map));
    }

    @Override
    public Optional<Tuple<NodeAttributes, Optional<String>>> apply(String value) {
        return apply2(value).map(tuple -> tuple.mapLeft(NodeAttributes::new));
    }
}