package com.meti;

public record Declaration(String identity) implements Node {
    @Override
    public String render() {
        return identity + ";";
    }
}
