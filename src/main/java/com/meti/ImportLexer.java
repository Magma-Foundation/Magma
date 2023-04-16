package com.meti;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public record ImportLexer(String line) implements Lexer {
    @Override
    public Optional<Node> lex() {
        if (line().startsWith("import ")) {
            var importSlice = line().substring("import ".length());
            var args = Arrays.stream(importSlice.split("\\."))
                    .map(String::strip)
                    .collect(Collectors.toList());

            return Optional.of(new Import(args));
        } else {
            return Optional.empty();
        }
    }
}