package com.meti.compile;

import com.meti.compile.node.Content;
import com.meti.compile.node.Text;
import com.meti.compile.node.Node;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

import java.util.ArrayList;

public record BlockLexer(Text text) implements Lexer {
    @Override
    public Option<Node> lex() {
        if (text.startsWithChar('{') && text.compute().endsWith("}")) {
            var lines = text.slice(1, text.compute().length() - 1).compute().split(";");
            var values = new ArrayList<Node>();
            for (String line : lines) {
                if (!line.isBlank()) {
                    values.add(new Content(new Text(line)));
                }
            }
            return new Some<>(new Block(values));
        }
        return new None<>();
    }
}
