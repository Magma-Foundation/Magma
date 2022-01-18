package com.meti.app.compile.common;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.lex.Lexer;
import com.meti.app.compile.node.Content;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.text.Text;

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
