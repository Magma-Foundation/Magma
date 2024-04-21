package com.meti;

public record Import(String name) implements Node {
    @Override
    public String render() {
        return Compiler.renderImport(name());
    }
}