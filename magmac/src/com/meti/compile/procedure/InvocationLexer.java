package com.meti.compile.procedure;

import com.meti.collect.option.Option;
import com.meti.collect.option.Options;
import com.meti.collect.option.Some;
import com.meti.collect.stream.Collectors;
import com.meti.compile.Lexer;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.meti.collect.option.None.None;
import static com.meti.collect.option.Options.$Option;

public final class InvocationLexer implements Lexer {
    private final JavaString stripped;

    public InvocationLexer(JavaString javaString) {
        this.stripped = javaString;
    }

    @Override
    public Option<Node> lex() {
        return $Option(() -> {
            var start = stripped.firstIndexOfChar('(').$();
            var end = stripped.lastIndexOf(')').$();
            var range = start.to(end).$();

            var list = stripped.sliceBetween(range)
                    .split(",")
                    .map(arg -> new Content(arg, 0))
                    .collect(Collectors.toList());

            return new InvocationNode(list);
        });
    }
}