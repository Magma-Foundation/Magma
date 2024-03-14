package com.meti.compile.scope;

import com.meti.collect.option.Option;
import com.meti.compile.node.Node;

import java.util.List;

import static com.meti.collect.option.Some.Some;

public record FieldNode(int indent, List<String> flags, String name, Node value) implements Node {
    @Override
    public Option<String> render() {
        var flagsString = String.join(" ", flags());
        var withSuffix = flagsString.isEmpty() ? "" : flagsString + " ";
        return Some("\t".repeat(indent()) + withSuffix + name() + " = " + value.render().orElse("") + ";\n");
    }

    @Override
    public Option<Node> findValueAsNode() {
        return Some(value);
    }

    @Override
    public Option<Node> withValue(Node value) {
        return Some(new FieldNode(indent, flags, name, value));
    }

    @Override
    public boolean is(String name) {
        throw new UnsupportedOperationException();
    }
}