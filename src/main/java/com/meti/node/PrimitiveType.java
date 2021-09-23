package com.meti.node;

import java.util.stream.Stream;

public enum PrimitiveType implements Node {
    I16("I16", "int"),
    U16("U16", "unsigned int");

    private final String magmaType;
    private final String nativeType;

    PrimitiveType(String magmaType, String nativeType) {
        this.magmaType = magmaType;
        this.nativeType = nativeType;
    }

    @Override
    public Attribute apply(Attribute.Type type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Group group() {
        return Group.Primitive;
    }

    @Override
    public boolean isFlagged(Declaration.Flag flag) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String renderMagma() {
        return magmaType;
    }

    @Override
    public String renderNative() {
        return nativeType;
    }

    @Override
    public Stream<Node> streamTypes() {
        return Stream.empty();
    }

    @Override
    public Node withType(Node type) {
        return this;
    }
}
