package com.meti.node;

import com.meti.ApplicationException;

import java.util.stream.Stream;

public class Block implements Node {
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
        return "{}";
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
    public Node withNode(Node node) {
        return null;
    }

    @Override
    public Node withType(Node type) {
        return null;
    }
}
