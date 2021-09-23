package com.meti.node;

import com.meti.ApplicationException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Block implements Node {
    private final List<Node> children;

    public Block(List<? extends Node> children) {
        this.children = new ArrayList<>(children);
    }

    @Override
    public Attribute apply(Attribute.Type type) throws ApplicationException {
        return null;
    }

    @Override
    public Group group() {
        return null;
    }

    @Override
    public boolean isFlagged(Declaration.Flag flag) {
        return false;
    }

    @Override
    public String renderMagma() {
        return null;
    }

    @Override
    public String renderNative() {
        return children.stream()
                .map(Node::renderNative)
                .collect(Collectors.joining("", "{", "}"));
    }

    @Override
    public Stream<Node> streamTypes() {
        return Stream.empty();
    }

    @Override
    public Stream<Node> streamNodes() {
        return Stream.empty();
    }

    @Override
    public Stream<Node> streamNodeGroups() {
        return children.stream();
    }

    @Override
    public Node withNodeGroup(List<Node> children) {
        return new Block(children);
    }

    @Override
    public Node withNode(Node node) {
        return null;
    }

    @Override
    public Node withType(Node type) {
        return null;
    }
}
