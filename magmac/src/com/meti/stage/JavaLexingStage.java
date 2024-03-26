package com.meti.stage;

import com.meti.ImportLexer;
import com.meti.java.*;
import com.meti.node.Content;
import com.meti.rule.*;

import java.util.List;

import static com.meti.rule.AndRule.And;

public class JavaLexingStage extends LexingStage {
    public static final Rule STRING = And(new RequireRule("\""),
            new TextRule("value", Rules.Any),
            new RequireRule("\""));

    public static final TextRule INT = new TextRule("value", new ListRule(new RangeRule('0', '9')));

    public static final Rule INVOKE = And(
            new NodeRule("caller", "value", 0),
            new RequireRule("("),
            new ListRule(new NodeListRule("arguments", "value", 0)),
            new RequireRule(")")
    );

    private final Rule FIELD = And(
            new NodeRule("parent", "value", 0),
            new RequireRule("."),
            Rules.Symbol("member")
    );

    @Override
    protected Lexer createLexer(Content value) {
        var innerValue = value.value();
        return switch (value.name()) {
            case "top" -> new CompoundLexer(List.of(
                    () -> new ClassLexer(value.value(), value.indent()),
                    () -> new ImportLexer(value.value())
            ));
            case "class" -> new ClassLexer(innerValue, value.indent());

            /*
            TODO: statements
             */
            case "class-member" -> new CompoundLexer(List.of(
                    () -> new MethodLexer(value.indent(), value.value()),
                    () -> DefinitionLexer.createDefinitionLexer(value.value(), value.indent()),
                    () -> new ClassLexer(value.value(), value.indent())
            ));

            case "method-statement" -> DefinitionLexer.createDefinitionLexer(innerValue, value.indent());
            case "value" -> new CompoundLexer(List.of(
                    () -> new RuleLexer("string", innerValue, STRING),
                    () -> new RuleLexer("field", innerValue, FIELD),
                    () -> new RuleLexer("invoke", innerValue, INVOKE),
                    () -> new RuleLexer("int", innerValue, INT)));
            default -> throw new UnsupportedOperationException("Unknown node name: " + value.name());
        };
    }
}
