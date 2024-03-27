package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.Attribute;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

class EmptyRule implements Rule {
    public static Rule EMPTY = new EmptyRule();

    private Optional<Tuple<Optional<String>, Map<String, Attribute>>> lex(String input) {
        if (input.isEmpty()) return Optional.of(new Tuple<>(Optional.empty(), Collections.emptyMap()));
        else return Optional.empty();
    }

    @Override
    public Optional<String> render(Map<String, Attribute> attributes) {
        return Optional.of("");
    }

    @Override
    public Optional<Tuple<Optional<String>, Map<String, Attribute>>> lexImpl(String input) {
var result = lex(input);
        return result;
    }
}
