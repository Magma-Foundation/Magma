package com.meti.compile.block;

import com.meti.api.collect.Index;
import com.meti.api.collect.JavaString;
import com.meti.api.option.Option;
import com.meti.compile.Lexer;
import com.meti.compile.Node;
import com.meti.compile.Splitter;

import static com.meti.api.option.Options.$Option;

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