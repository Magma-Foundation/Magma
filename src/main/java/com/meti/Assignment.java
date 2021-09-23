package com.meti;

public class Assignment implements Node {
    private final String name;
    private final String value;

    public Assignment(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String renderMagma() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String renderNative() {
        return name + "=" + value + ";";
    }
}
