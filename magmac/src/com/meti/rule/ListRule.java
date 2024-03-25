package com.meti.rule;

import com.meti.node.Attribute;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class ListRule implements Rule {
    private final Rule value;

    public ListRule(Rule value) {
        this.value = value;
    }

    @Override
    public Optional<Map<String, Attribute>> apply(String input) {
        if (input.isEmpty()) return Optional.of(Collections.emptyMap());

        return value.apply(input);
    }
}
