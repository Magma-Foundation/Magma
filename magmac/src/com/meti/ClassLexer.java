package com.meti;

import static com.meti.Options.$Option;

public record ClassLexer(JavaString stripped) implements Lexer {
    @Override
    public Option<Node> lex() {
        return $Option(() -> {
            if (!stripped().contains("class ")) {
                return Options.$$();
            }

            var bodyStart = stripped().firstIndexOfChar('{').$();
            var bodyEnd = stripped()
                    .lastIndexOfChar('}').$()
                    .next().$();

            var extendsIndex = stripped().firstIndexOfSlice("extends ");
            var name = extendsIndex.map(extendsIndex1 -> {
                var keys = stripped().sliceTo(extendsIndex1).strip();
                var separator = keys.lastIndexOf(' ');
                return keys.substring(separator + 1).strip();
            }).unwrapOrElseGet(() -> {
                var keys = stripped().sliceTo(bodyStart).strip();
                var separator = keys.lastIndexOf(' ');
                return keys.substring(separator + 1).strip();
            });

            var body = stripped().slice(bodyStart.to(bodyEnd).$()).strip();
            return new ClassNode(name, body);
        });
    }
}