package com.meti;

public class Assignment implements Node {
    private final String name;

    public Assignment(String name) {
        this.name = name;
    }

    @Override
    public String renderMagma() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String renderNative() {
        return name + "=69;";
    }
}
