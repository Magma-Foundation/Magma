package com.meti;

import java.util.List;
import java.util.Optional;

public record DefinitionNode(List<String> flags, String name, String type, Node value) {
    Optional<String> render() {
        var flagsString = String.join(" ", flags());
        return Optional.of(flagsString + " " + name() + " : " + type() + " = " + value().findValue() + ";");
    }

    public Node findValue() {
        return value;
    }

    public DefinitionNode withValue(Node value) {
        return new DefinitionNode(flags, name, type, value);
    }
}