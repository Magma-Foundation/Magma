package com.meti.rule;

import com.meti.node.Attribute;
import com.meti.node.StringListAttribute;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class StringListRule implements Rule {
    private final Rule required;
    private final String name;

    public StringListRule(String name, Rule required) {
        this.required = required;
        this.name = name;
    }

    @Override
    public Optional<Map<String, Attribute>> apply(String input) {
        if (required.apply(input).isPresent()) {
            return Optional.of(Map.of(name, new StringListAttribute(Collections.singletonList(input))));
        } else {
            return Optional.empty();
        }
    }
}
