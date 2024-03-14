package com.meti.compile.procedure;

import com.meti.collect.option.None;
import com.meti.collect.option.Some;
import com.meti.collect.option.Option;
import com.meti.compile.Lexer;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;

import java.util.Arrays;

public record InvocationLexer(String stripped) implements Lexer {
    @Override
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