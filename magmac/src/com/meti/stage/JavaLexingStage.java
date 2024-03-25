package com.meti.stage;

import com.meti.ImportLexer;
import com.meti.java.*;
import com.meti.node.Content;

import java.util.List;

public class JavaLexingStage extends LexingStage {
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
            case DefinitionLexer.ID -> new DefinitionLexer(innerValue);
            case "value" -> new CompoundLexer(List.of(
                    () -> new FieldLexer(innerValue),
                    () -> new InvokeLexer(innerValue),
                    () -> new IntegerLexer(innerValue),
                    () -> new StringLexer(innerValue)));
            default -> throw new UnsupportedOperationException("Unknown node name: " + value.name());
        };
    }
}
