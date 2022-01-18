package com.meti.app.compile.magma;

import com.meti.api.option.Option;
import com.meti.app.compile.lex.Lexer;
import com.meti.app.compile.node.Content;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.text.Text;

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
