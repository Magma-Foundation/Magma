package com.meti;

import static com.meti.None.None;
import static com.meti.Some.Some;

public record StringLexer(String stripped) implements Lexer {
    @Override
    public Option<Node> lex() {
        if (stripped().startsWith("\"") && stripped().endsWith("\"")) {
            return Some(new StringNode(stripped().substring(1, stripped().length() - 1)));
        }
        return None();
    }
}