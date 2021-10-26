package com.meti.attribute;

import com.meti.feature.Node;

import java.util.List;
import java.util.stream.Stream;

public record NodeListAttribute(List<Node> children) implements Attribute {
    @Override
    public Stream<Node> asNodeStream() {
        return children.stream();
    }
}
