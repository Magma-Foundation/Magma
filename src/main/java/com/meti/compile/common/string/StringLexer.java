package com.meti.compile.common.string;

import com.meti.compile.lex.Lexer;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

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
