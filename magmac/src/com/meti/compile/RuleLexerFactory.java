package com.meti.compile;

import com.meti.api.collect.JavaString;
import com.meti.compile.rule.Rule;
import com.meti.compile.rule.RuleNodeLexer;

public record RuleLexerFactory(String requiredType, Rule rule) {
    public RuleNodeLexer create(JavaString input, JavaString type) {
        return new RuleNodeLexer(input, type, rule(), requiredType());
    }
}