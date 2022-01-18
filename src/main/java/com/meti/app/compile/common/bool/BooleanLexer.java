package com.meti.app.compile.common.bool;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.lex.Lexer;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.Text;

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
