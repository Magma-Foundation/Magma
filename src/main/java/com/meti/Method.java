package com.meti;

public class Method extends Definition {
    public Method(String name, boolean isPublic) {
        super(name, isPublic);
    }

    @Override
    public String render() {
        var isPublic = this.isPublic ? "public " : "";
        return isPublic + "void " + name + "(){}";
    }
}
