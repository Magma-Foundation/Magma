package com.meti.node;

import com.meti.Tuple;

import java.util.Optional;

public record MapNode(String type, NodeAttributes node) {
    public static Optional<MapNode> fromTuple(Tuple<NodeAttributes, Optional<String>> tuple) {
        return tuple.right().map(name -> new MapNode(name, tuple.left()));
    }

    public Optional<Attribute> apply(String name) {
        return node.apply(name);
    }
}