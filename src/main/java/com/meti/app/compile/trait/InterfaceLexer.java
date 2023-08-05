package com.meti.app.compile.trait;

import com.meti.app.compile.*;
import com.meti.core.Ok;
import com.meti.core.Option;
import com.meti.core.Options;
import com.meti.core.Result;
import com.meti.iterate.Index;
import com.meti.java.String_;

import static com.meti.java.JavaString.fromSlice;

public record InterfaceLexer(String_ value) implements Lexer {

    @Override
    public Option<Result<Node, CompileException>> lex() {
        return Options.$Option(() -> {
            var range = value.firstIndexOfSlice("interface ").$();
            var nameStart = Index.endOf(range).$();

            var genericsStart = value.firstIndexOfChar('<');
            var genericsEnd = value.firstIndexOfChar('>');
            var generics = genericsStart.flatMap(Index::nextExclusive).and(genericsEnd)
                    .flatMap(value -> value.a().to(value.b()))
                    .map(value::sliceBetween);

            var bodyStart = value.firstIndexOfChar('{').$();

            var name = value.sliceBetween(nameStart.to(genericsStart.unwrapOrElse(bodyStart)).$())
                    .strip();

            var body = value.sliceFrom(bodyStart);
            var builder = MapNode.create(fromSlice("interface"))
                    .withString(fromSlice("name"), name)
                    .withNode(fromSlice("body"), fromSlice("block"), Content.ofContent(body));

            var withGenerics = generics
                    .map(genericString -> builder.withString(fromSlice("generic"), genericString))
                    .unwrapOrElse(builder);

            return Ok.apply(withGenerics.complete());
        });
    }
}
