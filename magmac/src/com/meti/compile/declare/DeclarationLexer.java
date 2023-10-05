package com.meti.compile.declare;

import com.meti.api.collect.JavaString;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.compile.Lexer;
import com.meti.compile.RuleLexerFactory;
import com.meti.compile.lex.*;
import com.meti.compile.node.MapNode;
import com.meti.compile.node.Node;

import static com.meti.api.option.Options.$Option;

public class DeclarationLexer implements Lexer {
    private final JavaString input;
    private final JavaString actualType;
    private final Rule rule;
    private final String requiredType;

    public DeclarationLexer(JavaString input, JavaString actualType, Rule rule, String requiredType) {
        this.input = input;
        this.actualType = actualType;
        this.requiredType = requiredType;
        this.rule = rule;
    }

    public static DeclarationLexer createDeclarationLexer(JavaString input, JavaString type) {
        return new RuleLexerFactory("definition", ConjunctionRule.of(
                ContentRule.of(JavaString.apply("type")),
                ValueRule.of(JavaString.apply(" ")),
                TextRule.of(JavaString.apply("name"))
        )).createDeclarationLexer(input, type);
    }

    @Override
    public Option<Node> lex() {
        if (!actualType.equalsToSlice(requiredType)) {
            return None.apply();
        }

        return $Option(() -> {
            var result = rule.extract(input).$();
            var left = result.getContent().get("type").$();
            var right = result.getText().get("name").$();
            return MapNode.Builder("type")
                    .withString("name", right)
                    .withContent("type", left)
                    .complete();
        });
    }
}
