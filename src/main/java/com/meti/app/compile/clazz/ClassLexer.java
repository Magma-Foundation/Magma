package com.meti.app.compile.clazz;

import com.meti.app.compile.*;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.NodeAttribute;
import com.meti.app.compile.attribute.StringAttribute;
import com.meti.core.Ok;
import com.meti.core.Option;
import com.meti.core.Result;
import com.meti.iterate.Index;
import com.meti.java.JavaMap;
import com.meti.java.String_;

import static com.meti.core.Options.$Option;
import static com.meti.java.JavaString.fromSlice;

public record ClassLexer(String_ type, String_ input) implements Lexer {

    @Override
    public Option<Result<Node, CompileException>> lex() {
        return $Option(() -> {
            var classIndex = input.firstIndexOfSlice("class ").map(Index::startOf).$()
                    .nextExclusive("class ".length()).$();

            var contentStart = input.firstIndexOfChar('{').$();

            var name = input.sliceBetween(classIndex.to(contentStart).$()).strip();
            var body = input.sliceFrom(contentStart);

            return Ok.apply(new MapNode(fromSlice("class"), JavaMap.<String_, Attribute>empty()
                    .insert(fromSlice("name"), new StringAttribute(name))
                    .insert(fromSlice("statements"), new NodeAttribute(fromSlice("any"), new Content(fromSlice(""), body)))));
        });
    }
}