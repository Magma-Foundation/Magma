package com.meti.compile.magma;

import com.meti.compile.common.Field;
import com.meti.compile.common.Function;
import com.meti.compile.lex.Lexer;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.option.None;
import com.meti.option.Option;

import java.util.Collections;

public record FunctionLexer(Text text) implements Lexer {
    @Override
    public Option<Node> lex() {
        if (text.startsWithSlice("def ")) {
            return text.firstIndexOfChar('(').map(paramStart -> {
                var name = text.slice("def ".length(), paramStart);
                var typeSeparator = text.lastIndexOfChar(':').orElse(-1);
                var valueSeparator = text.firstIndexOfSlice("=>").orElse(-1);
                var returnType = new Content(text.slice(typeSeparator + 1, valueSeparator));
                var value = new Content(text.slice(valueSeparator + 2));
                var identity = new Field(name, returnType);
                return new Function(identity, Collections.emptySet(), value);
            });
        }
        return new None<>();
    }
}
