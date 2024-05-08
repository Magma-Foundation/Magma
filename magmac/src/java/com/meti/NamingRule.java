package com.meti;

import java.util.Map;
import java.util.Optional;

public record NamingRule(Rule rule, String name) implements Rule {
    @Override
    public Optional<Tuple<Map<String, String>, Optional<String>>> apply(String stripped) {
        return rule.apply(stripped).map(tuple -> tuple.replaceRight(Optional.of(name)));
    }
}