package com.meti;

public class Assignment implements Node {
    private final String name;
    private final String value;

    public Assignment(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public Group group() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String renderMagma() {
        return name + " = " + value + ";";
    }

    @Override
    public String renderNative() {
        return name + "=" + value + ";";
    }
}
