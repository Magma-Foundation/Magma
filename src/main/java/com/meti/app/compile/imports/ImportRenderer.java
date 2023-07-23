package com.meti.app.compile.imports;

import com.meti.app.Attribute;
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
            var parent = this.node.apply(fromSlice("parent")).flatMap(Attribute::asString).$();
            var child = this.node.apply(fromSlice("child")).flatMap(Attribute::asString).$();
            return fromSlice("import { ")
                    .appendOwned(child)
                    .append(" } from ")
                    .appendOwned(parent)
                    .append(";\n");
        });
    }
}
