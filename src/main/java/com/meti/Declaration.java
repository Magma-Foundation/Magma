package com.meti;

public class Declaration {
    private final String name;
    private final PrimitiveType type;
    private final String value;

    public Declaration(String name, PrimitiveType type, String value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    String renderMagma() {
        return "const " + name + " : " + type.renderMagma() + " = " + value + ";";
    }

    String renderNative() {
        return "\t" + type.renderNative() + " " + name + "=" + value + ";\n";
    }
}
