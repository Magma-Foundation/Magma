package com.meti.node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class NodeListAttribute implements Attribute {
    private final List<Node> nodes;

    public NodeListAttribute() {
        this(Collections.emptyList());
    }

    @Override
    public Optional<List<Node>> asNodeList() {
        return Optional.of(nodes);
    }

    public NodeListAttribute(List<? extends Node> nodes) {
        this.nodes = new ArrayList<>(nodes);
    }
}
