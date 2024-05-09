package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.MapNode;
import com.meti.node.NodeAttributes;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class DiscardRule implements Rule {
    public static final Rule Discard = new DiscardRule();

    private DiscardRule() {
    }

    private Optional<Tuple<Map<String, String>, Optional<String>>> apply2() {
        return Optional.of(new Tuple<>(Collections.emptyMap(), Optional.empty()));
    }

    @Override
    public Optional<Tuple<NodeAttributes, Optional<String>>> fromString(String value) {
        return apply2().map(tuple -> tuple.mapLeft(NodeAttributes::fromStrings));
    }

    @Override
    public Optional<String> toString(MapNode node) {
        return Optional.of("");
    }
}
