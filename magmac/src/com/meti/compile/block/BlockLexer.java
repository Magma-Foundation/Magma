package com.meti.compile.block;

import com.meti.api.collect.*;
import com.meti.api.option.Option;
import com.meti.compile.*;

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

            Range range = bodyStart.to(bodyEnd).$();
            var slicedBody = this.root().sliceBetween(range).strip();
            var collect1 = new Splitter(slicedBody).split();
            var collect = collect1.iter()
                    .map(Content::new)
                    .collect(ImmutableLists.into());

            return MapNode.Builder(JavaString.apply("block"))
                    .withListOfNodes(JavaString.apply("lines"), collect)
                    .complete();
        });
    }
}