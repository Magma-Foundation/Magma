package com.meti.app.compile.common.returns;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.lex.Lexer;
import com.meti.app.compile.node.Content;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.text.RootText;
import com.meti.app.compile.text.Text;

public record ReturnLexer(Text text) implements Lexer {
    @Override
    public Option<Node> lex() {
        if (text.computeTrimmed().startsWith("return ")) {
            var valueString = new RootText(text.computeTrimmed()).slice("return ".length(), text.computeTrimmed().length()).computeTrimmed();
            var value = new Content(new RootText(valueString));
            return new Some<>(new Return(value));
        }
        return new None<>();
    }
}
