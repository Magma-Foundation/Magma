package com.meti.stage;

import com.meti.ImportLexer;
import com.meti.java.*;
import com.meti.node.Content;

import java.util.List;

public class JavaLexingStage extends LexingStage {
    @Override
    protected Lexer createLexer(Content value) {
        var innerValue = value.value();
        if (value.name().equals("top")) {
            return new CompoundLexer(List.of(
                    () -> new ClassLexer(value.value(), value.indent()),
                    () -> new ImportLexer(value.value())
            ));
        } else {
            if (value.name().equals("class")) {
                return new ClassLexer(innerValue, value.indent());
            } else if (value.name().equals(DefinitionLexer.ID)) {
                return new DefinitionLexer(innerValue);
            } else if (value.name().equals("value")) {
                return new CompoundLexer(List.of(
                        () -> new FieldLexer(innerValue),
                        () -> new InvokeLexer(innerValue),
                        () -> new IntegerLexer(innerValue),
                        () -> new StringLexer(innerValue)));
            } else {
                throw new UnsupportedOperationException("Unknown node name: " + value.name());
            }
        }
    }
}
