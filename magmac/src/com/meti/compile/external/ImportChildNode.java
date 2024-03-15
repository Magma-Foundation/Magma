package com.meti.compile.external;

import com.meti.compile.node.Node;
import com.meti.collect.option.Option;

import static com.meti.collect.option.Some.Some;

public record ImportChildNode(com.meti.java.JavaString child, com.meti.java.JavaString parent) implements Node {
    @Override
    public Option<String> render() {
        return Some("import { " + child.inner() + " } from " + parent.inner() + ";\n");
    }

    @Override
    public boolean is(String name) {
        return name.equals("import_child");
    }
}