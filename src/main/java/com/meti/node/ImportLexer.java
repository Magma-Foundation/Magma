package com.meti.node;

import java.util.Optional;

public record ImportLexer(String input) implements Lexer {
    static String formatImport(String value, int separator) {
        String importValue;
        if (separator == -1) {
            importValue = value;
        } else {
            var before = value.substring(0, separator);
            var after = value.substring(separator + 1);
            importValue = after + " from " + before;
        }
        return importValue;
    }

    @Override
    public Optional<Node> lex() {
        if (input().startsWith("import ")) {
            var value = input().substring(Import.Prefix.length());
            var separator = value.indexOf('.');
            var importValue = formatImport(value, separator);
            return Optional.of(new Import(importValue));
        }
        return Optional.empty();
    }
}