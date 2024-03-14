package com.meti;

import java.util.List;

import static com.meti.Some.Some;

public record ObjectNode(List<String> flags, String name, Node value) implements Node {
    @Override
    public Option<Node> findValueAsNode() {
        return Some(value);
    }

    @Override
    public Option<Node> withValue(Node value) {
        return Some(new ObjectNode(flags, name, value));
    }

    @Override
    public Option<String> render() {
        var flagsString = !flags().isEmpty() ? String.join(" ", flags()) + " " : "";
        return Some("\n" + flagsString + "object " + name() + " = " + value.render().orElse(""));
    }
}