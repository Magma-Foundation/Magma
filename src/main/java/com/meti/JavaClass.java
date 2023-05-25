package com.meti;

public record JavaClass(String name) implements Node {

    public static final String PREFIX = "class ";

    @Override
    public String render() {
        return PREFIX + name() + " {}";
    }
}