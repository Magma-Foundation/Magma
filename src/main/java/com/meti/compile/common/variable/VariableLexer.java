package com.meti.compile.common.variable;

import com.meti.compile.lex.Lexer;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record VariableLexer(Text text) implements Lexer {
    @Override
    public Option<Node> lex() {
        var trimmed = text.computeTrimmed();
        for (int i = 0; i < trimmed.length(); i++) {
            var c = trimmed.charAt(i);
            if (!Character.isLetter(c)) {
                return new None<>();
            }
        }
        return new Some<>(new Variable(text));
    }
}
