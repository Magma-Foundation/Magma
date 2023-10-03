package com.meti.compile.block;

import com.meti.api.collect.Collectors;
import com.meti.api.collect.ImmutableLists;
import com.meti.api.collect.JavaString;
import com.meti.api.iterate.Iterators;
import com.meti.api.option.Option;
import com.meti.api.result.Ok;
import com.meti.api.result.Result;
import com.meti.compile.CompileException;
import com.meti.compile.Renderer;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.node.Node;

public record BlockRenderer(Node node) implements Renderer {

    @Override
    public Option<Result<JavaString, CompileException>> render() {
        return this.node().apply(JavaString.apply("lines"))
                .flatMap(Attribute::asListOfNodes)
                .map(node11 -> node11.iter()
                        .map(child -> child.apply(JavaString.apply("value")))
                        .flatMap(Iterators::fromOption)
                        .map(Attribute::asString)
                        .flatMap(Iterators::fromOption)
                        .collect(ImmutableLists.into()))
                .map(lines -> Ok.apply(lines.iter()
                        .collect(Collectors.joining(new JavaString("\t")))
                        .unwrapOrElse(JavaString.Empty)
                        .prepend("{\n")
                        .append("\n}")));
    }
}