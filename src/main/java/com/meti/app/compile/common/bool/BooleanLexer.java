package com.meti.app.compile.common.bool;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.lex.Lexer;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.text.Input;

public record BooleanLexer(Input text) implements Lexer {
    @Override
    public Option<Node> lex() {
        var state = text.equalsSlice("true");
        if (text.equalsSlice("false") || state) {
            return new Some<>(state ? Boolean.True : Boolean.False);
        }
        return new None<>();
    }
}
