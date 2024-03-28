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

    @Override
    public Optional<String> render(Map<String, Attribute> attributes) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Tuple<Optional<String>, Map<String, Attribute>>> lex(String input, Stack<String> stack) {
        Optional<Map<String, Attribute>> result;
        if (input.equals(requiredValue)) result = Optional.of(Collections.emptyMap());
        else result = Optional.empty();
        return result.map(attributes -> new Tuple<>(Optional.empty(), attributes));
    }

    @Override
    public String toString() {
        return "RequireRule{" +
               "requiredValue='" + requiredValue + '\'' +
               '}';
    }
}
