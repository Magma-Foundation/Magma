package com.meti.compile;

import com.meti.api.collect.JavaString;
import com.meti.compile.lex.Rule;
import com.meti.compile.lex.RuleLexer;

public record RuleLexerFactory(String requiredType, Rule rule) {
    public RuleLexer createDeclarationLexer(JavaString input, JavaString type) {
        return new RuleLexer(input, type, rule(), requiredType());
    }
}