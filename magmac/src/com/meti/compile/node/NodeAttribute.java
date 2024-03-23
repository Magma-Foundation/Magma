package com.meti.compile.node;

import com.meti.collect.option.Option;
import com.meti.collect.option.Some;

import java.util.Objects;

public class NodeAttribute implements Attribute {
    public static String Type = "node";
    private final Node value;

    public NodeAttribute(Node value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeAttribute that = (NodeAttribute) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean is(String type) {
        return type.equals(Type);
    }

    @Override
    public Option<Node> asNode() {
        return Some.Some(value);
    }

    @Override
    public String toString() {
        return "new NodeAttribute(" + value.toString() + ")";
    }
}
