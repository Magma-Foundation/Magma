package com.meti;

public record Content(int indent, String value) implements Node {
    @Override
    public String findValue() {
        return value;
    }

    @Override
    public int findIndent() {
        return indent;
    }
}