package com.meti;

import java.util.stream.Stream;

public class ImplicitType implements Node {
    public static final Node ImplicitType_ = new ImplicitType();

    private ImplicitType() {
    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public Group group() {
        return null;
    }

    @Override
    public Stream<Node> streamTypes() {
        return null;
    }

    @Override
    public Node withType(Node type) {
        return null;
    }

    @Override
    public String renderMagma() {
        return null;
    }

    @Override
    public String renderNative() {
        return null;
    }
}
