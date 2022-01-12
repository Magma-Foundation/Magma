package com.meti.compile.common.returns;

import com.meti.compile.lex.Lexer;
import com.meti.compile.node.Content;
import com.meti.compile.node.Text;
import com.meti.compile.node.Node;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record ReturnLexer(Text text) implements Lexer {
    @Override
    public Option<Node> lex() {
        if (text.computeTrimmed().startsWith("return ")) {
            var valueString = new Text(text.computeTrimmed()).slice("return ".length(), text.computeTrimmed().length()).computeTrimmed();
            var value = new Content(new Text(valueString));
            return new Some<>(new Return(value));
        }
        return new None<>();
    }
}