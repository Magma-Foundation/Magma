package com.meti.app.compile.imports;

import com.meti.app.compile.CompileException;
import com.meti.app.compile.Node;
import com.meti.app.compile.Renderer;
import com.meti.app.compile.attribute.Attribute;
import com.meti.core.Ok;
import com.meti.core.Option;
import com.meti.core.Result;
import com.meti.java.String_;

import static com.meti.core.Options.$Option;
import static com.meti.java.JavaString.fromSlice;

public record ImportRenderer(Node node) implements Renderer {
    @Override
    public Option<Result<String_, CompileException>> render() {
        return $Option(() -> {
            var parent = this.node.applyOptionally(fromSlice("parent")).flatMap(Attribute::asString).$();
            var child = this.node.applyOptionally(fromSlice("child")).flatMap(Attribute::asString).$();
            return fromSlice("import { ")
                    .appendOwned(child)
                    .append(" } from ")
                    .appendOwned(parent)
                    .append(";\n");
        }).map(Ok::apply);
    }
}
