package com.meti;

import java.util.stream.Stream;

public class ImplicitType implements Node {
    public static final Node ImplicitType_ = new ImplicitType();

    private ImplicitType() {
    }

    @Override
    public Node getType() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getValue() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Group group() {
        return Group.Implicit;
    }

    @Override
    public Stream<Node> streamTypes() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Node withType(Node type) {
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
}
