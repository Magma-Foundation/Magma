package com.meti.compile.external;

import com.meti.collect.option.Option;
import com.meti.collect.stream.Stream;
import com.meti.collect.stream.Streams;
import com.meti.compile.Lexer;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;

import static com.meti.collect.option.Options.$Option;

public record ImportLexer(JavaString stripped) implements Lexer {
    private Option<Node> lex0() {
        return $Option(() -> {
            var prefix = stripped.firstIndexOfSlice("import ")
                    .$()
                    .next("import ".length())
                    .$();

            var staticIndex = stripped.firstIndexOfSlice("import static ")
                    .flatMap(index -> index.next("import static ".length()));

            var content = stripped.sliceFrom(staticIndex.orElse(prefix));

            var last = content.lastIndexOfChar('.').$();
            var child = content.sliceFrom(last.next().$()).strip();
            var parent = content.sliceTo(last).strip();

            return child.equalsToSlice("*")
                    ? new ImportAllNode(parent)
                    : new ImportChildNode(child, parent);
        });
    }

    @Override
    public Stream<Node> lex() {
        return Streams.fromOption(lex0());
    }
}