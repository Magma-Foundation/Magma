package com.meti;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Block implements Node {
    private final List<Node> children;

    public Block(Node...children) {
        this(List.of(children));
    }

    public Block(List<Node> children) {
        this.children = new ArrayList<>(children);
    }

    @Override
    public String render() {
        return children.stream()
                .map(Node::render)
                .collect(Collectors.joining("", "{", "}"));
    }
}
