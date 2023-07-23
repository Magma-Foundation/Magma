package com.meti.app.compile.imports;

import com.meti.app.compile.Node;
import com.meti.app.compile.Renderer;
import com.meti.core.Option;
import com.meti.java.String_;

import static com.meti.core.Options.$Option;
import static com.meti.java.JavaString.fromSlice;

public record ImportRenderer(Node node) implements Renderer {
    @Override
    public Option<String_> render() {
        return $Option(() -> {
            var parent = this.node.parent().$();
            var child = this.node.child().$();
            return fromSlice("import { ")
                    .appendOwned(child)
                    .append(" } from ")
                    .appendOwned(parent)
                    .append(";\n");
        });
    }
}
