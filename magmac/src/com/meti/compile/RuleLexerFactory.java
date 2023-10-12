package com.meti.compile;

import com.meti.api.collect.JavaString;
import com.meti.api.result.Ok;
import com.meti.api.result.Result;
import com.meti.compile.node.Node;
import com.meti.compile.rule.Rule;
import com.meti.compile.rule.RuleNodeLexer;

public record RuleLexerFactory(String requiredType, Rule rule) implements LexerFactory<Node> {
    public Result<Lexer<Node>, CompileException> create(JavaString input, JavaString type) {
        return Ok.apply(new RuleNodeLexer(input, type, rule(), requiredType()));
    }
}