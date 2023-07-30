package com.meti.app.compile.clazz;

import com.meti.app.Attribute;
import com.meti.app.compile.*;
import com.meti.core.Ok;
import com.meti.core.Option;
import com.meti.core.Result;
import com.meti.java.JavaMap;
import com.meti.java.String_;

import static com.meti.core.Options.$Option;
import static com.meti.java.JavaString.fromSlice;

public record ClassLexer(String_ line) implements Lexer {
    private Option<Node> lex1() {
        return $Option(() -> {
            var classIndex = line
                    .firstIndexOfSlice("class ").$()
                    .nextExclusive("class ".length()).$();

            var contentStart = line.firstIndexOfChar('{').$();

            var name = line.sliceBetween(classIndex.to(contentStart).$()).strip();
            var body = line.sliceFrom(contentStart);

            return new MapNode(fromSlice("class"), JavaMap.<String_, Attribute>empty()
                    .insert(fromSlice("name"), new StringAttribute(name))
                    .insert(fromSlice("body"), new NodeAttribute(new Content(body))));
        });
    }

    @Override
    public Option<Result<Node, CompileException>> lex() {
        return lex1().map(Ok::apply);
    }
}