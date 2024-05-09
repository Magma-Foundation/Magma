package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.MapNode;
import com.meti.node.NodeAttribute;
import com.meti.node.NodeAttributes;

import java.util.Map;
import java.util.Optional;

public record NodeRule(String name, Rule parent) implements Rule {
    public static NodeRule Node(String name, NamingRule name1) {
        return new NodeRule(name, name1);
    }

    @Override
    public Optional<Tuple<NodeAttributes, Optional<String>>> apply(String value) {
        return parent.apply(value)
                .flatMap(tuple -> tuple.right().map(right -> new MapNode(right, tuple.left())))
                .map(this::toTuple);
    }

    private Tuple<NodeAttributes, Optional<String>> toTuple(MapNode node) {
        var attributes = new NodeAttributes(Map.of(name, new NodeAttribute(node)));
        return new Tuple<>(attributes, Optional.empty());
    }
}
