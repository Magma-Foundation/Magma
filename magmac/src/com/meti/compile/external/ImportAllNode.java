package com.meti.compile.external;

import com.meti.compile.node.Node;
import com.meti.collect.option.Option;

import static com.meti.collect.option.Some.Some;

public record ImportAllNode(String parent) implements Node {
    @Override
    public Option<String> render() {
        return Some("import " + parent() + ";\n");
    }
}