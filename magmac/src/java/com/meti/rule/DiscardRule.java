package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.MapNode;
import com.meti.node.NodeAttributes;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class DiscardRule implements Rule {
    public static final Rule Empty = new DiscardRule();

    private DiscardRule() {
    }

    @Override
    public Optional<Tuple<NodeAttributes, Optional<String>>> fromString(String value) {
        return Optional.of(new Tuple<>(new NodeAttributes(Collections.emptyMap()), Optional.empty()));
    }

    @Override
    public Optional<String> toString(MapNode node) {
        return Optional.of("");
    }
}
