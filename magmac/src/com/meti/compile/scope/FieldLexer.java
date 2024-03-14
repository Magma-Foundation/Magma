package com.meti.compile.scope;

import com.meti.collect.Index;
import com.meti.collect.option.Option;
import com.meti.collect.option.Options;
import com.meti.compile.Lexer;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;

public record FieldLexer(JavaString input) implements Lexer {
    @Override
    public Option<Node> lex() {
        return Options.$Option(() -> {
            var separator = input.firstIndexOfChar('.').$();
            var parent = input.sliceTo(separator);
            var child = input.sliceFrom(separator.next().$());
            return new FieldNode(new Content(parent, 0), child);
        });
    }
}
