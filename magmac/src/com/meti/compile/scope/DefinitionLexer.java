package com.meti.compile.scope;

import com.meti.collect.option.Option;
import com.meti.collect.stream.Collectors;
import com.meti.compile.Lexer;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.meti.collect.option.None.None;
import static com.meti.collect.option.Options.$Option;
import static com.meti.collect.option.Some.Some;

public record DefinitionLexer(JavaString stripped, int indent) implements Lexer {
    @Override
    public Option<Node> lex() {
        return $Option(() -> {
            var separator = stripped.firstIndexOfChar('=').$();
            var withName = stripped.sliceTo(separator);
            var nameSeparator = withName.lastIndexOfChar(' ').$();
            var name = withName.sliceFrom(nameSeparator.next().$());

            var withType = withName.sliceTo(nameSeparator);
            var typeSeparatorOptional = withType.lastIndexOfChar(' ');

            var inputFlags = typeSeparatorOptional.map(typeSeparator -> {
                return withType.sliceTo(typeSeparator)
                        .split(" ")
                        .map(JavaString::inner)
                        .collect(Collectors.toList());
            }).orElseGet(() -> {
                return Collections.singletonList(withType.inner());
            });

            var flagsString = new ArrayList<JavaString>();
            if(inputFlags.contains("public")) flagsString.add(new JavaString("pub"));
            if(inputFlags.contains("final")) flagsString.add(new JavaString("const"));
            if(inputFlags.contains("var")) flagsString.add(new JavaString("let"));

            var compiledValue = new Content(stripped.sliceFrom(separator.next().$()), 0);
            return new DefinitionNode(indent, flagsString, name, compiledValue);
        });
    }
}