package com.meti.rule;

import java.util.List;

public class QuantitySplitRule extends SplitRule implements Rule {
    public QuantitySplitRule(String propertyName, Rule childRule) {
        super(propertyName, childRule);
    }

    public static Rule Quantities(String propertyName, Rule childRule) {
        return new QuantitySplitRule(propertyName, childRule);
    }

    @Override
    public List<String> split(String input) {
        return List.of(input.split(","));
    }

    @Override
    protected String computeDelimiter() {
        return ", ";
    }
}
