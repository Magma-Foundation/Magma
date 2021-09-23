package com.meti;

import java.util.stream.Stream;

public class Declaration implements Node {
    private final Flag flag;

    private final String name;
    private final Node type;
    private final String value;

    public Declaration(Flag flag, String name, Node type, String value) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.flag = flag;
    }

    @Override
    public Node getType() {
        return type;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public Group group() {
        return Group.Declaration;
    }

    @Override
    public String renderMagma() {
        return flag.name().toLowerCase() + " " + name + " : " + type.renderMagma() + " = " + value + ";";
    }

    @Override
    public String renderNative() {
        return type.renderNative() + " " + name + "=" + value + ";";
    }

    @Override
    public Stream<Node> streamTypes() {
        return Stream.of(type);
    }

    @Override
    public Node withType(Node type) {
        return new Declaration(flag, name, type, value);
    }

    enum Flag {
        CONST,
        LET
    }
}
