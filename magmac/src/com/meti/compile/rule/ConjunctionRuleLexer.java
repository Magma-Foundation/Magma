package com.meti.compile.rule;

import com.meti.api.collect.JavaString;
import com.meti.api.option.Option;
import com.meti.compile.Lexer;

import static com.meti.api.option.Options.$Option;

public record ConjunctionRuleLexer(JavaString content) implements Lexer<Rule> {
    @Override
    public Option<Rule> lex() {
        return $Option(() -> {
            var separator = content.firstIndexOfChar(' ').$();
            var left = content.sliceTo(separator).strip();
            var right = content.sliceFrom(separator.next().$()).strip();
            return new ConjunctionRule(new UnrealizedRule(left), new UnrealizedRule(right));
        });
    }
}
