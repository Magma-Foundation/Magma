package com.meti;

public class Assignment implements Node {
    @Override
    public String renderMagma() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String renderNative() {
        return "x=69;";
    }
}
