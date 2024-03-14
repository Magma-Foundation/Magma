package com.meti.compile.string;

import com.meti.collect.option.Option;
import com.meti.compile.Lexer;
import com.meti.compile.node.Node;

import static com.meti.collect.option.None.None;
import static com.meti.collect.option.Some.Some;

public record StringLexer(String stripped) implements Lexer {
    @Override
    public Option<Node> lex() {
        if (stripped().startsWith("\"") && stripped().endsWith("\"")) {
            return Some(new StringNode(stripped().substring(1, stripped().length() - 1)));
        }
        return None();
    }
}