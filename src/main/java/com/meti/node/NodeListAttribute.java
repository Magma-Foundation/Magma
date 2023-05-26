package com.meti.node;

import java.util.ArrayList;
import java.util.List;

public class NodeListAttribute implements Attribute {
    private final List<Node> nodes;

    public NodeListAttribute(List<? extends Node> nodes) {
        this.nodes = new ArrayList<>(nodes);
    }
}
