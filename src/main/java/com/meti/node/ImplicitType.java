package com.meti.node;

import java.util.stream.Stream;

public class ImplicitType implements Node {
    public static final Node ImplicitType_ = new ImplicitType();

    private ImplicitType() {
    }

    @Override
    public Attribute apply(Attribute.Type type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Group group() {
        return Group.Implicit;
    }

    @Override
    public boolean isFlagged(Declaration.Flag flag) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String renderMagma() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String renderNative() {
        throw new UnsupportedOperationException("Implicit types can't be rendered. Did the compiler remove this type?");
    }

    @Override
    public Stream<Node> streamNodes() {
        return Stream.empty();
    }

    @Override
    public Stream<Node> streamTypes() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Node withNode(Node node) {
        return this;
    }

    @Override
    public Node withType(Node type) {
        throw new UnsupportedOperationException();
    }
}
