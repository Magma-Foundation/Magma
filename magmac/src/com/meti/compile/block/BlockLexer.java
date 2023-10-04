package com.meti.compile.block;

import com.meti.api.collect.ImmutableLists;
import com.meti.api.collect.Index;
import com.meti.api.collect.JavaString;
import com.meti.api.collect.Range;
import com.meti.api.option.Option;
import com.meti.compile.Lexer;
import com.meti.compile.node.Content;
import com.meti.compile.node.MapNode;
import com.meti.compile.node.Node;
import com.meti.compile.state.Splitter;

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
                    .map(value -> new Content(value, JavaString.Empty))
                    .collect(ImmutableLists.into());

            return MapNode.Builder(JavaString.apply("block"))
                    .withNodeList(JavaString.apply("lines"), collect)
                    .complete();
        });
    }
}