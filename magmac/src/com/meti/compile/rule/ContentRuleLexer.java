package com.meti.compile.rule;

import com.meti.api.collect.JavaString;
import org.jetbrains.annotations.NotNull;

public final class ContentRuleLexer extends EncapsulatedRuleLexer {
    public ContentRuleLexer(JavaString content) {
        super(content, '<', '>');
    }

    @Override
    @NotNull
    protected Rule complete(JavaString value) {
        return new ContentRule(value);
    }
}
