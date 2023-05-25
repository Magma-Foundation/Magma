package com.meti;

public class Function implements Node {
    protected final String name;
    protected final boolean isPublic;

    public Function(String name, boolean isPublic) {
        this.name = name;
        this.isPublic = isPublic;
    }

    @Override
    public String render() {
        var prefix = isPublic ? "public " : "";
        return prefix + "class def " + name + "()";
    }
}
