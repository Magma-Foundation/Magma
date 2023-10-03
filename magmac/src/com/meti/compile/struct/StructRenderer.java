package com.meti.compile.struct;

import com.meti.api.collect.JavaString;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.api.option.ThrowableOption;
import com.meti.api.result.Result;
import com.meti.compile.CompileException;
import com.meti.compile.NodeException;
import com.meti.compile.Renderer;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.node.Node;

public record StructRenderer(Node node) implements Renderer {
    @Override
    public Option<Result<JavaString, CompileException>> render() {
        if (node.is("struct")) {
            return Some.apply(node.apply("name")
                    .flatMap(Attribute::asString)
                    .map(name -> name.prepend("struct ").append("{}"))
                    .into(ThrowableOption::new)
                    .unwrapOrThrow(new NodeException("No name found.", node)));
        } else {
            return None.apply();
        }
    }
}
