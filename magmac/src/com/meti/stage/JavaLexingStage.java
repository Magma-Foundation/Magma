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
                    () -> new DefinitionLexer(value.value(), value.indent()),
                    () -> new ClassLexer(value.value(), value.indent())
            ));

            case "method-statement" -> new DefinitionLexer(innerValue, value.indent());
            case "value" -> new CompoundLexer(List.of(
                    () -> new RuleLexer("string", STRING, innerValue),
                    () -> new FieldLexer(innerValue),
                    () -> new InvokeLexer(innerValue),
                    () -> new RuleLexer("int", INT, innerValue)));
            default -> throw new UnsupportedOperationException("Unknown node name: " + value.name());
        };
    }
}
