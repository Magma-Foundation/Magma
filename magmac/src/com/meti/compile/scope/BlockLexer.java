package com.meti.compile.scope;

import com.meti.collect.stream.Collectors;
import com.meti.collect.option.Option;
import com.meti.compile.Lexer;
import com.meti.compile.Splitter;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;

import static com.meti.collect.option.None.None;
import static com.meti.collect.option.Some.Some;

public record BlockLexer(String stripped, int indent) implements Lexer {
    @Override
    public Option<Node> lex() {
        if (!stripped().startsWith("{") || !stripped().endsWith("}")) return None();

        var split = new Splitter(stripped().substring(1, stripped().length() - 1).strip()).split();
        var content = split
                .map(line1 -> new Content(line1, 1))
                .collect(Collectors.toList());

        return Some(new BlockNode(indent(), content));
    }
}