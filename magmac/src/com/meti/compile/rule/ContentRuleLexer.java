package com.meti.compile.rule;

import com.meti.api.collect.JavaString;

public final class ContentRuleLexer extends EncapsulatedRuleLexer {
    public ContentRuleLexer(JavaString content) {
        super(content, '<', '>');
    }
}
