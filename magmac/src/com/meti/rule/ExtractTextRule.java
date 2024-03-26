package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.Attribute;
import com.meti.node.StringAttribute;

import java.util.Map;
import java.util.Optional;

public class ExtractTextRule implements Rule {
    private final Rule required;
    private final String name;

    public ExtractTextRule(String name, Rule required) {
        this.required = required;
        this.name = name;
    }

    private Optional<Map<String, Attribute>> apply1(String input) {
        if (required.apply(input).map(tuple -> tuple.b()).isPresent()) {
            return Optional.of(Map.of(name, new StringAttribute(input)));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Tuple<Optional<String>, Map<String, Attribute>>> apply(String input) {
        return apply1(input).map(attributes -> new Tuple<>(Optional.empty(), attributes));
    }
}
