package com.meti.compile.scope;

import com.meti.collect.JavaList;
import com.meti.collect.option.Option;
import com.meti.collect.stream.Collectors;
import com.meti.collect.stream.Stream;
import com.meti.collect.stream.Streams;
import com.meti.compile.Lexer;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;

import static com.meti.collect.option.Options.$Option;

public class InterfaceLexer implements Lexer {
    private final JavaString root;

    public InterfaceLexer(JavaString root) {
        this.root = root;
    }

    private Option<Node> lex0() {
        return $Option(() -> {
            var keywordStart = root.firstIndexOfSlice("interface ").$();
            var keywordEnd = keywordStart.next("interface ".length()).$();

            var flags = root.sliceTo(keywordStart).strip().split(" ").collect(Collectors.toList());

            var contentStart = root.firstIndexOfChar('{').$();

            var name = root.sliceBetween(keywordEnd.to(contentStart).$()).strip();
            var content = new Content(root.sliceFrom(contentStart), 0);

            var list = new JavaList<JavaString>();
            if (flags.contains(new JavaString("public"))) {
                list = list.add(new JavaString("export"));
            }

            return new TraitNode(name, content, list);
        });
    }

    @Override
    public Stream<Node> lex() {
        return Streams.fromOption(lex0());
    }
}
