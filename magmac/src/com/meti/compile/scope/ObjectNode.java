package com.meti.compile.scope;

import com.meti.collect.option.Option;
import com.meti.compile.node.Node;

import java.util.List;

import static com.meti.collect.option.Some.Some;

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

    @Override
    public boolean is(String name) {
        return name.equals("object");
    }
}