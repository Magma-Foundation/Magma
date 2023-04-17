package com.meti;

import java.util.Map;
import java.util.Optional;

public class DisjunctionRule implements Rule {
    private final Rule first;
    private final Rule second;

    public DisjunctionRule(Rule first, Rule second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public Optional<Map<String, String>> extract(String value) {
        var firstResult = first.extract(value);
        if (firstResult.isPresent()) return firstResult;

        return second.extract(value);
    }
}
