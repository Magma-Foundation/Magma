package com.meti;

public record Content(String value, int indent) implements Node {
    @Override
    public String findValue() {
        return value;
    }

    @Override
    public int findIndent() {
        return indent;
    }
}