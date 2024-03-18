package com.meti.compile.attempt;

import com.meti.collect.option.Option;
import com.meti.collect.option.Options;
import com.meti.collect.stream.Stream;
import com.meti.collect.stream.Streams;
import com.meti.compile.Lexer;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;

import static com.meti.collect.option.Options.$Option;

public record TryLexer(JavaString input, int indent) implements Lexer {
    private Option<Node> lex0() {
        return $Option(() -> {
            var index = input.firstIndexOfSlice("try").$();
            if(!index.isStart()) Options.$$();

            var content = index.next("try".length()).$();
            return new TryNode(new Content(input.sliceFrom(content), indent), indent);
        });
    }

    @Override
    public Stream<Node> lex() {
        return Streams.fromOption(lex0());
    }
}
