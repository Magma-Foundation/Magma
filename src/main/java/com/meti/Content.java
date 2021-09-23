package com.meti;

import java.util.stream.Stream;

public class Content implements Node {
    private final String value;

    public Content(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public Group group() {
        return Group.Content;
    }

    @Override
    public Stream<Node> streamTypes() {
        return Stream.empty();
    }

    @Override
    public Node withType(Node type) {
        return this;
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
