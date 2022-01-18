package com.meti.app.compile.common.alternate;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.lex.Lexer;
import com.meti.app.compile.node.Content;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.text.Text;

public record ElseLexer(Text text) implements Lexer {
    @Override
    public Option<Node> lex() {
        if (text.startsWithSlice("else ")) {
            var valueText = text.slice("else ".length());
            var value = new Content(valueText);
            return new Some<>(new Else(value));
        }
        return new None<>();
    }
}
