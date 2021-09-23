package com.meti;

public class Content implements Node {
    @Override
    public Group group() {
        return Group.Content;
    }

    @Override
    public String renderMagma() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String renderNative() {
        throw new UnsupportedOperationException();
    }
}
