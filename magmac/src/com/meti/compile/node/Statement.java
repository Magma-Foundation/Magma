package com.meti.compile.node;

import com.meti.collect.option.Option;

public class Statement implements Node {
    protected final Node child;
    protected final int indent;

    public Statement(Node child, int indent) {
        this.child = child;
        this.indent = indent;
    }

    @Override
    public Option<String> render() {
        return child.render().map(value -> "\n" + "\t".repeat(indent) + value + suffix());
    }

    protected String suffix() {
        return "";
    }

    @Override
    public boolean is(String name) {
        return name.equals("statement");
    }
}
