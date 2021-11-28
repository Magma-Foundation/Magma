package com.meti.app.attribute;

import com.meti.api.stream.JavaListStream;
import com.meti.api.stream.Stream;
import com.meti.app.node.Node;

import java.util.List;

public record NodesAttribute(List<? extends Node> children) implements Attribute {
    @Override
    public Stream<? extends Node> asNodeStream() {
        return new JavaListStream<>(children);
    }
}
