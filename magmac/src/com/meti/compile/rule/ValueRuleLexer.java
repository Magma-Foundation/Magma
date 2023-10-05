package com.meti.compile.rule;

import com.meti.api.collect.JavaString;

public class ValueRuleLexer extends EncapsulatedRuleLexer {
    public ValueRuleLexer(JavaString content) {
        super(content, '"', '"');
    }
}
