package com.meti.app.compile.common.returns;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.lex.Lexer;
import com.meti.app.compile.node.Content;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.Text;

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
