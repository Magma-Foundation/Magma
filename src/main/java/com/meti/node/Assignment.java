package com.meti.node;

import com.meti.ApplicationException;

import java.util.stream.Stream;

public class Assignment implements Node {
    private final String name;
    private final String value;

    public Assignment(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public Attribute apply(Attribute.Type type) throws ApplicationException {
        return switch (type) {
            case Name -> new StringAttribute(name);
            case Value -> new StringAttribute(value);
            default -> throw new ApplicationException();
        };
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
    public Stream<Node> streamNodes() {
        return Stream.empty();
    }

    @Override
    public Stream<Node> streamTypes() {
        return Stream.empty();
    }

    @Override
    public Node withNode(Node node) {
        return this;
    }

    @Override
    public Node withType(Node type) {
        return this;
    }
}
