package com.meti.compile.common.block;

import com.meti.api.collect.JavaList;
import com.meti.compile.lex.Lexer;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

import java.util.ArrayList;
import java.util.stream.Collectors;

public record BlockLexer(Text text) implements Lexer {
    @Override
    public Option<Node> lex() {
        if (text.startsWithChar('{') && text.computeTrimmed().endsWith("}")) {
            var body = text.slice(1, text.computeTrimmed().length() - 1);
            var lines = new Splitter(body)
                    .split()
                    .collect(Collectors.toList());

            var values = new ArrayList<Node>();
            for (var line : lines) {
                if (!line.isEmpty()) {
                    values.add(new Content(line));
                }
            }
            return new Some<>(new Block(new JavaList<>(values)));
        }
        return new None<>();
    }
}
