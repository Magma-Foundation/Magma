package com.meti.compile.rule;

import com.meti.api.collect.JavaString;
import org.jetbrains.annotations.NotNull;

public class TextRuleLexer extends EncapsulatedRuleLexer {
    public TextRuleLexer(JavaString content) {
        super(content, '(', ')');
    }

    @Override
    @NotNull
    protected Rule complete(JavaString value) {
        return new TextRule(value);
    }
}
