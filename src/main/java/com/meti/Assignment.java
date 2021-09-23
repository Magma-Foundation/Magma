package com.meti;

import java.util.stream.Stream;

public class Assignment implements Node {
    private final String name;
    private final String value;

    public Assignment(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Node getType() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getValue() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Group group() {
        return Group.Assignment;
    }

    @Override
    public boolean isFlagged(Declaration.Flag flag) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String renderMagma() {
        return name + " = " + value + ";";
    }

    @Override
    public String renderNative() {
        return name + "=" + value + ";";
    }

    @Override
    public Stream<Node> streamTypes() {
        return Stream.empty();
    }

    @Override
    public Node withType(Node type) {
        return this;
    }
}
