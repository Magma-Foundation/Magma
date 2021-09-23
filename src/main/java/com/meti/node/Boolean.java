package com.meti.node;

import com.meti.ApplicationException;

import java.util.stream.Stream;

public enum Boolean implements Node {
    False(false),
    True(true);

    private final boolean value;

    Boolean(boolean value) {
        this.value = value;
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
        return value ? "1" : "0";
    }

    @Override
    public Stream<Node> streamTypes() {
        return Stream.empty();
    }

    @Override
    public Node withType(Node type) {
        return null;
    }
}
