package com.meti.app;

import com.meti.core.Option;
import com.meti.iterate.Iterators;
import com.meti.java.JavaString;
import com.meti.java.String_;

import static com.meti.java.JavaString.Empty;
import static com.meti.java.JavaString.fromSlice;

public record FunctionRenderer(Function function) implements Renderer {
    @Override
    public Option<String_> render() {
        var joinedParameters = function().parameters().iter()
                .map(Node::value)
                .flatMap(Iterators::fromOption)
                .collect(JavaString.joining(fromSlice(", ")))
                .unwrapOrElse(JavaString.Empty);

        var renderedKeywords = function().keywords().iter()
                .map(value -> value.append(" "))
                .collect(JavaString.joiningEmpty())
                .unwrapOrElse(Empty);

        var body = function().body().value();
        return body.map(value -> renderedKeywords.append("def")
                .appendOwned(function().name()).append("(")
                .appendOwned(joinedParameters).append(") => ")
                .appendOwned(value));
    }
}