package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.Attribute;
import com.meti.node.StringAttribute;

import java.util.Map;
import java.util.Optional;
import java.util.Stack;

public class ExtractTextRule implements Rule {
    private final Rule required;
    private final String name;

    public ExtractTextRule(String name, Rule required) {
        this.required = required;
        this.name = name;
    }

    @Override
    public Optional<String> render(Map<String, Attribute> attributes) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Tuple<Optional<String>, Map<String, Attribute>>> lex(String input, Stack<String> stack) {
        Optional<Tuple<Optional<String>, Map<String, Attribute>>> result1;
        if (required.lex(input, stack).isPresent())
            result1 = Optional.<Tuple<Optional<String>, Map<String, Attribute>>>of(new Tuple<>(Optional.empty(), Map.of(name, new StringAttribute(input))));
        else result1 = Optional.<Tuple<Optional<String>, Map<String, Attribute>>>empty();
        return result1;
    }
}
