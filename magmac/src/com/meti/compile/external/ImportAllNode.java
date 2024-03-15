package com.meti.compile.external;

import com.meti.collect.option.Option;
import com.meti.compile.node.Node;

import static com.meti.collect.option.Some.Some;

public record ImportAllNode(com.meti.java.JavaString parent) implements Node {
    @Override
    public Option<String> render() {
        return Some("import " + parent.inner() + ";\n");
    }

    @Override
    public boolean is(String name) {
        return name.equals("import_all");
    }
}