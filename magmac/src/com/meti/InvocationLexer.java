package com.meti;

import java.util.Arrays;

public record InvocationLexer(String stripped) {
    public Option<Node> lex() {
        if (stripped().startsWith("Paths.get(")) {
            var start = stripped().indexOf("(");
            var end = stripped().indexOf(")");
            var list = Arrays.stream(stripped().substring(start + 1, end).split(","))
                    .map(arg -> new Content(arg, 0))
                    .toList();

            return Some.Some(new InvocationNode(list));
        }
        return None.None();
    }
}