package com.meti.compile.scope;

import com.meti.collect.option.Option;
import com.meti.collect.option.Options;
import com.meti.collect.stream.Stream;
import com.meti.collect.stream.Streams;
import com.meti.compile.Lexer;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;

public record FieldLexer(JavaString input) implements Lexer {
    private Option<Node> lex0() {
        return Options.$Option(() -> {
            var separator = input.firstIndexOfChar('.').$();
            var parent = input.sliceTo(separator);
            var child = input.sliceFrom(separator.next().$());
            return new FieldNode(new Content(parent, 0), child);
        });
    }

    @Override
    public Stream<Node> lex() {
        return Streams.fromOption(lex0());
    }
}
