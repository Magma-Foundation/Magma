package com.meti.compile.scope;

import com.meti.collect.JavaList;
import com.meti.collect.option.Option;
import com.meti.collect.stream.Collectors;
import com.meti.collect.stream.Stream;
import com.meti.collect.stream.Streams;
import com.meti.compile.Lexer;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;

import java.util.ArrayList;
import java.util.Collections;

import static com.meti.collect.option.Options.$$;
import static com.meti.collect.option.Options.$Option;

public record DefinitionLexer(JavaString stripped, int indent) implements Lexer {
    private Option<Node> lex0() {
        return $Option(() -> {
            var separator = stripped.firstIndexOfChar('=').$();
            var withName = stripped.sliceTo(separator).strip();
            var nameSeparator = withName.lastIndexOfChar(' ').$();
            var name = withName.sliceFrom(nameSeparator.next().$());
            if (name.isEmpty()) {
                $$();
            }

            var withType = withName.sliceTo(nameSeparator).strip();
            var typeSeparatorOptional = withType.lastIndexOfChar(' ');

            var inputFlags = typeSeparatorOptional.map(typeSeparator -> {
                return withType.sliceTo(typeSeparator)
                        .strip()
                        .split(" ")
                        .map(JavaString::inner)
                        .collect(Collectors.toList());
            }).orElseGet(() -> {
                return new JavaList<>(Collections.singletonList(withType.inner()));
            });

            var validFlags = inputFlags.stream()
                    .map(flag -> flag.equals("public")
                                 || flag.equals("final")
                                 || flag.equals("var"))
                    .collect(Collectors.allTrue());

            if (!validFlags) {
                $$();
            }

            var flagsString = new ArrayList<JavaString>();
            if (inputFlags.contains("public")) flagsString.add(new JavaString("pub"));
            if (inputFlags.contains("final")) flagsString.add(new JavaString("const"));
            if (inputFlags.contains("var")) flagsString.add(new JavaString("let"));

            var compiledValue = new Content(stripped.sliceFrom(separator.next().$()).strip(), 0);
            return new DefinitionNode(indent, flagsString, name, compiledValue);
        });
    }

    @Override
    public Stream<Node> lex() {
        return Streams.fromOption(lex0());
    }
}