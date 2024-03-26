package com.meti.java;

import com.meti.node.Node;
import com.meti.rule.Rule;

import java.util.Optional;

public class WrappedLexer implements Lexer {
    private final Rule rule;
    private final String input;

    public WrappedLexer(String input, Rule rule) {
        this.rule = rule;
        this.input = input;
    }

    @Override
    public Optional<Node> lex() {
        var apply = rule.apply(input).map(tuple -> tuple.b());
        return apply.flatMap(mapper -> mapper.get("value").asNode());
    }
}
