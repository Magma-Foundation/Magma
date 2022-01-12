package com.meti.compile.magma;

import com.meti.compile.common.Abstraction;
import com.meti.compile.common.Field;
import com.meti.compile.common.Function;
import com.meti.compile.common.Implementation;
import com.meti.compile.lex.Lexer;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.option.Option;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

public record FunctionLexer(Text text) implements Lexer {
    @Override
    public Option<Node> lex() {
        return text.firstIndexOfChar('(').flatMap(paramStart -> {
            var keys = text.slice(0, paramStart);
            return keys.lastIndexOfChar(' ').map(space -> {
                var flagStrings = Arrays.stream(keys.slice(0, space)
                        .computeTrimmed()
                        .split(" "))
                        .filter(value -> !value.isBlank())
                        .map(String::toUpperCase)
                        .collect(Collectors.toList());

                var flags = new HashSet<Field.Flag>();
                for (String flagString : flagStrings) {
                    for (Field.Flag value : Field.Flag.values()) {
                        if (value.name().toUpperCase().equals(flagString)) {
                            flags.add(value);
                        }
                    }
                }

                var name = keys.slice(space + 1);

                var paramEnd = text.firstIndexOfChar(')').orElse(-1);
                var parameters = Arrays.stream(text.slice(paramStart + 1, paramEnd).computeTrimmed()
                        .split(","))
                        .filter(value -> !value.isBlank())
                        .map(Text::new)
                        .map(Content::new)
                        .collect(Collectors.<Node>toSet());

                var valueSeparator = text.firstIndexOfSlice("=>");
                var returnType = text.lastIndexOfChar(':')
                        .<Node, RuntimeException>map(value -> new Content(text.slice(value + 1, valueSeparator.orElse(text.size()))))
                        .orElse(new ImplicitType());

                var identity = new Field(flags, name, returnType);

                return valueSeparator.<Function, RuntimeException>map(separator -> {
                    var value = new Content(text.slice(separator + 2));
                    return new Implementation(identity, parameters, value);
                }).orElseGet(() -> new Abstraction(identity, parameters));
            });
        });
    }
}
