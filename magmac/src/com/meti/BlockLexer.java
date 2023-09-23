package com.meti;

import static com.meti.Options.$Option;

public record BlockLexer(JavaString root) implements Lexer {
    @Override
    public Option<Node> lex() {
        return $Option(() -> {
            var bodyStart = root().firstIndexOfChar('{')
                    .filter(Index::isStart)
                    .$()
                    .next()
                    .$();

            var bodyEnd = root().lastIndexOfChar('}')
                    .filter(Index::isEnd)
                    .$();

            var slicedBody = root().sliceBetween(bodyStart.to(bodyEnd).$()).strip();
            var collect1 = new Splitter(slicedBody).split();
            return new BlockNode(collect1);
        });
    }
}