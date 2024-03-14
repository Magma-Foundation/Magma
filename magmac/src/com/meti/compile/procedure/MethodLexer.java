package com.meti.compile.procedure;

import com.meti.collect.option.Option;
import com.meti.collect.option.Options;
import com.meti.collect.stream.Collectors;
import com.meti.collect.stream.Streams;
import com.meti.compile.Lexer;
import com.meti.compile.TypeCompiler;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;

import static com.meti.collect.option.None.None;
import static com.meti.collect.option.Some.Some;

public record MethodLexer(JavaString stripped, int indent) implements Lexer {
    @Override
    public Option<Node> lex() {
        return Options.$Option(() -> {
            var paramStart = stripped.firstIndexOfChar('(').$();
            var paramEnd = stripped.firstIndexOfChar(')').$();
            var contentStart = stripped.firstIndexOfChar('{').$();

            var keyString = stripped.sliceTo(paramStart);
            var space = keyString.lastIndexOfChar(' ').$();

            var name = keyString.sliceFrom(space.next().$()).strip();
            var featuresString = keyString.sliceTo(space).strip();

            var typeSeparator = featuresString.lastIndexOfChar(' ').$();
            var type = new TypeCompiler(featuresString.sliceFrom(typeSeparator.next().$()).strip().inner()).compile();

            var annotations = featuresString.sliceTo(typeSeparator).strip().split(" ")
                    .filter(token -> token.startsWithSlice("@"))
                    .map(token -> token.sliceFromRaw(1))
                    .flatMap(Streams::fromOption)
                    .map(JavaString::inner)
                    .map(TypeCompiler::new)
                    .map(TypeCompiler::compile)
                    .collect(Collectors.toList());

            var content = new Content(stripped.sliceFrom(contentStart).strip(), indent());
            var more = stripped.sliceBetween(paramEnd.next().$().to(contentStart).$()).strip();

            var moreOutputValue = more.startsWithSlice("throws ")
                    ? Some(new TypeCompiler(more.sliceFromRaw("throws ".length()).$().inner()).compile())
                    : None();

            return new MethodNode(indent(), moreOutputValue, annotations, name, type, content);
        });
    }
}