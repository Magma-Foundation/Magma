package com.meti.rule;

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

    @Override
    public Optional<Map<String, Attribute>> apply(String input) {
        if (required.apply(input).isPresent()) {
            return Optional.of(Map.of(name, new StringAttribute(input)));
        } else {
            return Optional.empty();
        }
    }
}
