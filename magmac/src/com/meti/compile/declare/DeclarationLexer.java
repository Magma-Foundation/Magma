package com.meti.compile.declare;

import com.meti.api.collect.JavaString;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.compile.Lexer;
import com.meti.compile.lex.AnyRule;
import com.meti.compile.lex.ConjunctionRule;
import com.meti.compile.lex.ValueRule;
import com.meti.compile.node.Content;
import com.meti.compile.node.MapNode;
import com.meti.compile.node.Node;

import static com.meti.api.option.Options.$Option;

public class DeclarationLexer implements Lexer {
    private final JavaString input;
    private final JavaString type;

    public DeclarationLexer(JavaString input, JavaString type) {
        this.input = input;
        this.type = type;
    }

    @Override
    public Option<Node> lex() {
        if (!type.equalsToSlice("definition")) {
            return None.apply();
        }

        var rule = ConjunctionRule.of(
                AnyRule.of(JavaString.apply("type")),
                ValueRule.of(JavaString.apply(" ")),
                AnyRule.of(JavaString.apply("name"))
        );

        return $Option(() -> {
            var result = rule.extract(input).$();
            var left = result.getStrings().get("type").$();
            var right = result.getStrings().get("name").$();
            return MapNode.Builder("type")
                    .withString("name", right)
                    .withNode("type", new Content(left, JavaString.apply("type")))
                    .complete();
        });
    }
}
