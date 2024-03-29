package com.meti.compile.node;

import com.meti.collect.option.Option;
import com.meti.collect.option.Some;

public class NodeAttribute implements Attribute {
    public static String Type = "node";

    @Override
    public boolean is(String type) {
        return type.equals(Type);
    }

    private final Node value;

    public NodeAttribute(Node value) {
        this.value = value;
    }

    @Override
    public Option<Node> asNode() {
        return Some.Some(value);
    }

    @Override
    public String toString() {
        return "NodeAttribute{" +
               "value=" + value +
               '}';
    }
}
