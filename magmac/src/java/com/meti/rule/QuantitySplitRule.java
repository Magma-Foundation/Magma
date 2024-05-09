package com.meti.rule;

import java.util.List;
import java.util.stream.Collectors;

public class QuantitySplitRule extends SplitRule implements Rule {
    public QuantitySplitRule(String propertyName, Rule childRule) {
        super(propertyName, childRule);
    }

    public static Rule Quantities(String propertyName, Rule childRule) {
        return new QuantitySplitRule(propertyName, childRule);
    }

    @Override
    public List<String> split(String input) {
        return List.of(input.split(","))
                .stream()
                .filter(value -> !value.isBlank())
                .collect(Collectors.toList());
    }

    @Override
    protected String computeDelimiter() {
        return ", ";
    }
}
