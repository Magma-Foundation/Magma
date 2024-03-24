package com.meti;

import java.util.Optional;
import java.util.function.Function;

public record FieldNode(Node parentOutput, String member) {
    Optional<String> render() {
        return Optional.of(parentOutput().findValue() + "." + member());
    }

    public FieldNode withParent(Node parent) {
        return new FieldNode(parent, member);
    }

    public Optional<FieldNode> mapParent(Function<Node, Optional<Node>> mapper) {
        return mapper.apply(parentOutput).map(this::withParent);
    }
}