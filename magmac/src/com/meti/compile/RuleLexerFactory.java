package com.meti.compile;

import com.meti.api.collect.JavaString;
import com.meti.compile.declare.DeclarationLexer;
import com.meti.compile.lex.Rule;

public record RuleLexerFactory(String requiredType, Rule rule) {
    public DeclarationLexer createDeclarationLexer(JavaString input, JavaString type) {
        return new DeclarationLexer(input, type, rule(), requiredType());
    }
}