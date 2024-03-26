package com.meti.java;

import com.meti.node.MapNode;
import com.meti.node.Node;
import com.meti.node.StringAttribute;
import com.meti.rule.RequireRule;
import com.meti.rule.Rule;
import com.meti.rule.Rules;
import com.meti.rule.TextRule;

import java.util.Map;
import java.util.Optional;

import static com.meti.rule.AndRule.And;

public class StringLexer implements Lexer {
    public static final String ID = "string";
    public static final String VALUE = "value";
    public static final Rule RULE = And(new RequireRule("\""),
            new TextRule(VALUE, Rules.Any),
            new RequireRule("\""));

    private final String value;

    public StringLexer(String value) {
        this.value = value;
    }

    @Override
    public Optional<Node> lex() {
        return RULE.apply(value).map(attributes -> new MapNode(ID, attributes));
    }
}
