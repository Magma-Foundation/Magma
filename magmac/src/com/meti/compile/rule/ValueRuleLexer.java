package com.meti.compile.rule;

import com.meti.api.collect.JavaString;
import org.jetbrains.annotations.NotNull;

public class ValueRuleLexer extends EncapsulatedRuleLexer {
    public ValueRuleLexer(JavaString content) {
        super(content, '"', '"');
    }

    @Override
    @NotNull
    protected Rule complete(JavaString value) {
        return new ValueRule(value);
    }
}
