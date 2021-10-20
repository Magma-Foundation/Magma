package com.meti;

public class MagmaFunctionRenderer {
    public MagmaFunctionRenderer() {
    }

    String render(Function function) {
        return "def " + function.apply(Attribute.Group.Name).asString() + "() : " + function.apply(Attribute.Group.Type).asString() + " => " + function.apply(Attribute.Group.Body).asNode();
    }
}