package com.meti.compile.scope;

import com.meti.collect.option.Options;
import com.meti.collect.stream.Stream;
import com.meti.collect.stream.Streams;
import com.meti.compile.Lexer;
import com.meti.compile.node.Content;
import com.meti.compile.node.MapNode;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;

import static com.meti.collect.option.Options.$Option;

public record ReturnLexer(JavaString input, int indent) implements Lexer {
    public static final JavaString Id = JavaString.from("return");

    @Override
    public Stream<Node> lex() {
        return Streams.fromOption($Option(() -> {
            var index = input.firstIndexOfSlice("return ").$();
            if (!index.isStart()) Options.$$();

            var content = index.next("return ".length()).$();
            var content1 = new Content(input.sliceFrom(content), indent);
            return MapNode.Builder(Id)
                    .withNode("value", content1)
                    .withInteger("indent", indent)
                    .complete();
        }));
    }
}
