package com.meti.compile.rule;

import com.meti.api.collect.JavaString;

public class TextRuleLexer extends EncapsulatedRuleLexer {
    public TextRuleLexer(JavaString content) {
        super(content, '(', ')');
    }
}
