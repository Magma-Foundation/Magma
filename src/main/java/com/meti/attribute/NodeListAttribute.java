package com.meti.attribute;

import com.meti.feature.Node;

import java.util.List;
import java.util.stream.Stream;

public record NodeListAttribute(List<? extends Node> children) implements Attribute {
    @Override
    public Stream<? extends Node> asNodeStream() {
        return children.stream();
    }
}
