package com.meti.compile;

import com.meti.compile.node.Content;
import com.meti.compile.node.Text;
import com.meti.compile.node.Node;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record FunctionLexer(Text text) implements Lexer {
    @Override
    public Option<Node> lex() {
        if (text.compute().startsWith("def ")) {
            var paramStart = text.compute().indexOf('(');
            var name = text.slice("def ".length(), paramStart);
            var typeSeparator = text.compute().indexOf(':');
            var valueSeparator = text.compute().indexOf("=>");
            var returnType = new Content(text.slice(typeSeparator + 1, valueSeparator));
            var value = new Content(text.slice(valueSeparator + 2));
            var identity = new Field(name, returnType);
            return new Some<>(new Function(identity, value));
        }
        return new None<>();
    }

}
