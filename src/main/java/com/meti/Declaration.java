package com.meti;

public record Declaration(String identity) implements Node {
    @Override
    public Group group() {
        return Group.Declaration;
    }

    @Override
    public String render() {
        return identity + ";";
    }
}
