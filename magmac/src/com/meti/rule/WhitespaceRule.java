package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.Attribute;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;

public class WhitespaceRule implements Rule {
    public static final Rule WHITESPACE = new WhitespaceRule();
    public static final Rule PADDING = new OrRule(EmptyRule.EMPTY, WHITESPACE);

    private WhitespaceRule() {
    }

    @Override
    public Optional<String> render(Map<String, Attribute> attributes) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Tuple<Optional<String>, Map<String, Attribute>>> lex(String input, Stack<String> stack) {
        Optional<Map<String, Attribute>> result1;
        if (!input.isEmpty() && input.isBlank()) result1 = Optional.of(Collections.emptyMap());
        else result1 = Optional.empty();
        var result = result1.<Tuple<Optional<String>, Map<String, Attribute>>>map(attributes -> new Tuple<>(Optional.empty(), attributes));
        return result;
    }
}
