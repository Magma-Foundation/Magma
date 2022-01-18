package com.meti.app.compile.common.alternate;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.lex.Lexer;
import com.meti.app.compile.node.InputNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.text.Input;

public record ElseLexer(Input text) implements Lexer {
    @Override
    public Option<Node> lex() {
        if (text.startsWithSlice("else ")) {
            var valueText = text.slice("else ".length());
            var value = new InputNode(valueText);
            return new Some<>(new Else(value));
        }
        return new None<>();
    }
}
