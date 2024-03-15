package com.meti.compile.scope;

import com.meti.collect.option.Option;
import com.meti.compile.node.Node;

import java.util.List;

import static com.meti.collect.option.Some.Some;

public record DefinitionNode(int indent, List<String> flags, String name, Node value) implements Node {
    @Override
    public Option<String> render() {
        var flagsString = String.join(" ", flags());
        var withSuffix = flagsString.isEmpty() ? "" : flagsString + " ";
        return Some(withSuffix + name() + " = " + value.render().orElse(""));
    }

    @Override
    public Option<Node> findValueAsNode() {
        return Some(value);
    }

    @Override
    public Option<Node> withValue(Node value) {
        return Some(new DefinitionNode(indent, flags, name, value));
    }

    @Override
    public boolean is(String name) {
        return name.equals("definition");
    }
}