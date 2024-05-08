package com.meti.rule;

import com.meti.Tuple;

import java.util.Map;
import java.util.Optional;

public record StripRule(Rule next) implements Rule {
    public static StripRule Strip(Rule rule) {
        return new StripRule(rule);
    }

    @Override
    public Optional<Tuple<Map<String, String>, Optional<String>>> apply(String value) {
        return next.apply(value.strip());
    }
}