package com.meti.app.compile.clazz;

import com.meti.app.compile.*;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.NodeAttribute;
import com.meti.app.compile.attribute.StringAttribute;
import com.meti.core.Ok;
import com.meti.core.Option;
import com.meti.core.Options;
import com.meti.core.Result;
import com.meti.iterate.Index;
import com.meti.java.JavaMap;
import com.meti.java.String_;

import static com.meti.java.JavaString.fromSlice;

public record RecordLexer(String_ value) implements Lexer {
    @Override
    public Option<Result<Node, CompileException>> lex() {
        return Options.$Option(() -> {
            var nameIndex = value.firstIndexOfSlice("record ").$();
            var nameStart = Index.endOf(nameIndex).$();
            var paramStart = value.firstIndexOfChar('(').$();
            var bodyIndex = value.firstIndexOfChar('{').$();

            var name = value.sliceBetween(nameStart.to(paramStart).$()).strip();
            var body = value.sliceFrom(bodyIndex);

            return Ok.apply(new MapNode(fromSlice("record"), JavaMap.<String_, Attribute>empty()
                    .insert(fromSlice("name"), new StringAttribute(name))
                    .insert(fromSlice("body"), new NodeAttribute(fromSlice("node"), Content.ofContent(body)))));
        });
    }
}