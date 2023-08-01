package com.meti.app.compile.attribute;

import com.meti.app.compile.Node;
import com.meti.core.Option;
import com.meti.core.Some;

public record NodeAttribute(Node node) implements Attribute {
    @Override
    public Option<Node> asNode() {
        return Some.apply(node);
    }

    @Override
    public String toString() {
        return node.toString();
    }

    @Override
    public boolean is(Node.Group group) {
        return group == Node.Group.Node;
    }
}
