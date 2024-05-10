package com.meti.rule;

import java.util.ArrayList;
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
        var lines = new ArrayList<String>();
        var builder = new StringBuilder();
        var depth = 0;
        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (c == ',' && depth == 0) {
                lines.add(builder.toString());
                builder = new StringBuilder();
            } else {
                if (c == '<') depth++;
                if (c == '>') depth--;
                builder.append(c);
            }
        }
        lines.add(builder.toString());

        return lines.stream()
                .filter(value -> !value.isBlank())
                .collect(Collectors.toList());
    }

    @Override
    protected String computeDelimiter() {
        return ", ";
    }

    @Override
    public RuleResult fromString(String value) {
        return fromString1(value).<RuleResult>map(NodeRuleResult::new).orElseGet(() -> new ErrorRuleResult("", ""));
    }
}
