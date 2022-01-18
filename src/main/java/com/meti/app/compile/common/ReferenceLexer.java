package com.meti.app.compile.common;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.lex.Lexer;
import com.meti.app.compile.node.InputNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.text.Input;

public record ReferenceLexer(Input text) implements Lexer {
    @Override
    public Option<Node> lex() {
        if (text.startsWithChar('&')) {
            var valueText = text.slice(1);
            var node = new InputNode(valueText);
            return new Some<>(new Reference(node));
        } else {
            return new None<>();
        }
    }
}
