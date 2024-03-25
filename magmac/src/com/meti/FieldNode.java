package com.meti;

import java.util.Optional;
import java.util.function.Function;

public record FieldNode(Node parentOutput, String member) implements Node {
    @Override
    public Optional<String> render() {
        return Optional.of(this.parentOutput().findValue().orElseThrow() + "." + member());
    }

    @Override
    public Optional<Node> mapParent(Function<Node, Optional<Node>> mapper) {
        return mapper.apply(parentOutput).map(parent -> new FieldNode(parent, member));
    }
}