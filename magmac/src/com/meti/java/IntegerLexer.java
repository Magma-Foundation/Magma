package com.meti.java;

import com.meti.node.MapNode;
import com.meti.node.Node;
import com.meti.rule.ListRule;
import com.meti.rule.RangeRule;
import com.meti.rule.Rule;
import com.meti.rule.TextRule;

import java.util.Optional;

public class IntegerLexer implements Lexer {
    public static final String VALUE = "value";
    private static final Rule RULE = new TextRule("value", new ListRule(new RangeRule('0', '9')));

    public static String Id = "int";
    private final String value;

    public IntegerLexer(String value) {
        this.value = value;
    }

    @Override
    public Optional<Node> lex() {
        return RULE.apply(value).map(attributes -> new MapNode(Id, attributes));
    }
}
