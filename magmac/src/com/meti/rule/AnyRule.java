package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.Attribute;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;

public class AnyRule implements Rule {
    public static Rule Any = new AnyRule();

    private Optional<Tuple<Optional<String>, Map<String, Attribute>>> lex(String input) {
        return Optional.of(new Tuple<>(Optional.empty(), new HashMap<>()));
    }

    @Override
    public Optional<String> render(Map<String, Attribute> attributes) {
        return Optional.empty();
    }

    @Override
    public Optional<Tuple<Optional<String>, Map<String, Attribute>>> lex(String input, Stack<String> stack) {
        var result = lex(input);
        return result;
    }
}
