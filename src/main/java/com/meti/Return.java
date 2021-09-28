package com.meti;

import java.util.stream.Stream;

public record Return(Node value) implements Node {
    @Override
    public Node apply(Type type) {
        return value;
    }

    @Override
    public Group group() {
        return Group.Return;
    }

    @Override
    public String render() {
        return "return " + value.render() + ";";
    }

    @Override
    public Stream<Type> streamNodes() {
        return Stream.of(Type.Value);
    }

    @Override
    public Node withNode(Node node) {
        return new Return(node);
    }
}
