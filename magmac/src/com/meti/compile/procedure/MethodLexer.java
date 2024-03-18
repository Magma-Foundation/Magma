package com.meti.compile.procedure;

import com.meti.collect.Index;
import com.meti.collect.JavaList;
import com.meti.collect.option.Option;
import com.meti.collect.option.Options;
import com.meti.collect.stream.Collectors;
import com.meti.collect.stream.Streams;
import com.meti.compile.Lexer;
import com.meti.compile.TypeCompiler;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;

import java.util.ArrayList;

import static com.meti.collect.option.None.None;
import static com.meti.collect.option.Options.$$;
import static com.meti.collect.option.Some.Some;

public record MethodLexer(JavaString stripped, int indent) implements Lexer {
    @Override
    public Option<Node> lex() {
        return Options.$Option(() -> {
            var paramStart = stripped.firstIndexOfChar('(').$();
            var paramEnd = stripped.firstIndexOfChar(')').$();

            var keyString = stripped.sliceTo(paramStart);
            var space = keyString.lastIndexOfChar(' ').$();

            var name = keyString.sliceFrom(space.next().$()).strip();
            var featuresString = keyString.sliceTo(space).strip();

            var typeSeparator = featuresString.lastIndexOfChar(' ');
            var javaStringJavaList = typeSeparator.map(value -> featuresString.sliceTo(value).split(" ").collect(Collectors.toList()))
                    .orElse(new JavaList<>());

            var validFlags = javaStringJavaList.stream()
                    .map(value -> value.stream()
                            .map(c -> Character.isAlphabetic(c) || Character.isDigit(c))
                            .collect(Collectors.allTrue())).collect(Collectors.allTrue());

            if(!validFlags) $$();

            var afterTypeSeparator = typeSeparator.flatMap(Index::next);

            TypeCompiler typeCompiler2 = new TypeCompiler(afterTypeSeparator.map(featuresString::sliceFrom)
                    .orElse(featuresString)
                    .strip()
                    .inner());
            var type = typeCompiler2.compile().map(JavaString::inner)
                    .$();

            var annotations = typeSeparator.map(value -> featuresString.sliceTo(value).strip().split(" ")
                    .filter(token -> token.startsWithSlice("@"))
                    .map(token -> token.sliceFromRaw(1))
                    .flatMap(Streams::fromOption)
                    .map(JavaString::inner)
                    .map(TypeCompiler::new)
                    .map(typeCompiler1 -> typeCompiler1.compile().map(JavaString::inner))
                    .collect(Collectors.required(Collectors.toNativeList())))
                    .orElseGet(() -> Some(new ArrayList<>()))
                    .$();

            var contentStart = stripped.firstIndexOfChar('{');
            var more = stripped.sliceBetween(paramEnd.next().$().to(contentStart.orElse(stripped.end())).$()).strip();
            Option<?> moreOutputValue;
            if (more.startsWithSlice("throws ")) {
                TypeCompiler typeCompiler = new TypeCompiler(more.sliceFromRaw("throws ".length()).$().inner());
                moreOutputValue = Some(typeCompiler.compile().map(JavaString::inner));
            } else {
                moreOutputValue = None();
            }

            return contentStart.<Node>map(contentStartValue -> {
                var content = new Content(stripped.sliceFrom(contentStartValue).strip(), indent());
                return new ImplementationNode(indent(), moreOutputValue, annotations, name, type, content);
            }).orElseGet(() -> new MethodNode(indent(), moreOutputValue, annotations, name, type));
        });
    }
}