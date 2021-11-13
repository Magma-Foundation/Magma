package com.meti;

import java.util.List;

public record NodesAttribute(List<? extends Node> children) implements Attribute {
    @Override
    public Stream<? extends Node> asNodeStream() {
        return new JavaListStream<>(children);
    }
}
