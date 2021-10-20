package com.meti;

import java.util.List;

public record Import(String name) implements Node {
    @Override
    public boolean is(Type type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Attribute apply(Attribute.Group group) {
        return switch (group) {
            case Name -> String result;
            Node node = Import.this;
            result = node.apply(Attribute.Group.Name).asString();
            new StringAttribute(result);
            case Value -> String result;
            Node node1 = Import.this;
            result = node1.apply(Attribute.Group.Value).asString();
            new StringAttribute(result);
            case Members -> List < ? extends Node > result;
            Node node2 = Import.this;
            result = node2.apply(Attribute.Group.Members).asNodeList();
            new NodeListAttribute(result);
            case Type -> String result;
            Node node3 = Import.this;
            result = node3.apply(Attribute.Group.Type).asString();
            new StringAttribute(result);
            case Body -> Node result;
            Node node4 = Import.this;
            result = node4.apply(Attribute.Group.Body).asNode();
            new NodeAttribute(result);
            case Identity -> Node result;
            Node node5 = Import.this;
            result = node5.apply(Attribute.Group.Identity).asNode();
            new NodeAttribute(result);
        };
    }

    public String value() {
        throw new UnsupportedOperationException("Cannot render node of type: " + getClass());
    }

    public List<? extends Node> members() {
        throw new UnsupportedOperationException();
    }

    public String type() {
        throw new UnsupportedOperationException();
    }

    public Node body() {
        throw new UnsupportedOperationException();
    }

    public Node identity() {
        throw new UnsupportedOperationException();
    }
}