package com.meti.compile.magma;

import com.meti.compile.common.*;
import com.meti.compile.lex.Lexer;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

public record FunctionLexer(Text text) implements Lexer {
    @Override
    public Option<Node> lex() {
        return text.firstIndexOfChar('(').flatMap(paramStart -> {
            var keys = text.slice(0, paramStart);
            return keys.lastIndexOfChar(' ').flatMap(space -> {
                var flagStrings = Arrays.stream(keys.slice(0, space)
                        .computeTrimmed()
                        .split(" "))
                        .filter(value -> !value.isBlank())
                        .map(String::toUpperCase)
                        .collect(Collectors.toList());

                var flags = new HashSet<EmptyField.Flag>();
                for (String flagString : flagStrings) {
                    for (EmptyField.Flag value : EmptyField.Flag.values()) {
                        if (value.name().toUpperCase().equals(flagString)) {
                            flags.add(value);
                        }
                    }
                }

                if (flags.contains(Field.Flag.Def)) {
                    var name = keys.slice(space + 1);

                    var depth = 0;
                    var paramEnd = -1;
                    for (int i = paramStart + 1; i < text.size(); i++) {
                        var c = text.apply(i);
                        if (c == ')' && depth == 0) {
                            paramEnd = i;
                        } else {
                            if (c == '(') depth++;
                            if (c == ')') depth--;
                        }
                    }

                    var parameters = Arrays.stream(text.slice(paramStart + 1, paramEnd).computeTrimmed()
                            .split(","))
                            .filter(value -> !value.isBlank())
                            .map(Text::new)
                            .map(Content::new)
                            .collect(Collectors.<Node>toSet());

                    var valueSeparator = text.firstIndexOfSlice("=>", paramEnd);
                    var returnType = text.firstIndexOfCharWithOffset(':', paramEnd)
                            .filter(value -> value < valueSeparator.orElse(text.size()))
                            .map(value -> sliceToContent(value + 1, valueSeparator))
                            .orElse(new ImplicitType());

                    var identity = new EmptyField(flags, name, returnType);

                    return new Some<>(valueSeparator.<Function, RuntimeException>map(separator -> {
                        var value = new Content(text.slice(separator + 2));
                        return new Implementation(identity, parameters, value);
                    }).orElseGet(() -> new Abstraction(identity, parameters)));
                } else {
                    return new None<>();
                }
            });
        });
    }

    private Node sliceToContent(int start, Option<Integer> terminus) {
        var end = terminus.orElse(text.size());
        var slice = text.slice(start, end);
        return new Content(slice);
    }
}
