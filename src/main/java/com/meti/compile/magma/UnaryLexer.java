package com.meti.compile.magma;

import com.meti.compile.lex.Lexer;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.option.Option;

public record UnaryLexer(Text text) implements Lexer {
    @Override
    public Option<Node> lex() {
        return text.firstIndexOfChar(' ').map(separator -> {
            var callerString = text.slice(0, separator);
            var caller = new Content(callerString);

            var calleeString = text.slice(separator + 1);
            var callee = new Content(calleeString);

            return new UnaryOperation(caller, callee);
        });
    }
}
