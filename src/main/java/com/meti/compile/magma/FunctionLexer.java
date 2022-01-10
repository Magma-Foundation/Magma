package com.meti.compile.magma;

import com.meti.compile.common.Field;
import com.meti.compile.common.Function;
import com.meti.compile.lex.Lexer;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record FunctionLexer(Text text) implements Lexer {
    @Override
    public Option<Node> lex() {
        if (text.computeTrimmed().startsWith("def ")) {
            var paramStart = text.computeTrimmed().indexOf('(');
            var name = text.slice("def ".length(), paramStart);
            var typeSeparator = text.computeTrimmed().indexOf(':');
            var valueSeparator = text.computeTrimmed().indexOf("=>");
            var returnType = new Content(text.slice(typeSeparator + 1, valueSeparator));
            var value = new Content(text.slice(valueSeparator + 2));
            var identity = new Field(name, returnType);
            return new Some<>(new Function(identity, value));
        }
        return new None<>();
    }

}
