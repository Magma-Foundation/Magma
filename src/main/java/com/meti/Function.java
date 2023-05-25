package com.meti;

public class Function extends Definition {

    public Function(String name, boolean isPublic) {
        super(name, isPublic);
    }

    @Override
    public String render() {
        var prefix = isPublic ? "public " : "";
        return prefix + "class def " + name + "()";
    }
}
