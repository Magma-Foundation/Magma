package com.meti;

public class MagmaFunctionRenderer {
    public MagmaFunctionRenderer() {
    }

    String render(final String name, final String type, final String body) {
        return "def " + name + "() : " + type + " => " + body;
    }
}