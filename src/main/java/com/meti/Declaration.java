package com.meti;

public class Declaration implements Node {
    private final Flag flag;

    private final String name;
    private final PrimitiveType type;
    private final String value;

    public Declaration(String name, Flag flag, PrimitiveType type, String value) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.flag = flag;
    }

    @Override
    public String renderMagma() {
        return flag.name().toLowerCase() + " " + name + " : " + type.renderMagma() + " = " + value + ";";
    }

    @Override
    public String renderNative() {
        return type.renderNative() + " " + name + "=" + value + ";";
    }

    enum Flag {
        CONST,
        LET
    }
}
