package com.meti.compile.node.attribute;

import com.meti.compile.node.Node;

import java.util.List;
import java.util.stream.Stream;

public record NodeListAttribute(List<? extends Node> children) implements Attribute {
    @Override
    public Stream<? extends Node> asNodeStream() {
        return children.stream();
    }
}
