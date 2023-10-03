package com.meti.compile.function;

import com.meti.api.collect.Collectors;
import com.meti.api.collect.ImmutableLists;
import com.meti.api.collect.JavaString;
import com.meti.api.collect.List;
import com.meti.api.iterate.Iterators;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.api.option.ThrowableOption;
import com.meti.api.result.Result;
import com.meti.api.result.Results;
import com.meti.compile.CompileException;
import com.meti.compile.Renderer;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.node.Node;

public record FunctionRenderer(Node node) implements Renderer {

    private static List<JavaString> extractValues(List<? extends Node> values) {
        return values.iter()
                .map(child -> child.apply(JavaString.apply("value")))
                .flatMap(Iterators::fromOption)
                .map(Attribute::asString)
                .flatMap(Iterators::fromOption)
                .collect(ImmutableLists.into());
    }

    @Override
    public Option<Result<JavaString, CompileException>> render() {
        if (node.is("function")) {
            return Some.apply(Results.$Result(() -> {
                var name = this.node()
                        .apply(JavaString.apply("name"))
                        .flatMap(Attribute::asString)
                        .into(ThrowableOption::new)
                        .unwrapOrThrow(new CompileException("No name present for: " + node.toXML().value()))
                        .$();

                var parameters = this.node().apply(JavaString.apply("parameters"))
                        .flatMap(Attribute::asListOfNodes)
                        .map(FunctionRenderer::extractValues)
                        .into(ThrowableOption::new)
                        .unwrapOrThrow(new CompileException("No parameters present: " + node.toXML().value()))
                        .$();

                var body = this.node().apply(JavaString.apply("body"))
                        .flatMap(Attribute::asNode)
                        .flatMap(node11 -> node11.apply(JavaString.apply("value")))
                        .flatMap(Attribute::asString)
                        .into(ThrowableOption::new)
                        .unwrapOrThrow(new CompileException("No body present: " + node.toXML().value()))
                        .$();

                var joinedParameters = parameters.iter()
                        .collect(Collectors.joining())
                        .map(value -> value.prepend("(").append(")"))
                        .unwrapOrElse(JavaString.Empty);

                return new JavaString("export class def " + name + joinedParameters + " => " + body);
            }));
        } else {
            return None.apply();
        }
    }
}