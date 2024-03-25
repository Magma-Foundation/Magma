package com.meti.rule;

import com.meti.node.Attribute;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class RequireRule implements Rule {
    private final String requiredValue;

    public RequireRule(String requiredValue) {
        this.requiredValue = requiredValue;
    }

    @Override
    public Optional<Map<String, Attribute>> apply(String input) {
        if (input.equals(requiredValue)) return Optional.of(Collections.emptyMap());
        else return Optional.empty();
    }
}
