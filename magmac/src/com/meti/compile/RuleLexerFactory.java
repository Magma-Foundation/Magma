package com.meti.compile;

import com.meti.api.collect.JavaString;
import com.meti.compile.rule.Rule;
import com.meti.compile.rule.RuleLexer;

public record RuleLexerFactory(String requiredType, Rule rule) {
    public RuleLexer createDeclarationLexer(JavaString input, JavaString type) {
        return new RuleLexer(input, type, rule(), requiredType());
    }
}