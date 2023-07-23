package com.meti.app.compile.function;

import com.meti.app.Attribute;
import com.meti.app.compile.*;
import com.meti.core.Ok;
import com.meti.core.Option;
import com.meti.core.Result;
import com.meti.java.*;

import static com.meti.core.Options.$Option;
import static com.meti.java.JavaString.fromSlice;

public record MethodLexer(String_ line) implements Lexer {
    private Option<Node> lex1() {
        return $Option(() -> {
            var paramStart = line.firstIndexOfChar('(').$();
            var paramEnd = line.firstIndexOfChar(')').$();

            var args = line.sliceTo(paramStart).split(" ")
                    .collect(JavaList.asList())
                    .into(NonEmptyJavaList::from)
                    .$();

            var keys = args.sliceWithoutLast()
                    .into(NonEmptyJavaList::from)
                    .$();

            var keywords = keys.sliceWithoutLast().iter().collect(JavaSet.asSet());

            var type = keys.last();
            var name = args.last();

            var paramString = line().sliceBetween(paramStart.nextExclusive().$().to(paramEnd).$());
            var parameters = paramString.split(",")
                    .map(String_::strip)
                    .filter(value -> !value.isEmpty())
                    .map(Content::new)
                    .collect(JavaSet.asSet());

            var bodyStart = line().firstIndexOfChar('{').$();
            var body = line().sliceFrom(bodyStart);
            var node = new Content(body);

            return new MapNode(JavaMap.<String_, Attribute>empty()
                    .insert(fromSlice("keywords"), new StringSetAttribute(keywords))
                    .insert(fromSlice("name"), new StringAttribute(name))
                    .insert(fromSlice("parameters"), new NodeSetAttribute(parameters))
                    .insert(fromSlice("body"), new NodeAttribute(node))
                    .insert(fromSlice("returns"), new NodeAttribute(new Content(type))));
        });
    }

    @Override
    public Option<Result<Node, CompileException>> lex() {
        return lex1().map(Ok::apply);
    }
}