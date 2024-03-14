package com.meti;

import static com.meti.Some.Some;

public record ImportAllNode(String parent) implements Node {
    @Override
    public Option<String> render() {
        return Some("import " + parent() + ";\n");
    }
}