package com.meti.app.compile.common.string;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.lex.Lexer;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.Text;

public record StringLexer(Text text) implements Lexer {
    @Override
    public Option<Node> lex() {
        if (text.startsWithChar('\"') && text.endsWithChar('\"')) {
            var value = text.sliceRaw(1, text.size() - 1);
            return new Some<>(new String(value));
        } else {
            return new None<>();
        }
    }
}
