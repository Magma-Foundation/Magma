package com.meti;

import java.util.Optional;

public record FieldNode(Content parentOutput, String member) {
    Optional<String> render() {
        return Optional.of(parentOutput().findValue() + "." + member());
    }

    public FieldNode withParent(Content parent) {
        return new FieldNode(parent, member);
    }
}