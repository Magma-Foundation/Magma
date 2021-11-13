package com.meti;

import java.util.List;

public record NodesAttribute(List<Node> children) implements Attribute {
    @Override
    public Stream<Node> asNodeStream() {
        return new JavaListStream<>(children);
    }
}
