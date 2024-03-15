package com.meti.compile.node;

import com.meti.collect.option.Option;

public record Statement(Node child, int indent) implements Node {
    @Override
    public Option<String> render() {
        return child.render().map(value -> "\n" + "\t".repeat(indent) + value + ";");
    }

    @Override
    public boolean is(String name) {
        return name.equals("statement");
    }
}
