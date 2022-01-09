package com.meti.compile;

import com.meti.compile.node.Content;
import com.meti.compile.node.Text;
import com.meti.compile.node.Node;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record IntegerLexer(Text text) implements Lexer {
    @Override
    public Option<Node> lex() {
        try {
            Integer.parseInt(text.compute());
            return new Some<>(new Content(new Text(text.compute())));
        } catch (NumberFormatException e) {
            return new None<>();
        }
    }
}
