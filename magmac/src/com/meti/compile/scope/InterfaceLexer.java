package com.meti.compile.scope;

import com.meti.collect.Index;
import com.meti.collect.option.Option;
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

    @Override
    public Option<Node> lex() {
        return $Option(() -> {
            var keywordIndex = root.firstIndexOfSlice("interface ")
                    .$()
                    .next("interface ".length())
                    .$();

            var contentStart = root.firstIndexOfChar('{').$();

            var name = root.sliceBetween(keywordIndex.to(contentStart).$()).strip();
            var content = new Content(root.sliceFrom(contentStart), 0);

            return new TraitNode(name, content);
        });
    }
}
