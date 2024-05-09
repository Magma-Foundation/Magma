package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.MapNode;
import com.meti.node.NodeAttribute;
import com.meti.node.NodeAttributes;
import com.meti.util.Options;

import java.util.Map;
import java.util.Optional;

public record NodeRule(String name, Rule parent) implements Rule {
    public static NodeRule Node(String name, TypeRule name1) {
        return new NodeRule(name, name1);
    }

    @Override
    public Optional<Tuple<NodeAttributes, Optional<String>>> fromString(String value) {
        return parent.fromString(value)
                .flatMap(tuple -> tuple.right().map(right -> new MapNode(right, tuple.left())))
                .map(this::toTuple);
    }

    private Tuple<NodeAttributes, Optional<String>> toTuple(MapNode node) {
        var attributes = new NodeAttributes(Map.of(name, new NodeAttribute(node)));
        return new Tuple<>(attributes, Optional.empty());
    }

    @Override
    public Optional<String> toString(MapNode node) {
        var apply = Options.toNative(node.apply(name));
        if (apply.isEmpty()) return Optional.empty();

        var childNode = Options.toNative(apply.get().asNode());
        if (childNode.isEmpty()) return Optional.empty();

        return parent.toString(childNode.get());
    }
}
