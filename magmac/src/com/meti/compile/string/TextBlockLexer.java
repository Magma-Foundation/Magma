package com.meti.compile.string;

import com.meti.collect.option.Option;
import com.meti.collect.stream.Stream;
import com.meti.collect.stream.Streams;
import com.meti.compile.Lexer;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;

import static com.meti.collect.option.Options.$$;
import static com.meti.collect.option.Options.$Option;

public record TextBlockLexer(JavaString stripped) implements Lexer {
    private Option<Node> lex0() {
        return $Option(() -> {
            var start = stripped.firstIndexOfSlice("\"\"\"").$();
            var end = stripped.lastIndexOfSlice("\"\"\"").$();

            if (!start.isStart()) $$();
            if (!end.isEnd()) $$();

            return new TextBlockNode(stripped.sliceBetween(start.next("\"\"\"".length()).$().to(end).$()));
        });
    }

    @Override
    public Stream<Node> lex() {
        return Streams.fromOption(lex0());
    }
}