package com.meti.app.compile;

import com.meti.app.compile.attribute.Attribute;

import java.util.function.Function;

public record NodeListMapper(Function<Node, Node> mapper) implements Attribute {
    @Override
    public boolean is(Node.Group group) {
        return false;
    }
}
