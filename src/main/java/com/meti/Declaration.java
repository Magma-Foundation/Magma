package com.meti;

public record Declaration(String identity) implements Node {
    @Override
    public Group group() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String render() {
        return identity + ";";
    }
}
