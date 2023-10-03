package com.meti.compile.imports;

import com.meti.api.collect.JavaString;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.api.option.ThrowableOption;
import com.meti.api.result.Result;
import com.meti.compile.CompileException;
import com.meti.compile.Renderer;
import com.meti.compile.node.Node;

import static com.meti.api.option.Options.$Option;

public record ImportRenderer(Node root) implements Renderer {

    @Override
    public Option<Result<JavaString, CompileException>> render() {
        if (root.is("import")) {
            return Some.apply($Option(() -> {
                var child = root.apply("child").$().asString().$();
                var parent = root.apply("parent").$().asString().$();

                return new JavaString("import { " + child + " } from " + parent + ";\n");
            }).into(ThrowableOption::new).unwrapOrThrow(new CompileException("Import node is malformed: " + root.toXML())));
        } else {
            return None.apply();
        }
    }
}