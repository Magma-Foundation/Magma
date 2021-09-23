package com.meti;

public enum PrimitiveType {
    I16("I16", "int"),
    U16("U16", "unsigned int");

    private final String magmaType;
    private final String nativeType;

    PrimitiveType(String magmaType, String nativeType) {
        this.magmaType = magmaType;
        this.nativeType = nativeType;
    }

    public String renderMagma() {
        return magmaType;
    }

    public String renderNative() {
        return nativeType;
    }
}
