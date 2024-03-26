package com.meti.java;

import com.meti.node.MapNode;
import com.meti.node.Node;
import com.meti.rule.Rule;

import java.util.Optional;

public class NamedLexer implements Lexer {
    private final Rule rule;
    private final String input;

    public NamedLexer(String input, Rule rule) {
        this.rule = rule;
        this.input = input;
    }

    @Override
    public Optional<Node> lex() {
        return rule.apply(input).map(tuple -> new MapNode(tuple.a().orElseThrow(), tuple.b()));
    }
}
