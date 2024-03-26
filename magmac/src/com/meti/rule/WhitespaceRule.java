package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.Attribute;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class WhitespaceRule implements Rule {
    public static final Rule WHITESPACE = new WhitespaceRule();
    public static final Rule PADDING = new OrRule(Rules.EMPTY, WHITESPACE);

    private WhitespaceRule() {
    }

    private Optional<Map<String, Attribute>> apply1(String input) {
        if (input.isBlank()) return Optional.of(Collections.emptyMap());
        else return Optional.empty();
    }

    @Override
    public Optional<Tuple<Optional<String>, Map<String, Attribute>>> apply(String input) {
        return apply1(input).map(attributes -> new Tuple<>(Optional.empty(), attributes));
    }
}
