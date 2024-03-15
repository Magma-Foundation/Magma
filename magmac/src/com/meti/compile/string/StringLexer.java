package com.meti.compile.string;

import com.meti.collect.option.Option;
import com.meti.compile.Lexer;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;

import static com.meti.collect.option.None.None;
import static com.meti.collect.option.Options.$$;
import static com.meti.collect.option.Options.$Option;
import static com.meti.collect.option.Some.Some;

public record StringLexer(JavaString stripped) implements Lexer {
    @Override
    public Option<Node> lex() {
        return $Option(() -> {
            var start = stripped.firstIndexOfChar('"').$();
            var end = stripped.lastIndexOfChar('"').$();

            if (!start.isStart()) $$();
            if (!end.isEnd()) $$();

            return new StringNode(stripped.sliceBetween(start.next().$().to(end).$()));
        });
    }
}