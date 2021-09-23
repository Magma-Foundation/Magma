package com.meti.node;

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
    public Attribute apply(Attribute.Type type) {
        return switch (type) {
            case Name -> new StringAttribute(name);
            case Type -> new NodeAttribute(this.type);
            case Value -> new StringAttribute(value);
        };
    }

    @Override
    public Group group() {
        return Group.Declaration;
    }

    @Override
    public boolean isFlagged(Flag flag) {
        return this.flag == flag;
    }

    @Override
    public Stream<Node> streamTypes() {
        return Stream.of(type);
    }

    @Override
    public Node withType(Node type) {
        return new Declaration(flag, name, type, value);
    }

    @Override
    public String renderMagma() {
        return flag.name().toLowerCase() + " " + name + " : " + type.renderMagma() + " = " + value + ";";
    }

    @Override
    public String renderNative() {
        return type.renderNative() + " " + name + "=" + value + ";";
    }

    public enum Flag {
        CONST,
        LET
    }
}
