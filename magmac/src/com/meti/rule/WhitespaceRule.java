package com.meti.rule;

import com.meti.node.Attribute;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class WhitespaceRule implements Rule {
    public static final Rule WHITESPACE = new WhitespaceRule();
    public static final Rule PADDING = new OrRule(Rules.EMPTY, WHITESPACE);

    private WhitespaceRule() {
    }

    @Override
    public Optional<Map<String, Attribute>> apply(String input) {
        if (input.isBlank()) return Optional.of(Collections.emptyMap());
        else return Optional.empty();
    }
}
