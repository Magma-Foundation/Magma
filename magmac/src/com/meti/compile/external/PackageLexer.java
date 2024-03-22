package com.meti.compile.external;

import com.meti.collect.option.None;
import com.meti.collect.option.Option;
import com.meti.collect.option.Some;
import com.meti.collect.stream.Stream;
import com.meti.collect.stream.Streams;
import com.meti.compile.Lexer;
import com.meti.compile.node.Node;

public record PackageLexer(String input
) implements Lexer {
    private Option<Node> lex0() {
        return input.startsWith("package ")
                ? Some.Some(new PackageNode())
                : None.None();
    }

    @Override
    public Stream<Node> lex() {
        return Streams.fromOption(lex0());
    }
}
