package com.meti.compile.java;

import com.meti.collect.stream.Stream;
import com.meti.collect.stream.Streams;
import com.meti.compile.Lexer;
import com.meti.compile.node.MapNode;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;

public record IntegerLexer(JavaString input) implements Lexer {
    public static final String Id = "int";

    @Override
    public Stream<Node> lex() {
        try {
            var value = Integer.parseInt(input.inner());
            return Streams.from(MapNode.Builder(JavaString.from(Id))
                    .withInteger("value", value)
                    .complete());
        } catch (NumberFormatException e) {
            return Streams.empty();
        }
    }
}
