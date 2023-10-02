package com.meti.compile.function;

import com.meti.api.collect.ImmutableLists;
import com.meti.api.collect.JavaString;
import com.meti.api.iterate.Iterators;
import com.meti.api.option.Option;
import com.meti.api.result.Ok;
import com.meti.api.result.Result;
import com.meti.compile.Attribute;
import com.meti.compile.CompileException;
import com.meti.compile.Node;
import com.meti.compile.Renderer;

import static com.meti.api.option.Options.$Option;

public record FunctionRenderer(Node node) implements Renderer {

    @Override
    public Option<Result<JavaString, CompileException>> render() {
        return $Option(() -> {
            var name = this.node().apply(JavaString.apply("name"))
                    .flatMap(Attribute::asString).$();
            var parameters = this.node().apply(JavaString.apply("parameters"))
                    .flatMap(Attribute::asListOfNodes)
                    .map(node111 -> node111.iter()
                            .map(child -> child.apply(JavaString.apply("value")))
                            .flatMap(Iterators::fromOption)
                            .map(Attribute::asString)
                            .flatMap(Iterators::fromOption)
                            .collect(ImmutableLists.into())).$();
            var body = this.node().apply(JavaString.apply("body"))
                    .flatMap(Attribute::asNode)
                    .flatMap(node11 -> node11.apply(JavaString.apply("value")))
                    .flatMap(Attribute::asString).$();
            return Ok.apply(new JavaString("export class def " + name + parameters + " => " + body));
        });
    }
}