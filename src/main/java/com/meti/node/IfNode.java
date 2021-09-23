package com.meti.node;

import com.meti.ApplicationException;

import java.util.stream.Stream;

public class IfNode implements Node {
    private final Node condition;

    public IfNode(Node condition) {
        this.condition = condition;
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
        return "if(" + condition.renderNative() + "){}";
    }

    @Override
    public Stream<Node> streamNodes() {
        return Stream.of(condition);
    }

    @Override
    public Stream<Node> streamTypes() {
        return Stream.empty();
    }

    @Override
    public Node withNode(Node node) {
        return new IfNode(node);
    }

    @Override
    public Node withType(Node type) {
        return null;
    }
}
