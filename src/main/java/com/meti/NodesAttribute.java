package com.meti;

import com.meti.node.Node;
import com.meti.stream.JavaListStream;
import com.meti.stream.Stream;

import java.util.List;

public record NodesAttribute(List<? extends Node> children) implements Attribute {
    @Override
    public Stream<? extends Node> asNodeStream() {
        return new JavaListStream<>(children);
    }
}
