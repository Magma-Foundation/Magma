package com.meti;

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
    public Group group() {
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
}
