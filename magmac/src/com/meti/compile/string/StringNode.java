package com.meti.compile.string;

import com.meti.compile.node.Node;
import com.meti.collect.option.Option;

import static com.meti.collect.option.Some.Some;

public record StringNode(com.meti.java.JavaString value) implements Node {
    @Override
    public Option<String> render() {
        return Some("\"" + value.inner() + "\"");
    }

    @Override
    public boolean is(String name) {
        return name.equals("string");
    }
}