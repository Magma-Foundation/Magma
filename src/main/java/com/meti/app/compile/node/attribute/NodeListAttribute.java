package com.meti.app.compile.node.attribute;

import com.meti.api.ListStream;
import com.meti.api.Stream;
import com.meti.app.compile.node.Node;

import java.util.List;

public record NodeListAttribute(List<? extends Node> children) implements Attribute {
    @Override
    public Stream<? extends Node> asNodeStream() {
        return new ListStream<>(children);
    }
}
