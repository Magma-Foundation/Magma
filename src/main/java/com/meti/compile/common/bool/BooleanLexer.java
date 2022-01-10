package com.meti.compile.common.bool;

import com.meti.compile.lex.Lexer;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record BooleanLexer(Text text) implements Lexer {
    @Override
    public Option<Node> lex() {
        var trimmed = text.computeTrimmed();
        var state = trimmed.equals("true");
        if (trimmed.equals("false") || state) {
            return new Some<>(state ? Boolean.True : Boolean.False);
        }
        return new None<>();
    }
}
