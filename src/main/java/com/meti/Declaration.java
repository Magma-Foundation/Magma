package com.meti;

public class Declaration {
    private final String name;
    private final PrimitiveType type;

    public Declaration(String name, PrimitiveType type) {
        this.name = name;
        this.type = type;
    }

    String renderMagma() {
        return "const " + name + " : " + type.renderMagma() + " = 420;";
    }

    String renderNative() {
        return "\t" + type.renderNative() + " " + name + "=420;\n";
    }
}
