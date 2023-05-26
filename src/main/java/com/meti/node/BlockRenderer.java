package com.meti.node;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class BlockRenderer implements Renderer{
    private final Node node;

    public BlockRenderer(Node node) {
        this.node = node;
    }

    @Override
    public Optional<String> render() {
        if (!node.is("block")) return Optional.empty();
        return Optional.of(node.apply("children")
                .flatMap(Attribute::asNodeList)
                .stream()
                .flatMap(Collection::stream)
                .map(node -> node.apply("value"))
                .flatMap(Optional::stream)
                .map(Attribute::asString)
                .flatMap(Optional::stream)
                .collect(Collectors.joining("", "{", "}")));
    }
}
