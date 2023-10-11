package com.meti.compile;

import com.meti.api.collect.Iterator;
import com.meti.api.collect.JavaString;
import com.meti.compile.rule.RuleNodeLexers;

public class FileNodeLexerFactory {
    private final JavaString content;

    public FileNodeLexerFactory(JavaString content) {
        this.content = content;
    }

    public NodeLexer create(JavaString input, JavaString type) {
        return new CompoundLexer() {
            @Override
            Iterator<NodeLexer> enumerateLexers() {
                return content.split("\n")
                        .map(JavaString::strip)
                        .filter(value -> !value.isBlank())
                        .map(line -> RuleNodeLexers.createDeclarationLexer(line.value(), input, type));
            }
        };
    }
}
