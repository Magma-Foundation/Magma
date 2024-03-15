package com.meti.compile.scope;

import com.meti.collect.Index;
import com.meti.collect.option.Option;
import com.meti.collect.stream.Collectors;
import com.meti.collect.stream.Streams;
import com.meti.compile.Lexer;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;

import static com.meti.collect.option.Options.$Option;

public class LambdaLexer implements Lexer {
    private final JavaString input;

    public LambdaLexer(JavaString input) {
        this.input = input;
    }

    @Override
    public Option<Node> lex() {
        return $Option(() -> {
            var index = input.firstIndexOfSlice("->").$();
            var argumentString = input.sliceTo(index).strip();

            var argStart = argumentString.firstIndexOfChar('(');
            var argEnd = argumentString.firstIndexOfChar(')');
            var arguments = argStart.flatMap(Index::next).and(argEnd)
                    .flatMap(tuple -> tuple.a().to(tuple.b()))
                    .map(input::sliceBetween)
                    .map(args -> args.split(",").map(JavaString::strip).filter(value -> !value.isEmpty()))
                    .orElseGet(() -> Streams.from(argumentString))
                    .collect(Collectors.toNativeList());

            var content = input.sliceFrom(index.next("->".length()).$()).strip();
            var contentNode = new Content(content, 0);
            return new LambdaNode(arguments, contentNode);
        });
    }
}
