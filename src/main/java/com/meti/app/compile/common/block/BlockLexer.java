package com.meti.app.compile.common.block;

import com.meti.api.collect.java.List;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.lex.Lexer;
import com.meti.app.compile.node.InputNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.text.Input;

import java.util.ArrayList;
import java.util.stream.Collectors;

public record BlockLexer(Input text) implements Lexer {
    @Override
    public Option<Node> lex() {
        if (text.startsWithChar('{') && text.endsWithChar('}')) {
            var body = text.slice(1, text.size() - 1);
            var lines = new Splitter(body)
                    .split()
                    .collect(Collectors.toList());

            var values = new ArrayList<Node>();
            for (var line : lines) {
                if (!line.isEmpty()) {
                    values.add(new InputNode(line));
                }
            }
            return new Some<>(new Block(List.createList(values)));
        }
        return new None<>();
    }
}
