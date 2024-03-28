package com.meti.lex;

import com.meti.Tuple;
import com.meti.node.MapNode;
import com.meti.node.Node;
import com.meti.rule.Rule;

import java.util.Optional;

public class RuleLexer implements Lexer {
    private final Rule rule;
    private final String value;
    private final String id;

    public RuleLexer(String id, String value, Rule rule) {
        this.rule = rule;
        this.value = value;
        this.id = id;
    }

    @Override
    public Optional<Node> lex() {
        return rule.lexImpl(value)
                .map(Tuple::b)
                .map(attributes -> new MapNode(id, attributes));
    }
}