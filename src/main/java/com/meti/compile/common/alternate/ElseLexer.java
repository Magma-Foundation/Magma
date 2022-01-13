package com.meti.compile.common.alternate;

import com.meti.compile.lex.Lexer;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

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
