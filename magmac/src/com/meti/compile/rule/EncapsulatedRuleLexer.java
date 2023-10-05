package com.meti.compile.rule;

import com.meti.api.collect.JavaString;
import com.meti.api.option.Option;
import com.meti.api.option.Options;
import com.meti.compile.Lexer;

public class EncapsulatedRuleLexer implements Lexer<Rule> {
    protected final JavaString content;
    protected final char left;
    protected final char right;

    public EncapsulatedRuleLexer(JavaString content, char left, char right) {
        this.content = content;
        this.left = left;
        this.right = right;
    }

    @Override
    public Option<Rule> lex() {
        return Options.$Option(() -> {
            var start = content
                    .firstIndexOfChar(left).$()
                    .next().$();

            var end = content.firstIndexOfChar(right).$();
            var value = content.sliceBetween(start.to(end).$());
            return new ContentRule(value);
        });
    }
}
