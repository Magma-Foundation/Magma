package com.meti;

import java.util.List;

import static com.meti.None.None;
import static com.meti.Some.Some;

public record FieldLexer(String stripped, int indent) {
    Option<Node> lex() {
        if (!stripped().startsWith("public static final Path ")) return None();
        var content = stripped().substring("public static final Path ".length());
        var equals = content.indexOf('=');
        var name = content.substring(0, equals).strip();
        var compiledValue = new Content(content.substring(equals + 1), 0);
        var flags = List.of("pub", "const");
        return Some(new FieldNode(indent(), flags, name, compiledValue));
    }
}