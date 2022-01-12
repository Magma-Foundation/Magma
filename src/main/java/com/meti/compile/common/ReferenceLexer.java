package com.meti.compile.common;

import com.meti.compile.lex.Lexer;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record ReferenceLexer(Text text) implements Lexer {
    @Override
    public Option<Node> lex() {
        if (text.startsWithChar('&')) {
            var valueText = text.slice(1);
            var node = new Content(valueText);
            return new Some<>(new Reference(node));
        } else {
            return new None<>();
        }
    }
}
