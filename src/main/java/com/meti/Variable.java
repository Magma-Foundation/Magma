package com.meti;

public record Variable(String value) implements Node {
    @Override
    public Group group() {
        return Group.Variable;
    }

    @Override
    public String render() {
        return value;
    }
}
