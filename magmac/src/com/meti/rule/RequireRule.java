package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.Attribute;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;

public class RequireRule implements Rule {
    private final String requiredValue;

    public RequireRule(String requiredValue) {
        this.requiredValue = requiredValue;
    }

    private Optional<Map<String, Attribute>> apply1(String input) {
        if (input.equals(requiredValue)) return Optional.of(Collections.emptyMap());
        else return Optional.empty();
    }

    private Optional<Tuple<Optional<String>, Map<String, Attribute>>> lex(String input) {
        return apply1(input).map(attributes -> new Tuple<>(Optional.empty(), attributes));
    }

    @Override
    public Optional<String> render(Map<String, Attribute> attributes) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Tuple<Optional<String>, Map<String, Attribute>>> lex(String input, Stack<String> stack) {
var result = lex(input);
        return result;
    }
}
